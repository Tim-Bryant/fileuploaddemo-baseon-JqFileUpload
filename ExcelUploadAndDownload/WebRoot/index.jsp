<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传下载学习</title>
<link rel="stylesheet" type="text/css" href="css/forall.css">
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>

</style>
<script type="text/javascript">
  $(function(){
	  $(".thumbs a").click(function(){
		  var largePath=$(this).attr("href");
		  var largeAlt=$(this).attr("alt");
		  $("#largeImg").attr({
			  src:largePath,
			  alt:largeAlt
		  });
		  //阻止a标签页面跳转
		  return false;
	  });
	  
	  $("#myfile").change(function(){
		  var url=getFileUrl("myfile");
		  //$("#previewImg").attr("src","file:///"+$("#myfile").val());
		    var imgPre = document.getElementById("previewImg");
		    imgPre.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;
	  });
	  
	  /**
	    * 从 file 域获取 本地图片 url
	    */
	 function getFileUrl(sourceId) {
	    var url;
	    if (navigator.userAgent.indexOf("MSIE")>=1) { // IE
	    url = document.getElementById(sourceId).value;
	    } else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
	    url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	    } else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome
	    url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0));
	    }
	    return url;
	 }
	  
  });
</script>
</head>

<body>
    <form action="UploadServlet.do" enctype="multipart/form-data" method="post">
                请选择图片:<input id="myfile" name="myfile" type="file" />
               <input type="submit" value="提交" /> 
               
    </form>
    <br>
	<h2>图片预览</h2>
    <p><img id="largeImg" src="images/self_big.jpg" alt="Large Image"/></p>
    <p class="thumbs">
      <a href="images/self_big.jpg" alt="01"><img src="images/self.jpg"/></a>
      <a href="images/cover.jpg"  alt="02"><img src="images/cover.jpg"/></a>
    </p>
</body>
</html>