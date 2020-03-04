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
 * The {@link CosineSimilarity} computes the similarity 
 * of two given vectors based on the cosine.
 *  
 * @author Kai Amann
 *
 */
public class CosineSimilarity implements SimilarityMeasure {

    /**
     * Computes the similarity of two {@link WeightVector}s.
     * Assumes that orthogonal vectors (=> cos(v1,v2) = 0) are the least similar to each other.
     * Conversely vectors which point into the same  or opposite direction (=> |cos(v1,v2)| = 1) are the most similar.
     * 
     * 
     * @param v1 the first {@link WeightVector}
     * @param v2 the second {@link WeightVector}
     * @return the similarity
     *      a lower value indicates a closer similarity
     */
    public double calculateSimilarity(WeightVector v1, WeightVector v2){
            if (v1.size() != v2.size()){
                throw new IllegalArgumentException("Can't compute cosine similarity of vectors with different dimensions");
            }
            // in [-1, 1]
            double cos = v1.dot(v2) / (v1.L2Norm() * v2.L2Norm());

            // crop to [0,1] 
            cos = Math.abs(cos);

            // in [-1, 0]
            cos -= 1;

            // in [0, 1]
            cos *= -1;
            return cos;
    }
}