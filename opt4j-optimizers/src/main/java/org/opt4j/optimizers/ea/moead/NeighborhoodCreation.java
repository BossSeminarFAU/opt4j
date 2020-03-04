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

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static java.util.stream.Collectors.*;
import static java.util.Map.Entry;

/**
 * 
 * The {@link NeighborhoodCreation} creates the neighborhood for a given
 * {@link WeightVector} during initialization
 * uses a {@link SimilarityMeasure} for finding the closest neighbors
 * uses the {@link EuclideanDistance} by default
 * 
 * @author Kai Amann
 *
 */
public class NeighborhoodCreation {

    private final SimilarityMeasure sm;

    public NeighborhoodCreation() {
        sm = new EuclideanDistance();
    }


     /**
     * @param sm
     *      the {@link SimilarityMeasure} to be used
     */
    public NeighborhoodCreation(SimilarityMeasure sm) {
        this.sm = sm;
    }

    /**
     * Generates the neighborhood out of the input List of {@link WeightVector}s.
     * 
     * @param v 
     *      the reference {@link WeightVector} whose neighborhood should be generated
     * @param candidates       
     *      a List of {@link WeightVector}s
     * @param neighborhoodSize
     *      number of vectors in the neighborhood
     * @return the neighbourhood as a list of indices 
     */
    public int[] create(WeightVector v, List<WeightVector> candidates, int neighborhoodSize) {
        int numCandidates = candidates.size();

        // Catch faulty input
        if (numCandidates < neighborhoodSize) {
            throw new IllegalArgumentException("Cannot create a neighborhood of size " + neighborhoodSize + "with only "
                    + numCandidates + "subproblems!");
        }
        if (v == null) {
            throw new IllegalArgumentException("Reference weight vector cannot be null!");
        }
        if (neighborhoodSize <= 0) {
            throw new IllegalArgumentException("Neighborhood size cannot be equal or smaller than 0!");
        }

        // calculate distance to all candidates
        Map<Integer, Double> distances = new HashMap<>();
        for (int i = 0; i < numCandidates; i++) {
            WeightVector e = candidates.get(i);
            double distance = sm.calculateSimilarity(v, e);
            distances.put(i, distance);
        }

        // from https://www.javacodegeeks.com/2017/09/java-8-sorting-hashmap-values-ascending-descending-order.html
        // Sorts the hasmap w.r.t ascending distances
        Map<Integer, Double> sorted = distances.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        int[] neighborhood = new int[neighborhoodSize];
        int i = 0;
        for (Integer key : sorted.keySet()) {
            if (i >= neighborhoodSize)
                break;
            neighborhood[i] = key;
            i++;

        }
        return neighborhood;
    }
}