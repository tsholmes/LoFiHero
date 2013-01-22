package lofi;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class Entity {
	public static double MAX_FALL_SPEED = 12;

	public double x;
	public double y;
	public double vx;
	public double vy;
	public boolean ground = false;
	public boolean fallThrough = false;
	public boolean gravity = true;

	public final double width;
	public final double height;

	public Entity(double width, double height) {
		this.width = width;
		this.height = height;
	}

	public int X() {
		return (int) Math.round(x);
	}

	public int Y() {
		return (int) Math.round(y);
	}

	public void move(int[][] platforms) {
		if (gravity) {
			vy = Math.min(MAX_FALL_SPEED, vy + 1);
		}
		
		ground = false;
		double newX = x + vx;
		double newY = y + vy;

		if (newX < 0) {
			newX = 0;
		}
		if (newX > LoFiHeroGame.WIDTH - width) {
			newX = LoFiHeroGame.WIDTH - width;
		}
		if (newY > LoFiHeroGame.HEIGHT - height) {
			newY = LoFiHeroGame.HEIGHT - height;
			ground = true;
			vy = 0;
		}
		if (newY < 0) {
			newY = 0;
			vy = 0;
		}

		if (!fallThrough) {
			for (int[] platform : platforms) {
				if (newX + width >= platform[1] && newX <= platform[2]
						&& y + height <= platform[0]
						&& newY + height >= platform[0]) {
					newY = platform[0] - height;
					vy = 0;
					ground = true;
				}
			}
		}

		x = newX;
		y = newY;
	}

	public abstract void paint(Graphics2D g2);

	public abstract void update(Game game);

	public abstract Rectangle getBox();
}
