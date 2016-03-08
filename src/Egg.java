import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/*
 *  �йص��������Ϣ
 *  @author ZSY
 **/

public class Egg {
	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	
	//��ʼλ�ò��������
	private static Random r = new Random();
	//��ɫ
	private Color color = Color.PINK;

	
	//�˶�ʱ��λ��
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	//��ʼʱ��λ��
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
	}
	
	//�õ���������ڽ�����
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2; //2~2+Yard.ROWS-1,��Χ
		this.col = r.nextInt(Yard.COLS);
	}
	
	//����һ������
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	
	public void draw(Graphics g) { //������ɫ�仯
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h); //��Բ�ε���ɫ��䣬�õ����Բ��
		g.setColor(c);
		//������ɫ���н���
		if(color == Color.YELLOW){
			color = Color.RED;
		}else if(color == Color.RED){
			color = Color.BLUE;
		}
		else color = Color.YELLOW;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
}
