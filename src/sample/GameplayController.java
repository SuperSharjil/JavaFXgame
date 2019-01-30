package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.awt.*;
import javafx.scene.shape.*;
import javafx.animation.Animation;
import javafx.util.Duration;


public class GameplayController {


    @FXML Button ShootButton;
    @FXML ImageView TankImage;
    @FXML ImageView TankImage2;
    @FXML Circle RedBall;//0
    @FXML Circle RedBall2;//0
    @FXML ImageView Thunder;//1
    @FXML ImageView Missile;//2
    @FXML ImageView Missile2;//2
    @FXML ImageView Asteroid;//3
    @FXML ImageView Asteroid2;//3
    @FXML ImageView Umbrella;//4
    @FXML ImageView Umbrella2;//4
    @FXML Label Power;
    @FXML Label Angle;
    @FXML Label PlayerOneScore;
    @FXML Label PlayerTwoScore;
    @FXML Label PlayerTurn;
    @FXML Label CannonName;
    @FXML ImageView Explosion;
    @FXML ImageView Explosion2;


    private double t = 0;
    int power=100,angle=60,playerOneScore=0,playerTwoScore=0,cannonNumber=0,totalCannonNumber=5;


    @FXML
    public void initialize()  {
        Power.setText(Integer.toString(power));
        Angle.setText(Integer.toString(angle));
        PlayerOneScore.setText(Integer.toString(playerOneScore));
        PlayerTwoScore.setText(Integer.toString(playerTwoScore));
        PlayerTurn.setText("Your Turn");
        Thunder.setX(0);
        Thunder.setOpacity(0.0);
        Missile.setOpacity(0.0);
        Missile2.setOpacity(0.0);
        Umbrella.setOpacity(0.0);
        Umbrella2.setOpacity(0.0);
        Asteroid.setOpacity(0.0);
        Asteroid2.setOpacity(0.0);
        CannonName.setText("Red Ball");

        Explosion.setOpacity(0.0);
        Explosion2.setOpacity(0.0);
    }

    @FXML
    public void PowerUp()  {
        power++;
        Power.setText(Integer.toString(power));
    }

    @FXML
    public void PowerDown()  {
        power--;
        Power.setText(Integer.toString(power));
    }

    @FXML
    public void AngleUp()  {
        angle++;
        Angle.setText(Integer.toString(angle));
    }

    @FXML
    public void AngleDown()  {
        angle--;
        Angle.setText(Integer.toString(angle));
    }

    @FXML
    public void TankMoveLeft(ActionEvent event) throws IOException {
        TankImage.setX(TankImage.getX() - 20);
        RedBall.setTranslateX(RedBall.getTranslateX() - 20);
        if(TankImage.getX() < 10) {
            TankImage.setX(10);
            RedBall.setTranslateX(10);
        }
    }

    @FXML
    public void TankMoveRight(ActionEvent event) throws IOException {
        TankImage.setX(TankImage.getX() + 20);
        RedBall.setTranslateX(RedBall.getTranslateX() + 20);
        if(TankImage.getX() > (TankImage2.getLayoutX()-100)) {
            TankImage.setX(TankImage2.getLayoutX()-100);
            RedBall.setTranslateX(TankImage2.getLayoutX()-100);
        }
    }

    @FXML
    public void CannonUp()  {
        cannonNumber++;
        if(cannonNumber == totalCannonNumber)
            cannonNumber = 0;
        SetCannonName(cannonNumber);
    }

    @FXML
    public void CannonDown()  {
        cannonNumber--;
        if(cannonNumber<0)
            cannonNumber = totalCannonNumber - 1;
        SetCannonName(cannonNumber);
    }

    public void SetCannonName(int cannonNumber){

        if(cannonNumber == 0)
            CannonName.setText("Red Ball");
        if(cannonNumber == 1)
            CannonName.setText("Thunder");
        if(cannonNumber == 2)
            CannonName.setText("Missile");
        if(cannonNumber == 3)
            CannonName.setText("Asteroid");
        if(cannonNumber == 4)
            CannonName.setText("Umbrella");
    }

    @FXML
    void Shoot(){
        if(cannonNumber == 0) RedBallCannon();
        if(cannonNumber == 1) ThunderShot();
        if(cannonNumber == 2) MissileFire();
        if(cannonNumber == 3) AsteroidRain();
        if(cannonNumber == 4) UmbrellaDefense();
    }


    public void RedBallCannon(){
        int TankPositionX = (int)TankImage.getX(),TankPositionY = 0;
        double i=0;
        CannonShot cannonShot = new CannonShot(TankPositionX,TankPositionY);
        cannonShot.Shoot(power,angle);

        Path path = new Path();
        path.getElements().add (new MoveTo (TankPositionX, TankPositionY));

        for(i=(int)TankImage.getX();i<(int)TankImage.getX() + 1000; i=i+10)
            path.getElements().add (new CubicCurveTo ( (i),  cannonShot.getCannonBallPositionY(i - (int)TankImage.getX()),(i+3), cannonShot.getCannonBallPositionY(i+3 - (int)TankImage.getX()), (i+7),  cannonShot.getCannonBallPositionY(i+7 - (int)TankImage.getX())));

        path.getElements().add (new MoveTo (TankPositionX, TankPositionY));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setNode(RedBall);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.play();

        /*
        while(RedBall.getTranslateY() < 500) {
            if(RedBall.getTranslateX()>TankImage2.getLayoutX() && RedBall.getTranslateX()<(TankImage2.getLayoutX()+50) && RedBall.getTranslateY()==TankImage2.getLayoutY()){

                Explosion.setX( TankImage2.getX());
                FadeTransition ft = new FadeTransition(Duration.millis(200), Explosion);
                ft.setFromValue(0.0);
                ft.setToValue(1.0);
                ft.setCycleCount(2);
                ft.setAutoReverse(true);
                ft.play();
            }
        }
        */
    }

    public void ThunderShot(){
        Thunder.setX( TankImage.getX() + 300);
        FadeTransition ft = new FadeTransition(Duration.millis(400), Thunder);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();

    }

    public void MissileFire(){
        Missile.setOpacity(1.0);

        Path path = new Path();
        path.getElements().add (new MoveTo (TankImage.getX(), TankImage.getY()));
        path.getElements().add(new CubicCurveTo ( TankImage.getX() + 333, TankImage.getY(), TankImage.getX() + 667, TankImage.getY(), TankImage.getX() + 1000, TankImage.getY()));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(Missile);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.play();
    }

    public void AsteroidRain(){
        Asteroid.setOpacity(1.0);

        Asteroid.setLayoutX(TankImage.getLayoutX());
        Asteroid.setLayoutY(TankImage.getLayoutY());

        Path path = new Path();
        path.getElements().add (new MoveTo (TankImage.getX(), TankImage.getY()-300));
        path.getElements().add(new CubicCurveTo ( TankImage.getX() + 100, TankImage.getY()-200, TankImage.getX() + 200, TankImage.getY()-100, TankImage.getX() + 400, TankImage.getY()+100));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setNode(Asteroid);
        pathTransition.setPath(path);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.play();
    }

    public void UmbrellaDefense(){
        Umbrella.setX( TankImage.getX());
        FadeTransition ft = new FadeTransition(Duration.millis(800), Umbrella);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


}
