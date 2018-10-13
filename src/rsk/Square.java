package rsk;

import edu.princeton.cs.algs4.Draw;
import java.awt.Color;

final class Square implements Comparable<Square> {
	//the square is defined by the coordinates of its south-west corner.
	final int x; 
	final int y;
	int p; //content of this square in P tableau
	int q; //content of this square in Q tableau
	
	//Square() {}

	Square(int x, int y) {
	   this.x = x;
	   this.y = y;
	}
	
	Square(Square s) {
		   this.x = s.x;
		   this.y = s.y;
		}
	
	public void setP(int n) {
		this.p = n;
	}
	
	public void setQ(int n) {
		this.q = n;
	}
	
	/**
	 * draws the square and its P-content to the specified window.
	 * The difference of this function from drawP is that this version uses
	 *  the specified color.
	 */
	void draw(Draw dr, Color c){
    	double centerX = x + 0.5;
    	double centerY = y + 0.5;
    	dr.setPenColor(c);
    	dr.filledSquare(centerX, centerY, 0.5); 	
    	dr.setPenColor(Draw.BLACK);
    	dr.square(centerX, centerY, 0.5); 
    	dr.text(centerX, centerY, String.valueOf(p));
	}
	
	/**
	 * draws the square and its P-content to the specified window
	 */
	void drawP(Draw dr){
    	double centerX = x + 0.5;
    	double centerY = y + 0.5;
    	dr.setPenColor(Draw.WHITE);
    	dr.filledSquare(centerX, centerY, 0.5); 	
    	dr.setPenColor(Draw.BLACK);
    	dr.square(centerX, centerY, 0.5); 
    	dr.text(centerX, centerY, String.valueOf(p));
	}
	
	/**
	 * draws the square and its Q-content to the specified window
	 */
	void drawQ(Draw dr){
    	double centerX = x + 0.5;
    	double centerY = y + 0.5;
    	dr.setPenColor(Draw.WHITE);
    	dr.filledSquare(centerX, centerY, 0.5); 	
    	dr.setPenColor(Draw.BLACK);
    	dr.square(centerX, centerY, 0.5); 
    	dr.text(centerX, centerY, String.valueOf(q));
	}
	
	
    /**
     * Compares this square to another square and returns true if they are the same,
     * which happens only if this.x = other.x, this.y = other.y
     *
     * @param  other the other square
     * @return {@code true} if this square equals {@code other};
     *         {@code false} otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        Square that = (Square) other;
        if (this.x != that.x) return false;
        if (this.y != that.y) return false;
        return true;
    }

    /**
     * Returns an integer hash code for this square. The hash code depends only on x and y
     * @return an integer hash code for this square
     */
    @Override
    public int hashCode() {
        return 1299721 * x + y;
    }

    /**
     * Returns a string representation of this square.
     *
     * @return a string representation of this square, using the format
     *         {@code [x, y]}
     */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
    
    @Override
    public int compareTo(Square other) {
        if (other.x + other.y < x + y)  {
        	return 1;
        }  else if (other.x + other.y > x + y) {
        	return -1;
        } else if ( other.x < x ) {
        	return 1;
        } else if (other.x > x) {
        	return -1;
        }
        return 0;
    }

}

