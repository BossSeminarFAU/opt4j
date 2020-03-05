package org.opt4j.optimizers.ea.moead;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.List;

public class RandomSelectorTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNeighbourhoodIsNull() {
		RandomSelector rs = new RandomSelector();
		thrown.expect(NullPointerException.class);
		rs.selectParents(null, 2);
	}

	@Test
	public void testNeighbourhoodIsEmpty() {
		int[] neighbourhood = new int[] {};

		RandomSelector rs = new RandomSelector();
		thrown.expect(IllegalArgumentException.class);
		rs.selectParents(neighbourhood, 2);
	}

	@Test
	public void testNeighbourhoodSizeIsOne() {
		int[] neighbourhood = new int[] { 1 };

		RandomSelector rs = new RandomSelector();
		thrown.expect(IllegalArgumentException.class);
		rs.selectParents(neighbourhood, 2);
	}

	@Test
	public void testNumberOfParentsLessThanTwo() {
		int[] neighbourhood = new int[] { 1, 2, 3, 4 };

		RandomSelector rs = new RandomSelector();
		thrown.expect(IllegalArgumentException.class);
		rs.selectParents(neighbourhood, 1);
	}

	@Test
	public void testSelectParentsWithOneElementInNeighbourhood() {
		int[] neighbourhood = new int[] { 1 };

		RandomSelector rs = new RandomSelector();
		thrown.expect(IllegalArgumentException.class);
		rs.selectParents(neighbourhood, 2);
	}

	@Test
	public void testSelectParentsWithNeighbourhoodSizeEqualsParentsSize() {
		int[] neighbourhood = new int[] { 1, 2 };
		List<Integer> result;

		RandomSelector rs = new RandomSelector();
		result = rs.selectParents(neighbourhood, 2);
		assertNotEquals(result.get(0), null);
		assertNotEquals(result.get(1), null);
		assertEquals(result.get(0), Integer.valueOf(neighbourhood[0]));
		assertEquals(result.get(1), Integer.valueOf(neighbourhood[1]));

		neighbourhood = new int[] { 4, 3, 2, 1 };
		result.clear();
		result = rs.selectParents(neighbourhood, 4);
		assertNotEquals(result.get(0), null);
		assertNotEquals(result.get(1), null);
		assertNotEquals(result.get(2), null);
		assertNotEquals(result.get(3), null);
		assertEquals(result.get(0), Integer.valueOf(neighbourhood[0]));
		assertEquals(result.get(1), Integer.valueOf(neighbourhood[1]));
		assertEquals(result.get(2), Integer.valueOf(neighbourhood[2]));
		assertEquals(result.get(3), Integer.valueOf(neighbourhood[3]));
	}

	@Test
	public void testSelectParentsWithNeighbourhoodSizeSmallerParentsSize() {
		int[] neighbourhood = new int[] { 1, 2 };

		RandomSelector rs = new RandomSelector();
		thrown.expect(IllegalArgumentException.class);
		rs.selectParents(neighbourhood, 3);
	}

	@Test
	public void testSelectParentsDistinct() {
		int[] neighbourhood = new int[] { 1, 2, 3, 4, 5 };
		List<Integer> result;

		RandomSelector rs = new RandomSelector();
		result = rs.selectParents(neighbourhood, 2);

		int r0 = result.get(0);
		int r1 = result.get(1);

		assertNotEquals(r0, null);
		assertNotEquals(r1, null);
		assertNotEquals(r0, r1);
	}

	@Test
	public void testSelectFourParents() {
		int[] neighbourhood = new int[] { 6, 2, 3, 1, 4, 5, 8, 11, 9, 15 };
		List<Integer> result;

		RandomSelector rs = new RandomSelector();
		result = rs.selectParents(neighbourhood, 4);

		assertEquals(4, result.size());
	}
}
