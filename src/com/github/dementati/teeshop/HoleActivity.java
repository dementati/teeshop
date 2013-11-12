package com.github.dementati.teeshop;

import java.util.Calendar;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewDebug.FlagToString;
import android.widget.CheckBox;
import android.widget.EditText;

public class HoleActivity extends ActionBarActivity {
	public final static String ROUND = "com.github.dementati.teeshop.ROUND";
	public final static String HOLE_INDEX = "com.github.dementati.teeshop.HOLE_INDEX";
	public final static String CHANGED = "com.github.dementati.teeshop.CHANGED";
	
	private final static String FAIRWAY_HIT = "com.github.dementati.teeshop.FAIRWAY_HIT";
	private final static String FAIRWAY_HIT_DIST = "com.github.dementati.teeshop.FAIRWAY_HIT_DIST";
	private final static String GREEN_HIT = "com.github.dementati.teeshop.GREEN_HIT";
	private final static String GREEN_HIT_DIST = "com.github.dementati.teeshop.GREEN_HIT_DIST";
	private final static String PUTT_COUNT = "com.github.dementati.teeshop.PUTT_COUNT";
	
	private Round round;
	private int holeIndex = -1;
	private boolean changed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//restoreState(savedInstanceState);
		
		setContentView(R.layout.activity_hole);
		
		Intent intent = getIntent();
		if(intent.hasExtra(ROUND)) {
			round = (Round)intent.getSerializableExtra(ROUND);
			
			if(intent.hasExtra(HOLE_INDEX)) {
				holeIndex = intent.getIntExtra(HOLE_INDEX, round.getHoles().size());
			} else {
				holeIndex = round.getHoles().size();
			}
		} else {
			Calendar date = Calendar.getInstance();
			round = new Round(date);
			holeIndex = 0;
		}

		changed = intent.getBooleanExtra(CHANGED, false);
		Log.d("HoleActivity", "changed = " + String.valueOf(changed));
		
