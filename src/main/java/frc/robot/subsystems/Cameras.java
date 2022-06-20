// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CameraConstants;
import frc.robot.Constants.LauncherConstants;

public class Cameras extends SubsystemBase {
  NetworkTable m_table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = m_table.getEntry("tx");
  NetworkTableEntry ty = m_table.getEntry("ty");
  NetworkTableEntry ta = m_table.getEntry("ta");
  NetworkTableEntry tv = m_table.getEntry("tv");

  /** Creates a new Cameras. */
  public Cameras() {}

  public void configureLime() {
    // Uses processing pipelin # 0
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(0);
    // Starts off camera in driver mode
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    // Starts camera with LEDs Off
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    // Sets the camera stream to side by side
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(0);
  }

  public void driveMode() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }

  public void processMode() {
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(0);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(0);
  }

  public boolean hasTarget() {
    if (tv.getDouble(0.0) < 1.0) {
      return false;
    } else {
      return true;
    }
  }

  public double getTurnError() {
    return tx.getDouble(0.0);
  }

  public double getHeightError() {
    return ty.getDouble(0.0);
  }

  public double clampValue(double in, double min, double max) {
    if (in > max) {
      return (double) max;
    } else if (in < min) {
      return (double) min;
    } else {
      return 0.0;
    }
  }

  public double getTurnCmd() {
    return clampValue(getTurnError() * CameraConstants.kSteer, -0.2, 0.2);
  }

  public double getVelocityCmd() {
    return (LauncherConstants.kMaxVelocity - (getHeightError() * CameraConstants.kLaunch));
  }
}
