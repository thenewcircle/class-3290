package com.manwin.fib;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private EditText input;
	private Button buttonGo;
	private TextView output;
	private FibTask task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		input = (EditText) findViewById(R.id.input);
		buttonGo = (Button) findViewById(R.id.button_go);
		output = (TextView) findViewById(R.id.output);

		buttonGo.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		long n = Long.parseLong(input.getText().toString());

		task = new FibTask();
		task.execute(n);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		task.cancel(true);
	}

	class FibTask extends AsyncTask<Long, Void, Long> {
		private long n;

		@Override
		protected Long doInBackground(Long... params) {
			this.n = params[0];
			return FibLib.fibJ(n);
		}

		@Override
		protected void onPostExecute(Long result) {
			output.append(String.format("\n fib(%d)= %d", n, result));
		}
	}
}
