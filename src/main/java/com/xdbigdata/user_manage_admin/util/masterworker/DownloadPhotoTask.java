package com.xdbigdata.user_manage_admin.util.masterworker;

import com.xdbigdata.framework.fastdfs.FastDFSClient;
import com.xdbigdata.framework.fastdfs.constant.FastDFSConstant;
import com.xdbigdata.user_manage_admin.model.vo.StudentPhoto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Slf4j
public class DownloadPhotoTask extends Task<Boolean> {

    private String filePath;
    private File dir;

    public DownloadPhotoTask(StudentPhoto studentPhoto, File dir) {
        super(studentPhoto.getSn());
        this.filePath = studentPhoto.getFilePath();
        this.dir = dir;
    }

    @Override
    protected Boolean execute() {
        Map<String, String> descriptions = FastDFSClient.getFileDescriptions(filePath);
        if (MapUtils.isEmpty(descriptions)) {
            log.warn("学号为:{}的照片信息不存在.", super.getName());
            return false;
        }
        String fileName = descriptions.get(FastDFSConstant.FILE_DESCRIPTION_FILE_NAME);
        if (StringUtils.isBlank(fileName)) {
            log.warn("学号为:{}的照片信息错误.", super.getName());
            return false;
        }
        fileName = super.getName() + fileName.substring(fileName.lastIndexOf(FastDFSConstant.DOT));
        try {
            File file = new File(dir.getPath(), fileName);
            FileOutputStream fos = new FileOutputStream(file);
            FastDFSClient.write(filePath, fos);
        } catch (Exception e) {
            log.info("学号为:{}的照片下载失败.", super.getName());
            return false;
        }

        return true;
    }

}
