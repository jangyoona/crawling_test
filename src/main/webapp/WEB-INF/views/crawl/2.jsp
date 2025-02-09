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
		            <div class="board_list_search">
		            	<table class="listSchTbl">
		            		<colgroup>
		            			<col width="8%">
		            			<col width="*"> 
		            		</colgroup>
				            <tr>
			            		<td>
					                <div class="ipt_group">
					                	<select class="ipt resetVal" id="schGb" name="schGb" style="width: calc(20% - 5px); margin-right: 5px; border: 1px solid #bababa; border-radius: 4px;"> <!-- onchange="fn_search();" -->
					                		<option value="all" <c:if test="${schGb eq 'all'}">selected</c:if>>-전체-</option>
					                		<option value="userRole" <c:if test="${schGb eq 'userRole'}">selected</c:if>>권한</option>
					                		<option value="userName" <c:if test="${schGb eq 'userName'}">selected</c:if>>성명</option>
					                		<option value="userId" <c:if test="${schGb eq 'userId'}">selected</c:if>>아이디</option>
					                	</select>
					                    <input type="text" name="keyword" class="ipt resetVal" style="width: 80%;" placeholder="검색어를 입력하세요" value="${schVal}" onkeypress="if( event.keyCode == 13 ){fn_search();}">
					                    <span class="ipt_right addon"><button type="button" class="btn searchBtn" onclick="fn_search();">검색</button></span>
				                		<span class="ipt_right addon"><button type="button" class="btn white resetBtn" onclick="fn_reset();">초기화</button></span>
					                </div>
				                </td>
			                </tr>
		                </table>
		            </div>
		        </div>
		    </div>
			
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
		                    <col width="10%">
		                    <col width="10%">
		                </colgroup>
		                <thead>
		                    <tr>
								<th>No</th>
								<th>아이디</th>
								<th>성명</th>
								<th>권한</th>
								<th>최종 로그인</th>
								<th>최종 수정일</th>
								<th>사용여부</th>
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
							            <td class="center">${list.modifyedAt}</td>
							            <td class="center">${list.active == 1 ? "활성화" : "비활성화"}</td>
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

<script>

function fn_search(){
	listThread(1);
}
function listThread(pageNo) {
	// $("#pageNo").val(pageNo);
	// sbmit("form", "/admin/notice/noticeList");
	// location.href= "/admin/user";
	
	// location.href= "/admin/user?pageNo=" + pageNo;
	$("#pageNo").val(pageNo);
	$('#form').attr('action', '/admin/user');
	$('#form').submit();
}

function fn_add(){
	location.href= "/admin/add";
}

function fn_detail(isModify, noti_no){
	$("#isModify").val(isModify);
	if(isModify == 'Y') $("#noti_no").val(noti_no);
	sbmit("form", "/admin/notice/noticeDetail");
}

function fn_edit(id) {
	location.href= "/admin/edit/" + id;
}

</script>

