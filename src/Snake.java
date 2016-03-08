import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
/**
 * ������
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
	
	
	//��β�ͼӻ��ͷ�Ӷ�����  ����д�����������
	// ��β��
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L :
			node = new Node(tail.row, tail.col + 1, tail.dir);  //���󶯣�������+1����Ϊ�Ǽӵ�β���ϣ�������β�����
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
	
	// ��ͷ��
	public void addToHead() {
		Node node = null;
		switch(head.dir) { 
		case L :
			node = new Node(head.row, head.col - 1, head.dir);  //���󶯣�������-1����Ϊ�Ǽӵ�ͷ�ϣ�������ͷ��ǰ���
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
	
	public void draw(Graphics g) { //������
		if(size <= 0) return;
		move();
		for(Node n = head; n != null; n = n.next) {
			n.draw(g);
		}
	}
	
	private void move() {  //������ƶ�
		addToHead();  //ͷ��һ��
		deleteFromTail();   //β�ͼ�һ��
		checkDead(); //���������û
	}
 
	private void checkDead() {   //������Ƿ�����
		if(head.row < 2 || head.col < 0 || head.row > Yard.ROWS || head.col > Yard.COLS)  {
			//�����������ϼ����������ô �߾�����
			y.stop();
		}
		
		for(Node n = head.next; n != null; n = n.next) {
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
	}

	//��β���������һ������
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev; //β�ͱ��β�͵�ǰһ�������������һ��β�� 
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
		
		void draw(Graphics g) { //�ߵ���״
			Color c = g.getColor();
			g.setColor(Color.BLUE);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			g.setColor(c);
		}
	}
	
	public void eat(Egg e) {  //����һ���� �ߵ����
		if(this.getRect().intersects(e.getRect())) { //�Ե��ı߿����ߵ�ͷ�ı߿��Ƿ񽻲����ж����ǲ��������˵�
			e.reAppear();  //����һ����֮�󣬵�������һ���ط����֣�reAppear()��egg.java��ĺ���
			this.addToTail();  //��ͷ��ʼ��
			y.setScore(y.getScore() + 5); //���˼�5��
		}
	}
	
	private Rectangle getRect() {  //���Ƿ��ص�ǰ�����������ڵľ��Ρ�
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}
	
	
	public void keyPressed(KeyEvent e) {  //������Ǽ��������̵��������Ҽ�
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























