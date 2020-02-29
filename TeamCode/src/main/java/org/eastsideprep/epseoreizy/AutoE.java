package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;


@Autonomous(name = "Everest Auto 1 - Functions Test", group = "15203")

public class AutoE extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        robot.chassis.performAll(new ChassisInstruction[]{
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 1000),
                new ChassisInstruction(ChassisDirection.FORWARD, 0.25, 1000),
                new ChassisInstruction(ChassisDirection.TURN_LEFT, 0.25, 1000),
                new ChassisInstruction(ChassisDirection.TURN_RIGHT, 0.25, 1000),
                new ChassisInstruction(ChassisDirection.STRAFE_LEFT, 0.25, 1000),
                new ChassisInstruction(ChassisDirection.STRAFE_RIGHT, 0.25, 1000),
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000)
    });
        telemetry.addData("Status", "started");
        telemetry.update();


    }
}

