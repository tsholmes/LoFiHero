package lofi;

public class Res {

	public static SpriteSheet heroSheet = new SpriteSheet("HeroSheet.bmp", 16);

	public static Animation heroWalkRight = new Animation(heroSheet, new int[] {
			0, 1 }, new int[] { 2, 2 });
	public static Animation heroWalkLeft = new Animation(heroSheet, new int[] {
			2, 3 }, new int[] { 2, 2 });
	public static Animation heroIdleRight = new Animation(heroSheet,
			new int[] { 0 }, new int[] { 1 });
	public static Animation heroIdleLeft = new Animation(heroSheet,
			new int[] { 2 }, new int[] { 1 });
	public static Animation heroJumpRight = new Animation(heroSheet,
			new int[] { 4 }, new int[] { 1 });
	public static Animation heroJumpLeft = new Animation(heroSheet,
			new int[] { 5 }, new int[] { 1 });
	public static Animation heroFallRight = new Animation(heroSheet,
			new int[] { 6 }, new int[] { 1 });
	public static Animation heroFallLeft = new Animation(heroSheet,
			new int[] { 7 }, new int[] { 1 });
	public static Animation heroDashRight = new Animation(heroSheet,
			new int[] { 8 }, new int[] { 1 });
	public static Animation heroDashLeft = new Animation(heroSheet,
			new int[] { 9 }, new int[] { 1 });
	public static Animation heroDashUpRight = new Animation(heroSheet,
			new int[] { 10 }, new int[] { 1 });
	public static Animation heroDashUpLeft = new Animation(heroSheet,
			new int[] { 11 }, new int[] { 1 });
	public static Animation heroDashUp = new Animation(heroSheet,
			new int[] { 12 }, new int[] { 1 });
	public static Animation heroDashDownRight = new Animation(heroSheet,
			new int[] { 13 }, new int[] { 1 });
	public static Animation heroDashDownLeft = new Animation(heroSheet,
			new int[] { 14 }, new int[] { 1 });
	public static Animation heroDashDown = new Animation(heroSheet,
			new int[] { 15 }, new int[] { 1 });

	public static SpriteSheet swordSheet = new SpriteSheet("SwordSheet.bmp", 16);

	public static Animation swordCarryRight = new Animation(swordSheet,
			new int[] { 0 }, new int[] { 1 });
	public static Animation swordCarryLeft = new Animation(swordSheet,
			new int[] { 1 }, new int[] { 1 });
	public static Animation swordDashRight = new Animation(swordSheet,
			new int[] { 2 }, new int[] { 1 });
	public static Animation swordDashLeft = new Animation(swordSheet,
			new int[] { 3 }, new int[] { 1 });
	public static Animation swordDashUp = new Animation(swordSheet,
			new int[] { 4 }, new int[] { 1 });
	public static Animation swordDashUpRight = new Animation(swordSheet,
			new int[] { 5 }, new int[] { 1 });
	public static Animation swordDashUpLeft = new Animation(swordSheet,
			new int[] { 6 }, new int[] { 1 });
	public static Animation swordDashDown = new Animation(swordSheet,
			new int[] { 7 }, new int[] { 1 });
	public static Animation swordDashDownRight = new Animation(swordSheet,
			new int[] { 8 }, new int[] { 1 });
	public static Animation swordDashDownLeft = new Animation(swordSheet,
			new int[] { 9 }, new int[] { 1 });
	public static Animation swordSwingRight = new Animation(swordSheet,
			new int[] { 10, 11, 12 }, new int[] { 1, 1, 2 });
	public static Animation swordSwingLeft = new Animation(swordSheet,
			new int[] { 13, 14, 15 }, new int[] { 1, 1, 2 });

	public static SpriteSheet slimeSheet = new SpriteSheet("SlimeSheet.bmp", 16);

	public static Animation slimeIdle = new Animation(slimeSheet, new int[] {
			0, 1 }, new int[] { 6, 6 });
	public static Animation slimeJump = new Animation(slimeSheet,
			new int[] { 2 }, new int[] { 1 });
	public static Animation slimeFall = new Animation(slimeSheet,
			new int[] { 3 }, new int[] { 1 });
	public static Animation slimePeak = new Animation(slimeSheet,
			new int[] { 0 }, new int[] { 1 });
}
