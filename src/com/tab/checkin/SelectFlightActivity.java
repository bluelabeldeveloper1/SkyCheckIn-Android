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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.skycheckin.HomeActivity;
import com.skycheckin.R;

public class SelectFlightActivity extends Activity
{
	private LinearLayout llFlight, llNoFlight;
	private FlightListAdapter flightListAdapter;
	private ListView lvFlight;
	public static Activity activity;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_flight);
		init();
	}
	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = SelectFlightActivity.this;
		
		llFlight = (LinearLayout) findViewById(R.id.llFlight);
		llNoFlight = (LinearLayout) findViewById(R.id.llNoFlight);

		lvFlight = (ListView) findViewById(R.id.lvFlight);

		flightListAdapter = new FlightListAdapter(SelectFlightActivity.this);
		lvFlight.setAdapter(flightListAdapter);

	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{

		}
	};

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

			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.row_select_flight, null);

				holder = new ViewHolder();
				holder.tvflightCode = (TextView) convertView.findViewById(R.id.tvflightCode);
				holder.tvDepTime = (TextView) convertView.findViewById(R.id.tvDepTime);
				holder.tvArrTime = (TextView) convertView.findViewById(R.id.tvArrTime);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(SelectFlightActivity.this, SelectSeatActivity.class);
					startActivity(intent);

				}
			});

			return convertView;
		}
	}

	private void requestForUserLogin()
	{
		Intent intent = new Intent(SelectFlightActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(SelectFlightActivity.this);
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
