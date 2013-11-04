package com.github.dementati.teeshop.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.github.dementati.teeshop.model.Hole;
import com.github.dementati.teeshop.model.Player;
import com.github.dementati.teeshop.model.Round;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {
	private File dir;
	private String playerName;
	private Player player;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		dir = new File(".");
		playerName = "Bob";
		player = new Player(playerName);
	} 
	
	public void testSaveEmpty() {
		player.save(dir);
		try {
			String fileStr = readFile(playerName);
			
			assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player/>", fileStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testSaveOneEmptyRound() {
		try {
			String dateStr = "2013-11-04";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			Round round = new Round(date);
			
			player.addRound(round);
			player.save(dir);
			
			String fileStr = readFile(playerName);
			
			assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"/></Player>", fileStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void testSaveOneRoundOneHole() {
		try {
			String dateStr = "2013-11-04";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			Round round = new Round(date);
			
			boolean fairwayHit = true;
			float fairwayHitDist = 140.5f;
			boolean greenHit = true;
			float greenHitDist = 10.5f;
			int puttCount = 2;
			Hole hole = new Hole(fairwayHit, fairwayHitDist, greenHit, greenHitDist, puttCount);
			
			round.addHole(hole);
			player.addRound(round);
			player.save(dir);
			
			String fileStr = readFile(playerName);
			
			assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/></Round></Player>", fileStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void testSaveTwoRoundsOneHole() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String dateStr = "2013-11-04";
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			Round round1 = new Round(date);
			
			boolean fairwayHit = true;
			float fairwayHitDist = 140.5f;
			boolean greenHit = true;
			float greenHitDist = 10.5f;
			int puttCount = 2;
			Hole hole1 = new Hole(fairwayHit, fairwayHitDist, greenHit, greenHitDist, puttCount);
			
			round1.addHole(hole1);
			player.addRound(round1);
			
			String dateStr2 = "2013-11-03";
			Calendar date2 = Calendar.getInstance();
			date2.setTime(sdf.parse(dateStr2));
			Round round2 = new Round(date2);
			
			boolean fairwayHit2 = false;
			float fairwayHitDist2 = 145.7f;
			boolean greenHit2 = false;
			float greenHitDist2 = 9.0f;
			int puttCount2 = 3;
			Hole hole2 = new Hole(fairwayHit2, fairwayHitDist2, greenHit2, greenHitDist2, puttCount2);
			
			round2.addHole(hole2);
			player.addRound(round2);
			
			player.save(dir);
			
			String fileStr = readFile(playerName);
			
			assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/></Round><Round date=\"2013-11-03\"><Hole fairwayHit=\"false\" fairwayHitDist=\"145.7\" greenHit=\"false\" greenHitDist=\"9.0\" puttCount=\"3\"/></Round></Player>", fileStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void testSaveOneRoundTwoHoles() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			String dateStr = "2013-11-04";
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			Round round = new Round(date);
			
			boolean fairwayHit = true;
			float fairwayHitDist = 140.5f;
			boolean greenHit = true;
			float greenHitDist = 10.5f;
			int puttCount = 2;
			Hole hole1 = new Hole(fairwayHit, fairwayHitDist, greenHit, greenHitDist, puttCount);
			
			boolean fairwayHit2 = false;
			float fairwayHitDist2 = 145.7f;
			boolean greenHit2 = false;
			float greenHitDist2 = 9.0f;
			int puttCount2 = 3;
			Hole hole2 = new Hole(fairwayHit2, fairwayHitDist2, greenHit2, greenHitDist2, puttCount2);
			
			round.addHole(hole1);
			round.addHole(hole2);
			player.addRound(round);
			
			player.save(dir);
			
			String fileStr = readFile(playerName);
			
			assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/><Hole fairwayHit=\"false\" fairwayHitDist=\"145.7\" greenHit=\"false\" greenHitDist=\"9.0\" puttCount=\"3\"/></Round></Player>", fileStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void testLoadEmpty() {
		try {
			String fileStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player/>";
			
			writeFile(playerName, fileStr);
			player.load(dir);
			
			assertEquals("Bob", player.getName());
			assertEquals(0, player.getRounds().size());		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLoadOneEmptyRound() {
		try {
			String fileStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"/></Player>";
			
			writeFile(playerName, fileStr);
			player.load(dir);
			
			assertEquals("Bob", player.getName());
			assertEquals(1, player.getRounds().size());
			
			String dateStr = "2013-11-04";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			assertEquals(0, date.compareTo(player.getRounds().get(0).getDate()));
			
			assertEquals(0, player.getRounds().get(0).getHoles().size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLoadOneRoundOneHole() {
		try {
			String fileStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/></Round></Player>";
			
			writeFile(playerName, fileStr);
			player.load(dir);
			
			assertEquals("Bob", player.getName());
			assertEquals(1, player.getRounds().size());
			
			String dateStr = "2013-11-04";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			assertEquals(0, date.compareTo(player.getRounds().get(0).getDate()));
			
			assertEquals(1, player.getRounds().get(0).getHoles().size());
			
			Hole hole = player.getRounds().get(0).getHoles().get(0);
			assertTrue(hole.isFairwayHit());
			assertEquals(140.5f, hole.getFairwayHitDist());
			assertTrue(hole.isGreenHit());
			assertEquals(10.5f, hole.getGreenHitDist());
			assertEquals(2, hole.getPuttCount());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLoadTwoRoundsOneHole() {
		try {
			String fileStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/></Round><Round date=\"2013-11-03\"><Hole fairwayHit=\"false\" fairwayHitDist=\"145.7\" greenHit=\"false\" greenHitDist=\"9.0\" puttCount=\"3\"/></Round></Player>";
			
			writeFile(playerName, fileStr);
			player.load(dir);
			
			assertEquals("Bob", player.getName());
			assertEquals(2, player.getRounds().size());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Round round1 = player.getRounds().get(0);
			
			String dateStr = "2013-11-04";
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			assertEquals(0, date.compareTo(round1.getDate()));
			
			assertEquals(1, round1.getHoles().size());
			
			Hole hole1 = round1.getHoles().get(0);
			assertTrue(hole1.isFairwayHit());
			assertEquals(140.5f, hole1.getFairwayHitDist());
			assertTrue(hole1.isGreenHit());
			assertEquals(10.5f, hole1.getGreenHitDist());
			assertEquals(2, hole1.getPuttCount());
			
			Round round2 = player.getRounds().get(1);
			
			String dateStr2 = "2013-11-03";
			Calendar date2 = Calendar.getInstance();
			date2.setTime(sdf.parse(dateStr2));
			assertEquals(0, date2.compareTo(round2.getDate()));
			
			assertEquals(1, round2.getHoles().size());
			
			Hole hole2 = round2.getHoles().get(0);
			assertFalse(hole2.isFairwayHit());
			assertEquals(145.7f, hole2.getFairwayHitDist());
			assertFalse(hole2.isGreenHit());
			assertEquals(9.0f, hole2.getGreenHitDist());
			assertEquals(3, hole2.getPuttCount());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testLoadOneRoundTwoHoles() {
		try {
			String fileStr = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><Player><Round date=\"2013-11-04\"><Hole fairwayHit=\"true\" fairwayHitDist=\"140.5\" greenHit=\"true\" greenHitDist=\"10.5\" puttCount=\"2\"/><Hole fairwayHit=\"false\" fairwayHitDist=\"145.7\" greenHit=\"false\" greenHitDist=\"9.0\" puttCount=\"3\"/></Round></Player>";
			
			writeFile(playerName, fileStr);
			player.load(dir);
			
			assertEquals("Bob", player.getName());
			assertEquals(1, player.getRounds().size());
			
			Round round = player.getRounds().get(0);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = "2013-11-04";
			Calendar date = Calendar.getInstance();
			date.setTime(sdf.parse(dateStr));
			assertEquals(0, date.compareTo(round.getDate()));
			
			assertEquals(2, round.getHoles().size());
			
			Hole hole1 = round.getHoles().get(0);
			assertTrue(hole1.isFairwayHit());
			assertEquals(140.5f, hole1.getFairwayHitDist());
			assertTrue(hole1.isGreenHit());
			assertEquals(10.5f, hole1.getGreenHitDist());
			assertEquals(2, hole1.getPuttCount());
			
			Hole hole2 = round.getHoles().get(1);
			assertFalse(hole2.isFairwayHit());
			assertEquals(145.7f, hole2.getFairwayHitDist());
			assertFalse(hole2.isGreenHit());
			assertEquals(9.0f, hole2.getGreenHitDist());
			assertEquals(3, hole2.getPuttCount());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append( ls );
	    }

	    reader.close();
	    
	    return stringBuilder.toString().replaceAll(ls+"$", "");
	}
	
	private void writeFile(String file, String str) throws FileNotFoundException {
		PrintWriter out = new PrintWriter(file);
		out.print(str);
		out.close();
	}
}
