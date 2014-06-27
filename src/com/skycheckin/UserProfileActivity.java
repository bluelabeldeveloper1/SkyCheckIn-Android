package com.skycheckin;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.custom.ClassFlightDetail;
import com.custom.ClassFlightDetailDesc;
import com.custom.Global;
import com.custom.LogM;
import com.custom.MySharedPrefrence;
import com.custom.MyUtility;
import com.datatask.DataTask;
import com.swipelistview.SwipeListView;
import com.tab.checkin.DisplaySeatActivity;
import com.tab.checkin.FindFlightActivity;

public class UserProfileActivity extends Activity
{
	private TextView btnLogout, btnEdit;
	private LinearLayout llHomeFeed;
	private ImageView ivActivity, ivProfile, ivCheckIn;
	private TextView tvUserName, tvFirstName, tvLastName, tvKm, tvUserDetail;
	private ImageView ivUserImage, ivchatSwitch, ivFacebookProfile;
	private SwipeListView alvHome;
	private ArrayList<ClassFlightDetail> arrayListFlightDetails = new ArrayList<ClassFlightDetail>();
	private ComposerAdapter adapter;
	private boolean userInteraction = true;

	// public int swipedPos = -1;
	// public SwipeType swipeType = SwipeType.NONE;
	// private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		init();
	}

	// private enum SwipeType
	// {
	// NONE, LEFT, RIGHT
	// };

	public void setUserInteraction(boolean interaction)
	{
		btnLogout.setEnabled(interaction);
		btnEdit.setEnabled(interaction);
		ivUserImage.setEnabled(interaction);
		ivchatSwitch.setEnabled(interaction);
		ivFacebookProfile.setEnabled(interaction);
		ivActivity.setEnabled(interaction);
		ivProfile.setEnabled(interaction);
		ivCheckIn.setEnabled(interaction);
		userInteraction = interaction;
	}

	private void init()
	{
		// Bottom Header
		ivActivity = (ImageView) findViewById(R.id.ivActivity);
		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		ivCheckIn = (ImageView) findViewById(R.id.ivCheckIn);

		ivProfile.setSelected(true);
		ivActivity.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		ivCheckIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(UserProfileActivity.this, FindFlightActivity.class);
				startActivity(intent);
			}
		});

		btnLogout = (TextView) findViewById(R.id.btnLogout);
		btnEdit = (TextView) findViewById(R.id.btnEdit);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvFirstName = (TextView) findViewById(R.id.tvFirstName);
		tvKm = (TextView) findViewById(R.id.tvKm);
		tvUserDetail = (TextView) findViewById(R.id.tvUserDetail);

		llHomeFeed = (LinearLayout) findViewById(R.id.llHomeFeed);

		ivUserImage = (ImageView) findViewById(R.id.ivUserImage);
		ivFacebookProfile = (ImageView) findViewById(R.id.ivFacebookProfile);
		ivchatSwitch = (ImageView) findViewById(R.id.ivchatSwitch);

		btnLogout.setOnClickListener(clickListener);
		btnEdit.setOnClickListener(clickListener);
		ivchatSwitch.setOnClickListener(clickListener);

		alvHome = (SwipeListView) findViewById(R.id.alvHome);

		for (int i = 1; i < 2; i++)
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
		// gestureDetector = new GestureDetector(UserProfileActivity.this, new
		// SwipeGestureDetector());

		alvHome.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.row_user_flight_header, alvHome, false));
		// alvHome.setOnTouchListener(gestureListener);

		alvHome.setAdapter(adapter = new ComposerAdapter());

		// alvHome.setOnItemClickListener(new OnItemClickListener()
		// {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id)
		// {
		// Intent intent = new Intent(UserProfileActivity.this,
		// DisplaySeatActivity.class);
		// intent.putExtra("isCheckInSuccess", false);
		// startActivity(intent);
		// }
		// });

		setUserInteraction(true);
		requestForGetDetail();
	}

	private void requestForGetDetail()
	{
		String requestString = Global.URL + "action=getFullUser&id=" + MySharedPrefrence.getUserId(UserProfileActivity.this) + "&PHPSESSID="
				+ MySharedPrefrence.getSessionId(UserProfileActivity.this);

		DataTask dataTask = new DataTask(UserProfileActivity.this, requestString, getResources().getString(R.string.dialog_please_wait))
		{

			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);

				try
				{
					setUserInteraction(true);
					LogM.w("result = " + result);
					JSONObject jsonObject = new JSONObject(result);

					if (jsonObject.getString("status").equals("success"))
					{
						setValue();
					}
					else
					{
						MyUtility.setAlertMessage(UserProfileActivity.this, jsonObject.getString("message").toString());
					}

				}
				catch (JSONException e)
				{
					// TODO Auto-generated catch block
					setUserInteraction(true);
					e.printStackTrace();
				}

			};

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				setUserInteraction(true);
				MyUtility.setAlertMessage(UserProfileActivity.this, Global.WebServiceFailMessage);
			};

		};

		if (MyUtility.checkInternetConnection(UserProfileActivity.this))
		{
			dataTask.execute();
		}
		else
		{
			setUserInteraction(true);
			MyUtility.setInternetAlertMessage(UserProfileActivity.this);
		}

	}

	private void setValue()
	{

	}

	// private class SwipeGestureDetector extends SimpleOnGestureListener
	// {
	// private static final int SWIPE_MIN_DISTANCE = 120;
	// private static final int SWIPE_MAX_OFF_PATH = 200;
	// private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	//
	// @Override
	// public boolean onSingleTapUp(MotionEvent e)
	// {
	//
	// int position = alvHome.pointToPosition((int) e.getX(), (int) e.getY());
	// if (position >= 0)
	// {
	// onSingleTap(position);
	// }
	// return super.onSingleTapUp(e);
	// }
	//
	// @Override
	// public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	// float velocityY)
	// {
	// try
	// {
	// float diffAbs = Math.abs(e1.getY() - e2.getY());
	// float diff = e1.getX() - e2.getX();
	//
	// if (diffAbs > SWIPE_MAX_OFF_PATH)
	// return false;
	//
	// // Left swipe
	// if (diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >
	// SWIPE_THRESHOLD_VELOCITY)
	// {
	// onLeftSwipe(e1, e2);
	// // Right swipe
	// }
	// else if (-diff > SWIPE_MIN_DISTANCE && Math.abs(velocityX) >
	// SWIPE_THRESHOLD_VELOCITY)
	// {
	// onRightSwipe(e1, e2);
	// }
	// }
	// catch (Exception e)
	// {
	// Log.e("YourActivity", "Error on gestures");
	// }
	// return false;
	// }
	// }
	//
	// private void onLeftSwipe(MotionEvent e1, MotionEvent e2)
	// {
	// int pos = alvHome.pointToPosition((int) e1.getX(), (int) e1.getY());
	// swipedPos = pos;
	// adapter.notifyDataSetChanged();
	// Log.i("log_tag", "swipe left: " + pos);
	// }
	//
	// private void onRightSwipe(MotionEvent e1, MotionEvent e2)
	// {
	// int pos = alvHome.pointToPosition((int) e1.getX(), (int) e1.getY());
	// swipedPos = pos;
	// adapter.notifyDataSetChanged();
	// Log.i("log_tag", "swipe right");
	// }
	//
	// private View.OnTouchListener gestureListener = new View.OnTouchListener()
	// {
	//
	// @Override
	// public boolean onTouch(View view, MotionEvent event)
	// {
	// return gestureDetector.onTouchEvent(event);
	// }
	// };
	//
	// private void onSingleTap(int position)
	// {
	//
	// if (swipedPos > -1)
	// {
	// swipedPos = -1;
	// adapter.notifyDataSetChanged();
	// }
	// else
	// {
	// // Click
	// }
	//
	// }

	OnClickListener clickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (v == ivchatSwitch)
			{
				setUserInteraction(false);
				String msg = ivchatSwitch.isSelected() ? "1" : "0";

				String requestString = Global.URL + "action=setMessageVisibility&status=" + msg + "&PHPSESSID=" + MySharedPrefrence.getSessionId(UserProfileActivity.this);

				DataTask dataTask = new DataTask(UserProfileActivity.this, requestString, getResources().getString(R.string.dialog_please_wait))
				{

					protected void onPostExecute(String result)
					{
						super.onPostExecute(result);

						try
						{
							setUserInteraction(true);
							LogM.w("result = " + result);
							JSONObject jsonObject = new JSONObject(result);

							if (jsonObject.getString("status").equals("success"))
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
							MyUtility.setAlertMessage(UserProfileActivity.this, jsonObject.getString("message").toString());

						}
						catch (JSONException e)
						{
							// TODO Auto-generated catch block
							setUserInteraction(true);
							e.printStackTrace();
						}

					};

					@Override
					protected void onCancelled()
					{
						super.onCancelled();
						setUserInteraction(true);
						MyUtility.setAlertMessage(UserProfileActivity.this, Global.WebServiceFailMessage);
					};

				};

				if (MyUtility.checkInternetConnection(UserProfileActivity.this))
				{
					dataTask.execute();
				}
				else
				{
					setUserInteraction(true);
					MyUtility.setInternetAlertMessage(UserProfileActivity.this);
				}

			}
			else if (v == btnLogout)
			{
				setUserInteraction(false);
				CreateLogOutDialog(UserProfileActivity.this);
			}
			else if (v == btnEdit)
			{

				Intent intent = new Intent(UserProfileActivity.this, EditUserActivity.class);
				startActivity(intent);
			}
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

		public int getChildItemPosition(int position)
		{
			int c = 0;
			for (int i = 0; i < arrayListFlightDetails.size(); i++)
			{
				if (position >= c && position < c + arrayListFlightDetails.get(i).arrayListDescs.size())
				{
					return position - c;
				}
				c += arrayListFlightDetails.get(i).arrayListDescs.size();
			}
			return 0;
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
			TextView tvUserName, tvFlightNumber, tvDate, tvArrDep, tvSeatNumber, tvDelete;
			ImageView ivAirlineImage;
			LinearLayout llMain;
		}

		@Override
		public View getAmazingView(final int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = new ViewHolder();

			holder = new ViewHolder();
			if (convertView == null)
			{
				convertView = getLayoutInflater().inflate(R.layout.row_user_flight, null);

				holder.tvDelete = (TextView) convertView.findViewById(R.id.tvDelete);
				holder.llMain = (LinearLayout) convertView.findViewById(R.id.llMain);
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

			// if (position == swipedPos)
			// {
			//
			// holder.tvDelete.setVisibility(View.VISIBLE);
			// }
			// else
			// {
			// holder.tvDelete.setVisibility(View.GONE);
			//
			// }

			holder.tvDelete.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View view)
				{
					// Delete
					if (userInteraction)
					{
						int parentPos = getSectionForPosition(position);
						int childPos = getChildItemPosition(position);
						Log.w("log_tag", "dparent position..." + parentPos);
						Log.w("log_tag", "child position..." + childPos);

						alvHome.closeAnimate(position);
						arrayListFlightDetails.get(parentPos).arrayListDescs.remove(childPos);
						// swipedPos = -1;
						notifyDataSetChanged();
					}
				}
			});

			holder.llMain.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (userInteraction)
					{
						Intent intent = new Intent(UserProfileActivity.this, DisplaySeatActivity.class);
						intent.putExtra("isCheckInSuccess", false);
						startActivity(intent);
					}
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

	public void CreateLogOutDialog(final Activity context)
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
					setUserInteraction(true);
					dialog.dismiss();
				}
				else if (v == btnLogOut)
				{
					setUserInteraction(true);
					MySharedPrefrence.setUserId(context, 0);
					if (LogInActivity.activity != null)
						LogInActivity.FinishActivity();

					HomeActivity.FinishActivity();
					Intent intent = new Intent(context, LogInActivity.class);
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
