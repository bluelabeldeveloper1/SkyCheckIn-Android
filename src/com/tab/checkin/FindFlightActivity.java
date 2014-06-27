package com.tab.checkin;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.custom.MyUtility;
import com.skycheckin.HomeActivity;
import com.skycheckin.R;

public class FindFlightActivity extends Activity
{
	private EditText etFlightnumber, etDepaartureAirportCode, etarrivalAirportCode;
	private ImageView ivFindflightRoute, ivFindflightByairLine;
	private TextView tvAirlines, btnNext, etSelectDate;
	public static Activity activity;
	private int selectedPosition = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_flight);
		init();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = FindFlightActivity.this;

		etSelectDate = (TextView) findViewById(R.id.etSelectDate);
		etFlightnumber = (EditText) findViewById(R.id.etFlightnumber);
		etDepaartureAirportCode = (EditText) findViewById(R.id.etDepaartureAirportCode);
		etarrivalAirportCode = (EditText) findViewById(R.id.etarrivalAirportCode);
		ivFindflightRoute = (ImageView) findViewById(R.id.ivFindflightRoute);
		ivFindflightByairLine = (ImageView) findViewById(R.id.ivFindflightByairLine);
		tvAirlines = (TextView) findViewById(R.id.tvAirlines);
		btnNext = (TextView) findViewById(R.id.btnNext);
		ivFindflightByairLine.setSelected(true);

		ivFindflightByairLine.setOnClickListener(clickListener);
		ivFindflightRoute.setOnClickListener(clickListener);
		tvAirlines.setOnClickListener(clickListener);
		btnNext.setOnClickListener(clickListener);
		etSelectDate.setOnClickListener(clickListener);
	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == btnNext)
			{
				if (etSelectDate.getText().toString().equals(""))
				{
					MyUtility.setAlertMessage(FindFlightActivity.this, getResources().getString(R.string.alert_select_date));
				}
				else if (ivFindflightByairLine.isSelected() && selectedPosition == -1)
				{
					MyUtility.setAlertMessage(FindFlightActivity.this, getResources().getString(R.string.alert_select_airline));
				}
				else if (ivFindflightByairLine.isSelected() && etFlightnumber.getText().toString().equals(""))
				{
					MyUtility.setAlertMessage(FindFlightActivity.this, getResources().getString(R.string.alert_enter_flight_number));
				}
				else if (ivFindflightRoute.isSelected() && etDepaartureAirportCode.getText().toString().equals(""))
				{
					MyUtility.setAlertMessage(FindFlightActivity.this, getResources().getString(R.string.alert_enter_airport_code));
				}
				else if (ivFindflightRoute.isSelected() && etarrivalAirportCode.getText().toString().equals(""))
				{
					MyUtility.setAlertMessage(FindFlightActivity.this, getResources().getString(R.string.alert_enter_airport_code));
				}
				else
				{
					Intent intent = new Intent(FindFlightActivity.this, SelectFlightActivity.class);
					startActivity(intent);
				}

			}
			else if (v == ivFindflightByairLine)
			{
				ivFindflightRoute.setSelected(false);
				ivFindflightByairLine.setSelected(true);
				etarrivalAirportCode.setEnabled(false);
				etDepaartureAirportCode.setEnabled(false);
				etFlightnumber.setEnabled(true);
				tvAirlines.setEnabled(true);
			}
			else if (v == ivFindflightRoute)
			{
				ivFindflightRoute.setSelected(true);
				ivFindflightByairLine.setSelected(false);
				etarrivalAirportCode.setEnabled(true);
				etDepaartureAirportCode.setEnabled(true);
				etFlightnumber.setEnabled(false);
				tvAirlines.setEnabled(false);
			}
			else if (v == etSelectDate)
			{
				showToDateDialog(etSelectDate);
			}
			else if (v == tvAirlines)
			{
				MyUtility.hideSoftKeyboard(FindFlightActivity.this);
				DisplayAirLinesList();
			}

		}
	};

	private void DisplayAirLinesList()
	{

		final String airlines[] = new String[3];

		airlines[0] = "American Airlines";
		airlines[1] = "Indian Airlines";
		airlines[2] = "Canada Airlines";

		AlertDialog.Builder alert = new AlertDialog.Builder(FindFlightActivity.this);
		alert.setTitle(getResources().getString(R.string.alert_title_select_airline));
		alert.setSingleChoiceItems(airlines, (int) selectedPosition, new DialogInterface.OnClickListener()
		{

			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				selectedPosition = which;
				tvAirlines.setText(airlines[which]);
				dialog.dismiss();
			}
		});
		alert.setNegativeButton("Cancel", null);
		alert.show();

	}

	private void showToDateDialog(final TextView textView)
	{
		final Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);

		DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
		{
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
				textView.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year);

			}
		}, mYear, mMonth, mDay);
		dpd.show();
	}

}
