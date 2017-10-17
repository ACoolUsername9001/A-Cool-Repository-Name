package org.usfirst.frc.team5987.robot.commands;


import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Doron TODO Check default rpm,test Command
 *
 *         like the command below but with constant speed
 *        
 * ACoolName: changed all left related to right because left encoder stopped working, variables still exist only need to change back and un-docomentize it
 */
public class ChangePIDShootSpeedByRPMCommand extends Command {
	private double RPM;
	private double maxLeftValue = 1000.0; // the RPM when PWM in the left motor is 1
	private double maxRightValue = 1000.0; // the RPM when PWM in the right motor is 1
	private double defaultRight; // the default right speed if the current RPM is the wanted one
	private double defaultLeft; // the default left speed if the current RPM is the wanted one
	private double addRight = 0.0; // the added speed to default right speed (based on the difference between current and wanted speed)
	private double addLeft = 0.0; // the added speed to default left speed (based on the difference between current and wanted speed)
	
	double Kp = 0.2;
	double Kd = 200.0;
	double Ki = 0.0;
	double currentLeft;
	double currentRight;
	private double currentRightError = 0.0;
	private double currentLeftError = 0.0;
	private double priorRightError = 0.0;
	private double priorLeftError = 0.0;
	private long currentTimeMilli = getMillis();
	private long priorTime = getMillis();
	/**
	 * Change the speed to the wanted one based on the encoders
	 * @param RPM the wanted speed in rates per minute
	 * corrected by ACoolName
	 */
	public ChangePIDShootSpeedByRPMCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.shootingSubsystem);

		//this.RPM = SmartDashboard.getNumber("Boiler RPM", 750);
		this.RPM = RobotMap.sdBoardSubsystem.getBoilerRPM();
	}
	
	public long getMillis(){
		return System.nanoTime() * 1000000;
	}
	// Called just before this Command runs the first time
	protected void initialize() {
		//this.RPM = SmartDashboard.getNumber("Boiler RPM", 750);
		this.RPM = RobotMap.sdBoardSubsystem.getBoilerRPM();
//		if (SmartDashboard.getNumber("Max Speed left",0) == 0 || SmartDashboard.getNumber("Max Speed Right",0) == 0) //if one of them is 0 (not adjusted) i want to check the max for both(just in case)
//				SmartDashboard.putBoolean("Get Max", true);
//		if (SmartDashboard.getBoolean("Get Max", false)){
//			RobotMap.shootingSubsystem.setSpeed(1);
//			Timer.delay(0.1);
//			// find the RPM when PWM is 1
//				findMaxValues(1);
//		}
		// convert RPM to PWM based on the RPM when PWM equals 1
		
		defaultRight = RPM / maxRightValue;
		defaultLeft = RPM / maxLeftValue;
		RobotMap.shootingSubsystem.setLeftSpeed(defaultLeft);
		RobotMap.shootingSubsystem.setRightSpeed(defaultRight);
		SmartDashboard.putNumber("Kp", Kp);
		SmartDashboard.putNumber("Kd", Kd);
		SmartDashboard.putNumber("Ki", Ki);
		SmartDashboard.putBoolean("Shooting", true);
		SmartDashboard.putBoolean("Finish Shooting", false);
		Timer.delay(0.05);
	}
	/**
	 * limit the speed  motor to range of -1 to 1
	 */
	static double lim(double speed){
		if (speed > 1)
			return 1;
		if (speed < -1)
			return -1;
		return speed;
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		//maxLeftValue = SmartDashboard.getNumber("Max Speed Left",0); // the RPM when PWM in the left motor is 1
		//maxRightValue = SmartDashboard.getNumber("Max Speed Right",0); // the RPM when PWM in the right motor is 1
		
		//this.RPM = SmartDashboard.getNumber("Boiler RPM", 750);
		this.RPM = RobotMap.sdBoardSubsystem.getBoilerRPM();
		// print the PWM in the SmartDashboard
		double lastLeftPWM = RobotMap.shootingSubsystem.getLeftPWMSpeed();
		double lastRightPWM = RobotMap.shootingSubsystem.getRightPWMSpeed();
		
		currentLeft = RobotMap.shootingSubsystem.getLeftSpeed();
		currentRight = RobotMap.shootingSubsystem.getRightSpeed();
		
//		Kp = SmartDashboard.getNumber("Kp", 0);
//		Kd = SmartDashboard.getNumber("Kd", 0);
//		Ki = SmartDashboard.getNumber("Ki", 0);
		SmartDashboard.putNumber("Kp", Kp);
		SmartDashboard.putNumber("Kd", Kd);
		SmartDashboard.putNumber("Ki", Ki);
		
		// the added proportional values based on the difference between current and wanted speed ('RPM')
		if (maxLeftValue != 0 && maxRightValue != 0){
			SmartDashboard.putBoolean("max shoot are 0", false);
			currentLeftError = ((RPM - currentLeft) / maxLeftValue);
			currentRightError = ((RPM - currentRight) / maxRightValue);
			currentTimeMilli = getMillis();
			
			long timeDif = Math.abs(currentTimeMilli - priorTime); 
			double leftErrorByTime = currentLeftError * timeDif;
			double rightErrorByTime = currentRightError * timeDif;
			
			double pRight = currentRightError * Kp;
			double pLeft = currentLeftError * Kp;
			
			double dRight = ((currentRightError - priorRightError) / timeDif) * Kd;
			double dLeft = ((currentLeftError - priorLeftError) / timeDif) * Kd;
			
			double iRight = rightErrorByTime * timeDif * Ki;
			double iLeft = leftErrorByTime * timeDif * Ki;
			
			
			addLeft = pLeft + dLeft + iLeft;
			addRight = pRight + dRight + iRight;

			SmartDashboard.putNumber("Right Error", currentRightError);
			SmartDashboard.putNumber("Left Error", currentLeftError);
			SmartDashboard.putNumber("Add Left", addLeft);
			SmartDashboard.putNumber("Add Right", addRight);
			// set the speed to the default speed plus the proportional values
			RobotMap.shootingSubsystem.setLeftSpeed(lim(lastLeftPWM + addLeft));//lim(lastLeftPWM + addLeft));
			RobotMap.shootingSubsystem.setRightSpeed(lim(lastRightPWM + addRight));
			
			priorLeftError = currentLeftError;
			priorRightError= currentRightError;
			priorTime = currentTimeMilli;
			
			}else{
				SmartDashboard.putBoolean("max shoot are 0", true);
			}
		Timer.delay(0.05);
		}


	// Make this return true when this Command no longer needs to run execute()
	/**
	 * if the range between the current speed and wanted speed is less than 20, finish the command
	 */
	protected boolean isFinished() {
		//double OKRange = 20;
		//boolean rightOK = Math.abs(RobotMap.shootingSubsystem.getRightSpeed() - RPM) < OKRange;
		//boolean leftOK = Math.abs(RobotMap.shootingSubsystem.getLeftSpeed() - RPM) < OKRange;
		//
		//boolean isExiting = rightOK && leftOK;
		//
		//if (isExiting) {
		//	SmartDashboard.putString("state", "finish");
		//}
		//return false; // isExiting
		return SmartDashboard.getBoolean("Finish Shooting", true);
	}

	// Called once after isFinished returns true
	protected void end() {
		
		SmartDashboard.putBoolean("Shooting", false);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		
	}
}