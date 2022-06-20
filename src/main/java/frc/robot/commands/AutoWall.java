// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;


public class AutoWall extends SequentialCommandGroup {
  // Inital drive to pick up ball
  //private final double kRobotSpeed = 0.6; // percent output
  private final double kFirstDistance = 64.0; // inches
  private final double kSecondDistance = -3.0; // inches
  //private final double kAngle = 88.0;// Turn Angle
  //private final double kThirdDistance = 120.0;
  private final double kLaunchSpeed = 0.95;

  /** Creates a new Autonomous. */
  public AutoWall(Drivetrain drive, Intake intake, Lift lift, Launcher launch, Cameras cam) {
    addCommands(
      // STEP 1
      // Set drive to brake and zero the drive encoders
      new InstantCommand(drive::setDriveBrake, drive).withTimeout(0.2),
      new InstantCommand(drive::resetEncoders, drive).withTimeout(0.2),
      
      // STEP 2
      // Drive Straight into the wall while intaking the ball to pick up a second ball
      parallel(
        new FunctionalCommand(
          // On command start reset encoder
          drive::resetEncoders,
          // Start driving forward at the start of the command
          () -> drive.arcadeDrive(-0.5, 0.0),
          // At the end of the command stop driving
          interrupted -> drive.arcadeDrive(0.0,0.0),
          // When the distance driven exceeds the desired value
          () -> drive.getAvgEncoder() >= kFirstDistance / DriveConstants.kEncDistancePerPulse,
          // Requires the drivetrain subsystem
          drive).alongWith(
            new StartEndCommand(intake::ballIn, intake::stop, intake).withTimeout(2.0)
          ).withTimeout(3.0)),

      //STEP 3
      // Reset the drive enocders again once the robot has stopped full
      new InstantCommand(drive::resetEncoders, drive).withTimeout(0.2).beforeStarting(new WaitCommand(0.2)),
      //new InstantCommand(drive::resetAngle, drive).withTimeout(0.2),
      

      //STEP 4
      // Turn slightly to line up to upper hub
      parallel(
        new FunctionalCommand(
        // On command start reset encoder
        drive::resetEncoders,
        // Start driving forward at the start of the command
        () -> drive.arcadeDrive(0.1, 0.25),
        // At the end of the command stop driving
        interrupted -> drive.arcadeDrive(0.0,0.0),
        // When the distance driven exceeds the desired value
        () -> drive.getRightEncoder() <= kSecondDistance / DriveConstants.kEncDistancePerPulse,
        // Requires the drivetrain subsystem
        drive).withTimeout(5.0),
        new FireEverything(false, false, kLaunchSpeed, kLaunchSpeed, launch, lift, cam).withTimeout(5.0).beforeStarting(new WaitCommand(0.5))
      ),
      
      /*
      //STEP 5
      new WaitCommand(0.5),
      new FunctionalCommand(
          // On command start reset encoder
          () -> drive.arcadeDrive(0.0,0.0),
          // Start driving forward at the start of the command
          () -> drive.arcadeDrive(0.0, 0.4),
          // At the end of the command stop driving
          interrupted -> drive.arcadeDrive(0.0,0.0),
          // When the distance driven exceeds the desired value
          () -> drive.getLeftEncoder() == -drive.getRightEncoder(),
          // Requires the drivetrain subsystem
          drive).withTimeout(3.0),

      
      new WaitCommand(0.5),
      new FunctionalCommand(
        // On command start reset encoder
        drive::resetEncoders,
        // Start driving forward at the start of the command
        () -> drive.arcadeDrive(-0.7, 0.15),
        // At the end of the command stop driving
        interrupted -> drive.arcadeDrive(0.0,0.0),
        // When the distance driven exceeds the desired value
        () -> drive.getAvgEncoder() >= kThirdDistance / DriveConstants.kEncDistancePerPulse,
        // Requires the drivetrain subsystem
        drive).withTimeout(4.0),
      */

      // Taxi out of the Tarmac again
      //new DriveStraight(drive).withTimeout(1.0),
      new InstantCommand(drive::setDriveCoast, drive).withTimeout(0.2)
    );
  }
}

// Balls are 41 inches from the outside edge of the tarmac
// In a 25 ft 6 in diameter circle centered at the hub