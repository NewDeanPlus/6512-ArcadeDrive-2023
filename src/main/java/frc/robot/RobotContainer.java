// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: RobotContainer.

package frc.robot;

import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SelectCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
import static edu.wpi.first.wpilibj2.command.Commands.*;
import static frc.robot.Constants.Speeds.*;
import java.lang.Math.*;
import java.util.Map;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
// The robot's subsystems
    public final Arm m_arm = new Arm();
    public final DriveTrain m_driveTrain = new DriveTrain();
    public final Elevator m_elev = new Elevator();
    public final Pneumatics m_p = new Pneumatics();
    public final Vision m_vis = new Vision();

// Joysticks
private final Joystick joystick1 = new Joystick(0);
//private final XboxController xbox = new XboxController(1);
private final Joystick j2 = new Joystick(1);


  public CommandBase auto2 = Commands.sequence(
    Commands.parallel(
      m_driveTrain.lockWheels().raceWith(new WaitCommand(0.1)),
      m_arm.moveArmDown(ASpeedDown).until(()->m_arm.returnEncoderPos()>=100),
      m_elev.moveElevatorUp(ESpeedUp)
    ),
      m_p.moveClawR(),
    Commands.parallel(
      m_arm.moveArmUp(ASpeedUp).until(()->m_arm.returnEncoderPos()<=0),
      m_elev.moveElevatorDown(ESpeedDown).raceWith(new WaitCommand(5))
    ),//.andThen(
      m_p.moveClawF(),//.alongWith(
      m_driveTrain.autoDrive().until(()->(m_driveTrain.re.getPosition()) <= (-10.2*7)),//divides 120 inches (10 ft) by circumference of wheel, about 6.4 rotations
  //move 10ft backwards, get encoder value
    m_driveTrain.unlockWheels())
    /*)) */;

    public CommandBase restToUp = Commands.parallel(
      Commands.sequence(
          m_arm.moveArmDown(ASpeedDown).until(()->m_arm.returnEncoderPos()>=100),
          m_arm.setTargetState("Up")
      ),
      m_elev.moveElevatorUp(ESpeedUp)
    ); 
  
    public CommandBase restToDown = Commands.sequence(
      m_arm.moveArmDown(ASpeedDown).until(()->m_arm.returnEncoderPos()>=150),
      m_arm.setTargetState("Down")
    );
  
    public CommandBase upToRest = Commands.parallel(
      Commands.sequence(
          m_arm.moveArmUp(ASpeedUp).until(()->m_arm.returnEncoderPos()<=0),
          m_arm.setTargetState("Rest")
      ),
      m_elev.moveElevatorDown(ESpeedDown)
    );
  
    public CommandBase upToDown = Commands.sequence(
      m_elev.moveElevatorDown(ESpeedDown),
      m_arm.moveArmDown(ASpeedDown).until(()->m_arm.returnEncoderPos()>=150),
      m_arm.setTargetState("Down")
    );
  
    public CommandBase downToRest = Commands.sequence(
      m_arm.moveArmUp(ASpeedUp).until(()->m_arm.returnEncoderPos()<=0),
      m_arm.setTargetState("Rest")
    );
  
    public CommandBase downToUp = Commands.sequence(
      m_elev.moveElevatorUp(ESpeedUp),
      m_arm.moveArmDown(ASpeedDown).until(()->m_arm.returnEncoderPos()>=100),
      m_arm.setTargetState("Up")
    );
  
    public CommandBase def;
  
      public enum CommandSelector{
        restToUp,
        restToDown,
        upToRest,
        upToDown,
        downToRest,
        downToUp,
        def
      }
  
      public CommandSelector selectRestCommand(){ //these commands based on target state and get current state to determine what to do
        switch(SmartDashboard.getString("ArmState", " ")){
          case "Up": return CommandSelector.upToRest;
          case "Down": return CommandSelector.downToRest;
          default: return CommandSelector.def;
        }
      }
  
      public CommandSelector selectUpCommand(){
        switch(SmartDashboard.getString("ArmState", " ")){
          case "Rest": return CommandSelector.restToUp;
          case "Down": return CommandSelector.downToUp;
          default: return CommandSelector.def;
        }
      }
  
      public CommandSelector selectDownCommand(){
        switch(SmartDashboard.getString("ArmState", " ")){
          case "Up": return CommandSelector.upToDown;
          case "Rest": return CommandSelector.restToDown;
          default: return CommandSelector.def;
        }
      }
  
      private final Command selectRCommand = new SelectCommand(
        Map.ofEntries(
          Map.entry(CommandSelector.upToRest, upToRest),
          Map.entry(CommandSelector.downToRest, downToRest)
        ), this::selectRestCommand
      );
  
      private final Command selectUCommand = new SelectCommand(
        Map.ofEntries(
          Map.entry(CommandSelector.restToUp, restToUp),
          Map.entry(CommandSelector.downToUp, downToUp)
        ), this::selectUpCommand
      );
  
      private final Command selectDCommand = new SelectCommand(
        Map.ofEntries(
          Map.entry(CommandSelector.upToDown, upToDown),
          Map.entry(CommandSelector.restToDown, restToDown)
        ), this::selectDownCommand
      );

 
    
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

  
  // A chooser for autonomous commands
  //SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
  * The container for the robot.  Contains subsystems, OI devices, and commands.
  */
  private RobotContainer() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Smartdashboard Subsystems


    // SmartDashboard Buttons
    //SmartDashboard.putData("Autonomous Command", new AutonomousCommand(m_arm, m_driveTrain, m_elev, m_p));
    //SmartDashboard.putData("MoveArmUp", new MoveArmUp(m_arm, joystick1));
    //SmartDashboard.putData("MoveArmDown", new MoveArmDown(m_arm, joystick1));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SMARTDASHBOARD
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND

