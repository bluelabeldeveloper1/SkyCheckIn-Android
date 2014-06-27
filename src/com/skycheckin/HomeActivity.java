package com.skycheckin;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.amazinglistview.AmazingAdapter;
import com.amazinglistview.AmazingListView;
import com.custom.ClassHomeDetail;
import com.custom.ClassHomeFeed;
import com.custom.ClassHomeFeedDetail;
import com.custom.Global;
import com.custom.LogM;
import com.custom.MySharedPrefrence;
import com.custom.MyUtility;
import com.datatask.DataTask;
import com.pulltorefresh.PullToRefreshListView;
import com.pulltorefresh.PullToRefreshListView.OnRefreshListener;
import com.slidingmenu.lib.SlidingMenu;
import com.tab.checkin.DisplaySeatActivity;
import com.tab.checkin.FindFlightActivity;
import com.tab.checkin.OtherUserProfileActivity;

public class HomeActivity extends SherlockFragmentActivity
{

	private HomeComposerAdapter adapter;
	private PullToRefreshListView alvHome;
	private ArrayList<ClassHomeDetail> arrayListHomeFeeds = new ArrayList<ClassHomeDetail>();
	private TextView tvMessageCount;
	private LinearLayout llNoHomeFeed, llHomeFeed;
	private ImageView ivActivity, ivProfile, ivCheckIn, ivFriend;
	public static Activity activity;
	private SlidingMenu menu;
	private int pageIndex = 1;
	private boolean isLoadMoreComplete = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		init();
		initSliderMenu();
	}

	public static void FinishActivity()
	{
		activity.finish();
	}

	private void init()
	{
		activity = HomeActivity.this;

		Global.imageBaseUrl = MySharedPrefrence.getImageBaseUrl(HomeActivity.this);

		ivActivity = (ImageView) findViewById(R.id.ivActivity);
		ivProfile = (ImageView) findViewById(R.id.ivProfile);
		ivCheckIn = (ImageView) findViewById(R.id.ivCheckIn);
		ivFriend = (ImageView) findViewById(R.id.ivFriend);
		tvMessageCount = (TextView) findViewById(R.id.tvMessageCount);

		llNoHomeFeed = (LinearLayout) findViewById(R.id.llNoHomeFeed);
		llHomeFeed = (LinearLayout) findViewById(R.id.llHomeFeed);
		ivActivity.setSelected(true);

		ivCheckIn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(HomeActivity.this, FindFlightActivity.class);
				startActivity(intent);
			}
		});
		ivProfile.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(HomeActivity.this, UserProfileActivity.class);
				startActivity(intent);

			}
		});

		pageIndex = 1;
		isLoadMoreComplete = false;

		alvHome = (PullToRefreshListView) findViewById(R.id.alvHome);
		alvHome.setOnRefreshListener(new OnRefreshListener()
		{

			public void onRefresh()
			{
				if (!isLoadMoreComplete)
				{
					requestForGetHomeFeed();
				}
				else
				{
					alvHome.onRefreshComplete();
				}
			}
		});

		// alvHome.setAdapter(adapter = new HomeComposerAdapter());

		// for (int i = 0; i < 8; i++)
		// {
		// ClassHomeDetail classHomeFeed = new ClassHomeDetail();
		//
		// if (i == 0)
		// {
		// classHomeFeed.header_name = "Today's Flights";
		// classHomeFeed.isheader = true;
		// }
		// if (i == 2)
		// {
		// classHomeFeed.header_name = "Tomorrow's Flights";
		// classHomeFeed.isheader = true;
		// }
		// arrayListHomeFeeds.add(classHomeFeed);
		// }

		// alvHome.setOnScrollListener(new OnScrollListener()
		// {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState)
		// {
		// LogM.w("onScrollStateChanged");
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem, int
		// visibleItemCount, int totalItemCount)
		// {
		// LogM.w("onScroll");
		// }
		//
		//
		//
		// });

		// alvHome.setOnLoadMoreListener(new OnLoadMoreListener()
		// {
		//
		// @Override
		// public void onLoadMore()
		// {
		// LogM.w("load more listview");
		//
		// new LoadDataTask().execute();
		// }
		// });

		requestForGetHomeFeed();

	}

	private void initSliderMenu()
	{
		DisplayMetrics dimension = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dimension);
		int w = dimension.widthPixels;

		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffset(w * 18 / 100);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_left);
		menu.setSecondaryMenu(R.layout.menu_right);
		menu.setSlidingEnabled(true);

		ivFriend.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				menu.toggle();
			}
		});
		tvMessageCount.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				menu.showSecondaryMenu();
			}
		});
	}

	private void requestForGetHomeFeed()
	{
		boolean getByDistance = false;

		String requestString = "";

		if (getByDistance)
		{
			requestString = Global.URL + "action=getUserFeed&lat=" + Global.currentLat + "&lng=" + Global.currentLong + "&page=" + pageIndex + "&byDistance=" + getByDistance
					+ "&maxDistance=" + Global.maxDistance + "&PHPSESSID=" + MySharedPrefrence.getSessionId(HomeActivity.this);
		}
		else
		{
			requestString = Global.URL + "action=getUserFeed&page=" + pageIndex + "&byDistance=" + getByDistance + "&PHPSESSID="
					+ MySharedPrefrence.getSessionId(HomeActivity.this);
		}

		DataTask dataTask = new DataTask(HomeActivity.this, requestString, getResources().getString(R.string.dialog_please_wait))
		{
			protected void onPostExecute(String result)
			{
				super.onPostExecute(result);
				try
				{
					LogM.w("Home Activity result = " + result);
					alvHome.onRefreshComplete();

					if (result.charAt(0) == '[')
					{
						JSONArray jsonArray = new JSONArray(result);

						if (jsonArray.length() > 0)
						{
							for (int i = 0; i < jsonArray.length(); i++)
							{
								JSONObject jsonObject = jsonArray.getJSONObject(i);
								ClassHomeDetail classHomeDetail = new ClassHomeDetail();

								classHomeDetail.airlineCode = jsonObject.getString("airlineCode");
								classHomeDetail.arrivalTime = jsonObject.getString("arrivalTime");
								classHomeDetail.flightCode = jsonObject.getString("flightCode");
								classHomeDetail.flightInstanceID = jsonObject.getString("flightInstanceID");
								classHomeDetail.flightStatus = jsonObject.getString("flightStatus");
								classHomeDetail.from = jsonObject.getString("from");
								classHomeDetail.fromFullName = jsonObject.getString("fromFullName");
								// classHomeDetail.header_name =
								// jsonObject.getString("header_name");
								classHomeDetail.isheader = false;
								classHomeDetail.name = jsonObject.getString("name");
								classHomeDetail.nbSkycheckers = jsonObject.getString("nbSkycheckers");
								classHomeDetail.pictureURL = jsonObject.getString("pictureURL");
								classHomeDetail.seatcode = jsonObject.getString("seatcode");
								classHomeDetail.time = jsonObject.getString("time");
								classHomeDetail.to = jsonObject.getString("to");
								classHomeDetail.toFullName = jsonObject.getString("toFullName");
								arrayListHomeFeeds.add(classHomeDetail);

							}
							pageIndex++;
						}
						else
						{
							isLoadMoreComplete = true;
						}

					}
					if (arrayListHomeFeeds.size() > 0)
					{
						llNoHomeFeed.setVisibility(View.GONE);
						llHomeFeed.setVisibility(View.VISIBLE);
					}
					else
					{
						llNoHomeFeed.setVisibility(View.VISIBLE);
						llHomeFeed.setVisibility(View.GONE);
					}

					if (adapter == null)
					{
						if (arrayListHomeFeeds.size() > 0)
						{
							alvHome.setAdapter(adapter = new HomeComposerAdapter());
						}
					}
					else
					{
						adapter.notifyDataSetChanged();
					}
				}
				catch (JSONException e)
				{
					alvHome.onRefreshComplete();
					e.printStackTrace();
				}

			};

			@Override
			protected void onCancelled()
			{
				super.onCancelled();
				alvHome.onRefreshComplete();
				MyUtility.setAlertMessage(HomeActivity.this, Global.WebServiceFailMessage);
			};

		};

		if (MyUtility.checkInternetConnection(HomeActivity.this))
		{
			dataTask.execute();
		}
		else
		{
			alvHome.onRefreshComplete();
			MyUtility.setInternetAlertMessage(HomeActivity.this);
		}
	}

	class HomeComposerAdapter extends BaseAdapter
	{

		@Override
		public int getCount()
		{
			// TODO Auto-generated method stub
			return arrayListHomeFeeds.size();
		}

		@Override
		public ClassHomeDetail getItem(int position)
		{
			// TODO Auto-generated method stub
			return arrayListHomeFeeds.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			// TODO Auto-generated method stub
			return 0;
		}

		private class ViewHolder
		{
			TextView tvUserName, tvFlightNumber, tvDate, tvArrDep, tvSeatNumber, tvHeader;
			ImageView ivUserImage, ivAirlineImage;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.row_home_feed, null);

				// holder.tvUserName = (TextView)
				// convertView.findViewById(R.id.tvUserName);
				// holder.tvFlightNumber = (TextView)
				// convertView.findViewById(R.id.tvFlightNumber);
				// holder.tvDate = (TextView)
				// convertView.findViewById(R.id.tvDate);
				// holder.tvArrDep = (TextView)
				// convertView.findViewById(R.id.tvArrDep);
				holder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
				holder.ivUserImage = (ImageView) convertView.findViewById(R.id.ivUserImage);
				holder.ivAirlineImage = (ImageView) convertView.findViewById(R.id.ivAirlineImage);
				// holder.tvSeatNumber = (TextView)
				// convertView.findViewById(R.id.tvSeatNumber);

				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			// ClassHomeFeedDetail classHomeFeedDetail = getItem(position);

			if (getItem(position).isheader)
			{
				holder.tvHeader.setVisibility(View.VISIBLE);
				holder.tvHeader.setText(getItem(position).header_name);
			}
			else
			{
				holder.tvHeader.setVisibility(View.GONE);
			}

			holder.ivAirlineImage.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(HomeActivity.this, DisplaySeatActivity.class);
					startActivity(intent);
				}
			});

			holder.ivUserImage.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(HomeActivity.this, OtherUserProfileActivity.class);
					startActivity(intent);
				}
			});

			// convertView.setOnClickListener(new OnClickListener()
			// {
			//
			// @Override
			// public void onClick(View v)
			// {
			// Intent intent = new Intent(HomeActivity.this,
			// DisplaySeatActivity.class);
			// intent.putExtra("isCheckInSuccess", false);
			// startActivity(intent);
			// }
			// });

			return convertView;
		}

	}

	// class HomeComposerAdapter extends AmazingAdapter
	// {
	//
	// @Override
	// public int getCount()
	// {
	// int res = 0;
	// for (int i = 0; i < arrayListHomeFeeds.size(); i++)
	// {
	// res += arrayListHomeFeeds.get(i).arrayListDetail.size();
	// }
	// return res;
	// }
	//
	// @Override
	// public ClassHomeFeedDetail getItem(int position)
	// {
	// int c = 0;
	// for (int i = 0; i < arrayListHomeFeeds.size(); i++)
	// {
	// if (position >= c && position < c +
	// arrayListHomeFeeds.get(i).arrayListDetail.size())
	// {
	// return arrayListHomeFeeds.get(i).arrayListDetail.get(position - c);
	// }
	// c += arrayListHomeFeeds.get(i).arrayListDetail.size();
	// }
	// return null;
	// }
	//
	// @Override
	// public long getItemId(int position)
	// {
	// return position;
	// }
	//
	// @Override
	// protected void onNextPageRequested(int page)
	// {
	// }
	//
	// @Override
	// protected void bindSectionHeader(View view, int position, boolean
	// displaySectionHeader)
	// {
	// if (displaySectionHeader)
	// {
	// view.findViewById(R.id.header).setVisibility(View.VISIBLE);
	// TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
	// lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
	// }
	// else
	// {
	// view.findViewById(R.id.header).setVisibility(View.GONE);
	//
	// }
	// }
	//
	// private class ViewHolder
	// {
	// TextView tvUserName, tvFlightNumber, tvDate, tvArrDep, tvSeatNumber;
	// ImageView ivUserImage, ivAirlineImage;
	// }
	//
	// @Override
	// public View getAmazingView(int position, View convertView, ViewGroup
	// parent)
	// {
	// ViewHolder holder;
	// if (convertView == null)
	// {
	// holder = new ViewHolder();
	// convertView = getLayoutInflater().inflate(R.layout.row_home_feed, null);
	//
	// // holder.tvUserName = (TextView)
	// // convertView.findViewById(R.id.tvUserName);
	// // holder.tvFlightNumber = (TextView)
	// // convertView.findViewById(R.id.tvFlightNumber);
	// // holder.tvDate = (TextView)
	// // convertView.findViewById(R.id.tvDate);
	// // holder.tvArrDep = (TextView)
	// // convertView.findViewById(R.id.tvArrDep);
	// holder.ivUserImage = (ImageView)
	// convertView.findViewById(R.id.ivUserImage);
	// holder.ivAirlineImage = (ImageView)
	// convertView.findViewById(R.id.ivAirlineImage);
	// // holder.tvSeatNumber = (TextView)
	// // convertView.findViewById(R.id.tvSeatNumber);
	//
	// convertView.setTag(holder);
	// }
	// else
	// {
	// holder = (ViewHolder) convertView.getTag();
	// }
	//
	// // ClassHomeFeedDetail classHomeFeedDetail = getItem(position);
	//
	// holder.ivAirlineImage.setOnClickListener(new OnClickListener()
	// {
	//
	// @Override
	// public void onClick(View v)
	// {
	// Intent intent = new Intent(HomeActivity.this, DisplaySeatActivity.class);
	// startActivity(intent);
	// }
	// });
	//
	// holder.ivUserImage.setOnClickListener(new OnClickListener()
	// {
	//
	// @Override
	// public void onClick(View v)
	// {
	// Intent intent = new Intent(HomeActivity.this,
	// OtherUserProfileActivity.class);
	// startActivity(intent);
	// }
	// });
	//
	// // convertView.setOnClickListener(new OnClickListener()
	// // {
	// //
	// // @Override
	// // public void onClick(View v)
	// // {
	// // Intent intent = new Intent(HomeActivity.this,
	// // DisplaySeatActivity.class);
	// // intent.putExtra("isCheckInSuccess", false);
	// // startActivity(intent);
	// // }
	// // });
	//
	// return convertView;
	// }
	//
	// @Override
	// public void configurePinnedHeader(View header, int position, int alpha)
	// {
	// TextView lSectionHeader = (TextView) header;
	// lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
	// // lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
	// // lSectionHeader.setTextColor(alpha << 24 | (0x000000));
	// }
	//
	// @Override
	// public int getPositionForSection(int section)
	// {
	// if (section < 0)
	// section = 0;
	// if (section >= arrayListHomeFeeds.size())
	// section = arrayListHomeFeeds.size() - 1;
	// int c = 0;
	// for (int i = 0; i < arrayListHomeFeeds.size(); i++)
	// {
	// if (section == i)
	// {
	// return c;
	// }
	// c += arrayListHomeFeeds.get(i).arrayListDetail.size();
	// }
	// return 0;
	// }
	//
	// @Override
	// public int getSectionForPosition(int position)
	// {
	// int c = 0;
	// for (int i = 0; i < arrayListHomeFeeds.size(); i++)
	// {
	// if (position >= c && position < c +
	// arrayListHomeFeeds.get(i).arrayListDetail.size())
	// {
	// return i;
	// }
	// c += arrayListHomeFeeds.get(i).arrayListDetail.size();
	// }
	// return -1;
	// }
	//
	// @Override
	// public String[] getSections()
	// {
	// String[] res = new String[arrayListHomeFeeds.size()];
	// for (int i = 0; i < arrayListHomeFeeds.size(); i++)
	// {
	// res[i] = arrayListHomeFeeds.get(i).header;
	// }
	// return res;
	// }
	//
	// }

	//
	// private class LoadDataTask extends AsyncTask<Void, Void, Void>
	// {
	//
	// @Override
	// protected Void doInBackground(Void... params)
	// {
	//
	// if (isCancelled())
	// {
	// return null;
	// }
	//
	// // Simulates a background task
	// try
	// {
	// Thread.sleep(1000);
	// }
	// catch (InterruptedException e)
	// {
	// }
	//
	// for (int i = 0; i < 10; i++)
	// {
	// ClassHomeDetail classHomeFeed = new ClassHomeDetail();
	//
	// if (i == 5)
	// {
	// classHomeFeed.header_name = "Today's Flights";
	// classHomeFeed.isheader = true;
	// }
	// // if (i == 1)
	// // {
	// // classHomeFeed.headerName = "Tomorrow's Flights";
	// // classHomeFeed.isheader = true;
	// // }
	// arrayListHomeFeeds.add(classHomeFeed);
	// }
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result)
	// {
	//
	// // We need notify the adapter that the data have been changed
	// adapter.notifyDataSetChanged();
	// alvHome.onRefreshComplete();
	// // Call onLoadMoreComplete when the LoadMore task, has finished
	// // alvHome.onLoadMoreComplete();
	//
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected void onCancelled()
	// {
	// // Notify the loading more operation has finished
	// // alvHome.onLoadMoreComplete();
	// }
	// }
}
