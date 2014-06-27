package com.skycheckin;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.custom.ClassUserFacebookSignIn;
import com.custom.FacebookHelper;
import com.custom.FacebookHelper.FacebookHelperListerner;
import com.custom.Global;
import com.custom.LogM;
import com.custom.MySharedPrefrence;
import com.custom.MyUtility;
import com.datatask.DataTask;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.skycheckin.R.bool;

public class LogInActivity extends Activity
{
	private TextView tvForgotPassword;
	private Button btnSignInFacebook, btnNewUser, btnLogin, btnSignInEmail;
	private LinearLayout llEmailSign;
	private EditText etUserName, etPassword;
	public static Activity activity;
	private FacebookHelper fbHelper;
	ProgressDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	public void setUserInteraction(boolean interaction)
	{
		btnSignInFacebook.setEnabled(interaction);
		btnNewUser.setEnabled(interaction);
		btnLogin.setEnabled(interaction);
		btnSignInEmail.setEnabled(interaction);
		tvForgotPassword.setEnabled(interaction);
	}

	private void init()
	{
		activity = LogInActivity.this;

		mDialog = new ProgressDialog(LogInActivity.this);
		mDialog.setMessage(getResources().getString(R.string.dialog_signup_sigin));
		mDialog.setCanceledOnTouchOutside(false);

		tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);
		tvForgotPassword.setPaintFlags(tvForgotPassword.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		btnNewUser = (Button) findViewById(R.id.btnNewUser);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnSignInEmail = (Button) findViewById(R.id.btnSignInEmail);
		btnSignInFacebook = (Button) findViewById(R.id.btnSignInFacebook);
		llEmailSign = (LinearLayout) findViewById(R.id.llEmailSign);

		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);

		btnLogin.setOnClickListener(clickListener);
		btnNewUser.setOnClickListener(clickListener);
		btnSignInEmail.setOnClickListener(clickListener);
		btnSignInFacebook.setOnClickListener(clickListener);
		tvForgotPassword.setOnClickListener(clickListener);

