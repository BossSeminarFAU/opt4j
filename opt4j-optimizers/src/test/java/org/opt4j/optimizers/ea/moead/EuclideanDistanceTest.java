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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EuclideanDistanceTest {
    double EPSILON = 2E-10;

    @Test
    public void Vectors2DTest() {
        SimilarityMeasure cs = new EuclideanDistance();
        WeightVector v1 = new WeightVector(new double[] {0, 0});
        WeightVector v2 = new WeightVector(new double[] {1, 0});
        WeightVector v3 = new WeightVector(new double[] {1, 1});

        assertEquals(0, cs.calculateSimilarity(v1, v1), EPSILON);
        assertEquals(1, cs.calculateSimilarity(v1, v2), EPSILON);
        assertEquals(1, cs.calculateSimilarity(v2, v3), EPSILON);
        assertEquals(Math.sqrt(2), cs.calculateSimilarity(v1, v3), EPSILON);     
    }


    @Test
    public void Vectors3DTest() {
        SimilarityMeasure cs = new EuclideanDistance();
        WeightVector v1 = new WeightVector(new double[] {1, 1, 1});
        WeightVector v2 = new WeightVector(new double[] {2, 2, 2});
        WeightVector v3 = new WeightVector(new double[] {1, 5, 1});

        assertEquals(0, cs.calculateSimilarity(v1, v1), EPSILON);
        assertEquals(Math.sqrt(3), cs.calculateSimilarity(v1, v2), EPSILON);
        assertEquals(4, cs.calculateSimilarity(v1, v3), EPSILON);     
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void DifferentDimensionsTest() {
        SimilarityMeasure cs = new EuclideanDistance();
        WeightVector v1 = new WeightVector(new double[] {1, 0, 0});
        WeightVector v2 = new WeightVector(new double[] {0, 1});

        cs.calculateSimilarity(v1, v2);
    }

    
}