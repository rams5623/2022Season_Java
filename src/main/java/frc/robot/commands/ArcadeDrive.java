// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends CommandBase {
  private final Drivetrain m_drivetrain;
  private final DoubleSupplier m_straight;
  private final DoubleSupplier m_turn;
  
  /** Creates a new ArcadeDrive. */
  public ArcadeDrive(DoubleSupplier straight, DoubleSupplier turn, Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    m_straight = straight;
    m_turn = turn;
    addRequirements(m_drivetrain);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(DriveConstants.SPEED_STRAIGHT * m_straight.getAsDouble(), DriveConstants.SPEED_TURN * m_turn.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0.0, 0.0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
