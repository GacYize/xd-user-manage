package com.xdbigdata.user_manage_admin.base;
import com.xdbigdata.user_manage_admin.constant.PageConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 分页请求基础类
 */
@ApiModel(value = "分页基础类", description = "分页请求基础类")
public class BasePageQo implements Serializable {

    private static final long serialVersionUID = -2617812453505684850L;
    /*
     * 页码，从1开始
     */
    @ApiModelProperty(value = "page 页码从1开始", required = true)
    protected int pageNum;
    /*
     * 页面大小
     */
    @ApiModelProperty(value = "分页每页大小", required = true)
    protected int pageSize;
    /*
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段", required = false)
    protected String orderByColumn;
    /*
     * 排序方式
     */
    @ApiModelProperty(value = "排序方式：desc，asc", required = false)
    protected String orderByType;

    public int getPageNum() {

        return pageNum < 1 ? PageConstant.DEFAULT_PAGE_NUM : pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize < 1 ? PageConstant.DEFAULT_PAGE_SIZE : pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderByColumn() {
        return orderByColumn;
    }

    public void setOrderByColumn(String orderByColumn) {
        this.orderByColumn = orderByColumn;
    }

    public String getOrderByType() {
        return orderByType;
    }

    public void setOrderByType(String orderByType) {
        this.orderByType = orderByType;
    }
}