package lofi;

import java.awt.Rectangle;

public abstract class Enemy extends Entity {

	public static final int MAX_KNOCKBACK_TIMER = 10;
	public static final double KNOCKBACK_SPEED = 8;

	public int knockbackTimer = 0;
	public int health;

	public Enemy(double width, double height, int health) {
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

	public boolean attack(Rectangle swordBox, double sx, double sy) {
		if (knockbackTimer > MAX_KNOCKBACK_TIMER / 2)
			return false;
		if (swordBox.intersects(getBox())) {
			knockbackTimer = MAX_KNOCKBACK_TIMER;
			
			double dx = x + width / 2.0 - sx;
			double dy = -Math.abs(dx) * 2 - 1;
			double dist = Math.hypot(dx,dy);
			
			dx /= dist;
			dy /= dist;
			
			vx = dx * KNOCKBACK_SPEED;
			vy = dy * KNOCKBACK_SPEED;

			/*double angle = (Math.random() * 0.5 + 0.25) * Math.PI;

			vx = Math.cos(angle) * KNOCKBACK_SPEED;
			vy = -Math.sin(angle) * KNOCKBACK_SPEED;*/
			
			health--;
			
			return true;
		}
		return false;
	}
}
