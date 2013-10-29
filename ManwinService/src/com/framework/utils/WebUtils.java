package com.framework.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.manwin.service.BuildConfig;

/**
 * Class that will help you get data from the Internet.
 * 
 * @author Matthieu Coisne
 */
public class WebUtils {

	private static String TAG = WebUtils.class.getSimpleName();

	private WebUtils() {
	}

	/**
	 * Will do a HTTPGet and return data as a string.
	 * @param url The url you want to get data from.
	 * @return Data as a string.
	 * @throws AppStarterException
	 */
	public static String getStringFromHttpGet(String url) throws Exception {
		return StringUtils.convertStreamToString(doHttpGet(url));
	}

	/**
	 * Will do a HTTPPost and return data as a string.
	 * @param url The url you want to get data from.
	 * @param params A list of key-value pair.
	 * @return Data as a string.
	 * @throws AppStarterException
	 */
	public static String getStringFromHttpPost(String url, List<NameValuePair> params) throws Exception {
		return StringUtils.convertStreamToString(doHttpPost(url, params));
	}

	/**
	 * Will do a HTTPGet and return data as an InputStream.
	 * @param url The url you want to get data from.
	 * @return Data as an InputStream.
	 * @throws AppStarterException
	 */
	public static InputStream doHttpGet(String url) throws Exception {
		if (!isNetworkConnected()) {
			throw new Exception();
		}

		if (BuildConfig.DEBUG) {
			Log.d(TAG, "doHttpGet - url: " + url);
		}

		InputStream ret = null;

		// HttpParams httpParameters = new BasicHttpParams();
		// // Set the timeout in milliseconds until a connection is established.
		// // The default value is zero, that means the timeout is not used.
		// int timeoutConnection = 3000;
		// HttpConnectionParams.setConnectionTimeout(httpParameters,
		// timeoutConnection);
		// // Set the default socket timeout (SO_TIMEOUT)
		// // in milliseconds which is the timeout for waiting for data.
		// int timeoutSocket = 5000;
		// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		//
		// DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		// //or httpClient.setParams(httpParameters); if already exists

		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
//			httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; fr; rv:1.9.2) Gecko/20100115 Firefox/3.6");
			HttpResponse response = httpClient.execute(httpGet);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				ret = response.getEntity().getContent();
			}else{
				throw new Exception();
			}
		} catch (IOException e) {
			throw new Exception(e);
		} catch (IllegalArgumentException e){
			throw new Exception(e);
		} catch (IllegalStateException e) {
			throw new Exception(e);
		}

		return ret;
	}

	/**
	 * Will do a HTTPPost and return data as an InputStream.
	 * @param url The url you want to get data from.
	 * @param params A list of key-value pair.
	 * @return Data as an InputStream.
	 * @throws AppStarterException
	 */
	private static InputStream doHttpPost(String url, List<NameValuePair> params) throws Exception {
		if (!isNetworkConnected()) {
			throw new Exception();
		}

		if (BuildConfig.DEBUG) {
			Log.d(TAG, "doHttpPost - url: " + url);
			debugRequest(url, params);
		}

		InputStream ret = null;

		// HttpParams httpParameters = new BasicHttpParams();
		// // Set the timeout in milliseconds until a connection is established.
		// // The default value is zero, that means the timeout is not used.
		// int timeoutConnection = 3000;
		// HttpConnectionParams.setConnectionTimeout(httpParameters,
		// timeoutConnection);
		// // Set the default socket timeout (SO_TIMEOUT)
		// // in milliseconds which is the timeout for waiting for data.
		// int timeoutSocket = 5000;
		// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		//
		// DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		// //or httpClient.setParams(httpParameters); if already exists

		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse response = httpClient.execute(httpPost);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				ret = response.getEntity().getContent();
			}else{
				throw new Exception();
			}
		} catch (IOException e) {
			throw new Exception(e);
		} catch (IllegalArgumentException e){
			throw new Exception(e);
		} catch (IllegalStateException e) {
			throw new Exception(e);
		}
		return ret;
	}

	/**
	 * Method used to print the parameter list used in a HTTPPost.
	 * @param url The url you want to get data from.
	 * @param params A list of key-value pair.
	 */
	private static void debugRequest(String url, List<NameValuePair> params) {
		if (BuildConfig.DEBUG) {
			boolean first = true;
			StringBuilder sb = new StringBuilder();
			sb.append(url);

			if (params != null && params.size() > 0) {
				for (NameValuePair pair : params) {
					if (first) {
						sb.append("?");
					} else {
						sb.append("&");
					}
					sb.append(pair.getName());
					sb.append("=");
					sb.append(pair.getValue());
					first = false;
				}
			}
			Log.d(TAG, "debugRequest - parameters: " + sb.toString());
		}
	}

	/**
	 * Simple network connection check. 
	 * 
	 * <h1>Warning !</h1>
	 * The network the device is connected to may not have access to the Internet!
	 * i.e. A user without a data plan can be connected to a 3G network to make 
	 * phone calls. If he wants to access the Internet, his provider will block the
	 * request and display a custom web page. HTTP response status code will be 
	 * 200 (OK) but you will not get the data you expect.
	 */
	private static boolean isNetworkConnected() {
		return true;
	}
}