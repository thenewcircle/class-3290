package com.manwin.client;
 
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.manwin.common.ChangeLog;
import com.manwin.common.IManwinListener;
import com.manwin.common.Installation;
import com.manwin.common.ManwinManager;

public class MainActivity extends Activity {
	private static final String TAG = "ManwinClient";
	private ManwinManager manager;
	private EditText input;
	private static TextView output;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Log.d(TAG, "UUDI: "+Installation.id(this));
		 
		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);

		manager = new ManwinManager(this);
	}

	public void onClick(View v) {
		String packageName = input.getText().toString(); 

		boolean result = manager.update(packageName, LISTENER);

		output.append(String.format("\nUpdate of %s returned %b", packageName,
				result));
	}
	 
	private static final Handler HANDLER = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			output.setText((String)msg.obj);
		}
	};
	
	private static final IManwinListener LISTENER = new IManwinListener.Stub() {
		
		@Override 
		public void onProgress(int count, int length) throws RemoteException {
			Message msg = HANDLER.obtainMessage();
			msg.obj = String.format("Downloaded %d of %d", count, length);
			HANDLER.sendMessage(msg);
		}
		 
		@Override
		public void onFail() throws RemoteException {
			Message msg = HANDLER.obtainMessage();
			msg.obj = String.format("Failed to download");
			HANDLER.sendMessage(msg);
		}
		
		@Override
		public void onComplete(ChangeLog changeLog) throws RemoteException {
			Message msg = HANDLER.obtainMessage();
			msg.obj = String.format("Downloaded %s", changeLog.getDescription());
			HANDLER.sendMessage(msg);
		}
	};
}
