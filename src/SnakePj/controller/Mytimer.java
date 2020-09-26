package SnakePj.controller;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;

public class Mytimer extends AnimationTimer {
    private long lastTick = 0;
    private Snake s;
    private GraphicsContext gc;

    public Mytimer(Snake s, GraphicsContext gc) {
        this.s = s;
        this.gc = gc;
    }

    @Override
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


}
