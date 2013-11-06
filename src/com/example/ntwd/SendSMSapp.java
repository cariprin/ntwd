package com.example.ntwd;

import android.os.Bundle;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMSapp extends Activity {
	EditText phoneNumber, message;
	BroadcastReceiver sentReceiver, deliveredReceiver;
	String SENT = "SMS_SENT";
	String DELIVERED = "SMS_DELIVERED";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_smsapp);
		final PendingIntent sentPendIntent = PendingIntent.getBroadcast(this,0, new Intent(SENT),0);
		final PendingIntent delivered_pendintent = PendingIntent.getBroadcast(this,  0,  new Intent(DELIVERED), 0);
		sentReceiver = new BroadcastReceiver(){
			public void onReceive(Context arg0, Intent arg1){
				switch (getResultCode()){
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(),"SMS sent", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
					Toast.makeText(getBaseContext(),"Generic Failure", Toast.LENGTH_SHORT).show();
					break;	
				case SmsManager.RESULT_ERROR_NO_SERVICE:
					Toast.makeText(getBaseContext(),"No service", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU:
					Toast.makeText(getBaseContext(),"Null PDU", Toast.LENGTH_SHORT).show();
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF:
					Toast.makeText(getBaseContext(),"Radio off", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		deliveredReceiver = new BroadcastReceiver(){
			public void onReceive(Context arg0, Intent arg1){
				switch (getResultCode()){
				case Activity.RESULT_OK:
					Toast.makeText(getBaseContext(),"SMS successfully delivered", Toast.LENGTH_SHORT).show();
					break;
				case Activity.RESULT_CANCELED:
					Toast.makeText(getBaseContext(),"Failure - SMS not delivered", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		registerReceiver(sentReceiver, new IntentFilter(SENT));
		registerReceiver(deliveredReceiver, new IntentFilter(DELIVERED));
		Button sendBtn=(Button) this.findViewById(R.id.button1);
		sendBtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick (View v){
				phoneNumber = (EditText) findViewById(R.id.editText1);
				message = (EditText) findViewById(R.id.editText2);
				if(phoneNumber.getText().toString().trim().length() > 0 &&
						message.getText().toString().trim().length() > 0){
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(phoneNumber.getText().toString(), null, message.getText().toString(), sentPendIntent, delivered_pendintent);
				}
				else{
					Toast.makeText(SendSMSapp.this, "Either phone text is missing", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button cancelBtn = (Button) this.findViewById(R.id.button2);
		cancelBtn.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				phoneNumber.setText("");
				message.setText("");
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_smsapp, menu);
		return true;
	}

}
