package org.eastsideprep.eps15203;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="DragFoundationBlueBZ", group="15203")

public class DragFoundationBlueBZ extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware15203 robot = new Hardware15203();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "started");    //
        telemetry.update();

        waitForStart();


        sleep(2000);

        robot.allDrive(0.5, 800);
        robot.strafe(-0.5, 1000);
//        robot.turn(-0.5, 1440);
        robot.allDrive(0.5, 490);


        robot.fingerServo1.setPosition(0);
        robot.fingerServo2.setPosition(1);

        sleep(2000);
        robot.allDrive(-0.5, 1650);
//        robot.strafe(0.5,1300);

        robot.fingerServo1.setPosition(1);
        robot.fingerServo2.setPosition(0);
        robot.strafe(0.5,1300);
        robot.allDrive(0.5,500);
        robot.strafe(0.5, 1200);
        robot.strafe(-0.5,1100);



    }
}
