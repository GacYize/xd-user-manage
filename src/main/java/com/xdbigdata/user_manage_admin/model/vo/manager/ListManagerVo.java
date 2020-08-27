package com.xdbigdata.user_manage_admin.model.vo.manager;
import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class ListManagerVo extends BasePageVo<ManagerDto> {


    private static final long serialVersionUID = 3884726548891910881L;

    public ListManagerVo(Page page, List<ManagerDto> managerDtos){
        super(page.getPageNum(),page.getPageSize(),page.getTotal(),managerDtos);
    }

    public ListManagerVo(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

}