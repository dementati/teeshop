package com.github.dementati.teeshop;

import java.io.File;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

public class SettingsActivity extends Activity {
	public final static String SETTINGS_PROFILE = "com.github.dementati.teeshop.SETTINGS_PROFILE";
	public final static String SETTINGS_FILE = "com.github.dementati.teeshop.SETTINGS_FILE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme);
		setContentView(R.layout.activity_settings);
		
		ArrayAdapter<String> adapter = 
				new ArrayAdapter<String>(this,  
										 android.R.layout.simple_dropdown_item_1line, 
										 getFilesDir().list());
		adapter.notifyDataSetChanged();
		
		AutoCompleteTextView profileEdit = (AutoCompleteTextView)findViewById(R.id.profile_edit);
		profileEdit.setAdapter(adapter);
		profileEdit.setThreshold(1);
		
		SharedPreferences settings = getSharedPreferences(SETTINGS_FILE, MODE_PRIVATE);
		profileEdit.setText(settings.getString(SETTINGS_PROFILE, "Default"));
		
		profileEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String profileStr = s.toString().trim();
				if(profileStr.isEmpty()) {
					profileStr = "Default";
				}
				
				SharedPreferences settings = getSharedPreferences(SETTINGS_FILE, MODE_PRIVATE);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(SETTINGS_PROFILE, profileStr);
				editor.commit();
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
		});
		
		profileEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if(actionId == EditorInfo.IME_ACTION_DONE) {
					
				}
				return false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
}
