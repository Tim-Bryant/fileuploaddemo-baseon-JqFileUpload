package com.timbryant.excel.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.timbryant.excel.util.FtpFileUpload;
import com.timbryant.excel.util.StringUtil;

/**
 * Servlet implementation class UseCommonsFileUploadServlet
 */
public class UseCommonsFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UseCommonsFileUploadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 必须是post方法
		boolean multipartContent = ServletFileUpload.isMultipartContent(request);
		// 如果前台是以文件流方式上传
		if (multipartContent) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				for (FileItem fileItem:items) {
					if (fileItem.isFormField()) {
						 String fieldName = fileItem.getFieldName();
						 String value = fileItem.getString("UTF-8");
						// System.out.println(fieldName+":"+new
						// String(value.getBytes("ISO8859_1"), "UTF-8"));
						System.out.println(fieldName + ":" + value);
					} else {
						InputStream inputStream = fileItem.getInputStream();
						String longName =fileItem.getName();
						System.out.println("长文件名");
						// 获得文件后缀
						/**
						 * 在java中 \代表转义字符 \n \t 等，而 \\ 代表一个反斜杠 而.代表一个元字符
						 * 要表示一个.就需要用 要用\. 所以"\\." 在实际编译中 就代表 .
						 */
						if(!StringUtils.isBlank(longName)){
							String suffix = StringUtil.getFileExt(longName);
							String fileName = StringUtil.getFileName(longName);
							String realFileName = fileName.split("\\.")[0];
							FtpFileUpload.uploadFile(realFileName + suffix, inputStream);
						}
						/*
						 * File file = new File("E:/"+realFileName + suffix);
						 * file.createNewFile(); FileOutputStream outputStream
						 * =FileUtils.openOutputStream(file); //FileOutputStream
						 * outputStream=new FileOutputStream(file); byte[] b =
						 * new byte[1024]; while (inputStream.read(b) != -1) {
						 * outputStream.write(b); } outputStream.flush();
						 * outputStream.close(); inputStream.close();
						 */
					}
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}
	}

}
