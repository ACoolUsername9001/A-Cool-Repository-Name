package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * @author Doron && Tzvi but doron is guilty
 */
public class GearpusSubsystem extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.
	private static int lockPosition = 0;

	private static Victor climberLeft; // PWM
	private static Victor climberRight; // PWM

	private static AnalogInput climberPosition; // Analog
	private static Servo locker; // PWM
	
	private static double startPos = 0;

	private static final double str = 225;
	
	private static double maxP;
	private static double minP;
	
	public GearpusSubsystem() {
		climberLeft = new Victor(RobotMap.climberLPort);
		climberRight = new Victor(RobotMap.climberRPort);

		climberPosition = new AnalogInput(RobotMap.climberPositionPort);
		locker = new Servo(RobotMap.lockerPort);
		
		startPos = 0.444;
		SmartDashboard.putNumber("starterPos", startPos);
		maxP = RobotMap.maxGearP;
		minP = RobotMap.minGearP;
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
	}

	/**
	 * @parmeters speed the wanted speed for the motor the functions set the
	 *            speed for the climber motors
	 */
	public void setClimberSpeed(double speed) {
		climberLeft.set(speed);
		climberRight.set(speed);

	}

	/**
	 * @return This function returns the current angle of the climber in tau
	 *         (2PI = TAU) TODO move the smartdashboard to SD subsystem
	 */
	public double getClimberPosition() {
		SmartDashboard.putNumber("Pontialmeter CLIMBER ALERT", climberPosition.getValue());
		SmartDashboard.putNumber("Tau CLIMBER ALERT12", ((climberPosition.getValue() - minP) % ((maxP-minP)/10) / ((maxP-minP)/10)));
		return ((climberPosition.getValue() - minP) % ((maxP-minP)/10) / ((maxP-minP)/10));
	}
	
	public double getAngle() {
		SmartDashboard.putNumber("Tau CLIMBER ALERT",((360 + (getClimberPosition() * 360) + str - (startPos * 360)) % 360));
		return ((360 + (getClimberPosition() * 360) + str - (startPos * 360)) % 360) / 360;
	}

	public double getClimberCircule() {
		SmartDashboard.putNumber("Pontialmeter CLIMBER ALERT", climberPosition.getValue());
		SmartDashboard.putNumber("Circule CLIMBER ALERT", (climberPosition.getValue() - minP) / ((maxP-minP)/10));
		return (climberPosition.getValue() - minP) / ((maxP-minP)/10);
	}

	/*
	 * @parameters position set position to the servo
	 */
	public void setLockerPosition(double position) {
//		position = 1 - position;
		locker.set(position);
	}

	/**
	 * this function get the current from the servo
	 */
	public double getLockerPosition() {
		return locker.get();
	}

	/**
	 * @return this function returns if the current state of the limit switch is
	 *         locked
	 */
	public boolean isGearLocked() {
		return this.getLockerPosition() == lockPosition;

	}

}
