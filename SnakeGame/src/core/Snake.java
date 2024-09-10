package core;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.LinkedList;

public class Snake {
    private LinkedList<Segment> body;
    private Direction direction;

    public Snake(int startX, int startY) {
        body = new LinkedList<>();

        body.add(new Segment(startX, startY));
        body.add(new Segment(startX, startY + 1));
        body.add(new Segment(startX, startY + 2));
        direction = Direction.UP;
    }

    public void setDirection(Direction newDirection) {
        if (direction.isOpposite(newDirection)) {
            return;
        }
        direction = newDirection;
    }

    public void move() {
        Segment head = body.getFirst();
        int newX = head.getX() + direction.getDx();
        int newY = head.getY() + direction.getDy();
        body.addFirst(new Segment(newX, newY));
        body.removeLast();
    }

    public void grow() {
        Segment tail = body.getLast();
        body.add(new Segment(tail.getX(), tail.getY()));
    }

    public boolean checkCollision() {
        Segment head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void render(Graphics g) {
        for (Segment segment : body) {
            g.setColor(Color.green);
            g.fillRect(segment.getX() * Game.UNIT_SIZE, segment.getY() * Game.UNIT_SIZE, Game.UNIT_SIZE, Game.UNIT_SIZE);
        }
    }

    public Segment getHead() {
        return body.getFirst();
    }
}
