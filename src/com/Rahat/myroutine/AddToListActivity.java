package com.Rahat.myroutine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class AddToListActivity extends Activity {

	Button saveButton,clearButton, colorChooseButton;
	
	EditText etCourseId, etCourseName, etTeachers;

	String itemColorName;
	
	Intent intent;
	
	int buttonColorConstant;

	RoutineItem item;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Full Screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//------------*/
		setContentView(R.layout.activity_add_to_list);
		
		intent=getIntent();
		
		initialize();
	}

	private void initialize() {
		saveButton=(Button) findViewById(R.id.buttonSave);
		clearButton=(Button) findViewById(R.id.buttonClear);
		colorChooseButton=(Button) findViewById(R.id.buttonChoose);
		
		etCourseId=(EditText) findViewById(R.id.editTextCourseId);
		etCourseName=(EditText) findViewById(R.id.editTextCourseName);
		etTeachers=(EditText) findViewById(R.id.editTextTeachers);
		
		final int day=intent.getIntExtra("day",0);
		final int period=intent.getIntExtra("period", 0);
		
		saveButton.setText("Save ("+
				MainActivity.days[day-1]+","+MainActivity.times[period-1]+")");
		
		item=MainActivity.items[day][period];
		
		buttonColorConstant=item.getColorConstant();
		colorChooseButton.setBackgroundColor(buttonColorConstant);
		
		etCourseId.setText(item.getCourseID());
		etCourseName.setText(item.getCourseName());
		etTeachers.setText(item.getTeachers());
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				item.setCourseID(etCourseId.getText().toString());
				item.setCourseName(etCourseName.getText().toString());
				item.setTeachers(etTeachers.getText().toString());
				item.setColorConstant(buttonColorConstant);
				
				MainActivity.items[day][period]=item;
				MainActivity.dataNeedToBeSaved=true;

				finish();
			}
		});
		
		clearButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				item=new RoutineItem();//refering to new address
				buttonColorConstant=item.getColorConstant();
				colorChooseButton.setBackgroundColor(buttonColorConstant);
				
				etCourseId.setText(item.getCourseID());
				etCourseName.setText(item.getCourseName());
				etTeachers.setText(item.getTeachers());
				
				//Toast.makeText(getBaseContext(), "Cleared", Toast.LENGTH_SHORT).show();
			}
		});
		
		colorChooseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(AddToListActivity.this,ColorChooseActivity.class);
				startActivityForResult(intent, 0);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    //color activity request code was 0
	    if(requestCode==0 && resultCode==RESULT_OK){
	    	buttonColorConstant=data.getIntExtra("color_constant", RoutineItem.defaultColorConstant);
	    	colorChooseButton.setBackgroundColor(buttonColorConstant);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_to_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
