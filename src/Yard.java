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
 * ��������̰���ߵĻ����
 * @author ZSY
 */
public class Yard extends JFrame {

	PaintThread paintThread = new PaintThread();  //���̻߳�ͼ
	private boolean gameOver = false; //��Ϸ�Ƿ����
	
	/**
	 * ���� ���ƽ����С
	 */
	public static final int ROWS = 30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 15;
	
	//������ʾ������
	private Font fontGameOver = new Font("����", Font.BOLD, 50);
	
	//����
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	
	/* һ��ʼҪ�ɵ����飬д�����������main��������࣬���е�this����Yard����� */
	public void launch() {
		this.setLocation(90, 20);  //�����λ��
		this.setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		
		//�رմ���
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
	
	@Override //�������ֻ��Ϊ����д����ķ���ʱ���ñ�����֪����������֤�Ƿ��Ǹ����еķ����������Գ䵱ע��
	public void paint(Graphics g) {
		/* �������� */
		Color c = g.getColor();
		g.setColor(Color.pink);
		
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE); //����������
		
		g.setColor(Color.white);
		g.setFont(new Font("����",Font.ITALIC,15));
		g.drawString("Ŀǰ����: " + score, 10, 60);
		g.drawString("Tips��ÿ��һ����5�֣�����o((>��<))o~~~" , 10, 80);
		
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("game over!", 90, 170);
			
			//���ͼƬ��Ϊgame over
			Toolkit tool = this.getToolkit();
	        Image image = tool.getImage("../0.jpg");
	        g.drawImage(image, 125, 200,image.getWidth(this),image.getHeight(this), this);
	        g.setFont(new Font("����",Font.ITALIC,15));
	        g.drawString("��F2�������¿�ʼ��ϷӴ~(����������)~*",90,200);
			paintThread.pause();
		}
		
		g.setColor(c);
		
		s.eat(e);
		e.draw(g); //draw��������д��paint�������棬paint���������������
		s.draw(g);
		
		
	}
	
	@Override
	public void update(Graphics g) { //��ΪҪ�������������Ҫ�ã���paint()��������һ������Ĺ�ϵ
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
				
				try { //�쳣��׽
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() { //���¿�ʼ
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
			if(key == KeyEvent.VK_F2) {  //�������̣������F2����ô���¿�ʼ
				paintThread.reStart();
			}
			s.keyPressed(e);
		}
		
	}
	
	/**
	 * �õ����õķ���
	 * @return ����
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * �������õķ���
	 * @param score ����
	 */
	
	public void setScore(int score) {
		this.score = score;
	}

}
