package com.xdbigdata.user_manage_admin.task;

import com.xdbigdata.user_manage_admin.mapper.GradeMapper;
import com.xdbigdata.user_manage_admin.model.GradeModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;

@Slf4j
@Component
public class GradeTask {

    @Autowired
    private GradeMapper gradeMapper;

    /**
     * 每年8月1日定时添加当前年级
     */
    @Async
    @Scheduled(cron = "0 5 0 1 8 ?")
    public void addGrade() {
        GradeModel grade = new GradeModel();
        Year year = Year.now();
        grade.setName(year.toString());
        List<GradeModel> dbGrades = gradeMapper.select(grade);
        if (CollectionUtils.isNotEmpty(dbGrades)) {
            log.warn("{} 级已经存在数据库中.", year);
            return;
        }
        grade.setValid(1);
        gradeMapper.insertSelective(grade);
    }
}
