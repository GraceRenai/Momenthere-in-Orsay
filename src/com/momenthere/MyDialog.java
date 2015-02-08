//Coder: Lan Wang
package com.momenthere;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MyDialog extends Dialog{
	
	private MyDialogListener listener;
	
	public ImageButton button1;
	public ImageButton button2;
	public EditText edit;
	private long date = new Date().getTime();
	Timestamp time = new Timestamp(date);
	
	
    Context context;
    public MyDialog(Context context ,MyDialogListener  Listener1) {
        super(context);
        this.context = context;
        this.listener = Listener1;
    }
    public MyDialog(Context context, int theme, MyDialogListener  Listener1){
        super(context, theme);
        this.context = context;
        this.listener = Listener1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog);
        

        
        button1 = (ImageButton) findViewById(R.id.dialog_button_ok);
        button2 = (ImageButton) findViewById(R.id.dialog_button_cancel);
        edit = (EditText)findViewById(R.id.editText);
        button1.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					listener.onOkClick(edit.getText().toString());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MyDialog.this.dismiss();				
			}
        });
        button2.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MyDialog.this.dismiss();
			}
        });
    }

}
