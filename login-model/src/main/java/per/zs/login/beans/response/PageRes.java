package per.zs.login.beans.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import per.zs.login.beans.enums.HttpCodeEnum;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
@ApiModel("api接口分页通用返回对象")
public class PageRes<T> extends ResultRes<T> {
    @ApiModelProperty(notes = "当前页码", position = 2)
    private String pageNum;

    @ApiModelProperty(notes = "单页数据量", position = 3)
    private String pageSize;

    @ApiModelProperty(notes = "总页数", position = 4)
    private String totalPageNum;

    @ApiModelProperty(notes = "总数据量", position = 5)
    private String totalSize;

    public void setPageNum(int pageNum) {
        this.pageNum = String.valueOf(pageNum);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = String.valueOf(pageSize);
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = String.valueOf(totalPageNum);
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = String.valueOf(totalSize);
    }

    public PageRes(HttpCodeEnum httpCodeEnum) {
        setCode(httpCodeEnum.getCode());
        setMsg(httpCodeEnum.getMsg());
    }

    public PageRes(HttpCodeEnum httpCodeEnum, T data) {
        setCode(httpCodeEnum.getCode());
        setData(data);
        setMsg(httpCodeEnum.getMsg());
    }
    
    public PageRes(HttpCodeEnum httpCodeEnum, String message) {
        setCode(httpCodeEnum.getCode());
        setMsg(message);
    }

    public PageRes() {
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(String totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }
}
