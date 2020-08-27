package com.xdbigdata.user_manage_admin.model.vo.student;

import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.student.ManagerStudentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class ListManagerStudentVo extends BasePageVo<ManagerStudentDto> {



    public ListManagerStudentVo(Page page, List<ManagerStudentDto> managerStudentDtos){
        super(page.getPageNum(),page.getPageSize(),page.getTotal(), managerStudentDtos);
    }

    public ListManagerStudentVo(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

}