import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 这个类代表贪吃蛇的活动场所
 * @author ZSY
 */
public class Yard extends JFrame {

	PaintThread paintThread = new PaintThread();  //多线程画图
	private boolean gameOver = false; //游戏是否结束
	
	/**
	 * 行列 控制界面大小
	 */
	public static final int ROWS = 30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 15;
	
	//设置显示字属性
	private Font fontGameOver = new Font("楷体", Font.BOLD, 50);
	
	//分数
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	
	/* 一开始要干的事情，写在外面可以让main函数更简洁，所有的this都是Yard这个类 */
	public void launch() {
		this.setLocation(90, 20);  //界面的位置
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		
		//关闭窗口
		this.addWindowListener(new WindowAdapter() {
			@Override   
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}	
		});
		this.setVisible(true);
		
		this.addKeyListener(new KeyMonitor());
		
		new Thread(paintThread).start();
	}
	
	public static void main(String[] args) {
		new Yard().launch();
	}
	
	public void stop() {
		gameOver = true;
	}
	
	@Override //这条语句只是为了重写父类的方法时，让编译器知道，可以验证是否是父类中的方法。还可以充当注释
	public void paint(Graphics g) {
		/* 界面的设计 */
		Color c = g.getColor();
		g.setColor(Color.pink);
		
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE); //填充矩形区域
		
		g.setColor(Color.white);
		g.setFont(new Font("楷体",Font.ITALIC,15));
		g.drawString("目前分数: " + score, 10, 60);
		g.drawString("Tips：每吃一个加5分，加油o((>ω<))o~~~" , 10, 80);
		
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("game over!", 90, 170);
			
			//添加图片作为game over
			Toolkit tool = this.getToolkit();
	        Image image = tool.getImage("../0.jpg");
	        g.drawImage(image, 125, 200,image.getWidth(this),image.getHeight(this), this);
	        g.setFont(new Font("楷体",Font.ITALIC,15));
	        g.drawString("按F2可以重新开始游戏哟~(——▽——)~*",90,200);
			paintThread.pause();
		}
		
		g.setColor(c);
		
		s.eat(e);
		e.draw(g); //draw函数可以写在paint函数里面，paint是最外面的容器。
		s.draw(g);
		
		
	}
	
	@Override
	public void update(Graphics g) { //因为要绘制组件，所以要用，与paint()是类似于一个连体的关系
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,  null);
	}
	
	private class PaintThread implements Runnable {
		private boolean running = true;
		private boolean pause = false;
		public void run() {
			while(running) {
				if(pause) continue; 
				else repaint();
				
				try { //异常捕捉
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() { //重新开始
			this.pause = false;
			s = new Snake(Yard.this);
			gameOver = false;
		}
		
		public void gameOver() {
			running = false;
		}
		
	}
	
	private class KeyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_F2) {  //监听键盘，如果是F2，那么重新开始
				paintThread.reStart();
			}
			s.keyPressed(e);
		}
		
	}
	
	/**
	 * 拿到所得的分数
	 * @return 分数
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * 设置所得的分数
	 * @param score 分数
	 */
	
	public void setScore(int score) {
		this.score = score;
	}

}
