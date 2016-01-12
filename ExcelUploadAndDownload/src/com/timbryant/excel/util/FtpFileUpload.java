package com.timbryant.excel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FtpFileUpload {

	public static boolean uploadFile(String fileName, InputStream inputStream) throws SocketException, IOException {
		FTPClient ftpClient = openFtp();
		OutputStream outputStream = null;
		int bytes;
		byte[] b = new byte[1024];
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		ftpClient.enterLocalPassiveMode();

		outputStream = ftpClient.storeFileStream(new String(fileName.getBytes("GBK"), "ISO8859_1"));
		// log.debug("FTP 链接模式：" + ftpClient.getDataConnectionMode());
		try {
			while ((bytes = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, bytes);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			boolean flag;
			flag = ftpClient.completePendingCommand();
			return flag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * FTP文件的删除
	 * 
	 * @param fileName
	 * @param inputStream
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	public static boolean deleteFile(String pathName) throws SocketException, IOException {
		FTPClient ftpClient = openFtp();
		if (!StringUtils.isBlank(pathName)) {
			FTPFile[] listFiles = ftpClient.listFiles(new String(pathName.getBytes("GBK"), "ISO8859_1"));
			boolean deleteFile = false;
			if (listFiles.length <= 0) {
				System.out.println("文件不存在！");
			} else {
				deleteFile = ftpClient.deleteFile(new String(pathName.getBytes("GBK"), "ISO8859_1"));
			}
			return deleteFile;
		}
		return false;
	}

	/**
	 * FTP登录和目录切换
	 * 
	 * @return
	 * @throws SocketException
	 * @throws IOException
	 */
	private static FTPClient openFtp() throws SocketException, IOException {
		String ftpServerIp = "127.0.0.1";
		int ftpPort = 21;
		String userName = "admin_dx";
		String password = "passw0rd";
		String workingPath = "Timbryant/uploadfile";

		FTPClient ftpClient = null;
		if (ftpClient == null) {
			ftpClient = new FTPClient();
		}
		ftpClient.setBufferSize(1 * 1024 * 1024);
		if (!ftpClient.isConnected()) {
			// ftpClient.connect(ftpServerIp, ftpPort);
			ftpClient.connect(ftpServerIp, ftpPort);// 新版本是int型 原来是string
			ftpClient.login(userName, password);

		}
		if (!StringUtils.isBlank(workingPath)) {
			boolean b = ftpClient.changeWorkingDirectory(workingPath);
			/**
			 * 解决配置的路径不存在，自动创建的bug(配置路径时，账号的根路径不需要写进来)
			 */
			if (!b) {
				String[] directy = workingPath.split("/");
				StringBuffer buf = new StringBuffer();
				for (int i = 0; i < directy.length; i++) {
					if (i == directy.length - 1) {
						buf.append(directy[i].toString());
					} else {
						buf.append(directy[i].toString());
						buf.append("/");
					}
				}
				boolean isTure = ftpClient.makeDirectory(buf.toString());
				// 目录创建成功，转换到该目录
				if (isTure) {
					ftpClient.changeWorkingDirectory(workingPath);
				}
			}
		}
		return ftpClient;
	}
}
