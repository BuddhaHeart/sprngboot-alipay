package com.tang.config;

public class AlipayConfig {

	public static String app_id = "2016091400507316";//??????????????????
	
	public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCBY9za+VKnk3ZrSPhE4LtarUqp3nRE6gQYOwlewUZciMv6vxn9XPqyKmZNFqm4vdijMIXScaR1gIFVbrXYz13PW+5sikhtdeeQAz5UK6JIw7cJq2XQR6GoHqWPgdXZcXkanVZt3cSbFvkRtoO5yRhGuJ0R2517JT4/7trFFz6qxfYFjZqvkXBJAC9kGoo97rRlcfnzSyuXSFo+US2CKlyObLG0kbUPw/zGtCzqskmgAUtaTdsZGA74xtlIQwLp0+5QTNZEyWNJiuZMnTXfQF3F7kTSeZ7K3TJVL6ZrP5Lw3Z1VpFZVn/+WELamveoTJxs8gM6sSKlx8/QhUjXGJJ/TAgMBAAECggEAdu2hPKxaKqlB+U6y1Cx0SN5reEN+PzGR3eu1qyCfWcJrKuioLNdfno+WRFkGW0Y0b5rUBiBqd45MUohWIRhJM6VeOunHtMTguOAiVB9fJN6vyPV/ubd9gRfaOVOvihPgqnPf01A5vAubl4pY/VU3ORnLVXS1BVGwK/eRogLBmRjT1lm9IKMWnHpeUWfbVQAPvCNMem+TTmLngQPzvew8PO2tJsUtuxNRRQj89SIy8O8f0A5NW730KP+xX6Cnbb1SgneSF2R/kiRWM/ooUtG0zEJhQASxQ9zZDkapzZjobLgfy8hXA6wV3xW81KwYk8sV5K35FY8AG0YwykRIMZEZQQKBgQDaqHg3SEtn2SP1/McaGhK72Xs5lhPtgLmt25Y2dGR6lz+UBsfljzFVQK8zg4WatseU1a1vOsM2KK+vMFthMqLi5Bx3lit+GJ9U7L7ky/pc3uWzdp/ML4puAXIGd4LxKndtYRIeArRiTS2kRwKdAklgjJ0sXO+9VZt7/KuN+ckbMQKBgQCXfK2tnKrQ7YOyhAC9/AQAPx1LaRbraZkCZvLN6F3C/Owed6lOTpUIZpHa/anzRvNQ4hRXdfEp8AqKeTMnokeCD6lpGTmYfw684fh10s/tr1sgbmFaJdUlqyBhrJ4VjvIA/KMA3SUdwo1cqtjqucwnQx7CJJxkyYHNfGNT+cYiQwKBgF1ejR8GJvTsWxfCXf6+XXuLjbv0YwQK8L7Vwpn0L6fNiB+cmaVj9OvzmBzGL7n2DNl4OPwDOrKYwxRq8MpbhgSe5a0HGgFZYEgyY/1tQLaBk9KDeA8HyfPZeChcxjRXLvIs/kCEZSSI0lGQ5QPIoy6wPLlh4hmWQbO7HEKfBAoxAoGAfwlIYJJEUScNTTQTpz7jYA2NlILRfKO2c+Q/c9uMXYtau4YfzhJqOnxBjoMw9VmnjEm7Q0kd78xkGpHfCzq0euaFbCSCP1c11cIuaShbxHhMlBrcuwuE/qzOX/qxK2UPnzI9w+iwSo+vmmmPtPAQu1A3SBoJqR+vIdJRPcf1lx8CgYAEvyfCfH5d3cm6AaaVJ9Jme+3mw5KLjxum3BajA1zXV26pcXPIf47FJAdtcGuXgIEsiQAsisbZmHwYdaiGuWbYoYPzGYpVVk2kZcEI4hRr3qeh3zkiuuZnNUpZeSt0RT8RUapzLSFoJkVu+g5c8IHLjXgBwASXIwR4CqGDf0bg6Q==";//?????????????????????

	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgWPc2vlSp5N2a0j4ROC7Wq1Kqd50ROoEGDsJXsFGXIjL+r8Z/Vz6sipmTRapuL3YozCF0nGkdYCBVW612M9dz1vubIpIbXXnkAM+VCuiSMO3Catl0EehqB6lj4HV2XF5Gp1Wbd3Emxb5EbaDuckYRridEdudeyU+P+7axRc+qsX2BY2ar5FwSQAvZBqKPe60ZXH580srl0haPlEtgipcjmyxtJG1D8P8xrQs6rJJoAFLWk3bGRgO+MbZSEMC6dPuUEzWRMljSYrmTJ0130Bdxe5E0nmeyt0yVS+maz+S8N2dVaRWVZ//lhC2pr3qEycbPIDOrEipcfP0IVI1xiSf0wIDAQAB";//?????????????????????
	
	public static String notify_url = "http://localhost:8089/alipay/alipayNotifyNotice.action";
	
	public static String return_url = "http://localhost:8089/alipay/alipayReturnNotice.action";
	
	public static String sign_type = "RSA2";
	
	public static String charset = "utf-8";
	
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//????????????????????????https://openapi.alipay.com/gateway.do
}
