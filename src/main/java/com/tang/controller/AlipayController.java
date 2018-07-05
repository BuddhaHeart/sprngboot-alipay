package com.tang.controller;

import com.alibaba.fastjson.JSON;
import com.tang.common.BaseResult;
import com.tang.common.OrderStatusEnum;
import com.tang.model.Order;
import com.tang.model.Product;
import com.tang.service.OrderService;
import com.tang.service.ProductService;
import com.tang.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping("/product")
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
    @GetMapping("")
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
    public String goConfirm(ModelMap modelMap,String productId){
        modelMap.addAttribute("product",productService.getProductById(productId));
        return "goConfirm";
    }

    /**
     * 生成订单
     * @param order
     * @return
     */
    @GetMapping("/createOrder")
    @ResponseBody
    public String createOrder(Order order){
        Product product = productService.getProductById(order.getProductId());
        order.setOrderNum(Utils.getOrderNumber());
        order.setOrderStatus(OrderStatusEnum.WAIT_PAY.getValue());
        order.setOrderAmount(String.valueOf(Float.valueOf(product.getPrice()) * order.getBuyCounts()));
        order.setCreateTime(new Date());
        orderService.insert(order);
        BaseResult baseResult = new BaseResult();
        baseResult.setCode("200");
        baseResult.setData(order);
        return JSON.toJSONString(baseResult);
    }

    @GetMapping("/goPay")
    public String goPay(ModelMap modelMap,Integer orderId){
       modelMap.addAttribute("order",orderService.getOrderById(orderId));
        return "goPay";
    }
}
