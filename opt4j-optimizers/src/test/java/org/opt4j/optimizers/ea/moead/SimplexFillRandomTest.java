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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimplexFillRandomTest{
    @Test
    public void SingleObjectiveTest()
    {
        SimplexFill fill = new SimplexFillRandom();
        List<WeightVector> vecs = fill.fill(0,1);
        assertEquals(0, vecs.size());
        vecs = fill.fill(10,1);
        assertEquals(10, vecs.size());
        for(WeightVector vec: vecs)
        {
            assertEquals(1, vec.size());
            assertEquals(1.0, vec.get(0), 2 * Math.ulp(1.0));
        }
    }

    @Test
    public void TwoObjectiveTest()
    {
        SimplexFill fill = new SimplexFillRandom();
        List<WeightVector> vecs = fill.fill(0,2);
        assertEquals(0, vecs.size());
        vecs = fill.fill(1000,2);
        assertEquals(1000, vecs.size());
        Set<Double> values = new HashSet<Double>();
        for(WeightVector vec: vecs)
        {
            assertEquals(2, vec.size());
            assertEquals(1.0, vec.get(0) + vec.get(1), 2 * Math.ulp(1.0));
            values.add(vec.get(0));
            values.add(vec.get(1));
        }
        assertEquals(2000, values.size());
        vecs = fill.fill(1000000,2);
        double[] bins = new double[100];
        double sum = 0;
        for(WeightVector vec: vecs)
        {
            sum+= vec.get(0);
            bins[(int)(vec.get(0)*100)]+=1;
        }
        assertEquals(500000.0, sum, 1000);
        for(double bin: bins)
        {
            assertEquals(10000, bin, 500);
        }
    }

    @Test
    public void TenObjectiveTest(){
        SimplexFill fill = new SimplexFillRandom();
        List<WeightVector> vecs = fill.fill(0,10);
        assertEquals(0, vecs.size());
        vecs = fill.fill(1000,10);
        assertEquals(1000, vecs.size());
        Set<Double> values = new HashSet<Double>();
        for(WeightVector vec: vecs)
        {
            assertEquals(10, vec.size());
            double sum = 0;
            for(int i = 0; i< 10; i++)
            {
                sum+= vec.get(i);
            }
            assertEquals(1.0, sum, 20 * Math.ulp(1.0));
            values.add(vec.get(0));
        }
        assertEquals(1000, values.size());
        int count = 1000*1000*10;
        vecs = fill.fill(count,10);
        double[][] bins = new double[10][100];
        double[] sums = new double[10];
        for(WeightVector vec: vecs)
        {
            for(int i = 0; i < 10; i++){
                sums[i] += vec.get(i);
                bins[i][(int)(vec.get(i)*100)]+=1;
            }
        }
        for(int i = 0; i < 10; i++){
            assertEquals(count/10, sums[i], (count/10)*0.1);
        }
        for(int k = 0; k < 100; k++)
        {
            double max = 0;
            for(int i = 0; i< 10; i++)
            {
                if(bins[i][k] > max){
                    max = bins[i][k];
                }

            }
            for(int i = 0; i <10 ; i++){
                for(int j = i; j <10; j++){
                    double diff = bins[i][k] - bins[j][k];
                    double toll = Math.max(max*0.05, 1000);
                    assertEquals(0, diff, toll);
                }
            }
        }

    }

}