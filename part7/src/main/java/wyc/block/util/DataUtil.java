package wyc.block.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

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
	 * Convert hex string to byte[]
	 * @param hexString the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	/**
	 * Convert char to byte
	 * @param c char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
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
		if(bytes==null){
			return "";
		}
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

	/**
	 * RipeMD160消息摘要
	 * @param data 待处理的消息摘要数据
	 * @return byte[] 消息摘要
	 * */
	public static byte[] encodeRipeMD160(byte[] data) throws Exception{
		//加入BouncyCastleProvider的支持
		Security.addProvider(new BouncyCastleProvider());
		//初始化MessageDigest
		MessageDigest md=MessageDigest.getInstance("RipeMD160");
		//执行消息摘要
		return md.digest(data);

	}
	/**
	 * RipeMD160Hex消息摘要
	 * @param data 待处理的消息摘要数据
	 * @return String 消息摘要
	 * **/
	public static String encodeRipeMD160Hex(byte[] data) throws Exception{
		//执行消息摘要
		byte[] b=encodeRipeMD160(data);
		//做十六进制的编码处理
		return new String(Hex.encode(b));
	}

	/**
	 * 在字节数组中截取指定长度数组
	 * @param src 原数组
	 * @param begin 开始截取位置
	 * @param count 截多少位数
	 * @return
	 */
	public static byte[] subBytes(byte[] src, int begin, int count) {
		byte[] bs = new byte[count];
		System.arraycopy(src, begin, bs, 0, count);
		return bs;
	}

	/**
	 * 按照顺序合并多个byte数组为一个byte数组
	 * @param args
	 * @return
	 */
	public static byte[] combineBytes(byte[] ... args){
		int totalLength = 0;
		for(byte[] tmp:args){
			totalLength+=tmp.length;
		}
		byte[] retBytes = new byte[totalLength];
		int n=0;
		for(byte[] a:args){
			for(byte b:a){
				retBytes[n++]=b;
			}
		}
		return retBytes;
	}


	/**
	 * 用于输出打印协议中的值为16进制，以下划线间隔
	 * @param type Send or Recivie or Crc16
	 * @param bytes 协议内容
	 * @return String msg 转化后的字符串
	 */
	public static String bytesToHexStringWithUnderline( String type,byte[] bytes){

		String msg = "["+ type +"]";

		if ( bytes == null){
			return "收到空报文。";
		}

		for (int x = 0; x < bytes.length ; x++) {

			msg += x == bytes.length - 1 ? getNumberHex( bytes[x] ) : getNumberHex( bytes[x] ) + "_";

		}
		return msg;
	}
	public static String getNumberHex(int value){
		String temp = Integer.toHexString(value) ;
		if ( temp.length() > 2 ){
			temp = temp.substring(temp.length() - 2 ,temp.length());
		}else if ( temp.length() == 1 ){
			temp = "0" + temp;
		}
		return temp.toUpperCase();
	}

	public static byte[] shortToBytes(short s) {
		byte[] targets = new byte[2];
		for (int i = 0; i < 2; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}

}
