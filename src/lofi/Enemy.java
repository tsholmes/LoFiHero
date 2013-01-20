package lofi;

import java.awt.Rectangle;

public abstract class Enemy extends Entity {

	public static final int MAX_KNOCKBACK_TIMER = 10;
	public static final int KNOCKBACK_SPEED = 8;

	public int knockbackTimer = 0;
	public int health;

	public Enemy(int width, int height, int health) {
		super(width, height);
		this.health = health;
	}

	@Override
	public void move(int[][] platforms) {
		super.move(platforms);

		if (knockbackTimer > 0) {
			knockbackTimer--;
		}
	}

	public boolean attack(Rectangle swordBox) {
		if (knockbackTimer > MAX_KNOCKBACK_TIMER / 2)
			return false;
		if (swordBox.intersects(getBox())) {
			knockbackTimer = MAX_KNOCKBACK_TIMER;

			double angle = (Math.random() * 0.5 + 0.25) * Math.PI;

			int x = (int) (Math.cos(angle) * KNOCKBACK_SPEED);
			int y = (int) (Math.sin(angle) * KNOCKBACK_SPEED);

			vx = x;
			vy = -y;
			
			health--;
			
			return true;
		}
		return false;
	}
}
