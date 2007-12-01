package polygraphesnat;

import polygraphesnat.*;
import polygraphesnat.types.*;
import polygraphesnat.types.twopath.*;
import tom.library.sl.*;

import java.util.HashSet;
import java.util.Iterator;

public class PolygraphesNat{

%include { polygraphesnat/PolygraphesNat.tom }
%include { sl.tom }

private static HashSet<ThreePath> rewritingRules=new HashSet<ThreePath>();


//-----------------------------------------------------------------------------
// jeu de cellules pour les tests : inutile pour le compilateur final, c'esr juste plus pratique
//-----------------------------------------------------------------------------
// 1-chemin representant les entiers naturels
private static	OnePath nat=`OneCell("nat");
// constructeurs sur les entiers naturels
private static	TwoPath zero=`TwoCell("zero",Id(),nat,Constructor());
private static	TwoPath succ =`TwoCell("succ",nat,nat,Constructor());
// 2-cellules de structure
private static	TwoPath eraser= `TwoCell("eraser",nat,Id(),Function());
private static	TwoPath duplication= `TwoCell("duplication",nat,OneC0(nat,nat),Function());
private static	TwoPath permutation = `TwoCell("permutation",OneC0(nat,nat),OneC0(nat,nat),Function());
// addition, soustraction et division
private static	TwoPath plus = `TwoCell("plus",OneC0(nat,nat),nat,Function());
private static	TwoPath minus = `TwoCell("minus",OneC0(nat,nat),nat,Function());
private static	TwoPath division = `TwoCell("division",OneC0(nat,nat),nat,Function());
private static	TwoPath multiplication = `TwoCell("multiplication",OneC0(nat,nat),nat,Function());
// 3-cellules de structure
private static	ThreePath zeroPerm1 = `ThreeCell("zeroPerm1",TwoC1(TwoC0(zero,TwoId(nat)),permutation),TwoC0(TwoId(nat),zero),Function());
private static	ThreePath zeroPerm2 = `ThreeCell("zeroPerm2",TwoC1(TwoC0(TwoId(nat),zero),permutation),TwoC0(zero,TwoId(nat)),Function());
private static	ThreePath zeroDup = `ThreeCell("zeroDup",TwoC1(zero,duplication),TwoC0(zero,zero),Function());
private static	ThreePath zeroEraz = `ThreeCell("zeroEraz",TwoC1(zero,eraser),TwoId(Id()),Function());
private static	ThreePath succPerm1 = `ThreeCell("succPerm1",TwoC1(TwoC0(succ,TwoId(nat)),permutation),TwoC1(permutation,TwoC0(TwoId(nat),succ)),Function());
private static	ThreePath succPerm2 = `ThreeCell("succPerm2",TwoC1(TwoC0(TwoId(nat),succ),permutation),TwoC1(permutation,TwoC0(succ,TwoId(nat))),Function());
private static	ThreePath succDup = `ThreeCell("succDup",TwoC1(succ,duplication),TwoC1(duplication,TwoC0(succ,succ)),Function());
private static	ThreePath succEraz = `ThreeCell("succEraz",TwoC1(succ,eraser),TwoC1(TwoId(nat),eraser),Function());
// regles
private static	ThreePath plusZero = `ThreeCell("plusZero",TwoC1(TwoC0(zero,TwoId(nat)),plus),TwoId(nat),Function());
private static	ThreePath plusSucc = `ThreeCell("plusSucc",TwoC1(TwoC0(succ,TwoId(nat)),plus),TwoC1(plus,succ),Function());
private static	ThreePath minusZero1 = `ThreeCell("minusZero1",TwoC1(TwoC0(zero,TwoId(nat)),minus),TwoC1(eraser,zero),Function());
private static	ThreePath minusZero2 = `ThreeCell("minusZero2",TwoC1(TwoC0(TwoId(nat),zero),minus),TwoId(nat),Function());
private static	ThreePath minusDoubleSucc = `ThreeCell("minusDoubleSucc",TwoC1(TwoC0(succ,succ),minus),minus,Function());
private static	ThreePath divZero = `ThreeCell("divZero",TwoC1(TwoC0(zero,TwoId(nat)),division),TwoC1(eraser,zero),Function());	
private static	ThreePath divSucc = `ThreeCell("divSucc",TwoC1(TwoC0(succ,TwoId(nat)),division),TwoC1(TwoC0(TwoId(nat),duplication),TwoC0(minus,TwoId(nat)),division,succ),Function());
private static	ThreePath multZero = `ThreeCell("multZero",TwoC1(TwoC0(zero,TwoId(nat)),multiplication),TwoC1(eraser,zero),Function());	
private static	ThreePath multSucc = `ThreeCell("multSucc",TwoC1(TwoC0(succ,TwoId(nat)),multiplication),TwoC1(TwoC0(TwoId(nat),duplication),TwoC0(multiplication,TwoId(nat)),plus),Function());
//-----------------------------------------------------------------------------
// on regroupe les regles en les ajoutant a une collection
//-> l'ordre peut etre important afin d'aller plus vite (mais ici je n'ai pas fait attention)
//-----------------------------------------------------------------------------

public static void main(String[] args) {
/*ewritingRules.add(zeroPerm1);
rewritingRules.add(zeroPerm2);
rewritingRules.add(zeroDup);
rewritingRules.add(zeroEraz);
rewritingRules.add(succPerm1);
rewritingRules.add(succPerm2);
rewritingRules.add(succDup);
rewritingRules.add(succEraz);
rewritingRules.add(plusZero);
rewritingRules.add(plusSucc);
rewritingRules.add(minusZero1);
rewritingRules.add(minusZero2);
rewritingRules.add(minusDoubleSucc);
rewritingRules.add(divZero);
rewritingRules.add(divSucc);
rewritingRules.add(multZero);
rewritingRules.add(multSucc);

NormalizeRules();
//-----------------------------------------------------------------------------
// idee de methode pour le compilateur:
// -explorer le xml et extraire tous les chemins comme ci-dessus
// -regrouper tous les 3-cellules au sein d'une collection
// alterner gravity-verticalmerging-applyRules jusuq'� atteindre un point fixe
//-----------------------------------------------------------------------------

//-----------------------------------------------------------------------------
// TESTS
//-----------------------------------------------------------------------------
*/

TwoPath un=`TwoC1(zero,succ);
TwoPath deux=`TwoC1(un,succ);
TwoPath trois=`TwoC1(deux,succ);
TwoPath quatre=`TwoC1(trois,succ);
TwoPath cinq=`TwoC1(quatre,succ);
TwoPath six=`TwoC1(cinq,succ);
TwoPath sept=`TwoC1(six,succ);
TwoPath huit=`TwoC1(sept,succ);
TwoPath neuf=`TwoC1(huit,succ);
TwoPath dix=`TwoC1(neuf,succ);



TwoPath rule=`TwoC1(TwoC0(zero,zero),TwoC0(succ,succ),permutation,minus,eraser);
TwoPath rule2=`TwoC1(zero,TwoC0(succ,zero),division);
TwoPath rule3=`TwoC1(TwoC0(TwoC1(zero,succ,succ,succ),TwoC1(zero,succ,succ)),multiplication);
TwoPath rule4=`TwoC1(zero,succ,succ,TwoC0(succ,zero),TwoC0(succ,succ),TwoC0(succ,succ),division);
TwoPath rule5=`TwoC1(TwoC0(TwoC1(zero,succ,succ,succ),TwoC1(zero)),division);
TwoPath add=`TwoC1(TwoC0(TwoC1(zero,succ,succ,succ,succ,succ),TwoC1(zero,succ,succ,succ)),plus);
TwoPath div=`TwoC1(TwoC0(add,TwoC1(zero,succ,succ,succ)),division);
TwoPath total=`TwoC1(TwoC0(div,TwoC1(zero,succ,succ,succ,succ)),multiplication);
TwoPath nine=`TwoC1(TwoC0(TwoC1(TwoC0(six,six),multiplication),trois),minus);
TwoPath test = `TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(TwoC1(zero,TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),TwoC1(succ,succ)),succ);

test(test);
//System.out.println(rule3);
}

//-----------------------------------------------------------------------------
// STRATEGIES version 2 
//-----------------------------------------------------------------------------

public static void print (TwoPath path){
System.out.println(path.prettyPrint());
}

%strategy Normalize() extends Identity(){ 
  	visit TwoPath {
  		TwoC1(TwoC0(head1*,TwoC1(top*),tail1*),TwoC0(head2*,bottom*,tail2*)) -> {if(`head1*.target()==`head2*.source()&&`top*.target()==`bottom*.source()){System.out.println("1");return `TwoC0(TwoC1(head1*,head2*),TwoC1(top*,bottom*),TwoC1(tail1*,tail2*));}} 
  		TwoC1(TwoC0(head1*,top@TwoCell(_,_,_,_),tail1*),TwoC0(head2*,bottom*,tail2*))-> {if(`head1*.target()==`head2*.source()&&`top.target()==`bottom*.source()){System.out.println("2");return `TwoC0(TwoC1(head1*,head2*),TwoC1(top,bottom*),TwoC1(tail1*,tail2*));}} 
  	  	TwoC1(TwoC0(head1*,top@TwoId(_),tail1*),TwoC0(head2*,bottom*,tail2*))-> {if(`head1*.target()==`head2*.source()&&`top.target()==`bottom*.source()){System.out.println("3");return `TwoC0(TwoC1(head1*,head2*),TwoC1(top,bottom*),TwoC1(tail1*,tail2*));}} 
  	  	TwoC1(TwoC0(head*,TwoC1(top*),tail*),bottom*) -> {if(`top*.target()==`bottom*.source()){System.out.println("4");return `TwoC0(head*,TwoC1(top*,bottom*),tail*);}} 
  	  	TwoC1(TwoC0(head*,top@TwoCell(_,_,_,_),tail*),bottom*) -> {if(`top.target()==`bottom*.source()){System.out.println("5");return `TwoC0(head*,TwoC1(top,bottom*),tail*);}} 
  	  	TwoC1(TwoC0(head*,top@TwoId(_),tail*),bottom*) -> {if(`top.target()==`bottom*.source()){System.out.println("6");return `TwoC0(head*,TwoC1(top,bottom*),tail*);}} 
  	  	/*TwoC1(head*,top@TwoC0(X*),down@TwoC0(Y*),f@TwoCell(_,_,_,Function()),tail*) -> {//marche pas vraiment quand ya une fonction a plusieurs entrees dans y
  	  		int length=`f.sourcesize();
  	  		TwoPath myNewPath=`TwoId(Id());
  	  		for(int i=0;i<length;i++){
  	  			if(`((TwoPath)top.getChildAt(i)).target()==`((TwoPath)down.getChildAt(i)).source()){
  	  			TwoPath newC1=`TwoC1((TwoPath)top.getChildAt(i),(TwoPath)down.getChildAt(i));
  	  			if(i==0){myNewPath=`newC1;}
  	  			else if(i==1){myNewPath=`TwoC0(myNewPath,newC1);}
  	  			else{myNewPath.setChildAt(i,newC1);}
  	  		}
  	  		}
  	  		if(myNewPath!=`TwoId(Id())){
  	  		if(`head!=`TwoId(Id())){
  	  		myNewPath=`TwoC1(head,myNewPath,f,tail);}
  	  		else{myNewPath=`TwoC1(myNewPath,f,tail);}}
  	  		if(myNewPath!=`TwoId(Id())){
  	  		System.out.println("7");
  	  		return myNewPath;}
  	  		}*///couvert par le cas 10
  	  	TwoC1(head*,TwoC0(topleft*,top*,topright*),TwoC0(left*,f@TwoCell(_,_,_,Function()),right*),tail*) -> {//marche pas vraiment quand ya une fonction a plusieurs entrees dans y
  	  		if(`topleft*.target()==`left*.source()&&`top.target()==`f.source()){
  	  			TwoPath myNewPath=`TwoId(Id());
  	  		if(`head*!=`TwoId(Id())){myNewPath= `TwoC1(head*,TwoC0(TwoC1(topleft*,left*),TwoC1(top*,f),TwoC1(topright*,right*)),tail*);}
  	  		else{myNewPath= `TwoC1(TwoC0(TwoC1(topleft*,left*),TwoC1(top*,f),TwoC1(topright*,right*)),tail*);}
  	  		if(myNewPath!=`TwoId(Id())){
  	  		System.out.println("8");
  	  		return myNewPath;
  	  		}
  	  		}
  	  		}
  	  	TwoC1(head*,top,TwoC0(left*,X,right*),tail*) -> {if(`left*.source()==`Id()&&`right*.source()==`Id()&&`X.source()==`top.target()){	 
  	  		TwoPath myNewPath=`TwoId(Id());//peut etre verifier compatibilite de top et X?
  	  		if(`head*!=`TwoId(Id())){myNewPath=`TwoC1(head*,TwoC0(left*,TwoC1(top,X),right*),tail*);}else{myNewPath=`TwoC1(TwoC0(left*,TwoC1(top,X),right*),tail*);}
  	  			if(myNewPath!=`TwoId(Id())){
  	  		System.out.println("9");
  	  		return myNewPath;}
  	  	}}
  	  	TwoC1(head*,top@TwoC0(X*),down@TwoC0(Y*),f@TwoCell(_,_,_,Function()),tail*) -> {//extension du cas 7
  	  		int sourcelength=`f.sourcesize();
  	  		TwoPath myNewPath=`TwoId(Id());
  	  		int index=0;
  	  		if(sourcelength!=`down.length()){break;}
  	  		TwoPath[] array=toArray((TwoC0)`top);
  	  		for(int i=0;i<sourcelength;i++){
  	  			int downsourcelength=`((TwoPath)down.getChildAt(i)).sourcesize();
  	  			
   	  			TwoPath topPart=`TwoId(Id());
  	  			for(int j=index;j<downsourcelength+index;j++){
  	  				
  	  				try{TwoPath newC0 = (TwoPath)array[j];
  	  				
  	  				if(j==index){topPart=newC0;}
  	  			else if(j==index+1){topPart=`TwoC0(topPart,newC0);}
  	  			else{topPart.setChildAt(j,newC0);}

  	  				}catch (ArrayIndexOutOfBoundsException e){//cas ou il n y a pas que des constructeurs au dessus, duplication par example
  	  				}
  	  			}
  	  			index=downsourcelength;
  	  			if(topPart.target()==`((TwoPath)down.getChildAt(i)).source()){
  	  			TwoPath newC1=`TwoC1(topPart,(TwoPath)down.getChildAt(i));
  	  			if(i==0){myNewPath=`newC1;}
  	  			else if(i==1){myNewPath=`TwoC0(myNewPath,newC1);}
  	  			else{myNewPath.setChildAt(i,newC1);}
  	  		}  	  			
  	  		}
  	  		if(myNewPath!=`TwoId(Id())){
  	  		if(`head!=`TwoId(Id())){
  	  		myNewPath=`TwoC1(head,myNewPath,f,tail);}
  	  		else{myNewPath=`TwoC1(myNewPath,f,tail);}}
  	  		if(myNewPath!=`TwoId(Id())){
  	  		System.out.println("10");
  	  		return myNewPath;}
  	  		}
  	  		//a part, retransforme les onec0 en twoC0
  	  	TwoId(OneC0(head,tail*)) -> { System.out.println("onetotwo");return `TwoC0(TwoId(head),TwoId(tail*)); } //correction en m�me temps
  	  	
  	  	TwoC1(head*,t@TwoId(_),TwoId(_),tail*) -> { if(`head!=`TwoId(Id())){return `TwoC1(head,t,tail);}else{return `TwoC1(t,tail);}}
 	 } 
}

%strategy Print() extends Identity(){
  	visit TwoPath {
  	  x -> { System.out.print("STEP ");print(`x); } 
 	 } 
}


// le Y a la fin est important(meme si le * est inutil pour ces exemples)
%strategy ApplyRules() extends Identity(){ 
  	visit TwoPath {
  	  TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1*),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> {System.out.println("ZeroPerm1");return `TwoC1(TwoC0(X1,TwoCell("zero",Id(),OneCell("nat"),Constructor())),Y); } 
  	  TwoC1(TwoC0(X1*,TwoCell("zero",Id(),OneCell("nat"),Constructor())),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> {System.out.println("ZeroPerm2");return `TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1),Y); } 
  	  TwoC1(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("duplication",OneCell("nat"),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> { System.out.println("ZeroDup"); return `TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("zero",Id(),OneCell("nat"),Constructor())),Y*);}
  	  TwoC1(TwoCell("zero",Id(),OneCell("nat"),Constructor()),TwoCell("eraser",OneCell("nat"),Id(),Function()),Y*) -> {System.out.println("ZeroEraz"); return `Y*;}
  	  TwoC1(TwoC0(TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),X2*),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> {System.out.println("SuccPerm1"); return `TwoC1(TwoC0(X1,X2),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),TwoC0(TwoId(OneCell("nat")),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),Y*);}
  	  TwoC1(TwoC0(X1,TwoC1(X2*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()))),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> {System.out.println("SuccPerm2"); return `TwoC1(TwoC0(X1,X2),TwoCell("permutation",OneC0(OneCell("nat"),OneCell("nat")),OneC0(OneCell("nat"),OneCell("nat")),Function()),TwoC0(TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoId(OneCell("nat"))),Y*);}
  	  TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("duplication",OneCell("nat"),OneC0(OneCell("nat"),OneCell("nat")),Function()),Y*) -> {if(`X1!=`TwoId(Id())){System.out.println("SuccDup"); return `TwoC1(X1,TwoCell("duplication",OneCell("nat"),OneC0(OneCell("nat"),OneCell("nat")),Function()),TwoC0(TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),Y*);}}
  	  TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),TwoCell("eraser",OneCell("nat"),Id(),Function()),Y*) -> { System.out.println("SuccEraz"); return `TwoC1(X1,TwoCell("eraser",OneCell("nat"),Id(),Function()),Y*);}
  	  TwoC1(TwoC0(TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),X2*),TwoCell("plus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*)->{System.out.println("plusSucc");return `TwoC1(TwoC0(X1,X2),TwoCell("plus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),Y*);}
  	  TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1*),TwoCell("plus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("plusZero"); return `TwoC1(X1,Y*);}
  	  TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1*),TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("minusZero1"); return `TwoC1(X1,TwoCell("eraser",OneCell("nat"),Id(),Function()),TwoCell("zero",Id(),OneCell("nat"),Constructor()),Y*);}
  	  TwoC1(TwoC0(X1*,TwoCell("zero",Id(),OneCell("nat"),Constructor())),TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("minusZero2"); return `TwoC1(X1,Y*);}
  	  TwoC1(TwoC0(TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),TwoC1(X2*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()))),TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> {System.out.println("minusDoubleSucc");return `TwoC1(TwoC0(X1,X2),TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*);}
  	  TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1*),TwoCell("division",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("divZero"); return `TwoC1(X1,TwoCell("eraser",OneCell("nat"),Id(),Function()),TwoCell("zero",Id(),OneCell("nat"),Constructor()),Y*);}
  	  //TwoC1(TwoC0(X,TwoCell("zero",Id(),OneCell("nat"),Constructor())),TwoCell("division",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> {System.out.println("division by zero attempt,that's awfully wrong");return `TwoId(Id());}
  	  TwoC1(TwoC0(TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),X2*),TwoCell("division",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("divSucc"); return `TwoC1(TwoC0(X1,TwoC1(X2,TwoCell("duplication",OneCell("nat"),OneC0(OneCell("nat"),OneCell("nat")),Function()))),TwoC0(TwoCell("minus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),TwoId(OneCell("nat"))),TwoCell("division",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor()),Y*);}
  	  TwoC1(TwoC0(TwoCell("zero",Id(),OneCell("nat"),Constructor()),X1*),TwoCell("multiplication",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("multZero"); return `TwoC1(X1,TwoCell("eraser",OneCell("nat"),Id(),Function()),TwoCell("zero",Id(),OneCell("nat"),Constructor()),Y*);}
  	  TwoC1(TwoC0(TwoC1(X1*,TwoCell("succ",OneCell("nat"),OneCell("nat"),Constructor())),X2*),TwoCell("multiplication",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*) -> { System.out.println("multSucc"); return `TwoC1(X2,TwoC0(X1,TwoCell("duplication",OneCell("nat"),OneC0(OneCell("nat"),OneCell("nat")),Function())),TwoC0(TwoCell("multiplication",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),TwoId(OneCell("nat"))),TwoCell("plus",OneC0(OneCell("nat"),OneCell("nat")),OneCell("nat"),Function()),Y*);}
  	  
  	} 
}
public static TwoPath formatRule(TwoPath myPath){
try{myPath=(TwoPath) `RepeatId(TopDown(Normalize())).visit(myPath);
	}
	catch(VisitFailure e) {
      throw new tom.engine.exception.TomRuntimeException("strange term: "+myPath);
    }
return myPath;
}


