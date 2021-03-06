package com.sfumobile.wifilocator.request;

import com.sfumobile.wifilocator.types.RequestTypes;

public class FriendshipRequest extends Request {
	
	private int _userid, _friendid;
	
	
	WifiLocatorRequestThread _thread;
	public FriendshipRequest( int userid, int friendid ){
		super(RequestTypes.INIT_FRIENDSHIP);
		_userid = userid;
		_friendid = friendid;
	}
	
	public String getURL() {
		setProperty( "user_id", ""+_userid, RequestTypes.INT_TYPE);
		setProperty( "friend_id", ""+_friendid, RequestTypes.INT_TYPE);
		return RequestConstants.GET_FRIENDSHIP_BASE_URL;
	}
	
}
