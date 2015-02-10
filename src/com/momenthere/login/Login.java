package com.momenthere.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.momenthere.R;
import com.momenthere.R.id;
import com.momenthere.R.layout;
import com.momenthere.main.MainActivity;
import com.momenthere.main.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity implements Utility {

	private EditText username;
	private EditText password;
	private Button buttonLogin;
	private Button buttonRegister;
	private String baseURL = "http://" + base + "/servlet/LoginAction";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
		Window myWindow = this.getWindow();
		myWindow.setFlags(flag, flag);

		setContentView(R.layout.activity_login);

		username = (EditText) findViewById(R.id.LoginName);
		password = (EditText) findViewById(R.id.password);
		buttonLogin = (Button) findViewById(R.id.BtnMenulogin);
		buttonRegister = (Button) findViewById(R.id.BtnRegister);

		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = username.getText().toString();
				String passwrd = password.getText().toString();
				NameValuePair pair1 = new BasicNameValuePair("username", name);
				NameValuePair pair2 = new BasicNameValuePair("password",
						passwrd);

				List<NameValuePair> pairList = new ArrayList<NameValuePair>();
				pairList.add(pair1);
				pairList.add(pair2);

				try {
					HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
							pairList);
					// URL使用基本URL即可，其中不需要加参数
					HttpPost httpPost = new HttpPost(baseURL);
					// 将请求体内容加入请求中
					httpPost.setEntity(requestHttpEntity);
					// 需要客户端对象来发送请求
					Log.i("json", "l11.3");
					HttpClient httpClient = new DefaultHttpClient();
					// 发送请求
					HttpResponse response = httpClient.execute(httpPost);
					Log.i("json", "l11.4");
					HttpEntity httpEntity = response.getEntity();
					InputStream inputStream = httpEntity.getContent();
					Log.i("json", "l11.5");
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(inputStream));
					String result = "";
					String line = "";
					while (null != (line = reader.readLine())) {
						result += line;
					}
					Log.i("json", "l3" + result);
					Toast.makeText(getApplicationContext(), "result " + result,
							Toast.LENGTH_SHORT).show();
					if (result.equals("login success")) {
						Intent intent = new Intent(Login.this,
								MainActivity.class);
						intent.putExtra("username", name);
						
						Toast.makeText(getApplicationContext(), "name " + name,
								Toast.LENGTH_SHORT).show();
						startActivity(intent);
						finish();
					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		buttonRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, SignUp.class);
				startActivity(intent);

				// finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity
			}
		});

	}
}
