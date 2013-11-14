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
import android.widget.CheckBox;
import android.widget.EditText;

public class HoleActivity extends ActionBarActivity {
	public final static String ROUND = "com.github.dementati.teeshop.ROUND";
	public final static String HOLE_INDEX = "com.github.dementati.teeshop.HOLE_INDEX";
	public final static String CHANGED = "com.github.dementati.teeshop.CHANGED";
	
	private Round round;
	private int holeIndex = -1;
	private boolean changed;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		EditText parEdit = (EditText)findViewById(R.id.par_edit);
		EditText holeDistanceEdit = (EditText)findViewById(R.id.hole_distance_edit);
		
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
			
			if(hole.getPar() != null) {
				parEdit.setText(String.valueOf(hole.getPar()));
			} else {
				parEdit.setText("");
			}
			
			if(hole.getHoleDistance() != null) {
				holeDistanceEdit.setText(String.valueOf(hole.getHoleDistance()));
			} else {
				holeDistanceEdit.setText("");
			}
		} else {
			fairwayHitCheckBox.setChecked(false);
			fairwayHitDistEdit.setText("");
			greenHitCheckBox.setChecked(false);
			greenHitDistEdit.setText("");
			puttCountEdit.setText("");
			parEdit.setText("");
			holeDistanceEdit.setText("");
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
						 getPuttCount(),
						 getPar(),
						 getHoleDistance());
		Log.d("HoleActivity", "newHole = " + oldHole);
		round.getHoles().set(holeIndex, newHole);
		Log.d("HoleActivity", "oldHole.equals(newHole) = " + oldHole.equals(newHole));
		
		return !oldHole.equals(newHole);
	}
	
	private Boolean getFairwayHit() {
		CheckBox fairwayHitCheckBox = (CheckBox)findViewById(R.id.fairway_hit_checkbox);
		return fairwayHitCheckBox.isChecked();
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
	
	private Boolean getGreenHit() {
		CheckBox greenHitCheckBox = (CheckBox)findViewById(R.id.green_hit_checkbox);
		return greenHitCheckBox.isChecked();
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
	
	private Integer getPuttCount() {
		EditText puttCountEdit = (EditText)findViewById(R.id.putt_count_edit);
		String puttCountStr = puttCountEdit.getText().toString();
		Integer puttCount = null;
		if(!puttCountStr.isEmpty()) {
			puttCount = Integer.valueOf(puttCountStr);
		}
		
		return puttCount;
	}
	
	private Integer getPar() {
		EditText parEdit = (EditText)findViewById(R.id.par_edit);
		String parStr = parEdit.getText().toString();
		Integer par = null;
		if(!parStr.isEmpty()) {
			par = Integer.valueOf(parStr);
		}
		
		return par;
	}
	
	private Float getHoleDistance() {
		EditText holeDistanceEdit = (EditText)findViewById(R.id.hole_distance_edit);
		String holeDistanceStr = holeDistanceEdit.getText().toString();
		Float holeDistance = null;
		if(!holeDistanceStr.isEmpty()) {
			holeDistance = Float.valueOf(holeDistanceStr);
		}
		
		return holeDistance;
	}
}
