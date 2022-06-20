// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/*
    The constants are split between different classes based on where they
    will be used. This way we can import a specific class rather than
    the whole constants file.
*/

public final class Constants {
    
    /**  Class for constants used in the LAUNCHER subsystem **/
    public static final class LauncherConstants {
        public static final int kTalonFront = 4;
        public static final int kTalonBack = 6;

        public static final int kPIDTimeout = 10; // miliseconds
        public static final int kPIDidx = 0; // PID Index
        public static final int kSlotidx = 0; // PID Slot Index
        public static final int kEncoderCPR = 4096; // Stadard pulse/rev for ctre encoder
        public static final double kPulses2RPM = 
            // Encoder on gear box should be on the wheel side of the gear box
            // Converts pulses/100ms to rev/minute
            (600.0) / (double) kEncoderCPR;

        // Launch speed is applied to front motor
        // Launch diff is multiplied by the launch speed and applied to the back motor
        public static final double kSpeedLaunch = 0.95;
        public static final double kSpeedLaunchDiff = 1.0; // 1 = No differential
        public static final double kSpeedLaunch7 = 0.85;
        public static final double kSpeedLaunch7Diff = 1.0;
        public static final double kSpeedLaunch9 = 0.80;
        public static final double kSpeedLaunch9Diff = 1.0;
        public static final double kSpeedLaunch11 = 0.75; // Used for low hub shots
        public static final double kSpeedLaunch11Diff = 0.333; // back motor get 1/3 of the speed

    }
    
    /** Class for constants used in the DRIVETRAIN subsystem **/
    public static final class DriveConstants {
        public static final int kTalonLeftFront = 9;
        public static final int kTalonLeftBack = 1;
        public static final int kTalonRightFront = 2;
        public static final int kTalonRightBack = 12;

        public static final int kPIDTimeout = 10; // miliseconds
        public static final int kPIDidx = 0; // PID Index
        public static final int kSlotidx = 0; // PID Slot Index
        public static final int kEncoderCPR = 4096; // Stadard pulse/rev for ctre encoder
        public static final double kWheelDiameter = 6.0; // Inches
        public static final double kEncDistancePerPulse = 
            // Assumes encoders are mounted on wheel shafts
            (kWheelDiameter * Math.PI) / (double) kEncoderCPR;
        
        public static final double SPEED_TURN = 0.55; //0.65
        public static final double SPEED_STRAIGHT = -0.75; //0.85
    }

    /**  Class for constants used in the LIFT subsystem **/
    public static final class LiftConstants {
        public static final int kTalonLift = 7;

        public static final int kLiftDelay = 20;
        
        public static final double SPEED_LIFTUP = 1.0;
        public static final double SPEED_LIFTDOWN = -0.90;
    }

    /** Class for constants used in the INTAKE subsystem **/
    public static final class IntakeConstants {
        public static final int kTalonIntake = 3;

        public static final int kSensorProxDIO = 2;

        public static final double SPEED_INTAKE = 0.88;
        public static final double SPEED_OUTTAKE = -0.90;
    }

    /** Class for constants used in the CLIMB subsystem **/
    public static final class ClimbConstants {
        public static final int kTalonClimb = 5;

        public static final double SPEED_CLIMBUP = 0.90;
        public static final double SPEED_CLIMBDOWN = -0.80;
    }
}
