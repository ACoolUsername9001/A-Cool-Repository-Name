package org.usfirst.frc.team5987.robot.commands;

import org.usfirst.frc.team5987.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *@author Doron
 *@version 1.0v
 *
 *this is a generic auto command, use this to create your own autonomous command!
 */
public class AutoBasicGroupCommand extends CommandGroup {
    
	/**
	 * 
	 * @param initX the first point the robot drive to
	 * @param initY the first point the robot drive to
	 * @param isGear is the robot doing gears in auto mode
	 * @param shootingX the second point the robot drive to, if not necessary change to 0
	 * @param shootingY the second point the robot drive to, if not necessary change to 0
	 * @param isShoot is the robot shooting in auto mode
	 */
    public AutoBasicGroupCommand(double initX, double initY, 
    		boolean isGear,double GearX,double GearY, 
    		double secondX, double secondY, 
    		boolean isShoot) {
    	
    	requires(RobotMap.drivingSubsystem);
    	requires(RobotMap.gearpusSubsystem);
    	requires(RobotMap.transportingSubsystem);
    	requires(RobotMap.sdBoardSubsystem);
    	requires(RobotMap.shootingSubsystem);
    	
        addSequential(new DriveCommand(initX,initY,false));
        
        if (isGear) {
//        	
        	addSequential(new PutGearCommandGroup(GearX,GearY));
        }
        else {
        	addSequential(new DriveCommand(GearX,GearY,false));
        }
        
        addSequential(new DriveCommand(secondX,secondY,true));
        
        if (isShoot) {
        	addSequential(new ShootCommandGroup());
        }
    }

}
