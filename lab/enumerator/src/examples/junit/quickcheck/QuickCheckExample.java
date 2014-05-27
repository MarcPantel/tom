package examples.junit.quickcheck;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.contrib.theories.DataPoint;
import org.junit.contrib.theories.DataPoints;
import org.junit.contrib.theories.Theories;
import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.ForAll;
import com.pholser.junit.quickcheck.From;

import examples.adt.stack.ArrayStack;
import examples.adt.stack.EmptyStackException;

@RunWith(Theories.class)
public class QuickCheckExample {

	@DataPoint
	public static ArrayStack stack = new ArrayStack();
	@DataPoints
	public static int[] n = { 1, 2, 3 };

	@Theory
	public void testTop(ArrayStack s, int n) throws EmptyStackException {
		System.out.println("N: " + n);

		s.push(n);
		assertThat(s.top(), is(n));
	}

	@Theory
	public void testStringQuickcheck(
			@ForAll(sampleSize = 20) @From({ MyGenerator.class }) int n) {
		System.out.println("Quick: " + n);
	}

}
