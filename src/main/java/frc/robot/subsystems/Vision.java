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


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Vision extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    public NetworkTableEntry tv, tx, ty, ta, tid;
    public double ifTargets, targetX, targetY, targetArea, aprilTagID;
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    /**
    *
    */
    public Vision() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    
        tid = table.getEntry("tid");
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        double ifTargets = tv.getDouble(0);
        double targetX = tx.getDouble(0);
        double targetY = ty.getDouble(0);
        double targetArea = ta.getDouble(0);
        double aprilTagID = tid.getDouble(0);

        SmartDashboard.putNumber("ifTargets", ifTargets);
        SmartDashboard.putNumber("targetX", targetX);
        SmartDashboard.putNumber("targetY", targetY);
        SmartDashboard.putNumber("TargetArea (%)", targetArea);
        SmartDashboard.putNumber("AprilTagID", aprilTagID);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public boolean checkForTargets(){
        if((int)ifTargets == 1){
            return true;
        }else{
            return false;
        }
    }

    public void setPipeline(int pipeline){
        table.getEntry("pipeline").setNumber(pipeline);
    }

    public long getPipeline(){
        return table.getEntry("pipeline").getInteger(0);
    }

    public double[] getTargetValues(){
        double[] targetValues = new double[]{targetX,targetY,targetArea}; 
        return targetValues;
    }
}
