package org.usfirst.frc.team5987.robot;

import org.usfirst.frc.team5987.robot.commands.AddRPMCommand;
import org.usfirst.frc.team5987.robot.commands.ClimbeGroupCommand;
import org.usfirst.frc.team5987.robot.commands.FinishShootingCommand;
import org.usfirst.frc.team5987.robot.commands.PutGearCommandGroup;
import org.usfirst.frc.team5987.robot.commands.SetGearLockerCommand;
import org.usfirst.frc.team5987.robot.commands.ShootCommandGroup;
import org.usfirst.frc.team5987.robot.commands.TurnClimberCommand;
import org.usfirst.frc.team5987.robot.commands.getGearCommandGroup;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	public static Joystick rightStick = new Joystick(0);

	Button finish = new JoystickButton(rightStick, 1); //A
	Button getGear = new JoystickButton(rightStick, 2);
	Button putGear = new JoystickButton(rightStick, 3);
	Button shoot = new JoystickButton(rightStick, 4);
	Button fast = new JoystickButton(rightStick, 5); // Left Button
	Button slow = new JoystickButton(rightStick, 6);
	Button moveClimber = new JoystickButton(rightStick, 7);
	Button climeb = new JoystickButton(rightStick, 8);
	Button leftJoystickClick = new JoystickButton(rightStick, 9);
	Button rightJoystickClick = new JoystickButton(rightStick, 10);

	public OI() {
		
		finish.whenPressed(new FinishShootingCommand());
		getGear.toggleWhenPressed(new getGearCommandGroup());
		putGear.toggleWhenPressed(new PutGearCommandGroup());
		shoot.whenPressed(new ShootCommandGroup());
		moveClimber.toggleWhenPressed(new TurnClimberCommand());
		climeb.toggleWhenPressed(new ClimbeGroupCommand());
		fast.whenPressed(new AddRPMCommand(5));
		//fast.whenPressed(new ChangePIDShootSpeedByRPMCommand());
		slow.whenPressed(new AddRPMCommand(-5));
		leftJoystickClick.whenPressed(new SetGearLockerCommand(false,true));
		rightJoystickClick.whenPressed(new SetGearLockerCommand(true,true));
		
	}

}
