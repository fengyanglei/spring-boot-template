package com.fyl.boot.util;

import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidUtils {

    public static String getErrorMsg(BindingResult r) {
        List<FieldError> list = r.getFieldErrors();
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }
        StringBuffer err = new StringBuffer();
        String msg = "";
        for (int i = 0; i < list.size(); i++) {
            msg = list.get(0).getField() + " : " + list.get(0).getDefaultMessage();
            if (i == 0) {
                err.append(msg);
            } else {
                err.append(" & " + msg);
            }
        }
        return err.toString();
    }

}
