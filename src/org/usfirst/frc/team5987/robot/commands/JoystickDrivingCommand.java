package org.usfirst.frc.team5987.robot.commands;


import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author DorBrekhman
 *@version 0.1
 *
 *TODO test
 *
 *its driving..., called periodically
 */
public class JoystickDrivingCommand extends Command {
	
	
    public JoystickDrivingCommand() {
    	requires(RobotMap.drivingSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double rightSpeed = OI.rightStick.getY();
		double leftSpeed = OI.rightStick.getX();
    	RobotMap.drivingSubsystem.drive(rightSpeed, rightSpeed);
    	RobotMap.drivingSubsystem.turnInPlace(leftSpeed);
    	SmartDashboard.putNumber("L ENCODER", RobotMap.drivingSubsystem.getLeftEncoder());
    	SmartDashboard.putNumber("R ENCODER", RobotMap.drivingSubsystem.getRightEncoder());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true; // do only the initialize , than exit
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}