
package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.OI;
import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Doron
 */
public class TurnClimberCommand extends Command {

	private double wantedAngle;
	private double speed;
	private double maxAngle;
	private boolean byJoystick;

	public TurnClimberCommand(double angle) {
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.gearpusSubsystem);
		requires(RobotMap.sortingSubsystem);
		this.wantedAngle = angle; // Tau Zdainuuuuu!!!!!!!!!1
		byJoystick = false;
	}

	public TurnClimberCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.gearpusSubsystem);
		requires(RobotMap.sortingSubsystem);
		byJoystick = true;
		wantedAngle = 2;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putString("state2", "in");
		maxAngle = 0.3;
		

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		RobotMap.gearpusSubsystem.getClimberCircule();
		if (byJoystick) {
			RobotMap.gearpusSubsystem.setClimberSpeed(OI.starterStick.getRawAxis(1));
		}
		else {
			driveToAngle();
		}
		Timer.delay(0.05);
	}

	public void driveToAngle() {
		SmartDashboard.putNumber("d",wantedAngle - RobotMap.gearpusSubsystem.getAngle());
		speed = (wantedAngle - RobotMap.gearpusSubsystem.getAngle()) / maxAngle;
		if (speed > 1.0)
			speed = 1.0;
		if (speed < -1.0)
			speed = -1.0;
		SmartDashboard.putNumber("Speed Climeb", speed);
		RobotMap.gearpusSubsystem.setClimberSpeed(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (byJoystick)
			return false;
		if (RobotMap.gearpusSubsystem.getClimberCircule() < 0.5 && wantedAngle - RobotMap.gearpusSubsystem.getAngle() < 0) {
			SmartDashboard.putString("state2", "finish");
			return true;
		}
		if (RobotMap.gearpusSubsystem.getClimberCircule() > 9.5 && wantedAngle - RobotMap.gearpusSubsystem.getAngle() > 0) {
			SmartDashboard.putString("state2", "finish");
			return true;
		}
		if (Math.abs(wantedAngle - RobotMap.gearpusSubsystem.getAngle()) < 7.0 / 360) {
			SmartDashboard.putString("state2", "finish");
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotMap.gearpusSubsystem.setClimberSpeed(0.0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
