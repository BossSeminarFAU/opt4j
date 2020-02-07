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

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.opt4j.core.common.random.RandomMersenneTwister;


/**
* Fills a unit simplex of dimension m with N points, selected after Fig. 1 in
* A. Jaszkiewicz, "On the performance of multiple-objective genetic local
* search on the 0/1 knapsack problem - a comparative experiment," in IEEE
* Transactions on Evolutionary Computation, vol. 6, no. 4, pp. 402-412, Aug.
* 2002.
* @author Christian Vögl
*/
class SimplexFillRandom implements SimplexFill {
    private Random rand;

    public SimplexFillRandom() {
        rand =  new RandomMersenneTwister(0);
    }
    @Override
    public List<WeightVector> fill (int N, int m)
    {
        List<WeightVector> points =new ArrayList<>();

        for(int i = 0; i<N; i++)
        {
            double sum  = 0;
            double[] values = new double[m];
            double u = rand.nextDouble();
            if(m > 1){
                values[0] = 1 - Math.pow(u, 1.0 / (m -1));

                for(int k = 2; k<m; k++)
                {
                    u = rand.nextDouble();

                    double factor = 1;
                    for(int j = 0; j< k-1; j++)
                    {
                        factor -= values[j];
                    }
                    double z = 1 - Math.pow(u,(1.0/(m-k)));
                    values[k-1] = factor * z;
                }

                for( int j = 0; j <m-1; j++)
                {
                    sum+= values[j];
                }
            }
            if(m>0){
                values[m-1] = 1 - sum;
            }
            points.add(new WeightVector(values));
        }
        return points;
    }
 }