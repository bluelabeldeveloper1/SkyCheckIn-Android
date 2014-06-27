package com.tab.checkin;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.amazinglistview.AmazingAdapter;
import com.amazinglistview.AmazingListView;
import com.custom.ClassFlightDetail;
import com.custom.ClassFlightDetailDesc;
import com.skycheckin.HomeActivity;
import com.skycheckin.LogInActivity;
import com.skycheckin.MessageActivity;
import com.skycheckin.R;

public class OtherUserProfileActivity extends Activity
{
	// private Button btnLogout, btnEdit;
	private LinearLayout llHomeFeed;
	private ImageView ivActivity, ivProfile, ivCheckIn;
	private TextView tvUserName, tvFirstName, tvLastName, tvKm, tvUserDetail;
	private ImageView ivUserImage, ivchatSwitch, ivFacebookProfile;
	private AmazingListView alvHome;
	private ArrayList<ClassFlightDetail> arrayListFlightDetails = new ArrayList<ClassFlightDetail>();
	private ComposerAdapter adapter;
	private Button btnSendMessage;
	private TextView btnClose;

	public static Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other_user_profile);
		init();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = OtherUserProfileActivity.this;
		// Bottom Header
		ivActivity = (ImageView) findViewById(R.id.ivActivity);
		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		ivCheckIn = (ImageView) findViewById(R.id.ivCheckIn);
		btnSendMessage = (Button) findViewById(R.id.btnSendMessage);
		btnClose = (TextView) findViewById(R.id.btnClose);
		ivProfile.setSelected(true);
		ivActivity.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(OtherUserProfileActivity.this, HomeActivity.class);
				startActivity(intent);
			}
		});
		ivCheckIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(OtherUserProfileActivity.this, FindFlightActivity.class);
				startActivity(intent);
			}
		});
		ivProfile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// Intent intent = new Intent(UserProfileActivity.this,
				// UserProfileActivity.class);
				// startActivity(intent);
			}
		});

		// btnLogout = (Button) findViewById(R.id.btnLogout);
		// btnEdit = (Button) findViewById(R.id.btnEdit);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvFirstName = (TextView) findViewById(R.id.tvFirstName);
		tvKm = (TextView) findViewById(R.id.tvKm);
		tvUserDetail = (TextView) findViewById(R.id.tvUserDetail);

		llHomeFeed = (LinearLayout) findViewById(R.id.llHomeFeed);

		ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
		ivFacebookProfile = (ImageView) findViewById(R.id.ivFacebookProfile);
		ivchatSwitch = (ImageView) findViewById(R.id.ivchatSwitch);

		// btnLogout.setOnClickListener(clickListener);
		// btnEdit.setOnClickListener(clickListener);
		ivchatSwitch.setOnClickListener(clickListener);
		btnSendMessage.setOnClickListener(clickListener);
		btnClose.setOnClickListener(clickListener);

		alvHome = (AmazingListView) findViewById(R.id.alvHome);

		for (int i = 0; i < 2; i++)
		{
			ClassFlightDetail classFlightDetail = new ClassFlightDetail();

			classFlightDetail.header = "May 25, 2014";
			for (int j = 0; j < 2; j++)
			{
				ClassFlightDetailDesc classFlightDetailDesc = new ClassFlightDetailDesc();
				classFlightDetail.arrayListDescs.add(classFlightDetailDesc);

			}
			arrayListFlightDetails.add(classFlightDetail);
		}

		alvHome.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.row_user_flight_header, alvHome, false));
		alvHome.setAdapter(adapter = new ComposerAdapter());
	}

	OnClickListener clickListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			if (v == ivchatSwitch)
			{
				if (ivchatSwitch.isSelected())
				{
					ivchatSwitch.setSelected(false);
				}
				else
				{
					ivchatSwitch.setSelected(true);
				}

			}
			else if (v == btnSendMessage)
			{
				Intent intent = new Intent(OtherUserProfileActivity.this, MessageActivity.class);
				startActivity(intent);
			}
			else if (v == btnClose)
			{
				finish();
			}
			// else if (v == btnLogout)
			// {
			// CreateLogOutDialog(OtherUserProfileActivity.this);
			// }
		}
	};

	class ComposerAdapter extends AmazingAdapter
	{

		@Override
		public int getCount()
		{
			int res = 0;
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				res += arrayListFlightDetails.get(i).arrayListDescs.size();
			}
			return res;
		}

		@Override
		public ClassFlightDetailDesc getItem(int position)
		{
			int c = 0;
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				if (position >= c && position < c + arrayListFlightDetails.get(i).arrayListDescs.size())
				{
					return arrayListFlightDetails.get(i).arrayListDescs.get(position - c);
				}
				c += arrayListFlightDetails.get(i).arrayListDescs.size();
			}
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		protected void onNextPageRequested(int page)
		{
		}

		@Override
		protected void bindSectionHeader(View view, int position, boolean displaySectionHeader)
		{
			if (displaySectionHeader)
			{
				view.findViewById(R.id.header).setVisibility(View.VISIBLE);
				view.findViewById(R.id.ivLine).setVisibility(View.VISIBLE);

				TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
				lSectionTitle.setText(getSections()[getSectionForPosition(position)]);

			}
			else
			{
				view.findViewById(R.id.header).setVisibility(View.GONE);
				view.findViewById(R.id.ivLine).setVisibility(View.GONE);

			}
		}

		private class ViewHolder
		{
			TextView tvUserName, tvFlightNumber, tvDate, tvArrDep, tvSeatNumber;
			ImageView ivAirlineImage;
		}

		@Override
		public View getAmazingView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.row_user_flight_other_user, null);

				// holder.tvUserName = (TextView)
				// convertView.findViewById(R.id.tvUserName);
				// holder.tvFlightNumber = (TextView)
				// convertView.findViewById(R.id.tvFlightNumber);
				// holder.tvDate = (TextView)
				// convertView.findViewById(R.id.tvDate);
				// holder.tvArrDep = (TextView)
				// convertView.findViewById(R.id.tvArrDep);
				// holder.ivAirlineImage = (ImageView)
				// convertView.findViewById(R.id.ivAirlineImage);
				// holder.tvSeatNumber = (TextView)
				// convertView.findViewById(R.id.tvSeatNumber);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			// ClassHomeFeedDetail classHomeFeedDetail = getItem(position);

			convertView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(OtherUserProfileActivity.this, DisplaySeatActivity.class);
					intent.putExtra("isCheckInSuccess", false);
					startActivity(intent);
				}
			});

			return convertView;
		}

		@Override
		public void configurePinnedHeader(View header, int position, int alpha)
		{
			TextView lSectionHeader = (TextView) header.findViewById(R.id.header);
			lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
			// lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
			// lSectionHeader.setTextColor(alpha << 24 | (0x000000));
		}

		@Override
		public int getPositionForSection(int section)
		{
			if (section < 0)
				section = 0;
			if (section >= arrayListFlightDetails.size())
				section = arrayListFlightDetails.size() - 1;
			int c = 0;
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				if (section == i)
				{
					return c;
				}
				c += arrayListFlightDetails.get(i).arrayListDescs.size();
			}
			return 0;
		}

		@Override
		public int getSectionForPosition(int position)
		{
			int c = 0;
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				if (position >= c && position < c + arrayListFlightDetails.get(i).arrayListDescs.size())
				{
					return i;
				}
				c += arrayListFlightDetails.get(i).arrayListDescs.size();
			}
			return -1;
		}

		@Override
		public String[] getSections()
		{
			String[] res = new String[arrayListFlightDetails.size()];
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				res[i] = arrayListFlightDetails.get(i).header;
			}
			return res;
		}

	}

	private void requestForUserSignUp()
	{
		Intent intent = new Intent(OtherUserProfileActivity.this, HomeActivity.class);
		startActivity(intent);
		finish();
	}

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(OtherUserProfileActivity.this);
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

	public static void CreateLogOutDialog(final Activity context)
	{
		final Dialog dialog = new Dialog(context, R.style.myDialogTheme);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_log_out, null);

		// view.setAnimation(null);

		dialog.setContentView(view);

		final Button btnLogOut = (Button) view.findViewById(R.id.btnLogOut);
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
				else if (v == btnLogOut)
				{
					LogInActivity.FinishActivity();
					Intent intent = new Intent(context, LogInActivity.class);
					context.startActivity(intent);
					context.finish();
					dialog.dismiss();
				}
			}
		};

		btnCancel.setOnClickListener(listener);
		btnLogOut.setOnClickListener(listener);

		dialog.show();
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

	}

}
