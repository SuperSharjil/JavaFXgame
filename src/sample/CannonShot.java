package sample;

public class CannonShot {

    private double cannonBallPositionX ,cannonBallPositionY;
    private int Power, Angle, gravity = 10;

    public CannonShot(double cannonBallPositionX, double cannonBallPositionY) {
        this.cannonBallPositionX = cannonBallPositionX;
        this.cannonBallPositionY = cannonBallPositionY;
    }

    public void Shoot(int power, int angle) {
        Power = power;
        Angle = angle;
    }

    public double getCannonBallPositionX(double time) {
        cannonBallPositionX = cannonBallPositionX + Power * Math.cos(Math.toRadians(Angle)) * time;
        return cannonBallPositionX;
    }

    public double getCannonBallPositionY(double x) {
        cannonBallPositionY =  - x * Math.tan(Math.toRadians(Angle))  + 0.5 * gravity * x * x / (Power * Math.cos(Math.toRadians(Angle)) * Power * Math.cos(Math.toRadians(Angle)));
        return cannonBallPositionY;
    }
}
