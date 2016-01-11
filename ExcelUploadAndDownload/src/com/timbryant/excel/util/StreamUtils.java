package com.timbryant.excel.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtils {
	public static String read(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buf = new byte[1024];
		int len = -1;
		while ((len = is.read(buf)) != -1) {
			baos.write(buf, 0, len);
		}
		baos.close();
		is.close();
		return new String(baos.toByteArray());
	}
}
