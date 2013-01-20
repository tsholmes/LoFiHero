package lofi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Blood extends Entity {

	public static final int BLOOD_SPEED = 8;

	public Blood(double x, double y) {
		super(0, 0);
		this.x = x;
		this.y = y;

		double angle = Math.random() * Math.PI * 2;
		double speed = Math.random() * BLOOD_SPEED;
		vx = Math.cos(angle) * speed;
		vy = Math.sin(angle) * speed;
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.red);
		g2.fillRect(X(), Y(), 1, 1);
	}

	@Override
	public void update(Game game) {
		if (ground || Math.random() < 0.1) {
			vx *= 0.8;
		}

		gravity = Math.random() < 0.5;

		if (x <= 1) {
			x = 1;
			vx = 0;
		}
		if (x >= LoFiHeroGame.WIDTH - 1) {
			x = LoFiHeroGame.WIDTH - 1;
			vx = 0;
		}

		if (x == 1 || x == LoFiHeroGame.WIDTH - 1) {
			vy = Math.min(vy, MAX_FALL_SPEED / 3);
		}

		fallThrough = ground;
	}

	@Override
	public Rectangle getBox() {
		return new Rectangle(X(), Y(), 0, 0);
	}

}
