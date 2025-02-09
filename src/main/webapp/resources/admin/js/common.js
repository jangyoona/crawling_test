
/*
 * SBiz version 1.0
 *
 *  Copyright ⓒ 2022 kt corp. All rights reserved.
 *
 *  This is a proprietary software of kt corp, and you may not use this file except in
 *  compliance with license agreement with kt corp. Any redistribution or use of this
 *  software, with or without modification shall be strictly prohibited without prior written
 *  approval of kt corp, and the copyright notice above does not evidence any actual or
 *  intended publication of such software.
 */

var IS_MOBILE     = /mobile|android|bada|blackberry|blazer|ip(hone|od|ad)|windows (ce|phone)/i.test(navigator.userAgent||navigator.vendor||window.opera);
var WINDOW_HEIGHT   = window.innerHeight ? window.innerHeight : jQuery(window).height();
var SCROLL_TOP      = 0;
var SCROLL_TOP_PREV   = 0;
var SCROLL_DIRECTION  = "stop";

var TRANSFORM_TYPE    = "left";

var IS_IOS        = navigator.userAgent.toLowerCase().search(/ip(hone|ad|od)/i) > -1;
var IS_ANDROID      = navigator.userAgent.toLowerCase().indexOf("android")  > -1;
var IS_PHONEGAP     = false;
var IS_PHONEGAP_IOS   = false;
var IS_PHONEGAP_ANDROID = false;
var APP_VERSION_NUMBER  = 200;
var APP_MARKET_TYPE   = "appstore";

var LNB_FIRST_DEP,LNB_SECOND_DEP,LNB_THIRD_DEP;

//check browser
var isie=(/msie/i).test(navigator.userAgent); //ie
var isie6=(/msie 6/i).test(navigator.userAgent); //ie 6
var isie7=(/msie 7/i).test(navigator.userAgent); //ie 7
var isie8=(/msie 8/i).test(navigator.userAgent); //ie 8
var isie9=(/msie 9/i).test(navigator.userAgent); //ie 9
var isie10=(/msie 10/i).test(navigator.userAgent); //ie 9
var isfirefox=(/firefox/i).test(navigator.userAgent); //firefox
var isapple=(/applewebkit/i).test(navigator.userAgent); //safari,chrome
var isopera=(/opera/i).test(navigator.userAgent); //opera
var isios=(/(ipod|iphone|ipad)/i).test(navigator.userAgent);//ios
var isipad=(/(ipad)/i).test(navigator.userAgent);//ipad
var isandroid=(/android/i).test(navigator.userAgent);//android
var device;
var isieLw;
if(isie6 || isie7 || isie8){ isieLw=true;}

var pageNum , subNum, threeNum;

var mobileW = 750;


var opartsBOS = {
  site : {},
  init : function() {

    site = this;

//    site.sideMenu();

    $(window).resize(function() {
      //site.resize();
    }).resize();

    $(window).scroll(function () {
      site.scroll();
    }).scroll();

    $('.select_field').each(function() {
      selectBox($(this));
    });

  },
  resize : function() {
    site.wW = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
    site.wH = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight,
    site.shH = $('#header').outerHeight(),
    site.isMobile = false;

    if(site.wW <= mobileW) {
      site.isMobile = true;
    }

    $('#sidebar, #main').css({height:site.wH - site.shH});
  },
  scroll : function() {

  },
  /*sideMenu : function() {
    $("#sidebar .menu ul li > a").click(function(e) {
      if($(this).next().is('.menu')){
        e.preventDefault();
        $(this).next().addClass('on');
      }
    });

    $("#sidebar .menu .back").click(function(e) {
      e.preventDefault();
      $(this).parent().removeClass('on');
    });

    $("#header .menu_btn, #sidebar .menu_btn").click(function(e) {
      e.preventDefault();
      if($('body').hasClass('sideHide')){
        $('body').removeClass('sideHide');
      }else{
        $('body').addClass('sideHide');
      }
    });
  },*/
  pageRead : function(t,e) {
    e.preventDefault();
    if($("#pageRead").length === 0){
      $("#wrap").append($("<div></div>").prop({id : 'pageRead'}));
    }
    $("#pageRead").html('<iframe src="'+t+'" width="100%" height="'+$("#pageRead").height()+'px"></iframe>');
    /*$.ajax({
      url : t,
      type : "get",
      dataType : "html",
      success : function(data){
        $("#pageRead").html(data);
      },
      error : function(a,b,c){
          alert(b);
      }
    });*/
  },
  closeRead : function() {
    $('#pageRead', parent.document).remove();
  }
}

