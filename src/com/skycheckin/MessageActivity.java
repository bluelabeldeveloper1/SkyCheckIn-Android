package com.skycheckin;

import java.util.ArrayList;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.custom.MyUtility;
import com.skycheckin.R;

public class MessageActivity extends Activity
{
	private LinearLayout llMessage, llNoMessage, llBlockUser, llSendMessage;
	private FlightListAdapter flightListAdapter;
	private ListView lvMessage;
	private TextView tvUserName;
	private ImageView ivBlock;
	private Button btnBlock, btnCancel;
	private EditText etMessage;
	private TextView tvSend;
	private ArrayList<String> arrayList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		init();
	}

	private void init()
	{
		tvSend = (TextView) findViewById(R.id.tvSend);
		etMessage = (EditText) findViewById(R.id.etMessage);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		llMessage = (LinearLayout) findViewById(R.id.llMessage);
		llNoMessage = (LinearLayout) findViewById(R.id.llNoMessage);
		llBlockUser = (LinearLayout) findViewById(R.id.llBlockUser);
		llSendMessage = (LinearLayout) findViewById(R.id.llSendMessage);
		btnCancel = (Button) findViewById(R.id.btnCancel);
		btnBlock = (Button) findViewById(R.id.btnBlock);

		ivBlock = (ImageView) findViewById(R.id.ivBlock);

		lvMessage = (ListView) findViewById(R.id.lvMessage);
		ivBlock.setOnClickListener(clickListener);

		flightListAdapter = new FlightListAdapter(MessageActivity.this);
		lvMessage.setAdapter(flightListAdapter);
		btnCancel.setOnClickListener(clickListener);
		btnBlock.setOnClickListener(clickListener);

		tvSend.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (!etMessage.getText().toString().trim().equals(""))
				{
					arrayList.add(etMessage.getText().toString().trim());
					etMessage.setText("");
					flightListAdapter.notifyDataSetChanged();
				}

			}
		});

		for (int i = 0; i < 4; i++)
		{
			arrayList.add("Alis abor");
		}
	}

	OnClickListener clickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			if (v == ivBlock)
			{
				MyUtility.hideSoftKeyboard(MessageActivity.this);
				if (llBlockUser.getVisibility() == View.GONE)
				{
					llSendMessage.setVisibility(View.GONE);
					llBlockUser.setVisibility(View.VISIBLE);
				}
				else
				{
					llSendMessage.setVisibility(View.VISIBLE);
					llBlockUser.setVisibility(View.GONE);
				}
			}
			else if (v == btnBlock)
			{
				llSendMessage.setVisibility(View.VISIBLE);
				llBlockUser.setVisibility(View.GONE);
			}
			else if (v == btnCancel)
			{
				llSendMessage.setVisibility(View.VISIBLE);
				llBlockUser.setVisibility(View.GONE);
			}

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
			TextView tvMessage;
			ImageView ivUserImage;
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
			return arrayList.size();
		}

		@Override
		public String getItem(int position)
		{
			// TODO Auto-generated method stub
			return arrayList.get(position);
		}

		@Override
		public int getViewTypeCount()
		{
			// TODO Auto-generated method stub
			return 2;
		}

		public View getView(final int position, View convertView, ViewGroup parent)
		{

			ViewHolder holder;

			if (convertView == null)
			{
				if (position % 2 == 1)
				{
					convertView = mInflater.inflate(R.layout.row_message_other, null);
				}
				else
				{
					convertView = mInflater.inflate(R.layout.row_message_user, null);
				}

				holder = new ViewHolder();
				holder.tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);
				holder.tvMessage.setText(getItem(position));
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
					// Intent intent = new Intent(MessageActivity.this,
					// MessageActivity.class);
					// startActivity(intent);

				}
			});

			return convertView;
		}
	}

	private void requestForUserLogin()
	{
		Intent intent = new Intent(MessageActivity.this, HomeActivity.class);
		startActivity(intent);
	}

	private void displayAlert(String message)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(MessageActivity.this);
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
