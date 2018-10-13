/**
 * 
 */
package rsk;

import java.util.TreeSet;
import java.util.ArrayList;

import edu.princeton.cs.algs4.Draw;


/**
 * @author vladislavkargin
 *
 */
public class TShape {
	//conceptual variables
	protected TreeSet<Square> squares; // A bag of squares which are inside of the region.
	//Variables for visualization
	protected Draw pDr; //the window to which we will draw P. 
	protected Draw qDr;
	
	public TShape() {
		squares = new TreeSet<Square>();
	}
    
	public void addSquare(Square s) {
		squares.add(s);
	}
	
	
	public void draw(int size) {
		if (pDr == null) {
			pDr = new Draw();
			pDr.setCanvasSize(size, size);
			qDr = new Draw();
			qDr.setCanvasSize(size, size);
			qDr.setLocationOnScreen(size, 1);
		} else {
			pDr.setCanvasSize(size, size);
			qDr.setCanvasSize(size, size);
		}
		prepareDrawFrames(pDr);
		prepareDrawFrames(qDr);
		// now we can draw
		for (Square s : squares) {
			s.drawP(pDr);
			s.drawQ(qDr);
		}
		pDr.show();
		qDr.show();
	}
	
	public void drawPath(ArrayList<Integer> path) {
		Square s;
		for (int i = 0; i < path.size(); i++) {
			s = new Square(path.get(i), i);
			s = squares.floor(s);
			s.draw(pDr, Draw.BOOK_LIGHT_BLUE);
		}
	}
	
	private void prepareDrawFrames(Draw dr) {
		int sizeX = 0;
		int sizeY = 0;
		for (Square s : squares) {
			if (s.x + 1> sizeX) {
				sizeX = s.x + 1;
			} 
			if (s.y + 1> sizeY) {
				sizeY = s.y + 1;
			}
		}
		int size = sizeX;
		if (size < sizeY) {
			size = sizeY;
		}
		dr.setXscale(-0.5, size + 0.5);
		dr.setYscale(-0.5, size + 0.5);
		dr.clear(Draw.LIGHT_GRAY);
		dr.setPenRadius(0.001);
		
		dr.setPenColor(Draw.WHITE);
		for (int i = 0; i < size; i++) {
			dr.line(i, 0, i, size);
		}
		for (int i = 0; i < size; i++) {
			dr.line(0, i, size, i);
		}
		
		dr.setPenColor(Draw.BLACK);
		dr.setPenRadius(0.005);
	}	
}
