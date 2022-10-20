/*
Will Tempest
October 10th 2022
DrivebyDistance 
*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class DriveByDistance extends CommandBase {
  private final DriveBase driveBase;
  private final double distance;
  private final double maxSpeed;
  /** Creates a new DriveByDistence. */
  public DriveByDistance(DriveBase driveBase, double distance, double maxSpeed) {
    this.driveBase = driveBase;
    this.distance = distance;
    this.maxSpeed = maxSpeed;
    addRequirements(driveBase);
  }

  @Override
  public void initialize() {
    SmartDashboard.putBoolean("DriveByDistance", true);
    driveBase.setDeadband(0.0);
    driveBase.resetEncoderPosition();
  }

  @Override
  public void execute() {
    double leftError = distance - driveBase.getLeftEncoderDistance();
    double speed = leftError * 0.02;

    if(speed > maxSpeed){
      speed = maxSpeed;
    }else if(speed < -maxSpeed){
      speed = -maxSpeed;
    }else if(speed < 0.06 && speed > 0){
      speed = 0.06;
    }else if(speed > -0.06 && speed < 0){
      speed = -0.06;
    }

    SmartDashboard.putNumber("speed", speed);
    SmartDashboard.putNumber("error", leftError);
    driveBase.autoArcadedrive(speed, 0);
    }
  
  @Override
  public void end(boolean interrupted) {
    SmartDashboard.putBoolean("DriveByDistence", false);
    driveBase.autoArcadedrive(0, 0);
    driveBase.resetEncoderPosition();
  }


  @Override
  public boolean isFinished() {
    if((Math.abs(driveBase.getLeftEncoderDistance())) > (Math.abs(distance))) {
      return true;
    }else{
    return false;
  }
}
}
