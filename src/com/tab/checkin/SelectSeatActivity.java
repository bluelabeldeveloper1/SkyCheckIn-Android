package com.tab.checkin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.custom.FacebookHelper;
import com.custom.MyUtility;
import com.custom.FacebookHelper.FacebookHelperListerner;
import com.skycheckin.HomeActivity;
import com.skycheckin.LogInActivity;
import com.skycheckin.R;

public class SelectSeatActivity extends Activity
{
	private LinearLayout llFlight, llNoFlight, llSetOff, llSetOn;
	private FlightListAdapter flightListAdapter;
	private ListView lvFlight;
	private TextView btnCheckIn;
	private ImageView ivLogo, ivSeatSwitch, ivFacebook, ivTwitter;
	private TextView tvflightCode, tvDepTime, tvArrTime, tvSeat;
	public static Activity activity;
	int SelCharPosition = 0, oldChar = 0;
	int SelDigitPosition = 0, oldDigit = 0;
	String digit[] = new String[100];
	String Char[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	private FacebookHelper fbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_seat);
		init();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = SelectSeatActivity.this;
		btnCheckIn = (TextView) findViewById(R.id.btnCheckIn);
		ivLogo = (ImageView) findViewById(R.id.ivLogo);
		ivSeatSwitch = (ImageView) findViewById(R.id.ivSeatSwitch);
		ivFacebook = (ImageView) findViewById(R.id.ivFacebook);
		ivTwitter = (ImageView) findViewById(R.id.ivTwitter);

		tvflightCode = (TextView) findViewById(R.id.tvflightCode);
		tvDepTime = (TextView) findViewById(R.id.tvDepTime);
		tvArrTime = (TextView) findViewById(R.id.tvArrTime);
		tvSeat = (TextView) findViewById(R.id.tvSeat);

		llSetOff = (LinearLayout) findViewById(R.id.llSetOff);
		llSetOn = (LinearLayout) findViewById(R.id.llSetOn);

		ivFacebook.setOnClickListener(clickListener);
		ivTwitter.setOnClickListener(clickListener);
		ivSeatSwitch.setOnClickListener(clickListener);
		tvSeat.setOnClickListener(clickListener);
		btnCheckIn.setOnClickListener(clickListener);

