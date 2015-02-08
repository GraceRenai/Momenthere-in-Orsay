package com.momenthere.login;


import com.example.momentherelogin.R;
import com.momenthere.communication.AskForAddress;
import com.momenthere.communication.AskForAddressListener;
import com.momenthere.main.MainActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Postcard extends Activity{
	private ImageButton button1, button2, button3, button4, button5,navWall, sendButton;
	public ImageView postPreview;
	private EditText edit;
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String username;
    Uri uri = null;
    boolean flicker = false;
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.
				FEATURE_NO_TITLE );
		int flag=WindowManager.LayoutParams.
				FLAG_FULLSCREEN ;
		Window myWindow= this.getWindow(); 
		myWindow.setFlags(flag,flag);
		this.setContentView(R.layout.postcard);
		
		Bundle extras = this.getIntent().getExtras();
		username = extras.getString("username");
		
		button1 = (ImageButton)findViewById(R.id.takePhoto);
		button2 = (ImageButton)findViewById(R.id.gallery);
		button3 = (ImageButton)findViewById(R.id.textWrite);
		button4 = (ImageButton)findViewById(R.id.audio);
		button5 = (ImageButton)findViewById(R.id.video);
		navWall = (ImageButton)findViewById(R.id.navWall);
		sendButton = (ImageButton)findViewById(R.id.sendButton);
		edit = (EditText)findViewById(R.id.postText);
		edit.setVisibility(View.INVISIBLE);
		postPreview = (ImageView)findViewById(R.id.postcard);
		button1.setOnClickListener(new OnClickListener(){

			@Override
		      public void onClick(View v) {  
                // TODO Auto-generated method stub  
				 
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1); 

            }  
			
		});
		button2.setOnClickListener(getImage);
		button3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		sendButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog dialog = new AskForAddress(Postcard.this, R.style.AskForAddress,
						new AskForAddressListener(){
			});
				dialog.setContentView(R.layout.askforaddress);
				dialog.show();
			}
			
		});
		navWall.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(Postcard.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent); 
                finish();
			}
			
		});
		postPreview.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				if(flicker == false){
				postPreview.setImageDrawable(getResources().getDrawable(R.drawable.postcard3));
				flicker = true;
				edit.setEnabled(true);
				edit.setVisibility(View.VISIBLE);
				}
				else {
					if(uri!=null){
					postPreview.setImageURI(uri); 
					}
					else{
						postPreview.setImageDrawable(getResources().getDrawable(R.drawable.postcard1));
					}
					flicker = false;
					edit.setEnabled(false);
					edit.setVisibility(View.INVISIBLE);
				}
				
			}
			
		});
	}
	    private View.OnClickListener getImage = new OnClickListener() {  
	    	 
	        @Override 
	        public void onClick(View v) {  
	            Intent intent = new Intent();  
	            intent.setAction(Intent.ACTION_PICK);  
	            intent.setType("image/*");  
	            startActivityForResult(intent, 0);  
	        }  
	    };   
	    @Override 
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (requestCode == 0 && resultCode == -1) { 
	            uri = data.getData();  
	            postPreview.setImageURI(uri);  
	        } 
	        else if(requestCode==1&&resultCode == -1){
	            Bundle bundle = data.getExtras();
	            Bitmap bitmap = (Bitmap) bundle.get("data");
	            postPreview.setImageBitmap(bitmap);
	            uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
	        }
	        super.onActivityResult(requestCode, resultCode, data);  
	    }
	    
	    
}