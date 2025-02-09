<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!--   
  SBiz version 1.0

  Copyright ⓒ 2022 kt corp. All rights reserved.

  This is a proprietary software of kt corp, and you may not use this file except in
  compliance with license agreement with kt corp. Any redistribution or use of this
  software, with or without modification shall be strictly prohibited without prior written
  approval of kt corp, and the copyright notice above does not evidence any actual or
  intended publication of such software. 
-->

<!DOCTYPE html>
<html lang="en">
<head>
      <meta charset="utf-8">
      <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
      <meta name="title" content="대박가게 관리자">
      <meta http-equiv="X-UA-Compatible" content="IE=edge">
      <title><c:if test="${!empty pageName}">${pageName} | </c:if>대박가게 관리자</title>
      
      <script src="/resources/js/jquery-2.1.1.min.js"></script>
      <script src="/resources/js/jquery-ui.min.js"></script>
      <script src="/resources/js/spin.js"></script>
      <script src="/resources/js/smart_editor/js/service/HuskyEZCreator.js"></script>
      <script src="/resources/admin/js/bootstrap-datetimepicker.min.js"></script>
      <script src="/resources/js/jquery.form.min.js"></script>
      <script src="/resources/js/common.js"></script>
      <script src="/resources/admin/js/common.js"></script>
      <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
      
      <link rel="stylesheet" href="/resources/css/jquery-ui.min.css" />
      <link rel="stylesheet" href="/resources/css/datepicker.min.css" />
      <link rel="stylesheet" href="/resources/admin/css/font-awesome.min.css" />
      <link rel="stylesheet" href="/resources/admin/css/bootstrap-datetimepicker.css">
      <link rel="stylesheet" href="/resources/admin/css/common.css" />
      <link rel="stylesheet" href="/resources/admin/css/layout.css" />
      <link rel="stylesheet" href="/resources/admin/css/content.css" />
      <link rel="stylesheet" href="/resources/admin/css/newStyle.css">
      <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
      
</head>
<script>
  	$(function(){
 		opartsBOS.init();
 	});
</script>
<body>
<div class="process-bar"></div>
<div id="wrap">
