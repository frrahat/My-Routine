package com.Rahat.myroutine;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class FileNamePickerDialog extends DialogFragment {
	
	private EditText nameEditText;
	private String fileName;
	private OnFileNamePickedListener onFileNamePickedListener;
	
	@SuppressLint("InflateParams")
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
		
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View pickerView=inflater.inflate(R.layout.dialog_file_name_picker, null);
        nameEditText=(EditText) pickerView.findViewById(R.id.fileNameEditText);
        
        /*if(fileName!=null){
        	//setting fileName name for quick input
	        nameEditText.setText(fileName);
			
			int cursorIndex=fileName.lastIndexOf(".");
			if(cursorIndex>=0)
				nameEditText.setSelection(cursorIndex);
        }
        else{
        	nameEditText.setText(getResources().getText(R.string.fileNameHint));
        	nameEditText.setSelection(nameEditText.length());
        }*/
        
        nameEditText.setText(getResources().getText(R.string.fileNameHint));
    	nameEditText.setSelection(nameEditText.length());
        
        builder.setView(pickerView)
        // Add action buttons
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int id) {
                	   if(onFileNamePickedListener!=null)
                	   onFileNamePickedListener.OnFileNamePicked(nameEditText.getText().toString());
                   }
               })
               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                       FileNamePickerDialog.this.getDialog().cancel();
                   }
               });   
              
        return builder.create();
    }
	/*
	public void setFileName(String fileName){
		this.fileName=fileName;
	}
	
	public EditText getFocusingEditText(){
		return nameEditText;
	}
	*/
	
	//interface
	public static interface OnFileNamePickedListener{
		public void OnFileNamePicked(String fileName);
	}
	public void setOnFileNamePickedListener(OnFileNamePickedListener listener){
		this.onFileNamePickedListener=listener;
	}
}