/* Images Load Checking */
$.fn.imagesLoaded = function (fn) {
    var $imgs = this.find('img[src!=""]'), imgArr = {cpl : [], err : []};
    if (!$imgs.length){
        if(fn) fn();
        return;
    }
    var dfds = [], cnt = 0;
    $imgs.each(function(){
        var _this = this;
        var dfd = $.Deferred();
        dfds.push(dfd);
        var img = new Image();
        img.onload = function(){
            imgArr.cpl.push(_this);
            check();
        }
        img.onerror = function(){
            imgArr.err.push(_this);
            check();
        }
        img.src = this.src;
    });
    function check(){
        cnt++;
        if(cnt === $imgs.length){
            if(fn) fn.call(imgArr);
        }
    }
}

function readFileURL(input){
    var isIE = (navigator.appName=="Microsoft Internet Explorer");
    var isie8= (/msie 8/i).test(navigator.userAgent);
    var isie9 = (/msie 9/i).test(navigator.userAgent);
    var path = input.value;
    var ext = path.substring(path.lastIndexOf('.') + 1).toLowerCase();
    var inputField = $(input).closest('.fileinputs').find('.input > input');
    var ImgField = $(input).closest('.uploadImgFile').find('.thumb');

    if(path == ""){
      $(input).val('');
      inputField.val('');
      ImgField.html('');
      return;
    }

    if($(input).closest('.fileinputs').is('.excel')){
      inputField.val(input.files[0].name);
      return;
    }

    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
      alert('gif, png, jpg, jpeg 파일만 가능합니다.');
      $(input).val('');
      inputField.val('');
      ImgField.html('');
      return;
    }

    var img = "",
        btnDel = '<button class="i-set close1" onclick="readFileDel(this);">삭제</button>'
    if($(input).closest('.uploadImgFlie').hasClass('noneDel')) btnDel = '';

    inputField.val(input.files[0].name);

    if(isie8 || isie9) {
      alert("ie9이하 버전은 미리보기 기능을 지원하지 않습니다.");
      img = btnDel;
      ImgField.html(img);
      //$(input).closest('.fakefile').find('.file').css('display', 'block');
    }else{
         if(input.files && input.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                img = '<div class="img"><img src="'+e.target.result+'"/></div>'+btnDel;
                ImgField.html(img);
            }
            reader.readAsDataURL(input.files[0]);
         }
    }
}

var randomNum = {
    random : function(n1, n2) {
        return parseInt(Math.random() * (n2 -n1 +1)) + n1;
    },
    autoNo : function(n) {
        var value = "";
        for(var i=0; i<n; i++){
            value += randomNum.random(0,9);
        }
        return value;
    }
};

function fn_random(){
	const chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz'
	const stringLength = 8;
	let randomstring = ''
	for (let i = 0; i < stringLength; i++) {
		const rnum = Math.floor(Math.random() * chars.length)
	    randomstring += chars.substring(rnum, rnum + 1)
	}
	return randomstring;
}

