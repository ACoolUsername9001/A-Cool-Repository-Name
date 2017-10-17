package org.usfirst.frc.team5987.robot.subsystems;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Subsystem for the transportation of balls from storage to shooter
 *@author DorBrekhman
 *@version 0.2
 *
 *TODO add default command
 */
public class TransportSubsystem extends Subsystem {
    
    // define devices
	static Spark carrier;
//	static DigitalOutput ballElevator;
//	static DigitalInput carrierReceiver;
//	static DigitalOutput carrierTransmitter;
//	static DigitalInput nearShooterReceiver;
//	static DigitalOutput nearShooterTransmitter;
	
	private static double CARRIER_SPEED_FACTOR = -0.35;
	
	public TransportSubsystem() {
    	carrier = new Spark(RobotMap.carrierMotor);
//    	ballElevator = new DigitalOutput(RobotMap.ballElevatorMotor);
//    	carrierReceiver = new DigitalInput(RobotMap.carrierReceiver);
//    	nearShooterReceiver = new DigitalInput(RobotMap.nearShooterReceiver);
//    	carrierTransmitter = new DigitalOutput(RobotMap.carrierTransmitter);
//    	nearShooterTransmitter = new DigitalOutput(RobotMap.nearShooterTransmitter);
	}
	
    public void initDefaultCommand() {
    	// set the ports for the devices with RobotMap
    }
    /**
     * set the speed for the conveyor (masoa) that brings the balls to the ball elevator
     * @param speed for the motor (from 0 to 1)
     */
    public  void setCarrierSpeed(double speed){
    	carrier.set(speed * CARRIER_SPEED_FACTOR);
    }
    
}