package lofi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import lofi.Animation.AnimationHandle;
import lofi.Game.Key;

public class Hero extends Entity {
	public static final double WIDTH = 16;
	public static final double HEIGHT = 24;
	public static final int DASH_TIME = 16;
	public static final double DASH_SPEED = 10;
	public static final double DASH_DIAG_SPEED = 7;
	public static final double JUMP_SPEED = 9;
	public static final double MAX_FALL_SPEED = 12;
	public static final int MAX_KNOCKBACK_TIMER = 10;
	public static final double KNOCKBACK_SPEED = 8;

	public AnimationHandle heroWalkRight = Res.heroWalkRight.createHandle();
	public AnimationHandle heroWalkLeft = Res.heroWalkLeft.createHandle();
	public AnimationHandle heroIdleRight = Res.heroIdleRight.createHandle();
	public AnimationHandle heroIdleLeft = Res.heroIdleLeft.createHandle();
	public AnimationHandle heroJumpRight = Res.heroJumpRight.createHandle();
	public AnimationHandle heroJumpLeft = Res.heroJumpLeft.createHandle();
	public AnimationHandle heroFallRight = Res.heroFallRight.createHandle();
	public AnimationHandle heroFallLeft = Res.heroFallLeft.createHandle();
	public AnimationHandle heroDashRight = Res.heroDashRight.createHandle();
	public AnimationHandle heroDashLeft = Res.heroDashLeft.createHandle();
	public AnimationHandle heroDashUpRight = Res.heroDashUpRight.createHandle();
	public AnimationHandle heroDashUpLeft = Res.heroDashUpLeft.createHandle();
	public AnimationHandle heroDashUp = Res.heroDashUp.createHandle();
	public AnimationHandle heroDashDownRight = Res.heroDashDownRight
			.createHandle();
	public AnimationHandle heroDashDownLeft = Res.heroDashDownLeft
			.createHandle();
	public AnimationHandle heroDashDown = Res.heroDashDown.createHandle();

	public AnimationHandle swordCarryRight = Res.swordCarryRight.createHandle();
	public AnimationHandle swordCarryLeft = Res.swordCarryLeft.createHandle();
	public AnimationHandle swordDashRight = Res.swordDashRight.createHandle();
	public AnimationHandle swordDashLeft = Res.swordDashLeft.createHandle();
	public AnimationHandle swordDashUp = Res.swordDashUp.createHandle();
	public AnimationHandle swordDashUpRight = Res.swordDashUpRight
			.createHandle();
	public AnimationHandle swordDashUpLeft = Res.swordDashUpLeft.createHandle();
	public AnimationHandle swordDashDown = Res.swordDashDown.createHandle();
	public AnimationHandle swordDashDownRight = Res.swordDashDownRight
			.createHandle();
	public AnimationHandle swordDashDownLeft = Res.swordDashDownLeft
			.createHandle();
	public AnimationHandle swordSwingRight = Res.swordSwingRight.createHandle();
	public AnimationHandle swordSwingLeft = Res.swordSwingLeft.createHandle();

	public int dash = 0;
	public int dashX = 0;
	public int dashY = 0;
	public boolean right = true;
	public boolean canAttack = false;
	public boolean groundDash = false;
	public boolean canDash = false;
	public int swingTimer = 0;

	public boolean doDash = false;

	public int swordX = 0;
	public int swordY = 0;

	public int knockbackTimer = 0;

	public AnimationHandle heroAnimation = heroWalkRight;
	public AnimationHandle swordAnimation = swordCarryRight;

	public Hero() {
		super(WIDTH, HEIGHT);
	}

