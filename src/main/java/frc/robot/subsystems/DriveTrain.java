// RobotBuilder Version: 5.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

// ROBOTBUILDER TYPE: Subsystem.

package frc.robot.subsystems;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class DriveTrain extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    private CANSparkMax mc1, mc2, mc3, mc4;
    private MotorControllerGroup lc, rc;
    private DifferentialDrive d;
    private BuiltInAccelerometer a; //REMEMBER THE ACCELEROMETER
    private ADXRS450_Gyro g;

    

    public DriveTrain() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS

        //arm motor controller
        mc1 = new CANSparkMax(1, MotorType.kBrushless);
        mc2 = new CANSparkMax(2, MotorType.kBrushless);
        mc3 = new CANSparkMax(3, MotorType.kBrushed);
        mc4 = new CANSparkMax(4, MotorType.kBrushed);

        mc1.setInverted(true);
        mc2.setInverted(true);
        mc3.setInverted(true);
        mc4.setInverted(true);

        lc = new MotorControllerGroup(mc3, mc4);
        rc = new MotorControllerGroup(mc1, mc2);

        d = new DifferentialDrive(lc, rc);
        d.setSafetyEnabled(true);
        d.setExpiration(.1);
        d.setMaxOutput(1);
        d.setDeadband(.07);

        a = new BuiltInAccelerometer();

        g = new ADXRS450_Gyro();
    }

    @Override
    public void periodic() {
        d.tankDrive(0, 0);
        // This method will be called once per scheduler run
        //SmartDashboard.putNumber("EncoderPosition", 0);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public DifferentialDrive getDifferentialDrive(){
        return d;
    }

    public CommandBase autoDrive(){
        return this.runOnce(()->d.tankDrive(-.8, -.8));
    }

    public CommandBase trackTarget(int pipelineNumber){
        
    }

    //other stuff is mostly encoders, gyro, acc
}

