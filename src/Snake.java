import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
/**
 * 代表蛇
 * @author ZSY
 */
 
public class Snake {
	
	private Node head = null;
	private Node tail = null;
	private int size = 0;
	
	private Node n = new Node(20, 30, Dir.L);
	private Yard y;
	
	public Snake(Yard y) {
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
	
	//从尾巴加或从头加都可以  这里写出了两种情况
	// 从尾加
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L :
			node = new Node(tail.row, tail.col + 1, tail.dir);  //往左动，就是列+1，因为是加到尾巴上，是在蛇尾后面加
			break;
		case U :
			node = new Node(tail.row + 1, tail.col, tail.dir);
			break;
		case R :
			node = new Node(tail.row, tail.col - 1, tail.dir);
			break;
		case D :
			node = new Node(tail.row - 1, tail.col, tail.dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size ++;
	}
	
	// 从头加
	public void addToHead() {
		Node node = null;
		switch(head.dir) { 
		case L :
			node = new Node(head.row, head.col - 1, head.dir);  //往左动，就是列-1，因为是加到头上，是在蛇头的前面加
			break;
		case U :
			node = new Node(head.row - 1, head.col, head.dir);
			break;
		case R :
			node = new Node(head.row, head.col + 1, head.dir);
			break;
		case D :
			node = new Node(head.row + 1, head.col, head.dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size ++;
	}
	
	public void draw(Graphics g) { //画出蛇
		if(size <= 0) return;
		move();
		for(Node n = head; n != null; n = n.next) {
			n.draw(g);
		}
	}
	
	private void move() {  //蛇身的移动
		addToHead();  //头加一个
		deleteFromTail();   //尾巴减一个
		checkDead(); //检查蛇死了没
	}
 
	private void checkDead() {   //检查蛇是否死亡
		if(head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS)  {
			//若发生了以上几种情况，那么 蛇就死了
			y.stop();
		}
		
		for(Node n = head.next; n != null; n = n.next) {
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
	}

	//从尾巴哪里剪掉一个矩形
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev; //尾巴变成尾巴的前一个，即剪掉最后一个尾巴 
		tail.next = null;
		
	}

	private class Node {
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int row , col;
		Dir dir = Dir.L;
		Node next = null;
		Node prev = null;
		
		Node(int row, int col, Dir dir) {
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		void draw(Graphics g) { //蛇的形状
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			g.setColor(c);
		}
	}
	
	public void eat(Egg e) {  //吃了一个蛋 蛇的情况
		if(this.getRect().intersects(e.getRect())) { //以蛋的边框与蛇的头的边框是否交叉来判断蛇是不是碰到了蛋
			e.reAppear();  //吃了一个蛋之后，蛋会在另一个地方出现，reAppear()是egg.java里的函数
			this.addToTail();  //从头开始加
			y.setScore(y.getScore() + 5); //吃了加5分
		}
	}
	
	private Rectangle getRect() {  //就是返回当前蛋或者蛇所在的矩形。
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}
	
	
	public void keyPressed(KeyEvent e) {  //这个就是监听到键盘的上下左右键
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.dir != Dir.R)
				head.dir = Dir.L;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Dir.D)
				head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Dir.L)
				head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Dir.U)
				head.dir = Dir.D;
			break;
		}
	}
}























