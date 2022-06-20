// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
//import com.ctre.phoenix.sensors.PigeonIMU;

public class Drivetrain extends SubsystemBase {
  private final WPI_TalonSRX m_talonLeftFront = new WPI_TalonSRX(DriveConstants.kTalonLeftFront);
  private final WPI_TalonSRX m_talonLeftBack = new WPI_TalonSRX(DriveConstants.kTalonLeftBack);
  private final WPI_TalonSRX m_talonRightFront = new WPI_TalonSRX(DriveConstants.kTalonRightFront);
  private final WPI_TalonSRX m_talonRightBack = new WPI_TalonSRX(DriveConstants.kTalonRightBack);

  // Gyroscope and Accelerometer
  //private final PigeonIMU m_pidgey = new PigeonIMU(DriveConstants.kTalonLeftBack);

  
  /* MOTOR CONTROLLER GROUPS DONT SEEM TO WORK WITH TALON FOLLOWERS
  // Motors on the left side of the drive chassis
  private final MotorControllerGroup m_leftMotors = 
    new MotorControllerGroup(m_talonLeftFront,m_talonLeftBack);
  
  // Motors on the right side of the drive chassis
  private final MotorControllerGroup m_rightMotors = 
    new MotorControllerGroup(m_talonRightFront,m_talonRightBack);
  
  // Robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);
  */

  // Robot drive for talon followers
  private final DifferentialDrive m_drive = new DifferentialDrive(m_talonLeftFront, m_talonRightFront);

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // Reset talonSRX's to default factory settings to prevent any carryovers
    m_talonLeftFront.configFactoryDefault();
    m_talonLeftBack.configFactoryDefault();
    m_talonRightFront.configFactoryDefault();
    m_talonRightBack.configFactoryDefault();

    // Set the back motors to follow the front motors
    m_talonLeftBack.follow(m_talonLeftFront);
    m_talonRightBack.follow(m_talonRightFront);

    // Set the right side to be inverted
    //m_rightMotors.setInverted(true);
    m_talonLeftFront.setInverted(false);
    m_talonLeftBack.setInverted(InvertType.FollowMaster);
    m_talonRightFront.setInverted(true);
    m_talonRightBack.setInverted(InvertType.FollowMaster);

    // Set Neutral mode of drive motors to coast
    m_talonLeftFront.setNeutralMode(NeutralMode.Coast);
    m_talonLeftBack.setNeutralMode(NeutralMode.Coast);
    m_talonRightFront.setNeutralMode(NeutralMode.Coast);
    m_talonRightBack.setNeutralMode(NeutralMode.Coast);

    // Encoder Setup
    m_talonLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    m_talonRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    
    m_talonLeftFront.setSensorPhase(false);
    m_talonRightFront.setSensorPhase(true);

    m_talonLeftFront.configNominalOutputForward(0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configNominalOutputForward(0.0, DriveConstants.kPIDTimeout);
    m_talonLeftFront.configNominalOutputReverse(0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configNominalOutputReverse(0.0, DriveConstants.kPIDTimeout);

    m_talonLeftFront.configPeakOutputForward(1.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configPeakOutputForward(1.0, DriveConstants.kPIDTimeout);
    m_talonLeftFront.configPeakOutputReverse(1.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configPeakOutputReverse(1.0, DriveConstants.kPIDTimeout);

    m_talonLeftFront.selectProfileSlot(DriveConstants.kSlotidx, DriveConstants.kPIDidx);
    m_talonRightFront.selectProfileSlot(DriveConstants.kSlotidx, DriveConstants.kPIDidx);

    m_talonLeftFront.config_kF(DriveConstants.kSlotidx, 0.5, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kP(DriveConstants.kSlotidx, 0.01, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kI(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kD(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);

    m_talonRightFront.config_kF(DriveConstants.kSlotidx, 0.5, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kP(DriveConstants.kSlotidx, 0.01, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kI(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kD(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);

    m_talonLeftFront.setSelectedSensorPosition(0.0);
    m_talonRightFront.setSelectedSensorPosition(0.0);
    // Pidgeon IMU Gyroscope reset to default condifguration
    //m_pidgey.configFactoryDefault();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("AvgEncoder", getAvgEncoder());
    SmartDashboard.putNumber("LeftEncoder", getLeftEncoder());
    SmartDashboard.putNumber("RightEncoder", getRightEncoder());
  }

  // Drive the robot with regular arcade joystick controls //
  public void arcadeDrive(double forward, double rotate) {
    m_drive.arcadeDrive(forward, rotate);
  }

  /** Set the max allowable output of the drivetrain motors. can be used to slow the drive down **/
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  
  // Reset the drive encoders to read zero
  public void resetEncoders() {
    m_talonLeftFront.setSelectedSensorPosition(0.0, DriveConstants.kPIDidx, DriveConstants.kPIDTimeout);
    m_talonRightFront.setSelectedSensorPosition(0.0, DriveConstants.kPIDidx, DriveConstants.kPIDTimeout);
  }

  // Get the position of the Left Encoder
  public double getLeftEncoder() {
    return m_talonLeftFront.getSelectedSensorPosition() * DriveConstants.kEncDistancePerPulse;
  }

  // Get the position of the Left Encoder
  public double getRightEncoder() {
    return m_talonRightFront.getSelectedSensorPosition() * DriveConstants.kEncDistancePerPulse;
  }

  // Get Average Encoder Distance
  public double getAvgEncoder() {
    return (getRightEncoder() + getLeftEncoder()) / 2.0;
  }
  
}
