package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

import java.util.Timer;
import java.util.TimerTask;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private Timer timer;

	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
		    if(referenceSpeed+step > 0) {
                referenceSpeed += step;
            } else {
		        referenceSpeed = 0;
            }
		}

		enforceSpeedLimit();
	}

	public void setReferenceSpeed(int referenceSpeed) {
		if (referenceSpeed > speedLimit)
			this.referenceSpeed = speedLimit;
		else if (referenceSpeed < 0)
			this.referenceSpeed = 0;
		else
			this.referenceSpeed = referenceSpeed;
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

    @Override
    public int getJoystickSpeed() {
        return this.step; //I know;
    }

    @Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
			int a = 500;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;
		startTimer();
	}

	public void startTimer(){
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setReferenceSpeed(referenceSpeed + step);
			}
		}, 0, 1000);
	}



}
