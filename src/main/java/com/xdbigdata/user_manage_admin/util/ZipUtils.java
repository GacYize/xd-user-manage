package com.xdbigdata.user_manage_admin.util;

import com.xdbigdata.framework.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

@Slf4j
public class ZipUtils {


	/**
	 * 从输入流写文件到输出流
	 * 
	 * @param is 输入流
	 * @param os 输出流
	 * @throws IOException
	 */
	public static void writeFile(InputStream is, OutputStream os) throws IOException {
		byte[] b = new byte[2048];
		int length;
		while ((length = is.read(b)) > 0) {
			os.write(b, 0, length);
		}
	}

	/**
	 * 把一个目录打包到zip文件中的某目录
	 * @param dirpath   目录绝对地址
	 * @param pathName  zip中目录
	 */
	public static void packToolFiles(String dirpath, String pathName, OutputStream os) {
		if (StringUtils.isNotEmpty(pathName)) {
			pathName = pathName + File.separator;
		}
		try (
				ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(os);
		) {
			packToolFiles(zaos, dirpath, pathName);
		} catch (Exception e) {
			log.error("压缩文件夹出错", e);
			throw new BaseException("压缩文件夹出错");
		}
	}

	/**
	 * 把一个目录打包到一个指定的zip文件中
	 * @param dirpath           目录绝对地址
	 * @param pathName       zip文件抽象地址
	 */
	public static void packToolFiles(ZipArchiveOutputStream zaos, String dirpath, String pathName) throws IOException {
		File dir = new File(dirpath);
		//返回此绝对路径下的文件
		File[] files = dir.listFiles();
		if (files == null || files.length < 1) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			//判断此文件是否是一个文件夹
			if (files[i].isDirectory()) {
				packToolFiles(zaos, files[i].getAbsolutePath(), pathName + files[i].getName() + File.separator);
			} else {
				zaos.putArchiveEntry(new ZipArchiveEntry(pathName + files[i].getName()));
				IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()), zaos);
				zaos.closeArchiveEntry();
			}
		}
	}

	/**
	 * linux下删除文件夹
	 *
	 * @param dir 需要删除的目录
	 */
    public static void deleteDir(String dir)  {
		String cmd = "rm -rf " + dir;
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (Exception e) {
			log.error("删除文件夹{}失败", dir);
		}
    }
}
