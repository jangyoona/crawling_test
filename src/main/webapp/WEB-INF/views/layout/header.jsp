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
::-webkit-scrollbar-thumb {
  background: rgba(0,0,0,.2);
  border: 0px none #fff;
}
</style>

<header id="header">
    <div class="inner">
        <div class="left">
        </div>
        <div class="center"></div>
        <div class="right util">
            <ul>
            	<li><b>${sessionScope.sessionNm}(${sessionScope.sessionId})</b> 님</li>
                <li><a href="javascript: modalFn.show('/admin/account/openModifyAccountInfoPopup');"><i class="fas fa-user"></i> <span>개인정보 수정</span></a></li>
                <li><a href="/admin/login"><i class="fas fa-sign-out-alt"></i> <span>로그아웃</span></a></li>
            </ul>
        </div>
    </div>
</header>

<script>
	function fn_modifyMyInfo(accountCd){
		registAuthYn = true;
		// validation check START
		if(!fn_chkValidation(".modal")) return;
		// validation check End
		
		if(accountCd == "03") {
			$(".modal [name=bsnsNum]").val($(".modal .bsnsNum").map(function(){ return this.value }).get().join("-"));
			// 이메일 정규식 체크
			var emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			var loopFlag = true;
			$(".modal .emailTd").each(function(){
				if(!loopFlag) return;
				if( !isEmpty($(this).find(".email1").val()) || !isEmpty($(this).find(".email2").val()) ) {
					$(this).find("[type=hidden]").val($(this).find(".ipt").map(function(){ return this.value }).get().join("@"));
					if(!emailRegExp.test($(this).find("[type=hidden]").val())) {
						alert("이메일 양식이 유효하지 않습니다.");
						$(this).find(".email1").focus();
						loopFlag = false;
						registAuthYn = false;
						return;
					}
				}
			})
		} else {
			// 이메일 정규식 체크
			if(!isEmpty($(".modal .email1").val()) || !isEmpty($(".modal .email2").val())) {
				var emailRegExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
				$(".modal #email").val($(".modal .email").map(function(){ return this.value }).get().join("@"));
				if(!emailRegExp.test($(".modal #email").val())) {
					alert("이메일 양식이 유효하지 않습니다.");
					$(".modal .email1").focus();
					registAuthYn = false;
					return;
				}
			}
		}
		
		if(confirm("저장하시겠습니까?")) {
			
			$("#modifyAccountForm").attr("action", "/admin/account/saveAccountInfoAjax");
			
			var option = {
				success : function(data) {
					alert(data.message);
					if( data.retcode == "SUCC") location.reload(); 
					else {
						console.log(data.errCode);
						registAuthYn = false;
					}
				},
				type : "POST"
			}
			
			$("#modifyAccountForm").ajaxSubmit(option);
			
		}
	}
	
	function fn_openChgPwdModal(){
		modalFn.show('/admin/account/openModifyPasswordPopup');	
	}
</script>