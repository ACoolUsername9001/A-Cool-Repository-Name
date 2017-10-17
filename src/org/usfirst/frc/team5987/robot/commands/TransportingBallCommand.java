package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *@author doron
 *@version 1.Ov
 *
 *what do u think its do 
 */
public class TransportingBallCommand extends Command {

	private double speed;
	
    public TransportingBallCommand(double speed) {
        // Use requires() here to declare subsystem dependencies
        requires(RobotMap.transportingSubsystem);
		this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	RobotMap.transportingSubsystem.setCarrierSpeed(speed);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { //it will automatically force finish when toggled
        return speed == 0 || OI.starterStick.getRawButton(1);
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
