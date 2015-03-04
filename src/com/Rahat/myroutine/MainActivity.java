package com.Rahat.myroutine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final String storageFolderName=".com.frrahat.MyRoutine";
	private final String storageFileName="MyRoutineData.ser";
	private DrawView drawView;
	static RoutineItem[][] items;
	RoutineItem copiedRoutineItem;
	static boolean dataNeedToBeSaved;
	
	private boolean copyMode;
	private boolean pasteMode;
	
	File storageFile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Full Screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// ------------*/
		File root = Environment.getExternalStorageDirectory();
		File dir = new File(root.getAbsolutePath() + "/"+storageFolderName);
        if(!dir.exists())
        	dir.mkdirs();
        storageFile = new File(dir, storageFileName);
		
        loadData();
		dataNeedToBeSaved=false;
		copyMode=pasteMode=false;
		drawView=new DrawView(this);
		drawView.setBackgroundColor(Color.BLACK);
		
		setContentView(drawView);
		//setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_copy) {
			showToast("Touch on the item to copy");
			copyMode=true;
			return true;
		}
		else if (id == R.id.action_stopPaste) {
			showToast("Pasting stopped");
			pasteMode=false;
			return true;
		}
		else if (id == R.id.action_clearData) {
			tryClearAllData();
			return true;
		}
		else if (id == R.id.action_settings) {
			showToast("Not available in this version");
			return true;
		}
		else if (id == R.id.action_exit) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onPause(){
		Log.i("saving", "Saving Data");
		if(dataNeedToBeSaved){
			//Toast.makeText(this, "Saving Data", Toast.LENGTH_LONG).show();
			saveData();
			dataNeedToBeSaved=false;
		}
		super.onPause();
	}
	
	
	private void saveData(){
		Log.i("saving", "saveData() called");
        
		FileOutputStream outStream;
		ObjectOutputStream objectOutStream;
		try {
			outStream = new FileOutputStream(storageFile);
			objectOutStream = new ObjectOutputStream(outStream);
			
			//objectOutStream.writeInt(DrawView.total_days*DrawView.total_timeGaps); // Save size first

			for(int i=0;i<DrawView.total_days;i++){
				for(int j=0;j<DrawView.total_timeGaps;j++){
					objectOutStream.writeObject(items[i][j]);
				}
			}
			//objectOutStream.flush();
			objectOutStream.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadData(){
		Log.i("loading", "loadData() called");
		items=new RoutineItem[DrawView.total_days][DrawView.total_timeGaps];
        
		FileInputStream inputStream;
		ObjectInputStream objectInputStream;
		try {
			inputStream = new FileInputStream(storageFile);
			objectInputStream = new ObjectInputStream(inputStream);
			
			//objectOutStream.writeInt(DrawView.total_days*DrawView.total_timeGaps); // Save size first
			
			for(int i=0;i<DrawView.total_days;i++){
				for(int j=0;j<DrawView.total_timeGaps;j++){
					items[i][j]=(RoutineItem) objectInputStream.readObject();
				}
			}
			
			objectInputStream.close();
		} catch (IOException e) {
/*			Toast.makeText(this, e.getCause().toString()+"\n"+
		storageFile.toString(), Toast.LENGTH_LONG).show();*/
			resetRoutineItems();
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			showToast(e.getMessage());
			resetRoutineItems();
			e.printStackTrace();
		}
	}
	
	private void resetRoutineItems(){
		for(int i=0;i<DrawView.total_days;i++){
			for(int j=0;j<DrawView.total_timeGaps;j++){
				items[i][j]=new RoutineItem();
			}
		}
	}
	
	private void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	private void startItemEditActivity(Intent intent)
	{
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    //settings activity request code was 0
	    if(requestCode==0){
	    	//Toast.makeText(this, "updating", Toast.LENGTH_SHORT).show();
			drawView.postInvalidate();
	    }

	}
	
	class DrawView extends View {
	    Paint paint = new Paint();
	    
	    Rect[][] boxes;
	    
	    
		static final int total_days=6;//time,sat,sun,mon,tue,wed
		static final int total_timeGaps=7;//day,8,9,10,11,12,2
		private int box_width;
		private int box_height;
		
		private String[] days={"SAT","SUN","MON","TUE","WED","THU","FRI"};
		private String[] times={"8:00","9:00","10:00","11:00","12:00","02:00","4:00"};

	    public DrawView(Context context) {
	        super(context);
	        
	        DisplayMetrics displayMetrics=context.getResources().getDisplayMetrics();
	        int width = displayMetrics.widthPixels;
	        int height = displayMetrics.heightPixels;
	    	
	    	boxes=new Rect[total_days][total_timeGaps];
	    	//items=new RoutineItem[total_days][total_timeGaps];
	    	//initialization sent to loadData()
	    	
	    	box_width=width/total_timeGaps;
	    	box_height=height/total_days;
	    	
	    	for(int day=0;day<total_days;day++)
	    	{
	    		int top_coord=day*box_height;
	    		for(int gap=0;gap<total_timeGaps;gap++)
	        	{
	        		int left_coord=gap*box_width;
	        		boxes[day][gap]=new Rect(left_coord,top_coord,
	        				left_coord+box_width,top_coord+box_height);
	        	}
	    	}
	    }

	    @SuppressLint({ "ClickableViewAccessibility", "DrawAllocation" })
		@Override
	    public void onDraw(Canvas canvas) {
	    	
	    	//draw days
	    	//Toast.makeText(getContext(), Boolean.toString(dataChanged), Toast.LENGTH_SHORT).show();
	    	paint.setStrokeWidth(3);
	    	paint.setColor(Color.RED);
	    	paint.setStyle(Paint.Style.STROKE);
	    	
	    	for(int day=0;day<total_days;day++)
	    	{
	    		for(int gap=0;gap<total_timeGaps;gap++)
	    		{
	    			canvas.drawRect(boxes[day][gap],paint);
	    			//canvas.drawText("foo", left_coord, top_coord, paint);
	    		}
	    	}
	    	
	    	paint.setColor(Color.GREEN);
	    	paint.setAntiAlias(true);
	    	paint.setSubpixelText(true);
	    	paint.setStyle(Style.FILL);//for nice font

	    	for(int day=0;day<total_days-1;day++){
	    		int x=boxes[day+1][0].centerX()-20;
	    		int y=boxes[day+1][0].centerY();
	    		canvas.drawText(days[day], x, y, paint);
	    	}
	    	
	    	for(int gap=0;gap<total_timeGaps-1;gap++){
	    		int x=boxes[0][gap+1].centerX()-20;
	    		int y=boxes[0][gap+1].centerY();
	    		canvas.drawText(times[gap], x, y, paint);
	    	}
	    	
	    	for(int day=1;day<total_days;day++)
	    	{
	    		for(int gap=1;gap<total_timeGaps;gap++)
	    		{//TODO text position set nicely 
	    			int x=boxes[day][gap].centerX()-25;
	        		int y=boxes[day][gap].top+20;
	        		RoutineItem r=items[day][gap];
	        		
					if (r.getCourseID().length() != 0) {
						paint.setColor(r.getColorConstant());
						canvas.drawRect(boxes[day][gap], paint);
						paint.setColor(Color.BLACK);

						canvas.drawText(r.getCourseID(), x, y, paint);
						y += 15;
						canvas.drawText(r.getCourseName(), x, y, paint);
						y += 15;
						canvas.drawText(r.getTeachers(), x, y, paint);
					}
	        				
	    			//canvas.drawText("foo", left_coord, top_coord, paint);
	    		}
	    	}
	    	
	    	setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if(event.getAction()==MotionEvent.ACTION_DOWN)
					{
						float x=event.getX();
						float y=event.getY();
						
						int box_y=(int) (x/box_width);//for landscape
						int box_x=(int) (y/box_height);//forlandscape
						
						if(box_x<total_days && box_y<total_timeGaps)
						{
							if(copyMode){
								copiedRoutineItem=items[box_x][box_y];
								copyMode=false;
								pasteMode=true;
								showToast("Copied.\nNow Touch where to paste.");
							}
							else if(pasteMode){
								//copiedRoutineItem can never be null
								items[box_x][box_y]=copiedRoutineItem;
								postInvalidate();
							}
							else{
								Intent intent=new Intent(getContext(),AddToListActivity.class);
								intent.putExtra("day",box_x );
								intent.putExtra("period", box_y);
								
								startItemEditActivity(intent);
								//getContext().startActivity(intent);
							}
							
						}
						//Toast.makeText(getContext(), Integer.toString(box_x)+" "+
						//Integer.toString(box_y), Toast.LENGTH_SHORT).show();
					}
					return false;
				}
			});
	    }
 
	}
	
	private void tryClearAllData() {
		new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle("Clearing All Data")
		.setMessage("All of your saved data will be lost. Do you want to proceed?")
		.setPositiveButton("Yes",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						resetRoutineItems();
						dataNeedToBeSaved=true;
						drawView.postInvalidate();
					}

				}).setNegativeButton("No", null).show();
	}
}
