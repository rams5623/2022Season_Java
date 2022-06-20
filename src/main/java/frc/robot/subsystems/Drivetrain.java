// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
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
  //private final PigeonIMU m_pidgey = new PigeonIMU(DriveConstants.kPidgey);

  // Robot drive for talon followers
  //private final DifferentialDrive m_drive = new DifferentialDrive(m_talonLeftFront, m_talonRightFront);


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

    // Set the right side to be inverted and the followers will follow inversion of master
    m_talonLeftFront.setInverted(false);
    m_talonLeftBack.setInverted(InvertType.FollowMaster);
    m_talonRightFront.setInverted(true);

    m_talonRightBack.setInverted(InvertType.FollowMaster);

    // Set Neutral mode of drive motors to coast
    m_talonLeftFront.setNeutralMode(NeutralMode.Coast);
    m_talonLeftBack.setNeutralMode(NeutralMode.Coast);
    m_talonRightFront.setNeutralMode(NeutralMode.Coast);
    m_talonRightBack.setNeutralMode(NeutralMode.Coast);

    //Configure Motor Controler Neutral Deadband
    m_talonLeftFront.configNeutralDeadband(DriveConstants.kDeadbandLeft);
    m_talonLeftBack.configNeutralDeadband(DriveConstants.kDeadbandLeft);
    m_talonRightFront.configNeutralDeadband(DriveConstants.kDeadbandRight);
    m_talonRightBack.configNeutralDeadband(DriveConstants.kDeadbandRight);

    // Encoder Setup
    m_talonLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    m_talonRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    
    m_talonLeftFront.setSensorPhase(true);
    m_talonRightFront.setSensorPhase(true);

    m_talonLeftFront.configNominalOutputForward(0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configNominalOutputForward(0.0, DriveConstants.kPIDTimeout);
    m_talonLeftFront.configNominalOutputReverse(0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.configNominalOutputReverse(0.0, DriveConstants.kPIDTimeout);

    m_talonLeftFront.configPeakOutputForward(0.9, DriveConstants.kPIDTimeout);
    m_talonRightFront.configPeakOutputForward(0.9, DriveConstants.kPIDTimeout);
    m_talonLeftFront.configPeakOutputReverse(-0.9, DriveConstants.kPIDTimeout);
    m_talonRightFront.configPeakOutputReverse(-0.9, DriveConstants.kPIDTimeout);

    m_talonLeftFront.selectProfileSlot(DriveConstants.kSlotidx, DriveConstants.kPIDidx);
    m_talonRightFront.selectProfileSlot(DriveConstants.kSlotidx, DriveConstants.kPIDidx);

    m_talonLeftFront.config_kF(DriveConstants.kSlotidx, 0.9, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kP(DriveConstants.kSlotidx, 0.01, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kI(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);
    m_talonLeftFront.config_kD(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);

    m_talonRightFront.config_kF(DriveConstants.kSlotidx, 0.9, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kP(DriveConstants.kSlotidx, 0.01, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kI(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);
    m_talonRightFront.config_kD(DriveConstants.kSlotidx, 0.0, DriveConstants.kPIDTimeout);

    m_talonLeftFront.setSelectedSensorPosition(0.0);
    m_talonRightFront.setSelectedSensorPosition(0.0);
    
    // Pidgeon IMU Gyroscope reset to default condifguration
    //m_pidgey.configFactoryDefault();
    //m_pidgey.setYaw(0.0);

  }

  /**
   * The Periodic will continue to run all the time while the robot is running.
   * The encoder values will be sent to the dashboard all the time.
   */
  @Override
  public void periodic() {
    SmartDashboard.putNumber("AvgEncoder", getAvgEncoder());
    SmartDashboard.putNumber("Inches", getAvgEncoder() * DriveConstants.kEncDistancePerPulse);
    SmartDashboard.putNumber("LeftEncoder", getLeftEncoder() * DriveConstants.kEncDistancePerPulse);
    SmartDashboard.putNumber("RightEncoder", getRightEncoder() * DriveConstants.kEncDistancePerPulse);
    //SmartDashboard.putNumber("Pidgeon Yaw Angle", getAngle());
  }

  /**
   * Replaced the DifferentialDrive arcade drive command with a talonSRX version that
   * should perform in the same manner but allow for greater motion control during
   * autonomous mode.
   * @param forward
   * @param rotate
   */
  public void arcadeDrive(double forward, double rotate) {
    m_talonLeftFront.set(ControlMode.PercentOutput, (forward * DriveConstants.SPEED_STRAIGHT), DemandType.ArbitraryFeedForward, rotate * DriveConstants.SPEED_TURN);
    m_talonRightFront.set(ControlMode.PercentOutput, (forward * DriveConstants.SPEED_STRAIGHT), DemandType.ArbitraryFeedForward, -rotate * DriveConstants.SPEED_TURN);

  }

  /**
   * Drive a set distance using the drive encoders
   * @param distance
   */
  public void driveDistance(double distance) {
    // Inch / EncoderCount
    m_talonLeftFront.set(ControlMode.Position, distance / DriveConstants.kEncDistancePerPulse);
    m_talonRightFront.set(ControlMode.Position, distance / DriveConstants.kEncDistancePerPulse);
  }

  public void setDriveCoast() {
    m_talonLeftFront.setNeutralMode(NeutralMode.Coast);
    m_talonLeftBack.setNeutralMode(NeutralMode.Coast);
    m_talonRightFront.setNeutralMode(NeutralMode.Coast);
    m_talonRightBack.setNeutralMode(NeutralMode.Coast);
  }

  public void setDriveBrake() {
    m_talonLeftFront.setNeutralMode(NeutralMode.Brake);
    m_talonLeftBack.setNeutralMode(NeutralMode.Brake);
    m_talonRightFront.setNeutralMode(NeutralMode.Brake);
    m_talonRightBack.setNeutralMode(NeutralMode.Brake);
  }

  /**
   * ENCODER FUNCTIONS
   * 1 Encoder Revolution = 4096 Encoder Counts = 1 Wheel Revolution = 18.84956 Travel Distance (Inches)
   */

  // Reset the drive encoders to read zero
  public void resetEncoders() {
    m_talonLeftFront.setSelectedSensorPosition(0.0);
    m_talonRightFront.setSelectedSensorPosition(0.0);
  }

  // Get the position of the Left Encoder
  public double getLeftEncoder() {
    return m_talonLeftFront.getSelectedSensorPosition();
  }

  // Get the position of the Left Encoder
  public double getRightEncoder() {
    return m_talonRightFront.getSelectedSensorPosition();
  }

  // Get Average Encoder Distance
  public double getAvgEncoder() {
    return (getRightEncoder() + getLeftEncoder()) / 2.0;
  }
  
  /*
  public double getAngle() {
    return m_pidgey.getYaw();
  }

  public void resetAngle() {
    m_pidgey.setYaw(0.0);
  }
  */
}
