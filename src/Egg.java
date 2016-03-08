import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/*
 *  有关蛋的相关信息
 *  @author ZSY
 **/

public class Egg {
	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	
	//初始位置产生随机数
	private static Random r = new Random();
	//颜色
	private Color color = Color.PINK;

	
	//运动时的位置
	public Egg(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	//初始时在位置
	public Egg() {
		this(r.nextInt(Yard.ROWS-2) + 2, r.nextInt(Yard.COLS));
	}
	
	//让蛋随机出现在界面上
	public void reAppear() {
		this.row = r.nextInt(Yard.ROWS-2) + 2; //2~2+Yard.ROWS-1,范围
		this.col = r.nextInt(Yard.COLS);
	}
	
	//返回一个矩形
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	
	public void draw(Graphics g) { //蛋的颜色变化
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h); //用圆形的颜色填充，让蛋变成圆的
		g.setColor(c);
		//三种颜色进行交替
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
