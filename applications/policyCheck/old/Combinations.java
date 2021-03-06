import java.util.*;

public class Combinations {

	private int n;
	private int p;
	private ArrayList<ArrayList<Integer>> combination = new ArrayList<ArrayList<Integer>>();
	
  // An n "super-Combinations" (not unique elements) of a p size set 
	public Combinations( int n, int p) {
		this.n = n;
		this.p = p;
    this.run();
	}

  public int getN(){
    return n;
  }

  public int getP(){
    return p;
  }

  public ArrayList<ArrayList<Integer>>  getCombination(){
    return combination;
  }

	private void run(){
    //     System.out.println("RUN::  "+n+"-"+p);
		if (p==0){     // one element set
			ArrayList<Integer> cbn=new ArrayList<Integer>();
			for (int j = 0; j < n; j++) {
				cbn.add(p);
			}
			combination.add(cbn);
		} else {     // more than one element set
      if (n==1){     // one element set
        for (int i = 0; i <= p; i++) {
          ArrayList<Integer> cbn=new ArrayList<Integer>();
          cbn.add(i);
          combination.add(cbn);	
        } 
      }else{        
        ArrayList<Integer> cbn=new ArrayList<Integer>();
        for (int j = 0; j < n; j++) {
          cbn.add(p);
        }
        combination.add(cbn);

        // Start recursive call
        for (int j = 0; j < n; j++) {
          Combinations siblings = new Combinations(j+1,p-1);	
          //           siblings.run();

          ArrayList<ArrayList<Integer>> subCombinations=siblings.combination;
          // size of combination
          int size = subCombinations.get(0).size();
          
          for (ArrayList<Integer> name : subCombinations) {
            ArrayList<Integer> cbnAux=new ArrayList<Integer>();
            // fill to get to size "n" from size "size"
            for (int i = 0; i < n-size; i++) {
              cbnAux.add(p);
            }
            cbnAux.addAll(name);
            combination.add(cbnAux);
          }
        }
      }
    }
	}
	
}
