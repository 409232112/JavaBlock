package wyc.block.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class DataUtil {

	/**
	 * 利用java原生的摘要实现SHA256加密
	 * @param str
	 * @return 加密后的报文
	 */
	public static String getSHA256Str(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}

	/**
	 * 将byte数组转为16进制
	 * @param bytes
	 * @return
	 */
	public static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		String temp = null;
		for (int i = 0; i < bytes.length; i++) {
			temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				// 1得到一位的进行补0操作
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}

	/**
	 * 对byte数组进行hash
	 * @param bytes
	 * @return
	 */
	public static byte[] getSHA256Bytes(byte[] bytes) {
		MessageDigest messageDigest;
		byte[] encodeBytes = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(bytes);
			encodeBytes = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encodeBytes;
	}
	
	
	/**
	 * 字符串转byte数组 
	 * @param str
	 * @return
	 */
	public static byte[] string2Bytes(String str) {
		return str.getBytes();
	}

	/**
	 * byte数组转字符串
	 * @param bytes
	 * @return
	 */
	public static String bytes2String(byte[] bytes) {
		return new String(bytes);
	}
	

	/**
	 * long型转byte数组
	 * @param x long
	 * @return byte[]
	 * */
	public static byte[] longToByteArray(long x) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(0, x);
		return buffer.array();
	}
	
	/**
	 * 多个byte数组组合成一个byte数组
	 * @param args
	 * @return
	 */
	public static byte[] joinByte(byte[]... args){
		int totalLenght = 0;
		for(byte[] arg:args){
			totalLenght+=arg.length;
		}
		byte[] result =  new byte[totalLenght];
		int i=0;
		for(byte[] arg:args){
			for(byte bt :arg){
				result[i]=bt;
				i++;
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		
		
	}
}
