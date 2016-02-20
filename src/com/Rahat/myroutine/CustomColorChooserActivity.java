package com.Rahat.myroutine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CustomColorChooserActivity extends Activity {

	public static final int CustomColorChooseReqCode=111;
	
	Button saveButton;
	SeekBar seekBarRed, seekBarGreen, seekBarBlue;
	//SeekBar seekBarAlpha;
	View colorView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_color_chooser);
		
		colorView=findViewById(R.id.view_colorView);
		saveButton=(Button) findViewById(R.id.button_saveCustomColor);
		
		seekBarRed=(SeekBar) findViewById(R.id.seekBarRed);
		seekBarGreen=(SeekBar) findViewById(R.id.seekBarGreen);
		seekBarBlue=(SeekBar) findViewById(R.id.seekBarBlue);
		//seekBarAlpha=(SeekBar) findViewById(R.id.seekBarAlpha);

		colorView.setBackgroundColor(Color.BLACK);
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("color_constant", getIntColor());
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});
		
		seekBarRed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
	
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				colorView.setBackgroundColor(getIntColor());
			}
		});
		
		seekBarGreen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
	
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				colorView.setBackgroundColor(getIntColor());
			}
		});
		
		seekBarBlue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
	
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				colorView.setBackgroundColor(getIntColor());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom_color_chooser, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}*/
		return super.onOptionsItemSelected(item);
	}
	
	private int getIntColor(){
		int red=seekBarRed.getProgress();
		int green=seekBarGreen.getProgress();
		int blue=seekBarBlue.getProgress();
		
		return Color.rgb(red, green, blue);
	}
}