		// llFlight = (LinearLayout) findViewById(R.id.llFlight);
		// llNoFlight = (LinearLayout) findViewById(R.id.llNoFlight);
		//
		// lvFlight = (ListView) findViewById(R.id.lvFlight);
		//
		// flightListAdapter = new
		// FlightListAdapter(SelectSeatTabActivity.this);
		// lvFlight.setAdapter(flightListAdapter);

	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == ivFacebook)
			{
				if (ivFacebook.isSelected())
				{
					ivFacebook.setSelected(false);
				}
				else
				{
					ivFacebook.setSelected(true);
				}
			}
			else if (v == ivTwitter)
			{
				if (ivTwitter.isSelected())
				{
					ivTwitter.setSelected(false);
				}
				else
				{
					ivTwitter.setSelected(true);
				}
			}
			else if (v == ivSeatSwitch)
			{
				if (ivSeatSwitch.isSelected())
				{
					ivSeatSwitch.setSelected(false);
					llSetOff.setVisibility(View.VISIBLE);
					llSetOn.setVisibility(View.GONE);

				}
				else
				{
					llSetOff.setVisibility(View.GONE);
					llSetOn.setVisibility(View.VISIBLE);

					ivSeatSwitch.setSelected(true);
				}
			}
			else if (v == tvSeat)
			{
				MultipleSpinner();
			}
			else if (v == btnCheckIn)
			{
				if (ivSeatSwitch.isSelected() && tvSeat.getText().toString().equals(""))
				{
					MyUtility.setAlertMessage(SelectSeatActivity.this, getResources().getString(R.string.alert_select_seat));
				}
				else
				{
					if (ivFacebook.isSelected())
					{
						fbHelper = new FacebookHelper(SelectSeatActivity.this);

						if (fbHelper.isLogin())
							fbHelper.logout();

						fbHelper.setFBHelperListerner(mFacebookHelperListerner);
						fbHelper.login(false);
					}
					else if (ivTwitter.isSelected())
					{

					}
					else
					{
						Intent intent = new Intent(SelectSeatActivity.this, DisplaySeatActivity.class);
						intent.putExtra("isCheckInSuccess", true);
						startActivity(intent);
					}

				}
			}
		}
	};

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
					showToast("Login Aborted!");
				}
				else if (result.equals(FacebookHelper.FRIENDLIST_ABORT_ERROR))
				{
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
					showToast("Imported Successfully.");
				}
				else if (result.equals(FacebookHelper.POST_FAIL))
				{
					showToast("Post Fail.");
				}
				else if (result.equals(FacebookHelper.POST_LOGIN_NOTFOUND))
				{
					showToast("Login not Found.");
				}
				else if (result.equals(FacebookHelper.POST_SUCESS))
				{
					showToast("Post Successfully.");
				}
			}
			else
			{
				showToast("Please try again!");
			}
		}

	};

	private void afterLogin()
	{
		fbHelper.postStatusUpdate("");
		if (ivTwitter.isSelected())
		{

		}
	}

	private void showToast(String msg)
	{
		Toast.makeText(SelectSeatActivity.this, msg, Toast.LENGTH_SHORT).show();
	}

	private void MultipleSpinner()
	{
		oldChar = SelCharPosition;
		oldDigit = SelDigitPosition;

		for (int i = 0; i < 100; i++)
		{
			digit[i] = "" + (i + 1);
		}

		LayoutInflater li = LayoutInflater.from(this);

		View promptsView = li.inflate(R.layout.dialog_seat_number, null);
		Spinner spdigit = (Spinner) promptsView.findViewById(R.id.spDigit);
		Spinner spChar = (Spinner) promptsView.findViewById(R.id.spChar);
		Button btnCancel = (Button) promptsView.findViewById(R.id.btnCancel);
		Button btnSet = (Button) promptsView.findViewById(R.id.btnSet);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setView(promptsView);
		alertDialogBuilder.setTitle(getResources().getString(R.string.txt_choose_seat));
		final AlertDialog alertDialog = alertDialogBuilder.create();

		ArrayAdapter<String> spinnerArrayAdapterDigit = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, digit);
		spinnerArrayAdapterDigit.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The
		spdigit.setAdapter(spinnerArrayAdapterDigit);
		spdigit.setSelection(SelDigitPosition);
		spdigit.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				SelDigitPosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});
		ArrayAdapter<String> spinnerArrayAdapterChar = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Char);
		spinnerArrayAdapterChar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The
		spChar.setAdapter(spinnerArrayAdapterChar);
		spChar.setSelection(SelCharPosition);
		spChar.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				SelCharPosition = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});

		btnCancel.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				SelCharPosition = oldChar;
				SelDigitPosition = oldDigit;
				alertDialog.dismiss();
			}
		});

		btnSet.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				tvSeat.setText(digit[SelDigitPosition] + Char[SelCharPosition]);
				alertDialog.dismiss();
			}
		});

		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(false);

	}

	private class FlightListAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;
		private Context context;

		public FlightListAdapter(Context context)
		{
			// Cache the LayoutInflate to avoid asking for a new one each time.
			mInflater = LayoutInflater.from(context);
			this.context = context;
		}

		private class ViewHolder
		{
			TextView tvflightCode, tvDepTime, tvArrTime;
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public String getItem(int position)
		{
			// TODO Auto-generated method stub
			return "";
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{

			ViewHolder holder;

			convertView = mInflater.inflate(R.layout.row_select_flight, null);

			holder = new ViewHolder();
			holder.tvflightCode = (TextView) convertView.findViewById(R.id.tvflightCode);
			holder.tvDepTime = (TextView) convertView.findViewById(R.id.tvDepTime);
			holder.tvArrTime = (TextView) convertView.findViewById(R.id.tvArrTime);

			// convertView.setTag(holder);

			convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

				}
			});

			return convertView;
		}
	}

	private void requestForUserLogin()
	{
		Intent intent = new Intent(SelectSeatActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(SelectSeatActivity.this);
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
