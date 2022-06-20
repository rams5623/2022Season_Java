// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Launcher;

public class LaunchAssist extends CommandBase {
  Launcher m_launch;
  Cameras m_cam;
  /** Creates a new LaunchAssist. */
  public LaunchAssist(Launcher launch, Cameras cam) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_launch = launch;
    m_cam = cam;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_cam.processMode();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_cam.hasTarget()) {
      m_launch.launch(false, m_cam.getVelocityCmd(), m_cam.getVelocityCmd());
    } else {
      m_launch.launch(false, 0.90, 0.90);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_cam.driveMode();
    m_launch.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
