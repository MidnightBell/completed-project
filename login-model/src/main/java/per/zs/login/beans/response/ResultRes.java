package per.zs.login.beans.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import per.zs.login.beans.enums.HttpCodeEnum;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel("api接口通用返回对象")
public class ResultRes<T> {

    @ApiModelProperty(value = "返回数据", position = 6)
    private T data;
    @ApiModelProperty(value = "返回码", position = 1)
    protected String code;
    @ApiModelProperty(value = "说明信息", position = 7)
    protected String msg;

    private void setCodeAndMsg(String code, T data, String msg){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public void setCodeAndMsg(HttpCodeEnum httpCodeEnum){
        setCodeAndMsg(httpCodeEnum.getCode(), null, httpCodeEnum.getMsg());
    }

    public void setCodeAndMsg(HttpCodeEnum httpCodeEnum, String msg){
        setCodeAndMsg(httpCodeEnum.getCode(), null, msg);
    }

    public void setCodeAndMsgAndData(HttpCodeEnum httpCodeEnum, T data){
        setCodeAndMsg(httpCodeEnum.getCode(), data, httpCodeEnum.getMsg());
    }

    public void setCodeAndMsgAndData(HttpCodeEnum httpCodeEnum, T data, String msg){
        setCodeAndMsg(httpCodeEnum.getCode(), data, msg);
    }

    public ResultRes(HttpCodeEnum httpCodeEnum) {
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

    public ResultRes(HttpCodeEnum httpCodeEnum, T data) {
        this.code = httpCodeEnum.getCode();
        this.data = data;
        this.msg = httpCodeEnum.getMsg();
    }

    public ResultRes(HttpCodeEnum httpCodeEnum, T data, String msg) {
        this.code = httpCodeEnum.getCode();
        this.data = data;
        this.msg = msg;
    }

    public ResultRes() {
        super();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
