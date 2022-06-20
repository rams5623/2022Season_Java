// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Launcher;

public class LaunchBall extends CommandBase {
  private final Launcher m_launcher;
  private final ControlMode m_mode;
  private final double m_front;
  private final double m_back;

  /** Creates a new LaunchBall. */
  public LaunchBall(ControlMode mode, double front, double back, Launcher launcher) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_launcher = launcher;
    m_mode = mode;
    m_front = front;
    m_back = back;
    addRequirements(m_launcher);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_launcher.launch(m_mode, m_front, m_back);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_launcher.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
