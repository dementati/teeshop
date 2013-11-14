package com.github.dementati.teeshop;


import com.github.dementati.teeshop.model.Player;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class RoundListActivity extends ActionBarActivity {

	private Player player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_list);
		
		ActionBar ab = getSupportActionBar();
		ab.setTitle("Rounds");
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		initialize();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		
		initialize();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round_list, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_stats:
				Intent intent = new Intent(RoundListActivity.this, StatsActivity.class);
				startActivity(intent);
				return true;
				
			case R.id.action_settings:
				intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				return true;
		
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	private void initialize() {
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_FILE, MODE_PRIVATE);
		String profile = settings.getString(SettingsActivity.SETTINGS_PROFILE, "Default");
		
		player = new Player(profile);
		player.load(getFilesDir());
		
		final TableLayout t = (TableLayout)findViewById(R.id.round_list_table);
		t.removeAllViews();
		
		int roundIndex = 0;
		for(Round round : player.getRounds()) {
			final Round fRound = round;
			final int fRoundIndex = roundIndex;
			
			TableRow tr = new TableRow(this);
			final TableRow fTr = tr;
			
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tr.setGravity(Gravity.CENTER_VERTICAL);
			tr.setBackgroundResource(R.drawable.on_click_highlight);
			tr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(RoundListActivity.this, RoundActivity.class);
					intent.putExtra(HoleActivity.ROUND, fRound);
					intent.putExtra(RoundActivity.ROUND_INDEX, fRoundIndex);
					startActivity(intent);
					finish();
				}
			});
			
			TextView roundDateText = new TextView(this);
			LayoutParams roundDateParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			roundDateParams.gravity = Gravity.CENTER_VERTICAL;
			roundDateParams.weight = 1;
			roundDateText.setLayoutParams(roundDateParams);
			roundDateText.setText(round.getDateString());
			
			ImageView deleteImage = new ImageView(this);
			LayoutParams deleteImageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			deleteImageParams.gravity = Gravity.CENTER_VERTICAL;
			deleteImage.setLayoutParams(deleteImageParams);
			deleteImage.setImageResource(R.drawable.ic_delete);
			deleteImage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(RoundListActivity.this);
					builder.setMessage(R.string.delete_dialog_message);
					builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							player.getRounds().remove(fRound);
							t.removeView(fTr);
							player.save(getFilesDir());
						}
					});
					
					builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					builder.create().show();
				}
			});
			
			tr.addView(roundDateText);
			tr.addView(deleteImage);
			t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			
			roundIndex++;
		}
	}
}