var imgUpload = {
  insertImg:  function(obj,dataItem) {
    var isIE = (navigator.appName=="Microsoft Internet Explorer");
    var isie8= (/msie 8/i).test(navigator.userAgent);
    var isie9 = (/msie 9/i).test(navigator.userAgent);
    var path = obj.value;
    var ext = path.substring(path.lastIndexOf('.') + 1).toLowerCase();

    if(path == ""){
      return;
    }

    if($.inArray(ext, ['gif','png','jpg','jpeg']) == -1) {
      alert('gif, png, jpg, jpeg 파일만 가능합니다.');
      return;
    }

    $(obj).closest('.uploadImgFile').find('.ajax_image_file').remove();
    $(obj).closest('.uploadImgFile').find('input.title').val(obj.files[0].name);

    if(isie8 || isie9) {
      alert("ie9이하 버전은 미리보기 기능을 지원하지 않습니다.");
      var img = '<div class="img ajax_image_file"></div>';
      $(obj).closest('.uploadImgFile').find('.thumb').prepend(img);
    }else{
         if(obj.files && obj.files[0]) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var img = '<div class="img ajax_image_file"><img src="'+e.target.result+'" /></div>';
                $(obj).closest('.uploadImgFile').find('.thumb').prepend(img);
            }
            reader.readAsDataURL(obj.files[0]);
         }
    }
  },
  deleteImg: function(obj) {
    $(obj).closest('.uploadImgFile').find('.ajax_image_file').remove('');
    $(obj).closest('.uploadImgFile').find('.hidden_img').val('');
    $(obj).closest('.uploadImgFile').find('input[type="file"]').val('');
    $(obj).closest('.uploadImgFile').find('.up_hidden_img').val('');
    $(obj).closest('.uploadImgFile').find('input.title').val('');
  }
}


$(document).ready(function() {
  $('.uploadImgFile .thumb').off().hover(
      function() {
          var h = $(this).outerHeight() - $(this).find('img').height();
          if(h < 0){
              $(this).find('img').stop().animate({
                  top: h
              }, 1000);
          }
      }, function() {
          $(this).find('img').stop().animate({
              top: 0
          }, 1000);
      }
  );
  $('.priceComma').change(function(e) {
    var val = $(this).val();
    $(this).val(comma(val));
  }).change();
  $(".commaIpt").on("keyup", function(){
		fn_comma(this);
	})
});


function selectBox(t) {
  _this = t;

  if(_this.find('.select_text').length > 0) return false;

  _this.append('<input type="text" class="select_text ipt" readonly />');
  _this.append('<ul class="select_list" tabindex="-1"></ul>');

  var selectText = _this.find('.select_text');
  var selectList = _this.find('.select_list');

  _this.find('option').each(function() {
    var selected = $(this).attr('selected') ? 'selected' : '';
    if($(this).val() == ''){
      selectText.attr('placeholder',$(this).text());
    }else{
      if($(this).attr('selected')) selectText.val($(this).text());
      if($(this).data('img') != undefined){
        selectList.append('<li class="'+selected+'" style="background-image:url('+$(this).data('img')+')"><span>'+$(this).text()+'</span></li>');
      }else{
        selectList.append('<li class="'+selected+'"><span>'+$(this).text()+'</span></li>');
      }
    }
      
  });

  if(selectList.find('.selected').length < 1 && selectText.attr('placeholder') == undefined){
    selectList.children().eq(0).addClass('selected')
    selectText.val(selectList.children().eq(0).text());
  }

  selectText.on('click', function(e) {
      selectList.addClass('active');
      selectList.focus();
  });

  selectList.focusout(function(e) {
    selectList.removeClass('active');
  });

  selectList.find('span').on('click', function(e) {
    var text = $(this).text();
    selectText.val(text);
    $(this).closest('li').siblings().removeClass('selected').end().addClass('selected');
    selectList.removeClass('active');

    $(this).closest('.select_field').find('option').each(function() {
      if($(this).text() == text) $(this).prop('selected', 'selected'); 
    });

    $(this).closest('.select_field').find('select').change();
  });
}

/* Accordion */
function accordion(me) {
  var accordion = $(me).closest('.accordion');
  var accGroup = $(me).closest('.acc-group');
  if(!accGroup.hasClass('active')){
    accGroup.siblings('.active').removeClass('active').end().addClass('active');
  }else{
    accGroup.removeClass('active');
  }
  setTimeout(function(){
    $grid.packery();
  });
}

var tabs = {
  init: function() {
    $(".tabs .tab a").click(function(e) {
      e.preventDefault();
      if(!$(this).hasClass('active')){
        $(this).closest('.tab').siblings('.active').removeClass('active').end().addClass('active');
        tabs.active();
      }
    });
    tabs.active();
  },
  active: function() {
    $(".tabs .tab").each(function(index, el) {
      var t = $(this).find('a').attr('href');
      if($(this).hasClass('active')){
        $(t).addClass('active').css({display:''})
      }else{
        $(t).removeClass('active').css({display:'none'});
      }
    });
  }
}

