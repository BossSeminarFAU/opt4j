/*******************************************************************************
 * Copyright (c) 2020 Opt4J
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
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

import com.google.inject.ImplementedBy;

/**
 * 
 * A {@link SimilarityMeasure} computes the similarity of two given vectors.
 * By default implemented by {@link EuclideanDistance}.
 * 
 * @author Kai Amann
 *
 */
@ImplementedBy(EuclideanDistance.class)
public interface SimilarityMeasure {
    /**
     * Computes the similarity of two {@link WeightVector}s.
     * 
     * @param v1 the first {@link WeightVector}
     * @param v2 the second {@link WeightVector}
     * @return the similarity as a number
     *      a lower value indicates a higher similarity
     */
    public double calculateSimilarity(WeightVector v1, WeightVector v2);
}