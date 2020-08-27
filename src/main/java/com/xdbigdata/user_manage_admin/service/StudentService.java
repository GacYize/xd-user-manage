package com.xdbigdata.user_manage_admin.service;


import com.github.pagehelper.PageInfo;
import com.xdbigdata.framework.common.model.PageResult;
import com.xdbigdata.user_manage_admin.model.UserModel;
import com.xdbigdata.user_manage_admin.model.qo.XjydQo;
import com.xdbigdata.user_manage_admin.model.qo.student.*;
import com.xdbigdata.user_manage_admin.model.qo.updatecityQO;
import com.xdbigdata.user_manage_admin.model.vo.CityVO;
import com.xdbigdata.user_manage_admin.model.vo.CustomSelectionResultVo;
import com.xdbigdata.user_manage_admin.model.vo.QuzzyQueryVO;
import com.xdbigdata.user_manage_admin.model.vo.XjydVO;
import com.xdbigdata.user_manage_admin.model.vo.student.ListClazzStudentVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentInfoListVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StudentService {

    /**
     * 根据班级id查询学生信息
     *
     * @param listClazzStudentQo
     * @return
     */
    ListClazzStudentVo findByClassId(ListClazzStudentQo listClazzStudentQo);

    /**
     * 根据id获取学生详细信息
     *
     * @param id
     * @return
     */
    StudentVo findById(Long id);

    /**
     * 选中班级下新增学生
     *
     * @param addAndUpdateStudentQo
     */
    void addOrUpdate(AddAndUpdateStudentQo addAndUpdateStudentQo);

    /**
     * 读取批量上传学生Excel内容
     *
     * @param file
     */
    List<String> uploadStudentExcel(MultipartFile file, Long classId) throws Exception;

    /**
     * 移动学生
     *
     * @param moveClazzStudentQo
     * @return
     */
    String moveClazzStudent(MoveClazzStudentQo moveClazzStudentQo);

    /**
     * 删除学生
     *
     * @param studentId 学生id
     * @return
     */
    String removeClazzStudent(Long studentId);

    /**
     * 根据学号查询只有学生角色的用户
     *
     * @param sn
     * @return
     */
    UserModel findBySnAndOnlyStudentRole(String sn);

    /**
     * 获取学生信息列表
     *
     * @param studentInfoListQo 学生列表查询对象
     * @return
     */
    PageResult<StudentInfoListVo> getStudentInfoList(StudentInfoListQo studentInfoListQo);

    /**
     * 获取学生信息列表-新
     *
     * @param studentQo 学生列表查询对象
     * @return
     */
    CustomSelectionResultVo getStudentInfoListNew(StudentInfoListQo studentQo);

    /**
     * 根据查询条件获取学生列表导出数据
     *
     * @param studentInfoListQo
     * @return
     */
    String export(StudentInfoListQo studentInfoListQo);

    String exportStudent(StudentInfoListQo studentInfoListQo);

    /**
     * 批量下载相片
     *
     * @param studentInfoListQo
     * @param type              照片类型1入学照,2生活照
     */
    void downloadPhotos(StudentInfoListQo studentInfoListQo, Integer type, HttpServletResponse response) throws Exception;

    /**
     * 根据名称查询
     */
    List<CityVO> selectCityByName(QuzzyQueryVO quzzyQueryVO);

    /**
     * 跟新站点信息
     */
    void updateCityBySn(updatecityQO updatecityQO);


    /**
     * 根据学号查询学籍异动学生
     */
    PageResult<XjydVO> queryXJYDList(XjydQo xjydQo);

    /**
     * 查询简单学生数据给咨询管理
     * 2019年11月28日13:55:24
     * author：666
     *
     * @return
     */
    PageInfo findSimpStuInf2TalkManage(TKQuery tkQuery);
}