package com.xdbigdata.user_manage_admin.model.vo.role;

import java.util.List;

import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.role.ListRoleUserDto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ListRoleUserPageVo extends BasePageVo<ListRoleUserDto> {

	private static final long serialVersionUID = 8120978597745613634L;

	public ListRoleUserPageVo(Page page, List<ListRoleUserDto> roleUserDtos) {
		super(page.getPageNum(), page.getPageSize(), page.getTotal(), roleUserDtos);
	}

	public ListRoleUserPageVo(int pageNo, int pageSize, int totalCount) {
		super(pageNo, pageSize, totalCount);
	}

}