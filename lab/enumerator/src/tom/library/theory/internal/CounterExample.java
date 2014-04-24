package tom.library.theory.internal;

import java.util.Arrays;
import java.util.List;

import tom.library.sl.Visitable;

public class CounterExample {
	private final List<Object> fUnassigned;
	
	private CounterExample(List<Object> counterExamples) {
		fUnassigned = counterExamples;
	}
	
	public static CounterExample build(Object...counterExamples) {
		return new CounterExample(Arrays.asList(counterExamples));
	}
	
	public CounterExample nextCounterExample() {
		return new CounterExample(fUnassigned.subList(1, fUnassigned.size()));
	}
	
	public Object getCounterExampleObject() {
		return fUnassigned.get(0);
	}
	
	public boolean isEmpty() {
		return fUnassigned.isEmpty();
	}
	
	public boolean isEqualsTo(CounterExample counterExample) {
		boolean equal = true;
		if (fUnassigned.size() != counterExample.fUnassigned.size()) {
			return false;
		}
		for (int i = 0; i < fUnassigned.size(); i++) {
			if (!fUnassigned.get(i).equals(counterExample.fUnassigned.get(i))) {
				equal = false;
				break;
			}
		}
		return equal;
	}
	
	public boolean isSmallerThan(CounterExample counterExample) {
		int total1 = 0, total2 = 0;
		for (int i = 0; i < fUnassigned.size(); i++) {
			total1 += CounterExample.size((Visitable) fUnassigned.get(i));
			total2 += CounterExample.size((Visitable) counterExample.fUnassigned.get(i));
		}
		return total1 < total2;
	}
	
	public static int size(Visitable term) {
		if (term == null) {
			return Integer.MAX_VALUE;
		}
		if (term instanceof Visitable) {
			Visitable t = (Visitable) term;
			int size = sizeRec(0, t);
			return size;
		} else {
			return 0;
		}
	}
	
	private static int sizeRec(int s, Visitable term) {
		if (term.getChildCount() == 0) {
			return 1;
		}
		for (Visitable v : term.getChildren()) {
			s += sizeRec(s, v);
		}
		return s;
	}
}
