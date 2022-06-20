// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.FireEverything;
import frc.robot.Constants.LauncherConstants;
import frc.robot.commands.AimAssist;
import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoTwoball;
import frc.robot.commands.AutoWall;
import frc.robot.commands.Autonomous;
import frc.robot.commands.DriveStraight;
import frc.robot.subsystems.Cameras;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Launcher;
import frc.robot.subsystems.Lift;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
  private final Autonomous m_autoCommand = new Autonomous(m_drivetrain, m_intake, m_lift, m_launcher, m_cameras);
  private final AutoWall m_autoWall = new AutoWall(m_drivetrain, m_intake, m_lift, m_launcher, m_cameras);
  private final AutoTwoball m_autotwoball = new AutoTwoball(m_drivetrain, m_intake, m_lift, m_launcher, m_cameras);

  /*
  // Pick Up Second Ball, Drive Back and Launch Two Balls and then Taxi
  private final SequentialCommandGroup m_autoEverything = new SequentialCommandGroup(
    parallel(
      new SetDriveDistance(m_drivetrain),
      new StartEndCommand(m_intake::ballIn, m_intake::stop, m_intake).withTimeout(4.0)
    ),
    new SetDriveDistance(m_drivetrain).withTimeout(4.0),
    new FireEverything(ControlMode.Velocity, 7400, 7400, m_launcher, m_lift).withTimeout(5.0)
  );
  */
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  // Drive straight and pick up a second ball
  private final ParallelCommandGroup m_autoStraightBall = new ParallelCommandGroup(
    new DriveStraight(m_drivetrain).withTimeout(3.0),
    new StartEndCommand(m_intake::ballIn, m_intake::stop, m_intake).withTimeout(3.0));
  
  
  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Smart dashboard command buttons
    /**
     * 
     */
    

    // Smartdashboard the subsystems
    SmartDashboard.putData(m_drivetrain);
    SmartDashboard.putData(m_launcher);
    //SmartDashboard.putData(m_Jdriver);
    //SmartDashboard.putData(m_Jop);
    m_chooser.setDefaultOption("Simple Drive Straight Auto", m_autoStraightBall);
    m_chooser.addOption("2 Ball Limelight", m_autoCommand);
    m_chooser.addOption("Wall Ball Auto", m_autoWall);
    m_chooser.addOption("Two Ball Auto", m_autotwoball);
    SmartDashboard.putData(m_chooser);

    // Default Commands
    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(m_Jdriver::getY, m_Jdriver::getZ, m_drivetrain)
    );

    // Configure the button bindings
    configureButtonBindings();
  }


  /**
   * Use this method to define your button->command mappings. Initiate the buttons then use
   * them to call commands.
   */
  private void configureButtonBindings() {
    // Driver Joystick if its connected
    //if (m_Jdriver.isConnected()) {
      // Create the Buttons
      final JoystickButton Jdriver_1 = new JoystickButton(m_Jdriver, 1);
      final JoystickButton Jdriver_3 = new JoystickButton(m_Jdriver, 3);
      final JoystickButton Jdriver_5 = new JoystickButton(m_Jdriver, 5);
      //final JoystickButton Jdriver_7 = new JoystickButton(m_Jdriver, 7);
      final JoystickButton Jdriver_8 = new JoystickButton(m_Jdriver, 8);
      final JoystickButton Jdriver_12 = new JoystickButton(m_Jdriver, 12);
      
      // Use the buttons
      // Holding the trigger will turn the drivetrain to line up with the upper hub
      Jdriver_1.whenHeld(new AimAssist(m_Jdriver.getY(), m_Jdriver.getZ(), m_drivetrain, m_cameras));
      // climber arms up and down
      Jdriver_5.whenHeld(new StartEndCommand(m_climber::armsUp, m_climber::stop, m_climber));
      Jdriver_3.whenHeld(new StartEndCommand(m_climber::armsDown, m_climber::stop, m_climber));
      // Slow Button???
      Jdriver_8.whenHeld(new ArcadeDrive(() -> m_Jdriver.getY(), () -> m_Jdriver.getZ() * 0.4, m_drivetrain));
      Jdriver_12.whenHeld(new StartEndCommand(m_drivetrain::resetEncoders, m_drivetrain::resetEncoders, m_drivetrain));
    //}

    // Operator Joystick if its connected
    //if (m_Jop.isConnected()) {
      // Create the Buttons
      final JoystickButton Jop_1 = new JoystickButton(m_Jop, 1);
      final JoystickButton Jop_2 = new JoystickButton(m_Jop, 2);
      final JoystickButton Jop_3 = new JoystickButton(m_Jop, 3);
      final JoystickButton Jop_4 = new JoystickButton(m_Jop, 4);
      final JoystickButton Jop_6 = new JoystickButton(m_Jop, 6);
      final JoystickButton Jop_7 = new JoystickButton(m_Jop, 7);
      final JoystickButton Jop_5 = new JoystickButton(m_Jop, 5);
      final JoystickButton Jop_9 = new JoystickButton(m_Jop, 9);
      final JoystickButton Jop_10 = new JoystickButton(m_Jop, 10);
      final JoystickButton Jop_11 = new JoystickButton(m_Jop, 11);
      //final JoystickButton Jop_12 = new JoystickButton(m_Jop, 12);

      // Use the Buttons
      // Intake the ball command
      Jop_2.whenHeld(new StartEndCommand(m_intake::ballIn, m_intake::stop, m_intake));
      // Spit out the balls by outaking and lowering the lift
      Jop_3.whenHeld(new ParallelCommandGroup(
        new StartEndCommand(m_intake::ballOut, m_intake::stop, m_intake),
        new StartEndCommand(m_lift::ballDown, m_lift::stop, m_lift)
      )); 
      
      // Manual control of the lift up and down
      Jop_6.whenHeld(new StartEndCommand(m_lift::ballUp, m_lift::stop, m_lift));
      Jop_4.whenHeld(new StartEndCommand(m_lift::ballDown, m_lift::stop, m_lift));
  
      // Launcher controls that lower the lift to get the balls out of the way then spins up the motors
      Jop_1.whenHeld(new FireEverything(false, false, LauncherConstants.kSpeedLaunch, LauncherConstants.kSpeedLaunch * LauncherConstants.kSpeedLaunchDiff, m_launcher, m_lift, m_cameras));
      Jop_7.whenHeld(new FireEverything(false, false, LauncherConstants.kSpeedLaunch7, LauncherConstants.kSpeedLaunch7 * LauncherConstants.kSpeedLaunch7Diff, m_launcher, m_lift, m_cameras));
      Jop_9.whenHeld(new FireEverything(false, false, LauncherConstants.kSpeedLaunch9, LauncherConstants.kSpeedLaunch9 * LauncherConstants.kSpeedLaunch9Diff, m_launcher, m_lift, m_cameras));
      Jop_11.whenHeld(new FireEverything(false, false, LauncherConstants.kSpeedLaunch11, LauncherConstants.kSpeedLaunch11 * LauncherConstants.kSpeedLaunch11Diff, m_launcher, m_lift, m_cameras));
      Jop_10.whenHeld( new SequentialCommandGroup(
        new InstantCommand(m_cameras::processMode,m_cameras).withTimeout(0.2),
        new FireEverything(true, false, m_cameras.getVelocityCmd(), m_cameras.getVelocityCmd(), m_launcher, m_lift, m_cameras)
      ));
      Jop_5.whenHeld(new StartEndCommand(m_launcher::unlaunch, m_launcher::stop, m_launcher));
  }


  /**
   * This command inputs an int that will determine which auto mode
   * to use during autonomus
   * @param autoOption is int that decides
   * @return auto command
   */
  public Command getAutonomousCommand() {
    return m_chooser.getSelected();
  }
}
