package com.xdbigdata.user_manage_admin.model.dto.api;

import lombok.Data;

/**
 * @Author:666
 * @Date:2019/11/11 16:29
 * @Version:1.0.0
 **/
@Data
public class RecordQuery {

    private String userkey;

    private Integer pageSize;

    private Integer pageNum;

    private String sn;
}
