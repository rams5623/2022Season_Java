// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Drivetrain;

public class AimAssist extends CommandBase {
  Drivetrain m_drivetrain;
  Cameras m_camera;
  Double m_fwd;
  Double m_rot;

  /** Creates a new AimAssist. */
  public AimAssist(Double straight, Double turn, Drivetrain drive, Cameras cam) {
    m_drivetrain = drive;
    m_camera = cam;
    m_fwd = straight;
    m_rot = turn;
    addRequirements(m_drivetrain,m_camera);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_camera.processMode();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_camera.hasTarget()) {
      m_drivetrain.arcadeDrive(m_fwd, m_camera.getTurnCmd());
    } else {
      m_drivetrain.arcadeDrive(m_fwd, m_rot);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_camera.driveMode();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
