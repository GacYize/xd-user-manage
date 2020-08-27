package com.xdbigdata.user_manage_admin.mapper;

import com.xdbigdata.framework.mybatis.mapper.TKMapper;
import com.xdbigdata.user_manage_admin.model.StudentModel;
import com.xdbigdata.user_manage_admin.model.dto.student.ClazzStudentDto;
import com.xdbigdata.user_manage_admin.model.dto.student.StuSimInfDto;
import com.xdbigdata.user_manage_admin.model.qo.XjydQo;
import com.xdbigdata.user_manage_admin.model.qo.student.ListClazzStudentQo;
import com.xdbigdata.user_manage_admin.model.qo.student.StudentInfoListQo;
import com.xdbigdata.user_manage_admin.model.qo.updatecityQO;
import com.xdbigdata.user_manage_admin.model.vo.*;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentInfoExportVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentInfoListVo;
import com.xdbigdata.user_manage_admin.model.vo.student.StudentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper extends TKMapper<StudentModel> {

    StudentModel selectByUserId(@Param("userId") Long userId);

    /**
     * 根据班级查询学生信息
     *
     * @param listClazzStudentQo
     * @return
     */
    List<ClazzStudentDto> selectStudentsByClazz(ListClazzStudentQo listClazzStudentQo);

    /**
     * 根据userId获取学生详细信息
     *
     * @param userId
     * @return
     */
    StudentVo findByUserId(@Param("userId") Long userId);

    /**
     * 修改学生年级
     *
     * @param userId
     * @param grade
     */
    void updateStudentGrade(@Param("userId") Long userId, @Param("grade") String grade);

    /**
     * 根据条件查询学生列表
     *
     * @param studentInfoListQo 查询条件
     * @return 学生列表
     */
    List<StudentInfoListVo> selectByQuery(StudentInfoListQo studentInfoListQo);

    /**
     * 根据条件查询学生列表-新
     *
     * @param studentInfoListQo 查询条件
     * @return 学生列表
     */
    List<CustomSelectionAllVo> selectByQueryNew(@Param("studentInfoListQo") StudentInfoListQo studentInfoListQo, @Param("showSql") String showSql);

    /**
     * 全部查询导出
     *
     * @param studentInfoListQo
     * @return
     */
    List<CustomSelectionAllDownVo> selectByQueryNewExcel(@Param("studentInfoListQo") StudentInfoListQo studentInfoListQo);

    /**
     * 根据条件查询学生列表(导出)
     *
     * @param studentInfoListQo 查询条件
     * @return 学生列表
     */
    List<StudentInfoExportVo> exportByQuery(StudentInfoListQo studentInfoListQo);

    /**
     * 根据学号查询班级id
     *
     * @param sn 学号
     * @return 班级id
     */
    Long selectClassId(String sn);

    /**
     * 更新学生状态
     *
     * @param id
     * @param studentStatus
     */
    void updateStudentStatus(@Param("id") Long id, @Param("status") Integer studentStatus);

    /**
     * 根据学号查询学生信息
     *
     * @param sn 学号
     * @return 学生信息
     */
    StudentModel selectBySn(String sn);

    /**
     * 查询学生照片
     *
     * @param studentInfoListQo
     * @return
     */
    List<StudentPhoto> selectPhoto(StudentInfoListQo studentInfoListQo);

    String selectnstructorBySn(@Param("userId") Long userId);

    /**
     * 根据名称查询
     */
    List<CityVO> selectCityByName(QuzzyQueryVO quzzyQueryVO);

    /**
     * 跟新站点信息
     */
    void updateCityBySn(updatecityQO updatecityQO);

    /**
     * 修改站点信息
     */
    void updateDrivingRange(updatecityQO updatecityQO);

    /**
     * 根据学号查询学籍异动学生
     */
    List<XjydVO> queryXJYDList(XjydQo xjydQo);

    /**
     * 根据学院代码和年级查询学生
     *
     * @param collegeCode
     * @param grade
     * @return
     */
    List<StuSimInfDto> selectSimInfByColAndGrade(@Param("collegeCode") String collegeCode, @Param("grade") String grade,
                                                 @Param("sn") String sn, @Param("stuName") String stuName,
                                                 @Param("majorCode") String majorCode,@Param("classCode") String classCode);
}