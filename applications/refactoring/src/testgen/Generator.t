/*
 * Copyright (c) 2004-2008, INRIA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met: 
 *	- Redistributions of source code must retain the above copyright
 *	notice, this list of conditions and the following disclaimer.  
 *	- Redistributions in binary form must reproduce:w
 the above copyright
 *	notice, this list of conditions and the following disclaimer in the
 *	documentation and/or other materials provided with the distribution.
 *	- Neither the name of the INRIA nor the names of its
 *	contributors may be used to endorse or promote products derived from
 *	this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package testgen;

import testgen.tinyjava.*;
import testgen.tinyjava.types.*;
import java.io.*;

import tom.library.sl.*;

import java.util.*;


public class Generator {

  %include { util.tom }
  %include { lookup.tom }

  static Random random = new java.util.Random();

  static Strategy generateName = new GenerateName();

  static Strategy generateStmt = `Mu(MuVar("x"),ChoiceUndet(Mu(MuVar("y"),ChoiceUndet(Make_ConsBlock(MuVar("x"),MuVar("y")),Make_EmptyBlock())),Make_LocalVariableDecl(Make_Undefined(),generateName,Make_Undefined())));

  static Strategy generateClassDecl = `Mu(MuVar("x"),Make_ClassDecl(generateName,Make_Undefined(),Mu(MuVar("y"),ChoiceUndet(Make_ConsConcBodyDecl(ChoiceUndet(Make_FieldDecl(Make_Undefined(),generateName,Make_Undefined()),Make_MemberClassDecl(MuVar("x")),Make_Initializer(generateStmt)),MuVar("y")),Make_EmptyConcBodyDecl())))); 

  static Strategy generateClassDeclList = `Make_ConsConcClassDecl(generateClassDecl,Mu(MuVar("x"),ChoiceUndet(Make_ConsConcClassDecl(generateClassDecl,MuVar("x")),Make_EmptyConcClassDecl())));

  static Strategy generateProg = `Make_ConsProg(Make_PackageNode(generateName,generateClassDeclList),Mu(MuVar("x"),ChoiceUndet(Make_ConsProg(Make_PackageNode(generateName,generateClassDeclList),MuVar("x")),Make_EmptyProg())));

  public Prog generateProg() {
    try {
      return (Prog) generateProg.visit(`Prog());
    } catch ( VisitFailure e) {
      throw new RuntimeException("Unexpected strategy failure");
    }
  }

  public Prog removeConflicts(Prog p) {
    try {
      return (Prog) `InnermostId(RemoveConflicts()).visit(p);
    } catch ( VisitFailure e) {
      throw new RuntimeException("Unexpected strategy failure");
    }
  }


  public void generateClasses() throws java.io.IOException {
    StringWriter writer = new StringWriter();
    try {
      Prog p = (Prog) generateProg.visit(`Prog());
      System.out.println("Random class hierarchy generation...");
      System.out.println(collectAllTypes(p));
      System.out.println("Remove name conflicts...");
      p = (Prog) `InnermostId(RemoveConflicts()).visit(p);
      System.out.println(collectAllTypes(p));
      System.out.println("Generation of inheritance hierarchy...");
      System.out.println("For top-level classes");
      p = generateInheritanceHierarchyForTopLevelClasses(p);
      printDeclClass(p);
      System.out.println(p);
      System.out.println("Checking");
      `_Prog(_PackageNode(Identity(),_ConcClassDecl(_ClassDecl(Identity(),CheckSuperClassDefined(),Identity())))).visit(p);
      System.out.println("For member classes");
      p = generateInheritanceHierarchyForMemberClasses(p);
      System.out.println("Generation of types for fields and variables...");
      p = generateTypeForFieldsAndLocalVariables(p);
      System.out.println("Generation of assignments for fields and variables...");
      p = generateAssignmentForFieldsAndLocalVariables(p);
      System.out.println("Print classes");
      printDeclClass(p);
      %match ( p ) {
        Prog(_*,PackageNode(packageName,classes),_*) -> {
          File packagedir = new File("./"+`packageName.getname());
          if (! packagedir.exists() ) packagedir.mkdir();
          %match ( classes ) {
            ConcClassDecl(_*,c@ClassDecl[name=name],tail*) -> {
              File classfile = new File("./"+`packageName.getname()+"/"+`name.getname()+".java");
              classfile.createNewFile();
              FileWriter cwt = new FileWriter(classfile);
              cwt.write("package "+`packageName.getname()+";\n");
              printClass(cwt,`c);
              cwt.close();
            }
          }
        }
      }
    } catch ( VisitFailure e) {
      throw new RuntimeException("Unexpected strategy failure");
    }      
  }

  %strategy  CheckSuperClassDefined() extends Identity() {
    visit Name {
      Undefined() -> { throw new VisitFailure(); }
    }
  }

  %strategy RemoveConflicts() extends Identity() {
    visit BodyDeclList {
      ConcBodyDecl(X*,i1@Initializer[],Y*,i2@Initializer[],Z*) -> ConcBodyDecl(X*,i1,Y*,Z*)
        ConcBodyDecl(X*,c1@MemberClassDecl(ClassDecl[name=n]),Y*,c2@MemberClassDecl(ClassDecl[name=n]),Z*) -> ConcBodyDecl(X*,c2,Y*,Z*)
        ConcBodyDecl(X*,d1@FieldDecl[name=n],Y*,d2@FieldDecl[name=n],Z*) -> ConcBodyDecl(X*,d2,Y*,Z*)
    }
    visit Prog {
      Prog(X*,cu1@PackageNode[packageName=n],Y*,cu2@PackageNode[packageName=n],Z*) -> Prog(X*,cu1,Y*,Z*)
    }
    visit ClassDeclList {
      ConcClassDecl(X*,c1@ClassDecl[name=n],Y*,c2@ClassDecl[name=n],Z*) -> ConcClassDecl(X*,c1,Y*,Z*)
    }
    visit ClassDecl {
      c@ClassDecl[name=n] -> {
        //remove inner classes with the same name 
        return (ClassDecl) `InnermostId(RemoveConflictedMemberClasses(n)).visit(`c);
      }
    }
    visit Stmt {
      Block(X*,l1@LocalVariableDecl[name=name],Y*,l2@LocalVariableDecl[name=name],Z*) -> Block(X*,Y*,l2,Z*)
    }
  }

  %strategy RemoveConflictedMemberClasses(name:Name) extends Identity() {
    visit BodyDeclList {
      ConcBodyDecl(X*,MemberClassDecl[innerClass=ClassDecl[name=n]],Y*) -> {
        if (name.equals(`n)) {
          return `ConcBodyDecl(X*,Y*);
        } 
      }
    }
  }

  public void printClass(Writer w, ClassDecl c) throws java.io.IOException {
    String classname = c.getname().getname();
    w.write("\n public class "+classname+" extends "+getComposedName(c.getsuper())+" {\n\n");
    BodyDeclList bodyDeclSet = c.getbodyDecl();
    %match ( bodyDeclSet ) {
      ConcBodyDecl(_*,bodyDecl,_*) -> {
        %match ( bodyDecl ) {
          FieldDecl(fieldType,name,expr) -> {
            w.write("public "+getComposedName(`fieldType)+" "+`name.getname()+" = "+getComposedName(`expr)+";\n");
            //w.write(getComposedName(`fieldType)+" "+`name.getname()+";\n");
          }
          MemberClassDecl(innerClass) -> {
            printClass(w,`innerClass);
          }
          Initializer(body) -> {
            w.write("public "+classname+"() {\n");
            printStmt(w,`body);
            w.write("}\n");
          }
        }
      }
    }
    w.write("}\n");
  }

  public void printStmt(Writer w, Stmt c) throws java.io.IOException {
    %match(c) {
      b@Block(_*) -> {
        w.write("{");
        %match(b) {
          Block(_*,i,_*) -> {
            printStmt(w,`i);
          }
        }
        w.write("}\n");
      }
      LocalVariableDecl(varType,name,expr) -> {
        w.write(getComposedName(`varType)+" "+`name.getname()+" = "+getComposedName(`expr)+";\n");
        //w.write(getComposedName(`varType)+" "+`name.getname()+";\n");
      }
      SuperCall(name) -> {
        w.write(getComposedName(`name)+".super();\n");

      }
    }
  }

  public static class GenerateName extends BasicStrategy {
    public GenerateName() {
      super(`Identity());
    } 

    public Object visitLight(Object v, Introspector introspector) throws VisitFailure {
      return `Name(""+((char)('a'+(random.nextInt('z'-'a'+ 1)))));
    }
  }


  public Prog generateInheritanceHierarchyForTopLevelClasses(Prog p) {
    List<Type> alltopleveltypes = new ArrayList<Type>();
    alltopleveltypes.addAll(collectTopLevelTypes(p));
    List<Type> undefinedtypes = new ArrayList<Type>();
    undefinedtypes.addAll(alltopleveltypes);
    for (Type type: alltopleveltypes) {
      // type cannot be a superclass for itself
      boolean isUndefined = false;
      if (undefinedtypes.contains(type)) {
        isUndefined = true;
        undefinedtypes.remove(type);
      }      
      int size = undefinedtypes.size();
      if (size>0) {
        int nb_subclasses = random.nextInt(size);
        if (nb_subclasses > 0) {
          Set<Integer> set = new HashSet();
          while (set.size() != nb_subclasses) {
            int index = random.nextInt(alltopleveltypes.size());
            Type t = alltopleveltypes.get(index);
            Name superclassname = type.getComposedName();
            if (undefinedtypes.contains(t) & !set.contains(index) & ! isHiddenBy(superclassname,t.getComposedName())) {
              try {
                PositionWrapper res = new PositionWrapper(new Position());
                TypeWrapper wrapper_t = new TypeWrapper(t);
                System.out.println("begin of try to access "+superclassname+" from "+t);
                //check if the superclassname can be found with lookup and then that the found declaration is a top-level class (not an inner class that hides this top-level class)
                `Sequence(ApplyAt(wrapper_t,Sequence(RenameSuperClass(superclassname),_ClassDecl(Identity(),LookupClassDecl(res),Identity()))),ApplyAtPosition(res,Up(Is_ConsConcClassDecl()))).visit(p);
                // `Sequence(ApplyAt(wrapper_t,IsAccessibleFromClassDecl(superclassname,res)),ApplyAtPosition(res,Up(Is_ConsConcClassDecl()))).visit(p);
                // the strategy IsAccessibleFromClassDecl does not seem to work correctly for the moment
                System.out.println("end of try to access "+superclassname+" from "+t+" : success");
                //type is correctly accessible from t
                //test if it does not create a cycle
                if (!type.inherits(t)) {
                  set.add(index);
                } else {
                  // to avoid loops due to too much inaccessible types
                  nb_subclasses--;
                }
              } catch (VisitFailure e) {
                // to avoid loops due to too much inaccessible types
                nb_subclasses--;
                System.out.println("end of try to access "+superclassname+" from "+t+" : failure");
              }
            }
          }
          for (int index:set) {
            Type t = alltopleveltypes.get(index);
            undefinedtypes.remove(t);
            t.setsuperclass(type);
          }
        }
      }
      if (isUndefined) {
        undefinedtypes.add(type);
      }
    }
    Prog newp = p;
    for (Type type: alltopleveltypes) {
      TypeWrapper current = new TypeWrapper(type);
      Type superclass = type.getsuperclass();
      Name superclassname = `Dot(Name("Object"));
      if (superclass != null) {
        superclassname = superclass.getComposedName();
      }
      try {
        newp = (Prog) `ApplyAt(current,RenameSuperClass(superclassname)).visit(newp);
      } catch (VisitFailure e) {
        throw new RuntimeException(" Unexpected strategy failure");
      }
    }
    return newp;
  }

  public Prog generateInheritanceHierarchyForMemberClasses(Prog p) {
    Set<Position> undefinedtypes = collectMemberClassesPosition(p);
    if (! undefinedtypes.isEmpty()) {
      try {
        Iterator iter = undefinedtypes.iterator();
        Set<Name> inheritancePath = new HashSet();
        //find the corresponding name
        PositionWrapper current = new PositionWrapper((Position)iter.next());
        NameWrapper currentname = new NameWrapper();
        `ApplyAtPosition(current,GetName(currentname)).visit(p);
        //add it to the inheritance path
        inheritancePath.add(currentname.value);
        Set<Name> accessibleNames = new HashSet();
        return (Prog) `Mu(MuVar("x"),IfThenElse(IsComplete(undefinedtypes), Identity(), Sequence(PrintContext(current,accessibleNames,inheritancePath,undefinedtypes),CollectAllAccessibleNames(current,accessibleNames,inheritancePath),ApplyAtPosition(current,RenameSuperClassAndUpdate(current,accessibleNames,inheritancePath,undefinedtypes)),MuVar("x")))).visit(p);
      } catch (VisitFailure e) {
        throw new RuntimeException(" Unexpected strategy failure");
      }
    } else {
      return p;
    }
  }


  public Prog generateTypeForFieldsAndLocalVariables(Prog p) {
    Set<Name> accessibleNames = new HashSet();
    try {
      return (Prog) `TopDown(
          IfThenElse(Choice(Is_FieldDecl(),Is_LocalVariableDecl()),
            Sequence(ApplyAtEnclosingClass(CollectAccessibleTypes(accessibleNames)),RenameType(accessibleNames)),
            Identity()
            )
          ).visit(p);
    } catch (VisitFailure e) {
      throw new RuntimeException(" Unexpected strategy failure");
    }
  }


  %strategy CollectAccessibleTypes(accessibleNames:Set) extends Identity() {
    visit ClassDecl {
      decl -> {
        accessibleNames.clear();
        Strategy superclass_case = `Mu(MuVar("begin_specialcase"),Sequence(Debug("collect accessible names: begin special case"),_ClassDecl(
                Identity(),
                ApplyAtSuperClass(MuVar("begin_specialcase")),
                _ConcBodyDecl( IfThenElse(Is_MemberClassDecl(), _MemberClassDecl(Collect(accessibleNames)), Identity())))),Debug("collect accessible names: end special case"));

        Strategy main = `Mu(MuVar("begin_main"),Sequence(Debug("collect accessible names: begin main"),Collect(accessibleNames),_ClassDecl(
                Identity(),
                Sequence(Debug("apply at super class"),ApplyAtSuperClass(superclass_case)),
                _ConcBodyDecl( IfThenElse(Is_MemberClassDecl(), _MemberClassDecl(Collect(accessibleNames)), Identity())) 
                ),IfThenElse(Up(Is_MemberClassDecl()),
                  Sequence(Debug("apply at enclosing class"),ApplyAtEnclosingClass(MuVar("begin_main"))),
                  Sequence(Debug("apply at enclosing package"),ApplyAtEnclosingPackageNode(_PackageNode(Identity(),_ConcClassDecl(Collect(accessibleNames))))))));
        //TODO add classes in other packages
        main.visit(getEnvironment());
        //remove full qualified names p.c where p is the name of the current class
        NameWrapper currentname = new NameWrapper();
        `GetName(currentname).visit(getEnvironment());
        Set hiddenNames = new HashSet();
        for(Name name: (Set<Name>) accessibleNames) {
          if (isHiddenBy(name,currentname.value))  {
            hiddenNames.add(name);
          }      
        }
        accessibleNames.removeAll(hiddenNames);
        System.out.println("accessibleNames from the class "+`decl);
        System.out.println(accessibleNames);
      }
    }
  }

  public Prog generateAssignmentForFieldsAndLocalVariables(Prog p) {
    Set<Name> accessibleNames = new HashSet();
    try {
      return (Prog) `TopDown(
          IfThenElse(Choice(Is_FieldDecl(),Is_LocalVariableDecl()),
            Sequence(CollectAccessibleFieldsAndVars(accessibleNames),SetAssignment(accessibleNames)),
            Identity()
            )
          ).visit(p);
    } catch (VisitFailure e) {
      throw new RuntimeException(" Unexpected strategy failure");
    }
  }

  %strategy CollectAccessibleFieldsAndVars(accessibleFieldsOrVariables:Set) extends Identity() {
    visit BodyDecl {
      field@FieldDecl[FieldType=type,name=name] -> {
        NameWrapper currenttype = new NameWrapper();
        currenttype.value = `type;
        accessibleFieldsOrVariables.clear();
        Strategy superclass_case = `Mu(MuVar("begin"),Sequence(Debug("collect accessible fields: begin special case for super classes"),_ClassDecl(
                Identity(),
                ApplyAtSuperClass(MuVar("begin")),
                _ConcBodyDecl( IfThenElse(Is_FieldDecl(), CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables), Identity())))),Debug("collect accessible fields: end special case for super classes"));

        Strategy enclosingclass_case = `Mu(MuVar("begin"),Sequence(Debug("collect accessible fields: begin special case for enclosing classes"),_ClassDecl(
                Identity(),
                Sequence(Debug("apply at super class"),ApplyAtSuperClass(superclass_case)),
                _ConcBodyDecl( IfThenElse(Is_FieldDecl(), CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables), Identity())) 
                ),IfThenElse(Up(Is_MemberClassDecl()),
                  Sequence(Debug("apply at enclosing class"),ApplyAtEnclosingClass(MuVar("begin"))),
                  Identity())));

        //collect all the fields defined before the current field in the current class
        //then collect the accessible fields from super-classes and enclosing classes
        Strategy main = `Sequence(Debug("collect accessible fields: begin main"),
            Up(Mu(MuVar("bodydecl")),
              Up(IfThenElse(Is_ConsConcBodyDecl(),
                  Sequence(_ConsConcBodyDecl(IfThenElse(Is_FieldDecl(),CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables),Identity()),Identity()),MuVar("bodydecl")),
                  Identity()))),
            ApplyAtEnclosingClass(Sequence(
                _ClassDecl(Identity(),ApplyAtSuperClass(superclass_case),Identity()),
                IfThenElse(Up(Is_MemberClassDecl()),
                  Sequence(Debug("apply at enclosing class"),ApplyAtEnclosingClass(enclosingclass_case)),
                  Identity()))));
        main.visit(getEnvironment());
        //remove any accessible field or variable named name
        accessibleFieldsOrVariables.remove(`name);

        System.out.println("accessibleFieldsOrVariables from the field "+`name);
        System.out.println(accessibleFieldsOrVariables);
      }
    }
    visit Stmt {
      var@LocalVariableDecl[VarType=type,name=name] -> {
        //collect all accessible local variables and fields 
        accessibleFieldsOrVariables.clear();
        NameWrapper currenttype = new NameWrapper();
        currenttype.value = `type;
        accessibleFieldsOrVariables.clear();
        Strategy superclass_case = `Mu(MuVar("begin"),Sequence(Debug("collect accessible fields: begin special case for super classes"),_ClassDecl(
                Identity(),
                ApplyAtSuperClass(MuVar("begin")),
                _ConcBodyDecl( IfThenElse(Is_FieldDecl(), CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables), Identity())))),Debug("collect accessible fields: end special case for super classes"));

        //collect all the local variables defined before the current variable in the current class
        //then collect the accessible fields from the enclosing class
        //TODO collect variables out the current block
        Strategy main = `Sequence(Debug("collect accessible fields: begin main"),
            Up(Mu(MuVar("bodydecl")),
              Up(IfThenElse(Is_ConsBlock(),
                  Sequence(_ConsBlock(IfThenElse(Is_LocalVariableDecl(),CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables),Identity()),Identity()),MuVar("bodydecl")),
                  Identity()))),
            ApplyAtEnclosingClass(
              Mu(MuVar("begin"),Sequence(Debug("collect accessible fields: begin special case for enclosing classes"),_ClassDecl(
                    Identity(),
                    Sequence(Debug("apply at super class"),ApplyAtSuperClass(superclass_case)),
                    _ConcBodyDecl( IfThenElse(Is_FieldDecl(), CollectFieldOrVariable(currenttype,accessibleFieldsOrVariables), Identity())) 
                    ),IfThenElse(Up(Is_MemberClassDecl()),
                      Sequence(Debug("apply at enclosing class"),ApplyAtEnclosingClass(MuVar("begin"))),
                      Identity())))));

        main.visit(getEnvironment());
        //remove any accessible field or variable named name
        accessibleFieldsOrVariables.remove(`name);

        System.out.println("accessibleFieldsOrVariables from the field "+`name);
        System.out.println(accessibleFieldsOrVariables);
      }
    }
  }

  %strategy SetAssignment(accessibleFieldsOrVariables:Set) extends Identity() {
    visit BodyDecl {
      field@FieldDecl[] -> {
        int index = random.nextInt(accessibleFieldsOrVariables.size()+1);
        if (index == accessibleFieldsOrVariables.size()) {
          return `field.setexpr(`Name("null"));
        } else {
          Iterator iter = accessibleFieldsOrVariables.iterator();
          for (int i=0;i<index;i++) { iter.next(); }
          Name type = (Name) iter.next();
          return `field.setexpr(type);
        }
      }
    }
    visit Stmt {
      var@LocalVariableDecl[] -> {
        int index = random.nextInt(accessibleFieldsOrVariables.size()+1);
        if (index == accessibleFieldsOrVariables.size()) {
          return `var.setexpr(`Name("null"));
        } else {
          Iterator iter = accessibleFieldsOrVariables.iterator();
          for (int i=0;i<index;i++) { iter.next(); }
          Name type = (Name) iter.next();
          return `var.setexpr(type);
        }
      }
    }
  }

  %strategy RenameType(accessibleNames:Set) extends Identity() {
    visit BodyDecl {
      field@FieldDecl[] -> {
        int index = random.nextInt(accessibleNames.size()+1);
        if (index == accessibleNames.size()) {
          return `field.setFieldType(`Dot(Name("Object")));
        } else {
          Iterator iter = accessibleNames.iterator();
          for (int i=0;i<index;i++) { iter.next(); }
          Name type = (Name) iter.next();
          return `field.setFieldType(type);
        }
      }
    }
    visit Stmt {
      var@LocalVariableDecl[] -> {
        int index = random.nextInt(accessibleNames.size()+1);
        if (index == accessibleNames.size()) {
          return `var.setVarType(`Dot(Name("Object")));
        } else {
          Iterator iter = accessibleNames.iterator();
          for (int i=0;i<index;i++) { iter.next(); }
          Name type = (Name) iter.next();
          return `var.setVarType(type);
        }
      }
    }
  }

  %strategy PrintContext(current:PositionWrapper,accessibleNames:Set,inheritancePath:Set,undefinedtypes:Set) extends Identity() {
    visit Prog {
      p -> {
        System.out.println("current");
        System.out.println(current.value);
        System.out.println("inheritancePath");
        System.out.println(inheritancePath);
        System.out.println("accessibleNames");
        System.out.println(accessibleNames);
        System.out.println("undefinedtypes");
        System.out.println(undefinedtypes);
        System.out.println("prog");
        System.out.println(`p);
      }
    }
  }


  %strategy IsComplete(undefinedtypes:Set) extends Fail() {
    visit Prog {
      p -> {
        if (undefinedtypes.isEmpty()) {
          // the hierarchy is now complete
          return `p;
        }
      }
    }
  }

  %strategy RenameSuperClassAndUpdate(current:PositionWrapper, accessibleNames:Set, inheritancePath:Set, undefinedtypes:Set) extends Identity() {
    visit ClassDecl {
      c@ClassDecl[super=Undefined()] -> {
        undefinedtypes.remove(current.value);
        if (accessibleNames.isEmpty()) {
          // the hierarchy is now complete
          return `c.setsuper(`Dot(Name("Object")));
        } else {
          while ( true ) {
            int index = random.nextInt(accessibleNames.size()+1);
            if (index == accessibleNames.size()) {
              // this index corresponds to the case where there is no super-class
              inheritancePath.clear();
              if(! undefinedtypes.isEmpty()) {
                current.value = (Position) undefinedtypes.iterator().next();
                NameWrapper currentname = new NameWrapper();
                `ApplyAtPosition(current,GetName(currentname)).visit(getEnvironment());
                System.out.println("currentname "+currentname.value);
                inheritancePath.add(currentname.value);
              }
              return `c.setsuper(`Dot(Name("Object")));
            } else {
              Iterator iter = accessibleNames.iterator();
              for (int i=0;i<index;i++) { iter.next(); }
              Name superclassname = (Name) iter.next();
              getEnvironment().setSubject(`c.setsuper(superclassname));
              PositionWrapper res = new PositionWrapper(new Position());
              getEnvironment().down(2);
              System.out.println("try to lookup "+getEnvironment().getSubject());
              try {
                `LookupClassDecl(res).visit(getEnvironment());
                getEnvironment().up();
                current.value = res.value;
                NameWrapper currentname = new NameWrapper();
                `ApplyAtPosition(current,GetName(currentname)).visit(getEnvironment());
                `ApplyAtPosition(current,IfThenElse(Up(Is_MemberClassDecl()),AdaptConstructor(),Identity())).visit(getEnvironment());
                System.out.println("currentname "+currentname.value);
                inheritancePath.add(currentname.value);
                return (ClassDecl) getEnvironment().getSubject();

              } catch (VisitFailure e) {
                //try to find an other super class
                getEnvironment().up();
                accessibleNames.remove(superclassname);
              }
            }
          }
        }
      }
      c@ClassDecl[super=!Undefined()] -> {
        inheritancePath.clear();
        if (! undefinedtypes.isEmpty()) {
          current.value = (Position) undefinedtypes.iterator().next();
          NameWrapper currentname = new NameWrapper();
          `ApplyAtPosition(current,GetName(currentname)).visit(getEnvironment());
          System.out.println("currentname "+currentname.value);
          inheritancePath.add(currentname.value);
        }
      }
    }
  }


  //strategy that creates explicit super call in constructor 
  %strategy AdaptConstructor() extends Identity() {
    visit ClassDecl {
      c@ClassDecl[super=superclass,bodyDecl=ConcBodyDecl(X*,i@Initializer[body=body],Y*)] -> {
        return `c.setbodyDecl(`ConcBodyDecl(X*,Initializer(Block(SuperCall(Dot(superclass,Name("this"))),body)),Y*));
      }
      c@ClassDecl[super=superclass,bodyDecl=ConcBodyDecl(X*,!Initializer[],Y*)] -> {
        return `c.setbodyDecl(`ConcBodyDecl(X*,Initializer(SuperCall(Dot(superclass,Name("this")))),Y*));
      }
    }
  }

  %strategy CollectAllAccessibleNames(current:PositionWrapper, accessibleNames:Set, inheritancePath:Set) extends Identity() {
    visit Prog {
      p -> {
        Set<Type> alltopleveltypes = collectTopLevelTypes(`p); 
        for (Type toplevel: alltopleveltypes) {
          accessibleNames.add(`Dot(Name(toplevel.getpackagename()),Name(toplevel.getname())));
        }
        Strategy superclass_case = `Mu(MuVar("begin"),_ClassDecl(
              Identity(),
              ApplyAtSuperClass(MuVar("begin")),
              _ConcBodyDecl( IfThenElse(Is_MemberClassDecl(), _MemberClassDecl(Collect(accessibleNames)), Identity()))));

        Strategy main = `ApplyAtPosition(current,ApplyAtEnclosingClass(
              Mu(MuVar("begin"),Sequence(Collect(accessibleNames),_ClassDecl(
                    Identity(),
                    ApplyAtSuperClass(superclass_case),
                    _ConcBodyDecl( IfThenElse(Is_MemberClassDecl(), _MemberClassDecl(Collect(accessibleNames)), Identity())) 
                    ),IfThenElse(Up(Is_MemberClassDecl()),
                      ApplyAtEnclosingClass(MuVar("begin")),
                      Identity())))));
        main.visit(getEnvironment());
        //remove full qualified names p.c where p is the name of the current class
        NameWrapper currentname = new NameWrapper();
        `ApplyAtPosition(current,GetName(currentname)).visit(getEnvironment());
        Set hiddenNames = new HashSet();
        for(Name name: (Set<Name>) accessibleNames) {
          if (isHiddenBy(name,currentname.value))  {
            hiddenNames.add(name);
          }      
        }
        accessibleNames.removeAll(hiddenNames);
        accessibleNames.removeAll(inheritancePath);
      }
    }
  }

  public static boolean isHiddenBy(Name hiddenname, Name name) {
    %match (hiddenname) {
      Dot(packagename,_) -> {
        //TODO no more generating dot(name) but just name
        %match(name) {
          Dot(classname) -> {
            if (`packagename.equals(`classname)) {
              return true;
            }
          }
          Dot(_,classname) -> {
            if (`packagename.equals(`classname)) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }


  %strategy Collect(allAccessibleNames:Set) extends Identity() {
    visit ClassDecl {
      ClassDecl[name=name] -> {
        allAccessibleNames.add(`Dot(name));
      }
    }
  }

  %strategy CollectFieldOrVariable(type:NameWrapper,accessibleFieldsOrVariables:Set) extends Identity() {
    visit BodyDecl {
      FieldDecl[FieldType=fieldtype,name=name] -> {
        if (`fieldtype.equals(type.value)) {
          accessibleFieldsOrVariables.add(`name);
        }
      }
    }
    visit Stmt {
      LocalVariableDecl[VarType=varType,name=name] -> {
        if (`varType.equals(type.value)) {
          accessibleFieldsOrVariables.add(`name);
        }
      }
    }
  }


  public void printAccessibleClassesForMemberClasses(Prog p) {
    Set<Type> allMemberTypes = collectMemberTypes(p);
    for (Type current:allMemberTypes) {
      System.out.println("accessible classes for "+current);
      TypeWrapper wrapper = new TypeWrapper(current);
      Strategy printAllAccessibleClasses =  `ApplyAt(wrapper,ApplyAtEnclosingClass(
            Mu(MuVar("begin"),Sequence(Print(),_ClassDecl(
                  Identity(),
                  ApplyAtSuperClass(MuVar("begin")),
                  _ConcBodyDecl( IfThenElse(Is_MemberClassDecl(), _MemberClassDecl(Print()), Identity())) 
                  ),IfThenElse(Up(Is_MemberClassDecl()),
                    ApplyAtEnclosingClass(MuVar("begin")),
                    Identity())))));
      try {
        printAllAccessibleClasses.visit(p);
      } catch (VisitFailure e) {
        throw new RuntimeException(" Unexpected strategy failure");
      }
    }
  }

  %strategy ApplyAtSuperClass(s:Strategy) extends Identity() {
    visit Name {
      n -> {
        PositionWrapper res = new PositionWrapper(new Position());
        System.out.println("try to find the super-class "+`n);
        `IfThenElse(LookupClassDecl(res),Sequence(Debug("start to apply at the super class"),ApplyAtPosition(res,Sequence(Debug("at pos res"),s)),Debug("end to apply at the super class")),Identity()).visit(getEnvironment());
      }
    }
  }

  %strategy RenameSuperClass(name:Name) extends Identity() {
    visit ClassDecl {
      decl -> {
        return `decl.setsuper(name);
      }
    }
  }

  public static void main(String[] args) {
    Generator generator = new Generator();
    try {
      generator.generateClasses();
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }

} 
