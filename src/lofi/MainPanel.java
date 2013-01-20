package lofi;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 7571407562101235950L;

	public static final int SCALE = 3;

	private Image backBuffer = null;

	private Game game = null;

	public MainPanel() {
		setPreferredSize(new Dimension(320 * SCALE, 240 * SCALE));

		game = new LoFiHeroGame();

		addKeyListener(this);

		new UpdateThread().start();
	}

	@Override
	public void paint(Graphics g) {
		if (backBuffer == null) {
			backBuffer = createImage(320, 240);
		}
		if (backBuffer != null) {
			game.updatePressed();
			game.update();
			game.paint((Graphics2D) backBuffer.getGraphics());

			g.drawImage(backBuffer, 0, 0, 320 * SCALE, 240 * SCALE, null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			game.onKeyDown(Game.Key.Left);
			break;
		case KeyEvent.VK_UP:
			game.onKeyDown(Game.Key.Up);
			break;
		case KeyEvent.VK_RIGHT:
			game.onKeyDown(Game.Key.Right);
			break;
		case KeyEvent.VK_DOWN:
			game.onKeyDown(Game.Key.Down);
			break;
		case KeyEvent.VK_SPACE:
			game.onKeyDown(Game.Key.Space);
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			game.onKeyUp(Game.Key.Left);
			break;
		case KeyEvent.VK_UP:
			game.onKeyUp(Game.Key.Up);
			break;
		case KeyEvent.VK_RIGHT:
			game.onKeyUp(Game.Key.Right);
			break;
		case KeyEvent.VK_DOWN:
			game.onKeyUp(Game.Key.Down);
			break;
		case KeyEvent.VK_SPACE:
			game.onKeyUp(Game.Key.Space);
			break;
		default:
			break;
		}
	}

	private class UpdateThread extends Thread {
		public UpdateThread() {
			setDaemon(true);
		}

		public void run() {
			while (true) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
				if (isDisplayable()) {
					repaint();
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		MainPanel panel = new MainPanel();
		frame.addKeyListener(panel);
		frame.setContentPane(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.requestFocus();
	}
}
