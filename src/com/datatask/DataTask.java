package com.datatask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.custom.JSONParser;
import com.custom.LogM;

public class DataTask extends AsyncTask<String[], Void, String>
{
	private Context context;
	private ProgressDialog mDialog;
	private String requestString = "";
	String dialogTitle = "";
	boolean showDialogContinue = false;
	boolean showCustomDialog = false;

	public DataTask(Context context, String requestString, String dialogTitle)
	{
		showDialogContinue = false;
		showCustomDialog = false;
		this.dialogTitle = dialogTitle;
		this.context = context;
		this.requestString = requestString;
	}

	public DataTask(Context context, String requestString, boolean showCustomDialog, boolean showDialog, ProgressDialog mDialog)
	{
		this.context = context;
		this.requestString = requestString;
		this.showCustomDialog = showCustomDialog;
		this.showDialogContinue = showDialog;
		this.mDialog = mDialog;
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
			result = JSONParser.getJSONResponseFromUrl(requestString);
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
