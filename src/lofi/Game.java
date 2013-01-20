package lofi;

import java.awt.Graphics2D;

public abstract class Game {
	
	public static enum Key
	{
		Left,Up,Right,Down,Space
	}
	
	private boolean[] keyDown = new boolean[5];
	private boolean[] lastKeyDown = new boolean[5];
	private boolean[] keyPressed = new boolean[5];
	
	public abstract void paint(Graphics2D g2);
	public abstract void update();
	
	public final boolean isKeyDown(Key key)
	{
		return keyDown[key.ordinal()];
	}
	
	public final boolean isKeyPressed(Key key)
	{
		return keyPressed[key.ordinal()];
	}
	
	public final void onKeyDown(Key key)
	{
		keyDown[key.ordinal()] = true;
		keyPressed[key.ordinal()] = true;
	}
	
	public final void onKeyUp(Key key)
	{
		keyDown[key.ordinal()] = false;
	}
	
	public final void updatePressed() {
		for (int i = 0; i < 5; i++) {
			keyPressed[i] = keyDown[i] && !lastKeyDown[i];
		}
		lastKeyDown = keyDown.clone();
	}
	
	public final void flushKeys()
	{
		for (int i = 0; i < 5; i++) {
			keyPressed[i] = false;
		}
	}
}
