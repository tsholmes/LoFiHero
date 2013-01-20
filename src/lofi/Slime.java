package lofi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import lofi.Animation.AnimationHandle;

public class Slime extends Enemy {

	public static final int WIDTH = 16;
	public static final int HEIGHT = 16;
	public static final int MAX_ACTION_TIMER = 50;
	public static final int JUMP_SPEED = 10;
	public static final int HORIZ_SPEED = 4;
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

			double xdif = 0;
			if (x + width < target.x) {
				xdif = target.x - (x + width);
			} else if (x > target.x + target.width) {
				xdif = (target.x + target.width) - x;
			}

			double ydif = 0;
			if (y + height < target.y) {
				ydif = target.y - (y + height);
			} else if (y > target.y + target.height) {
				ydif = (target.y + target.height) - y;
			}

			if (ydif > 0) {
				if (Math.abs(xdif) >= Math.abs(ydif)) {
					vy = -JUMP_SPEED;
					if (xdif > 0) {
						vx = HORIZ_SPEED;
					} else if (xdif < 0) {
						vx = -HORIZ_SPEED;
					}
				} else {
					fallThrough = true;
				}
			} else {
				vy = -JUMP_SPEED;
				if (xdif > 0) {
					vx = HORIZ_SPEED;
				} else if (xdif < 0) {
					vx = -HORIZ_SPEED;
				}
			}
		}
		if (actionTimer == 0) {
			actionTimer = MAX_ACTION_TIMER;
		}
	}

	@Override
	public Rectangle getBox() {
		return new Rectangle(X() + 1, Y() + 4, WIDTH - 2, HEIGHT - 4);
	}

}
