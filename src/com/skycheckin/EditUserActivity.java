package com.skycheckin;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.custom.Global;
import com.custom.LogM;
import com.custom.MySharedPrefrence;
import com.custom.MyUtility;
import com.datatask.DataTask;
import com.datatask.DataTaskUploadImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditUserActivity extends Activity
{
	private TextView tvCancle, tvSave, tvChangeProfilePic, tvRemoveProfilePic;
	private LinearLayout llMale, llFemale, llFacebookLogin;
	private EditText etFirstName, etLastName, etEmailAddress, etPersonalMessage, etPassword;
	private ImageView ivMale, ivFemale, ivUserPic1, ivUserPic2, ivUserPic3, ivUserPic4, ivUserPic5, ivPicSelection1, ivPicSelection2, ivPicSelection3, ivPicSelection4,
			ivPicSelection5, ivUserPicMain;
	private boolean isFacebookLogIn = false;
	private AlertDialog myDialog;
	private String[] items = { "", "" };
	private int REQUEST_CAMERA = 101;
	private int SELECT_FILE = 102;
	private int selectedImagePos;
	private File imageFile;
	private Bitmap userBitmap;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_user);
		init();
	}

	private void init()
	{
		tvCancle = (TextView) findViewById(R.id.tvCancle);
		tvSave = (TextView) findViewById(R.id.tvSave);
		tvChangeProfilePic = (TextView) findViewById(R.id.tvChangeProfilePic);
		tvRemoveProfilePic = (TextView) findViewById(R.id.tvRemoveProfilePic);
		// tvPassword = (TextView) findViewById(R.id.tvPassword);
		llMale = (LinearLayout) findViewById(R.id.llMale);
		llFemale = (LinearLayout) findViewById(R.id.llFemale);
		llFacebookLogin = (LinearLayout) findViewById(R.id.llFacebookLogin);

		items[0] = "" + getResources().getString(R.string.txt_take_photo);
		items[1] = "" + getResources().getString(R.string.txt_gallery);

		ivFemale = (ImageView) findViewById(R.id.ivFemale);
		ivMale = (ImageView) findViewById(R.id.ivMale);
		ivUserPicMain = (ImageView) findViewById(R.id.ivUserPicMain);

		ivUserPic1 = (ImageView) findViewById(R.id.ivUserPic1);
		ivUserPic2 = (ImageView) findViewById(R.id.ivUserPic2);
		ivUserPic3 = (ImageView) findViewById(R.id.ivUserPic3);
		ivUserPic4 = (ImageView) findViewById(R.id.ivUserPic4);
		ivUserPic5 = (ImageView) findViewById(R.id.ivUserPic5);

		ivPicSelection1 = (ImageView) findViewById(R.id.ivPicSelection1);
		ivPicSelection2 = (ImageView) findViewById(R.id.ivPicSelection2);
		ivPicSelection3 = (ImageView) findViewById(R.id.ivPicSelection3);
		ivPicSelection4 = (ImageView) findViewById(R.id.ivPicSelection4);
		ivPicSelection5 = (ImageView) findViewById(R.id.ivPicSelection5);

		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etEmailAddress = (EditText) findViewById(R.id.etEmailAddress);
		etPassword = (EditText) findViewById(R.id.etPassword);
		// etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
		etPersonalMessage = (EditText) findViewById(R.id.etPersonalMessage);

		ivPicSelection1.setOnClickListener(clickListener);
		ivPicSelection2.setOnClickListener(clickListener);
		ivPicSelection3.setOnClickListener(clickListener);
		ivPicSelection4.setOnClickListener(clickListener);
		ivPicSelection5.setOnClickListener(clickListener);
		tvRemoveProfilePic.setOnClickListener(clickListener);
		tvChangeProfilePic.setOnClickListener(clickListener);

		tvCancle.setOnClickListener(clickListener);
		tvSave.setOnClickListener(clickListener);
		llMale.setOnClickListener(clickListener);
		llFemale.setOnClickListener(clickListener);
		etPassword.setOnClickListener(clickListener);

		isFacebookLogIn = MySharedPrefrence.getFacebookLogIn(EditUserActivity.this);
		if (isFacebookLogIn)
		{
			llFacebookLogin.setVisibility(View.GONE);
		}
		else
		{
			llFacebookLogin.setVisibility(View.VISIBLE);
		}
		setUserDetail();
	}

	private void setUserDetail()
	{
		etFirstName.setText("Charly");
		etLastName.setText("Wa");

		if (!MySharedPrefrence.getFacebookLogIn(EditUserActivity.this))
		{
			etEmailAddress.setText("charlywa@gmail.com");
		}
		etPassword.setText("123123");
		ivMale.setSelected(true);
		etPersonalMessage.setText("Non Plique \ndoloreped  \npro \nmagnis");

		ivUserPicMain.setImageResource(R.drawable.profile_pic);
		ivUserPic1.setImageResource(R.drawable.profile_pic);
		ivUserPic2.setImageResource(R.drawable.profile_pic);
		ivUserPic3.setImageResource(R.drawable.profile_pic);
		ivUserPic4.setImageResource(R.drawable.profile_pic);

		ivPicSelection1.setSelected(true);
		ivPicSelection2.setSelected(true);
		ivPicSelection3.setSelected(true);
		ivPicSelection4.setSelected(true);

	}

	public static void CreateChangePasswordDialog(final Activity context)
	{
		final Dialog dialog = new Dialog(context);
		dialog.setCancelable(false);
		dialog.setTitle(context.getResources().getString(R.string.txt_change_password));
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_password, null);

		// view.setAnimation(null);

		dialog.setContentView(view);

		final EditText etOldPassword = (EditText) view.findViewById(R.id.etOldPassword);
		final EditText etPassword = (EditText) view.findViewById(R.id.etPassword);
		final EditText etConfirmPassword = (EditText) view.findViewById(R.id.etConfirmPassword);
		final Button btnSend = (Button) view.findViewById(R.id.btnSend);
		final Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

		OnClickListener listener = new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (v == btnCancel)
				{
					dialog.dismiss();
				}
				else if (v == btnSend)
				{
					String old = etOldPassword.getText().toString().trim();
					String password = etPassword.getText().toString().trim();
					String confirmPassword = etConfirmPassword.getText().toString().trim();

					if (old.equals(""))
					{
						etOldPassword.requestFocus();
						MyUtility.setAlertMessage(context, context.getResources().getString(R.string.alert_enter_old_password));

					}
					else if (password.equals(""))
					{
						etPassword.requestFocus();
						MyUtility.setAlertMessage(context, context.getResources().getString(R.string.alert_enter_password));
					}
					else if (confirmPassword.equals(""))
					{
						etConfirmPassword.requestFocus();
						MyUtility.setAlertMessage(context, context.getResources().getString(R.string.alert_enter_confirm_password));
					}
					else if (!password.trim().equals(confirmPassword.trim()))
					{
						Log.w("log_tag", "New Password..." + password);
						Log.w("log_tag", "Confirm Password..." + confirmPassword);

						etConfirmPassword.setText("");
						etConfirmPassword.requestFocus();
						MyUtility.setAlertMessage(context, context.getResources().getString(R.string.alert_password_and_confirm_password_not_same));
					}
					else
					{
						String requestString = Global.URL + "action=resetPassword&oldPassword=" + old + "&newPassword=" + password + "&PHPSESSID="
								+ MySharedPrefrence.getSessionId(context);

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
								MyUtility.setAlertMessage(context, Global.WebServiceFailMessage);
							};

						};

						if (MyUtility.checkInternetConnection(context))
						{
							dataTask.execute();
						}
						else
						{
							MyUtility.setInternetAlertMessage(context);
						}
						dialog.dismiss();
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

	private void getImage()
	{

		AlertDialog.Builder myDialog = new AlertDialog.Builder(EditUserActivity.this);
		myDialog.setTitle(getResources().getString(R.string.txt_get_photo_title));
		// builder.setIcon(R.drawable.snowflake);
		myDialog.setItems(items, new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				if (which == 0)
				{

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					intent.putExtra(MediaStore.EXTRA_OUTPUT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
					// ******** code for crop image
					intent.putExtra("crop", "true");
					intent.putExtra("aspectX", 0);
					intent.putExtra("aspectY", 0);
					intent.putExtra("outputX", 150);
					intent.putExtra("outputY", 150);

					try
					{

						intent.putExtra("return-data", true);
						startActivityForResult(intent, REQUEST_CAMERA);

					}
					catch (ActivityNotFoundException e)
					{
						// Do nothing for now
					}

					// Intent intent = new
					// Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// File f = new
					// File(android.os.Environment.getExternalStorageDirectory(),
					// "temp.jpg");
					// intent.putExtra(MediaStore.EXTRA_OUTPUT,
					// Uri.fromFile(f));
					// intent.putExtra("crop", "true");
					// intent.putExtra("aspectX", 0);
					// intent.putExtra("aspectY", 0);
					// intent.putExtra("outputX", 200);
					// intent.putExtra("outputY", 150);
					// startActivityForResult(intent, REQUEST_CAMERA);
				}
				else
				{
					Intent intent = new Intent();
					// call android default gallery
					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					// ******** code for crop image
					intent.putExtra("crop", "true");
					intent.putExtra("aspectX", 0);
					intent.putExtra("aspectY", 0);
					intent.putExtra("outputX", 150);
					intent.putExtra("outputY", 150);

					try
					{

						intent.putExtra("return-data", true);
						startActivityForResult(Intent.createChooser(intent, "Complete action using"), SELECT_FILE);

					}
					catch (ActivityNotFoundException e)
					{
						// Do nothing for now

					}

					// Intent intent = new Intent(Intent.ACTION_PICK,
					// android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					// intent.setType("image/*");
					// intent.putExtra("crop", "true");
					// intent.putExtra("aspectX", 0);
					// intent.putExtra("aspectY", 0);
					// intent.putExtra("outputX", 200);
					// intent.putExtra("outputY", 150);
					// startActivityForResult(Intent.createChooser(intent,
					// "Select File"), SELECT_FILE);
				}
			}
		});

		myDialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			if (requestCode == REQUEST_CAMERA)
			{
				Bundle extras = data.getExtras();
				if (extras != null)
				{
					Bitmap photo = extras.getParcelable("data");
					imageFile = MyUtility.saveImageForShare(EditUserActivity.this, photo);
					requestForUploadUserImage(imageFile);
					userBitmap = photo;
					// setUserImage(photo);
				}
			}

			if (requestCode == SELECT_FILE)
			{
				Bundle extras2 = data.getExtras();
				if (extras2 != null)
				{
					Bitmap photo = extras2.getParcelable("data");
					imageFile = MyUtility.saveImageForShare(EditUserActivity.this, photo);
					requestForUploadUserImage(imageFile);
					userBitmap = photo;
					// setUserImage(photo);
				}

			}
		}

		// if (requestCode == REQUEST_CAMERA)
		// {
		// File f = new
		// File(Environment.getExternalStorageDirectory().toString());
		// for (File temp : f.listFiles())
		// {
		// if (temp.getName().equals("temp.jpg"))
		// {
		// f = temp;
		// break;
		// }
		// }
		// try
		// {
		// Bitmap bm;
		// BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
		//
		// bm = BitmapFactory.decodeFile(f.getAbsolutePath(), btmapOptions);
		//
		// // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
		// selectedImageView.setImageBitmap(bm);
		// if (selectedImageView != ivUserPicMain)
		// selectedImageViewForbtn.setSelected(true);
		//
		// String path =
		// android.os.Environment.getExternalStorageDirectory() +
		// File.separator + "Phoenix" + File.separator + "default";
		// f.delete();
		// OutputStream fOut = null;
		// File file = new File(path,
		// String.valueOf(System.currentTimeMillis()) + ".jpg");
		// try
		// {
		// fOut = new FileOutputStream(file);
		// bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
		// fOut.flush();
		// fOut.close();
		// }
		// catch (FileNotFoundException e)
		// {
		// e.printStackTrace();
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		// }
		// else if (requestCode == SELECT_FILE)
		// {
		// Uri selectedImageUri = data.getData();
		//
		// String tempPath = getPath(selectedImageUri,
		// EditUserActivity.this);
		// Bitmap bm;
		// BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
		// bm = BitmapFactory.decodeFile(tempPath, btmapOptions);
		// selectedImageView.setImageBitmap(bm);
		// if (selectedImageView != ivUserPicMain)
		// selectedImageViewForbtn.setSelected(true);
		// }
	}

	private void setUserImage(Bitmap bitmap)
	{
		if (selectedImagePos == 0)
		{
			ivUserPicMain.setImageBitmap(bitmap);

		}
		else if (selectedImagePos == 1)
		{
			ivUserPic1.setImageBitmap(bitmap);
			ivPicSelection1.setSelected(true);
		}
		else if (selectedImagePos == 2)
		{
			ivUserPic2.setImageBitmap(bitmap);
			ivPicSelection2.setSelected(true);
		}
		else if (selectedImagePos == 3)
		{
			ivUserPic3.setImageBitmap(bitmap);
			ivPicSelection3.setSelected(true);
		}
		else if (selectedImagePos == 4)
		{
			ivUserPic4.setImageBitmap(bitmap);
			ivPicSelection4.setSelected(true);
		}
		else if (selectedImagePos == 5)
		{
			ivUserPic5.setImageBitmap(bitmap);
			ivPicSelection5.setSelected(true);
		}
	}

	public String getPath(Uri uri, Activity activity)
	{
		String[] projection = { MediaColumns.DATA };
		Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == tvSave)
			{
				String firstName = etFirstName.getText().toString().trim();
				String lastName = etLastName.getText().toString().trim();
				// String confirmPassword =
				// etConfirmPassword.getText().toString().trim();
				String username = etEmailAddress.getText().toString().trim();
				// String password = etPassword.getText().toString().trim();
				String aboutUser = etPersonalMessage.getText().toString().trim();
				String gender = ivMale.isSelected() ? "M" : "F";
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
				else if (!isFacebookLogIn && username.equals(""))
				{
					etEmailAddress.requestFocus();
					displayAlert(getResources().getString(R.string.alert_enter_email_id));
				}
				// else if (!isFacebookLogIn && password.equals(""))
				// {
				// etPassword.requestFocus();
				// displayAlert(getResources().getString(R.string.alert_enter_password));
				// }
				// else if (!isFacebookLogIn && confirmPassword.equals(""))
				// {
				// etConfirmPassword.requestFocus();
				// displayAlert(getResources().getString(R.string.alert_enter_confirm_password));
				// }
				// else if (!isFacebookLogIn &&
				// !password.trim().equals(confirmPassword.trim()))
				// {
				// Log.w("log_tag", "New Password..." + password);
				// Log.w("log_tag", "Confirm Password..." + confirmPassword);
				//
				// etConfirmPassword.setText("");
				// etConfirmPassword.requestFocus();
				// displayAlert(getResources().getString(R.string.alert_password_and_confirm_password_not_same));
				// }
				else if (etPersonalMessage.equals(""))
				{
					etPersonalMessage.requestFocus();
					MyUtility.setAlertMessage(EditUserActivity.this, getResources().getString(R.string.alert_personal_detail));
				}
				else
				{
					String requestString = Global.URL + "action=updateUserDetails&firstName=" + etFirstName + "&lastName=" + etLastName + "&aboutMe=" + etPersonalMessage
							+ "&email=" + username + "&birthDate=&gender=" + gender + "&PHPSESSID=" + MySharedPrefrence.getSessionId(EditUserActivity.this);

					DataTask dataTask = new DataTask(EditUserActivity.this, requestString, getResources().getString(R.string.dialog_please_wait))
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
									setUserImage(userBitmap);
									AlertDialog.Builder dialog = new AlertDialog.Builder(EditUserActivity.this);
									dialog.setTitle(getResources().getString(R.string.app_name));
									dialog.setMessage(jsonObject.getString("message").toString());
									dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
									{

										@Override
										public void onClick(DialogInterface dialog, int which)
										{
											finish();
										}
									});
									dialog.show();

								}
								else
								{
									MyUtility.setAlertMessage(EditUserActivity.this, jsonObject.getString("message").toString());
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
							MyUtility.setAlertMessage(EditUserActivity.this, Global.WebServiceFailMessage);
						};

					};

					if (MyUtility.checkInternetConnection(EditUserActivity.this))
					{
						dataTask.execute();
					}
					else
					{
						MyUtility.setInternetAlertMessage(EditUserActivity.this);
					}

				}
			}

			if (v == llFemale)
			{

				ivFemale.setSelected(true);
				ivMale.setSelected(false);
			}
			else if (v == llMale)
			{
				ivFemale.setSelected(false);
				ivMale.setSelected(true);
			}
			else if (v == tvCancle)
			{
				finish();
			}
			else if (v == ivPicSelection1)
			{
				if (ivPicSelection1.isSelected())
				{
					ivPicSelection1.setSelected(false);
					ivUserPic1.setImageResource(0);
				}
				else
				{
					selectedImagePos = 1;
					getImage();

				}
			}
			else if (v == ivPicSelection2)
			{
				if (ivPicSelection2.isSelected())
				{
					ivPicSelection2.setSelected(false);
					ivUserPic2.setImageResource(0);
				}
				else
				{
					selectedImagePos = 2;
					getImage();
				}
			}
			else if (v == ivPicSelection3)
			{
				if (ivPicSelection3.isSelected())
				{
					ivPicSelection3.setSelected(false);
					ivUserPic3.setImageResource(0);
				}
				else
				{
					selectedImagePos = 3;
					getImage();
				}
			}
			else if (v == ivPicSelection4)
			{
				if (ivPicSelection4.isSelected())
				{
					ivPicSelection4.setSelected(false);
					ivUserPic4.setImageResource(0);
				}
				else
				{
					selectedImagePos = 4;
					getImage();
				}
			}
			else if (v == ivPicSelection5)
			{
				if (ivPicSelection5.isSelected())
				{
					ivPicSelection5.setSelected(false);
					ivUserPic5.setImageResource(0);
				}
				else
				{
					selectedImagePos = 5;
					getImage();
				}
			}
			else if (v == tvChangeProfilePic)
			{

				selectedImagePos = 0;
				getImage();
			}
			else if (v == tvRemoveProfilePic)
			{
				ivUserPicMain.setImageResource(0);
			}
			else if (v == etPassword)
			{
				CreateChangePasswordDialog(EditUserActivity.this);
			}
		}
	};

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(EditUserActivity.this);
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

	private void requestForUploadUserImage(File file)
	{

		DataTaskUploadImage dataTask = new DataTaskUploadImage(EditUserActivity.this, file, getResources().getString(R.string.dialog_please_wait))
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
						AlertDialog.Builder dialog = new AlertDialog.Builder(EditUserActivity.this);
						dialog.setTitle(getResources().getString(R.string.app_name));
						dialog.setMessage(jsonObject.getString("message").toString());
						dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								finish();
							}
						});
						dialog.show();

					}
					else
					{
						MyUtility.setAlertMessage(EditUserActivity.this, jsonObject.getString("message").toString());
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
				MyUtility.setAlertMessage(EditUserActivity.this, Global.WebServiceFailMessage);
			};

		};

		if (MyUtility.checkInternetConnection(EditUserActivity.this))
		{
			dataTask.execute();
		}
		else
		{
			MyUtility.setInternetAlertMessage(EditUserActivity.this);
		}

	}

}
