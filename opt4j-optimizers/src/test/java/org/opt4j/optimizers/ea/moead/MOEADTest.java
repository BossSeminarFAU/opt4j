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

import static org.mockito.Mockito.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opt4j.core.Individual;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.Objectives;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.optimizers.ea.Mating;

public class MOEADTest {

    @Rule
	public ExpectedException thrown = ExpectedException.none();

    private IndividualFactory individualFactory;

    private IndividualCompleter completer;

    private Selector selector;

    private Mating mating;

    private Decomposition decomposition;

    private NeighborhoodCreation neighborhoodCreation;

    private Population population;

    private Repair repair;

    private MOEAD createMOEAD(int numObjectives, int numProblems, int neighborhoodSize, int numberOfParents,
            int newIndividuals, int overfill) {
        Individual i = mock(Individual.class);
        when(i.getObjectives()).thenReturn(new Objectives());

        population = mock(Population.class);
        when(population.size()).thenReturn(0, 1, numProblems);
        when(population.add(any(Individual.class))).thenReturn(true);
        Individual[] pop = new Individual[numProblems];
        for (int j = 0; j < numProblems; j++)
            pop[j] = i;
        when(population.toArray(any(Individual[].class))).thenReturn(pop);
        when(population.remove(any(Individual.class))).thenReturn(true);

        individualFactory = mock(IndividualFactory.class);

        completer = mock(IndividualCompleter.class);

        selector = mock(Selector.class);
        doReturn(new ArrayList<Integer>(Collections.nCopies(numberOfParents, 0))).when(selector)
                .selectParents(any(int[].class), anyInt());

        mating = mock(Mating.class);
        Collection<Individual> collection = new ArrayList<Individual>(newIndividuals);
        for(int j = 0; j < newIndividuals; j++)
                collection.add(i);
        when(mating.getOffspring(anyInt(), anyCollection())).thenReturn(collection);

        decomposition = mock(Decomposition.class);
        WeightVector w = mock(WeightVector.class);
        List<WeightVector> wlist = new ArrayList<>(numProblems);
        for(int j = 0; j < numProblems; j++)
                wlist.add(w);
        when(decomposition.decompose(anyInt(), anyInt())).thenReturn(wlist);

        neighborhoodCreation = mock(NeighborhoodCreation.class);
        when(neighborhoodCreation.create(any(WeightVector.class), anyList(), anyInt()))
                .thenReturn(new int[neighborhoodSize]);

        repair = mock(Repair.class);
        when(repair.repairSolution(any(Individual.class))).then(returnsFirstArg());

        return new MOEAD(population, individualFactory, completer, selector, mating, decomposition,
                neighborhoodCreation, repair, numObjectives, numProblems, neighborhoodSize, numberOfParents,
                newIndividuals, overfill);
    }

    @Test
    public void testInitialize() throws TerminationException {
        MOEAD moead = createMOEAD(5, 30, 10, 2, 1, 0);

        moead.initialize();
    }

    @Test
    public void testNext() throws TerminationException {
        MOEAD moead = createMOEAD(5, 30, 10, 2, 2, 0);

        moead.initialize();
        moead.next();
    }
    @Test
    public void testNumObjectives() {
        thrown.expect(IllegalArgumentException.class);
        createMOEAD(0, 30, 10, 2, 1, 0);
    }

    @Test
    public void testNumProblems() {
        thrown.expect(IllegalArgumentException.class);
        createMOEAD(5, 0, 10, 2, 1, 0);
    }

    @Test
    public void testNeighborhoodSize() {
        thrown.expect(IllegalArgumentException.class);
        createMOEAD(5, 30, 0, 2, 1, 0);
    }

    @Test
    public void testNumberOfParents() {
        thrown.expect(IllegalArgumentException.class);
        createMOEAD(5, 30, 10, 0, 1, 0);
    }

    @Test
    public void testNewIndividuals() {
        thrown.expect(IllegalArgumentException.class);
        createMOEAD(5, 30, 10, 2, 0, 0);
    }

}