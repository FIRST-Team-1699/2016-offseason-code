/**
 * FIRST Team 1699
 * 
 * This class is the RobotDrive for Swerve robots.
 * 
 * @author thatging3rkid, FIRST Team 1699
 * @author squirlemaster42, FIRST Team 1699
 * 
 * @version v0.2-norobot
 */
package org.usfirst.frc.team1699.robot.swerve;

import java.util.ArrayList;

import org.usfirst.frc.team1699.robot.main.Constants;

/**
 * Drive code, similar to RobotDrive, except for swerve drive robots.
 */
public class SwerveDrive extends edu.wpi.first.wpilibj.RobotDrive {

	private SwerveModule frontLeft;
	private SwerveModule backLeft;
	private SwerveModule frontRight;
	private SwerveModule backRight;

	private double frameLength;
	private double frameWidth;

	private double rotateAngle;

	/**
	 * Constructor a Swerve Drive
	 * 
	 * @param _frontLeft
	 *            reference to the front left module
	 * @param _backLeft
	 *            reference to the back left module
	 * @param _frontRight
	 *            reference to the front right module
	 * @param _backRight
	 *            reference to the back left module
	 * @param _length
	 *            frame length or distance between wheels in the "front" in
	 *            inches
	 * @param _width
	 *            frame width or distance between wheels on a "side" in inches
	 */
	public SwerveDrive(SwerveModule _frontLeft, SwerveModule _backLeft, SwerveModule _frontRight,
			SwerveModule _backRight, double _length, double _width) {
		super(_frontLeft.getDriveController(), _backLeft.getDriveController(), _frontRight.getDriveController(),
				_backRight.getDriveController());

		// Checks to make sure that width and length are valid
		if ((_width == 0) || ((Double) _width == null)) {
			throw new IllegalArgumentException("Width was 0 or null.");
		}
		if ((_length == 0) || ((Double) _length == null)) {
			throw new IllegalArgumentException("Length was 0 or null.");
		}

		this.frontLeft = _frontLeft;
		this.backLeft = _backLeft;
		this.frontRight = _frontRight;
		this.backRight = _backRight;
		this.frameLength = Math.abs(_length);
		this.frameWidth = Math.abs(_width);
		// If you really understand the math, you'll know that length and width
		// can be in whatever unit
		this.rotateAngle = Math.toDegrees(Math.atan(frameLength / frameWidth));

		// Check for null SwerveModules. Would be bad if they were null...
		if (this.frontLeft == null) {
			throw new IllegalArgumentException("frontLeft is null");
		}

		if (this.backLeft == null) {
			throw new IllegalArgumentException("backLeft is null");
		}

		if (this.frontRight == null) {
			throw new IllegalArgumentException("frontRight is null");
		}

		if (this.backRight == null) {
			throw new IllegalArgumentException("backRight is null");
		}

		// Create an ArrayList of SwerveModules
		ArrayList<SwerveModule> modules = new ArrayList<>();
		// Add the modules one by one
		modules.add(this.frontLeft);
		modules.add(this.backLeft);
		modules.add(this.frontRight);
		modules.add(this.backRight);
		// Construct the update service
		SwerveUpdateService service = new SwerveUpdateService(modules);
		// Start the service
		service.start();
	}

	/**
	 * A drive method that aligns the modules and drives in any direction.
	 * 
	 * @param xStick value of the x axis.
	 * @param yStick value of the y axis.
	 */
	public void CrabDrive(double xStick, double yStick) {
		double speed, angle;
		// Check if in deadzone
		if (((xStick + Constants.X_DEADZONE) >= 0) && ((yStick + Constants.Y_DEADZONE) >= 0) && ((xStick - Constants.X_DEADZONE) < 0)
				&& ((yStick + Constants.Y_DEADZONE) < 0)) {
			speed = 0;
			angle = 0;
		} else {
			speed = Math.hypot(xStick, yStick);
			angle = Math.atan(yStick / xStick);
			// angle needs conversion to meet the output from the encoder
			angle = Math.toDegrees(angle);
		}

		Vector output = new Vector(speed, angle);

		frontLeft.setSpeedAngle(output);
		backLeft.setSpeedAngle(output);
		frontRight.setSpeedAngle(output);
		backRight.setSpeedAngle(output);
	}

