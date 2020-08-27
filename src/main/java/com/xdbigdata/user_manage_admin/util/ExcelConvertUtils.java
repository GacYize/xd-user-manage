package com.xdbigdata.user_manage_admin.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Excel字段转换工具
 */
public class ExcelConvertUtils {

    /**
     * 转换地区
     */
    public String convertOriginPlace(String code) {
        return OriginPlaceUtils.getName(code);
    }


    /**
     * 转换日期
     */
    public String convertOriginDate(Date date) {
        if(date == null){
            return null;
        }else{
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd" );
            return sdf.format(date);
        }
    }

    /**
     * 转换日期
     */
    public String convertOriginDate1(Date date) {
        if(date == null){
            return null;
        }else{
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM" );
            return sdf.format(date);
        }
    }




}
