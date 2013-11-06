package com.github.dementati.teeshop;

import java.text.SimpleDateFormat;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class RoundActivity extends ActionBarActivity {

	private Round round;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round);
		
		Intent intent = getIntent();
		round = (Round)intent.getSerializableExtra(HoleActivity.ROUND);
		
		ActionBar ab = getSupportActionBar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ab.setTitle(sdf.format(round.getDate().getTime()));
		
		TableLayout t = (TableLayout)findViewById(R.id.round_table);
		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		
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
					startActivity(intent);
				}
			});
			
			TextView holeIndexText = new TextView(this);
			holeIndexText.setLayoutParams(lp);
			holeIndexText.setText(String.valueOf(holeIndex+1));
			holeIndex++;
			
			ImageView fairwayHitImage = new ImageView(this);
			if(hole.isFairwayHit()) {
				fairwayHitImage.setImageResource(R.drawable.ic_checked);
			}
			
			TextView fairwayHitDistText = new TextView(this);
			fairwayHitDistText.setLayoutParams(lp);
			String fairwayHitDistStr = "";
			if(hole.getFairwayHitDist() != null) {
				fairwayHitDistStr = String.valueOf(hole.getFairwayHitDist());
			}
			fairwayHitDistText.setText(fairwayHitDistStr);
			
			ImageView greenHitImage = new ImageView(this);
			if(hole.isGreenHit()) {
				greenHitImage.setImageResource(R.drawable.ic_checked);
			}
			
			TextView greenHitText = new TextView(this);
			greenHitText.setLayoutParams(lp);
			greenHitText.setText(String.valueOf(hole.isGreenHit()));
			
			TextView greenHitDistText = new TextView(this);
			greenHitDistText.setLayoutParams(lp);
			String greenHitDistStr = "";
			if(hole.getGreenHitDist() != null) {
				greenHitDistStr = String.valueOf(hole.getGreenHitDist());
			}
			greenHitDistText.setText(greenHitDistStr);
			
			TextView puttCountText = new TextView(this);
			puttCountText.setLayoutParams(lp);
			String puttCountStr = "";
			if(hole.getPuttCount() != null) {
				puttCountStr = String.valueOf(hole.getPuttCount());
			}
			puttCountText.setText(puttCountStr);
			
			tr.addView(holeIndexText);
			tr.addView(fairwayHitImage);
			tr.addView(fairwayHitDistText);
			tr.addView(greenHitImage);
			tr.addView(greenHitDistText);
			tr.addView(puttCountText);
			t.addView(tr, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round, menu);
		return true;
	}

}
