package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateToAngle extends Command {

	private double Wangle;
	
	private double max;
	private double min;
	
    public RotateToAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(RobotMap.drivingSubsystem);
        this.Wangle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	max = 45;
    	min = 1.5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double speed = RobotMap.navx.getAngle() - Wangle;
    	speed /= max;
    	if (speed > 1)
    		speed = 1;
    	if (speed < -1)
    		speed = -1;
    	RobotMap.drivingSubsystem.drive(speed, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Math.abs(RobotMap.navx.getAngle() - Wangle) < min)
    		return true;
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.drivingSubsystem.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
