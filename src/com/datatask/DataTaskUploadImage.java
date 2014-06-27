package com.datatask;

import java.io.File;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.custom.Global;
import com.custom.JSONParser;
import com.custom.LogM;

public class DataTaskUploadImage extends AsyncTask<String[], Void, String>
{
	private Context context;
	private ProgressDialog mDialog;
	// private String requestString = "";
	String dialogTitle = "";
	boolean showDialogContinue = false;
	boolean showCustomDialog = false;
	private String[] key;
	private String[] value;
	private String url = "";
	private File file;

	// public DataTaskUploadImage(Context context, String requestString, String
	// dialogTitle)
	// {
	// showDialogContinue = false;
	// showCustomDialog = false;
	// this.dialogTitle = dialogTitle;
	// this.context = context;
	// this.requestString = requestString;
	// }
	//
	// public DataTaskUploadImage(Context context, String requestString, boolean
	// showCustomDialog, boolean showDialog, ProgressDialog mDialog)
	// {
	// this.context = context;
	// this.requestString = requestString;
	// this.showCustomDialog = showCustomDialog;
	// this.showDialogContinue = showDialog;
	// this.mDialog = mDialog;
	// }

	public DataTaskUploadImage(Context context, File file, String dialogTitle)
	{
		showDialogContinue = false;
		showCustomDialog = false;
		this.context = context;
		this.key = key;
		this.value = value;
		this.dialogTitle = dialogTitle;
		this.url = url;
		this.file = file;
	}

	protected void onPreExecute()
	{
		if (context != null)
		{
			if (showCustomDialog)
			{
				if (!mDialog.isShowing())
				{
					mDialog.show();
				}
			}
			else
			{
				mDialog = new ProgressDialog(context);
				mDialog.setMessage(dialogTitle);
				mDialog.setCanceledOnTouchOutside(false);
				if (mDialog != null)
				{
					if (!mDialog.isShowing())
					{
						mDialog.show();
					}

				}
			}
		}
	}

	@Override
	protected void onCancelled()
	{
		if (mDialog != null)
		{
			mDialog.dismiss();
		}
		super.onCancelled();
	}

	protected String doInBackground(String[]... args)
	{
		String result = null;

		try
		{
			MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("action", new StringBody("photoUpload"));
			reqEntity.addPart("fileToUpload", new FileBody(file));
			reqEntity.addPart("encytype", new StringBody("form-multi-part"));
			result = JSONParser.Webserice_Call_Multipart(Global.URL, reqEntity);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}

	@Override
	protected void onPostExecute(String result)
	{
		LogM.w("onPostExecute result : " + result);

		try
		{
			if (mDialog != null)
			{
				if (showCustomDialog)
				{
					if (!showDialogContinue)
					{
						mDialog.dismiss();
					}
				}
				else
				{
					mDialog.dismiss();
				}
			}
		}
		catch (Exception e)
		{
		}
	}
}
