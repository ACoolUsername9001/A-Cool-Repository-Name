package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;
import org.usfirst.frc.team5987.robot.commands.JoystickDrivingCommand;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;;

/**
 *@author DorBrekhman
 *@version 0.1
 *
 *TODO add Encoders and set default command
 */

public class DrivingSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	static Victor leftRearMotor;
	static Victor leftFrontMotor;
	static Victor rightRearMotor;
	static Victor rightFrontMotor;
	
	private static Encoder leftEncoder;
	private static Encoder rightEncoder;
	
	private final double distancePerPulse = 0.00133;
	
	final static double SPEED_FACTOR = 1; // the speed is multiplied by this factor
	
	public DrivingSubsystem() {
    	// set ports for the victors using the preassigned values of the RobotMap
    	leftRearMotor = new Victor(RobotMap.leftRearMotor);
    	leftFrontMotor = new Victor(RobotMap.leftFrontMotor);
    	rightRearMotor = new Victor(RobotMap.rightRearMotor);
    	rightFrontMotor = new Victor(RobotMap.rightFrontMotor);
    	
    	// set the timeout used for updating the speed
    	leftRearMotor.setExpiration(0.05);
    	leftFrontMotor.setExpiration(0.05);
    	rightRearMotor.setExpiration(0.05);
    	rightFrontMotor.setExpiration(0.05);
    	
    	leftRearMotor.setInverted(true);
    	leftFrontMotor.setInverted(true);
    	
    	
    	leftEncoder = new Encoder(RobotMap.leftDriveChanelA, RobotMap.leftDriveChanelB);
    	rightEncoder = new Encoder(RobotMap.rightDriveChanelA, RobotMap.rightDriveChanelB);
    	
    	leftEncoder.setDistancePerPulse(distancePerPulse);
    	rightEncoder.setDistancePerPulse(distancePerPulse);
	}
	
    public void initDefaultCommand() {
    	setDefaultCommand(new JoystickDrivingCommand());
    }
    
    public void setLeft(double speed){
    	speed *= SPEED_FACTOR;
    	leftRearMotor.set(speed);
    	leftFrontMotor.set(speed);
    }
    
    public void setRight(double speed){
    	speed *= SPEED_FACTOR;
    	rightRearMotor.set(speed);
    	rightFrontMotor.set(speed);
    }
    
    /**
     * Set speed for both motors (-1 < speed < 1)
     * @param speedL speed of left motors
     * @param speedR speed of right motors
     */
    public void drive(double speedL, double speedR){
    	setLeft(speedL);
    	setRight(speedR);
    }
    
    /**
     * turns the robot in place 
     * @param if positive, turn right, else turns left (range of -1 to 1)
     */
    public void turnInPlace(double speed){
    	setLeft(speed);
    	setRight(-speed);
    }
    /**
     * turn right the robot in place
     * @param speed for turning (-1 < speed < 1)
     */
    public void turnRight(double speed){
    	turnInPlace(speed);
    }
    
    /**
     * turn left the robot in place
     * @param speed for turning (-1 < speed < 1)
     */
    public void turnLeft(double speed){
    	turnInPlace(-speed);
    }
    
    /**
     * move the robot forward
     * @param speed for moving (-1 < speed < 1)
     */
    public void forward(){
    	setLeft(1);
    	setRight(1);
    }
    
    /**
     * move the robot backwards
     * @param speed for moving (-1 < speed < 1)
     */
    public void backwards(){
    	setLeft(-1);
    	setRight(-1);
    }
    
    /**
     * stop the robot (set the speed of all motors to 0)
     */
    public void stop(){
    	setLeft(0);
    	setRight(0);
    }
    
    public  double getLeftEncoder() {
    	
    	return -leftEncoder.getDistance();
    }
    
    public  double getRightEncoder() {
    	
    	return rightEncoder.getDistance();
    }
}