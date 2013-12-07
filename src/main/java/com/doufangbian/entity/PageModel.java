package com.doufangbian.entity;

import java.util.List;

/**
 * 
 * 分页的实体
 * 
 * @author Administrator
 * 
 * @param <T>
 */
public class PageModel<T> {
	// 定义属性
	private Integer size = 10; //每页显示条数

	private Integer sumCount; //总条数

	private Integer currentPage; //当前页

	private Integer sumPage;// 总页数

	private Integer first = 1; // 首页

	private Integer upper; // 上一页
	
	private Integer down; //下一页

	private Integer last; //尾页

	private List<T> data;

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getSumCount() {
		return sumCount;
	}

	public void setSumCount(Integer sumCount) {
		this.sumCount = sumCount;

		// 计算出总的页数
		//this.sumPage = (Integer) Math.ceil(this.sumCount / (float) this.size);、
		
		this.sumPage = (this.sumCount-1)/this.size+1;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;

		// 计算出上一页，下一页，尾页
		this.upper = this.currentPage - 1 <= 1 ? 1 : this.currentPage - 1;
		this.down = this.currentPage + 1 >= sumPage ? sumPage
				: this.currentPage + 1;
		this.last = this.sumPage;
	}

	public Integer getSumPage() {
		return sumPage;
	}

	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Integer getUpper() {
		return upper;
	}


	public Integer getDown() {
		return down;
	}


	public Integer getLast() {
		return last;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}


}
