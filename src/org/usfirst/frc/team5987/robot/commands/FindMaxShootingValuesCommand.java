package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class FindMaxShootingValuesCommand extends Command {
	private double seconds;
	private double maxRightValue = 0;
	private double maxLeftValue = 0;
    public FindMaxShootingValuesCommand(double seconds) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.seconds = seconds;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