public static void NormalizeRules(){
HashSet<ThreePath> normalizedRewritingRules=new HashSet<ThreePath>();
for (Iterator iterator = rewritingRules.iterator(); iterator.hasNext();) {
	ThreePath rule = (ThreePath) iterator.next();
	try{
	TwoPath normalizedSource=(TwoPath)`TopDown(NormalizeSource()).visit(rule.getSource());
	ThreePath normalizedRule=`ThreeCell(rule.getName(),normalizedSource,rule.getTarget(),rule.getType());
	normalizedRewritingRules.add(normalizedRule);
	}
	catch(VisitFailure e) {
      throw new tom.engine.exception.TomRuntimeException("strange term: "+rule);
    }
}
rewritingRules=normalizedRewritingRules;
}

%strategy NormalizeSource() extends Identity(){ 
  	visit TwoPath {
  	  TwoC0(left*,X,right*) -> {if(`X.sourcesize()>0){ return `TwoC0(left*,TwoC1(TwoId(X.source()),X),right);} }
  	  TwoC1(head*,t@TwoId(!Id()),TwoId(!Id()),tail*) -> {return `TwoC1(head,t,tail);} 
 	 } 
}


public static TwoPath test(TwoPath myPath){//fonction pour tester la combinaison de toutes les strategies
try{
System.out.println("BEFORE");
print(myPath);
System.out.println("LOG");
myPath=(TwoPath) `RepeatId(Sequence(RepeatId(TopDown(Gravity())),RepeatId(TopDown(Normalize())),RepeatId(Sequence(TopDown(ApplyRules()),Print())))).visit(myPath);
System.out.println("RESULT");
print(myPath);
System.out.println(result(myPath));
tom.library.utils.Viewer.display(myPath);
return myPath;
}
catch(VisitFailure e) {
      throw new tom.engine.exception.TomRuntimeException("strange term: " + myPath);
    }
}



// fait tomber les constructeurs
%strategy Gravity() extends Identity(){ 
  	visit TwoPath {
  		TwoC1(head*,f@TwoCell(_,_,_,Constructor()),g@TwoId(_),tail*)->{
				if(`f.target()==`g.source()){
				if(`head*==`TwoId(Id())){
				if(`tail*==`TwoId(Id())){return `TwoC1(TwoId(f.source()),f);}
				return `TwoC1(TwoId(f.source()),f,tail*);
				}
				if(`tail*==`TwoId(Id())){return `TwoC1(head*,TwoId(f.source()),f);}
				System.out.println("GravityA");
				return `TwoC1(head*,TwoId(f.source()),f,tail*);
				}
}
  		TwoC1(head*,TwoC0(head1*,f@TwoCell(_,_,_,Constructor()),tail1*),TwoC0(head2*,g@TwoId(_),tail2*),tail*) -> { 

			if(`head1*.target()==`head2*.source()&&`tail1*.target()==`tail2*.source()&&`f.target()==`g.source()){
				//en fait, on n'a pas vraiment besoins de tester les tails si on teste les heads
																											
				if(`head*==`TwoId(Id())){
					if(`tail*==`TwoId(Id())){return `TwoC1(TwoC0(head1*,TwoId(f.source()),tail1*),TwoC0(head2*,f,tail2*));}
					System.out.println("GravityB1");
					return `TwoC1(TwoC0(head1*,TwoId(f.source()),tail1*),TwoC0(head2*,f,tail2*),tail*);
				}
				if(`tail*==`TwoId(Id())){return `TwoC1(head*,TwoC0(head1*,TwoId(f.source()),tail1*),TwoC0(head2*,f,tail2*));}
				System.out.println("GravityB2");
				return `TwoC1(head*,TwoC0(head1*,TwoId(f.source()),tail1*),TwoC0(head2*,f,tail2*),tail*);
			}
  	  }
  		TwoC1(head*,f@TwoCell(_,_,_,Constructor()),TwoC0(head2*,g@TwoId(_),tail2*),tail*) -> { 

			if(`f.target()==`g.source()){

																											
				if(`head*==`TwoId(Id())){
					if(`tail*==`TwoId(Id())){return `TwoC0(head2*,f,tail2*);}
					System.out.println("GravityC1");
					return `TwoC1(TwoC0(head2*,f,tail2*),tail*);
				}
				if(`tail*==`TwoId(Id())){return `TwoC1(head*,TwoC0(head2*,f,tail2*));}
				System.out.println("GravityC2");
				return `TwoC1(head*,TwoC0(head2*,f,tail2*),tail*);
			}
  	  }
  		  TwoC1(head*,TwoC0(head1*,f@TwoCell(_,_,_,Constructor()),tail1*),g@TwoId(_),tail*) -> { 

				if(`f.target()==`g.source()){
				if(`head*==`TwoId(Id())){
					if(`tail*==`TwoId(Id())){System.out.println("GravityD1");return `TwoC1(TwoC0(head1*,TwoId(f.source()),tail1*),f);}
					System.out.println("GravityD2");return `TwoC1(TwoC0(head1*,TwoId(f.source()),tail1*),f,tail*);
				}
				if(`tail*==`TwoId(Id())){System.out.println("GravityD3");return `TwoC1(head*,TwoC0(head1*,TwoId(f.source()),tail1*),f);}
				System.out.println("GravityD4");return `TwoC1(head*,TwoC0(head1*,TwoId(f.source()),tail1*),f,tail*);
			}
  	  }
  	} 
}

public static TwoPath[] toArray(TwoC0 twoc0) {
    int size = twoc0.length();
    TwoPath[] array = new TwoPath[size];
    int i=0;
    if(twoc0 instanceof ConsTwoC0) {
      TwoPath cur = twoc0;
      while(cur instanceof ConsTwoC0) {
        TwoPath elem = ((ConsTwoC0)cur).getHeadTwoC0();
        array[i] = elem;
        i++;
        cur = ((ConsTwoC0)cur).getTailTwoC0();
        
      }
      array[i] = cur;
    }
    return array;
  }

public static int result(TwoPath resultat){
%match (TwoPath resultat){
			TwoId(Id()) -> { return 0; }
			TwoCell("zero",_,_,_) -> {return 0;}
			TwoCell("succ",_,_,_) -> {return 1;}
			TwoC1(TwoCell("zero",_,_,_),X*) -> {return result(`X*);}
			TwoC1(TwoCell("succ",_,_,_),X*) -> {return 1+result(`X*);}
			TwoC1(TwoCell("succ",_,_,_),TwoCell("succ",_,_,_)) -> {return 2; }
			}
System.out.println("RESULTAT NON CONFORME");resultat.print();
return 0;
}

}