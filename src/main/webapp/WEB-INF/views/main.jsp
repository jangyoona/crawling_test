<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="./layout/top.jsp"%>
<%@include file="./layout/header.jsp"%>
<%@include file="./layout/sidebar.jsp"%>

<div id="main">
	<form id="form" name="form" method="POST">
		<input type="hidden" name="noti_no" id="noti_no"/>
		<input type="hidden" name="inq_no" id="inq_no"/>
		<div class="group">
			
				<div class="header">
					<h3>통계</h3>
				</div>
				
				<div class="chartArea" style="width: 49%; float: left;">
					<div class="body donutArea col2" id="donutArea">
						<div style="text-align: center;">	
							<h3>맞춤컨설팅 창업 희망 업종</h3>
							<canvas id="myChartSec" style="display: inline-block; width: 350px; height: 350px"></canvas>
						</div>
					</div>
				</div>
				
				<div class="chartArea" style="width: 49%; float: right;">
					<div class="body donutArea col2" id="donutArea2">
						<div style="text-align: center;">
							<h3>맞춤컨설팅 주요 타켓 연령대</h3>
							<canvas id="myChartAge" style="display: inline-block; width: 350px; height: 350px"></canvas>
						</div>
					</div>
				</div>	
			
		</div>
		
		<div class="group">
		
			<div class="chartArea" style="width: 49%; float: left;">
				<div class="header">
					<h3>공지사항</h3>
				</div>
		        <div class="body">
		            <table class="board_list_normal">
		                <colgroup>
		                    <col width="10%">
		                    <col width="55%">
		                    <col width="15%">
		                    <col width="20%">
		                </colgroup>
		                <thead>
		                    <tr>
		                        <th>No</th>
		                        <th>제목</th>
		                        <th>등록자</th>
		                        <th>등록일자</th>
		                    </tr>
		                </thead>
		                <tbody>
		                	<c:if test="${not empty noticeList}">
		                		<c:forEach items="${noticeList}" var="list" varStatus="status">
		                			<tr>
				                        <td class="num">${list.rownum}</td>
				                        <td class="center">${list.noti_title}</td>
				                        <td class="center">${list.rgr_eno}</td>
				                        <td class="center">${list.rgr_dtm}</td>
				                    </tr>
		                		</c:forEach>
		                	</c:if>
		                	<c:if test="${empty noticeList}">
		                		<tr>
		                        	<td class="none" colspan="4">등록된 리스트가 없습니다.</td>
		                   		</tr>
		                	</c:if>
		                </tbody>
		            </table>
		        </div>
			</div>
			
			<div class="chartArea" style="width: 49%; float: right;">
				<div class="header">
					<h3>1:1 문의사항</h3>
				</div>
				<div class="body">
		            <table class="board_list_normal">
		                <colgroup>
		                    <col width="10%">
		                    <col width="15%">
		                    <col width="35%">
		                    <col width="10%">
		                    <col width="20%">
		                    <col width="10%">
		                </colgroup>
		                <thead>
		                    <tr>
		                        <th>No</th>
		                        <th>카테고리</th>
		                        <th>제목</th>
		                        <th>등록자</th>
		                        <th>등록일자</th>
		                        <th>답변여부</th>
		                    </tr>
		                </thead>
		                <tbody>
		                	<c:if test="${not empty inquiryList}">
		                		<c:forEach items="${inquiryList}" var="list" varStatus="status">
		                			<tr>
				                        <td class="num">${list.rownum}</td>
				                        <td class="center">${list.inq_category_nm}</td>
				                        <td class="center">${list.inq_title}</td>
				                        <td class="center">${list.rgr_eno}</td>
				                        <td class="center">${list.rgr_dtm}</td>
				                        <td class="center">${list.inq_anw_yn}</td>
				                    </tr>
		                		</c:forEach>
		                	</c:if>
		                	<c:if test="${empty inquiryList}">
		                		<tr>
		                        	<td class="none" colspan="6">등록된 리스트가 없습니다.</td>
		                   		</tr>
		                	</c:if>
		                </tbody>
		            </table>
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
	$("#pageNo").val(pageNo);
	sbmit("form", "/admin/notice/noticeList");
}

function fn_detail(isModify, noti_no){
	$("#isModify").val(isModify);
	if(isModify == 'Y') $("#noti_no").val(noti_no);
	sbmit("form", "/admin/notice/noticeDetail");
}
</script>