function styleMode() {
  if ($('body').hasClass('dark_mode')) {
    $('body').removeClass('dark_mode');
    setCookie('darkMode',0,365);
  }else{
    $('body').addClass('dark_mode');
    setCookie('darkMode',1,365);
  }
}

var addRemoveItem = {
    add: function(t) {
        var itemList = $(t).closest('.addRemoveItem_ctrl').prev('.addRemoveItem_list'),
            min = parseInt($(t).closest('.addRemoveItem_ctrl').data('min')),
            max = parseInt($(t).closest('.addRemoveItem_ctrl').data('max'));

//        for(i = 0; i < min; i++) {
            var item = itemList.find('.item').eq(0).clone();
            item.find('input').not("input[type=radio]").val('');
            item.find('input').attr('fileSeq', '');
            item.find('textarea').val('');
            item.find('.ajax_image_file').removeClass('inFiles img');
            item.find('.ajax_image_file').html('');
            item.find('.hidden_img').val('');
            item.find('input[type="file"]').val('');
            item.find('.up_hidden_img').val('');
            item.find('.fileList').html('');
            itemList.append(item);
//        }
    	
        if(itemList.find('.item').length >= max){
            $(t).closest('.addRemoveItem_ctrl').find('button.add').attr("disabled", true);
        }
        if(itemList.find('.item').length > min){
            $(t).closest('.addRemoveItem_ctrl').find('button.remove').attr("disabled", false);
        }
        
        $('.uploadImgList.sortImgList').sortable({
        	item: $('.item'),
        	start: function(event, ui) {
//        	    console.log('start point : ' + ui.item.position().top);
            },
            end: function(event, ui) {
//                console.log('end point : ' + ui.item.position().top);
            }
        })
    },
    remove: function(t) {
        var itemList = $(t).closest('.addRemoveItem_ctrl').prev('.addRemoveItem_list'),
            min = parseInt($(t).closest('.addRemoveItem_ctrl').data('min')),
            max = parseInt($(t).closest('.addRemoveItem_ctrl').data('max'));

        var removeStart = itemList.find('.item').length - 1;
        itemList.find('.item').each(function(i) {
            if(removeStart != 0 && i > removeStart-1) $(this).remove();
        	if(removeStart == 0) {
        		$(this).find('input').val('');
        	}
        });
        
        if(itemList.find('.item').length < max){
            $(t).closest('.addRemoveItem_ctrl').find('button.add').attr("disabled", false);
        }
        
        if(itemList.find('.item').length <= min){
            $(t).closest('.addRemoveItem_ctrl').find('button.remove').attr("disabled", true);
        }
    }
}


function fn_reset(){
	$(".resetVal").val('');
	listThread(1);
}

//파일첨부 공통 함수 START

function fn_attachFile(t){
	$(t).siblings(".chooseFile").trigger("click");
}

function fn_fileValChg(t) { 
	 
	if (t.files && t.files[0]) { 
		var fileVal = $(t).val();
		
		// 파일 확장자 체크
		if(!isEmpty($(t).data("extns")) && !isEmpty(fileVal)) {
			var ext = fileVal.split('.').pop().toLowerCase(); //확장자분리
	        //아래 확장자가 있는지 체크
	        if($.inArray(ext, $(t).data("extns").split(",")) == -1) {
	          alert($(t).data("extns") + "파일만 업로드 할수 있습니다.");
	          $(t).val("");
	          return;
	        }
		}
		
		// 파일 용량 체크
		var maxSize = 10 * 1024 * 1024; // 10MB
		if(!isEmpty($(t).data("max-size"))) maxSize = $(t).data("max-size") * 1024 * 1024; 
	    var fileSize = t.files[0].size;
	    if(fileSize > maxSize){
		    alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
		    $(t).val("");
		    return;
	    }
		
		var fileName = "";
	 	if(!isEmpty(fileVal)) fileName = fileVal.substring((fileVal).lastIndexOf("\\")+1);
	 	$(t).siblings(".btns").hide();
		$(t).siblings(".fileName").val(fileName);
	
		if($("#isModify").val() != "Y") return;
		
		if(!isEmpty($(t).closest(".chooseFile").attr("fileSeq"))) {
			deleteFileArr.push($(t).closest(".chooseFile").attr("fileSeq"));
		}
	}
}

