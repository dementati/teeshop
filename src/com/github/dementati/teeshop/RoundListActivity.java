package com.github.dementati.teeshop;

import java.text.SimpleDateFormat;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Player;
import com.github.dementati.teeshop.model.Round;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

public class RoundListActivity extends Activity {

	private Player player;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_list);
		
		player = new Player(RoundActivity.PROFILE);
		player.load(getFilesDir());
		
		TableLayout t = (TableLayout)findViewById(R.id.round_list_table);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		int roundIndex = 0;
		for(Round round : player.getRounds()) {
			TableRow tr = new TableRow(this);
			tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			tr.setGravity(Gravity.CENTER_VERTICAL);
			tr.setBackgroundResource(R.drawable.on_click_highlight);
			
			final Round fRound = round;
			final int fRoundIndex = roundIndex;
			tr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(RoundListActivity.this, RoundActivity.class);
					intent.putExtra(HoleActivity.ROUND, fRound);
					intent.putExtra(RoundActivity.ROUND_INDEX, fRoundIndex);
					startActivity(intent);
				}
			});
			roundIndex++;
			
			TextView roundDateText = new TextView(this);
			LayoutParams roundDateParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			roundDateParams.gravity = Gravity.CENTER_VERTICAL;
			roundDateParams.weight = 1;
			roundDateText.setLayoutParams(roundDateParams);
			String roundDateStr = sdf.format(round.getDate().getTime());
			roundDateText.setText(roundDateStr);
			
			ImageView deleteImage = new ImageView(this);
			LayoutParams deleteImageParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			deleteImageParams.gravity = Gravity.CENTER_VERTICAL;
			deleteImage.setLayoutParams(deleteImageParams);
			deleteImage.setImageResource(R.drawable.ic_delete);
			
			tr.addView(roundDateText);
			tr.addView(deleteImage);
			t.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round_list, menu);
		return true;
	}

}
