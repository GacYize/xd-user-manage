package com.xdbigdata.user_manage_admin.model.vo;

import lombok.Data;

import java.util.List;

@Data
public class CustomSelectionResultVo {

    private int pageNum;
    private int pageSize;
    private int pages;
    private long total;

    private List<List<DataResultVo>> data;
}
