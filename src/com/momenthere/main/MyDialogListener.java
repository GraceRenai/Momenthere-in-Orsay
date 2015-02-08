//Coder: Yuebai Xu
package com.momenthere.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;

public interface MyDialogListener {
	public void onOkClick(String message) throws UnsupportedEncodingException, ClientProtocolException, IOException;
}
