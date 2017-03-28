import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 50;
	private static final int GRID_Y = 40;
	private static final int INNER_CELL_SIZE = 30;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;
	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];
	int [][] mines = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	boolean [][] visible = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	int [][]aroundmines = new int [TOTAL_COLUMNS][TOTAL_ROWS];
	boolean [][] flag = new boolean [TOTAL_COLUMNS][TOTAL_ROWS];
	Random numRandom =  new Random();
	int neighborcount = 0;
	String[][] neighborNum = new  String [TOTAL_COLUMNS][TOTAL_ROWS];
	public int totalColumns(){
		return TOTAL_COLUMNS;
	}
	public int totalRows(){
		return TOTAL_ROWS;
	}
	
	public MyPanel() {   //This is the constructor... this code runs first to initialize
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		for (int x = 0; x < TOTAL_COLUMNS; x++) {   // Paint the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if (numRandom.nextInt(100)<15){
					mines[x][y] = 1;
					}else{
						mines[x][y]=0;
					}
				flag[x][y] = false;
				visible[x][y] = false;
				colorArray[x][y] = Color.white;
				}
			}
		for (int x=0;x<TOTAL_COLUMNS;x++){
			for (int y=0;y<TOTAL_ROWS;y++){
		if (mines[x][y] != 1){
			if (x>0 && y>0 && mines[x-1][y-1]==1){//up left
				neighborcount++;
			}
			if (y>0 && mines[x][y-1]==1){//up 
				neighborcount++;
			}
			if ( y>0 && x<mines.length-1 && y<mines.length && mines[x+1][y-1]==1){//up right
				neighborcount++;
			}
			if (x<mines.length-1 && mines[x+1][y]==1){//right
				neighborcount++;
			}
			if (x<mines.length-1 && y>0 && mines[x+1][y-1]==1){//bottom right
				neighborcount++;
			}
			if (y<mines.length-1 && mines[x][y+1]==1){//bottom 
				neighborcount++;
			}
			if (x>0 && y<mines.length-1 && mines[x-1][y+1]==1){//bottom left
				neighborcount++;
			}
			if (x>0 && mines[x-1][y]==1){//left
				neighborcount++;
			}
			aroundmines[x][y] = neighborcount;
			if (neighborcount==0){
					neighborNum[x][y] = "";}
				else
					neighborNum[x][y] = String.valueOf(aroundmines[x][y]);
			}
			else {
				neighborNum[x][y] = "";
			}
		}
	}

		}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x1, y1, width + 1, height + 1);

		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS)));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
			}
		}
	}
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);

		if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 1) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

	/*public void aroundMines(){
        for(int f=0;f<numMines;f++){
            for(int c=0;c<numMines;c++){
                if(mines[f][c]==9){
                    for(int f2=f-1;f2<=f+1;f2++){
                        for(int c2=c-1;c2<=c+1;c2++){
                            if(f2>=0 && f2<numMines && c2>=0 && c2<numMines && mines[f2][c2]!=9)
                                mines[f2][c2]++;
                        }
                      }
                   }
                }
            }
        }*/
}