	@Override
	public void paint(Graphics2D g2) {
		swordY = 0;
		if (vx > 0) {
			heroAnimation = heroWalkRight;
			swordAnimation = swordCarryRight;
			swordX = -12;
		} else if (vx < 0) {
			heroAnimation = heroWalkLeft;
			swordAnimation = swordCarryLeft;
			swordX = 4;
		} else {
			if (right) {
				heroAnimation = heroIdleRight;
				swordAnimation = swordCarryRight;
				swordX = -12;
			} else {
				heroAnimation = heroIdleLeft;
				swordAnimation = swordCarryLeft;
				swordX = 4;
			}
		}

		if (!ground) {
			if (vy < 0) {
				if (right) {
					heroAnimation = heroJumpRight;
				} else {
					heroAnimation = heroJumpLeft;
				}
			} else if (vy >= 0) {
				if (right) {
					heroAnimation = heroFallRight;
				} else {
					heroAnimation = heroFallLeft;
				}
			}
		}

		if (dash > 0) {
			if (dashY == 0) {
				if (dashX == 1) {
					heroAnimation = heroDashRight;
					swordAnimation = swordDashRight;
					swordX = 12;
					swordY = 4;
				} else if (dashX == -1) {
					heroAnimation = heroDashLeft;
					swordAnimation = swordDashLeft;
					swordX = -20;
					swordY = 4;
				}
			} else if (dashY == 1) {
				if (dashX == 1) {
					heroAnimation = heroDashDownRight;
					swordAnimation = swordDashDownRight;
					swordX = 10;
					swordY = 10;
				} else if (dashX == -1) {
					heroAnimation = heroDashDownLeft;
					swordAnimation = swordDashDownLeft;
					swordX = -18;
					swordY = 10;
				} else if (dashX == 0) {
					heroAnimation = heroDashDown;
					swordAnimation = swordDashDown;
					swordX = -4;
					swordY = 16;
				}
			} else if (dashY == -1) {
				if (dashX == 1) {
					heroAnimation = heroDashUpRight;
					swordAnimation = swordDashUpRight;
					swordX = 10;
					swordY = -12;
				} else if (dashX == -1) {
					heroAnimation = heroDashUpLeft;
					swordAnimation = swordDashUpLeft;
					swordX = -18;
					swordY = -12;
				} else if (dashX == 0) {
					heroAnimation = heroDashUp;
					swordAnimation = swordDashUp;
					swordX = -4;
					swordY = -24;
				}
			}
		}
		if (swingTimer > 0) {
			if (right) {
				swordAnimation = swordSwingRight;
				swordX = 12;
				swordY = 4 - swingTimer;
				swordSwingLeft.advance();
			} else {
				swordAnimation = swordSwingLeft;
				swordX = -20;
				swordY = 4 - swingTimer;
				swordSwingRight.advance();
			}
		}

		if (knockbackTimer >= MAX_KNOCKBACK_TIMER / 2 && dash == 0) {
			if (vx > 0) {
				heroAnimation = heroIdleRight;
			} else {
				heroAnimation = heroIdleLeft;
			}
		}

		if (g2 != null) {
			if (knockbackTimer % 4 > 1 || dash % 2 == 1) {
				g2.setXORMode(Color.red);
			}
			heroAnimation.paint(g2, X(), Y());
			swordAnimation.paint(g2, X() + swordX, Y() + swordY);
			if (knockbackTimer % 4 > 1 || dash % 2 == 1) {
				g2.setPaintMode();
			}

			if (LoFiHeroGame.DEBUG) {
				g2.setColor(Color.magenta);
				g2.draw(getBox());

				Rectangle rect = getSwordBox();
				if (rect != null) {
					g2.draw(rect);
				}
			}
		}
	}

