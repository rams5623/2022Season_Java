// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimbConstants;

public class Climber extends SubsystemBase {
  private final WPI_TalonSRX m_talonClimb = new WPI_TalonSRX(ClimbConstants.kTalonClimb);

  /** Creates a new Climber. */
  public Climber() {
    // Name everything for the live dashboard
    addChild("Motor", m_talonClimb);

    // Settings for the talonSRX lift motor
    m_talonClimb.configFactoryDefault();
    m_talonClimb.setInverted(true);
    m_talonClimb.setNeutralMode(NeutralMode.Brake);
  }

  public void armsUp() {
    m_talonClimb.set(ControlMode.PercentOutput, ClimbConstants.SPEED_CLIMBUP);
  }

  public void armsDown() {
    m_talonClimb.set(ControlMode.PercentOutput, ClimbConstants.SPEED_CLIMBDOWN);
  }

  public void stop() {
    m_talonClimb.set(ControlMode.PercentOutput, 0.0);
  }
}
