package com.plugs.base;

import com.github.miemiedev.mybatis.paginator.domain.Paginator;

/**
 * Created by Zhouhy on 2016/4/18.
 */
public class PageBean {
	/**
	 * 当前页
	 */
	private int pageNum = 1;
	/**
	 * 页面大小
	 */
	private int pageSize = 10;
	/**
	 * 总数
	 */
	private long totalCount;
	/**
	 * 总页数
	 */
	private long totalPages = 1;

	private long startRow;
	private long endRow;
	/**
	 * 记录操作的次数  每次加1
	 */
	private int sEcho = 1;

	public PageBean() {
	}

	public PageBean(Integer pageNum, Integer pageSize) {
		if (pageNum != null && pageNum.intValue() > 0) {
			this.pageNum = pageNum;
		}

		if (pageSize != null && pageSize.intValue() > 0) {
			this.pageSize = pageSize;
		}
	}

	public PageBean(int pageNum, int pageSize,int sEcho ) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.sEcho = sEcho;
	}
	public PageBean(int pageNum, int pageSize, long totalCount,
					long totalPages, long startRow, long endRow) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.totalPages = totalPages;
		this.startRow = startRow;
		this.endRow = endRow;
	}

	public int getPrePage() {
		return this.isHasPrePage() ? this.pageNum - 1 : this.pageNum;
	}

	public boolean isHasPrePage() {
		return this.pageNum - 1 >= 1;
	}

	public int getNextPage() {
		return this.isHasNextPage() ? this.pageNum + 1 : this.pageNum;
	}

	public boolean isHasNextPage() {
		return this.pageNum + 1 <= this.getTotalPages();
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getStartRow() {
		return startRow;
	}

	public void setStartRow(long startRow) {
		this.startRow = startRow;
	}

	public long getEndRow() {
		return endRow;
	}

	public void setEndRow(long endRow) {
		this.endRow = endRow;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public void setInitData(Paginator paginator) {
		if (paginator != null) {
			setTotalCount(paginator.getTotalCount());
			setTotalPages(paginator.getTotalPages());
			setStartRow(paginator.getStartRow());
			setEndRow(paginator.getEndRow());
		}
	}
}
