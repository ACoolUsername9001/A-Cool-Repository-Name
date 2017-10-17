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
public class ChangeShootSpeedByRPMCommand extends Command {
	private double RPM;
	private double maxLeftValue = SmartDashboard.getNumber("Max Speed left",0); // the RPM when PWM in the left motor is 1
	private double maxRightValue = SmartDashboard.getNumber("Max Speed Right",0); // the RPM when PWM in the right motor is 1
	private double defaultRight; // the default right speed if the current RPM is the wanted one
	private double defaultLeft; // the default left speed if the current RPM is the wanted one
	private double addRight = 0; // the added speed to default right speed (based on the difference between current and wanted speed)
	private double addLeft = 0; // the added speed to default left speed (based on the difference between current and wanted speed)
	double K_ADDED = 0.05; // the number that is multiplied by 'addRight' and 'addLeft'
	
	double currentLeft;
	double currentRight;
	
	/**
	 * Change the speed to the wanted one based on the encoders
	 * @param RPM the wanted speed in rates per minute
	 * corrected by ACoolName
	 */
	public ChangeShootSpeedByRPMCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.shootingSubsystem);

		//this.RPM = SmartDashboard.getNumber("Boiler RPM", 750);
		this.RPM = RobotMap.sdBoardSubsystem.getBoilerRPM();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//this.RPM = SmartDashboard.getNumber("Boiler RPM", 750);
		this.RPM = RobotMap.sdBoardSubsystem.getBoilerRPM();
		if (SmartDashboard.getNumber("Max Speed left",0) == 0 || SmartDashboard.getNumber("Max Speed Right",0) == 0) //if one of them is 0 (not adjusted) i want to check the max for both(just in case)
				SmartDashboard.putBoolean("Get Max", true);
		if (SmartDashboard.getBoolean("Get Max", false)){
			RobotMap.shootingSubsystem.setSpeed(1);
			Timer.delay(0.1);
			// find the RPM when PWM is 1
				findMaxValues(1);
		}
		// convert RPM to PWM based on the RPM when PWM equals 1
		defaultRight = RPM / maxRightValue;
		defaultLeft = RPM / maxLeftValue;
		RobotMap.shootingSubsystem.setLeftSpeed(defaultLeft);
		RobotMap.shootingSubsystem.setRightSpeed(defaultRight);
		SmartDashboard.putBoolean("Finish Shooting", true);
		SmartDashboard.putBoolean("Finish Shooting", false);
		SmartDashboard.putBoolean("Shooting", true);
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
		
		// the added proportional values based on the difference between current and wanted speed ('RPM')
		if (maxLeftValue != 0 && maxRightValue != 0){
			SmartDashboard.putBoolean("max shoot are 0", false);
			addLeft = ((RPM - currentLeft) / maxLeftValue) * K_ADDED;
			addRight = ((RPM - currentRight) / maxRightValue) * K_ADDED;
			SmartDashboard.putNumber("addLeft", addLeft);
			SmartDashboard.putNumber("addRight", addRight);
			
			// set the speed to the default speed plus the proportional values
			RobotMap.shootingSubsystem.setLeftSpeed(lim(lastLeftPWM + addLeft));//lim(lastLeftPWM + addLeft));
			RobotMap.shootingSubsystem.setRightSpeed(lim(lastRightPWM + addRight));
			}else{
				SmartDashboard.putBoolean("max shoot are 0", true);
			}
		Timer.delay(0.05);
		}

	public void findMaxValues(double seconds) {
		double leftValue;
		double rightValue;

		double loopTime = 0.05;
		int loops = (int) (seconds / loopTime);
		for (int i = 0; i <= loops; i++) {
			rightValue = RobotMap.shootingSubsystem.getRightSpeed();

			if (rightValue > maxRightValue) {

				maxRightValue = rightValue;

			}
			leftValue = RobotMap.shootingSubsystem.getLeftSpeed();

			if (leftValue > maxLeftValue) {
				
				maxLeftValue = leftValue;
			
			}
			Timer.delay(loopTime);
		}
		
		SmartDashboard.putNumber("Max Speed left", maxLeftValue);
		SmartDashboard.putNumber("Max Speed Right", maxRightValue);
		SmartDashboard.putBoolean("Get Max", false);
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