/**
 * 
 */
package rsk;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
/**
 * @author vladislavkargin
 *
 */
public class TableauPair {
	private ArrayList<ArrayList<Integer>> tableauP;
	private ArrayList<ArrayList<Integer>> tableauQ; //this is an associated table, which is formed in RSK algorithm
	public TShape shape;
	
	/**
	 * Initializes an empty tableau
	 */
	public TableauPair() {
		this.tableauP= new ArrayList<ArrayList<Integer>>();
		this.tableauQ= new ArrayList<ArrayList<Integer>>();
		shape = new TShape();
	}
	
	/**
	 * initializes a tableau pair from two tables of numbers.  
	 * @param P tableau P
	 * @param Q tableau Q
	 */
	 
	public TableauPair(ArrayList<ArrayList<Integer>> P, ArrayList<ArrayList<Integer>> Q) {
		this.tableauP= new ArrayList<ArrayList<Integer>>();
		this.tableauQ= new ArrayList<ArrayList<Integer>>();
		shape = new TShape();
		//input data and create shape
		for (int i = 0; i < P.size(); i++) {
			ArrayList<Integer> row = new ArrayList<Integer>(P.get(i));
		  this.tableauP.add(row);
		  row = new ArrayList<Integer>(Q.get(i));
		  this.tableauQ.add(row);
		}
		this.calcShape();
	}
	
	public TableauPair(ArrayList<ArrayList<Integer>> P) {
		this(P, P); 
	}
	
	
	/**
	 * Makes a copy of another TableauPair
	 * 
	 * @param other
	 */
	public TableauPair(TableauPair other) {
		tableauP = new ArrayList<ArrayList<Integer>>();
		tableauQ =  new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row = new ArrayList<Integer>();
		for (int i = 0; i < other.tableauP.size(); i++) {
			row = new ArrayList<Integer>(other.tableauP.get(i));
			tableauP.add(row);
			row = new ArrayList<Integer>(other.tableauQ.get(i));
			tableauQ.add(row);
		}
		this.calcShape();
	}
	
	/**
	 * Calculates shape from the current state of the tableau
	 */
	public void calcShape(){
		shape = new TShape();
		ArrayList<Integer> rowP, rowQ;
		for (int i = 0; i < tableauP.size(); i++) {
			rowP = new ArrayList<Integer>(tableauP.get(i));
			rowQ = new ArrayList<Integer>(tableauQ.get(i));
			  for (int j = 0; j < rowP.size(); j++) {
				  Square s = new Square(j, i);
				  s.setP(rowP.get(j));
				  s.setQ(rowQ.get(j));
				  shape.addSquare(s);
			  }
		}
	}
	/**
	 * Inserts integer x into a tableau by bumping appropriate integers up.
	 * Preserves the main property of tableaux. 
	 * 
	 * @param x an integer to insert, should be different from the integers currently in the tableau
	 * @return the path of the insertion (position at which x and bumped integers were inserted).
	 */
	 
	private ArrayList<Integer>  insert(int x) {
		ArrayList<Integer> insertionPath = new ArrayList<Integer>();
		ArrayList<Integer> row;
		//corner case
		if (tableauP.isEmpty()) {
			row = new ArrayList<Integer>();
			row.add(x);
			tableauP.add(row);
			insertionPath.add(0);
		} else {	
		     Integer y = x; //the integer that is going to be inserted in a current iteration.
		     int r = 0; //row in which we are pushing the number;
		     while (y != null) {
		    	 //StdOut.println(" r = " + r);
		    	 if (r >= tableauP.size()) {
		    		 row = new ArrayList<Integer>();
		    		 row.add(y);
		    		 tableauP.add(row);
		    		 insertionPath.add(0);
		    		 break;
		    	 } else {
		    		 row = tableauP.get(r);
		    		 Boolean bumpOccured = false;
		    		 for (int i = 0; i < row.size(); i++) {
		    			 if (row.get(i) > y) {
		    				 bumpOccured = true;
		    				 //StdOut.println(" Bumped occured ");
		    				 int y1 = row.get(i);
		    				 row.set(i, y);
		    				 y = y1;
		    				 r++;
		    				 insertionPath.add(i);
		    				 break; //going to the next iteration of the while loop
		    			 }		    			 
		    		 }
		    		 if (!bumpOccured) {
		    			 insertionPath.add(row.size());
		    			 row.add(y);
		    			 y = null; //breaking out of the while loop
		    		 }
		    	 }
		     }
		}
		return insertionPath;
	}
	
