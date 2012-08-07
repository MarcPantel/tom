with Ada.Text_IO;
use  Ada.Text_IO;
package body AbstractStrategyCombinatorPackage is

	procedure initSubterm(sc : in out AbstractStrategyCombinator) is
	begin
		sc.arguments := null;
	end;
		
	procedure initSubterm(sc : in out AbstractStrategyCombinator; str : StrategyPtr) is
	begin
		sc.arguments := new ObjectPtrArray'( 0 => ObjectPtr(str) );
	end;
	
	procedure initSubterm(sc : in out AbstractStrategyCombinator; str1,str2 : StrategyPtr) is
	begin
		sc.arguments := new ObjectPtrArray'( ObjectPtr(str1), ObjectPtr(str2)  );
	end;
	
	
	procedure initSubterm(sc : in out AbstractStrategyCombinator; str1,str2,str3 : StrategyPtr) is
	begin
		sc.arguments := new ObjectPtrArray'( ObjectPtr(str1), ObjectPtr(str2), ObjectPtr(str3)  );
	end;
	
	procedure initSubterm(sc : in out AbstractStrategyCombinator; str : ObjectPtrArray) is
	begin
		sc.arguments := new ObjectPtrArray( str'range );
		
		for i in str'range loop
			sc.arguments(i) := str(i);
		end loop;
		
	end;
	
	function getVisitors(sc: AbstractStrategyCombinator) return ObjectPtrArray is
		arr :  ObjectPtrArray(sc.arguments'range);
	begin
		for i in arr'range loop
			arr(i) := sc.arguments(i);
		end loop;
		
		return arr;
	end;
	
	function getVisitor(sc: AbstractStrategyCombinator ; i : Integer) return Strategy'Class is
	begin
		return Strategy'Class(sc.arguments(i).all);
	end;
	
	----------------------------------------------------------------------------
	-- Visitable implementation
	----------------------------------------------------------------------------
	
	overriding
	function getChildCount(v: AbstractStrategyCombinator) return Integer is
	begin
		if v.arguments = null then
			return 0;
		else
			return v.arguments'Length;
		end if;
	end;
	
	overriding
	function getChildAt(v: AbstractStrategyCombinator; i : Integer) return VisitablePtr is
	begin
		return VisitablePtr(v.arguments(i));
	end;
	
	overriding
	procedure setChildAt(v: in out AbstractStrategyCombinator; i: in Integer; child: in VisitablePtr) is
	begin
		v.arguments(i) := ObjectPtr(child);
	end;
	
	overriding
	procedure setChildren(v: in out AbstractStrategyCombinator ; children : ObjectPtrArrayPtr) is
	begin
		if children /= null then
			v.arguments := new ObjectPtrArray(children'range);
			
			for i in children'range loop
				v.arguments(i) := new Object'Class'( children(i).all );
			end loop;
		else
			v.arguments := null;
		end if;
	end;
	
	overriding
	function getChildren(v: AbstractStrategyCombinator) return ObjectPtrArrayPtr is
	begin
		return v.arguments;
	end;
	
	----------------------------------------------------------------------------
	
end AbstractStrategyCombinatorPackage;

