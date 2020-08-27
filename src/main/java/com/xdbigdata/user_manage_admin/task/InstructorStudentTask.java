package com.xdbigdata.user_manage_admin.task;

import com.xdbigdata.user_manage_admin.mapper.StudentOrganizationLineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InstructorStudentTask {

    @Autowired
    private StudentOrganizationLineMapper studentOrganizationLineMapper;

    @Async
    @Transactional
    @Scheduled(cron = "0 30 1 * * ?")
    public void modifyInstructorStudent() {
        studentOrganizationLineMapper.deleteInstructorStudent();
        studentOrganizationLineMapper.updateInstructorStudent();
    }
}
