import java.util.*;
import accesscontrol.*;
import accesscontrol.types.*;
import java.util.ArrayList;
import mathsEnumeration.Product;
import mathsEnumeration.PermutationGenerator;

public class CheckAllPermutationsOfRequests{
  %include { sl.tom }
	%include { accesscontrol/Accesscontrol.Tom }
  ArrayList<Subject> Subjects;
  ArrayList<SecurityObject> Objects;
  Policy policy;
  ArrayList<ArrayList<Integer>> allRequests;

CheckAllPermutationsOfRequests(ListOfSubjects ListOfSubjects, ListOfObjects ListOfObjects,Policy policy){
int numberOfAccessModes=2;
this.policy=policy;
this.Subjects=ListToArrayS(ListOfSubjects);
this.Objects=ListToArrayO(ListOfObjects);
List<Integer> sizeOfsets=new ArrayList<Integer>();
sizeOfsets.add(Subjects.size());
sizeOfsets.add(Objects.size());
sizeOfsets.add(numberOfAccessModes);
this.allRequests=Product.getProduct(sizeOfsets);
}
public static ArrayList<Subject> ListToArrayS(ListOfSubjects L){
ArrayList<Subject> S=new ArrayList<Subject>();
%match(L){
	subjects(_*,x,_*)->{S.add(`x);}
}
return S;
}

public static ArrayList<SecurityObject> ListToArrayO(ListOfObjects L){
ArrayList<SecurityObject> O=new ArrayList<SecurityObject>();
%match(L){
	objects(_*,x,_*)->{O.add(`x);}
}
return O;
}



  CheckAllPermutationsOfRequests(ArrayList<Subject> Subjects, ArrayList<SecurityObject> Objects,Policy policy){
    int numberOfAccessModes=2;
    this.policy=policy;
    this.Subjects=Subjects;
    this.Objects=Objects;
    List<Integer> sizeOfsets=new ArrayList<Integer>();
    sizeOfsets.add(Subjects.size());
    sizeOfsets.add(Objects.size());
    sizeOfsets.add(numberOfAccessModes);
    this.allRequests=Product.getProduct(sizeOfsets);
  }

  CheckAllPermutationsOfRequests(ArrayList<Subject> Subjects, ArrayList<SecurityObject> Objects,Policy policy,ArrayList<ArrayList<Integer>> allRequests){
    this.policy=policy;
    this.Subjects=Subjects;
    this.Objects=Objects;
    this.allRequests=allRequests;
  }



  // Fixed configuration for Subjects and Objects; PG generates all permutation of requests
public boolean checkConfiguration(){
		boolean b=false;
	  PermutationGenerator PG= new PermutationGenerator (allRequests.size());
      //foreach permutation
	  while (PG.hasMore ()) {
		  //get a permuation
		  int[] currentPermutation=PG.getNext();
		  //check this permutation of requests with the fixed configuration of Subjects and objects
		  if(!check(currentPermutation)){
			  b=true;
			  //return the current permutation if it generates a leakage 
		  }
      System.out.println("for permutation :"+Arrays.toString(currentPermutation));
    }
	
    //return the array rep if no leakage is detected  
    return b;
  }


 
  public Request createRequest(ArrayList<Subject> Subjects,ArrayList<SecurityObject> Objects,ArrayList<Integer> requestIndexes){	  //create an access
    Access a=`access(Subjects.get(requestIndexes.get(0)),Objects.get(requestIndexes.get(1)),(aM(requestIndexes.get(2))),explicit());
    // create the corresponding request 	
    return `request(add(),a);  
  }




   // For a fixed configuration of subjects and objects and a given permutation for requests  
  public boolean check( int[] permutationOfRequests){
	  //boolean to store the check result  
	  boolean result=true;
	  //Create an empty state
	  State M=`state(accesses(),accesses());
	  //for each request whose index in "allRequests" is determined by "permutationOfRequests" 
	  for (int i = 0; i < permutationOfRequests.length; i++) {
		  //Get the element i from the list "permutationOfRequests" where it is represented by 3 indexes: the subject, the object and the access mode
		  //and generate the couple (request,state)  
		  RequestUponState rus=`rus(createRequest(Subjects,Objects,allRequests.get(permutationOfRequests[i])),M);
      	  // try to add the access to the state given the implementation of the policy
		  Object[] checkResult=TestAccess.check(rus,policy); 
		  //set the result boolean verifying leakage detection
		  result=(Boolean)checkResult[1];
		  if (!result)break;
		  //update the current state
		  M=(State)checkResult[0];
		  
      }
	  //else generate a message and return true
	if (result) System.out.println("No information leakage detected");
    
    return result;
  }
}
