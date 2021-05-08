package per.zs.common.beans.enums;

/**
* Create time 2020年8月25日 上午9:28:47 
* @author sheng.zhong 
* @Description
 */
public enum HttpCodeEnum {
    //成功
    TRUE("200","success", "成功"),
    NO_RETURN("201", "no data", "成功但无数据返回"),
    UN_LOGIN("300", "unlogin", "未登录"),
    //失败
    ERROR("401", "error", "异常"),
    FAIL("402", "fail", "失败"),
    PARAM_ERROR("403", "parameter error", "参数出错"),
    UN_EXIST("404", "The query object does not exist", "查询对象不存在"),
    IS_EXIST("405", "data is exist", "数据已经存在");

    private String code;
    private String msg;
    private String cmt;

    HttpCodeEnum(String code, String msg, String cmt){
        this.code = code;
        this.msg = msg;
        this.cmt = cmt;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCmt() {
        return cmt;
    }
}
