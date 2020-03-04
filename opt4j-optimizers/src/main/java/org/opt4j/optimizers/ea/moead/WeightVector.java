/*******************************************************************************
 * Copyright (c) 2019 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/

package org.opt4j.optimizers.ea.moead;

/**
 * A class representing a weight vector used for weighting objectives
 * 
 * @author Christian Vögl
 * @author Kai Amann
 *
 */
public class WeightVector {

	private final double[] entries;

	/**
	 * Creates a new {@link WeightVector} from an existing array of numbers
	 * 
	 * @param entries an existing array of numbers
	 */
	WeightVector(double[] entries) {
		isValidDoubleArray(entries);
		this.entries = entries;
	}

	/**
	 * Computes the L2-Norm of the vector
	 *
	 * @return the L2-norm
	 */
	public double L2Norm() {
		double result = 0.0;
		for (double elem : entries) {
			result += elem * elem;
		}
		return Math.sqrt(result);
	}

	/**
	 * Returns the number of entries of the vector
	 * 
	 * @return number of entries
	 */
	public int size() {
		return entries.length;
	}

	/**
	 * Returns the weight coefficient at the specified index
	 * 
	 * @param index the index of the weight coefficient that should be returned
	 * @return the weight coefficient
	 */
	public double get(int index) {
		isValidIndex(index);
		return this.entries[index];
	}

	/**
	 * Computes the dot product of this vector and another {@link WeightVector}
	 * 
	 * @param v the vector with should be used for the dot product computation
	 * @return the dot product
	 */
	public double dot(WeightVector v) {
		isValidWeightVector(v);

		double result = 0.0;
		for (int i = 0; i < entries.length; i++) {
			result += entries[i] * v.entries[i];
		}
		return result;
	}

	/**
	 * Checks the {@link WeightVector} v for validity
	 * 
	 * @param v the vector to be checked
	 * @throws IllegalArgumentException when the vector is not valid
	 */
	private void isValidWeightVector(WeightVector v) {
		if (entries.length != v.entries.length) {
			throw new IllegalArgumentException("Can't take dot product of vectors with different sizes");
		}
	}

	/**
	 * Checks if the given index is within bounds of the current vector
	 * 
	 * @param index the index to be checked
	 * @throws ArrayIndexOutOfBoundsException when the index is not valid
	 */
	private void isValidIndex(int index) {
		if (index < 0 || index >= this.entries.length) {
			throw new ArrayIndexOutOfBoundsException("Provided index is not within bounds");
		}
	}

	/**
	 * Checks the given array for validity
	 * 
	 * @param entries the array to be checked
	 * @throws IllegalArgumentException when the array is not valid
	 */
	private void isValidDoubleArray(double[] entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Provided entries array is null!");
		}
	}
}
