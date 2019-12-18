package org.firstinspires.ftc.teamcode.robots;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.motors.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.motors.MotorDistances;

public class SimpleAutoRobot implements Robot {

    MotorPowers driveMotors;

    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Telemetry telemetry;
    LinearOpMode lop;
    public Arm arm;
    public Claw claw;
    DcMotor pivotMotor;
    DcMotor linkageMotor;
    public DriveTrain driveTrain;
    Servo frontServo;
    double encoderSpinDegrees;
    double encoderDist;

    Boolean strafeMode;
    Boolean turnMode;
    Boolean forwardMode;
    Boolean nothingMode;
    Boolean grabbing;
    Long lastTime;
    Long timer;

    // accuracy is how close an encoder value needs to be to what it's expected to be
    double accuracy;


    public SimpleAutoRobot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode lop) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.lop = lop;

    }

    @Override
    public void init() {
        strafeMode = false;
        forwardMode = false;
        turnMode = false;
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");


        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, true);
        // running with encoders, you should probably stop and reset their current position to 0
        driveTrain.runWithoutEncoders();

        //driveTrain.stopAndResetEncoders();
        driveMotors = new MotorPowers(0, 0, 0, 0);
        arm = new Arm(hardwareMap, telemetry);
        arm.init();
        linkageMotor = arm.getLinkageMotor();
        pivotMotor = arm.getPivotMotor();

        claw = new Claw(hardwareMap, telemetry);
        claw.init();
        frontServo = claw.getFrontServo();


        grabbing = false;

    }


    public void update() {


    }

    public void armUpdate(){
        arm.update();
    }

    public String getChassisWheels() {
        return driveTrain.toString();
    }

    public Claw getClaw() {
        return claw;
    }

    public Arm getArm(){
        return arm;
    }

    public Servo getFrontServo(){
        return frontServo;
    }

    public DcMotor getPivotMotor(){
        return pivotMotor;
    }

    public DcMotor getLinkageMotor(){
        return linkageMotor;
    }

    public void stopAndResetEncoders() {
        driveTrain.stopAndResetEncoders();

    }

    public void runWithoutEncoders() {
        driveTrain.runWithoutEncoders();
    }


    // if negative, spins to the left, positive to the right
    public void spinDegrees(double degrees, double speed) {


    }

    public DriveTrain getDriveTrain(){
        return driveTrain;
    }



    /**
     * Set the Robot to strafe
     * @param left is the direction of the robot, left if true, right if false
     * @param speed is the speed of the motors
     */
    public void strafe(Boolean left, Double speed) {

        MotorPowers strafing = (left) ? new MotorPowers(-speed, speed, speed, -speed) : new MotorPowers(speed, -speed, -speed, speed);
        strafing.scale();
        setMotors(strafing);
        moveMotors();
    }

    /**
     * Set the Robot to strafe for a certain period of time
     * @param left is the direction of the robot, left if true, right if false
     * @param speed is the speed of the motors
     * @param time is the time to be strafed in milliseconds
     */
    public void strafeTime(Boolean left, Double speed, Long time) {
        strafeMode = true;
        forwardMode = false;
        timer = time;
        lastTime = System.currentTimeMillis();

        MotorPowers strafing = (left) ? new MotorPowers(-speed, speed, speed, -speed) : new MotorPowers(speed, -speed, -speed, speed);
        strafing.scale();
        setMotors(strafing);
        moveMotors();
        while (System.currentTimeMillis() - lastTime < timer) {
            moveMotors();
            if (lop.isStopRequested()) {
                break;
            }
        }
        driveMotors.setAll(0);
        moveMotors();
    }


    /**
     * Set the Robot to drive forward
     * @param forwardDirection is the direction of the robot, forward if true, backward if false
     * @param speed is the speed of the motors
     */
    public void forward(Boolean forwardDirection, Double speed) {
        MotorPowers forwarding = (forwardDirection) ? new MotorPowers(speed, speed, speed, speed) : new MotorPowers(-speed, -speed, -speed, -speed);
        forwarding.scale();
        setMotors(forwarding);
        driveTrain.runMotors(driveMotors);
    }


    public void grab(){
        claw.close();
        grabbing = true;
    }

    public void ungrab(){
        grabbing = false;
        claw.open();
    }

    /**
     * Set the Robot to drive forward for a certain period of time
     * @param forwardDirection is the direction of the robot, forward if true, backward if false
     * @param speed is the speed of the motors
     * @param time is the time to drive forward in milliseconds
     */
    public void forwardTime(Boolean forwardDirection, Double speed, Long time) {
        forwardMode = true;
        strafeMode = false;
        timer = time;
        lastTime = System.currentTimeMillis();
        MotorPowers forwarding = (forwardDirection) ? new MotorPowers(speed, speed, speed, speed) : new MotorPowers(-speed, -speed, -speed, -speed);
        forwarding.scale();
        setMotors(forwarding);
        moveMotors();
        while (System.currentTimeMillis() - lastTime < timer) {
            moveMotors();
            if (lop.isStopRequested()) {
                break;
            }
        }
        driveMotors.setAll(0);
        moveMotors();
    }

    public void forwardEncoder(Boolean forwardDirection, Double speed, Double encoderVal, Double checkPoint) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, checkPoint, 0, false, telemetry, lop);


    }

    public void forwardEncoderSlowDown(Boolean forwardDirection, Double speed, Double encoderVal, Double checkPoint, Double slowPoint) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, checkPoint, slowPoint, true, telemetry, lop);


    }

    public void spinEncoderVal(MotorDistances mds, MotorPowers mps, double check, double slowPoint, boolean slowApproach) {
        driveTrain.runWithoutEncoders();

        driveTrain.toEncoderVal(mds, mps, check, slowPoint, slowApproach, telemetry, lop);
        telemetry.addData("status", "complete");
    }

    @Override
    public void setMotors(MotorPowers robotMotors) {
        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = -robotMotors.fR;
        driveMotors.bR = -robotMotors.bR;

    }

    public void setAllMotors(Double power){
        driveMotors.fL = power;
        driveMotors.bL = power;
        driveMotors.fR = power;
        driveMotors.bR = power;
    }

    @Override
    public void moveMotors() {
        driveTrain.runMotors(driveMotors);
    }

    @Override
    public MotorPowers getDriveMotors() {
        return null;
    }
}