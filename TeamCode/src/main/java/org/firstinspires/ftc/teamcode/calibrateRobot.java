package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;
import org.firstinspires.ftc.teamcode.robots.motors.MotorDistances;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

@Autonomous(name = "ca1ibrateRobot", group = "autos")

public class calibrateRobot extends LinearOpMode {

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    MotorPowers motorPowers;
    MotorDistances motorDistances;
    Servo clawServo;
    SimpleAutoRobot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, this.telemetry, this);
        robot.init();
        motorPowers = new MotorPowers(0,0,0,0);
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        motorDistances = new MotorDistances(560);
        motorPowers = new MotorPowers(0.2);
        robot.driveTrain.backRightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.driveTrain.frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        robot.runWithoutEncoders();
        robot.spinEncoderVal(motorDistances, motorPowers, 10,200, true);
    }


}
