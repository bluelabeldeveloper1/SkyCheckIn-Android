package com.skycheckin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class MenuMessageFragment extends SherlockFragment
{
	private ListView lvMessage;
	private MessageListAdapter listAdapter;
	private EditText etSearch;
	private ImageView ivCancel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.menu_message, container, false);
		init(v);
		return v;
	}

	private void init(View v)
	{
		lvMessage = (ListView) v.findViewById(R.id.lvMessage);
		etSearch = (EditText) v.findViewById(R.id.etSearch);
		etSearch.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				etSearch.setText("");
			}
		});

		listAdapter = new MessageListAdapter(getActivity());
		lvMessage.setAdapter(listAdapter);
	}

	private class MessageListAdapter extends BaseAdapter
	{
		private LayoutInflater mInflater;
		private Context context;

		public MessageListAdapter(Context context)
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

			// if (convertView == null)
			// {

			convertView = mInflater.inflate(R.layout.row_menu_message, null);

			holder = new ViewHolder();
			holder.tvMessage = (TextView) convertView.findViewById(R.id.tvMessage);

			String name = "Karen L";
			String message = "Bus, sit alis";
			
			SpannableString ss1 = new SpannableString(name+"  "+message);
			
			ss1.setSpan(new RelativeSizeSpan(1.5f), 0,name.length(),0); // set size
			ss1.setSpan(new RelativeSizeSpan(0.5f), 0, (name+"  "+message).length(), 0);

			holder.tvMessage.setText(ss1);
			convertView.setTag(holder);
			// }
			// else
			// {
			// holder = (ViewHolder) convertView.getTag();
			// }
			convertView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent(getActivity(), MessageActivity.class);
					startActivity(intent);

				}
			});

			return convertView;
		}
	}

}
