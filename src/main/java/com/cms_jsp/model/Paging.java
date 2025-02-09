package com.cms_jsp.model;

public class Paging {
	
	private int totalCount;
	private int pageNum = 10;
	private int startPage;
	private int endPage;
	
	private boolean prev;
	private boolean next;
	
	private Criteria criteria;
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		pagingData();
	}
	
	private void pagingData() {
		endPage = (int) (Math.ceil(criteria.getPageNo() / (double) pageNum) * pageNum);
		startPage = (endPage - pageNum) + 1;
		
		int tmepEndPage = (int)(Math.ceil(totalCount/(double) criteria.getPerPageNum()));
		if(endPage > tmepEndPage) endPage = tmepEndPage;
		
		prev = startPage == 1 ? false : true;
		next = endPage * criteria.getPerPageNum() >= totalCount ? false : true;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getEndPage() {
		return endPage;
	}
	
	public void setEndPage (int endPage) {
		this.endPage = endPage;
	}
	
	public int getStartPage() {
		return startPage;
	}
	
	public void setStartPage (int startPage) {
		this.startPage = startPage;
	}
	
	public boolean isPrev() {
		return prev;
	}
	
	public void  setPrev(boolean prev) {
		this.prev = prev;
	}
	
	public boolean isNext() {
		return next;
	}
	
	public void  setNext(boolean next) {
		this.next = next;
	}
	
	public Criteria getCriteria() {
		return criteria;
	}
	
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
}
