// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;


public class FireEverything extends SequentialCommandGroup {
  /** Creates a new FireEverything. */
  public FireEverything(ControlMode mode, double front, double back, Launcher launch, Lift lift) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new StartEndCommand(lift::ballDown, lift::stop, lift).withTimeout(0.3),
      parallel(
        new LaunchBall(mode, front, back, launch),
        new StartEndCommand(lift::ballUp, lift::stop, lift).beforeStarting(new WaitCommand(0.5))
      )
    );
  }
}

/*
public class FireEverything extends ParallelCommandGroup {
  public FireEverything(ControlMode mode, double front, double back, Launcher launch, Lift lift) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new LaunchBall(mode, front, back, launch),
      new StartEndCommand(lift::ballUp, lift::stop, lift).beforeStarting(new WaitCommand(0.5))
    );
  }
}
*/
