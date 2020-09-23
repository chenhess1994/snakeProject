package SnakePj.main;

import SnakePj.controller.Corner;
import SnakePj.controller.Snake;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {


    Snake s=new Snake();

    public void start(Stage primaryStage) {
        try {
            //create a circle of food.
            s.newFood();
            //create canvas with vbox.
            VBox root = new VBox();
            Canvas c = new Canvas(s.getWidth() * s.getCornerSize(), s.getHeight() * s.getCornerSize());
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);
            //k
            //here we do the animation of the snake.
            AnimationTimer timer = new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                    lastTick = now;
                    s.tick(gc);
                    return;
                }

                 if (now - lastTick > 1000000000 / s.getSpeed()) {
                    lastTick = now;
                    s.tick(gc);
                 }
                }
            };


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
            primaryStage.show();
            
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