	/**
	 * Generates the CrabDrive output as an ArrayList of Vectors
	 * 
	 * @param xStick value of the x axis
	 * @param yStick value of the y axis
	 * @return an ArrayList with 4 vectors
	 */
	public ArrayList<Vector> CrabDriveVectors(double xStick, double yStick) {
		double speed, angle;
		// Check if in deadzone
		if (((xStick + Constants.X_DEADZONE) >= 0) && ((yStick + Constants.Y_DEADZONE) >= 0) && ((xStick - Constants.X_DEADZONE) < 0)
				&& ((yStick + Constants.Y_DEADZONE) < 0)) {
			speed = 0;
			angle = 0;
		} else {
			speed = Math.hypot(xStick, yStick);
			angle = Math.atan(yStick / xStick);
			// angle needs conversion to meet the output from the encoder
			angle = Math.toDegrees(angle);
		}

		Vector output = new Vector(speed, angle);

		ArrayList<Vector> returned = new ArrayList<Vector>();
		returned.add(output); // frontLeft
		returned.add(output); // backLeft
		returned.add(output); // frontRight
		returned.add(output); // backRight

		return returned;
	}

	/**
	 * Turns all the modules to a certain degree that then allows the robot on
	 * spin a central point
	 * 
	 * @param amount amount of power to spin the wheels
	 */
	public void RotateDrive(double amount) {
		if (Math.abs(amount) > Constants.ROTATE_DEADZONE) {
			Vector frontLeftVector = new Vector(amount, 1 * rotateAngle);
			Vector backLeftVector = new Vector(amount, -1 * rotateAngle);
			Vector frontRightVector = new Vector(amount, -1 * rotateAngle);
			Vector backRightVector = new Vector(amount, 1 * rotateAngle);

			frontLeft.setSpeedAngle(frontLeftVector);
			backLeft.setSpeedAngle(backLeftVector);
			frontRight.setSpeedAngle(frontRightVector);
			backRight.setSpeedAngle(backRightVector);
		}
	}

	/**
	 * Generates the RotateDrive output as an ArrayList of Vectors
	 * 
	 * @param amount amount of power to spin the wheels
	 * @return an ArrayList with 4 vectors
	 */
	public ArrayList<Vector> RotateDriveVectors(double amount) {
		ArrayList<Vector> returned = new ArrayList<Vector>();

		if (Math.abs(amount) > Constants.ROTATE_DEADZONE) {
			Vector frontLeftVector = new Vector(amount, 1 * rotateAngle);
			Vector backLeftVector = new Vector(amount, -1 * rotateAngle);
			Vector frontRightVector = new Vector(amount, -1 * rotateAngle);
			Vector backRightVector = new Vector(amount, 1 * rotateAngle);

			returned.add(frontLeftVector); // frontLeft
			returned.add(backLeftVector); // backLeft
			returned.add(frontRightVector); // frontRight
			returned.add(backRightVector); // backRight
		} else {
			returned.add(new Vector());
			returned.add(new Vector());
			returned.add(new Vector());
			returned.add(new Vector());
		}
		return returned;
	}

	/**
	 * An efficient drive system that combines Crab and Rotate drives.
	 * 
	 * This method adds the two vectors generated by Crab and Rotate together
	 * and sets it as output.
	 * 
	 * @see http://www.applepirobotics.org/wp-content/uploads/2013/11, Drive
	 *      Trains part 3, slide 8
	 * 
	 * @param xStick x value
	 * @param yStick y value
	 * @param rotateStick  amount of rotate
	 */
	public void UnicornDrive(double xStick, double yStick, double rotateStick) {
		ArrayList<Vector> crabVectors = this.CrabDriveVectors(xStick, yStick);
		ArrayList<Vector> rotateVectors = this.RotateDriveVectors(rotateStick);

		frontLeft.setSpeedAngle(Vector.getResultantVector(crabVectors.get(0), rotateVectors.get(0)));
		backLeft.setSpeedAngle(Vector.getResultantVector(crabVectors.get(1), rotateVectors.get(1)));
		frontRight.setSpeedAngle(Vector.getResultantVector(crabVectors.get(2), rotateVectors.get(2)));
		backRight.setSpeedAngle(Vector.getResultantVector(crabVectors.get(3), rotateVectors.get(3)));
	}

	/**
	 * Tank drive for swerve, who would want this?
	 * 
	 * @param leftValue left stick value
	 * @param rightValue right stick value
	 */
	@Override
	public void tankDrive(double leftValue, double rightValue) {
		this.frontLeft.setAngle(0);
		this.backLeft.setAngle(0);
		this.frontRight.setAngle(0);
		this.backRight.setAngle(0);

		super.tankDrive(leftValue, rightValue);
	}
	
	
}
