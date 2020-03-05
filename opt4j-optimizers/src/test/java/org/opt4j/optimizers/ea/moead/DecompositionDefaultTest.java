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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DecompositionDefaultTest {
    @Test
    public void TestZeroSubproblems() {
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(0, 0, 10);
        assertEquals(0, vecs.size());
        vecs = decomp.decompose(0, 17, 10);
        assertEquals(0, vecs.size());
    }

    @Test
    public void TestZeroObjectives()
    {
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(1, 0, 10);
        assertEquals(0, vecs.get(0).size());
        vecs = decomp.decompose(17, 0, 10);
        assertEquals(0, vecs.get(15).size());
    }
    @Test
    public void SingleObjective()
    {
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(1, 1, 10);
        assertEquals(1, vecs.size());
        assertEquals(1, vecs.get(0).size());
        assertEquals(1.0, vecs.get(0).get(0), 2*Math.ulp(1.0));
    }

    @Test
    public void TwoObjectivesTwoProblems(){
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(2, 2, 10);
        assertEquals(2, vecs.size());
        WeightVector vec1 = vecs.get(0);
        WeightVector vec2 = vecs.get(1);
        assertEquals(2, vec1.size());
        assertEquals(2, vec2.size());
        assertEquals(1.0, vec1.get(0) + vec1.get(1), 2 * Math.ulp(1.0));
        assertEquals(1.0, vec2.get(0) + vec2.get(1), 2 * Math.ulp(1.0));
        assertEquals(1.0, Math.max(vec1.get(0), vec1.get(1)), 2 * Math.ulp(1.0));
        assertEquals(1.0, Math.max(vec2.get(0), vec2.get(1)), 2 * Math.ulp(1.0));
        double min = Math.min(vec1.get(0), vec2.get(0));
        double max = Math.max(vec1.get(0), vec2.get(0));
        assertEquals(1.0, max - min, 2 * Math.ulp(1.0));
    }
    @Test
    public void TwoObjectivesThreeProblems()
    {
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(3, 2, 10);
        assertEquals(3, vecs.size());
        WeightVector middle = null;
        for(int i =0 ; i<3 ; i++)
        {
            WeightVector vec = vecs.get(i);
            assertEquals(2, vec.size());
            double max = Math.max(vec.get(0), vec.get(1));
            double min = Math.min(vec.get(0), vec.get(1));
        if( (max - min) < 1 )
            {
                assertNull(middle);
                middle = vec;
            }
        }
        assertNotNull(middle);

    }

    private double distance(WeightVector i, WeightVector j)
    {
        if(i.size() != j.size()){
            throw new IllegalArgumentException("distance between vectors of different dimension is not defined");
        }
        double dist = 0;
        for(int l = 0; l< i.size(); l++){
            double x = i.get(l) - j.get(l);
            dist += x*x;
        }
        return Math.sqrt(dist);
    }

    @Test
    public void TenObjectives(){
        final int count = 350;
        Decomposition decomp = new DecompositionDefault();
        List<WeightVector> vecs = decomp.decompose(count, 10, 10);
        assertEquals(count, vecs.size());
        List<WeightVector> extrema = new ArrayList<WeightVector>();
        for(WeightVector vec: vecs)
        {
            assertEquals(10, vec.size());
            for(int i = 0; i < 10; i++){
                if(vec.get(i) == 1.0)
                {
                    extrema.add(vec);
                }
            }
        }
        assertEquals(10, extrema.size());
        int pseudoIsolated =0;
        for(int i = 0; i< vecs.size(); i++){
            double min_dist = Double.MAX_VALUE;
            for(int j = 0; j< vecs.size(); j++){
                if(i == j) {
                    continue;
                }
                double dist = distance(vecs.get(i), vecs.get(j));
                if(dist < min_dist){
                    min_dist = dist;
                }
            }
            if(min_dist > 0.25)
            {
                pseudoIsolated++;
            }
            //use assertEquals 0, with the actuall threshold as tollarence to get a report
            //of the actual value
            assertEquals(0, min_dist, 0.6);
        }
        assertEquals(0, pseudoIsolated, count*0.05);
    }
}

