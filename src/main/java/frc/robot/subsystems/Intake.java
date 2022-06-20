// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Intake extends SubsystemBase {
  private final WPI_TalonSRX m_talonIntake = new WPI_TalonSRX(IntakeConstants.kTalonIntake);
  private final DigitalInput m_proxLift = new DigitalInput(IntakeConstants.kSensorProxDIO);

  /** Creates a new Intake. */
  public Intake() {
    // Name everything for the live dashboard
    addChild("Motor", m_talonIntake);
    addChild("Ball Prox", m_proxLift);

    // Settings for the talonSRX lift motor
    m_talonIntake.configFactoryDefault();
    m_talonIntake.setInverted(true);
    m_talonIntake.setNeutralMode(NeutralMode.Coast);
  }

  public boolean hasTwoBalls() {
    return !m_proxLift.get();
  }

  public void ballIn() {
    m_talonIntake.set(ControlMode.PercentOutput, IntakeConstants.SPEED_INTAKE);
  }

  public void ballOut() {
    m_talonIntake.set(ControlMode.PercentOutput, IntakeConstants.SPEED_OUTTAKE);
  }

  public void stop() {
    m_talonIntake.set(ControlMode.PercentOutput, 0.0);
  }
}
