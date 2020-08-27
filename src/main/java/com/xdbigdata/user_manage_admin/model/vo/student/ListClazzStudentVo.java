package com.xdbigdata.user_manage_admin.model.vo.student;
import com.github.pagehelper.Page;
import com.xdbigdata.user_manage_admin.base.BasePageVo;
import com.xdbigdata.user_manage_admin.model.dto.student.ClazzStudentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class ListClazzStudentVo extends BasePageVo<ClazzStudentDto> {


    private static final long serialVersionUID = 4256056300160085822L;

    public ListClazzStudentVo(Page page, List<ClazzStudentDto> clazzStudentDtos){
        super(page.getPageNum(),page.getPageSize(),page.getTotal(), clazzStudentDtos);
    }

    public ListClazzStudentVo(int pageNo, int pageSize, int totalCount) {
        super(pageNo, pageSize, totalCount);
    }

}