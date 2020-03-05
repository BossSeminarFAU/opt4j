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

import java.util.List;

/**
 * The default implementation for the Decomposition interface As default we use
 * a MSS-R selection. (Deb K., Bandaru S., Seada H. (2019) Generating Uniformly
 * Distributed Points on a Unit Simplex for Evolutionary Many-Objective
 * Optimization. In: Deb K. et al. (eds) Evolutionary Multi-Criterion
 * Optimization. EMO 2019. Lecture Notes in Computer Science, vol 11411.
 * Springer, Cham)
 * 
 * @author Christian Vögl
 */
public class DecompositionDefault implements Decomposition {

    /**
     * @param numProblems   the number of subproblems (number of weight vectors)
     * @param numObjectives the numbe of objectives (number of entries per weight
     *                      vector)
     * @param overfill      controlls how many WeightVectors are randomly generated
     *                      per selected WeightVector
     * @return the weight vectors
     */
    @Override
    public List<WeightVector> decompose(int numProblems, int numObjectives, int overfill) {
        SimplexFill fill = new SimplexFillRandom();
        List<WeightVector> initial = fill.fill(overfill * numProblems, numObjectives);
        Selection select = new MaximallySparseSelection();
        return select.select(initial, numProblems);
    }

}