//파일 삭제
function fn_fileDelete(t) {
	if($("#isModify").val() == "Y") {
		var fileSeq = $(t).closest(".file_group").find(".chooseFile").attr("fileSeq");
		if(!isEmpty(fileSeq)) deleteFileArr.push(fileSeq);
	}
	$(t).closest(".file_group").find(".chooseFile").val("");
	$(t).closest(".file_group").find(".fileName").val("");
	$(t).closest(".btns").hide();
}

//수정페이지 첨부파일 다운로드
function fn_fileDownload(t) {
	var fileSeq = $(t).closest(".file_group").find(".chooseFile").attr("fileSeq");
	if(!isEmpty(fileSeq)) window.location.href= "/file/fileDownload.do?fileSeq=" + fileSeq + "&realFilePath=" + $("#realFilePath").val();
} 

function fn_attachImgFile(t) {
	$(t).closest(".uploadImgFile").find('.file-upload-input').trigger('click');
}

function readImgFileName(ipt){
	if (ipt.files && ipt.files[0]) {

		var fileVal = $(ipt).val();
		
		// 파일 확장자 체크
		if(!isEmpty($(ipt).data("extns")) && !isEmpty(fileVal)) {
			var ext = fileVal.split('.').pop().toLowerCase(); //확장자분리
	        //아래 확장자가 있는지 체크
	        if($.inArray(ext, $(ipt).data("extns").split(",")) == -1) {
	          alert($(ipt).data("extns") + "파일만 업로드 할수 있습니다.");
	          $(ipt).val("");
	          return;
	        }
		}
		
		// 파일 용량 체크
		var maxSize = 10; // 10MB
		if(!isEmpty($(ipt).data("max-size"))) maxSize = $(ipt).data("max-size"); 
	    var fileSize = ipt.files[0].size;
	    if(fileSize > (maxSize * 1024 * 1024)){
		    alert("첨부파일 사이즈는 " + maxSize + "MB 이내로 등록 가능합니다.");
		    $(ipt).val("");
		    return;
	    }
		
		var reader = new FileReader();
		var $p = $(ipt).closest(".uploadImgFile");
		reader.onload = function(e) {
			$p.find('.ajax_image_file').addClass("img");
			if(ipt.files[0].type.includes('video')){
				var v = document.createElement('video');
				var url = URL.createObjectURL(ipt.files[0]);
				$(v).attr('src', url)
				$(v).attr('height', '240px')
				$(v).attr('controls', 'controls')
				$p.find(".ajax_image_file").append(v);
			}else{
				$p.find('.ajax_image_file').html('<img src="' + e.target.result + '">');
			}
			$p.find('.btn_delete').show();
			if($p.hasClass("multiUploadImgFile")) $p.find('.btn_delete').attr('onclick', 'fn_removeMultiUploadImg(this)'); 
			else $p.find('.btn_delete').attr('onclick', 'fn_removeUploadImg(this)');
		};

		if($("#isModify").val() == "Y") {
			var fileSeq = $(ipt).attr("fileSeq");
			if(!isEmpty(fileSeq) && deleteFileArr != null) deleteFileArr.push(fileSeq);
		} 
		
		reader.readAsDataURL(ipt.files[0]);
	}
}

function readImgURL(input) {
	var $this = $(input);
	var $parent = $this.closest(".item");
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		
		var fileSize = input.files[0].size;
		var maxSize = 10 * 1024 * 1024;
		if(fileSize > maxSize){
			alert("첨부파일 사이즈는 10MB 이내로 등록 가능합니다.");
			$this.val('');
			return false;
		}

		reader.onload = function(e) {
			$parent.find(".image-upload-wrap").hide();
			$parent.find('.ajax_image_file').addClass("img");
			$parent.find('.ajax_image_file').html('<img src="' + e.target.result + '">');
		};
		
		if($("#isModify").val() == "Y") {
			var fileSeq = $this.attr("fileSeq");
			if(!isEmpty(fileSeq) && deleteFileArr != null) deleteFileArr.push(fileSeq);
		} 
		
		reader.readAsDataURL(input.files[0]); 
		$parent.find(".btn_delete").show();
	} 
}

