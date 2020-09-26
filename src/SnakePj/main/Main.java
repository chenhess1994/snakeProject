package SnakePj.main;

import SnakePj.controller.Corner;
import SnakePj.controller.Mytimer;
import SnakePj.controller.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application implements ManagerGame {

    Button startButton;
    Button exitButton;
    Snake s = new Snake();

    @Override
    public void startGame(Stage primaryStage) {
        try {
            //create a circle of food.
            s.newFood();

            //buttons
            startButton = new Button("Restart");
            startButton.setTextFill(Color.WHITE);
            startButton.setTranslateX(((s.getWidth() * s.getCornerSize()) / 2));
            startButton.setStyle("-fx-background-color:#003300;");
            startButton.setMinWidth((s.getWidth() * s.getCornerSize()) / 2);
            exitButton = new Button("Exit");
            exitButton.setTextFill(Color.WHITE);
            exitButton.setTranslateX(0);
            exitButton.setStyle("-fx-background-color:#003300;");
            exitButton.setMinWidth((s.getWidth() * s.getCornerSize()) / 2 - 3);

            //buttons functionality
            startButton.setOnMouseClicked(event -> {
            restart(primaryStage);
            });
            exitButton.setOnMouseClicked(event -> {
            exit(primaryStage);
            });

            //create canvas with vbox.
            VBox root = new VBox();
            GridPane gridPane = new GridPane();
            Canvas c = new Canvas(s.getWidth() * s.getCornerSize(), s.getHeight() * s.getCornerSize());
            GraphicsContext gc = c.getGraphicsContext2D();

            //children
            root.getChildren().add(gridPane);
            gridPane.getChildren().addAll(exitButton, startButton);
            root.getChildren().add(c);

            //here we do the animation of the snake,and the gc.
            AnimationTimer timer = new Mytimer(s, gc);
            timer.start();

            //create scene and put it on the canvas.
            Scene scene = new Scene(root, s.getWidth() * s.getCornerSize(), s.getHeight() * s.getCornerSize());

            // control the snake.
            s.controlTheSnake(scene);

            // add start snake parts
            s.getSnake().add(new Corner(s.getWidth() / 2, s.getHeight() / 2));
            s.getSnake().add(new Corner(s.getWidth() / 2, s.getHeight() / 2));
            s.getSnake().add(new Corner(s.getWidth() / 2, s.getHeight() / 2));

            primaryStage.setScene(scene);
            primaryStage.setTitle("SNAKE GAME");
            primaryStage.setResizable(false);
            primaryStage.show();
            //
            Image image=new Image("/SnakePj/image/snake.png");
            primaryStage.getIcons().add(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restart(Stage primaryStage) {
            primaryStage.close();
            Platform.runLater(() -> new Main().start(new Stage()));
    }

    @Override
    public void exit(Stage primaryStage) {
            primaryStage.close();
    }


    public void start(Stage primaryStage) {
        startGame(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

