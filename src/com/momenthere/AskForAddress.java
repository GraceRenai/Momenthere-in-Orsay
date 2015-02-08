//Coder: Lan Wang
package com.momenthere;



import java.sql.Timestamp;
import java.util.Date;

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


public class AskForAddress extends Dialog{
	
	private AskForAddressListener listener;
	
	public ImageButton button1;
	public ImageButton button2;
	public EditText edit;
	private long date = new Date().getTime();
	Timestamp time = new Timestamp(date);
	
	
    Context context;
    public AskForAddress(Context context ,AskForAddressListener  Listener1) {
        super(context);
        this.context = context;
        this.listener = Listener1;
    }
    public AskForAddress(Context context, int theme, AskForAddressListener  Listener1){
        super(context, theme);
        this.context = context;
        this.listener = Listener1;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.askforaddress);
        

        
        button1 = (ImageButton) findViewById(R.id.button_ok);
        button2 = (ImageButton) findViewById(R.id.button_cancel);
        edit = (EditText)findViewById(R.id.postalAddress);
        button1.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//listener.onOkClick(edit.getText().toString());
				Toast.makeText(getContext(), "You have successfully sent your postcard", Toast.LENGTH_LONG).show();
				AskForAddress.this.dismiss();				
			}
        });
        button2.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AskForAddress.this.dismiss();
			}
        });
    }
}
