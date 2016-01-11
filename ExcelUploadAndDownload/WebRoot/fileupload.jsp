<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<title>jQuery File Upload Example</title>
<link rel="stylesheet" href="js/bootstrap3/css/bootstrap.css">
<!-- Generic page styles -->
<link rel="stylesheet" href="css/style.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="css/jquery.fileupload.css">
</head>
<body>
<div class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-fixed-top .navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="https://github.com/blueimp/jQuery-File-Upload">jQuery File Upload Demo</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="https://github.com/blueimp/jQuery-File-Upload/tags">Waitting...</a></li>
            </ul>
        </div>
    </div>
</div>
<div class="container">
    <h1>jQuery File Upload Demo</h1>
    <h2 class="lead">by TimBryant</h2>
    <ul class="nav nav-tabs">
        <li class="active"><a href="fileupload.jsp">Basic</a></li>
        <li><a href="fileupload_plus.jsp">Basic Plus</a></li>
        <li><a href="index.html">Basic Plus UI</a></li>
        <li><a href="jquery-ui.html">jQuery UI</a></li>
    </ul>
    <br>
    <blockquote>
        <p>File Upload widget with multiple file selection, drag&amp;drop support and progress bar for jQuery.<br>
        Supports cross-domain, chunked and resumable file uploads.<br>
        Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.</p>
    </blockquote>
    <br>
    <!-- The fileinput-button span is used to style the file input field as button -->
    <span class="btn btn-success fileinput-button">
        <i class="glyphicon glyphicon-plus"></i>
        <span>选择文件...</span>
        <!-- The file input field used as target for the file upload widget -->
        <input id="fileupload" type="file" name="files[]" multiple>
    </span>
    <br>
    <br>
    <!-- The global progress bar -->
    <div id="progress" class="progress">
        <span class="progress-bar progress-bar-success"></span>
    </div>
    <!-- The container for the uploaded files -->
    <div id="files" class="files"></div>
    <br>
</div>

<!-- 引入js文件 -->
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script src="js/plupload/jquery.ui.widget.js"></script>
<script src="js/plupload/jquery.iframe-transport.js"></script>
<script src="js/plupload/jquery.fileupload.js"></script>
<script src="js/bootstrap3/js/bootstrap.min.js"></script>
<script>
/*jslint unparam: true */
/*global window, $ */
$(function () {
    //'use strict';
    // Change this to the location of your server-side upload handler:
    var url = "useCommonsFileUploadServlet.do";
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        done: function (e, data) {
        	console.info(data);
            $.each(data.files, function (index, file) {
                $('<p/>').text(file.name).appendTo('#files');
            });
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                'width',
                progress + '%'
            );
            //自定义百分比数字的显示
            $('#progress .progress-bar').html(
                   progress + '%'
            );
        }
    }).prop('disabled', !$.support.fileInput)
        .parent().addClass($.support.fileInput ? undefined : 'disabled');
});
</script>
</body> 
</html>