// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FireEverything;
//import frc.robot.commands.LaunchBall;
import frc.robot.commands.SwitchCamera;
import frc.robot.Constants.LauncherConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveStraight;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
//import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;



public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Cameras m_cameras = new Cameras();
  private final Climber m_climber = new Climber();
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Intake m_intake = new Intake();
  private final Launcher m_launcher = new Launcher();
  private final Lift m_lift = new Lift();

  private final Joystick m_Jdriver = new Joystick(0);
  private final Joystick m_Jop = new Joystick(1);

  // Autonomous Commands
  private final Autonomous m_autoCommand = new Autonomous(m_drivetrain, m_intake, m_lift, m_launcher);

  //private final Autonomous m_autoEverything = new Seq

  private final ParallelCommandGroup m_autoStraightBall = new ParallelCommandGroup(
    new DriveStraight(m_drivetrain).withTimeout(1.0),
    new StartEndCommand(m_intake::ballIn, m_intake::stop, m_intake));

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Smart dashboard command buttons
    /**
     * 
     */

    // Default Commands
    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(m_Jdriver::getY, m_Jdriver::getZ, m_drivetrain)
    );

    // Smartdashboard the subsystems
    SmartDashboard.putData(m_drivetrain);
    SmartDashboard.putData(m_launcher);

    // Configure the button bindings
    configureButtonBindings();
  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Driver Joystick
    final JoystickButton Jdriver_1 = new JoystickButton(m_Jdriver, 1);
    final JoystickButton Jdriver_3 = new JoystickButton(m_Jdriver, 3);
    final JoystickButton Jdriver_5 = new JoystickButton(m_Jdriver, 5);
    final JoystickButton Jdriver_7 = new JoystickButton(m_Jdriver, 7);
    final JoystickButton Jdriver_8 = new JoystickButton(m_Jdriver, 8);
    // Operator Joystick
    final JoystickButton Jop_1 = new JoystickButton(m_Jop, 1);
    final JoystickButton Jop_2 = new JoystickButton(m_Jop, 2);
    final JoystickButton Jop_3 = new JoystickButton(m_Jop, 3);
    final JoystickButton Jop_4 = new JoystickButton(m_Jop, 4);
    final JoystickButton Jop_6 = new JoystickButton(m_Jop, 6);
    final JoystickButton Jop_7 = new JoystickButton(m_Jop, 7);
    final JoystickButton Jop_9 = new JoystickButton(m_Jop, 9);
    final JoystickButton Jop_11 = new JoystickButton(m_Jop, 11);

    // Command Control for DRIVER
    Jdriver_1.toggleWhenPressed(new SwitchCamera(m_cameras));
    Jdriver_5.whenHeld(new StartEndCommand(m_climber::armsUp, m_climber::stop, m_climber));
    Jdriver_3.whenHeld(new StartEndCommand(m_climber::armsDown, m_climber::stop, m_climber));

    Jdriver_8.whenHeld(new ArcadeDrive(m_Jdriver::getY, m_Jdriver::getZ, m_drivetrain));

    // Command Control for OPERATOR
    Jop_2.whenHeld(new StartEndCommand(m_intake::ballIn, m_intake::stop, m_intake));
    Jop_3.whenHeld(new StartEndCommand(m_intake::ballOut, m_intake::stop, m_intake));

    Jop_6.whenHeld(new StartEndCommand(m_lift::ballUp, m_lift::stop, m_lift));
    Jop_4.whenHeld(new StartEndCommand(m_lift::ballDown, m_lift::stop, m_lift));

    Jop_1.whenHeld(new FireEverything(ControlMode.PercentOutput, LauncherConstants.kSpeedLaunch, LauncherConstants.kSpeedLaunch * LauncherConstants.kSpeedLaunchDiff, m_launcher, m_lift));
    Jop_7.whenHeld(new FireEverything(ControlMode.PercentOutput, LauncherConstants.kSpeedLaunch7, LauncherConstants.kSpeedLaunch7 * LauncherConstants.kSpeedLaunch7Diff, m_launcher, m_lift));
    Jop_9.whenHeld(new FireEverything(ControlMode.PercentOutput, LauncherConstants.kSpeedLaunch9, LauncherConstants.kSpeedLaunch9 * LauncherConstants.kSpeedLaunch9Diff, m_launcher, m_lift));
    Jop_11.whenHeld(new FireEverything(ControlMode.PercentOutput, LauncherConstants.kSpeedLaunch11, LauncherConstants.kSpeedLaunch11 * LauncherConstants.kSpeedLaunch11Diff, m_launcher, m_lift));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
    return m_autoStraightBall;
  }
}
