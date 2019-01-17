package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import java.awt.*;

public class Controller {
    @FXML private Button PlayButton;
    @FXML private Button QuitButton;
    @FXML private Button SettingsButton;

    /*
    public void ContinueButtonAction(ActionEvent event){
        primaryStage.setScene(scene2);
    }*/
    public void PlayButtonClicked (ActionEvent event){
        System.out.println("PLAY");
    }

    public void QuitButtonClicked(ActionEvent event){
        System.out.println("PLAY");
    }

    public void SettingsButtonClicked(ActionEvent event){
        System.out.println("PLAY");
    }
}
