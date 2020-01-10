package org.firstinspires.ftc.teamcode.auto.simple;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;

@Autonomous(name = "StrafeLeft", group = "autos")

public class StrafeLeft extends LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        long time = 1000;
        robot.strafeTime(true, 1.0, time);

        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.addData("Encoders", robot.getDriveTrain().encoders());
        telemetry.update();

    }


}
