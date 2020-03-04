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

public class WeightVector {

	final double[] entries;

	public double L2Norm() {
		double result = 0.0;
		for (double elem : entries) {
			result += elem * elem;
		}
		return Math.sqrt(result);
	}

	public int size() {
		return entries.length;
	}

	WeightVector(double[] entries) {
		assertIsValidDoubleArray(entries);
		this.entries = entries;
	}

	public double get(int index) {
		assertIsValidInteger(index);
		return this.entries[index];
	}

	public double dot(WeightVector v) {
		assertIsValidWeightVector(v);

		double result = 0.0;
		for (int i = 0; i < entries.length; i++) {
			result += entries[i] * v.entries[i];
		}
		return result;
	}

	private void assertIsValidWeightVector(WeightVector v) {
		if (entries.length != v.entries.length) {
			throw new IllegalArgumentException("Can't take dot product of vectors with different sizes");
		}
	}

	private void assertIsValidInteger(int index) {
		if (index < 0 || index >= this.entries.length) {
			throw new ArrayIndexOutOfBoundsException("Provided index is not within bounds");
		}
	}

	private void assertIsValidDoubleArray(double[] entries) {
		if (entries == null) {
			throw new IllegalArgumentException("Provided entries array is null!");
		}
	}
}
