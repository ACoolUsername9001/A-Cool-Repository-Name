package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.subsystems.DrivingSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.GearpusSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.SDboardSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.ShootingSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.SortingSubsystem;
import org.usfirst.frc.team5987.robot.subsystems.TransportSubsystem;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//public static AHRS navx = new AHRS(SPI.Port.kMXP);
	
	// PWM
	public static int leftFrontMotor = 6;
	public static int leftRearMotor = 7;
	public static int rightRearMotor = 3;
	public static int rightFrontMotor = 2;
	public static int shootingAnglePort = 0;
	public static int leftShooterPort =10;
	public static int rightShooterPort = 11;
	public static int sortingServoPort = 8;
	public static int carrierMotor = 5;
	
	//NAVX PWM
	
	public static int climberLPort= 4; //RobotMap.navx.getChannelFromPin(PinType.PWM, 0);
	public static int climberRPort= 1; //RobotMap.navx.getChannelFromPin(PinType.PWM, 1);
	public static int lockerPort = 9; 
	

	
	//Digital 
	
	public static int leftDriveChanelA = 4;
	public static int leftDriveChanelB = 5;
	
	public static int rightDriveChanelA =6;
	public static int rightDriveChanelB = 7;
	
	public static int leftShooterChanelA = 2;
	public static int leftShooterChanelB = 3;
	
	public static int rightShooterChanelA = 0;
	public static int rightShooterChanelB = 1;

	//Analog
	public static int climberPositionPort = 0;
	
	//Subsystems
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final DrivingSubsystem drivingSubsystem = new DrivingSubsystem();
	public static final GearpusSubsystem gearpusSubsystem = new GearpusSubsystem();
	public static final ShootingSubsystem shootingSubsystem = new ShootingSubsystem();
	public static final  SortingSubsystem sortingSubsystem = new SortingSubsystem();
	public static final TransportSubsystem transportingSubsystem = new TransportSubsystem();
	public static final SDboardSubsystem sdBoardSubsystem = new SDboardSubsystem();

	public static final double maxGearP = 4056;

	public static final double minGearP = 5;	
	
		
}

