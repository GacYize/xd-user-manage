package com.xdbigdata.user_manage_admin.mapper.oracle;

import com.xdbigdata.user_manage_admin.model.domain.StudentScore;
import com.xdbigdata.user_manage_admin.model.oracle.StudentRemote;
import com.xdbigdata.user_manage_admin.model.qo.XjydQo;
import com.xdbigdata.user_manage_admin.model.vo.XjydVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentRemoteMapper {

    /**
     * 查询所有的学生
     *
     * @return
     */
    List<StudentRemote> selectAllStudent();

//    /**
//     * 查询所有的学生
//     *
//     * @return
//     */
//    List<StudentScore> selectAllStudentScore(@Param("sn") String sn);





}
