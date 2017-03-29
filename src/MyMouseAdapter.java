import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame) c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			//Do nothing
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
		case 1:		//Left mouse button
			Component c = e.getComponent();
			while (!(c instanceof JFrame)) {
				c = c.getParent();
				if (c == null) {
					return;
				}
			}
			JFrame myFrame = (JFrame)c;
			MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
			Insets myInsets = myFrame.getInsets();
			int x1 = myInsets.left;
			int y1 = myInsets.top;
			e.translatePoint(-x1, -y1);
			int x = e.getX();
			int y = e.getY();
			myPanel.x = x;
			myPanel.y = y;
			int gridX = myPanel.getGridX(x, y);
			int gridY = myPanel.getGridY(x, y);
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						
						//Released the mouse button on the same cell where it was pressed
						//On the grid other than on the left column and on the top row:
						if (myPanel.flag[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == false){
							
							if(myPanel.mines[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == 1)
							{
								for(int i=0; i < myPanel.totalColumns(); i++){
									for (int j=0; j< myPanel.totalRows(); j++){
										if (myPanel.mines[i][j] == 1){
											myPanel.colorArray[i][j] = Color.black;
											myPanel.visible[i][j]= true;
										}	
									}
								}
							}
							else{
								if(myPanel.visible[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = true){
									myPanel.aroundmines[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = myPanel.neighborcount;
									if (myPanel.neighborcount == 0){
										myPanel.neighborNum[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = "";}
										else{
								
									}
									JLabel 	numbers = new JLabel(Integer.toString(myPanel.neighborcount));
									numbers.setBounds(51 + 30*x, 41+30*y, 30, 30);
									myPanel.add(numbers);
									
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.GRAY;}
								
							}
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			Component c3 = e.getComponent();
			while (!(c3 instanceof JFrame)) {
				c3 = c3.getParent();
				if (c3 == null) {
					return;
				}
			}

			JFrame rightClickFrame = (JFrame) c3;
			MyPanel rightClickPanel = (MyPanel) rightClickFrame.getContentPane().getComponent(0);
			Insets rightClickInsets = rightClickFrame.getInsets();
			int xR = rightClickInsets.left;
			int yR = rightClickInsets.top;
			e.translatePoint(-xR, -yR);
			int xR1 = e.getX();
			int yR1 = e.getY();
			rightClickPanel.mouseDownGridX = rightClickPanel.getGridX(xR1, yR1);
			rightClickPanel.mouseDownGridY = rightClickPanel.getGridY(xR1, yR1);
			rightClickPanel.x = xR1;
			rightClickPanel.y = yR1;
		
			
			int rightClickGridX = rightClickPanel.getGridX(xR1, yR1);
			int rightClickGridY = rightClickPanel.getGridY(xR1, yR1);
			
			
			if ((rightClickPanel.mouseDownGridX == -1) || (rightClickPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((rightClickGridX == -1) || (rightClickGridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((rightClickPanel.mouseDownGridX != rightClickGridX) || (rightClickPanel.mouseDownGridY != rightClickGridY)) {
						//Released the mouse button on a different cell where it was pressed
						//Do nothing
					} else {
						//Released the mouse button on the same cell where it was pressed

								if(rightClickPanel.visible[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY]!= true)//does not let to marked revealed spaces
								{
									
										if(rightClickPanel.flag[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY]== false)//paints the cell if it doesn't have a bomb
										{
											rightClickPanel.colorArray[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY] = Color.RED;
											rightClickPanel.flag[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY] = true;
											
											
										}else
											{
											rightClickPanel.flag[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY] = false;
											rightClickPanel.colorArray[rightClickPanel.mouseDownGridX][rightClickPanel.mouseDownGridY] = Color.WHITE;
										}
								}
							
						}
					}
			}
					rightClickPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
}
