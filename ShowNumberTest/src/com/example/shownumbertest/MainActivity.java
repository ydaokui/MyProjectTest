package com.example.shownumbertest;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shownumbertest.presenter.PresenterLogin;
import com.example.shownumbertest.presenter.PresenterLoginImpl;
import com.example.shownumbertest.view.ViewLogin;

public class MainActivity extends ActionBarActivity implements ViewLogin{

	Button btnBegin,btnLogin,btnLoginClear;
	TextView tvName;
	EditText edtName,edtPwd;
	String contactName = "",contactId = "",userName = "",userNumber = "";
	PresenterLogin presenterLogin;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        init();
    }
    
    private void init(){
        presenterLogin = new PresenterLoginImpl(this);
        btnBegin = (Button)findViewById(R.id.btn_begin_video);
        btnBegin.setOnClickListener(listener);
        
        btnLogin = (Button)findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(listener);
        
        btnLoginClear = (Button)findViewById(R.id.btn_login_clear);
        btnLoginClear.setOnClickListener(listener);
        
        edtName = (EditText)findViewById(R.id.edt_name);
        edtPwd = (EditText)findViewById(R.id.edt_pwd);
        
        tvName = (TextView)findViewById(R.id.tv_name);
    }
    
    View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View view) {
			switch (view.getId()) {
			case R.id.btn_begin_video:
				startContact();
				break;
			case R.id.btn_login:
				presenterLogin.login();
				break;
			case R.id.btn_login_clear:
				presenterLogin.clear();
				break;

			default:
				break;
			}
		}
	};
	
	private static final int REQUEST_CONTACT = 1;
	
	private void startContact() {

		Intent intent = new Intent();

		intent.setAction(Intent.ACTION_PICK);

		intent.setData(ContactsContract.Contacts.CONTENT_URI);

		startActivityForResult(intent, REQUEST_CONTACT);

	}
	
	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == REQUEST_CONTACT) {

			if (resultCode == RESULT_OK) {

				if (data == null) {

					return;

				}
				 ContentResolver reContentResolverol = getContentResolver();
				
				Uri contactData = data.getData();
				
				Cursor cursor = managedQuery(contactData, null, null, null, null);  
				
				cursor.moveToFirst();
				userName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				Cursor phone = reContentResolverol.query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
								+ " = " + contactId, null, null);
				List<String> list = new ArrayList<String>();
				while (phone.moveToNext()) {
					userNumber = phone
							.getString(phone
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
					list.add(userNumber);
					tvName.setText(list.toString() + " (" + userName + ")");
				}
	            
//				Uri result = data.getData();
//				contactId = result.getLastPathSegment();
//				result.getUserInfo();
//				contactName = result.getUserInfo();
//				tvName.setText(contactName);
//
//				//进入系统拨号盘界面
//				Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + userNumber));
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				startActivity(intent);
				showToast(list.toString() + " (" + userName + ")");
			}

		}
	}
	
	public void showToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	public void showToast(int resId){
		showToast(getString(resId));
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

	@Override
	public String getUserName() {
		if(!TextUtils.isEmpty(edtName.getText().toString())){
			return edtName.getText().toString();
		}else{
			return "null";
		}
	}

	@Override
	public String getUserPwd() {
		if(!TextUtils.isEmpty(edtPwd.getText().toString())){
			return edtPwd.getText().toString();
		}else{
			return "0000";
		}
	}

	@Override
	public void clearName() {
		edtName.setText("");
	}

	@Override
	public void clearPwd() {
		edtPwd.setText("");
	}

	@Override
	public void showMesg(String msg) {
		showToast(msg);
	}

}
