package com.xdbigdata.user_manage_admin.model.dto.manager;

import com.xdbigdata.framework.excel.annotation.UploadExcelTitle;
import com.xdbigdata.framework.excel.annotation.UploadVerify;

import lombok.Data;

@Data
@UploadVerify(verifyTitleLength = true, requireRowValue = true, requireCellValue = true)
public class BatchGrantTeacherDto {

	@UploadExcelTitle(value = "职工号", require = true)
	private String sn;
	
	@UploadExcelTitle(value = "姓名", require = true)
	private String name;
}
