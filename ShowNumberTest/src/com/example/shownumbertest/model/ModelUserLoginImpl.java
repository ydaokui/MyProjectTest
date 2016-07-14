package com.example.shownumbertest.model;

import com.example.shownumbertest.bean.BeanUser;
import com.example.shownumbertest.listener.ListenerLogin;

public class ModelUserLoginImpl implements ModelUserLogin {
	private ListenerLogin linister;

	public ModelUserLoginImpl(ListenerLogin linister) {
		this.linister = linister;
	}

	@Override
	public void login(BeanUser user) {
		String userName, userPwd;
		boolean statue = false;
		userName = user.getName();
		userPwd = user.getPwd();
		if (userName != null && "ÕÅÈý".equals(userName)) {
			if (userPwd != null && "123456".equals(userPwd)) {
					statue = true;
			}
		}
		if(linister != null){
			linister.loginStatus(statue);
		}
	}

}
