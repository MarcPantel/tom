package examples;

import java.math.BigInteger;

import tom.library.enumerator.*;

public class TreeHole {

    public static void main(String args[]) {
        // examples for trees
        // TTree = Leaf(A) | Node(A,TTree,TTree)
        // A = a | b

        System.out.println("START");

        final Enumeration<A> holeEnum = Enumeration.singleton((A) Hole.make());
        final Enumeration<String> StringEnum = Enumeration.singleton("f").plus(Enumeration.singleton("g"));

        F<Enumeration<TTree>, Enumeration<TTree>> f = new F<Enumeration<TTree>, Enumeration<TTree>>() {
            public Enumeration<TTree> apply(final Enumeration<TTree> e) {
                return leafEnum(getLeafFunction(), holeEnum).plus(nodeEnum(getNodeFunction(), StringEnum, e).pay());
            }
        };

        Enumeration<TTree> treeEnum = Enumeration.fix(f);

        System.out.println("Enumerator for " + "TTree");
        for (int i = 0; i < 20; i++) {
        	TTree t = treeEnum.get(BigInteger.valueOf(i));
            System.out.println("Get " + i + "th term: " + t + " --> " + t.nbHole() + " holes") ;
        }
  
        final Enumeration<A> AEnum = Enumeration.singleton((A) new aa()).plus(Enumeration.singleton((A) new bb()));

        
        // g(*,*)
        
		Enumeration<TTree> l = leafEnum(getLeafFunction(), AEnum);
        /*
         Enumeration<TTree> gss = Enumeration.apply(Enumeration.apply(Enumeration.apply(Enumeration.singleton(getNodeFunction()), Enumeration.singleton("g")), l), l);
       
        System.out.println("Instantiate g(*,*)");
        for (int i = 0; i < 4; i++) {
        	TTree t = gss.get(BigInteger.valueOf(i));
            System.out.println("Get " + i + "th term: " + t);
        }
        */
        
    	TTree tss = treeEnum.get(BigInteger.valueOf(2));
        System.out.println("tss = " + tss);
        Enumeration<TTree> gss = instantiate(tss, Hole.make(), AEnum);
        System.out.println("Instantiate g(*,*)");
        for (int i = 0; i < 4; i++) {
        	TTree t = gss.get(BigInteger.valueOf(i));
            System.out.println("Get " + i + "th term: " + t);
        }
        
  
        
        
     // g(*,f(*,*)
        Enumeration<TTree> gsfss = Enumeration.apply(Enumeration.apply(
        		Enumeration.apply(Enumeration.singleton(getNodeFunction()), Enumeration.singleton("g")),
        		l), 
        		Enumeration.apply(Enumeration.apply(
                		Enumeration.apply(Enumeration.singleton(getNodeFunction()), Enumeration.singleton("f")),l),l)
        		);
            
        System.out.println("Instantiate g(*,f(*,*))");
        for (int i = 0; i < 8; i++) {
        	TTree t = gsfss.get(BigInteger.valueOf(i));
            System.out.println("Get " + i + "th term: " + t);
        }
        
        /*
        F<Enumeration<TTree>, Enumeration<TTree>> ff = new F<Enumeration<TTree>, Enumeration<TTree>>() {
        	public Enumeration<TTree> apply(final Enumeration<TTree> e) {
        		return Enumeration.apply(Enumeration.apply(Enumeration.apply(Enumeration.singleton(getNodeFunction()), Enumeration.singleton("g")), l), l);
        	}
        };
        */
        
        
    }

    private static Enumeration instantiate(TTree t, A hole, Enumeration<A> e) {
    	if(t instanceof Leaf) {
    		Leaf l = (Leaf)t;
    		if(l.elem == hole) {
    			return Enumeration.apply(Enumeration.singleton(getLeafFunction()), e);
    		} else {
    			System.out.println("strange term: " + l);
    			return null;
    		}
    	} else {
    		Node n = (Node)t;
    		Enumeration arg1 = instantiate(n.t1, hole, e);
    		Enumeration arg2 = instantiate(n.t2, hole, e);
    		return Enumeration.apply(Enumeration.apply(
    				Enumeration.apply(Enumeration.singleton(getNodeFunction()), Enumeration.singleton("f")),arg1),arg2);
    	}
    }
    
    private static F untypedGetNodeFunction() {
    	return new F() {
    		public Object apply(final Object head) {
    			return new F() {
    				public Object apply(final Object t1) {
    					return new F() {
    						public Object apply(final Object t2) {
    							return new Node((String)head, (TTree)t1, (TTree)t2);
    						}
    					};
    				}
    			};
    		}
    	};
    }
    
    /*
     * elem -> Leaf(elem)
     */
    private static F<A, TTree> getLeafFunction() {
    	return new F<A, TTree>() {
    		public TTree apply(final A elem) {
    			return new Leaf(elem);
    		}
    	};
    }

    /*
     * head -> t1 -> t2 -> Node(head, t1, t2)
     */
    private static F<String, F<TTree, F<TTree, TTree>>> getNodeFunction() {
    	return new F<String, F<TTree, F<TTree, TTree>>>() {
    		public F<TTree, F<TTree, TTree>> apply(final String head) {
    			return new F<TTree, F<TTree, TTree>>() {
    				public F<TTree, TTree> apply(final TTree t1) {
    					return new F<TTree, TTree>() {
    						public TTree apply(final TTree t2) {
    							return new Node(head, t1, t2);
    						}
    					};
    				}
    			};
    		}
    	};
    }
    
    private static Enumeration<TTree> leafEnum(F<A, TTree> leaf, Enumeration<A> AEnum) {
        return Enumeration.apply(Enumeration.singleton(leaf), AEnum);
    }

    private static Enumeration<TTree> nodeEnum(F<String, F<TTree, F<TTree, TTree>>> node, Enumeration<String> StringEnum, Enumeration<TTree> e) {
        return Enumeration.apply(Enumeration.apply(Enumeration.apply(Enumeration.singleton(node), StringEnum), e), e);
    }

    // examples for trees
    // TTree = Leaf(A) | Node(String,TTree,TTree)
    // A = Hole 
    // String = "f" | "g"
    private static abstract class TTree {
        public abstract int size();
        public abstract int nbHole();        
    }

    private static class Leaf extends TTree {

        private A elem;

        public Leaf(A e) {
            this.elem = e;
        }

        public String toString() {
        	return elem.toString();
        }

        public int size() {
            return 1 + elem.size();
        }
        
        public int nbHole() {
            return elem.nbHole();
        }
    }

    private static class Node extends TTree {

        protected String head;
        protected TTree t1, t2;

        public Node(String h, TTree t1, TTree t2) {
            this.head = h;
            this.t1 = t1;
            this.t2 = t2;
        }

        public String toString() {
            return head + "(" + t1 + "," + t2 + ")";
        }

        public int size() {
            return 1 + t1.size() + t2.size();
        }
        
        public int nbHole() {
            return t1.nbHole() + t2.nbHole();
        }
    }

    private static abstract class A {

        public abstract int size();
        
        public int nbHole() {
            return 0;
        }
    }

    private static class Hole extends A {
    	private Hole() {}
    	private static Hole instance = new Hole();;
    	public static Hole make() {
    		return instance;
    	}
    	
        public String toString() {
            return "*";
        }

        public int size() {
            return 0;
        }
        
        public int nbHole() {
            return 1;
        }
    }
    
    private static class aa extends A {

        public String toString() {
            return "a";
        }

        public int size() {
            return 0;
        }
    }

    private static class bb extends A {

        public String toString() {
            return "b";
        }

        public int size() {
            return 0;
        }
    }
}
