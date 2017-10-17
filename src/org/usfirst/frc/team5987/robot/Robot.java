/**
 * TODO  all :
 * create robot and OI
 * 
 * fix changeShootSpeed ONLY RIGHT MOTOR WORKS
 * 
 * test navigatetoLift
 * 
 * test autonomous command
 * test gearGroupCommand
 * test putGearGroupCommand
 * test shootGroupCommand
 * 
 * changes:
 * ONLY RIGHT SHOTING MOTOR WORKS
 * 
 * 
 */
package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.AutoBasicGroupCommand;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static RobotMap rm;
	Command autonomousCommand;
	SendableChooser chooser;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		//.reset();
		oi = new OI();
		rm = new RobotMap();
		chooser = new SendableChooser();
		chooser.addDefault("line", new AutoBasicGroupCommand(-2, 0, false, 0, 0, 0, 0, true));
		
		chooser.addObject("midGear", new AutoBasicGroupCommand(0, 0, true, -1.97, 0, 0, 0, true));
		chooser.addObject("BoilerGear : blue", new AutoBasicGroupCommand(-1.45, 0, false, -1.35, -1.95, 0, 0, false));
		chooser.addObject("BoilerGear : red", new AutoBasicGroupCommand(-1.45, 0, false, -1.35, 1.95, 0, 0, false));
		
		chooser.addObject("lineNOs", new AutoBasicGroupCommand(-2, 0, false, 0, 0, 0, 0, false));
		
		chooser.addObject("midGearNOs", new AutoBasicGroupCommand(0, 0, true, -2.03, 0, 0, 0, false));
				
		SmartDashboard.putNumber("kill him", 1);
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("Boiler RPM", 750);
		SmartDashboard.putNumber("Added RPM", 0);

		// RobotMap.sortingSubsystem.setServoPosition(0.45);
		RobotMap.gearpusSubsystem.setLockerPosition(0);

	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		//RobotMap.navx.reset();
		autonomousCommand = (Command) chooser.getSelected();

		// String autoSelected = SmartDashboard.getString("Auto Selector",
		// "Default");
		// switch(autoSelected) {
		// case "My Auto": autonomousCommand = new MyAutoCommand(); break;
		// case "Default Auto": default:
		// autonomousCommand = new ExampleCommand(); break; }

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		SmartDashboard.putNumber("Boiler X Difference", 1);
		SmartDashboard.putNumber("Gears X Difference", 1);

	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		// RobotMap.shootingSubsystem.getLeftPWMSpeed();
		RobotMap.shootingSubsystem.getRightPWMSpeed();

		RobotMap.shootingSubsystem.getLeftSpeed();
		RobotMap.shootingSubsystem.getRightSpeed();

		RobotMap.sdBoardSubsystem.getPDP();
		//SmartDashboard.putNumber("angle", RobotMap.navx.getAngle());
		RobotMap.gearpusSubsystem.getClimberCircule();
		RobotMap.gearpusSubsystem.getAngle();
		RobotMap.sortingSubsystem.getServoPosition();

	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
