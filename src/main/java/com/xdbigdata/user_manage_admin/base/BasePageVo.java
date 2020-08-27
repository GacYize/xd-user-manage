package com.xdbigdata.user_manage_admin.base;


import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回基础类
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BasePageVo<T> implements Serializable {

	private static final long serialVersionUID = 644335821814470081L;

	public static final int DEF_COUNT = 20;


	protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;
	protected int startIndex = 0;
	protected int totalPage = 0;
	protected List<T> pageData;

	public static int checkPageNo(Integer pageNo) {
		return (pageNo == null || pageNo < 1) ? 1 : pageNo;
	}

	public BasePageVo() {

	}

	public BasePageVo(Builder builder) {
		setTotalCount(builder.totalCount);
		setPageSize(builder.pageSize);
		setPageNo(builder.pageNo);
		adjustPageNo();
		setPageData(builder.pageData);
	}

	public  static class Builder{
		private int pageNo;
		private int pageSize;
		private int totalCount;
		private List pageData;

		public Builder setPageNo(int pageNo) {
			this.pageNo = pageNo;
			return  this;
		}

		public Builder setPageSize(int pageSize) {
			this.pageSize = pageSize;
			return  this;
		}

		public Builder setTotalCount(int totalCount) {
			this.totalCount = totalCount;
			return  this;
		}

		public Builder setPageData(List pageData) {
			this.pageData = pageData;
			return  this;
		}

		public BasePageVo build(){
			return new BasePageVo(this);
		}
	}



	public BasePageVo(int pageNo, int pageSize, int totalCount) {
		this();
		setTotalCount(totalCount);
		setPageSize(pageSize);
		setPageNo(pageNo);
		adjustPageNo();
	}

	public BasePageVo(int pageNo, int pageSize, int totalCount, List<T> pageData) {
		this();
		setPageSize(pageSize);
		setTotalCount(totalCount);
		setPageNo(pageNo);
		adjustPageNo();
		setPageData(pageData);
	}




	public BasePageVo(int pageNo, int pageSize, long totalCount, List<T> pageData) {
		this(pageNo, pageSize, new Long(totalCount).intValue(), pageData);
	}

	public List<T> getPageData() {
		if (CollectionUtils.isEmpty(pageData)) {
			Lists.newArrayList();
		}
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}

	/**
	 * 调整页码，使其不超过最大页数
	 */
	public void adjustPageNo() {
		if (pageNo == 1) {
			return;
		}
		int totalPage = getTotalPage();
		if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * 是否第一页
	 *
	 * @return
	 */
	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	/**
	 * 是否最后一页
	 *
	 * @return
	 */
	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	/**
	 * 下一页页码
	 *
	 * @return
	 */
	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPretPage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	public void setTotalCount(int totalCount) {

		//设置总条数
		if (totalCount < 0) {
			this.totalCount = 0;
		} else {
			this.totalCount = totalCount;
		}

		//设置总页数
		int totalPage = totalCount / pageSize;
		if (totalPage == 0 || totalCount % pageSize != 0) {
			totalPage++;
		}
		this.totalPage = totalPage;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize < 1) {
			this.pageSize = DEF_COUNT;
		} else {
			this.pageSize = pageSize;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}

	public void setPageNo(Integer pageNo) {
		if (pageNo == null || pageNo < 1) {
			this.pageNo = 1;
		} else {
			this.pageNo = pageNo;
		}
		startIndex = this.pageSize * (this.pageNo - 1);
	}
}