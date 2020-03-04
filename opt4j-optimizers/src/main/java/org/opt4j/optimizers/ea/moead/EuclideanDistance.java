/*******************************************************************************
 * Copyright (c) 2014 Opt4J
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

/**
 * 
 * The {@link EuclideanDistance} computes the euclidean distance between two given vectors
 * 
 * @author Kai Amann
 *
 */
public class EuclideanDistance implements SimilarityMeasure {

    /**
     * Computes the euclidean distance between two {@link WeightVector}s.
     * 
     * @param v1 the first {@link WeightVector}
     * @param v2 the second {@link WeightVector}
     * @return the euclidean distance
     */
    public double calculateSimilarity(WeightVector v1, WeightVector v2){
            if (v1.size() != v2.size()){
                throw new IllegalArgumentException("Can't compute euclidean distance of vectors with different dimensions");
            }
            double distanceSquared = 0;
            for(int i = 0; i < v1.size(); i++){
                distanceSquared += (v1.get(i) - v2.get(i)) * (v1.get(i) - v2.get(i));
            }
            return Math.sqrt(distanceSquared);
    }
}