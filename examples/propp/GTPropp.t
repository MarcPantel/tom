/* From Propositional prover by H. Cirstea */
package Propp;

import aterm.*;
import aterm.pure.*;
import java.util.*;
import jtom.runtime.*;
import Propp.seq.*;
import Propp.seq.types.*;
import java.io.*;
import antlr.CommonAST;

public class GTPropp extends Propp {

	private Factory factory;
	private GenericTraversal traversal;

	// ------------------------------------------------------------  
	%include { seq.tom }
	// ------------------------------------------------------------  

	public GTPropp() {
		this(new Factory(new PureFactory()));
		this.traversal = new GenericTraversal();
	}
		
	public GTPropp(Factory factory) {
		this.factory = factory;
		this.traversal = new GenericTraversal();
	}
	
	//{{{ public void run(String query)
	public void run(String query) {
		Sequent initSeq = makeQuery(query);
		Proof initP = `hyp(initSeq);

		long startChrono = System.currentTimeMillis();
		Proof proofTerm = solve(initP);
		long stopChrono = System.currentTimeMillis();

		System.out.println("Proof term = " + proofTerm);
		ListPair tex_proofs = `concPair();
		/*
		%match(ListProof proofTerm) {
			concProof(_*,p,_*) -> {
				tex_proofs.add(proofToTex(p));
			}
		}
		*/
		System.out.println("Is input proved ? " + isValid(proofTerm));

		tex_proofs = `concPair(pair(1,proofToTex(proofTerm)));

		System.out.println("Build LaTeX");
		write_proof_latex(tex_proofs,"proof.tex");

		System.out.println("Latex : " + tex_proofs);
		System.out.println("res found in " + (stopChrono-startChrono) + " ms");

	}
	//}}}

	Collection rules_appl = new HashSet();
	Collection result = new HashSet();

	public Proof solve(Proof init) {
		Proof res = (Proof)replace.apply(init);
		if (res != init) {
			res = solve(res);
		}
		return res;
	}

	Replace1 replace = new Replace1() {
		public ATerm apply(ATerm pt) {
			if (pt instanceof Proof) {
				Proof subject = (Proof)pt;
				%match(Proof subject) {

					// {{{	negd
					hyp(seq(concPred(X*),concPred(Y*,neg(Z),R*))) -> {
						Proof prod = `hyp(seq(concPred(X*,Z),concPred(Y*,R*)));
						return `rule(
							negd,
							seq(concPred(X*),concPred(Y*,mark(neg(Z)),R*)),
							concProof(prod));
					}
					// }}}

					//{{{ disjd
					hyp(seq(concPred(X*),concPred(Y*,vee(Z,R),S*))) -> {
						Proof prod = `hyp(seq(concPred(X*),concPred(Y*,Z,R,S*)));
						return `rule(
							disjd,
							seq(concPred(X*),concPred(Y*,mark(vee(Z,R)),S*)),
							concProof(prod));
					}
					//}}}			

					//{{{ impd
					hyp(seq(concPred(X*),concPred(S*,impl(Y,Z),R*))) -> {
						Proof prod = `hyp(seq(concPred(X*,Y),concPred(S*,Z,R*)));
						return `rule(
							impd,
							seq(concPred(X*),concPred(S*,mark(impl(Y,Z)),R*)),
							concProof(prod));
					}
					//}}}

					//{{{ negg
					hyp(seq(concPred(X*,neg(Y),S*),concPred(Z*))) -> {
						Proof prod = `hyp(seq(concPred(X*,S*),concPred(Y,Z*)));
						return `rule(
							negg,
							seq(concPred(X*,mark(neg(Y)),S*),concPred(Z*)),
							concProof(prod));
					}
					//}}}

					//{{{ conjg
					hyp(seq(concPred(X*,wedge(Y,Z),S*),concPred(R*))) -> {
						Proof prod = `hyp(seq(concPred(X*,Y,Z,S*),concPred(R*)));
						return `rule(
							conjg,
							seq(concPred(X*,mark(wedge(Y,Z)),S*),concPred(R*)),
							concProof(prod));
					}
					//}}}

					//{{{ disjg
					hyp(seq(concPred(X*,vee(Y,Z),S*),concPred(R*))) -> {
						Proof s1 = `hyp(seq(concPred(X*,Y,S*),concPred(R*)));
						Proof s2 = `hyp(seq(concPred(X*,Z,S*),concPred(R*)));
						return `rule(
							disjg,
							seq(concPred(X*,mark(vee(Y,Z)),S*),concPred(R*)),
							concProof(s1,s2));
					}
					//}}}

					//{{{ conjd
					hyp(seq(concPred(R*),concPred(X*,wedge(Y,Z),S*))) -> {
						Proof s1 = `hyp(seq(concPred(R*),concPred(X*,Y,S*)));
						Proof s2 = `hyp(seq(concPred(R*),concPred(X*,Z,S*)));
						return `rule(
							conjd,
							seq(concPred(R*),concPred(X*,mark(wedge(Y,Z)),S*)),
							concProof(s1,s2));
					}
					//}}}

					//{{{ impg
					hyp(seq(concPred(X*,impl(Y,Z),S*),concPred(R*))) -> {
						Proof s1 = `hyp(seq(concPred(X*,S*),concPred(R*,Y)));
						Proof s2 = `hyp(seq(concPred(X*,Z,S*),concPred(R*)));
						return `rule(
							impg,
							seq(concPred(X*,mark(impl(Y,Z)),S*),concPred(R*)),
							concProof(s1,s2));
					}
					//}}}

					//{{{ axio
					hyp(seq(concPred(L1*,X,L2*),concPred(L3*,X,L4*))) -> {
						if (X != `EmptyP()) {
							return `rule(
								axiom,
								seq(concPred(L1*,mark(X),L2*),concPred(L3*,mark(X),L4*)),
								concProof());
						}
					}
					//}}}

				}// end %match
			}
			return traversal.genericTraversal(pt,this);
		}
	};

	//{{{ public final static void main(String[] args)
	public static void main(String[] args) {
		GTPropp test = new GTPropp(new Factory(new PureFactory()));

		String query ="";
		try {
			//query = args[0];
			SeqLexer lexer = new SeqLexer(new DataInputStream(System.in));
			SeqParser parser = new SeqParser(lexer);
			// Parse the input expression
			parser.seq();
			CommonAST t = (CommonAST)parser.getAST();
			// Print the resulting tree out in LISP notation
			// System.out.println(t.toStringList());
			// System.out.println("Applying treewalker");
			SeqTreeWalker walker = new SeqTreeWalker();
			// Traverse the tree created by the parser
			query = walker.seq(t);
			System.out.println("Query : "+query);
		} catch (Exception e) {
			System.err.println("exception: "+e);
			return;
		}
		test.run(query);
	}
	//}}}

}
