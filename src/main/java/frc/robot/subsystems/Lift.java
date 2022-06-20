// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LiftConstants;

public class Lift extends SubsystemBase {
  private final WPI_TalonSRX m_talonLift = new WPI_TalonSRX(LiftConstants.kTalonLift);
  
  /** Creates a new Lift. */
  public Lift() {
    // Name everything for the live dashboard
    addChild("Motor", m_talonLift);

    // Settings for the talonSRX lift motor
    m_talonLift.configFactoryDefault();
    m_talonLift.setInverted(true);
    m_talonLift.setNeutralMode(NeutralMode.Coast);
  }

  public void ballUp() {
    m_talonLift.set(ControlMode.PercentOutput, LiftConstants.SPEED_LIFTUP);
  }

  public void ballDown() {
    m_talonLift.set(ControlMode.PercentOutput, LiftConstants.SPEED_LIFTDOWN);
  }

  public void stop() {
    m_talonLift.set(ControlMode.PercentOutput, 0.0);
  }
}
