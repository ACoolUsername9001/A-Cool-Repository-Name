package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootCommandGroup extends CommandGroup {
    
    public  ShootCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(RobotMap.shootingSubsystem);
   		requires(RobotMap.drivingSubsystem);
    	requires(RobotMap.sdBoardSubsystem);
    	requires(RobotMap.transportingSubsystem);
    	
//    	addParallel(new ChangeShootSpeedByRPMCommand());
    	addParallel(new ChangePIDShootSpeedByRPMCommand());
    	addSequential(new rotateByPixel(false));
//    	addSequential(new wait(5));
    	addSequential(new TransportingBallCommand(1));
    	addParallel(new TransportingBallCommand(0));
    	addSequential(new FinishShootingCommand());
    }
}
