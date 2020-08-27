package com.xdbigdata.user_manage_admin.service;

import com.xdbigdata.framework.web.model.PageQuery;
import com.xdbigdata.user_manage_admin.model.dto.student.StudentModifyDto;
import com.xdbigdata.user_manage_admin.model.vo.StudentInfoVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyDetailVo;
import com.xdbigdata.user_manage_admin.model.vo.SubmitModifyVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StudentManageService {
    /**
     * 根据sn获取当前学生信息
     */
    StudentInfoVo getStudentInfo(String sn);

    Map getStudentAllInfo(String sn) throws Exception;

    /**
     * 提交学生修改信息
     *
     * @param studentModifyDto
     */
    void commitUpdateInfo(StudentModifyDto studentModifyDto);

    /**
     * 获取提交历史列表
     *
     * @return
     */
    List<SubmitModifyVo> getCommitHistoryList(PageQuery query);

    /**
     * 根据id查询提交修改详情
     *
     * @param id 提交记录id
     * @return 修改详情
     */
    SubmitModifyDetailVo getSubmitModifyDetail(Long id);

    /**
     * 修改学生生活照
     *
     * @param file
     * @param sn
     */
    String updateLifeHead(MultipartFile file, String sn);
}
