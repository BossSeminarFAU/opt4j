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

import static org.junit.Assert.assertArrayEquals;
import org.opt4j.optimizers.ea.moead.MOEADModule.SimilarityMeasures;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class NeighborhoodCreationTest {

    @Test
    public void SquareCenterEuclideanTest() {
        /**
         * creates 8 vectors around the weight vector (v) in a square: e.g.: 
         * 5 6 7 
         * 3 v 4 
         * 0 1 2
         */
        int neighborhoodSize = 4;
        WeightVector v = new WeightVector(new double[] { 1, 1 });
        List<WeightVector> candidates = new LinkedList<WeightVector>();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!(i == 1 && j == 1))
                    candidates.add(new WeightVector(new double[] { i, j }));

        NeighborhoodCreation c = new NeighborhoodCreation();
        c.setSimilarityMeasure(SimilarityMeasures.EUCLIDEAN);
        int[] actual = c.create(v, candidates, neighborhoodSize);
        int[] expected = { 1, 3, 4, 6 };
        assertArrayEquals(expected, actual);
    }

    @Test
    public void SquareCenterCosineTest() {
        /**
         * creates 8 vectors around the weight vector (v) in a square: e.g.: 
         * 5 6 7 
         * 3 v 4 
         * 0 1 2
         */
        int neighborhoodSize = 4;
        WeightVector v = new WeightVector(new double[] { 2, 2 });
        List<WeightVector> candidates = new LinkedList<WeightVector>();

        for (int i = 1; i < 4; i++)
            for (int j = 1; j < 4; j++)
                if (!(i == 2 && j == 2))
                    candidates.add(new WeightVector(new double[] { i, j }));

        NeighborhoodCreation c = new NeighborhoodCreation();
        c.setSimilarityMeasure(SimilarityMeasures.COSINE);
        int[] actual = c.create(v, candidates, neighborhoodSize);
        int[] expected = {7, 0, 4, 6};
        assertArrayEquals(expected, actual);
    }


    @Test(expected = IllegalArgumentException.class)
    public void NeighborhoodSizeTestGreaterNumCandidates() {
        int neighborhoodSize = 4;
        WeightVector v = new WeightVector(new double[] { 1, 1 });
        List<WeightVector> candidates = new LinkedList<WeightVector>();

        candidates.add(new WeightVector(new double[] { 2, 2 }));

        NeighborhoodCreation c = new NeighborhoodCreation();
        c.create(v, candidates, neighborhoodSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void NeighborhoodSizeEqZeroTest() {
        int neighborhoodSize = 0;
        WeightVector v = new WeightVector(new double[] { 1, 1 });
        List<WeightVector> candidates = new LinkedList<WeightVector>();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!(i == 1 && j == 1))
                    candidates.add(new WeightVector(new double[] { i, j }));

        NeighborhoodCreation c = new NeighborhoodCreation();
        c.create(v, candidates, neighborhoodSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void WeightVectorEqNull() {
        int neighborhoodSize = 0;
        WeightVector v = null;
        List<WeightVector> candidates = new LinkedList<WeightVector>();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (!(i == 1 && j == 1))
                    candidates.add(new WeightVector(new double[] { i, j }));

        NeighborhoodCreation c = new NeighborhoodCreation();
        c.create(v, candidates, neighborhoodSize);
    }
}