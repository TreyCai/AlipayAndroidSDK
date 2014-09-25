package com.alipay.android.utils;

import com.alipay.android.Keys;
import com.alipay.android.msp.utils.Rsa;

import android.text.TextUtils;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Alipay 的支付参数字符串生成类
 * <p/>
 * Example:
 * <pre>
 *     public String createAlipayInfo() {
 *         AlipayOrderInfoBuilder builder = new AlipayOrderInfoBuilder();
 *
 *         // builder.setLogEnabled(true); // For debug
 *         builder.setLogEnabled(false);
 *
 *         //---- Required ----
 *         builder.setOutTradeNo( your_out_trade_no );
 *         builder.setSubject( your_subject );
 *         builder.setBody( your_body );
 *         builder.setTotalFee( your_total_fee ); // String or double
 *
 *         //---- Optional ----
 *         builder.setNotifyUrl( your_notify_url );
 *         builder.setItBPay(new AlipayTimeOut(15).minute()); // For example, 15 min.
 *         builder.setShowUrl( your_show_url );
 *         builder.setAppId( your_app_id );
 *         builder.setAppEnv( your_app_env );
 *         builder.setExternToken( your_extern_token );
 *
 *         return builder.build();
 *     }
 * </pre>
 */
public final class AlipayOrderInfoBuilder {

    private static final String TAG = "AlipayOrderInfoBuilder";

    /**
     * 字符串拼接的 pattern
     * <p>需要在签名前用 {@link java.lang.StringBuilder#substring(int)} 去掉最开始的 '&'</p>
     */
    private static final String PARAM_FORMAT = "&%s=\"%s\"";

    private static final String DEFAULT_CHARSET = "utf-8";

    private StringBuilder mAlipayInfoBuilder;

    private boolean mLogEnabled = true;

    private String mOutTradeNo;
    private String mSubject;
    private String mBody;
    private String mTotalFee;
    private String mNotifyUrl;
    private String mItBPay;
    private String mShowUrl;
    private String mAppId;
    private String mAppEnv;
    private String mExternToken;

    public AlipayOrderInfoBuilder() {
        mAlipayInfoBuilder = new StringBuilder();
    }

    /**
     * @param outTradeNo 合作者的商户网站唯一的订单号
     * @see com.alipay.android.utils.AlipayParam#OUT_TRADE_NO
     */
    public void setOutTradeNo(String outTradeNo) {
        mOutTradeNo = outTradeNo;
    }

    /**
     * @param subject 商品名称
     * @see com.alipay.android.utils.AlipayParam#SUBJECT
     */
    public void setSubject(String subject) {
        mSubject = subject;
    }

    /**
     * @param body 商品详情
     * @see com.alipay.android.utils.AlipayParam#BODY
     */
    public void setBody(String body) {
        mBody = body;
    }

    /**
     * @param totalFee 该笔订单的资金总额
     * @see com.alipay.android.utils.AlipayParam#TOTAL_FEE
     */
    public void setTotalFee(String totalFee) {
        mTotalFee = totalFee;
    }

    /**
     * @param totalFee 该笔订单的资金总额
     * @see com.alipay.android.utils.AlipayParam#TOTAL_FEE
     */
    public void setTotalFee(double totalFee) {
        mTotalFee = String.format("%.2f", totalFee);
    }

    /**
     * @param notifyUrl 服务器异步通知页面的 url
     * @see com.alipay.android.utils.AlipayParam#NOTIFY_URL
     */
    public void setNotifyUrl(String notifyUrl) {
        setNotifyUrl(notifyUrl, DEFAULT_CHARSET);
    }

    /**
     * 应使用 {@link #setNotifyUrl(String)}, 默认编码是 utf-8
     *
     * @param notifyUrl 服务器异步通知页面的 url
     * @param charset   Url encode 的编码字符集
     * @see com.alipay.android.utils.AlipayParam#NOTIFY_URL
     */
    public void setNotifyUrl(String notifyUrl, String charset) {
        mNotifyUrl = encode(notifyUrl, charset);
    }

    /**
     * @param itBPay 未付款交易的超时时间
     * @see com.alipay.android.utils.AlipayParam#IT_B_PAY
     */
    public void setItBPay(String itBPay) {
        mItBPay = itBPay;
    }

    /**
     * @param showUrl 商品展示的 url 地址
     * @see com.alipay.android.utils.AlipayParam#SHOW_URL
     */
    public void setShowUrl(String showUrl) {
        setShowUrl(showUrl, DEFAULT_CHARSET);
    }

    /**
     * 应使用 {@link #setShowUrl(String)}, 默认编码是 utf-8
     *
     * @param showUrl 商品展示的 url 地址
     * @param charset Url encode 的编码字符集
     * @see com.alipay.android.utils.AlipayParam#SHOW_URL
     */
    public void setShowUrl(String showUrl, String charset) {
        mShowUrl = encode(showUrl, charset);
    }

    /**
     * @param logEnabled 是否打印更详细的参数信息
     */
    public void setLogEnabled(boolean logEnabled) {
        mLogEnabled = logEnabled;
    }

    /**
     * @param appId 客户端号
     * @see com.alipay.android.utils.AlipayParam#APP_ID
     */
    public void setAppId(String appId) {
        mAppId = appId;
    }

    /**
     * @param appEnv 客户端来源，用来标识客户端来源
     * @see com.alipay.android.utils.AlipayParam#APP_ENV
     */
    public void setAppEnv(String appEnv) {
        mAppEnv = appEnv;
    }