		initializeActivity();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.hole, menu);
		
		if(holeIndex == 0) {
			menu.findItem(R.id.action_prev_hole).setVisible(false);
		} else if(holeIndex == 17) {
			menu.findItem(R.id.action_next_hole).setVisible(false);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch(item.getItemId()) {
			case R.id.action_prev_hole:
				if(saveData()) {
					changed = true;
				}
				holeIndex--;
				supportInvalidateOptionsMenu();
				initializeActivity();
				return true;
				
			case R.id.action_round:
				goToRound();
				return true;
				
			case R.id.action_next_hole:
				if(saveData()) {
					changed = true;
				}
				holeIndex++;
				supportInvalidateOptionsMenu();
				initializeActivity();
				return true;
		
			case R.id.action_settings:
				intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goToRound();
            return true;
        } else {
        	return super.onKeyDown(keyCode, event);
        }
    }
	
	/*
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putBoolean(FAIRWAY_HIT, getFairwayHit());
		
		Float fairwayHitDist = getFairwayHitDist();
		if(fairwayHitDist == null) {
			outState.putFloat(FAIRWAY_HIT_DIST, -1F);
		} else {
			outState.putFloat(FAIRWAY_HIT_DIST, fairwayHitDist);
		}
		
		outState.putBoolean(GREEN_HIT, getGreenHit());
		
		Float greenHitDist = getGreenHitDist();
		if(greenHitDist == null) {
			outState.putFloat(GREEN_HIT_DIST, Float.NaN);
		} else {
			outState.putFloat(GREEN_HIT_DIST, greenHitDist);
		}
		
		Integer puttCount = getPuttCount();
		if(puttCount == null) {
			outState.putInt(PUTT_COUNT, 0);
		} else {
			outState.putInt(PUTT_COUNT, getPuttCount());
		}
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		restoreState(savedInstanceState);
		
		super.onRestoreInstanceState(savedInstanceState);
	}
	*/
	
	private void restoreState(Bundle savedInstanceState) {
		if(savedInstanceState == null) {
			return;
		}
		
		if(savedInstanceState.containsKey(FAIRWAY_HIT)) {
			setFairwayHit(savedInstanceState.getBoolean(FAIRWAY_HIT));
		}
		
		if(savedInstanceState.containsKey(FAIRWAY_HIT_DIST)) {
			float fairwayHitDist = savedInstanceState.getFloat(FAIRWAY_HIT_DIST);
			if(fairwayHitDist == Float.NaN) {
				setFairwayHitDist(null);
			} else {
				setFairwayHitDist(fairwayHitDist);
			}
		}
		
		if(savedInstanceState.containsKey(GREEN_HIT)) {
			setGreenHit(savedInstanceState.getBoolean(GREEN_HIT));
		}
		
		if(savedInstanceState.containsKey(GREEN_HIT_DIST)) {
			float greenHitDist = savedInstanceState.getFloat(GREEN_HIT_DIST);
			if(greenHitDist == Float.NaN) {
				setGreenHitDist(null);
			} else {
				setGreenHitDist(greenHitDist);
			}
		}
		
		if(savedInstanceState.containsKey(PUTT_COUNT)) {
			int puttCount = savedInstanceState.getInt(PUTT_COUNT);
			if(puttCount == 0) {
				setPuttCount(null);
			} else {
				setPuttCount(puttCount);
			}
		}
	}
	
	private void goToRound() {
		if(saveData()) {
			changed = true;
		}
		Intent intent = new Intent(this, RoundActivity.class);
		intent.putExtra(ROUND, round);
		
		if(getIntent().hasExtra(RoundActivity.ROUND_INDEX)) {
			intent.putExtra(RoundActivity.ROUND_INDEX, getIntent().getIntExtra(RoundActivity.ROUND_INDEX, -1));
		}
		
		intent.putExtra(CHANGED, changed);
		
		startActivity(intent);
		finish();
	}
	
	private void initializeActivity() {
		ActionBar ab = getSupportActionBar();
		ab.setTitle(getString(R.string.hole) + " " + (holeIndex + 1));
		
		CheckBox fairwayHitCheckBox = (CheckBox)findViewById(R.id.fairway_hit_checkbox);
		EditText fairwayHitDistEdit = (EditText)findViewById(R.id.fairway_hit_dist_edit);
		CheckBox greenHitCheckBox = (CheckBox)findViewById(R.id.green_hit_checkbox);
		EditText greenHitDistEdit = (EditText)findViewById(R.id.green_hit_dist_edit);
		EditText puttCountEdit = (EditText)findViewById(R.id.putt_count_edit);
		
		if(round.getHoles().size() > holeIndex) {
			Hole hole = round.getHoles().get(holeIndex);
			
			fairwayHitCheckBox.setChecked(hole.isFairwayHit());
			
			if(hole.getFairwayHitDist() != null) {
				fairwayHitDistEdit.setText(String.valueOf(hole.getFairwayHitDist()));
			} else {
				fairwayHitDistEdit.setText("");
			}
			
			greenHitCheckBox.setChecked(hole.isGreenHit());
			
			if(hole.getGreenHitDist() != null) {
				greenHitDistEdit.setText(String.valueOf(hole.getGreenHitDist()));
			} else {
				greenHitDistEdit.setText("");
			}
			
			if(hole.getPuttCount() != null) {
				puttCountEdit.setText(String.valueOf(hole.getPuttCount()));
			} else {
				puttCountEdit.setText("");
			}
		} else {
			fairwayHitCheckBox.setChecked(false);
			fairwayHitDistEdit.setText("");
			greenHitCheckBox.setChecked(false);
			greenHitDistEdit.setText("");
			puttCountEdit.setText("");
		}
	}
	
	// Returns true if data changed.
	private boolean saveData() {		
		Hole oldHole = round.getHoles().get(holeIndex);
		Log.d("HoleActivity", "oldHole = " + oldHole);
		Hole newHole = 
				new Hole(getFairwayHit(), 
						 getFairwayHitDist(), 
						 getGreenHit(), 
						 getGreenHitDist(), 
						 getPuttCount());
		Log.d("HoleActivity", "newHole = " + oldHole);
		round.getHoles().set(holeIndex, newHole);
		Log.d("HoleActivity", "oldHole.equals(newHole) = " + oldHole.equals(newHole));
		
		return !oldHole.equals(newHole);
	}
	
	private Boolean getFairwayHit() {
		CheckBox fairwayHitCheckBox = (CheckBox)findViewById(R.id.fairway_hit_checkbox);
		return fairwayHitCheckBox.isChecked();
	}
	
	private void setFairwayHit(Boolean fairwayHit) {
		CheckBox fairwayHitCheckBox = (CheckBox)findViewById(R.id.fairway_hit_checkbox);
		fairwayHitCheckBox.setChecked(fairwayHit);
	}
	
	private Float getFairwayHitDist() {
		EditText fairwayHitDistEdit = (EditText)findViewById(R.id.fairway_hit_dist_edit);
		String fairwayHitDistStr = fairwayHitDistEdit.getText().toString();
		Float fairwayHitDist = null;
		if(!fairwayHitDistStr.isEmpty()) {
			fairwayHitDist = Float.valueOf(fairwayHitDistStr);
		}
		
		return fairwayHitDist;
	}
	
	private void setFairwayHitDist(Float fairwayHitDist) {
		EditText fairwayHitDistEdit = (EditText)findViewById(R.id.fairway_hit_dist_edit);
		if(fairwayHitDist == null) {
			fairwayHitDistEdit.setText("");
		} else {
			fairwayHitDistEdit.setText(fairwayHitDist.toString());
		}
	}
	
	private Boolean getGreenHit() {
		CheckBox greenHitCheckBox = (CheckBox)findViewById(R.id.green_hit_checkbox);
		return greenHitCheckBox.isChecked();
	}
	
	private void setGreenHit(Boolean greenHit) {
		CheckBox greenHitCheckBox = (CheckBox)findViewById(R.id.green_hit_checkbox);
		greenHitCheckBox.setChecked(greenHit);
	}
	
	private Float getGreenHitDist() {
		EditText greenHitDistEdit = (EditText)findViewById(R.id.green_hit_dist_edit);
		String greenHitDistStr = greenHitDistEdit.getText().toString();
		Float greenHitDist = null;
		if(!greenHitDistStr.isEmpty()) {
			greenHitDist = Float.valueOf(greenHitDistStr);
		}
		
		return greenHitDist;
	}
	
	private void setGreenHitDist(Float greenHitDist) {
		EditText greenHitDistEdit = (EditText)findViewById(R.id.green_hit_dist_edit);
		if(greenHitDist == null) {
			greenHitDistEdit.setText("");
		} else {
			greenHitDistEdit.setText(greenHitDist.toString());
		}
	}
	
	private Integer getPuttCount() {
		EditText puttCountEdit = (EditText)findViewById(R.id.putt_count_edit);
		String puttCountStr = puttCountEdit.getText().toString();
		Integer puttCount = null;
		if(!puttCountStr.isEmpty()) {
			puttCount = Integer.valueOf(puttCountStr);
		}
		
		return puttCount;
	}
	
	private void setPuttCount(Integer puttCount) {
		EditText puttCountEdit = (EditText)findViewById(R.id.putt_count_edit);
		if(puttCount == null) {
			puttCountEdit.setText("");
		} else {
			puttCountEdit.setText(puttCount);
		}
	}
}
