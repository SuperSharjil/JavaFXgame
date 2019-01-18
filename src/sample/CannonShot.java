package sample;

public class CannonShot {

    private double cannonBallPositionX ,cannonBallPositionY;
    private int Power, Angle, gravity = 5;

    public CannonShot(double cannonBallPositionX, double cannonBallPositionY) {
        this.cannonBallPositionX = cannonBallPositionX;
        this.cannonBallPositionY = cannonBallPositionY;
    }



    public void Shoot(int power, int angle) {
        Power = power;
        Angle = angle;
    }

    public double getCannonBallPositionX(double time) {
        cannonBallPositionX = Power * Math.cos(Math.toRadians(Angle)) * time;
        return cannonBallPositionX;
    }

    public double getCannonBallPositionY(double time) {
        cannonBallPositionY = Power * Math.sin(Math.toRadians(Angle)) * time - 0.5 * gravity * time * time;
        return cannonBallPositionY;
    }
}
