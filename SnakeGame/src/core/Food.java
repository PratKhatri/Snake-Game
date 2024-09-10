package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.Random;

public class Food {
    private int x, y;
    private Random rand;

    public Food(int gridWidth, int gridHeight) {
        rand = new Random();
        spawn(gridWidth, gridHeight);
    }

    public void spawn(int gridWidth, int gridHeight) {
        x = rand.nextInt(gridWidth);
        y = rand.nextInt(gridHeight);
    }

    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x * Game.UNIT_SIZE, y * Game.UNIT_SIZE, Game.UNIT_SIZE, Game.UNIT_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
