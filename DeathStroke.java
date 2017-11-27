package ynkassira_atvargas;
import java.awt.*;
import robocode.*;
import java.awt.Color;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import robocode.RateControlRobot;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.util.Utils;
import java.util.Random; 
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
 

import java.awt.*;

public class DeathStroke extends Robot {
	int count = 0; 
	double gunTurnAmt; 
	String trackName;
	static double direction;	
	


	public void run() {
	
		setColors(Color.cyan,Color.cyan,Color.black);
		setBulletColor(Color.blue); 
		

	
		trackName = null; 
		setAdjustGunForRobotTurn(true); 
		gunTurnAmt = 10;
		
		

	
		while (true) {
		
		Random rand = new Random(); 
		Random rand2 = new Random();
		int n = rand.nextInt(50);
		int n2 = rand2.nextInt(50);
		double rn = (double)(n);
		double rn2 = (double)(n2);
		 
			ahead(n);
			turnRight(n2);
			back(n);
			ahead(n2);
			turnGunRight(n);
			
			count++;
			
			if (count > 2) {
				gunTurnAmt = -10;
			}
		
			if (count > 5) {
				gunTurnAmt = 10;
			}
		
			if (count > 11) {
				trackName = null;
			}
		}
	
		
	}

	public void onScannedRobot(ScannedRobotEvent e) {

	
		if (trackName != null && !e.getName().equals(trackName)) {
			return;
		}

	
		if (trackName == null) {
			trackName = e.getName();
			
		}
	
		count = 0;
	
		if (e.getDistance() > 150) {
			gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));

			turnGunRight(gunTurnAmt); 
			turnRight(e.getBearing());
		
			ahead(e.getDistance() - 140);
			return;
		}

		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);

		if (e.getDistance() < 100) {
			if (e.getBearing() > -90 && e.getBearing() <= 90) {
				back(40);
			} else {
				ahead(40);
			}
		}
		scan();
	}

	public void onHitRobot(HitRobotEvent e) {
	
		if (trackName != null && !trackName.equals(e.getName())) {
			out.println("Tracking " + e.getName() + " due to collision");
		}
	
		trackName = e.getName();
	
		gunTurnAmt = normalRelativeAngleDegrees(e.getBearing() + (getHeading() - getRadarHeading()));
		turnGunRight(gunTurnAmt);
		fire(3);
		back(50);
	}

	public void onHitWall(HitWallEvent e)
	{
		back(1000);
		turnRight(180); 
		turnGunRight(180);
	}
		
	public void onWin(WinEvent e) {
		back(100);
		turnRight(720);  
		ahead(100); 
		turnLeft(720); 
	}
	
}



 
 