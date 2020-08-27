package com.xdbigdata.user_manage_admin.model.vo.operatelog;

import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.operatelog.OperateLogDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
public class ListOperateLogVo extends BasePageVo<OperateLogDto> {

	private static final long serialVersionUID = 8120978597745613634L;

	public ListOperateLogVo(Page page, List<OperateLogDto> operateLogDtos) {
		super(page.getPageNum(), page.getPageSize(), page.getTotal(), operateLogDtos);
	}

	public ListOperateLogVo(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

}