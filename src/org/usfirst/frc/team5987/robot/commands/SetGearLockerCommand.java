package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *@author Tzvi 
 *this command will open or close the locker servo
 */
public class SetGearLockerCommand extends Command {
	public static int UNLOCK = 0;
	public static int LOCK = 1;
	private boolean isLocked;
	private boolean auto;
	
	public SetGearLockerCommand(boolean isLocked,boolean auto) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis)
		requires(RobotMap.gearpusSubsystem);
		this.auto = auto;
		this.isLocked = isLocked;

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (isLocked) {
			RobotMap.gearpusSubsystem.setLockerPosition(UNLOCK);
			SmartDashboard.putBoolean("isLocked", isLocked);
		} else {

			SmartDashboard.putBoolean("isLocked", isLocked);
			RobotMap.gearpusSubsystem.setLockerPosition(LOCK);

		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		
		
		return OI.starterStick.getRawButton(1) || isLocked || auto;
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
