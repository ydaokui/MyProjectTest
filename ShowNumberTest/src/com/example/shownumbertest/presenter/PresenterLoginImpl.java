package com.example.shownumbertest.presenter;

import com.example.shownumbertest.bean.BeanUser;
import com.example.shownumbertest.listener.ListenerLogin;
import com.example.shownumbertest.model.ModelUserLogin;
import com.example.shownumbertest.model.ModelUserLoginImpl;
import com.example.shownumbertest.view.ViewLogin;

public class PresenterLoginImpl implements PresenterLogin,ListenerLogin{

	private ModelUserLogin modeUserLogin;
	private ViewLogin viewLogin;
	
	public PresenterLoginImpl(ViewLogin viewLogin) {
		super();
		modeUserLogin = new ModelUserLoginImpl(this);
		this.viewLogin = viewLogin;
	}

	@Override
	public void loginStatus(boolean state) {
		String msg;  
        if (state){
            msg = "login succeed";  
        }else { 
            msg = "login failed";  
        }
        viewLogin.showMesg(msg);
	}

	@Override
	public void login() {
		BeanUser user = new BeanUser();
		user.setName(viewLogin.getUserName());
		user.setPwd(viewLogin.getUserPwd());
		modeUserLogin.login(user);
	}

	@Override
	public void clear() {
		viewLogin.clearName();
		viewLogin.clearPwd();
	}

}
