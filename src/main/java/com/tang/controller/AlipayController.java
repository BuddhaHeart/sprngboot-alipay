package com.tang.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.tang.common.BaseResult;
import com.tang.common.OrderStatusEnum;
import com.tang.config.AlipayConfig;
import com.tang.model.Order;
import com.tang.model.Product;
import com.tang.service.OrderService;
import com.tang.service.ProductService;
import com.tang.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/alipay")
@Slf4j
public class AlipayController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    /**
     * 商品列表
     * @param modelMap
     * @return
     */
    @GetMapping("/products")
    public String getProductList(ModelMap modelMap){
        modelMap.addAttribute("productList",productService.getProductList());
        return "product";
    }

    /**
     * 确认页面
     * @param modelMap
     * @param productId
     * @return
     */
    @GetMapping("/goConfirm")
    public String goConfirm(ModelMap modelMap,@RequestParam("productId") Integer productId){
        modelMap.addAttribute("product",productService.getProductById(productId));
        return "goConfirm";
    }

    /**
     * 生成订单
     * @param order
     * @return
     */
    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(Order order){
        Product product = productService.getProductById(order.getProductId());
        order.setOrderNum(Utils.getOrderNumber());
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.getValue());
        order.setOrderAmount(String.valueOf(Float.valueOf(product.getPrice()) * order.getBuyCounts()));
        order.setCreateTime(new Date());
        orderService.insert(order);
        BaseResult baseResult = new BaseResult();
        baseResult.setSuccess(true);
        baseResult.setCode("200");
        baseResult.setData(order.getId());
        return JSON.toJSONString(baseResult);
    }

    @GetMapping("/goPay")
    public String goPay(ModelMap modelMap,Integer orderId){
        Order order = orderService.getOrderById(orderId);
        modelMap.addAttribute("order",order);
        modelMap.addAttribute("product",productService.getProductById(order.getProductId()));
        return "goPay";
    }
    @PostMapping("/goAlipay")
    @ResponseBody
    public String goAlipay(Integer orderId, HttpServletRequest request, HttpServletResponse response){
        Order order = orderService.getOrderById(orderId);

        Product product = productService.getProductById(order.getProductId());

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNum();
        //付款金额，必填
        String total_amount = order.getOrderAmount();
        //订单名称，必填
        String subject = product.getName();
        //商品描述，可空
        String body = "用户订购商品个数：" + order.getBuyCounts();

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1c";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.info("支付结果:" + result);
        return JSON.toJSONString(result);
    }
    /**
     * @Description: 支付宝同步通知页面
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, HttpServletRequest response,ModelMap modelMap) throws Exception {

        log.info("支付成功, 进入同步通知接口...");

        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
            System.out.println("name:" + name + ",value:" + valueStr);
        }
        log.info("params:" + String.valueOf(params.values()));
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
            //orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
            orderService.updateOrderStatus(out_trade_no);

            Order order = orderService.getOrderByTradeNum(out_trade_no);
            Product product = productService.getProductById(order.getProductId());

            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");

            modelMap.addAttribute("out_trade_no",out_trade_no);
            modelMap.addAttribute("trade_no",trade_no);
            modelMap.addAttribute("total_amount",total_amount);
            modelMap.addAttribute("productName",product.getName());

        }else {
            log.info("支付, 验签失败...");
        }

        return "paySucess";
    }

    /**
     *
     * @Title: AlipayController.java
     * @Package com.sihai.controller
     * @Description: 支付宝异步 通知页面
     * Copyright: Copyright (c) 2017
     * Company:FURUIBOKE.SCIENCE.AND.TECHNOLOGY
     *
     * @author sihai
     * @date 2017年8月23日 下午8:51:13
     * @version V1.0
     */
    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名
        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
                orderService.updateOrderStatus(out_trade_no);

                Order order = orderService.getOrderByTradeNum(out_trade_no);
                Product product = productService.getProductById(order.getProductId());

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
                log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");
            }
            log.info("支付成功...");

        }else {//验证失败
            log.info("支付, 验签失败...");
        }

        return "success";
    }
}
