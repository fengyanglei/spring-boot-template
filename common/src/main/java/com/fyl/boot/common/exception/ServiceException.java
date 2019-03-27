package com.fyl.boot.common.exception;

/**
 * Created by lwb on 2017-6-29.
 */
public class ServiceException extends Exception {
    private String msg;

    private String businessType;




    public ServiceException() {
    }

    public ServiceException(String msg) {
        this.msg = msg;
    }

    public ServiceException businessType(String businessType){
        this.setBusinessType(businessType);
        return this;
    }


    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
