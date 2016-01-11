package com.timbryant.excel.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadServlet
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
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
		System.out.println("文件解析");
		// request获取文件流信息
		InputStream inputStream = request.getInputStream();

		String temfileName = "E:/tempFile";
		// tempFile指向临时文件
		File tempFile = new File(temfileName);
		// outputstream文件输出流指向这个临时文件
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		byte b[] = new byte[1024];
		int n;
		while ((n = inputStream.read(b)) != -1) {
			fileOutputStream.write(b, 0, n);
		}
		// 关闭流
		inputStream.close();
		fileOutputStream.close();

		// 获取上传文件名称
		RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r");
		randomAccessFile.readLine();
		String line2 = randomAccessFile.readLine();
		int beginIndex = line2.lastIndexOf("\\") + 1;
		int endIndex = line2.lastIndexOf("\"");
		String filenameString = line2.substring(beginIndex, endIndex);
		System.out.println(filenameString);

		// 重新定位文件指针到文件头
		randomAccessFile.seek(0);
		long startPosition = 0;
		int i = 1;
		// 获取文件内容开始位置
		while ((n = randomAccessFile.readByte()) != -1 && i <= 4) {
			if (n == '\n') {// 读取换行符的位置
				startPosition = randomAccessFile.getFilePointer();
				i++;
			}
		}
		startPosition = startPosition - 1;

		// 获取文件内容的结束位置
		randomAccessFile.seek(randomAccessFile.length());
		long endPosition = randomAccessFile.getFilePointer();
		int j = 1;
		while (endPosition >= 0 && j <= 2) {
			endPosition--;
			randomAccessFile.seek(endPosition);
			if (randomAccessFile.readByte() == '\n') {
				j++;
			}
		}
		endPosition = endPosition - 1;
	}
}
