package rsk;
import edu.princeton.cs.algs4.StdRandom;

class Utility {
	
	private Utility(){}
	
	static TableauPair horizontalTableau(int N) {
		TableauPair tp = new TableauPair();
		for (int i = 0; i < N; i++) {
			tp.insertRSK(i, i);
		}
		return tp;
	}
	
	static TableauPair verticalTableau(int N) {
		TableauPair tp = new TableauPair();
		for (int i = 0; i < N; i++) {
			tp.insertRSK(N - i - 1, i);
		}
		return tp;
	}
	
	static TableauPair randomTableau(int N) {
		TableauPair tp = new TableauPair();
	    int[] permutation = StdRandom.permutation(N);
	    for (int i = 0; i < N; i++) {
	    	tp.insertRSK(permutation[i], i);
	    }	
		return tp;
	}

	/**
	 * For testing methods.
	 */
	public static void main(String[] args) {
		TableauPair tpH, tpV, tpRand, prod1, prod2;
		int N = 10;
		tpH = horizontalTableau(N);
		tpV = verticalTableau(N);
		tpRand = randomTableau(N);
		prod1 = (tpH.times(tpRand)).times(tpV);
		prod1.shape.draw(500);
		prod2 = tpH.times(tpRand.times(tpV));
		prod2.shape.draw(500);
	}
}
