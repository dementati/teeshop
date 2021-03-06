package com.github.dementati.teeshop;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Player;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class RoundActivity extends ActionBarActivity {
	public final static String ROUND_INDEX = "com.github.dementati.teeshop.ROUND_INDEX"; 
	
	private Round round;
	private boolean changed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round);
		
		Intent intent = getIntent();
		
		changed = intent.getBooleanExtra(HoleActivity.CHANGED, false);
		
		Log.d("RoundActivity", "changed = " + String.valueOf(changed));
		
		round = (Round)intent.getSerializableExtra(HoleActivity.ROUND);
		
		ActionBar ab = getSupportActionBar();
		ab.setTitle(round.getDateString());
		
		TableLayout t = (TableLayout)findViewById(R.id.round_table);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		
		int holeIndex = 0;
		for(Hole hole : round.getHoles()) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(lp);
			tr.setGravity(Gravity.CENTER_VERTICAL);
			tr.setBackgroundResource(R.drawable.on_click_highlight);
			final int fHoleIndex = holeIndex;
			tr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(RoundActivity.this, HoleActivity.class);
					intent.putExtra(HoleActivity.ROUND, round);
					intent.putExtra(HoleActivity.HOLE_INDEX, fHoleIndex);
					intent.putExtra(HoleActivity.CHANGED, changed);
					
					if(getIntent().hasExtra(RoundActivity.ROUND_INDEX)) {
						intent.putExtra(RoundActivity.ROUND_INDEX, getIntent().getIntExtra(RoundActivity.ROUND_INDEX, -1));
					}
					
					startActivity(intent);
					finish();
				}
			});
			
			TextView holeIndexText = new TextView(this);
			holeIndexText.setLayoutParams(lp);
			holeIndexText.setText(String.valueOf(holeIndex+1));
			holeIndexText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			holeIndex++;
			
			ImageView fairwayHitImage = new ImageView(this);
			if(hole.isFairwayHit()) {
				fairwayHitImage.setImageResource(R.drawable.ic_checked);
			} else {
				fairwayHitImage.setImageResource(R.drawable.ic_unchecked);
			}
			
			TextView fairwayHitDistText = new TextView(this);
			fairwayHitDistText.setLayoutParams(lp);
			String fairwayHitDistStr = "";
			if(hole.getFairwayHitDist() != null) {
				fairwayHitDistStr = String.valueOf(hole.getFairwayHitDist());
			}
			fairwayHitDistText.setText(fairwayHitDistStr);
			fairwayHitDistText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			ImageView transportHitImage = new ImageView(this);
			if(hole.isTransportHit()) {
				transportHitImage.setImageResource(R.drawable.ic_checked);
			} else {
				transportHitImage.setImageResource(R.drawable.ic_unchecked);
			}
			
			TextView transportHitDistText = new TextView(this);
			transportHitDistText.setLayoutParams(lp);
			String transportHitDistStr = "";
			if(hole.getTransportHitDist() != null) {
				transportHitDistStr = String.valueOf(hole.getTransportHitDist());
			}
			transportHitDistText.setText(transportHitDistStr);
			transportHitDistText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			ImageView greenHitImage = new ImageView(this);
			if(hole.isGreenHit()) {
				greenHitImage.setImageResource(R.drawable.ic_checked);
			} else {
				greenHitImage.setImageResource(R.drawable.ic_unchecked);
			}
			
			TextView greenHitDistText = new TextView(this);
			greenHitDistText.setLayoutParams(lp);
			String greenHitDistStr = "";
			if(hole.getGreenHitDist() != null) {
				greenHitDistStr = String.valueOf(hole.getGreenHitDist());
			}
			greenHitDistText.setText(greenHitDistStr);
			greenHitDistText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			TextView puttCountText = new TextView(this);
			puttCountText.setLayoutParams(lp);
			String puttCountStr = "";
			if(hole.getPuttCount() != null) {
				puttCountStr = String.valueOf(hole.getPuttCount());
			}
			puttCountText.setText(puttCountStr);
			puttCountText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			TextView parText = new TextView(this);
			parText.setLayoutParams(lp);
			String parStr = "";
			if(hole.getPar() != null) {
				parStr = String.valueOf(hole.getPar());
			}
			parText.setText(parStr);
			parText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			TextView holeDistanceText = new TextView(this);
			holeDistanceText.setLayoutParams(lp);
			String holeDistanceStr = "";
			if(hole.getHoleDistance() != null) {
				holeDistanceStr = String.valueOf(hole.getHoleDistance());
			}
			holeDistanceText.setText(holeDistanceStr);
			holeDistanceText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
			
			tr.addView(holeIndexText);
			tr.addView(fairwayHitImage);
			tr.addView(fairwayHitDistText);
			tr.addView(transportHitImage);
			tr.addView(transportHitDistText);
			tr.addView(greenHitImage);
			tr.addView(greenHitDistText);
			tr.addView(puttCountText);
			tr.addView(parText);
			tr.addView(holeDistanceText);
			t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
			case R.id.action_save:
				goToRoundList();
				return true;
		
			case R.id.action_settings:
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				return true;
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToRoundList();
            return true;
        } else {
        	return super.onKeyDown(keyCode, event);
        }
    }
	
	private void goToRoundList() {
		if(changed) {
			save();
		} else {
			Intent intent = new Intent(RoundActivity.this, RoundListActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void save() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.save_dialog_message);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_FILE, MODE_PRIVATE);
				String profile = settings.getString(SettingsActivity.SETTINGS_PROFILE, "Default");
				
				Player player = new Player(profile);
				
				player.load(getFilesDir());
				
				Intent intent = RoundActivity.this.getIntent();
				if(intent.hasExtra(ROUND_INDEX)) {
					int roundIndex = intent.getIntExtra(ROUND_INDEX, -1);
					player.getRounds().set(roundIndex, round);
				} else {
					player.addRound(round);
				}
				
				player.save(getFilesDir());
				
				Intent newIntent = new Intent(RoundActivity.this, RoundListActivity.class);
				startActivity(newIntent);
				finish();
			}
		});
		
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
