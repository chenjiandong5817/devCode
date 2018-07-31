package com.plugs.utils;


import com.util.StringUtil;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class HttpKit {

	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	private static String get(String url) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException, KeyManagementException {
		StringBuffer bufferRes = null;
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL urlGet = new URL(url);
		HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
		// 连接超时
		http.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		http.setReadTimeout(25000);
		http.setRequestMethod("GET");
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setSSLSocketFactory(ssf);
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();

		InputStream in = http.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in,
				DEFAULT_CHARSET));
		String valueString = null;
		bufferRes = new StringBuffer();
		while ((valueString = read.readLine()) != null) {
			bufferRes.append(valueString);
		}
		in.close();
		if (http != null) {
			// 关闭连接
			http.disconnect();
		}
		return bufferRes.toString();
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	public static String get(String url, Boolean https)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException,
			KeyManagementException {
		if (https != null && https) {
			return get(url);
		}
		else {
			StringBuffer bufferRes = null;
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		}
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static String get(String url, Map<String, String> params, Boolean https)
			throws KeyManagementException, NoSuchAlgorithmException,
			NoSuchProviderException, IOException {
		return get(initParams(url, params), https);
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	private static byte[] getBytes(String url) throws NoSuchAlgorithmException,
			NoSuchProviderException, IOException, KeyManagementException {
		StringBuffer bufferRes = null;
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();

		URL urlGet = new URL(url);
		HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
		// 连接超时
		http.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		http.setReadTimeout(25000);
		http.setRequestMethod("GET");
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setSSLSocketFactory(ssf);
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();

		InputStream in = http.getInputStream();

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=in.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}

		in.close();
		if (http != null) {
			// 关闭连接
			http.disconnect();
		}
		return outStream.toByteArray();
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	public static byte[] getBytes(String url, Boolean https)
			throws NoSuchAlgorithmException, NoSuchProviderException, IOException,
			KeyManagementException {
		if (https != null && https) {
			return getBytes(url);
		}
		else {
			URL urlGet = new URL(url);
			System.out.println(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("GET");
			http.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			InputStream in = http.getInputStream();

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while( (len=in.read(buffer)) != -1 ){
				outStream.write(buffer, 0, len);
			}

			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return outStream.toByteArray();
		}
	}
	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static byte[] getBytes(String url, Map<String, String> params, Boolean https)
			throws KeyManagementException, NoSuchAlgorithmException,
			NoSuchProviderException, IOException {
		return getBytes(initParams(url, params), https);
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws KeyManagementException
	 */
	public static String post(String url, Map<String, String> params,
			Boolean https) throws NoSuchAlgorithmException, NoSuchProviderException,
			IOException, KeyManagementException {
		if (https != null && https) {
			return get(url);
		}
		else {
			StringBuffer bufferRes = null;
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("POST");
			http.setRequestProperty("Accept-Charset", "utf-8");
			http.setRequestProperty("contentType", "application/json");
			http.setUseCaches(false);
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			StringBuffer strBuffer = new StringBuffer();

			boolean first = true;
			for (Entry<String, String> entry : params.entrySet()) {
				if (first) {
					first = false;
				}
				else {
					strBuffer.append("&");
				}
				String key = entry.getKey();
				String value = entry.getValue();
				strBuffer.append(key).append("=");
				if (!StringUtil.isEmpty(value)) {
					strBuffer.append(value);
				}
			}

			byte[] bypes = strBuffer.toString().getBytes();
			http.getOutputStream().write(bypes);// 输入参数

			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,
					DEFAULT_CHARSET));
			String valueString = null;
			bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			return bufferRes.toString();
		}
	}

	/**
	 * 发送Post请求
	 *
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public static String post(String url, String params) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		StringBuffer bufferRes = null;
		TrustManager[] tm = { new MyX509TrustManager() };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		URL urlGet = new URL(url);
		HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();
		// 连接超时
		http.setConnectTimeout(25000);
		// 读取超时 --服务器响应比较慢，增大时间
		http.setReadTimeout(25000);
		http.setRequestMethod("POST");
		http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		http.setSSLSocketFactory(ssf);
		http.setDoOutput(true);
		http.setDoInput(true);
		http.connect();
		OutputStream out = http.getOutputStream();
		out.write(params.getBytes("UTF-8"));
		out.flush();
		out.close();
		InputStream in = http.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in,
				DEFAULT_CHARSET));
		String valueString = null;
		bufferRes = new StringBuffer();
		while ((valueString = read.readLine()) != null) {
			bufferRes.append(valueString);
		}
		in.close();
		if (http != null) {
			// 关闭连接
			http.disconnect();
		}
		return bufferRes.toString();
	}

	/**
	 * 上传媒体文件
	 *
	 * @param url
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 * @throws KeyManagementException
	 */
	public static String upload(String url, File file) throws IOException,
			NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		String BOUNDARY = "----WebKitFormBoundaryiDGnV9zdZA1eM1yL"; // 定义数据分隔线
		StringBuffer bufferRes = null;
		URL urlGet = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) urlGet.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);

		OutputStream out = new DataOutputStream(conn.getOutputStream());
		byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
				+ file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] data = sb.toString().getBytes();
		out.write(data);
		DataInputStream fs = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = fs.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		out.write("\r\n".getBytes()); // 多个文件时，二个文件之间加入这个
		fs.close();
		out.write(end_data);
		out.flush();
		out.close();

		// 定义BufferedReader输入流来读取URL的响应
		InputStream in = conn.getInputStream();
		BufferedReader read = new BufferedReader(new InputStreamReader(in,
				DEFAULT_CHARSET));
		String valueString = null;
		bufferRes = new StringBuffer();
		while ((valueString = read.readLine()) != null) {
			bufferRes.append(valueString);
		}
		in.close();
		if (conn != null) {
			// 关闭连接
			conn.disconnect();
		}
		return bufferRes.toString();
	}

	/**
	 * 构造url
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	private static String initParams(String url, Map<String, String> params) {
		if (null == params || params.isEmpty()) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		}
		else {
			sb.append("&");
		}
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			}
			else {
				sb.append("&");
			}
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=");
			if (!StringUtil.isEmpty(value)) {
				sb.append(value);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args)throws Exception {
		System.out.println(get("http://59.60.12.62:8765/api/billingWay/billingRules.arport",new HashMap<String, String>(),false));
	}
}

/**
 * 证书管理
 */
class MyX509TrustManager implements X509TrustManager {
	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

	@Override
	public void checkClientTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] chain, String authType)
			throws CertificateException {
	}
}