	@Override
	public void update(Game game) {
		fallThrough = false;

		if (dash == 0 && knockbackTimer < MAX_KNOCKBACK_TIMER / 2) {
			if (game.isKeyDown(Key.Down) && ground) {
				fallThrough = true;
			}

			if (game.isKeyDown(Key.Right)) {
				if (vx < 0) {
					vx += 2;
				} else {
					vx = Math.min(4, vx + 1);
				}
				right = true;
			} else if (game.isKeyDown(Key.Left)) {
				if (vx > 0) {
					vx -= 2;
				} else {
					vx = Math.max(-4, vx - 1);
				}
				right = false;
			} else {
				vx *= 0.8;
				if (Math.abs(vx) < 0.8)
					vx = 0;
			}
			if (game.isKeyDown(Key.Up) && ground) {
				vy = -JUMP_SPEED - 1;
			}
			if (swingTimer == 0 && groundDash && doDash) {
				if (game.isKeyDown(Key.Up)) {
					dashY = -1;
				} else if (game.isKeyDown(Key.Down)) {
					dashY = 1;
				} else {
					dashY = 0;
				}
				if (game.isKeyDown(Key.Right)) {
					dashX = 1;
				} else if (game.isKeyDown(Key.Left)) {
					dashX = -1;
				} else {
					dashX = 0;
				}
				if (game.isKeyDown(Key.Up) || game.isKeyDown(Key.Down)
						|| game.isKeyDown(Key.Right)
						|| game.isKeyDown(Key.Left)) {
					dash = DASH_TIME;
					swingTimer = 0;
				}
				doDash = false;
			}
			if (game.isKeyPressed(Key.Space) && swingTimer == 0 && canAttack) {
				swingTimer = 4;
				swordSwingRight.reset();
				swordSwingLeft.reset();
				doDash = true;
			}
		}

		if (!game.isKeyDown(Key.Space)) {
			doDash = false;
			dash = 0;
		}

		if (dash > 0) {
			if (Math.abs(dashX) + Math.abs(dashY) > 1) {
				vx = dashX * DASH_DIAG_SPEED;
				vy = dashY * DASH_DIAG_SPEED;
			} else {
				vx = dashX * DASH_SPEED;
				vy = dashY * DASH_SPEED;
			}
			if (dashY > 0) {
				fallThrough = true;
			}
			groundDash = false;
			dash--;
		}

		if (swingTimer > 0) {
			swingTimer--;
			canAttack = false;
			if (swingTimer == 0 && !groundDash) {
				doDash = false;
			}
		}

		if (knockbackTimer > 0) {
			knockbackTimer--;
		}

		canAttack = swingTimer == 0 && dash == 0;
		groundDash |= ground;
	}

	public Rectangle getSwordBox() {
		paint(null);

		int x = X();
		int y = Y();

		if (swordAnimation == swordDashRight) {
			return new Rectangle(x + swordX, y + swordY + 8, 24, 6);
		} else if (swordAnimation == swordDashLeft) {
			return new Rectangle(x + swordX, y + swordY + 8, 24, 6);
		} else if (swordAnimation == swordDashUp) {
			return new Rectangle(x + swordX + 9, y + swordY, 6, 24);
		} else if (swordAnimation == swordDashUpRight) {
			return new Rectangle(x + swordX + 8, y + swordY, 16, 16);
		} else if (swordAnimation == swordDashUpLeft) {
			return new Rectangle(x + swordX, y + swordY, 16, 16);
		} else if (swordAnimation == swordDashDown) {
			return new Rectangle(x + swordX + 9, y + swordY, 6, 24);
		} else if (swordAnimation == swordDashDownRight) {
			return new Rectangle(x + swordX + 8, y + swordY + 8, 16, 16);
		} else if (swordAnimation == swordDashDownLeft) {
			return new Rectangle(x + swordX, y + swordY + 8, 16, 16);
		} else if (swordAnimation == swordSwingRight) {
			return new Rectangle(x + swordX + 8, y + swordY + 2, 16, 16);
		} else if (swordAnimation == swordSwingLeft) {
			return new Rectangle(x + swordX, y + swordY + 2, 16, 16);
		}
		return null;
	}

	@Override
	public Rectangle getBox() {
		return new Rectangle(X() + (int) (WIDTH / 2), Y() + (int) (HEIGHT / 2)
				- 2, 1, 4);
	}

	public void hit(Rectangle box) {
		if (dash > 0 || knockbackTimer > 0)
			return;
		if (box.intersects(getBox())) {
			knockbackTimer = MAX_KNOCKBACK_TIMER;
			double angle = Math.random() * Math.PI;
			vx = Math.cos(angle) * KNOCKBACK_SPEED;
			vy = -Math.sin(angle) * KNOCKBACK_SPEED;
		}
	}
}
