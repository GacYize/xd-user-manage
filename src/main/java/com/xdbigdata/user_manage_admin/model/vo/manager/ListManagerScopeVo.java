package com.xdbigdata.user_manage_admin.model.vo.manager;
import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.manager.ManagerScopeDetailDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ListManagerScopeVo extends BasePageVo<ManagerScopeDetailDto> {


    private static final long serialVersionUID = 3884726548891910881L;

    public ListManagerScopeVo(Page page, List<ManagerScopeDetailDto> managerDtos){
        super(page.getPageNum(),page.getPageSize(),page.getTotal(),managerDtos);
    }

    public ListManagerScopeVo(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

}