package com.tang.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {
    /**
     * 生成16位订单号
     * @return
     */
    public static String getOrderNumber(){
        String s = "TZF";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        String format = sdf.format(c.getTime());
        StringBuffer str = new StringBuffer();
        str.append(s + format).append(getOneRandom(5));
        return str.toString();
    }
    //六位随机码数
    static String getOneRandom(int n){
        String str = "";
        while (str.length() < n){
            str = str + (int)(Math.random() * 10);
        }
        return str;
    }
}
