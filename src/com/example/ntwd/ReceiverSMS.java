package com.example.ntwd;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.view.Menu;
import android.widget.Toast;

public class ReceiverSMS extends BroadcastReceiver {

	public void onReceive(Context content, Intent intent){
		Bundle bundle = intent.getExtras();
		SmsMessage[]msg = null;
		String str = "";
		
		if (bundle != null){
			Object [] pdus = (Object []) bundle.get("pdus");
			msg = new SmsMessage[pdus.length];
					for (int i=0; i<msg.length; i++){
						msg[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
						str+= "SMS Received from: "+msg[i].getOriginatingAddress();
						str+=":";
						str+=msg[i].getMessageBody().toString();
						str+="\n";
					}
					Toast.makeText(content,  str,  Toast.LENGTH_SHORT).show();
		}
	}
	/*@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_receiver_sms);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.receiver_sm, menu);
		return true;
	}*/

}
