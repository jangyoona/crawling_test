<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../layout/top.jsp"%>
<%@include file="../layout/header.jsp"%>
<%@include file="../layout/sidebar.jsp"%>


<div id="path">
	    <div class="inner">
	        <div class="left">
	            <h2 class="pTit"></h2>
	        </div>
	        <div class="right menuDepthTxt"><span></span></div>
	    </div>
	</div>

	<div id="main">
		<form id="form" name="form" method="POST">
			<input type="hidden" name="pageNo" id="pageNo" value="${pageNo}"/>
			<input type="hidden" name="isModify" id="isModify"/>

		    <div class="group">
		        <div class="body">
					<button type="button" class="btn green leftBtn listBtn" onclick="fn_add();"><i class="fas fa-user-plus"></i> 계정추가</button>
		            <div class="board_list_top">
		                <div class="board_list_info" style="float: left; position: relative; top: 7px;">
							<!--Search. <span id="schCount">${listCntInfo.sch_count}</span> / Total. <span id="totalCount">${listCntInfo.total_cnt}</span>--> 
							Search. <span id="schCount">카운트</span> / Total. <span id="totalCount">${not empty adminUserList ? adminUserList.getTotalElements() : 0}</span> 
		                </div>
		            </div>
		            <table class="board_list_normal">
		                <colgroup>
		                    <col width="5%">
		                    <col width="10%">
		                    <col width="10%">
		                    <col width="10%">
		                    <col width="10%">
		                </colgroup>
		                <thead>
		                    <tr>
								<th>번호</th>
								<th>제목</th>
								<th>등록자</th>
								<th>등록일</th>
								<th>조회수</th>
		                    </tr>
		                </thead>
		                <tbody>
							<c:if test="${not empty adminUserList}">
							    <c:forEach items="${adminUserList.content}" var="list" varStatus="status">
							        <tr onclick="fn_edit(${list.id})" style="cursor: pointer;">
							            <td class="num">${list.id}</td>
							            <td class="center">${list.userId}</td>
							            <td class="center">${list.userName}</td>
							            <td class="center">${list.userRole}</td>
							            <td class="center">${list.lastLogin}</td>
							        </tr>
							    </c:forEach>
							</c:if>

							<c:if test="${empty adminUserList}">
							    <tr>
							        <td class="none" colspan="6">등록된 관리자가 없습니다.</td>
							    </tr>
							</c:if>
		                </tbody>
		            </table>
		            <div class="pagination">
	                	<c:if test="${paging.prev}"><span><a onclick="javascript:listThread(${paging.startPage-1});">이전</a></span></c:if>
	                	<c:forEach begin="${paging.startPage}" end="${paging.endPage}" var="num">
	                		<span <c:if test="${pageNo eq num}">class="ON"</c:if>><a onclick="javascript:listThread(${num});">${num}</a></span>
	                	</c:forEach>
	                	<c:if test="${paging.next}"><span><a onclick="javascript:listThread(${paging.endPage+1});">다음</a></span></c:if>
	            	</div>
					
		        </div>
		    </div>
		</form>
	</div>