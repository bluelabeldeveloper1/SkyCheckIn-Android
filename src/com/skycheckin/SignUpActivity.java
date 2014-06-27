package com.skycheckin;

import org.json.JSONException;
import org.json.JSONObject;

import com.custom.Global;
import com.custom.LogM;
import com.custom.MySharedPrefrence;
import com.custom.MyUtility;
import com.datatask.DataTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SignUpActivity extends Activity
{
	private Button btnLogin;
	private LinearLayout llMale, llFemale;
	private EditText etFirstName, etLastName, etPassword, etUserName, etConfirmPassword;
	private ImageView ivMale, ivFemale;
	private TextView btnCancel;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
	}

	private void init()
	{
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnCancel = (TextView) findViewById(R.id.btnCancel);
		llMale = (LinearLayout) findViewById(R.id.llMale);
		llFemale = (LinearLayout) findViewById(R.id.llFemale);

		ivFemale = (ImageView) findViewById(R.id.ivFemale);
		ivMale = (ImageView) findViewById(R.id.ivMale);

		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
		btnCancel.setOnClickListener(clickListener);
		btnLogin.setOnClickListener(clickListener);
		llMale.setOnClickListener(clickListener);
		llFemale.setOnClickListener(clickListener);

	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == btnLogin)
			{
				String firstName = etFirstName.getText().toString().trim();
				String lastName = etLastName.getText().toString().trim();
				String confirmPassword = etConfirmPassword.getText().toString().trim();
				String username = etUserName.getText().toString().trim();
				String password = etPassword.getText().toString().trim();
				String gender = "";
				// if (username.equals("") && password.equals("") &&
				// firstName.equals("") && lastName.equals("") &&
				// confirmPassword.equals(""))
				// {
				// etFirstName.requestFocus();
				// displayAlert(getResources().getString(R.string.alert_all_field_are_mandatory));
				// }
				if (ivMale.isSelected())
				{
					gender = "male";
				}

				if (ivFemale.isSelected())
				{
					gender = "female";
				}

				if (firstName.equals(""))
				{
					etFirstName.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_firstname));
				}
				else if (lastName.equals(""))
				{
					etLastName.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_lastname));
				}
				else if (username.equals(""))
				{
					etUserName.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_user_name));
				}
				else if (password.equals(""))
				{
					etPassword.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_password));
				}
				else if (confirmPassword.equals(""))
				{
					etConfirmPassword.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_confirm_password));
				}
				else if (!password.equals(confirmPassword))
				{
					etConfirmPassword.setText("");
					etConfirmPassword.requestFocus();
					displayAlert(getResources().getString(R.string.alert_password_and_confirm_password_not_same));
				}
				else if (gender.equals(""))
				{
					displayAlert(getResources().getString(R.string.alert_select_gender));
				}
				else
				{

					// Intent intent = new Intent(packageContext, cls)

					String requestString = Global.URL + "action=registerNewUser&firstName=" + firstName + "&lastName=" + lastName + "&email=" + username + "&password=" + password
							+ "&gender=" + gender;

					DataTask dataTask = new DataTask(SignUpActivity.this, requestString, getResources().getString(R.string.dialog_signup_sigin))
					{

						protected void onPostExecute(String result)
						{
							super.onPostExecute(result);
							try
							{
								LogM.w("result = " + result);
								JSONObject jsonObject = new JSONObject(result);

								if (jsonObject.getString("status").toString().trim().equals("success"))
								{
									Global.messageSuccessSignIn = jsonObject.getString("message");
									finish();
								}
								else
								{
									MyUtility.setAlertMessage(SignUpActivity.this, jsonObject.getString("message").toString());
								}

							}
							catch (JSONException e)
							{
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						};

						@Override
						protected void onCancelled()
						{
							super.onCancelled();
							MyUtility.setAlertMessage(SignUpActivity.this, Global.WebServiceFailMessage);
						};

					};

					if (MyUtility.checkInternetConnection(SignUpActivity.this))
					{
						dataTask.execute();
					}
					else
					{
						MyUtility.setInternetAlertMessage(SignUpActivity.this);
					}

				}
			}
			else if (v == llFemale)
			{

				ivFemale.setSelected(true);
				ivMale.setSelected(false);
			}
			else if (v == llMale)
			{
				ivFemale.setSelected(false);
				ivMale.setSelected(true);
			}
			else if (v == btnCancel)
			{
				finish();
			}
		}
	};

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(SignUpActivity.this);
		dialog.setTitle(getResources().getString(R.string.app_name));
		dialog.setMessage(message);
		dialog.setPositiveButton(getResources().getString(R.string.btn_ok), new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				return;
			}
		});

		dialog.show();

	}
}
