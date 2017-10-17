package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author Doron Goldenberg
 * @version v1.0
 *
 *          It shoot it score!!!
 *
 *          TODO fix right motor with +- and shit ;)
 */
public class ShootingSubsystem extends Subsystem {

	

	Victor leftShooter;
	Victor rightShooter;

	Encoder leftSpeed;
	Encoder rightSpeed;

	public ShootingSubsystem() {


		leftShooter = new Victor(RobotMap.leftShooterPort);
		rightShooter = new Victor(RobotMap.rightShooterPort);

		leftSpeed = new Encoder(RobotMap.leftShooterChanelA, RobotMap.leftShooterChanelB, false);
		rightSpeed = new Encoder(RobotMap.rightShooterChanelA, RobotMap.rightShooterChanelB, false);

		leftSpeed.setDistancePerPulse(1);
		rightSpeed.setDistancePerPulse(-1);

		leftShooter.setInverted(true);

	}

	public void initDefaultCommand() {
		
	}





	public void setSpeed(double speed) {
		setLeftSpeed(speed);
		setRightSpeed(speed);
	}

	public void setLeftSpeed(double speed) {

		leftShooter.set(speed);
		getLeftPWMSpeed();
	}

	public void setRightSpeed(double speed) {

//		rightShooter.set(speed);
		getRightPWMSpeed();
	}

	public double getLeftSpeed() {
		SmartDashboard.putNumber("left speed", leftSpeed.getRate());
		return leftSpeed.getRate();
	}

	public double getRightSpeed() {
		SmartDashboard.putNumber("right speed", rightSpeed.getRate());
		return -rightSpeed.getRate();
	}

	public double getLeftPWMSpeed() {
		SmartDashboard.putNumber("left pwm speed", -leftShooter.get());
		return -leftShooter.get();
	}

	public double getRightPWMSpeed() {
		SmartDashboard.putNumber("right pwm speed", rightShooter.get());
		return rightShooter.get();
	}

	//public double getDeltaSpeeds() {
		//SmartDashboard.putNumber("Delta", Math.abs(getLeftSpeed() - getRightSpeed()));
		//return Math.abs(getLeftSpeed() - getRightSpeed());
	//}
}
