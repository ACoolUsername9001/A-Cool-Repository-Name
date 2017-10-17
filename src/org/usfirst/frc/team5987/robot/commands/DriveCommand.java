
package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveCommand extends Command {

	private static final double kmaxAngle = 90;

	private static final double kmaxDistance = -1;

	private static final double kminDistance = 0.2;

	double x = 0;
	double y = 0;
	
	double X;
	double Y;
	
	private double wantAngle;

	private double distance;

	private double newAngle;
	private double preAngle;

	private double newLeftEncoder;
	private double initLeftEncoder;

	private double newRightEncoder;
	private double initRightEncoder;
	private double startTime;
	private double currentTime;
	private boolean forword;
	
	public DriveCommand(double x, double y,boolean forword) {
		// Use requires() here to declare subsystem dependencies
		requires(RobotMap.drivingSubsystem);
		
		X = x;
		Y = y;
		
		this.forword = forword;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putString("state2", "in");
		startTime = System.currentTimeMillis() * 1000 / 1000 / 1000;
		this.x = X;
		this.y = Y;

		//preAngle = smartModulo(RobotMap.navx.getAngle());
		initLeftEncoder = RobotMap.drivingSubsystem.getLeftEncoder();
		initRightEncoder = RobotMap.drivingSubsystem.getRightEncoder();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		//newAngle = smartModulo(RobotMap.navx.getAngle());
		newLeftEncoder = RobotMap.drivingSubsystem.getLeftEncoder();
		newRightEncoder = RobotMap.drivingSubsystem.getRightEncoder();

		updateLocation();

		updateParams();

		setSpeed();

		print();

		preAngle = newAngle;
		initLeftEncoder = newLeftEncoder;
		initRightEncoder = newRightEncoder;
	}

	private void print() {
		SmartDashboard.putNumber("pre angle", preAngle);
		SmartDashboard.putNumber("new angle", newAngle);

		SmartDashboard.putNumber("init left", initLeftEncoder);
		SmartDashboard.putNumber("new left", newLeftEncoder);

		SmartDashboard.putNumber("init right", initRightEncoder);
		SmartDashboard.putNumber("new right", newRightEncoder);

		SmartDashboard.putNumber("wanted angle", wantAngle);
		SmartDashboard.putNumber("distance", distance);

		SmartDashboard.putNumber("x", x);
		SmartDashboard.putNumber("y", y);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(distance) < kminDistance) {
			SmartDashboard.putString("state2", "finish");
			return true;
		}
		currentTime = System.currentTimeMillis() * 1000 / 1000 / 1000;
		if(Math.abs(currentTime - startTime) >= 4)
			return true;
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		RobotMap.drivingSubsystem.drive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		SmartDashboard.putString("state2", "interrupted");
		end();
	}

	private void updateLocation() {
		double dLeftEncoder = newLeftEncoder - initLeftEncoder;
		double dRightEncoder = newRightEncoder - initRightEncoder;

		double d = avg(dLeftEncoder, dRightEncoder);
		double angle = avg(preAngle, newAngle);
		
		SmartDashboard.putNumber("DLE", dLeftEncoder);
		SmartDashboard.putNumber("DRE", dRightEncoder);
		SmartDashboard.putNumber("ADE", d);
		SmartDashboard.putNumber("AA", angle);
		
		SmartDashboard.putNumber("COS",Math.cos(angleToRadian(angle)));
		SmartDashboard.putNumber("SIN",Math.sin(angleToRadian(angle)));
		
		SmartDashboard.putNumber("XD", d * Math.cos(angleToRadian(angle)));
		SmartDashboard.putNumber("YD", d * Math.sin(angleToRadian(angle)));
		
		x -= d * Math.cos(angleToRadian(angle));
		y -= d * Math.sin(angleToRadian(angle));
	}

	private double avg(double num1, double num2) {
		return (num1 + num2) / 2.0;
	}

	private void updateParams() {
		double tan;
		if (x == 0 && y > 0)
			tan = angleToRadian(90);
		else if (x == 0 && y < 0)
			tan = angleToRadian(-90);
		else
			tan = Math.atan(y / x);
		wantAngle = radianToAngle(tan);

		if (x < 0) {
			wantAngle = smartModulo(wantAngle + 180);
		}

		distance = Math.sqrt(x * x + y * y);

		SmartDashboard.putNumber("tan", tan);

	}

	private double radianToAngle(double radian) {
		return radian * 180 / Math.PI;
	}

	private void setSpeed() {
		double left = lim(strightSpeed() - angleSpeed())/1.5;
		double right = lim(strightSpeed() + angleSpeed())/1.5;
		SmartDashboard.putNumber("left speed AUTO", left);
		SmartDashboard.putNumber("right speed AUTO", right);
		RobotMap.drivingSubsystem.drive(left, right);
	}

	private double angleSpeed() {
		double speed = lim((deltaAngles() / kmaxAngle));

		SmartDashboard.putNumber("angle speed", speed);

		return speed;
	}

	private double deltaAngles() {
		double fact = forword ? 0 : 180;
		SmartDashboard.putNumber("Delta Angels", smartModulo(wantAngle - newAngle + fact));
		return smartModulo(wantAngle - newAngle + fact);
	}

	private double smartModulo(double angle) {
		if (angle >= 0) {
			if ((angle % 360) > 180)
				return (angle % 360) - 360;
			return (angle % 360);
		} else {
			if ((angle % 360) < -180)
				return (angle % 360) + 360;
			return (angle % 360);
		}
	}

	private double strightSpeed() {
		double speed = lim(distance * Math.cos(angleToRadian(smartModulo(wantAngle)-avg(preAngle, newAngle))) / kmaxDistance) / 1.5;

		SmartDashboard.putNumber("stright speed", speed);

		return speed;
	}

	private double angleToRadian(double angle) {
		return angle * Math.PI / 180;
	}

	private double lim(double number) {
		if (number > 1)
			return 1;
		if (number < -1)
			return -1;
		return number;
	}
}
