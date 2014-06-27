package com.custom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class JSONParser
{

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser()
	{
	}

	public static String Webserice_Call_Multipart(String url, MultipartEntity reqEntity) throws Exception
	{
		String result = null;

		// MultipartEntity reqEntity;

		try
		{
			DefaultHttpClient httpclient = new DefaultHttpClient();

			HttpPost httppost = new HttpPost(url);

			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			// is = entity.getContent();
			result = EntityUtils.toString(entity);

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
		/*
		 * try
		 * {
		 * BufferedReader reader = new BufferedReader(new InputStreamReader(is,
		 * "iso-8859-1"), 8);
		 * StringBuilder sb = new StringBuilder();
		 * String line = null;
		 * while ((line = reader.readLine()) != null)
		 * {
		 * sb.append(line + "\n");
		 * }
		 * is.close();
		 * result = sb.toString();
		 * 
		 * } catch (Exception e)
		 * {
		 * e.printStackTrace();
		 * throw e;
		 * // Log.e("log_tag", "Error converting result " + e.toString());
		 * }
		 */return result;
	}

	// public static String getJSONResponseFromMltipart(String url)
	// {
	// InputStream is = null;
	// String json = "";
	//
	// // Making HTTP request
	// try
	// {
	// // defaultHttpClient
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// HttpPost httpPost = new HttpPost(url);
	//
	// Multip multipartContent = new CustomMultiPartEntity(new
	// ProgressListener()
	// {
	// @Override
	// public void transferred(long num)
	// {
	// publishProgress((int) ((num / (float) totalSize) * 100));
	// }
	// });
	//
	// File myFile = new File(sFilePath);
	//
	// FileBody fileBody = new FileBody(myFile, "audio/aac");
	// multipartContent.addPart("file", fileBody);
	//
	// if (isLogin)
	// {
	// multipartContent.addPart("id", new StringBody(sUserID));
	// multipartContent.addPart("login_key", new StringBody(sLoginKey));
	// }
	//
	// totalSize = multipartContent.getContentLength();
	//
	// httpPost.setEntity(multipartContent);
	//
	// HttpResponse httpResponse = httpClient.execute(httpPost);
	// HttpEntity httpEntity = httpResponse.getEntity();
	// is = httpEntity.getContent();
	//
	// }
	// catch (UnsupportedEncodingException e)
	// {
	// e.printStackTrace();
	// }
	// catch (ClientProtocolException e)
	// {
	// e.printStackTrace();
	// }
	// catch (IOException e)
	// {
	// e.printStackTrace();
	// }
	//
	// try
	// {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(is,
	// "iso-8859-1"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null)
	// {
	// sb.append(line + "\n");
	// }
	// is.close();
	// json = sb.toString();
	// }
	// catch (Exception e)
	// {
	// Log.e("Buffer Error", "Error converting result " + e.toString());
	// }
	// // return JSON String
	// return json;
	// }

	public static String getJSONResponseFromUrl(String url, String[] name, String[] value)
	{

		// Making HTTP request
		try
		{
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("encytype", "multipart/form-data");

			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			if (value != null)
			{
				for (int i = 0; i < value.length; i++)
				{
					pairs.add(new BasicNameValuePair(name[i], value[i]));
				}
			}

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, HTTP.UTF_8);
			httpPost.setEntity(entity);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			reader.close();
			json = sb.toString();
		}
		catch (Exception e)
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// return JSON String
		return json;
	}

	public static String getJSONResponseFromUrl(String url)
	{

		LogM.i("URL : " + url);
		// Making HTTP request
		try
		{
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);

			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
			{
				sb.append(line + "\n");
			}
			reader.close();
			json = sb.toString();
		}
		catch (Exception e)
		{
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// return JSON String
		return json;
	}
}
