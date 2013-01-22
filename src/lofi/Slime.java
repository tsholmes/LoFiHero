package lofi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import lofi.Animation.AnimationHandle;

public class Slime extends Enemy {

	public static final double WIDTH = 16;
	public static final double HEIGHT = 16;
	public static final int MAX_ACTION_TIMER = 10;
	public static final double JUMP_SPEED = 10;
	public static final double HORIZ_SPEED = 4;
	public static final int MAX_HEALTH = 3;

	public AnimationHandle slimeIdle = Res.slimeIdle.createHandle();
	public AnimationHandle slimeJump = Res.slimeJump.createHandle();
	public AnimationHandle slimeFall = Res.slimeFall.createHandle();
	public AnimationHandle slimePeak = Res.slimePeak.createHandle();

	public AnimationHandle animation = slimeIdle;
	public Entity target = null;

	public int actionTimer = MAX_ACTION_TIMER;

	public Slime() {
		super(WIDTH, HEIGHT, MAX_HEALTH);
	}

	@Override
	public void paint(Graphics2D g2) {
		animation = slimeIdle;
		if (knockbackTimer == 0 && !ground) {
			if (Math.abs(vy) < 2) {
				animation = slimePeak;
			} else if (vy < 0) {
				animation = slimeJump;
			} else {
				animation = slimeFall;
			}
		}

		if (knockbackTimer % 4 > 1) {
			g2.setXORMode(Color.red);
		}
		animation.paint(g2, X(), Y());
		if (knockbackTimer % 4 > 1) {
			g2.setPaintMode();
		}

		if (LoFiHeroGame.DEBUG) {
			g2.setColor(Color.magenta);
			g2.draw(getBox());
		}
	}

	@Override
	public void update(Game game) {
		fallThrough = false;

		if (ground) {
			if (knockbackTimer == 0) {
				actionTimer--;
			}
			vx = 0;
		}

		if (actionTimer == 0 && target != null) {
			if (y + height < target.y + target.height && ground) {
				fallThrough = true;
			}

			Rectangle box = getBox();
			Rectangle tbox = target.getBox();

			double xdif = 0;
			/*
			 * if (box.getMaxX() < tbox.getMinX()) { xdif = tbox.getMinX() -
			 * box.getMaxX(); } else if (box.getMinX() > tbox.getMaxX()) { xdif
			 * = tbox.getMaxX() - box.getMinX(); }
			 */
			xdif = tbox.getCenterX() - box.getCenterX();

			double ydif = 0;
			/*
			 * if (box.getMaxY() < tbox.getMinY()) { ydif = tbox.getMinY() -
			 * box.getMaxY(); } else if (box.getMinY() > tbox.getMaxY()) { ydif
			 * = tbox.getMaxY() - box.getMinY(); }
			 */
			ydif = tbox.getCenterY() - box.getCenterY();

			if (ydif > 0) {
				if (Math.abs(xdif) >= Math.abs(ydif)) {
					vy = -JUMP_SPEED;
					if (xdif > 0) {
						vx = Math.min(xdif / 20, HORIZ_SPEED);
						// vx = HORIZ_SPEED;
					} else if (xdif < 0) {
						vx = Math.max(xdif / 20, -HORIZ_SPEED);
						// vx = -HORIZ_SPEED;
					}
				} else {
					fallThrough = true;
				}
			} else {
				vy = -JUMP_SPEED;
				if (xdif > 0) {
					vx = Math.min(xdif / 20, HORIZ_SPEED);
					// vx = HORIZ_SPEED;
				} else if (xdif < 0) {
					vx = Math.max(xdif / 20, -HORIZ_SPEED);
					// vx = -HORIZ_SPEED;
				}
			}
		}
		if (actionTimer == 0) {
			actionTimer = MAX_ACTION_TIMER;
		}
	}

	@Override
	public Rectangle getBox() {
		return new Rectangle(X() + 3, Y() + 8, (int) WIDTH - 7,
				(int) HEIGHT - 12);
	}

}
