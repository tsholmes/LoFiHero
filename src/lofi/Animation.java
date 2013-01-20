package lofi;

import java.awt.Graphics2D;

public class Animation {
	private SpriteSheet sheet;
	private int[] frames;
	private int[] delays;
	
	public Animation(SpriteSheet sheet, int[] frames, int[] delays) {
		this.sheet = sheet;
		this.frames = frames;
		this.delays = delays;
	}
	
	public AnimationHandle createHandle() {
		return new AnimationHandle();
	}
	
	public class AnimationHandle {
		
		private int curFrame = 0;
		private int curDelay = 0;
		
		public void paint(Graphics2D g2, int x, int y)
		{
			sheet.drawFrame(g2, frames[curFrame], x, y);
			
			advance();
		}
		
		public void advance() {
			curDelay++;
			if (curDelay >= delays[curFrame]) {
				curDelay = 0;
				curFrame = (curFrame + 1) % frames.length;
			}
		}
		
		public void reset() {
			curFrame = 0;
			curDelay = 0;
		}
	}
}
