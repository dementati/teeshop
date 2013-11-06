package com.github.dementati.teeshop;

import java.util.Calendar;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;

public class HoleActivity extends ActionBarActivity {
	public final static String ROUND = "com.github.dementati.teeshop.ROUND";
	public final static String HOLE_INDEX = "com.github.dementati.teeshop.HOLE_INDEX";
	
	private Round round;
	private int holeIndex = -1;
	
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
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		initializeActivity();
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
		switch(item.getItemId()) {
			case R.id.action_prev_hole:
				saveData();
				holeIndex--;
				supportInvalidateOptionsMenu();
				initializeActivity();
				return true;
				
			case R.id.action_round:
				saveData();
				Intent intent = new Intent(this, RoundActivity.class);
				intent.putExtra(ROUND, round);
				startActivity(intent);
				return true;
				
			case R.id.action_next_hole:
				saveData();
				holeIndex++;
				supportInvalidateOptionsMenu();
				initializeActivity();
				return true;
		
			default:
				return super.onOptionsItemSelected(item);
		}
		
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
	
	private void saveData() {		
		CheckBox fairwayHitCheckBox = (CheckBox)findViewById(R.id.fairway_hit_checkbox);
		Boolean fairwayHit = fairwayHitCheckBox.isChecked();
		
		EditText fairwayHitDistEdit = (EditText)findViewById(R.id.fairway_hit_dist_edit);
		String fairwayHitDistStr = fairwayHitDistEdit.getText().toString();
		Float fairwayHitDist = null;
		if(!fairwayHitDistStr.isEmpty()) {
			fairwayHitDist = Float.valueOf(fairwayHitDistStr);
		}
		
		CheckBox greenHitCheckBox = (CheckBox)findViewById(R.id.green_hit_checkbox);
		Boolean greenHit = greenHitCheckBox.isChecked();
		
		EditText greenHitDistEdit = (EditText)findViewById(R.id.green_hit_dist_edit);
		String greenHitDistStr = greenHitDistEdit.getText().toString();
		Float greenHitDist = null;
		if(!greenHitDistStr.isEmpty()) {
			greenHitDist = Float.valueOf(greenHitDistStr);
		}
		
		EditText puttCountEdit = (EditText)findViewById(R.id.putt_count_edit);
		String puttCountStr = puttCountEdit.getText().toString();
		Integer puttCount = null;
		if(!puttCountStr.isEmpty()) {
			puttCount = Integer.valueOf(puttCountStr);
		}
		
		Hole hole = new Hole(fairwayHit, fairwayHitDist, greenHit, greenHitDist, puttCount);
		
		if(round.getHoles().size() < holeIndex) {
			throw new IllegalStateException("holeIndex can only be one greater than the existing number of holes in the current round.");
		} else if(round.getHoles().size() == holeIndex) {
			round.addHole(hole);
		} else {
			round.getHoles().set(holeIndex, hole);
		}
	}
}
