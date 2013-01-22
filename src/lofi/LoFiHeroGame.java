package lofi;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;

public class LoFiHeroGame extends Game {

	public static final boolean DEBUG = false;
	public static final double SPAWN_CHANCE = 0.05;

	public static final int WIDTH = 319;
	public static final int HEIGHT = 239;

	public static final int BLOOD_HIT_COUNT = 200;
	public static final int BLOOD_DEATH_COUNT = 2000;

	public static final int BLOOD_MODE_COUNT = 100000;

	private int[][] platforms = { { 200, 40, 120 }, { 200, 200, 280 },
			{ 160, 120, 200 }, { 120, 40, 120 }, { 120, 200, 280 },
			{ 80, 120, 200 }, { 40, 40, 120 }, { 40, 200, 280 } };

	private Hero hero = new Hero();

	private HashSet<Enemy> enemies = new HashSet<Enemy>();
	private HashSet<Blood> blood = new HashSet<Blood>();

	private int bloodCount = 0;
	private boolean bloodMode = false;

	int tick = 0;

	public LoFiHeroGame() {
		hero.x = (WIDTH - Hero.WIDTH) / 2;
		hero.y = HEIGHT - Hero.HEIGHT;
	}

	@Override
	public void paint(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.fillRect(0, 0, WIDTH, HEIGHT);

		g2.setColor(Color.red);
		for (int i = 0; i < platforms.length; i++) {
			g2.drawLine(platforms[i][1], platforms[i][0], platforms[i][2],
					platforms[i][0]);
		}

		tick += 1;
		int bloodHeight = HEIGHT * bloodCount / BLOOD_MODE_COUNT;
		for (int x = 0; x < WIDTH; x += 10) {
			g2.drawLine(x, HEIGHT - bloodHeight, x, HEIGHT);
		}

		for (Blood b : blood) {
			b.paint(g2);
		}

		hero.paint(g2);

		for (Enemy enemy : enemies) {
			enemy.paint(g2);
		}

		g2.setColor(Color.red);
		g2.drawRect(0, 0, WIDTH, HEIGHT);
	}

	@Override
	public void update() {
		hero.update(this);
		for (Enemy enemy : enemies) {
			enemy.update(this);
			if (enemy.knockbackTimer == 0) {
				hero.hit(enemy.getBox());
			}
		}
		for (Blood b : blood) {
			b.update(this);
		}

		Rectangle sword = hero.getSwordBox();

		if (sword != null) {
			for (Enemy enemy : enemies) {
				if (enemy.attack(sword, hero.x + hero.width / 2.0, hero.y + hero.height / 2.0)) {
					for (int i = 0; i < BLOOD_HIT_COUNT; i++) {
						Blood b = new Blood(enemy.x, enemy.y);
						blood.add(b);
					}
				}
			}
		}

		hero.move(platforms);
		for (Enemy enemy : enemies) {
			enemy.move(platforms);
		}
		for (Blood b : blood) {
			b.move(platforms);
		}

		HashSet<Enemy> newEnemies = new HashSet<Enemy>();
		for (Enemy enemy : enemies) {
			if (enemy.knockbackTimer > Enemy.MAX_KNOCKBACK_TIMER / 2
					|| enemy.health > 0) {
				newEnemies.add(enemy);
				if (bloodMode && enemy.health > 0) {
					enemy.health = 0;
					enemy.knockbackTimer = 0;
					enemy.attack(enemy.getBox(), enemy.x + enemy.width / 2.0, enemy.y + enemy.height);
					enemy.knockbackTimer += Math.random()
							* Enemy.MAX_KNOCKBACK_TIMER;
				}
			} else {
				for (int i = 0; i < BLOOD_DEATH_COUNT; i++) {
					Blood b = new Blood(enemy.x, enemy.y);
					blood.add(b);
				}
			}
		}
		enemies = newEnemies;

		HashSet<Blood> newBlood = new HashSet<Blood>();
		for (Blood b : blood) {
			if (b.y < HEIGHT) {
				newBlood.add(b);
			} else if (!bloodMode) {
				bloodCount++;
			}
		}
		blood = newBlood;

		if (Math.random() < SPAWN_CHANCE && !bloodMode) {
			Slime slime = new Slime();
			slime.target = hero;
			slime.x = (int) (Math.random() * 380 + 20);
			slime.y = (int) (Math.random() * 200 + 20);
			enemies.add(slime);
		}

		if (bloodMode) {
			bloodCount = Math.max(0, bloodCount - BLOOD_MODE_COUNT / 100);
		}

		if (blood.size() == 0 && enemies.size() == 0 && bloodCount == 0) {
			bloodMode = false;
		}

		if (bloodCount >= BLOOD_MODE_COUNT) {
			bloodMode = true;
		}
	}

}
