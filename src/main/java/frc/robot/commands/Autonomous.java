// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Autonomous extends SequentialCommandGroup {
  /** Creates a new Autonomous. */
  public Autonomous(Drivetrain drive, Intake intake, Lift lift, Launcher launch) {
    addCommands(
      new DriveStraight(drive).withTimeout(1.0),
      parallel(
        new LaunchBall(ControlMode.Velocity, 7400, 7400, launch).withTimeout(5.0),
        new StartEndCommand(lift::ballUp, lift::stop, lift).withTimeout(5.0)
        ),
      parallel(
        new DriveStraight(drive).withTimeout(3.0),
        new StartEndCommand(intake::ballIn, intake::stop, intake).withTimeout(3.0)
      )
    );
  }
}
