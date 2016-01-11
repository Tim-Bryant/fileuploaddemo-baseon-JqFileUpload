<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>文件上传下载学习</title>
<link rel="stylesheet" type="text/css" href="css/forall.css">
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
</head>

<body>
    <form action="useCommonsFileUploadServlet.do" enctype="multipart/form-data" method="post">
      <input id="textVal" name="textVal" type="text" />
                请选择文件:<input id="streamfile" name="streamfile" type="file" />
               <input type="submit" value="提交" /> 
    </form>
</body>
</html>