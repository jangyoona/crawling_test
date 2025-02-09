              <%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--   
  SBiz version 1.0

  Copyright ⓒ 2022 kt corp. All rights reserved.

  This is a proprietary software of kt corp, and you may not use this file except in
  compliance with license agreement with kt corp. Any redistribution or use of this
  software, with or without modification shall be strictly prohibited without prior written
  approval of kt corp, and the copyright notice above does not evidence any actual or
  intended publication of such software. 
-->

<style>
	#sidebar{
		background: #fff;
	  	overflow: auto;
	  		
	  	scrollbar-face-color: #e0e0e0;
		scrollbar-track-color: #fff;
		scrollbar-arrow-color: none;
		scrollbar-highlight-color: #e0e0e0;
		scrollbar-3dlight-color: none;
		scrollbar-shadow-color: #e0e0e0;
		scrollbar-darkshadow-color: none;
	}
	
	#sidebar::-webkit-scrollbar {
		width: 6px;
  		height: 6px;
	}
	#sidebar::-webkit-scrollbar-button:start:decrement, ::-webkit-scrollbar-button:end:increment {
		display: block;
		height: 5px;
		background: #fff;
	}
	#sidebar::-webkit-scrollbar-track {
		background: #fff;
		-webkit-border-radius: 10px;
		border-radius:10px;
	}
	#sidebar::-webkit-scrollbar-thumb {
		height: 50px;
		width: 50px;
		background: rgba(0,0,0,.2);
		-webkit-border-radius: 8px;
		border-radius: 8px;
	}
</style>


<input type="hidden" id="sessionAccountCd" value="${sessionScope.sessionAccountCd}">
<input type="hidden" name="nowMenuSeq" id="nowMenuSeq" value="${nowMenuSeq}" />
<input type="hidden" id="windowTargetHidField" value="${windowTarget}" />
<input type="hidden" id="sessionId" value="${sessionScope.sessionId}" />

<nav id="sidebar">
	<a href="/admin/main" class="logo">
        <img src="/resources/images/logo_c.png"/>
    </a>
	<div class="menu on" data-level="1">
		<h2>CATEGORIES</h2>
		<ul id="adminSideBar"></ul>
	</div>
</nav>

<script>

	var nowMenuSeq = "";
	var registAuthYn = false;
	
	$(function(){
		nowMenuSeq = $("#nowMenuSeq").val();
		//fn_getAuthMenuList();
	})
	
	function fn_clickMenu(t){
		if(!$(t).closest("li").hasClass("on")) {
			$(".ico_leftArrow").removeClass("on");
			$(".ico_leftArrow").find(".menu").slideUp(500);
		}
		$(t).closest("li").toggleClass("on");
		$(t).closest("li").find(".menu").slideToggle(500);
	}
	
	function fn_getAuthMenuList(){
		$.ajax({
			type : "POST",
			url : "/admin/selectAccountAuthMenuListAjax",
			traditional : true,
			dataType : 'json',
			data : {},
			success : function(data) {
				if( data.retcode == "SUCC") {
					var menuList = data.accountAuthMenuList;
					// 기능 중 다른 메뉴 링크 태웠을 경우 권한 체크 
					if(!isEmpty(nowMenuSeq) && menuList.findIndex(v => v.menu_seq === nowMenuSeq) == -1) {
						alert("접근 권한이 없습니다.");
						if($("#windowTargetHidField").val() == "blank") window.close();
						else location.href = '/admin/login';
					} 

					if(menuList != null) {
						$.each(menuList, function(idx, item){
							if(item.menu_depth == 1) { // 대메뉴
								if(item.depth2_child > 0) { // 소메뉴 있는 대메뉴
									var str = '<li class="ico_leftArrow">'
										    +  '<a href="javascript:void(0);" onclick="fn_clickMenu(this);">' + (!isEmpty(item.menu_icon) ? '<i class="' + item.menu_icon + '"></i> ' : '') + '<span>' + item.menu_nm + '</span></a>'
										    +  '<div class="menu">'
										    +  '<ul id="pMenu_' + item.menu_seq + '"></ul></div></li>';
								} else { // 소메뉴 없는 자체 URL 대메뉴
									var str = '<li data-menu_seq="' + item.menu_seq + '">';
									if(item.menu_gb == "01") str += '<a href="' + item.menu_url + '" class="depth1Link">';
									if(!isEmpty(item.menu_icon)) str += '<i class="' + item.menu_icon + '"></i> ';
									str += '<span>' + item.menu_nm + '</span>';
									if(item.menu_gb == "01") str += '</a>';
									str += '</li>';
								}
								$("#adminSideBar").append(str);
							} else { // 소메뉴
								var str = '<li data-menu_seq="' + item.menu_seq + '"><a href="' + item.menu_url + '" class="depth2Link"><span>' + item.menu_nm + '</span></a></li>';
								$("#pMenu_" + item.p_menu_seq).append(str);								
							}
						});
					}
					if(isEmpty(nowMenuSeq)) return;
					var $menuTag = $("[data-menu_seq=" + nowMenuSeq + "]");
					$menuTag.addClass("active");
					$menuTag.parents("ul").parents("li.ico_leftArrow").addClass("on");
					if($menuTag.find("a").hasClass("depth2Link")) $menuTag.parents("li.ico_leftArrow").find("div.menu").addClass("on");
					var nowMenuNm = $menuTag.find("a span").text();
					$(".pTit").text(nowMenuNm);
					$(".menuDepthTxt span").text("HOME > " + $(".ico_leftArrow.on > a span").text() + " > " + nowMenuNm)
					
					// 등록 수정 삭제 권한 체크
					var accountRegistAuthList = data.accountRegistAuthList;
					if(!isEmpty(accountRegistAuthList)) {
						accountRegistAuthList = accountRegistAuthList.split(",");
						if(accountRegistAuthList.indexOf(nowMenuSeq) > -1) registAuthYn = true;
					}
					fn_hideRegistArea();
				}
				else console.log(data.errCode);	
			},
			error : function(jqXHR, textStatus, errorThrown){
				if(isEmpty($("#sessionId").val())) {
					alert('로그인 후 이용해주세요.'); 
					location.href='/admin/login';
				} else {
		        	alert("문제가 발생하였습니다.\n고객센터에 문의하세요.\n"+ajaxJsonError(jqXHR, textStatus, errorThrown));
				}
		    }
		});	  
	}
	
</script>
