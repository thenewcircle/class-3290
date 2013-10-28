package com.manwin.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.manwin.common.ManwinManager;

public class MainActivity extends Activity {
	private ManwinManager manager;
	private EditText input;
	private TextView output;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		output = (TextView) findViewById(R.id.output);

		manager = new ManwinManager(this);
		manager.update("fail!"); 
	}

	public void onClick(View v) {
		String packageName = input.getText().toString(); 

		boolean result = manager.update(packageName);

		output.append(String.format("\nUpdate of %s returned %b", packageName,
				result));
	}
}
