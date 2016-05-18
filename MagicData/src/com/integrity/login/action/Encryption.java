package com.integrity.login.action;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption {
	private static String Algorithm = "DES";
	private static String key = "*:@1$7!a";

	public static void main(String[] args) {
		String str = "adminhha好";
		try {
			String f1 = Encrypt(str);
			System.out.println("加密后：" + f1);
			String f2 = Decrypt("%E4%B2V%A5%D6%B98%B7");
			System.out.println("解密后：" + f2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String md5(String str) {
		String md5_str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			byte b[] = md.digest();

			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			md5_str = buf.toString();
			// System.out.println("result: " + buf.toString());// 32位的加密
			// System.out.println("result: " + buf.toString().substring(8,
			// 24));// 16位的加密

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5_str;
	}
	public static String encodeUTF8(String xmlDoc) throws Exception {
		String str = URLEncoder.encode(xmlDoc, "utf-8");
		return str;
	}

	public static String decodeUTF8(String str) throws Exception {
		String xmlDoc = URLDecoder.decode(str, "utf-8");
		return xmlDoc;
	}

	public static String Encrypt(String cleartext) throws Exception {
		SecretKey skey = new SecretKeySpec(key.getBytes(), Algorithm);
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(1, skey);
		byte[] cipherByte = cipher.doFinal(cleartext.getBytes());

		return URLEncoder.encode(new String(cipherByte, "ISO-8859-1"), "ISO-8859-1");
	}

	public static String Decrypt(String ciphertext) throws Exception {
		ciphertext = URLDecoder.decode(ciphertext, "ISO-8859-1");
		SecretKey skey = new SecretKeySpec(key.getBytes(), Algorithm);
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(2, skey);
		byte[] clearByte = cipher.doFinal(ciphertext.getBytes("ISO-8859-1"));
		return new String(clearByte);
	}

	public static String EncryptForFloat(float cleartext) throws Exception {
		SecretKey skey = new SecretKeySpec(key.getBytes(), Algorithm);
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(1, skey);

		byte[] cipherByte = cipher.doFinal(String.valueOf(cleartext).getBytes());

		String temp = URLEncoder.encode(new String(cipherByte, "ISO-8859-1"), "ISO-8859-1");

		return temp;
	}

	public static float DecryptForFloat(String ciphertext) throws Exception {
		String temp1 = String.valueOf(ciphertext);
		temp1 = URLDecoder.decode(temp1, "ISO-8859-1");
		SecretKey skey = new SecretKeySpec(key.getBytes(), Algorithm);
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(2, skey);
		byte[] clearByte = cipher.doFinal(temp1.getBytes("ISO-8859-1"));
		String temp2 = new String(clearByte);
		return Float.valueOf(temp2).floatValue();
	}

	/**
	 * 方法描述: md5签名
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String md5Digest(String src) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] b = md.digest(src.getBytes("UTF-8"));

		return byte2HexStr(b);
	}

	/**
	 * 字节数组转化为大写16进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}

			sb.append(s.toUpperCase());
		}

		return sb.toString();
	}
}