    /**
     * @param externToken 授权令牌
     * @see com.alipay.android.utils.AlipayParam#EXTERN_TOKEN
     */
    public void setExternToken(String externToken) {
        mExternToken = externToken;
    }

    /**
     * @return 生成支付宝所需要的字符串
     */
    public String build() {
        logAlipayInfo("Alipay info before build: ");

        mAlipayInfoBuilder.setLength(0); // Clean the builder
        appendRequiredParam(AlipayParam.PARTNER, Keys.DEFAULT_PARTNER);
        appendRequiredParam(AlipayParam.SELLER_ID, Keys.DEFAULT_SELLER);
        appendRequiredParam(AlipayParam.OUT_TRADE_NO, mOutTradeNo);
        appendRequiredParam(AlipayParam.SUBJECT, mSubject);
        appendRequiredParam(AlipayParam.BODY, mBody);
        appendRequiredParam(AlipayParam.TOTAL_FEE, mTotalFee);
        appendRequiredParam(AlipayParam.SERVICE, "mobile.securitypay.pay");
        appendRequiredParam(AlipayParam.INPUT_CHARSET, DEFAULT_CHARSET);
        appendRequiredParam(AlipayParam.PAYMENT_TYPE, "1");
        appendOptionalParam(AlipayParam.NOTIFY_URL, encode(mNotifyUrl, DEFAULT_CHARSET));
        appendOptionalParam(AlipayParam.IT_B_PAY, mItBPay);
        appendOptionalParam(AlipayParam.SHOW_URL, encode(mShowUrl, DEFAULT_CHARSET));
        appendOptionalParam(AlipayParam.APP_ID, mAppId);
        appendOptionalParam(AlipayParam.APP_ENV, mAppEnv);
        appendOptionalParam(AlipayParam.EXTERN_TOKEN, mExternToken);

        // 对要支付的内容进行 RSA 签名，再把签名后的内容并入到支付字符串中
        appendRequiredParam(AlipayParam.SIGN, sign());
        appendRequiredParam(AlipayParam.SIGN_TYPE, "RSA");

        logAlipayInfo("Alipay info after build:");
        if (mLogEnabled) {
            Log.i(TAG, "Alipay info string: " + mAlipayInfoBuilder);
        }
        return mAlipayInfoBuilder.toString();
    }

    private void appendRequiredParam(String paramName, String paramValue) {
        if (TextUtils.isEmpty(paramValue)) {
            Log.e(TAG, paramName + " is empty.");
            return;
        }
        mAlipayInfoBuilder.append(String.format(PARAM_FORMAT, paramName, paramValue));
    }

    private void appendOptionalParam(String paramName, String paramValue) {
        if (TextUtils.isEmpty(paramValue)) {
            if (mLogEnabled) {
                Log.w(TAG, paramName + " is empty.");
            }
        } else {
            mAlipayInfoBuilder.append(String.format(PARAM_FORMAT, paramName, paramValue));
        }
    }

    /**
     * 对已拼接的字符串进行 {@link com.alipay.android.msp.utils.Rsa} 签名
     *
     * @return 签名后的字串
     */
    private String sign() {
        // 移除第一個 '&'
        String sign = Rsa.sign(mAlipayInfoBuilder.substring(1), Keys.PRIVATE);
        return encode(sign, DEFAULT_CHARSET);
    }

    private String encode(String content, String charset) {
        if (TextUtils.isEmpty(content)) {
            return null;
        }

        try {
            return URLEncoder.encode(content, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 打印每个参数
     *
     * @param titleMsg 该次打印的 title
     */
    private void logAlipayInfo(String titleMsg) {
        if (!mLogEnabled) {
            return;
        }

        String signString = TextUtils.isEmpty(mAlipayInfoBuilder) ? "" : sign();

        String logMsg = titleMsg
                + "\n(Required) [" + AlipayParam.PARTNER + "]: " + Keys.DEFAULT_PARTNER
                + "\n(Required) [" + AlipayParam.SELLER_ID + "]: " + Keys.DEFAULT_SELLER
                + "\n(Required) [" + AlipayParam.OUT_TRADE_NO + "]: " + mOutTradeNo
                + "\n(Required) [" + AlipayParam.SUBJECT + "]: " + mSubject
                + "\n(Required) [" + AlipayParam.BODY + "]: " + mBody
                + "\n(Required) [" + AlipayParam.TOTAL_FEE + "]: " + mTotalFee
                + "\n(Required) [" + AlipayParam.SERVICE + "]: " + "mobile.securitypay.pay"
                + "\n(Required) [" + AlipayParam.INPUT_CHARSET + "]: " + DEFAULT_CHARSET
                + "\n(Required) [" + AlipayParam.PAYMENT_TYPE + "]: " + "1"
                + "\n(Optional) [" + AlipayParam.NOTIFY_URL + "]: " + mNotifyUrl
                + "\n(Optional) [" + AlipayParam.IT_B_PAY + "]: " + mItBPay
                + "\n(Optional) [" + AlipayParam.SHOW_URL + "]: " + mShowUrl
                + "\n(Optional) [" + AlipayParam.APP_ID + "]: " + mAppId
                + "\n(Optional) [" + AlipayParam.APP_ENV + "]: " + mAppEnv
                + "\n(Optional) [" + AlipayParam.EXTERN_TOKEN + "]: " + mExternToken
                + "\n(Required) [" + AlipayParam.SIGN + "]: " + signString
                + "\n(Required) [" + AlipayParam.SIGN_TYPE + "]: " + "RSA";
        Log.i(TAG, logMsg);
    }
}