//DRIVE TRAIN DEFAULT COMMAND
    //m_driveTrain.setDefaultCommand(new TankDrive(m_driveTrain));

    m_driveTrain.setDefaultCommand(new TankDrive(m_driveTrain));

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=SUBSYSTEM_DEFAULT_COMMAND

    // Configure autonomous sendable chooser
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    //m_chooser.setDefaultOption("Autonomous Command", autonomous3);

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=AUTONOMOUS

    //SmartDashboard.putData("Auto Mode", m_chooser);
    //SmartDashboard.putNumber("Speed", 0);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
// Create some buttons
                        
/*final JoystickButton emergencyStop = new JoystickButton(joystick1,1);
emergencyStop.whileTrue(parallel(m_arm.stopArm(), m_elev.stopElevator()));*/

final JoystickButton clawOpen = new JoystickButton(joystick1, 7);
clawOpen.onTrue(m_p.moveClawF());

final JoystickButton clawClose = new JoystickButton(joystick1, 6);
clawClose.onTrue(m_p.moveClawR());

//Normal inefficient arm/elevator controls

final JoystickButton armUp = new JoystickButton(j2, 7);        
armUp.whileTrue(m_arm.moveArmUp(ASpeedUp));
                        
final JoystickButton armDown = new JoystickButton(j2, 8);        
armDown.whileTrue(m_arm.moveArmDown(ASpeedDown));//.whileFalse(m_arm.stopArm());
/* 
final JoystickButton elevUp = new JoystickButton(xbox, 3);        
//elevUp.whileTrue(new MoveElevator(m_arm, joystick1,"Up").withInterruptBehavior(InterruptionBehavior.kCancelSelf));
elevUp.toggleOnTrue(m_elev.moveElevatorUp(ESpeedUp));//.toggleOnFalse(m_elev.stopElevator());

final JoystickButton elevDown = new JoystickButton(xbox, 1);        
//elevDown.whileTrue(new MoveElevator(m_arm, joystick1,"Down").withInterruptBehavior(InterruptionBehavior.kCancelSelf));
elevDown.toggleOnTrue(m_elev.moveElevatorDown(ESpeedDown));//.whileFalse(m_elev.stopElevator());*/


/*final JoystickButton b = new JoystickButton(joystick1, 7);
b.onTrue(m_driveTrain.autoDrive());*/

//new Efficient Arm buttons
final JoystickButton toRest = new JoystickButton(joystick1, 1);
toRest.onTrue(selectRCommand);

final JoystickButton toUp = new JoystickButton(joystick1, 3);
toUp.onTrue(selectUCommand);

final JoystickButton toDown = new JoystickButton(joystick1, 4);
toDown.onTrue(selectDCommand);


        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=BUTTONS
  }

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS
public Joystick getJoystick1() {
        return joystick1;
    }

    public Joystick getJoystick2() {
      return j2;
  } 


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=FUNCTIONS

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
  */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return auto2;
  }

}

