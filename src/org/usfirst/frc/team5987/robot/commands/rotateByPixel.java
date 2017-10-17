package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Tzvi
 * TODO test
 * This command makes the robot to turn to the boiler or gears 
 */
public class rotateByPixel extends Command {

	double angle;
	boolean angleGear;
	private boolean isLift;
	private double startTime;
	private double currentTime;
	double REAL_CENTER_X = 0.12;
	double R = 0.4;

	public rotateByPixel(boolean isLift) {
		this.isLift = isLift;
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.sdBoardSubsystem);
		requires(RobotMap.drivingSubsystem);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putString("asda", "hhyjyurfu");
		
		SmartDashboard.putBoolean("Inside Gershnik", true);
		startTime = System.currentTimeMillis() * 1000 / 1000 / 1000;
	}

	protected void execute() {
		if (isLift)
			angle = RobotMap.sdBoardSubsystem.getXdifLift() - REAL_CENTER_X;
		else
			angle = RobotMap.sdBoardSubsystem.getXdifBoiler() - REAL_CENTER_X;
		SmartDashboard.putNumber("what robot C", lim((-angle)*R));
		RobotMap.drivingSubsystem.drive(lim((-angle)*R),lim(angle*R));
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() 
	{
		currentTime = System.currentTimeMillis() * 1000 / 1000 / 1000;
		if(Math.abs(currentTime - startTime) >= 4)
			return true;
		if(Math.abs(angle) < 0.08){
			RobotMap.drivingSubsystem.stop();
			Timer.delay(0.25);
			if (isLift)
				angle = RobotMap.sdBoardSubsystem.getXdifLift() - REAL_CENTER_X;
			else
				angle = RobotMap.sdBoardSubsystem.getXdifBoiler() - REAL_CENTER_X;
			if(Math.abs(angle) < 0.08)
				return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end()

	{
		RobotMap.drivingSubsystem.stop();
		SmartDashboard.putBoolean("Inside Gershnik", false);
	}
	
	
	public double lim(double speed){
		if(speed > 0.3){
			return speed*0.75;}
		if(speed < -0.3){
			return speed*0.75;}
		if(speed<0.2 && speed>0)
			return 0.2;
		if(speed>-0.2 && speed<0)
			return -0.2;
		return speed;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
