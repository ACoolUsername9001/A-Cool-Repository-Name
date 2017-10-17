package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PutGearCommandGroup extends CommandGroup {
    
    public  PutGearCommandGroup(double x, double y) {
    	requires(RobotMap.gearpusSubsystem);
		requires(RobotMap.sortingSubsystem);
		requires(RobotMap.drivingSubsystem);

		addSequential(new TurnClimberCommand(135.0/360.0));
		
		addSequential(new DriveCommand(x,y,false));
		
    	addSequential(new SetGearLockerCommand(false,true));
    	
    	addSequential(new drive(-0.2,-0.2,1));
    	
    	addSequential(new SetGearLockerCommand(true,true));
    	addSequential(new TurnClimberCommand(285.0/360));
    }
    
    public  PutGearCommandGroup() {
    	requires(RobotMap.gearpusSubsystem);
		requires(RobotMap.sortingSubsystem);

    	addSequential(new TurnClimberCommand(135.0/360));
    	addSequential(new SetGearLockerCommand(false,false));
    	
    	
    	addSequential(new SetGearLockerCommand(true,false));
    	addSequential(new TurnClimberCommand(285.0/360));
    }
}
