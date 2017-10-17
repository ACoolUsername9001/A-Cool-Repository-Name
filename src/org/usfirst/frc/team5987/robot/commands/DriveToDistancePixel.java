package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToDistancePixel extends Command {
	private boolean isLift;
	private double startDist;
	public double MIN_SPEED = 0.2;
	public double MAX_SPEED = 0.5;
	public double K_DRIVE = 0.3;
	private double wantedDistance;
	
    public DriveToDistancePixel(double wantedDistance, boolean isLift) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(RobotMap.drivingSubsystem);
    	requires(RobotMap.sdBoardSubsystem);
    	this.isLift = isLift;
    	this.startDist = getDist();
    	SmartDashboard.putNumber("Start Dist Lift", startDist);
    	this.wantedDistance = wantedDistance;
    }
    public double getDist(){
    	double dist = isLift ? RobotMap.sdBoardSubsystem.getDistLift() : RobotMap.sdBoardSubsystem.getDistBoiler();
    	return dist;
    }
    	
    public double lim(double speed){
    	    // limit to min speed
    		if(speed < MIN_SPEED && speed > 0 )
    			return MIN_SPEED;
    		if(speed > -MIN_SPEED && MIN_SPEED < 0 )
    			return -MIN_SPEED;
    		// limit to max speed
    		if(speed > MAX_SPEED)
    			return MAX_SPEED;
    		if(speed < -MAX_SPEED)
    			return -MAX_SPEED;
    		return speed;
    	
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putBoolean("In Lift Driving", true);
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	double speed = lim((getDist() - this.wantedDistance) * K_DRIVE);
    	RobotMap.drivingSubsystem.setLeft(speed);
    	RobotMap.drivingSubsystem.setRight(speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	if(Math.abs(getDist() - wantedDistance) < ACCEPTABLE_DISTANCE_DIFFERENCE){
//         ;
//    	}
    	if (startDist > wantedDistance)
    		return getDist() <= wantedDistance;
    	else
    		return getDist() >= wantedDistance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	SmartDashboard.putBoolean("In Lift Driving", false);
    	RobotMap.drivingSubsystem.drive(0.5, 0.5);
    	Timer.delay(0.43);
    	RobotMap.drivingSubsystem.drive(0,0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
