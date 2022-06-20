// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  WPI_TalonSRX m_talonFront = new WPI_TalonSRX(LauncherConstants.kTalonFront);
  WPI_TalonSRX m_talonBack = new WPI_TalonSRX(LauncherConstants.kTalonBack);

  /** Creates a new Launcher. */
  public Launcher() {
    addChild("Front Motor", m_talonFront);
    addChild("Back Motor", m_talonBack);

    m_talonFront.configFactoryDefault();
    m_talonBack.configFactoryDefault();

    m_talonFront.setInverted(false);
    m_talonBack.setInverted(false);

    m_talonFront.setNeutralMode(NeutralMode.Coast);
    m_talonBack.setNeutralMode(NeutralMode.Coast);

    // Encoder Set up
    m_talonFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    m_talonBack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    m_talonFront.setSensorPhase(true);
    m_talonBack.setSensorPhase(true);

    m_talonFront.configNominalOutputForward(0.0, LauncherConstants.kPIDTimeout);
    m_talonBack.configNominalOutputForward(0.0, LauncherConstants.kPIDTimeout);
    m_talonFront.configNominalOutputReverse(0.0, LauncherConstants.kPIDTimeout);
    m_talonBack.configNominalOutputReverse(0.0, LauncherConstants.kPIDTimeout);
    
    m_talonFront.configPeakOutputForward(1.0, LauncherConstants.kPIDTimeout);
    m_talonBack.configPeakOutputForward(1.0, LauncherConstants.kPIDTimeout);
    m_talonFront.configPeakOutputReverse(-1.0, LauncherConstants.kPIDTimeout);
    m_talonBack.configPeakOutputReverse(-1.0, LauncherConstants.kPIDTimeout);

    m_talonFront.selectProfileSlot(LauncherConstants.kSlotidx, LauncherConstants.kPIDidx);
    m_talonBack.selectProfileSlot(LauncherConstants.kSlotidx, LauncherConstants.kPIDidx);

    m_talonFront.config_kF(LauncherConstants.kSlotidx, 0.17, LauncherConstants.kPIDTimeout);
    m_talonFront.config_kP(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);
    m_talonFront.config_kI(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);
    m_talonFront.config_kD(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);

    m_talonBack.config_kF(LauncherConstants.kSlotidx, 0.17, LauncherConstants.kPIDTimeout);
    m_talonBack.config_kP(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);
    m_talonBack.config_kI(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);
    m_talonBack.config_kD(LauncherConstants.kSlotidx, 0.0, LauncherConstants.kPIDTimeout);

    m_talonFront.setSelectedSensorPosition(0, LauncherConstants.kPIDidx, LauncherConstants.kPIDTimeout);
    m_talonBack.setSelectedSensorPosition(0, LauncherConstants.kPIDidx, LauncherConstants.kPIDTimeout);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("BackEncoder", getBackEncoder() );//* DriveConstants.kEncDistancePerPulse);
    SmartDashboard.putNumber("FrontEncoder", getFrontEncoder() );//* DriveConstants.kEncDistancePerPulse);
  }

  public void launch(boolean vel, double front, double back) {
    if (vel) {
      m_talonFront.set(ControlMode.Velocity, front);
      m_talonBack.set(ControlMode.Velocity, back);
    } else {
      m_talonFront.set(ControlMode.PercentOutput, front);
      m_talonBack.set(ControlMode.PercentOutput, back);
    }
  }

  public void unlaunch() {
    m_talonFront.set(ControlMode.PercentOutput, -0.7);
    m_talonBack.set(ControlMode.PercentOutput, -0.7);
  }

  public void stop() {
    m_talonFront.set(ControlMode.PercentOutput, 0.0);
    m_talonBack.set(ControlMode.PercentOutput, 0.0);
  }

  public double getFrontEncoder() {
    return m_talonFront.getSelectedSensorVelocity();
  }

  public double getBackEncoder() {
    return m_talonBack.getSelectedSensorVelocity();
  }
}
