package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.awt.*;

public class GameplayController {

    @FXML Rectangle rectangle;
    @FXML Button UpButton;
    @FXML Button LeftButton;
    @FXML Button DownButton;
    @FXML Button RightButton;



    private double t = 0;

    /*
    Pane root = new Pane();

    private Parent createContent() throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Gameplay.fxml"));

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

        timer.start();


        return root;
    }


    private void update() {

    }
*/
    public void UpButton(ActionEvent event){

        moveUp();
    }
    public void LeftButton(ActionEvent event){

        moveLeft();
    }
    public void DownButton(ActionEvent event){

        moveDown();
    }
    public void RightButton(ActionEvent event){

        moveRight();
    }


    void moveLeft() {
        rectangle.setTranslateX(rectangle.getTranslateX() - 5);
    }

    void moveRight() {
        rectangle.setTranslateX(rectangle.getTranslateX() + 5);
    }

    void moveUp() {
        rectangle.setTranslateY(rectangle.getTranslateY() - 5);
    }

    void moveDown() {
        rectangle.setTranslateY(rectangle.getTranslateY() + 5);
    }



}
