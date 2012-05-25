package com.sfumobile.wifilocator;

import android.app.ExpandableListActivity;
//import android.content.Intent; //find friends from server?
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
//import android.widget.Button;
import android.widget.BaseExpandableListAdapter;
import android.widget.AbsListView;
import android.widget.Button;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnGroupClickListener;
//import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;

public class Friends extends ExpandableListActivity implements OnClickListener{
	
	private FriendAdapter mAdapter;
	private String[] friends, loc;
	private String[][] status;
	private Button addFriendButton;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friend_screen);

		userProfile id = new userProfile();

		friends = id.get_friends();
		loc = id.get_loc();

		addFriendButton = (Button)findViewById(R.id.addFriendButton);
		addFriendButton.setOnClickListener(this);
		
		status = new String[loc.length][1];		
		for (int i=0; i< loc.length; i++){
			status[i][0] = loc[i];
		}
		
		mAdapter = new FriendAdapter();
		setListAdapter(mAdapter);

	}
	
	public class FriendAdapter extends BaseExpandableListAdapter {
		public Object getChild(int groupPosition, int childPosition) {
			return status[groupPosition][childPosition];
		}

		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		public TextView getGenericView() {
			AbsListView.LayoutParams params = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			
			TextView txtView = new TextView(Friends.this);
			txtView.setLayoutParams(params);
			txtView.setPadding(60, 5, 0, 5);
			return txtView;
		}
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {

			View inflatedView = View.inflate(getApplicationContext(), R.layout.friend_sub, null);
			inflatedView.setPadding(50, 0, 0, 0);
			TextView txtView = (TextView)inflatedView.findViewById(R.id.textView1);
				
			txtView.setTextSize(17);
			txtView.setText(getChild(groupPosition, childPosition).toString());
			return inflatedView;
		}

		public int getChildrenCount(int groupPosition) {
			return status[groupPosition].length;
		}

		public Object getGroup(int groupPosition) {
			return friends[groupPosition];
		}

		public int getGroupCount() {
			return friends.length;
		}

		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView txtView = getGenericView();
			txtView.setText(getGroup(groupPosition).toString());
			return txtView;
		}

		public boolean hasStableIds() {
			return true;
		}

		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	public void onClick(View v) {
		switch(v.getId()){
		case R.id.addFriendButton:
			break;
		}
		
	}

}
