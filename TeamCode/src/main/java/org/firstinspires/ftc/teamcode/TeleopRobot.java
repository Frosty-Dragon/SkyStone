package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TeleopRobot implements Robot  {

    MotorPowers driveMotors;

    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    DriveTrain driveTrain;
    Servo clawServo;

    // this may need to be changed, depending on what we want the default for this servo to be
    double clawInit = 0.0;
    double clawGrab = 0.5;

    boolean grabbing;

    public TeleopRobot(){


    }

    public TeleopRobot(HardwareMap hardwareMap){
        this.hardwareMap = hardwareMap;
    }

    public void init(){
        frontLeftMotor =hardwareMap.dcMotor.get("FrontLeftMotor");
        frontRightMotor =hardwareMap.dcMotor.get("FrontRightMotor");
        backLeftMotor =hardwareMap.dcMotor.get("BackLeftMotor");
        backRightMotor =hardwareMap.dcMotor.get("BackRightMotor");
        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);


        pivotMotor = hardwareMap.dcMotor.get("PivotMotor");
        armMotor = hardwareMap.dcMotor.get("ArmMotor");

        clawServo = hardwareMap.servo.get("ClawServo");

        clawServo.setPosition(clawInit);
        grabbing = false;

    }

    public void setMotors(MotorPowers robotMotors){

        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = robotMotors.fR;
        driveMotors.bR = robotMotors.bR;



    }



    public void moveMotors(){
        driveMotors.scale();
        driveTrain.runMotors(driveMotors);


    }


    public MotorPowers getDriveMotors() {
        return driveMotors;
    }

    public void moveArm(double m){
        armMotor.setPower(m);


    }

    public void grab(){

        clawServo.setPosition(clawGrab);
        grabbing = true;

    }

    public void ungrab()
    {

        clawServo.setPosition(clawInit);
        grabbing = false;
    }


}