		setUserInteraction(true);
	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == btnLogin)
			{
				setUserInteraction(false);
				String username = etUserName.getText().toString().trim();
				String password = etPassword.getText().toString().trim();

				// if (username.equals("") && password.equals(""))
				// {
				// displayAlert(getResources().getString(R.string.alert_all_field_are_mandatory));
				// }

				if (username.equals(""))
				{
					MyUtility.setAlertMessage(LogInActivity.this, getResources().getString(R.string.alert_enter_user_name));
					setUserInteraction(true);
				}
				else if (password.equals(""))
				{
					MyUtility.setAlertMessage(LogInActivity.this, getResources().getString(R.string.alert_enter_password));
					setUserInteraction(true);
				}
				else
				{
					// MySharedPrefrence.setFacebookLogIn(LogInActivity.this,
					// false);
					// MySharedPrefrence.setUserId(LogInActivity.this, 1);
					// Intent intent = new Intent(LogInActivity.this,
					// HomeActivity.class);
					// startActivity(intent);
					// finish();

					String requestString = Global.URL + "action=login&user=" + username + "&password=" + password + "&deviceToken=" + Global.DeviceTokenForGCM;

					DataTask dataTask = new DataTask(LogInActivity.this, requestString, true, true, mDialog)
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
									MySharedPrefrence.setFacebookLogIn(LogInActivity.this, false);
									MySharedPrefrence.setUserId(LogInActivity.this, jsonObject.getLong("userID"));
									MySharedPrefrence.setSessionId(LogInActivity.this, jsonObject.getString("sessionId"));
									requestForImageBaseUrl();
									// Intent intent = new
									// Intent(LogInActivity.this,
									// HomeActivity.class);
									// startActivity(intent);
									// finish();
								}
								else
								{
									mDialog.dismiss();
									MyUtility.setAlertMessage(LogInActivity.this, jsonObject.getString("message").toString());
								}
								setUserInteraction(true);

							}
							catch (JSONException e)
							{
								// TODO Auto-generated catch block
								mDialog.dismiss();
								setUserInteraction(true);
								e.printStackTrace();
							}

						};

						@Override
						protected void onCancelled()
						{
							super.onCancelled();
							mDialog.dismiss();
							MyUtility.setAlertMessage(LogInActivity.this, Global.WebServiceFailMessage);
							setUserInteraction(true);
						};

					};

					if (MyUtility.checkInternetConnection(LogInActivity.this))
					{
						dataTask.execute();
					}
					else
					{
						MyUtility.setInternetAlertMessage(LogInActivity.this);
						setUserInteraction(true);
					}

				}
			}
			else if (v == btnNewUser)
			{
				setUserInteraction(false);
				Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
				startActivity(intent);
				setUserInteraction(true);
			}
			else if (v == btnSignInEmail)
			{
				llEmailSign.setVisibility(View.VISIBLE);
				btnSignInEmail.setVisibility(View.GONE);
			}
			else if (v == btnSignInFacebook)
			{
				setUserInteraction(false);
				fbHelper = new FacebookHelper(LogInActivity.this);

				if (fbHelper.isLogin())
					fbHelper.logout();

				fbHelper.setFBHelperListerner(mFacebookHelperListerner);
				fbHelper.login(false);
			}
			else if (v == tvForgotPassword)
			{
				setUserInteraction(false);
				CreateForgotPasswordDialog(LogInActivity.this);
			}
		}
	};

	@Override
	protected void onResume()
	{
		super.onResume();
		if (!Global.messageSuccessSignIn.equals(""))
		{
			MyUtility.setAlertMessage(LogInActivity.this, Global.messageSuccessSignIn);
			Global.messageSuccessSignIn = "";
		}
	};

	@Override
	public void onStart()
	{
		super.onStart();
		if (fbHelper != null)
			fbHelper.onStart();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		if (fbHelper != null)
			fbHelper.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		if (fbHelper != null)
			fbHelper.onSavedInstanceState(outState);
	}

	private FacebookHelperListerner mFacebookHelperListerner = new FacebookHelperListerner()
	{

		@Override
		public void onComplete(String result, int countMerge)
		{
			// TODO Auto-generated method stub
			if (result != null)
			{
				if (result.equals(FacebookHelper.LOGIN_ABORT_ERROR))
				{
					setUserInteraction(true);
					showToast("Login Aborted!");
				}
				else if (result.equals(FacebookHelper.FRIENDLIST_ABORT_ERROR))
				{
					setUserInteraction(true);
					showToast("Import Aborted!");
				}
				else if (result.equals(FacebookHelper.LOGIN_SUCESS))
				{
					// showToast("Login Sucess!");
					afterLogin();
				}
				else if (result.equals(FacebookHelper.LOGOUT_SUCESS))
				{
					// showToast("Logout Sucess!");
				}
				else if (result.equals(FacebookHelper.FRIENDLIST_SUCESS))
				{
					setUserInteraction(true);
					showToast("Imported Successfully.");
				}
				else if (result.equals(FacebookHelper.POST_FAIL))
				{
					setUserInteraction(true);
					showToast("Post Fail.");
				}
				else if (result.equals(FacebookHelper.POST_LOGIN_NOTFOUND))
				{
					setUserInteraction(true);
					showToast("Login not Found.");
				}
				else if (result.equals(FacebookHelper.POST_SUCESS))
				{
					setUserInteraction(true);
					showToast("Post Successfully.");
				}
			}
			else
			{
				setUserInteraction(true);
				showToast("Please try again!");
			}
		}
	};

	public void afterLogin()
	{
		Log.e("log_tag", "afterLogin");
		Request request = Request.newMeRequest(Session.getActiveSession(), new GraphUserCallback()
		{
			@Override
			public void onCompleted(GraphUser user, Response response)
			{

				ClassUserFacebookSignIn classUserFacebookSignIn = new ClassUserFacebookSignIn();
				classUserFacebookSignIn.first_name = user.getFirstName();
				classUserFacebookSignIn.last_name = user.getLastName();
				classUserFacebookSignIn.facebook_id = user.getId();
				classUserFacebookSignIn.email = "" + user.getProperty("email");
				classUserFacebookSignIn.birth_date = "" + user.getBirthday();
				classUserFacebookSignIn.gender = "" + user.getProperty("gender");

				Session session = Session.getActiveSession();
				if (session != null && session.getState().isOpened())
				{
					classUserFacebookSignIn.facebook_access_token = session.getAccessToken();
				}

				// classUserFacebookSignIn.image_url =
				// "http://graph.facebook.com/" +
				// classUserFacebookSignIn.facebook_id + "/picture";

				requsetForFacebookSigIn(classUserFacebookSignIn);
			}
		});
		request.executeAsync();
	}

	public void CreateForgotPasswordDialog(final Activity context)
	{
		final Dialog dialog = new Dialog(context);
		dialog.setCancelable(false);
		dialog.setTitle(context.getResources().getString(R.string.txt_forgot_password));
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_forgot_password, null);

		// view.setAnimation(null);

		dialog.setContentView(view);

		final EditText editText = (EditText) view.findViewById(R.id.etEmail);
		final Button btnSend = (Button) view.findViewById(R.id.btnSend);
		final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

		OnClickListener listener = new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (v == btnCancel)
				{
					setUserInteraction(true);
					dialog.dismiss();
				}
				else if (v == btnSend)
				{
					if (editText.getText().toString().trim().equals(""))
					{
						MyUtility.setAlertMessage(context, context.getResources().getString(R.string.alert_enter_email_id));
					}
					else
					{
						String requestString = Global.URL + "action=sendPassword&email=" + editText.getText().toString().trim();

						DataTask dataTask = new DataTask(context, requestString, context.getResources().getString(R.string.dialog_please_wait))
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
										dialog.dismiss();
										MyUtility.setAlertMessage(context, jsonObject.getString("message").toString());
									}
									else
									{
										MyUtility.setAlertMessage(context, jsonObject.getString("message").toString());
									}
									setUserInteraction(true);
								}
								catch (JSONException e)
								{
									// TODO Auto-generated catch block
									e.printStackTrace();
									setUserInteraction(true);
								}

							};

							@Override
							protected void onCancelled()
							{
								super.onCancelled();
								MyUtility.setAlertMessage(context, Global.WebServiceFailMessage);
								setUserInteraction(true);
							};

						};

						if (MyUtility.checkInternetConnection(context))
						{
							dataTask.execute();
						}
						else
						{
							MyUtility.setInternetAlertMessage(context);
							setUserInteraction(true);
						}
					}
				}
			}
		};

		btnCancel.setOnClickListener(listener);
		btnSend.setOnClickListener(listener);

		dialog.show();
		// dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
		// LayoutParams.MATCH_PARENT);

	}

	private void requestForImageBaseUrl()
	{

		String requestString = Global.URL + "action=getPictureBaseURL&PHPSESSID=" + MySharedPrefrence.getSessionId(LogInActivity.this);

		DataTask dataTask = new DataTask(LogInActivity.this, requestString, true, false, mDialog)
		{

			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				setUserInteraction(true);
				LogM.w("result = " + result);

				MySharedPrefrence.setImageBaseUrl(LogInActivity.this, result);
				Global.imageBaseUrl = result;

				Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();

			};

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				setUserInteraction(true);
				mDialog.dismiss();
				MyUtility.setAlertMessage(LogInActivity.this, Global.WebServiceFailMessage);
			};

		};

		if (MyUtility.checkInternetConnection(LogInActivity.this))
		{
			dataTask.execute();
		}
		else
		{
			setUserInteraction(true);
			mDialog.dismiss();
			MyUtility.setInternetAlertMessage(LogInActivity.this);
		}
	}

	private void requsetForFacebookSigIn(ClassUserFacebookSignIn classUserFacebookSignIn)
	{

		LogM.d("FirstName :- " + classUserFacebookSignIn.first_name);
		LogM.d("LastName :- " + classUserFacebookSignIn.last_name);
		LogM.d("Access token :- " + classUserFacebookSignIn.facebook_access_token);
		LogM.d("birth date :- " + classUserFacebookSignIn.birth_date);
		LogM.d("gender :- " + classUserFacebookSignIn.gender);

		String requestString = Global.URL + "action=loginFacebook&facebookID=" + classUserFacebookSignIn.facebook_id + "&firstName=" + classUserFacebookSignIn.first_name
				+ "&lastName=" + classUserFacebookSignIn.last_name + "&email=" + classUserFacebookSignIn.email + "&gender=" + classUserFacebookSignIn.gender + "&birthDate="
				+ classUserFacebookSignIn.birth_date + "&deviceToken=" + Global.DeviceTokenForGCM;

		DataTask dataTask = new DataTask(LogInActivity.this, requestString, true, true, mDialog)
		{

			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				try
				{
					setUserInteraction(true);
					LogM.w("result = " + result);
					JSONObject jsonObject = new JSONObject(result);

					if (jsonObject.getString("status").toString().trim().equals("success"))
					{

						MySharedPrefrence.setFacebookLogIn(LogInActivity.this, true);
						MySharedPrefrence.setUserId(LogInActivity.this, jsonObject.getLong("userID"));
						MySharedPrefrence.setSessionId(LogInActivity.this, jsonObject.getString("sessionId"));
						requestForImageBaseUrl();
						// Intent intent = new Intent(LogInActivity.this,
						// HomeActivity.class);
						// startActivity(intent);
						// finish();
					}
					else
					{

						MyUtility.setAlertMessage(LogInActivity.this, jsonObject.getString("message").toString());
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
				setUserInteraction(true);
				MyUtility.setAlertMessage(LogInActivity.this, Global.WebServiceFailMessage);
			};

		};

		if (MyUtility.checkInternetConnection(LogInActivity.this))
		{
			dataTask.execute();
		}
		else
		{
			setUserInteraction(true);
			MyUtility.setInternetAlertMessage(LogInActivity.this);
		}

	}

	private void showToast(String msg)
	{
		Toast.makeText(LogInActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (fbHelper != null)
			fbHelper.onActivityResult(requestCode, resultCode, data);

	}

}
