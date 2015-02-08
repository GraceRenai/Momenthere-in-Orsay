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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity {
	
	private EditText username;
	private EditText password;
	private EditText confirm_password;
	private Button buttonCancel;
	private Button buttonRegister;
    private String baseURL = "http://54.93.57.115:8080/myhttp2/servlet/SignUpAction";	

	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.
				FEATURE_NO_TITLE );
		int flag=WindowManager.LayoutParams.
				FLAG_FULLSCREEN ;
		Window myWindow= this.getWindow(); 
		myWindow.setFlags(flag,flag);
		setContentView(R.layout.signup);

		username = (EditText)findViewById(R.id.user_name_edit);
		password = (EditText)findViewById(R.id.password_edit);
		buttonCancel = (Button)findViewById(R.id.BtnCancel);
		buttonRegister = (Button)findViewById(R.id.BtnRegister);	
		confirm_password = (EditText)findViewById(R.id.confirm_password_edit);
		
		buttonCancel.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();  
                intent.setClass(SignUp.this, Login.class);  
                startActivity(intent);  
               // finish();//停止当前的Activity,如果不写,则按返回键会跳转回原来的Activity  
			}			
		});
		
		buttonRegister.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = username.getText().toString();
				String passwrd = password.getText().toString();
				String passwrd_confirm = confirm_password.getText().toString();
				
				if(!passwrd.equals(passwrd_confirm)){
					Toast.makeText(getApplicationContext(), "The password is not the same, please enter it again : )", Toast.LENGTH_LONG).show();
				}else{
					
		            NameValuePair pair1 = new BasicNameValuePair("username", name);
		            NameValuePair pair2 = new BasicNameValuePair("password", passwrd);
		            
		            List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		            pairList.add(pair1);
		            pairList.add(pair2);
				
		            HttpEntity requestHttpEntity;
					try {
						requestHttpEntity = new UrlEncodedFormEntity(pairList);				
						HttpPost httpPost = new HttpPost(baseURL);
		                httpPost.setEntity(requestHttpEntity);
		                HttpClient httpClient = new DefaultHttpClient();
						HttpResponse response = httpClient.execute(httpPost);			
				        HttpEntity httpEntity = response.getEntity();
				        InputStream inputStream = httpEntity.getContent();
			            BufferedReader reader = new BufferedReader(new InputStreamReader(
			                    inputStream));
			            String result = "";
			            String line = "";
			            while (null != (line = reader.readLine()))
			            {
			                result += line;
			            }
			            //Toast.makeText(getApplicationContext(), "response"+result, Toast.LENGTH_SHORT).show();			            
			            if(result.equals("success")){
			            	Toast.makeText(getApplicationContext(), "Sign up succeed!", Toast.LENGTH_SHORT).show();
							Intent intent = new Intent();  
			                intent.setClass(SignUp.this, Login.class);  
			                startActivity(intent);  
			            }else{
			            	
			            	Toast.makeText(getApplicationContext(), "Sign up failed!", Toast.LENGTH_SHORT).show();
			            }
			            			            

					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
				}
				}
				
			});
	}
}