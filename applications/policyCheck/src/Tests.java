/* Generated by TOM (version 20080109 (src)): Do not edit this file */import accesscontrol.*;
import accesscontrol.types.*;

public class Tests {

  /* Generated by TOM (version 20080109 (src)): Do not edit this file *//* Generated by TOM (version 20080109 (src)): Do not edit this file */private static boolean tom_equal_term_boolean(boolean t1, boolean t2) { return  t1==t2 ;}private static boolean tom_is_sort_boolean(boolean t) { return  true ;} /* Generated by TOM (version 20080109 (src)): Do not edit this file */private static boolean tom_equal_term_int(int t1, int t2) { return  t1==t2 ;}private static boolean tom_is_sort_int(int t) { return  true ;} private static boolean tom_equal_term_State(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_State(Object t) { return  (t instanceof accesscontrol.types.State) ;}private static boolean tom_equal_term_Response(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Response(Object t) { return  (t instanceof accesscontrol.types.Response) ;}private static boolean tom_equal_term_RequestType(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_RequestType(Object t) { return  (t instanceof accesscontrol.types.RequestType) ;}private static boolean tom_equal_term_ListOfAccesses(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_ListOfAccesses(Object t) { return  (t instanceof accesscontrol.types.ListOfAccesses) ;}private static boolean tom_equal_term_SecurityLevelsSet(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_SecurityLevelsSet(Object t) { return  (t instanceof accesscontrol.types.SecurityLevelsSet) ;}private static boolean tom_equal_term_Resource(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Resource(Object t) { return  (t instanceof accesscontrol.types.Resource) ;}private static boolean tom_equal_term_ListOfResources(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_ListOfResources(Object t) { return  (t instanceof accesscontrol.types.ListOfResources) ;}private static boolean tom_equal_term_SecurityLevelsLattice(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_SecurityLevelsLattice(Object t) { return  (t instanceof accesscontrol.types.SecurityLevelsLattice) ;}private static boolean tom_equal_term_AccessMode(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_AccessMode(Object t) { return  (t instanceof accesscontrol.types.AccessMode) ;}private static boolean tom_equal_term_ListOfSubjects(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_ListOfSubjects(Object t) { return  (t instanceof accesscontrol.types.ListOfSubjects) ;}private static boolean tom_equal_term_Subject(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Subject(Object t) { return  (t instanceof accesscontrol.types.Subject) ;}private static boolean tom_equal_term_AccessType(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_AccessType(Object t) { return  (t instanceof accesscontrol.types.AccessType) ;}private static boolean tom_equal_term_SecurityLevel(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_SecurityLevel(Object t) { return  (t instanceof accesscontrol.types.SecurityLevel) ;}private static boolean tom_equal_term_RequestUponState(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_RequestUponState(Object t) { return  (t instanceof accesscontrol.types.RequestUponState) ;}private static boolean tom_equal_term_Access(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Access(Object t) { return  (t instanceof accesscontrol.types.Access) ;}private static boolean tom_equal_term_Request(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Request(Object t) { return  (t instanceof accesscontrol.types.Request) ;}private static boolean tom_equal_term_Decision(Object t1, Object t2) { return  (t1==t2) ;}private static boolean tom_is_sort_Decision(Object t) { return  (t instanceof accesscontrol.types.Decision) ;}private static  accesscontrol.types.AccessMode  tom_make_am( int  t0) { return  accesscontrol.types.accessmode.am.make(t0) ; } 
	
	public static void main(String[] args) {

		System.out.println("START ---------------------");

    AccessMode am = tom_make_am(0);
		System.out.println("AM: "+am);

//     SecurityLevel l1 = `sl(11);
//     SecurityLevel l2 = `sl(2);
// 		System.out.println("l1 < l2: "+`l1.compare2(l2));
	}
	
	
	

}
