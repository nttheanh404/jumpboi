package core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import inputs.KeyboardHandler;
import level.BackgroundMusicPlayer;
import level.LevelHandler;
import objects.Player;
import ui.MenuHandler;////


public class Window extends Canvas implements Runnable {
	public BufferedImage image;
	// Variables
	private static final long serialVersionUID = 1L;
	private Thread thread;
	private boolean running = false;
	
	public KeyboardHandler Klistener = new KeyboardHandler(this);
	
	public LevelHandler level = new LevelHandler(this);
	// Variables
	public  BackgroundMusicPlayer bgMusicPlayer;
	public MenuHandler menu = new MenuHandler(this);////
	public GameState gs = GameState.Menu;///
	// Run when a new window is created
	public Window(String Title)	{
		JFrame frame = new JFrame(Title);
		
		// Set size
		frame.setSize(1920, 1080);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set logic 
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this);
	    getImage();
	}
	
	public void getImage() {
		try {
		    // Sử dụng đường dẫn bắt đầu từ tên package
		    image = ImageIO.read(getClass().getResourceAsStream("/map/wall.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		
	}

	// Start the game logic 
	public void start()	{
		running = true;
		thread = new Thread(this);
		thread.start();	
		bgMusicPlayer = new BackgroundMusicPlayer();
	    bgMusicPlayer.playBackgroundMusic("/soundeffects/backmusic.wav");
        bgMusicPlayer.setVolume(-10.0f); // Adjust volume if needed
	}
	
	// Stop the game logic
	public void stop()	{
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e)	{
			e.printStackTrace();
		}
		if (bgMusicPlayer != null) {
            bgMusicPlayer.stopBackgroundMusic();
        }
	}
	public void run()	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		while(running)	{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta > -1)	{
				tick();
				updates++;
				delta--;
			}
			Render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000)	{
				timer += 1000;
//				System.out.println("FPS: " + frames + " TICKS " + updates);
				frames = 0;
				updates = 0;
			}
		}
		
		stop();
	}
	
	// Update window
	public void tick() {
//		player.tick();
		level.tick();
//		if (gs == GameState.Game)	level.tick();
//		if (gs == GameState.Menu)	menu.tick();
	}
	
	// Paint onto the window
	public void Render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null)	{
			this.createBufferStrategy(2);
			bs = this.getBufferStrategy();
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	    if (image != null) {
	        g.drawImage(image, 0,0, null); // Thay đổi x và y là vị trí bạn muốn vẽ hình ảnh lên
	    }
		level.Render(g);	
//	    if (gs == GameState.Game)	level.Render(g);////
//		if (gs == GameState.Menu)	menu.Render(g);////
		bs.show();
		g.dispose();
	}
}