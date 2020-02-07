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
import java.util.List;
import java.util.ArrayList;

public class MaximallySparseSelectionTest{
    @Test
    public void SingleObjectiveTest(){
        Selection select = new MaximallySparseSelection();
        List<WeightVector> vectors = new ArrayList<WeightVector>();
        vectors.add(new WeightVector(new double[] {0.5}));
        List<WeightVector> selected = select.select(vectors,1);
        assertEquals(1, selected.size());
        assertEquals(1, selected.get(0).size());
        assertEquals(1.0, selected.get(0).get(0), 0.0);
        vectors.add(new WeightVector(new double[] {0.0}));
        selected = select.select(vectors,2);
        double x = Math.min(selected.get(0).get(0), selected.get(1).get(0));
        assertEquals(0.0, x, 0.0);

    }

    @Test
    public void ThreeObjectiveTest(){
        Selection select = new MaximallySparseSelection();
        List<WeightVector> vectors = new ArrayList<WeightVector>();
        vectors.add(new WeightVector(new double[] {1/3.0, 1/3.0, 1/3.0}));
        vectors.add(new WeightVector(new double[]{0.1, 0.4, 0.5}));
        vectors.add(new WeightVector(new double[]{0.9, 0, 0.1}));
        List<WeightVector> selected = select.select(vectors, 6);
        assertEquals(6, selected.size());
        assertEquals(0.9, selected.get(3).get(0), 0.0);
        assertEquals(0.1, selected.get(4).get(0), 0.0);
        assertEquals(1/3.0, selected.get(5).get(0), 0.0);
    }
}
