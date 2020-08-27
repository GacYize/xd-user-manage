package com.xdbigdata.user_manage_admin.bean;
import com.xdbigdata.user_manage_admin.constant.ErrorCodeConstant;
import lombok.Data;

import java.io.Serializable;
/**
 * 返回结果的通用类
 */
@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -5682944106593327862L;
    public static final Boolean SUCCESS = true;
    public static final Boolean FAILED = false;
    public static final Integer SUCCESS_CODE=0;
    public static final String SUCCESS_MSG = "成功";
    public static final String FAILED_MSG = "失败";


    private boolean status = SUCCESS;
    private String message = SUCCESS_MSG;
    private Integer code=SUCCESS_CODE;
    private T data;

    public static ResultBean createResultBean(String message){
        ResultBean resultBean=new ResultBean();
        resultBean.setMessage(message);
        return resultBean;
    }

    public ResultBean() {
        super();
    }

    public ResultBean(T data) {
        this();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        this();
        this.code= ErrorCodeConstant.ERROR_CODE_UNKNOWN;
        this.message = e.getMessage();
        this.status = FAILED;
    }

    public ResultBean(int code, String message, T data) {
        super();
        this.code = code;
        this.status = FAILED;
        this.message = message;
        this.data = data;
    }

    public ResultBean(int code, Throwable e, T data) {
        this(code,e.getMessage(),data);
    }


    public ResultBean(int code, Throwable e ) {
        this(code,e.getMessage(),null);
    }

    public ResultBean(int code, String message ) {
        this(code,message,null);
    }
}