function fn_removeUploadImg(t) {
	$parent = $(t).closest('.uploadImgFile');
    $parent.find('.ajax_image_file').html('');
    $parent.find('.ajax_image_file').removeClass("img");
    
    var $fileIpt = $parent.find('input:file');
    var fileSeq = $fileIpt.attr('fileSeq');
    if(!isEmpty(fileSeq) && deleteFileArr != null) deleteFileArr.push(fileSeq);
    
    $fileIpt.val("");
    $fileIpt.attr("fileSeq", "");
    $(t).css("display","none");
}

function fn_removeMultiUploadImg(t) {
	if($(".addRemoveItem_list .item").length == 1) fn_removeUploadImg(t)
	else {
		$parent = $(t).closest('.uploadImgFile');
		
		var $fileIpt = $parent.find('input:file');
	    var fileSeq = $fileIpt.attr('fileSeq');
	    if(!isEmpty(fileSeq) && deleteFileArr != null) deleteFileArr.push(fileSeq);
	    
		$(t).closest('.item').remove();
	}
}

function fn_addFile(cls, e) {
	var cnt = $(e).closest('.uploadFile').find("input[type=file]").length;
	$("[name=" + cls + "_" + cnt).trigger('click');
	$(e).closest('.uploadFile').append($("[name=" + cls + "_" + cnt).clone().attr('name', cls + '_' + (cnt + 1)) );
}

function fn_readFileName(input) {
	var $parent = $(input).closest('.uploadFile');

	if (input.files && input.files[0]) {
		var str = '<li class="file" data-file-ipt-name="' + $(input).attr("name") + '">'
			+ ' 	<input name="" type="checkbox" id="'+input.files[0].name+'" class="custom files">'
			+ ' 	<label for="'+input.files[0].name+'" class="text-none"></label>'
			+ ' 	<span class="name">' + input.files[0].name
			+ '</span>' + ' </li>';
		$parent.find('.fileList').append(str);
	}
}

function fn_fileAllChk(t) {
	$(t).closest(".uploadFile").find("input:checkbox").prop("checked", $(t).is(":checked"))
}

function fn_multiDeleteFile(obj) {
	var $parent = $(obj).closest('.uploadFile');

	if ($parent.find('.files:checked').length != 0) {
		$parent.find('.files:checked').each(function(idx, item) {
			var $fileLi = $(this).parents("li.file");
			$("[name=" + $fileLi.attr("data-file-ipt-name") + "]").val("");
			$fileLi.remove();
			if(!isEmpty($(item).attr('id')) && deleteFileArr != null) deleteFileArr.push($(item).attr('id'));
		});
	} else {
		alert("삭제하실 파일을 선택해주세요.")
	}
}

function fn_multiFileDown(fileSeq) {
	window.location.href= "/file/fileDownload.do?fileSeq=" + fileSeq + "&realFilePath=" + $("#realFilePath").val();
}

// 파일첨부 공통 함수 END

var addRemoveVisualItem = {
    add: function() {
    	
        var item = $(".visualOuterWrap").find('.group').eq(0).clone();
        
        item.removeClass("modifyInfo").addClass("insertInfo");
        item.find("input").val("");
        item.find("textarea").val("");
        item.find(".ajax_image_file").html("");
        item.find(".useYnChk").prop("checked", true);
        item.find("input").attr("fileSeq", "");
        item.find(".visualIdx").text($(".visualOuterWrap").find('.group').length + 1);
        item.find(".delBtn").show();
        
        $(".visualOuterWrap").append(item);
        
    },
    remove: function(t) {
        $(t).parents(".group").remove();
        $(".visualIdx").each(function(idx, item){ $(this).text(idx + 1); })
    }
}

$(function(){
	var copyright = '<div class="copyright" style="text-align: right; color: #666; font-size: 12px; position: absolute; right: 35px; bottom: 5px;">Copyright © kt Co., Ltd. All rights reserved.</div>';
	if($("#main").length > 0 && $("#main .copyright").length == 0) $("#main").append(copyright);
})
    
