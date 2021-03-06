with System; use System;
package body Successeur is

	%typeterm Nat {
	  implement {Integer}
	  is_sort(t) { True }
	}

	%op Nat zero() {
	  is_fsym(t) { $t = 0 }
	  make() { 0 }
	}

	%op Nat suc(p:Nat) {
	  is_fsym(t) { $t > 0 }
	  get_slot(p,t) { $t-1 }
	  make(t) { $t+1 }
	}

	function plus(t1: Integer; t2: Integer) return Integer is
	begin
	  %match(Nat t1, Nat t2) {
		x,zero()   -> {
			return `x;
		}
		x,suc(y) -> {
			return `suc(plus(x,y));
		}
		_,_ -> {
			return `zero(); -- should never occur but the Ada compiler does not know it.
		}
	  }
	end plus;

end Successeur;
