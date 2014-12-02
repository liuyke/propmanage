package com.prop.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class IOUtils {

	public static String readFile2String(String path) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str).append("\n");
			}
			if (sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] readByteArrayFromResource(String resource)	throws IOException {
		byte[] arrayOfByte;
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
			if (in == null) {
				arrayOfByte = null;
				return arrayOfByte;
			}
			arrayOfByte = readByteArray(in);
			return arrayOfByte;
		} finally {
			if (in != null)
				in.close();
		}
	}

	public static byte[] readByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		copy(input, output);
		return output.toByteArray();
	}

	public static long copy(InputStream input, OutputStream output)	throws IOException {
		byte[] buffer = new byte[4096];
		long count = 0L;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}
}
