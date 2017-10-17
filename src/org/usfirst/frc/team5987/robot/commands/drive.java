package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class drive extends Command {

	private double l;
	private double r;
	private double time;
	
    public drive(double l, double r,double time) {
        // Use requires() here to declare subsystem dependencies
        requires(RobotMap.drivingSubsystem);
        this.l = l;
        this.r = r;
        this.time = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Timer.delay(3);
    	RobotMap.drivingSubsystem.drive(l, r);
    	Timer.delay(time);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.drivingSubsystem.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
