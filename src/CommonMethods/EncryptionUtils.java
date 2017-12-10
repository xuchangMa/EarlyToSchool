package CommonMethods;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;

public class EncryptionUtils {
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * MD5����
	 * 
	 * @param bytes
	 *            an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeMD5(final byte[] bytes) {
		return DigestUtils.md5Hex(bytes);
	}

	/**
	 * MD5���ܣ�Ĭ��UTF-8
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeMD5(final String str) {
		return encodeMD5(str, DEFAULT_CHARSET);
	}

	/**
	 * MD5����
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @param charset
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeMD5(final String str, final String charset) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bytes = str.getBytes(charset);
			return encodeMD5(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * SHA����
	 * 
	 * @param bytes
	 *            an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeSHA(final byte[] bytes) {
		return DigestUtils.sha512Hex(bytes);
	}

	/**
	 * SHA����
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @param charset
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeSHA(final String str, final String charset) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bytes = str.getBytes(charset);
			return encodeSHA(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * SHA����,Ĭ��utf-8
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeSHA(final String str) {
		return encodeSHA(str, DEFAULT_CHARSET);
	}

	/**
	 * BASE64����
	 * 
	 * @param bytes
	 *            an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeBASE64(final byte[] bytes) {
		return new String(Base64.encodeBase64String(bytes));
	}

	/**
	 * BASE64����
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @param charset
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeBASE64(final String str, String charset) {
		if (str == null) {
			return null;
		}
		try {
			byte[] bytes = str.getBytes(charset);
			return encodeBASE64(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * BASE64����,Ĭ��UTF-8
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String encodeBASE64(final String str) {
		return encodeBASE64(str, DEFAULT_CHARSET);
	}

	/**
	 * BASE64����,Ĭ��UTF-8
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String decodeBASE64(String str) {
		return decodeBASE64(str, DEFAULT_CHARSET);
	}

	/**
	 * BASE64����
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @param charset
	 *            �ַ�����
	 * @return a {@link java.lang.String} object.
	 */
	public static String decodeBASE64(String str, String charset) {
		try {
			byte[] bytes = str.getBytes(charset);
			return new String(Base64.decodeBase64(bytes));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CRC32�ֽ�У��
	 * 
	 * @param bytes
	 *            an array of byte.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(byte[] bytes) {
		CRC32 crc32 = new CRC32();
		crc32.update(bytes);
		return Long.toHexString(crc32.getValue());
	}

	/**
	 * CRC32�ַ���У��
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @param charset
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(final String str, String charset) {
		try {
			byte[] bytes = str.getBytes(charset);
			return crc32(bytes);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * CRC32�ַ���У��,Ĭ��UTF-8�����ȡ
	 * 
	 * @param str
	 *            a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(final String str) {
		return crc32(str, DEFAULT_CHARSET);
	}

	/**
	 * CRC32��У��
	 * 
	 * @param input
	 *            a {@link java.io.InputStream} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(InputStream input) {
		CRC32 crc32 = new CRC32();
		CheckedInputStream checkInputStream = null;
		int test = 0;
		try {
			checkInputStream = new CheckedInputStream(input, crc32);
			do {
				test = checkInputStream.read();
			} while (test != -1);
			return Long.toHexString(crc32.getValue());
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * CRC32�ļ�ΨһУ��
	 * 
	 * @param file
	 *            a {@link java.io.File} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(File file) {
		InputStream input = null;
		try {
			input = new BufferedInputStream(new FileInputStream(file));
			return crc32(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	/**
	 * CRC32�ļ�ΨһУ��
	 * 
	 * @param url
	 *            a {@link java.net.URL} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String crc32(URL url) {
		InputStream input = null;
		try {
			input = url.openStream();
			return crc32(input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}
}
