package com.Rahat.myroutine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ColorChooseActivity extends Activity {

	ListView colorListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_color_choose);
		
		colorListView = (ListView) findViewById(R.id.listViewColorList);
				
		final String[] colorNames=MyColors.getColorNames();
		
		BaseAdapter adapter = new BaseAdapter() {

			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			@SuppressLint("InflateParams")
			@Override
			public View getView(int position, View view, ViewGroup parent) {
				if (view == null) {
					view = layoutInflater.inflate(R.layout.list_item, null);
				}
				TextView textView = (TextView) view.findViewById(R.id.textViewColorName);

				textView.setText(colorNames[position]);
				textView.setBackgroundColor(MyColors.getColorConstant(position));
				return view;
			}

			@Override
			public long getItemId(int position) {
				return position + 1;
			}

			@Override
			public Object getItem(int position) {
				return position + 1;
			}

			@Override
			public int getCount() {
				return colorNames.length;
			}
		};

		colorListView.setAdapter(adapter);

		colorListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("color_constant", MyColors.getColorConstant(position));
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.color_choose, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_chooseCustomColor) {
			Intent intent=new Intent(ColorChooseActivity.this,CustomColorChooserActivity.class);
			startActivityForResult(intent, CustomColorChooserActivity.CustomColorChooseReqCode);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==CustomColorChooserActivity.CustomColorChooseReqCode && resultCode==RESULT_OK){
			//get data
	    	int colConstant=data.getIntExtra("color_constant", RoutineItem.defaultColorConstant);
	    	
	    	//return data
			Intent resultIntent = new Intent();
			resultIntent.putExtra("color_constant", colConstant);
			setResult(Activity.RESULT_OK, resultIntent);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
