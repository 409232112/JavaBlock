package wyc.block.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
	 * 将byte数组转为16进制字符串
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
	 * 将16进制字符串转成byte数组
	 * @param str
	 * @return
	 */
	public static byte[] hex2Byte(String str) {
		if(str.length()%2==1){
			str="0"+str;
		}
		int iPos = 0;
		int iLen = str.length();
		byte[] result =new byte[iLen/2];

		while(iLen>=2){
			result[iPos++] = Byte.valueOf(str.substring(0, 2));
			str= str.substring(2);
			iLen = str.length();
		}
		return result;
	}

	public static void main(String[] args){

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
		int totalLength = 0;
		for(byte[] arg:args){
			totalLength+=arg.length;
		}
		byte[] result =  new byte[totalLength];
		int i=0;
		for(byte[] arg:args){
			for(byte bt :arg){
				result[i]=bt;
				i++;
			}
		}
		return result;
	}




	/**
	 * 将num数字左移动moveCount位
	 * @param num
	 * @param moveCount
	 * @return
	 */
	public static BigInteger moveLeft(int num,int moveCount){
		BigDecimal a = new BigDecimal(num);
		BigDecimal b = new BigDecimal(2);
		b=b.pow(moveCount);
		a=a.multiply(b);
		return new BigInteger(a.toString());
	}


	/**
	 * 255 ->[0,0,0,0,0,0,0,255]
	 * 257 ->[0,0,0,0,0,0,1,1]
	 * @param value
	 * @return
	 */
	public static byte[]  intToHex(long value){
		int count=7;
		short[] bytes = new short[8];
		cal(value,count,bytes);
		return shorts2bytes(bytes);
	}


	private static void cal(long value,int count,short[] bytes){
		if (value/256 == 0){
			bytes[count]=(short)value;
			count--;
		}else {
			bytes[count]= (short)(value%256);
			count--;
			cal(value/256,count,bytes);
		}

	}

	/**
	 * short数组转byte数组
	 * @param src
	 * @return
	 */
	public static byte[] shorts2bytes(short[] src) {
		int count = src.length;
		byte[] dest = new byte[count << 1];
		for (int i = 0; i < count; i++) {
			dest[i * 2] = (byte) (src[i] >> 8);
			dest[i * 2 + 1] = (byte) (src[i] >> 0);
		}
		return dest;
	}

	/**
	 * byte数组转short数组
	 * @param src
	 * @return
	 */
	public static short[] bytes2Shorts(byte[] src) {
		int count = src.length >> 1;
		short[] dest = new short[count];
		for (int i = 0; i < count; i++) {
			dest[i] = (short) (src[i * 2] << 8 | src[2 * i + 1] & 0xff);
		}
		return dest;
	}

	/**
	 * byte数组转成BigInteger
	 * @param bytes
	 * @return
	 */
	public static BigInteger bytes2BigInteger(byte[] bytes) {
		return  new BigInteger(bytes);
	}
}
