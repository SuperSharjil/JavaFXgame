import static java.lang.Math.*;

public class Player {
    int Power, Angle;
    double playerPositionX, playerPositionY , gravity = 5;

    public Player( double playerPositionX, double playerPositionY, int power, int angle) {
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        Power = power;
        Angle = angle;
    }

    double cannonBallPositionX = playerPositionX;
    double cannonBallPositionY = playerPositionY;

    public void Shoot(int power, int angle) {
        Power = power;
        Angle = angle;
    }

    public double getCannonBallPositionX(double time) {
        cannonBallPositionX = playerPositionX + Power * Math.cos(Math.toRadians(Angle)) * time;
        return cannonBallPositionX;
    }

    public double getCannonBallPositionY(double time) {
        cannonBallPositionY = playerPositionY + Power * Math.sin(Math.toRadians(Angle)) * time - 0.5 * gravity * time * time;
        return cannonBallPositionY;
    }


}
