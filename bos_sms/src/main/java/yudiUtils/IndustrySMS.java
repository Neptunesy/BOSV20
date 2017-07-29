package yudiUtils;


import yudiUtils.common.Config;
import yudiUtils.common.HttpUtil;

/**
 * 验证码通知短信接口
 *
 * @ClassName: IndustrySMS
 * @Description: 验证码通知短信接口
 */
public class IndustrySMS {
    private static String operation = "/industrySMS/sendSMS";

    private static String accountSid = Config.ACCOUNT_SID;
    //	private static String to = "18826202591";

    /**
     * 验证码通知短信
     */
    public static void execute(String to, String content) {
        String url = Config.BASE_URL + operation;
        String body = "accountSid=" + accountSid + "&to=" + to + "&smsContent=" + "【itsun科技】验证码：{" + content + "}，打死都不要告诉别人哦！"
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);
    }

}
