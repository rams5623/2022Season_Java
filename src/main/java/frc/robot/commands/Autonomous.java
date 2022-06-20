// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;


public class Autonomous extends SequentialCommandGroup {
  // Inital drive to pick up ball
  //private final double kRobotSpeed = 0.6; // percent output
  private final double kFirstDistance = 65.0; // inches
  private final double kSecondDistance = -5.0; // inches
  private final double kLaunchSpeed = 7200.0;
  DoubleSupplier ksupplier;

  /** Creates a new Autonomous. */
  public Autonomous(Drivetrain drive, Intake intake, Lift lift, Launcher launch, Cameras cam) {
    addCommands(
      // Intake On while driving to pick up the ball
      new InstantCommand(drive::setDriveBrake, drive).withTimeout(0.2),
      new InstantCommand(drive::resetEncoders, drive).withTimeout(0.2),
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
            new StartEndCommand(intake::ballIn, intake::stop, intake).withTimeout(3.0)
          )),

      new InstantCommand(drive::resetEncoders, drive).withTimeout(0.2),

      // Drive reverse back to the tarmac line to shoot both balls
      new FunctionalCommand(
        // On command start reset encoder
        drive::resetEncoders,
        // Start driving forward at the start of the command
        () -> drive.arcadeDrive(0.3, 0.0),
        // At the end of the command stop driving
        interrupted -> drive.arcadeDrive(0.0,0.0),
        // When the distance driven exceeds the desired value
        () -> drive.getAvgEncoder() <= kSecondDistance / DriveConstants.kEncDistancePerPulse,
        // Requires the drivetrain subsystem
        drive).withTimeout(1.2),
      
      // Launch both balls while aiming
      parallel(
        new FireEverything(ControlMode.Velocity, kLaunchSpeed, kLaunchSpeed, launch, lift).withTimeout(5.0),
        new AimAssist(0.0, 0.0, drive, cam).withTimeout(5.0)
      ),
      
      /*parallel(
        new LaunchBall(ControlMode.Velocity, 7400, 7400, launch).withTimeout(5.0),
        new StartEndCommand(lift::ballUp, lift::stop, lift).withTimeout(5.0)
        ),*/

      // Taxi out of the Tarmac again
      new DriveStraight(drive).withTimeout(0.8),
      new InstantCommand(drive::setDriveCoast, drive).withTimeout(0.2)
    );
  }
}

// Balls are 41 inches from the outside edge of the tarmac
// In a 25 ft 6 in diameter circle centered at the hub