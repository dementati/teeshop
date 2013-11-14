package com.github.dementati.teeshop;

import java.util.ArrayList;

import com.github.dementati.teeshop.model.Player;
import com.github.dementati.teeshop.model.Round;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.LegendAlign;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class StatsActivity extends Activity {

	private final static int LATEST_ROUNDS = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		
		SharedPreferences settings = getSharedPreferences(SettingsActivity.SETTINGS_FILE, MODE_PRIVATE);
		String profile = settings.getString(SettingsActivity.SETTINGS_PROFILE, "Default");
		
		Player player = new Player(profile);
		player.load(getFilesDir());
		
		ArrayList<GraphViewData> fairwayHitData = new ArrayList<GraphViewData>();
		ArrayList<GraphViewData> greenHitData = new ArrayList<GraphViewData>();
		int n = player.getRounds().size();
		for(int i = (n - 1 - LATEST_ROUNDS >= 0 ? n - 1 - LATEST_ROUNDS : 0); i < n; i++) {
			Round round = player.getRounds().get(i);
			fairwayHitData.add(new GraphViewData(i, round.fairwayHitFreq()));
			greenHitData.add(new GraphViewData(i, round.greenHitFreq()));
		}
		GraphViewSeriesStyle fairwayHitSeriesStyle = new GraphViewSeriesStyle(Color.rgb(0, 153, 204), 4);
		GraphViewSeriesStyle greenHitSeriesStyle = new GraphViewSeriesStyle(Color.rgb(102, 156, 0), 4);
		GraphViewSeries fairwayHitSeries = new GraphViewSeries("Fairway hit freq.", fairwayHitSeriesStyle, fairwayHitData.toArray(new GraphViewData[0]));
		GraphViewSeries greenHitSeries = new GraphViewSeries("Green hit freq.", greenHitSeriesStyle, greenHitData.toArray(new GraphViewData[0]));

		GraphView graphView = new LineGraphView(this, "");
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.BLACK);
		graphView.getGraphViewStyle().setTextSize(getResources().getDimension(R.dimen.graph_text_size));
		graphView.setManualYAxis(true);
		graphView.setManualYAxisBounds(1, 0);
		graphView.setHorizontalLabels(new String[0]);
		graphView.setVerticalLabels(new String[] {"100%", "0%"});
		graphView.addSeries(fairwayHitSeries);
		graphView.addSeries(greenHitSeries);
		
		
		graphView.setShowLegend(true);  
		graphView.setLegendAlign(LegendAlign.TOP);  
		graphView.setLegendWidth(200);  

		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		layout.addView(graphView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch(item.getItemId()) {
			case R.id.action_settings:
				intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				return true;
		
			default:
				return super.onOptionsItemSelected(item);
		}
		
	}

}
