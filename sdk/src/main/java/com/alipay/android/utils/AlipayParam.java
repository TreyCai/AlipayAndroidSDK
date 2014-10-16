package com.alipay.android.utils;

/**
 * 支付宝请求接口的参数名称及其文档内的说明
 */
public final class AlipayParam {

    private AlipayParam() {
    }

    /**
     * 接口名称，固定值为 mobile.securitypay.pay
     */
    public static final String SERVICE = "service";

    /**
     * 合作者身份 ID。<br />
     * 与支付宝签约的账号对应的支付宝唯一用户号，2088 开头的 16 位纯数字
     */
    public static final String PARTNER = "partner";

    /**
     * 参数编码字符集。<br />
     * 作者的商户网站的输入格式，固定值为 utf-8
     */
    public static final String INPUT_CHARSET = "_input_charset";

    /**
     * 签名类型，仅支持 {@link com.alipay.android.msp.utils.Rsa}
     */
    public static final String SIGN_TYPE = "sign_type";

    /**
     * 签名内容
     */
    public static final String SIGN = "sign";

    /**
     * 服务器异步通知页面的 url。<br />
     * 支付宝服务器主动通知合作者的商户网站里指定的页面 http 路径。需要 URL encode。
     */
    public static final String NOTIFY_URL = "notify_url";

    /**
     * 客户端号。<br />
     * 用来标识客户端。
     */
    public static final String APP_ID = "app_id";

    /**
     * 客户端来源，用来标识客户端来源。<br />
     * <pre>
     *     参数值内容 约定如下:
     *     appenv=”system=客户端平台名^version=业务系统版本”,
     *
     *     Example:
     *       appenv=”system=iphone^version=3.0.1.2”
     *       appenv=”system=ipad^version=4.0.1.1”
     * </pre>
     */
    public static final String APP_ENV = "appenv";

    /**
     * 合作者的商户网站唯一的订单号
     */
    public static final String OUT_TRADE_NO = "out_trade_no";

    /**
     * 商品名称。<br />
     * 商品的标题/交易标题/订单标题/订单关键字等。该参数最长为 128 个汉字。
     */
    public static final String SUBJECT = "subject";

    /**
     * 支付类型。默认值为 1（商品购买）
     */
    public static final String PAYMENT_TYPE = "payment_type";

    /**
     * 合作者的收款的支付宝账号（邮箱，手机或 {@link #PARTNER}）
     */
    public static final String SELLER_ID = "seller_id";

    /**
     * 该笔订单的资金总额。<br />
     * 单位为 RMB-Yuan。取值范围为 [0.01, 100000000.00]，精确到小数点后两位。
     */
    public static final String TOTAL_FEE = "total_fee";

    /**
     * 商品详情。<br />
     * 对一笔交易的具体描述信息。如果是多种商品,请将商品描述字符串累加传给 body。
     */
    public static final String BODY = "body";

    /**
     * 未付款交易的超时时间。<br />
     * <pre>
     *     设置未付款交易的超时时间，一旦超时，该笔交易就会自动被关闭。
     *     取值范围: 1m~15d
     *     m-分钟, h-小时, d-天, 1c-当天(无论交易何时创建,都在 0 点关闭)。
     *     该参数数值不接受小数点，如 1.5h,可转换为 90m。
     * </pre>
     */
    public static final String IT_B_PAY = "it_b_pay";

    /**
     * 商品展示的 url 地址
     */
    public static final String SHOW_URL = "show_url";

    /**
     * 授权令牌。<br />
     * 开放平台返回的账户的 token(授权令牌，商户在一定时间内对支付宝某些服务的访问权限)。
     */
    public static final String EXTERN_TOKEN = "extern_token";
}
