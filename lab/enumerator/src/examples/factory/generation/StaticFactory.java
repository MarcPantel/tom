package examples.factory.generation;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import tom.library.enumerator.Enumeration;
import examples.factory.Color;

public class StaticFactory extends AbstractGeneratorFactory{

	@Override
	protected StringBuilder appendImports(Class<?>c,String packagePath){
		StringBuilder sb=super.appendImports(c,packagePath);
		appendln(sb, makeImport(List.class));
		appendln(sb, makeImport(Field.class));
		appendln(sb, makeImport(MyIntrospection.class));
		appendln(sb, makeImport(FieldConstructor.class));
		
		return sb;
	}
	
	
	
	@Override
	protected StringBuilder core(FieldConstructor fc,String packagePath) throws IOException {
		// TODO Auto-generated method stub
		Class<?>c=fc.getTypeChamp();
		String CLASSNAME=c.getSimpleName();
		
		StringBuilder sb=new StringBuilder();
		
		appendln(sb, "public static final Enumeration<"+CLASSNAME+"> getEnumeration() {");
		appendln(sb, "Enumeration<"+CLASSNAME+"> enumRes = null;");
		appendln(sb, "try{");
		appendln(sb, "final Enumeration<"+CLASSNAME+"> emptyEnum = Enumeration.singleton(null);");
		int a=0;
		List<Field> listFC=MyIntrospection.getAllFieldFromEnumerateStaticClass(c);
		Map<String,Object>map=MyIntrospection.getValueStatic(c);
		
		try {
			sb.append(lesStatics(c));
		} catch (NoSuchFieldException | SecurityException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Field f:listFC){
			appendln(sb, "final Enumeration<"+CLASSNAME+"> "+f.getName()+"Enum = Enumeration.singleton("+f.getName()+");");
			a++;
		}
		
		if(!listFC.isEmpty())appendln(sb, "enumRes = "+listFC.get(a-1).getName()+"Enum;");
		for(int i=a-2;i>=0;i--){
			appendln(sb, "enumRes = enumRes.pay().plus("+listFC.get(i).getName()+"Enum);");
		}
		
		appendln(sb, "}catch(ClassNotFoundException | IllegalArgumentException| IllegalAccessException e){");
		
		appendln(sb, "}");
		appendln(sb, "return enumRes;");
		appendln(sb, "}");
		
		return sb;
	}
	
	public StringBuilder lesStatics(Class c) throws NoSuchFieldException, SecurityException, ClassNotFoundException{
		StringBuilder sb=new StringBuilder();
		appendln(sb, "Object obj=Class.forName(\""+c.getCanonicalName()+"\");");
		appendln(sb, "List<Field> listFC=MyIntrospection.recupererTousChampEnumerateStatic("+c.getCanonicalName()+".class);");
		List<Field> listFC=MyIntrospection.getAllFieldFromEnumerateStaticClass(c);
		
		appendln(sb, "Field f;");
		for(Field fc:listFC){
			appendln(sb, "f=MyIntrospection.getField("+fc.getType().getCanonicalName()+".class,\""+fc.getName()+"\");");
		//c.getCLASS() n'est peut etre pas bon faudrait peut etre faire plus de reflection
			String clas=fc.getType().getCanonicalName();
			appendln(sb, clas+" "+fc.getName()+"=("+clas+")f.get(obj);");
		}
		
		return sb;
	}


}
