package com.jsp.request;

public class SearchCriteria extends Criteria{
	
	private String searchType=""; //검색 구분
	private String Keyword=""; //검색어
	
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return Keyword;
	}
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	
	
	
	
}
