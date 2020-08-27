package com.xdbigdata.user_manage_admin.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 通用常量
 *
 * @author caijiang
 * @create 2018-10-25 10:30
 */
public interface CommonConstant {

	String TOKEN = "token";

	String USER_IN_SESSION = "sessionUser";
	/**
	 * 数字0
	 */
	int ZERO = 0;

	/**
	 * 数字1
	 */
	int ONE = 1;

	/**
	 * 数字2
	 */
	int TWO = 2;

	/**
	 * 学生角色name
	 */
	String STUDENT_ROLE = "学生";

	/**
	 * 学生角色id
	 */
	Long STUDENT_ROLE_ID = 1L;
	/**
	 * 保存文件的路径
	 */
	String FILEPATH = "./file";

	Long GENDER_MALE = 1L;

	/**
	 * 上传头像允许的后缀名
	 */
	List<String> HEAD_ALLOW_SUFFIXES = Arrays.asList("PNG", "JPG", "JPEG");

}
