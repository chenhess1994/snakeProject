package SnakePj.controller;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Snake {

    // variable
    private int speed = 5;
    private int foodColor = 0;
    private int width = 20;
    private int height = 20;
    private int foodX = 0;
    private int foodY = 0;
    private int cornerSize = 25;
    private List<Corner> snake = new ArrayList<>();
    private Dir direction = Dir.left;
    private boolean gameOver = false;
    private Random rand = new Random();
    private Corner corner = new Corner(20, 20);

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFoodX() {
        return foodX;
    }

    public void setFoodX(int foodX) {
        this.foodX = foodX;
    }

    public int getFoodY() {
        return foodY;
    }

    public void setFoodY(int foodY) {
        this.foodY = foodY;
    }

    public int getCornerSize() {
        return cornerSize;
    }

    public void setCornerSize(int cornerSize) {
        this.cornerSize = cornerSize;
    }

    public List<Corner> getSnake() {
        return snake;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // tick
    public void tick(GraphicsContext gc) {

        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("", 50));
            gc.fillText("GAME OVER", 100, 250);
            return;
        }

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        switch (direction) {
            case up:
                snake.get(0).setY(snake.get(0).getY() - 1);
                if (snake.get(0).getY() < 0) {
                    gameOver = true;
                }
                break;
            case down:
                snake.get(0).setY(snake.get(0).getY() + 1);
                if (snake.get(0).getY() > height) {
                    gameOver = true;
                }
                break;
            case left:
                snake.get(0).setX(snake.get(0).getX() - 1);
                if (snake.get(0).getX() < 0) {
                    gameOver = true;
                }
                break;
            case right:
                snake.get(0).setX(snake.get(0).getX() + 1);
                if (snake.get(0).getX() > width) {
                    gameOver = true;
                }
                break;
        }

        // eat food
        if (foodX == snake.get(0).getX() && foodY == snake.get(0).getY()) {
            snake.add(new Corner(-1, -1));
            newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()) {
                gameOver = true;
            }
        }

        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width * cornerSize, height * cornerSize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Ariel", 25));
        gc.fillText("Score: " + (speed - 6), 10, 30);

        // random foodcolor
        Color colorFoodSet = Color.WHITE;

        switch (foodColor) {
            case 0:
                colorFoodSet = Color.PURPLE;
                break;
            case 1:
                colorFoodSet = Color.LIGHTBLUE;
                break;
            case 2:
                colorFoodSet = Color.YELLOW;
                break;
            case 3:
                colorFoodSet = Color.PINK;
                break;
            case 4:
                colorFoodSet = Color.ORANGE;
                break;
            case 5:
                colorFoodSet = Color.RED;
        }
        gc.setFill(colorFoodSet);
        gc.fillOval(foodX * cornerSize, foodY * cornerSize, cornerSize, cornerSize);

        // snake
        for (Corner c : snake) {
            gc.setFill(Color.LIGHTGREEN);
            gc.fillRect(c.getX() * cornerSize, c.getY() * cornerSize, cornerSize - 1, cornerSize - 1);
            gc.setFill(Color.GREEN);
            gc.fillRect(c.getX() * cornerSize, c.getY() * cornerSize, cornerSize - 2, cornerSize - 2);

        }

    }

    // food
    public void newFood() {
        start:
        while (true) {
            foodX = rand.nextInt(width-2);
            foodY = rand.nextInt(height-2);

            for (Corner c : snake) {
                if (c.getX() == foodX && c.getY() == foodY) {
                    continue start;
                }
            }
            foodColor = rand.nextInt(6);
            speed++;
            break;
        }
    }

    public void controlTheSnake(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
            if (key.getCode() == KeyCode.UP) {
                setDirection(Dir.up);
            }
            if (key.getCode() == KeyCode.LEFT) {
                setDirection(Dir.left);
                ;
            }
            if (key.getCode() == KeyCode.DOWN) {
                setDirection(Dir.down);
            }
            if (key.getCode() == KeyCode.RIGHT) {
                setDirection(Dir.right);
            }

        });

    }
}