	/**
	 * Inserts x in tableau P and then adds n to tableau Q. A basic step of the RSK algorithm
	 * 
	 * @param x an integer to insert in P
	 * @param n an integer to add to Q
	 * @return the insertion path for x
	 */
	
	public ArrayList<Integer>  insertRSK(int x, int n) {
		ArrayList<Integer> row;
		ArrayList<Integer> insertionPath = insert(x);
		//StdOut.println("Insertion path is " + insertionPath);
		
		int r = insertionPath.size();
		if (tableauQ.isEmpty()) {
			row = new ArrayList<Integer>();
			row.add(n);
			tableauQ.add(row);
			return insertionPath;
		}
        if (r > tableauQ.size()) {
			row = new ArrayList<Integer>();
			row.add(n);
			tableauQ.add(row);
        } else {
        	row = tableauQ.get(r - 1);
        	row.add(n);
        }
		calcShape();
		return insertionPath;
	}
	/**
	 * multiplication of two tableaux, as defined in the book by Fulton.
	 * The multiplication is actually was define by Fulton for P tableaux. 
	 * The definition for Q parts was chosen arbitrarily by me. (and in fact the
	 * associativity of the multiplication does not hold for Q parts)
	 * 
	 * @param other
	 * @return
	 */
	
	public TableauPair times(TableauPair other) {
		TableauPair prod = new TableauPair(this);
		ArrayList<Integer> rowP = new ArrayList<Integer>();
		ArrayList<Integer> rowQ = new ArrayList<Integer>();
		int N = other.tableauP.size();
		for (int i = 0; i < N; i++) {
			rowP = new ArrayList<Integer>(other.tableauP.get(N - 1 - i));
			rowQ = new ArrayList<Integer>(other.tableauQ.get(N - 1 - i));
			//StdOut.println("rowP = " + rowP);
			for (int j = 0; j < rowP.size(); j++) {
				/* This is also interesting but the algorithm in Fulton's book is different
				int p = rowP.get(rowP.size() - j - 1);
				int q = rowP.get(rowQ.size() - j - 1);
				*/
				int p = rowP.get(j);
				int q = rowQ.get(j);
				prod.insertRSK(p, q);
			}
		}
		return prod;
	}

	/**
	 * For testing methods
	 * @param args
	 */
	public static void main(String[] args) {
		/* First construction (by using an array)
		ArrayList<ArrayList<Integer>> T = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row = new ArrayList<Integer>();
		row.add(0);
		row.add(2);
		T.add(row);
		row = new ArrayList<Integer>();
		row.add(1);
		T.add(row);
		for (int i = 0; i < T.size(); i++) {
		   StdOut.println(T.get(i));
		}
		Tableau tbl = new Tableau(T);
		tbl.shape.draw(400);
		*/
		TableauPair tbl = new TableauPair();
		ArrayList<Integer> path = new ArrayList<Integer>();
		/*
	    int N = 30;
	    int[] permutation = StdRandom.permutation(N);
	    for (int i = 0; i < N; i++) {
	    	//tbl.insertRSK(((N - i)^2 % N) + 1, i + 1);
	    	//tbl.insertRSK(((N - i) * 1299721 % N) + 1, i + 1);
	    	path = tbl.insertRSK(permutation[i], i);
	    }	    
	    */
		tbl.insertRSK(1, 0);
		tbl.insertRSK(1, 1);
		tbl.insertRSK(0, 2);
		for (int i = 0; i < tbl.tableauP.size(); i++) {
			   StdOut.println(tbl.tableauP.get(i));
			}
		for (int i = 0; i < tbl.tableauQ.size(); i++) {
			   StdOut.println(tbl.tableauQ.get(i));
			}
		tbl.shape.draw(600);
		tbl.shape.drawPath(path);
	}

}
