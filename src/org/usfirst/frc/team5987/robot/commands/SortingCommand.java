package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author ariel && Doron
 *@version 1.1
 *This command responsible on the distinguishing between gears and balls
 */
public class SortingCommand extends Command {
	boolean force;
	boolean state;
			
	
	/**
	 * 
	 * @param force distinguish if we force the subsystem state to change or not
	 * @param state the next state of the subsystem
	 */
    public SortingCommand(boolean force, boolean state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(RobotMap.sortingSubsystem);
    	requires(RobotMap.gearpusSubsystem);
    	this.force = force;
    	this.state = state;
    
    }

    // Called just before this Command runs the first time
    protected void initialize() {
      	if(force)
    		RobotMap.sortingSubsystem.setServoPosition(state2Position(state));
    	
    	else
    		RobotMap.sortingSubsystem.setServoPosition(state2Position(state));
    		
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (145.0/360 < RobotMap.gearpusSubsystem.getAngle() && RobotMap.gearpusSubsystem.getAngle() < 215.0/360)
    		return true;
        return OI.starterStick.getRawButton(1) || !state;
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.sortingSubsystem.setServoPosition(RobotMap.sortingSubsystem.getServoPosition());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }


    public double state2Position(boolean state)
    {
    	if(state)
    		return 1;
    	return 0.45;
    }

}
