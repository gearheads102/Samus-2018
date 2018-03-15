package org.usfirst.frc.team102.robot.subsystems;



import org.usfirst.frc.team102.robot.Robot;
import org.usfirst.frc.team102.robot.RobotMap;
import org.usfirst.frc.team102.robot.commands.DriveWithXBox;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.MecanumDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Creating MecanumDrive Object
	private MecanumDrive drive;

	// Creating Motor Objects
	WPI_TalonSRX frontLeftMotor;
	WPI_TalonSRX backLeftMotor;
	WPI_TalonSRX frontRightMotor;
	WPI_TalonSRX backRightMotor;

	// Creating Joystick Axis Variables
	private double leftJoyX;
	private double leftJoyY;
	private double rightJoyX;
	@SuppressWarnings("unused")
	private double rightJoyY;
	
	private double distanceTraveled;
	private double direction;
	
	//variables needed for distance counter
	private static double metersPerSec = 21.19;
	private double time;
	private double distanceToGo;
	
	
	
	//driver station info
	DriverStation driverStation;
	
	
	

	// DriveTrain constructor
	 public DriveTrain() {
		 

		// Initializing motors Motors
		frontLeftMotor = new WPI_TalonSRX(RobotMap.m1);
		backLeftMotor = new WPI_TalonSRX(RobotMap.m2);
		frontRightMotor = new WPI_TalonSRX(RobotMap.m3);
		backRightMotor = new WPI_TalonSRX(RobotMap.m4);
		driverStation = DriverStation.getInstance();

		// drive = new MecanumDrive(m1 = new WPI_TalonSRX(RobotMap.m1), m2 = new
		// WPI_TalonSRX(RobotMap.m2),
		// m3 = new WPI_TalonSRX(RobotMap.m3), m4 = new
		// WPI_TalonSRX(RobotMap.m4));

		//m1.setInverted(true);
		//m2.setInverted(true);

		// creating an instance of MecanumDrive
		drive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);

		// turning Safety ON
		drive.setSafetyEnabled(true);

	}

	// Setting the default command of drivetrain to driveWithXBox
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new DriveWithXBox());
		

	}

	// driveWithXBox method that takes joystick axises and puts it into
	// driveCartesian
	// to move the motors for mecanum drive
	public void driveWithXBox(Joystick xBox) {

		// Pulling joystick values
		leftJoyX = xBox.getRawAxis(RobotMap.xBoxLeftXAxis);
		leftJoyY = xBox.getRawAxis(RobotMap.xBoxLeftYAxis);
		rightJoyY = xBox.getRawAxis(RobotMap.xBoxRightYAxis);
		rightJoyX = xBox.getRawAxis(RobotMap.xBoxRightXAxis);

		if (leftJoyX <= 0.1 && leftJoyX >= -0.1) {

			leftJoyX = 0.0;

		}

		if (leftJoyY <= 0.1 && leftJoyY >= -0.1) {

			leftJoyY = 0.0;

		}

		if (rightJoyX <= 0.1 && rightJoyX >= -0.1) {

			rightJoyX = 0.0;

		}

		// putting it into method to move motors
		drive.driveCartesian(leftJoyX, -leftJoyY, rightJoyX);

	}
	
	//BEWARE: AUTONOMOUS BEYOND THIS POINT

	
			
			
			
			
			public double distanceCounter(double percentSpeed, double time){
				double distance;
				
				
				distance = percentSpeed*metersPerSec*time;
				
				return distance;
			}
			
			public DriverStation getDriverStation(){
				return driverStation;
			}
			
			//turn 90 degrees
			public void turn(double turnSpeed){
	
				drive.driveCartesian(0.0,0.0, turnSpeed);
				

			}
			
			// Method to drive robot straight
			public void driveStraight(double speed, double distanceToGo, double time) {

				// moves motors forward
				this.distanceToGo = distanceToGo;
				drive.driveCartesian(speed, 0.0, 0.0);
				
				distanceTraveled = distanceCounter(speed, time);
				System.out.println("Distance traveled: " + distanceTraveled);
    			System.out.println("Distance to go" + distanceToGo);
	    		if (distanceTraveled >= distanceToGo) {
	    			System.out.println("Recognizes distance is met");
	    			drive.driveCartesian(0, 0, 0);
	    		
	    			
	    			
	    		}
			}
			
			public double getDistanceToGo(){
				return distanceToGo;
			}
			public double getDistanceTraveled(){
				return distanceTraveled;
			}
			
			//driveSideways in auto
			public void driveSideways(double percentSpeed ,boolean isLeft, double distanceToGo){
				distanceTraveled = Robot.robotDriveTrain.distanceCounter(percentSpeed, time);
		    	if(isLeft){
		    		direction = 0.5;	
		    	}
		    	else{
		    		direction= -0.5;
		    	}
		    	while(distanceTraveled < distanceToGo){
		    		
		    		drive.driveCartesian(0, direction, 0);
		    	}
			}
			
			//SameSwitchSideScore
			//SS= same side
		
				


}
