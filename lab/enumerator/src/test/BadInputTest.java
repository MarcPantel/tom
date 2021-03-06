package test;

import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.number.OrderingComparison.*;

import org.junit.contrib.theories.Theory;
import org.junit.runner.RunWith;

import examples.adt.stack.EmptyStackException;
import tom.library.enumerator.Combinators;
import tom.library.enumerator.Enumeration;
import tom.library.theory.BadInputException;
import tom.library.theory.Enum;
import tom.library.theory.PropCheck;
import tom.library.theory.ForSome;

@RunWith(PropCheck.class)
public class BadInputTest {
	@Theory
	public void testBadInputFailure(
			@ForSome(maxSampleSize = 30) Integer input)
			throws EmptyStackException, BadInputException {
		assumeThat(Math.abs(input), greaterThan(3));
		if (Math.abs(input) < 5) {
			throw new BadInputException();
		}
		assertThat(Math.abs(input), is(greaterThanOrEqualTo(5)));
	}
}
