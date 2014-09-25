AlipayAndroidSDK
================

 基于官方的支付宝 Android SDK 稍作改进
 
 一方面为了更好 debug
 
 一方面为了稍微好看点的代码

## 如何使用

第一步:

    git clone git@github.com:TreyWalker/AlipayAndroidSDK.git

第二步:

Android Studio：File -> Import module

第三步:

选中要添加 sdk 的 module -> F3 -> Dependencies -> 添加 module library -> OK

第四步:

例子:

    private void toPay() {
        AliPay aliPay = new AliPay(PayActivity.this, mAlipayHandler);
        //设置为沙箱模式，不设置默认为线上环境
        //alipay.setSandBox(true);
        String result = aliPay.pay(createAlipayInfo());
        
        ...
    }

	public String createAlipayInfo() {
         AlipayOrderInfoBuilder builder = new AlipayOrderInfoBuilder();
 
         // builder.setLogEnabled(true); // For debug
         builder.setLogEnabled(false);

         //---- Required ----
         builder.setOutTradeNo( your_out_trade_no );
         builder.setSubject( your_subject );
         builder.setBody( your_body );
         builder.setTotalFee( your_total_fee ); // String or double
 
         //---- Optional ----
         builder.setNotifyUrl( your_notify_url );
         builder.setItBPay(new AlipayTimeOut(15).minute()); // For example, 15 min.
         builder.setShowUrl( your_show_url );
         builder.setAppId( your_app_id );
         builder.setAppEnv( your_app_env );
         builder.setExternToken( your_extern_token );

         return builder.build();
     }

## 官方支付宝无线 SDK 及文档地址

Here: [官方地址](https://b.alipay.com/order/productDetail.htm?productId=2014082507508574&tabId=4#ps-tabinfo-hash)

