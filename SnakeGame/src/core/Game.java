package core;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends BasicGameState {
	public static final int UNIT_SIZE = 20;
	private int gridWidth;
	private int gridHeight;
	private static final int INITIAL_SPEED = 200;

	private Snake snake;
	private Food food;
	private boolean running;
	private int timer;
	private int speed;
	private int score;
	private int id;

	public Game(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setShowFPS(true);
		gridWidth = gc.getWidth() / UNIT_SIZE;
		gridHeight = gc.getHeight() / UNIT_SIZE;
		startGame();
	}

	public void startGame() {
		snake = new Snake(gridWidth / 2, gridHeight / 2);
		food = new Food(gridWidth, gridHeight);
		running = true;
		timer = 0;
		speed = INITIAL_SPEED;
		score = 0;
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();

		if (running) {
			if (input.isKeyPressed(Input.KEY_UP) || (input.isKeyPressed(Input.KEY_W))) {
				snake.setDirection(Direction.UP);
			} else if (input.isKeyPressed(Input.KEY_DOWN) || (input.isKeyPressed(Input.KEY_S))) {
				snake.setDirection(Direction.DOWN);
			} else if (input.isKeyPressed(Input.KEY_LEFT) || (input.isKeyPressed(Input.KEY_A))) {
				snake.setDirection(Direction.LEFT);
			} else if (input.isKeyPressed(Input.KEY_RIGHT) || (input.isKeyPressed(Input.KEY_D))) {
				snake.setDirection(Direction.RIGHT);
			}

			timer += delta;
			if (timer >= speed) {
				snake.move();
				timer = 0;

				Segment head = snake.getHead();
				if (head.getX() == food.getX() && head.getY() == food.getY()) {
					snake.grow();
					food.spawn(gridWidth, gridHeight);
					score++;
					speed = Math.max(speed - 10, 50);
				}

				if (snake.checkCollision() || checkWallCollision(head)) {
					running = false;
				}

			}
		} else {
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				startGame();
			}
		}
	}

	private boolean checkWallCollision(Segment head) {
		return head.getX() < 0 || head.getX() >= gridWidth || head.getY() < 0 || head.getY() >= gridHeight;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (running) {
			snake.render(g);
			food.render(g);

			g.setColor(Color.white);
			g.drawString("Score: " + score, 10, 25);

			g.setColor(Color.white);
			g.drawRect(0, 0, gc.getWidth() - 1, gc.getHeight() - 1);
		} else {
			g.setColor(Color.red);
			g.drawString(
					"Game Over",
					(gc.getWidth() - g.getFont().getWidth("Game Over")) / 2,
					(gc.getHeight() - g.getFont().getHeight("Game Over")) / 2
			);

			g.drawString(
					"Final Score: " + score,
					(gc.getWidth() - g.getFont().getWidth("Final Score: " + score)) / 2,
					(gc.getHeight() - g.getFont().getHeight("Game Over")) / 2 + 50
			);

			g.drawString(
					"Press SPACE to Play Again",
					(gc.getWidth() - g.getFont().getWidth("Press SPACE to Play Again")) / 2,
					(gc.getHeight() - g.getFont().getHeight("Game Over")) / 2 + 100
			);
		}
	}

	@Override
	public int getID() {
		return id;
	}
}
