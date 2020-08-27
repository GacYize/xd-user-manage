package com.xdbigdata.user_manage_admin.model.dto.student;

import com.xdbigdata.framework.excel.annotation.UploadExcelTitle;
import com.xdbigdata.framework.excel.annotation.UploadVerify;
import lombok.Data;

/**
 * Created by qiujian on 2018/11/6.
 */
@Data
@UploadVerify(verifyTitleLength = true)
public class UploadStudentDto {

    @UploadExcelTitle(value = "学号", require = true)
    private String sn;

    @UploadExcelTitle(value = "姓名", require = true)
    private String name;

}
