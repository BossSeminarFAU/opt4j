package org.opt4j.optimizers.ea.moead;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 * The {@link RandomSelector} provides functionality to select random parents
 * from a neighbourhood list. 
 * 
 * @author Ufuk Altiparmakoglu
 *
 */
public class RandomSelector implements Selector {

	/**
	 * Randomly selects parents from the provided neighbourhood
	 */
	@Override
	public List<Integer> selectParents(int[] neighbourhood, int numberOfParents) {
		checkIsValidNumberOfParents(neighbourhood);

		int neighbourhoodSize = neighbourhood.length;
		List<Integer> parents = null;

		checkIsValidNumberOfParents(numberOfParents, neighbourhoodSize);

		// Copy all int[] entries into collection
		List<Integer> convertedList = IntStream.of(neighbourhood).boxed().collect(Collectors.toList());

		if (neighbourhoodSize == numberOfParents) {
			parents = convertedList;
		} else {
			// Shuffle list and pick correct amount
			Collections.shuffle(convertedList);
			parents = convertedList.subList(0, numberOfParents);
		}

		return parents;
	}

	private void checkIsValidNumberOfParents(int numberOfParents, int neighbourhoodSize) {
		if (numberOfParents < 2) {
			throw new IllegalArgumentException("Provided number of parents must be greater or equals 2!");
		}

		if (neighbourhoodSize < numberOfParents) {
			throw new IllegalArgumentException("Can not pick " + numberOfParents
					+ " from neighbourhood which only contains " + neighbourhoodSize + " item(s)!");
		}
	}

	private void checkIsValidNumberOfParents(int[] neighbourhood) {
		if (neighbourhood == null) {
			throw new NullPointerException("Provided neighbourhood array is null!");
		}

		if (neighbourhood.length < 2) {
			throw new IllegalArgumentException("Provided neighbourhood array is smaller than 2!");
		}
	}
}
