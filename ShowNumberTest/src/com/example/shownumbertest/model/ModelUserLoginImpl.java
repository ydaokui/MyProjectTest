package com.example.shownumbertest.model;

import com.example.shownumbertest.bean.BeanUser;
import com.example.shownumbertest.listener.ListenerLogin;

public class ModelUserLoginImpl implements ModelUserLogin {
	private ListenerLogin linisterLogin;

	public ModelUserLoginImpl(ListenerLogin linister) {
		this.linisterLogin = linister;
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
		if(linisterLogin != null){
			linisterLogin.loginStatus(statue);
		}
	}

}
