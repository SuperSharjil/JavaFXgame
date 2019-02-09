package sample;

import javafx.animation.*;
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
import javafx.util.Duration;
import java.io.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;


public class GameplayController {


    @FXML Button ShootButton;
    @FXML Button StartGame;
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
    @FXML ImageView Arrow;
    @FXML ImageView Arrow2;


    private double t = 0;
    int power=100,angle=60,playerOneScore=0,playerTwoScore=0,cannonNumber=0,totalCannonNumber=5, turnOfPlayer=1, power2=0,angle2=0,cannonNumber2=0;

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private ServerSocket server;
    private Socket connection;


    @FXML
    public void initialize()  throws IOException{
        Power.setText(Integer.toString(power));
        Angle.setText(Integer.toString(angle));
        PlayerOneScore.setText(Integer.toString(playerOneScore));
        PlayerTwoScore.setText(Integer.toString(playerTwoScore));
        PlayerTurn.setText("Your Turn");

        ArrowAnimation();

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
    public void StartGame (ActionEvent event) throws IOException{
        StartGame.setOpacity(0.0);
        startRunning();

    }


    private void ArrowAnimation() {
        Arrow2.setOpacity(0.0);
        FadeTransition ft = new FadeTransition(Duration.millis(300), Arrow);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();
    }

    private void ArrowAnimation2() {
        Arrow.setOpacity(0.0);
        FadeTransition ft = new FadeTransition(Duration.millis(300), Arrow2);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(6);
        ft.setAutoReverse(true);
        ft.play();
    }

    @FXML
    public void PowerUp()  {
        power++;
        if(power > 150)
            power = 150;
        Power.setText(Integer.toString(power));
    }

    @FXML
    public void PowerDown()  {
        power--;
        if(power<0)
            power = 0;
        Power.setText(Integer.toString(power));
    }

    @FXML
    public void AngleUp()  {
        angle++;
        if(angle==90)
            angle = 89;
        Angle.setText(Integer.toString(angle));
    }

    @FXML
    public void AngleDown()  {
        angle--;
        if(angle<0)
            angle = 0;
        Angle.setText(Integer.toString(angle));
    }

    @FXML
    public void TankMoveLeft(ActionEvent event) throws IOException {
        TankImage.setLayoutX(TankImage.getLayoutX() - 20);
        RedBall.setTranslateX(RedBall.getTranslateX() - 20);
        Arrow.setTranslateX(Arrow.getTranslateX() - 20);
        if(TankImage.getX() < 10) {
            TankImage.setX(10);
            RedBall.setTranslateX(10);
            Arrow.setTranslateX(10);
        }

        try{
            output.writeObject("2");
            output.flush();
            showMessage("going left");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }

    @FXML
    public void TankMoveRight(ActionEvent event) throws IOException {
        TankImage.setLayoutX(TankImage.getLayoutX() + 20);
        RedBall.setTranslateX(RedBall.getTranslateX() + 20);
        Arrow.setTranslateX(Arrow.getTranslateX() + 20);
        if(TankImage.getLayoutX() > (TankImage2.getLayoutX()-100)) {
            TankImage.setLayoutX(TankImage2.getLayoutX()-100);
            RedBall.setTranslateX(TankImage2.getLayoutX()-100);
            Arrow.setTranslateX(TankImage2.getLayoutX()-100);
        }

        try{
            output.writeObject("3");
            output.flush();
            showMessage("going right");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }

    public void TankMoveLeft2()   {
        TankImage2.setLayoutX(TankImage2.getLayoutX() - 20);
        RedBall2.setTranslateX(RedBall2.getTranslateX() - 20);
        Arrow2.setTranslateX(Arrow2.getTranslateX() - 20);
        if(TankImage2.getLayoutX() < (TankImage.getLayoutX()+100)) {
            TankImage2.setLayoutX(TankImage.getLayoutX()+100);
            RedBall2.setTranslateX(TankImage.getLayoutX()+100);
            Arrow2.setTranslateX(TankImage.getLayoutX()+100);
        }
    }

    public void TankMoveRight2() {
        TankImage2.setLayoutX(TankImage2.getLayoutX() + 20);
        RedBall2.setTranslateX(RedBall2.getTranslateX() + 20);
        Arrow2.setTranslateX(Arrow2.getTranslateX() - 20);
        if(TankImage2.getLayoutX() > 950) {
            TankImage2.setLayoutX(950);
            RedBall2.setTranslateX(950);
            Arrow2.setTranslateX(950);
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
    void Shoot() throws IOException{
        if(cannonNumber == 0) RedBallCannon();
        else if(cannonNumber == 1) ThunderShot();
        else if(cannonNumber == 2) MissileFire();
        else if(cannonNumber == 3) AsteroidRain();
        else if(cannonNumber == 4) UmbrellaDefense();






        new Thread(new Runnable() {
            @Override
            public void run() {

                if(turnOfPlayer==2) {
                    PlayerTurn.setText("Enemy's Turn");
                    ArrowAnimation2();
                    turnOfPlayer=1;
                }
                else if(turnOfPlayer==1){
                    PlayerTurn.setText("Your Turn");
                    ArrowAnimation();
                    turnOfPlayer=2;
                }


                String str = "";

        try{
            output.writeObject("1");
            output.flush();
            showMessage("Shoot");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }

        str = Integer.toString(power);
        try{
            output.writeObject(str);
            output.flush();
            showMessage("power sent");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }

        str = Integer.toString(angle);
        try{
            output.writeObject(str);
            output.flush();
            showMessage("angle sent");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }

        str = Integer.toString(cannonNumber);
        try{
            output.writeObject(str);
            output.flush();
            showMessage("cannon number sent");
        }catch(IOException ioException){
            showMessage("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }

            }
        }).start();





        new Thread(new Runnable() {
            @Override
            public void run() {
                // code goes here.
                try{
                    WaitForOtherPlayer();
                }catch(IOException ioException){

                }
            }
        }).start();


    }

    public void WaitForOtherPlayer() throws IOException{
        while(true) {
            try{
                message = (String) input.readObject();

                if(message.equals("1")){
                    showMessage("shoot");
                    try{
                        message = (String) input.readObject();
                        power2 = Integer.parseInt(message);
                        showMessage("power got");
                        System.out.println(power2);
                    }catch(ClassNotFoundException classNotFoundException){
                        showMessage("Unknown data received!");
                    }

                    try{
                        message = (String) input.readObject();
                        angle2 = Integer.parseInt(message);
                        showMessage("angle got");
                        System.out.println(angle2);
                    }catch(ClassNotFoundException classNotFoundException){
                        showMessage("Unknown data received!");
                    }

                    try{
                        message = (String) input.readObject();
                        cannonNumber2 = Integer.parseInt(message);
                        showMessage("cannon number got");
                        System.out.println(cannonNumber2);
                    }catch(ClassNotFoundException classNotFoundException){
                        showMessage("Unknown data received!");
                    }

                    if(cannonNumber2 == 0) RedBall2Cannon();
                    else if(cannonNumber2 == 1) Thunder2Shot();
                    else if(cannonNumber2 == 2) Missile2Fire();
                    else if(cannonNumber2 == 3) Asteroid2Rain();
                    else if(cannonNumber2 == 4) Umbrella2Defense();

                    break;
                }

                else if(message.equals("2")){
                    TankMoveLeft2();
                    showMessage("going left");
                }

                else if(message.equals("3")){
                    TankMoveRight2();
                    showMessage("going right");
                }

            }catch(ClassNotFoundException classNotFoundException){
                showMessage("Unknown data received!");
            }
        }
    }

    public void RedBallCannon() {
        int TankPositionX = (int)TankImage.getLayoutX(),TankPositionY = 0;
        double i=0;
        CannonShot cannonShot = new CannonShot(TankPositionX,TankPositionY);
        cannonShot.Shoot(power,angle);

        Path path = new Path();
        path.getElements().add (new MoveTo (TankPositionX, TankPositionY));

        for(i=(int)TankImage.getLayoutX();i<(int)TankImage.getLayoutX() + 1000; i=i+10)
            path.getElements().add (new CubicCurveTo ( (i),  cannonShot.getCannonBallPositionY(i - (int)TankImage.getLayoutX()),(i+3), cannonShot.getCannonBallPositionY(i+3 - (int)TankImage.getLayoutX()), (i+7),  cannonShot.getCannonBallPositionY(i+7 - (int)TankImage.getLayoutX())));

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

    public void RedBall2Cannon(){
        int TankPositionX = (int)TankImage2.getLayoutX(),TankPositionY = 0;
        double i=0;
        CannonShot cannonShot = new CannonShot(TankPositionX,TankPositionY);
        cannonShot.Shoot(power2,angle2);

        Path path = new Path();
        path.getElements().add (new MoveTo (TankPositionX, TankPositionY));

        for(i=(int)TankImage2.getLayoutX();i<(int)TankImage2.getLayoutX() + 1000; i=i+10)
            path.getElements().add (new CubicCurveTo ( ((int)TankImage2.getLayoutX() - i),  cannonShot.getCannonBallPositionY(i - (int)TankImage2.getLayoutX()),((int)TankImage2.getLayoutX()-i-3), cannonShot.getCannonBallPositionY(i+3 - (int)TankImage2.getLayoutX()), ((int)TankImage2.getLayoutX()-i-7),  cannonShot.getCannonBallPositionY(i+7 - (int)TankImage.getLayoutX())));

        path.getElements().add (new MoveTo (TankPositionX, TankPositionY));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(4000));
        pathTransition.setNode(RedBall2);
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
        Thunder.setX( TankImage.getX() + 300 + power);
        FadeTransition ft = new FadeTransition(Duration.millis(400), Thunder);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();

    }

    public void Thunder2Shot(){
        Thunder.setX( TankImage2.getX() + 340 - power2);
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
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);

        pathTransition.play();
    }

    public void Missile2Fire(){
        Missile2.setOpacity(1.0);

        Path path = new Path();
        path.getElements().add (new MoveTo (TankImage2.getX(), TankImage2.getY()));
        path.getElements().add(new CubicCurveTo ( TankImage2.getX() - 333, TankImage2.getY(), TankImage2.getX() - 667, TankImage2.getY(), TankImage2.getX() - 1000, TankImage2.getY()));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(Missile2);
        pathTransition.setPath(path);
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

    public void Asteroid2Rain(){
        Asteroid2.setOpacity(1.0);

        Asteroid2.setLayoutX(TankImage2.getLayoutX());
        Asteroid2.setLayoutY(TankImage2.getLayoutY());

        Path path = new Path();
        path.getElements().add (new MoveTo (TankImage2.getX(), TankImage2.getY()-300));
        path.getElements().add(new CubicCurveTo ( TankImage.getX() - 100, TankImage.getY()-200, TankImage.getX() - 200, TankImage.getY()-100, TankImage.getX() - 400, TankImage.getY()+100));

        PathTransition pathTransition = new PathTransition();

        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setNode(Asteroid2);
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

    public void Umbrella2Defense(){
        Umbrella2.setX( TankImage2.getX());
        FadeTransition ft = new FadeTransition(Duration.millis(800), Umbrella2);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }


    public void startRunning(){
        try{
            server = new ServerSocket(6789, 100); //6789 is a dummy port for testing, this can be changed. The 100 is the maximum people waiting to connect.
            //while(true){
                try{
                    //Trying to connect and have conversation
                    waitForConnection();
                    setupStreams();
                    //whileChatting();
                }catch(EOFException eofException){
                    showMessage("\n Server ended the connection! ");
                } /*finally{
                    closeConnection(); //Changed the name to something more appropriate
                }
            }*/
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    //wait for connection, then display connection information
    private void waitForConnection() throws IOException{
        showMessage(" Waiting for someone to connect... \n");
        connection = server.accept();
        showMessage(" Now connected to " + connection.getInetAddress().getHostName());
    }

    //get stream to send and receive data
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        showMessage("\n Streams are now setup \n");
    }

    //during the chat conversation
    private void whileChatting() throws IOException{
        String message = " You are now connected! ";
        sendMessage(message);
        ableToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n" + message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("The user has sent an unknown object!");
            }
        }while(!message.equals("CLIENT - END"));
    }

    public void closeConnection(){
        showMessage("\n Closing Connections... \n");
        ableToType(false);
        try{
            output.close(); //Closes the output path to the client
            input.close(); //Closes the input path to the server, from the client.
            connection.close(); //Closes the connection between you can the client
        }catch(IOException ioException){
            ioException.printStackTrace();
        }
    }

    //Send a mesage to the client
    private void sendMessage(String message){
        try{
            output.writeObject("SERVER - " + message);
            output.flush();
            showMessage("\nSERVER -" + message);
        }catch(IOException ioException){
            //chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
        }
    }

    //update chatWindow
    private void showMessage(final String text){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        System.out.println(text);
                        //chatWindow.append(text);
                    }
                }
        );
    }

    private void ableToType(final boolean tof){
        SwingUtilities.invokeLater(
                new Runnable(){
                    public void run(){
                        //userText.setEditable(tof);
                    }
                }
        );
    }

}
