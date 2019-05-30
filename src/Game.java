import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {
    
	private Display display;
	public int width, height;
	public String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;
	
	private BufferedImage testImage,testImage2,testImage3;
	
	private KeyManager keyManager;
	
	public Game(String title, int width, int height){
		this.width = width;
		this.height = height;
		this.title = title;
		keyManager = new KeyManager();
	}

	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);
		
		testImage = ImageLoader.loadImage("/textures/background2.png");
		testImage2= ImageLoader.loadImage("/textures/ball2.png");
		testImage3= ImageLoader.loadImage("/textures/player.png");
	}
	
	int y=500;
	private void tick(){

			if( y>=280)	y=y-2;
		
	}
	
	

	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		
		
		g.drawImage(testImage, 20, 20, null);
		g.drawImage(testImage2, 465, y, 120,120,null);
		g.drawImage(testImage3,200,300,400,400,null);
		
		//g.drawImage(testImage3, 20, 20, null);
		//g.drawImage(testImage2, 465, 550->300, 150,150,null);
		//change of y
		//g.drawImage(img, x, y, width, height, observer)
		
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		
		while(running){
			tick();
			render();
		}
		
		stop();
		
	}
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}