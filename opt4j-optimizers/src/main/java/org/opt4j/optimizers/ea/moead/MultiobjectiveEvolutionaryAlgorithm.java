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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.opt4j.core.Individual;
import org.opt4j.core.Objectives;
import org.opt4j.core.IndividualFactory;
import org.opt4j.core.optimizer.IndividualCompleter;
import org.opt4j.core.optimizer.IterativeOptimizer;
import org.opt4j.core.optimizer.Population;
import org.opt4j.core.optimizer.TerminationException;
import org.opt4j.core.start.Constant;
import org.opt4j.optimizers.ea.Mating;
import org.opt4j.core.optimizer.Archive;
import org.opt4j.core.common.archive.UnboundedArchive;

import com.google.inject.Inject;

/**
 * The {@link MultiobjectiveEvolutionaryAlgorithm} is an implementation of an Evolutionary
 * Algorithm that uses {@link Decomposition} on a Multiobjective optimization Problem.
 * It is based on the ideas introduced in the paper 
 * "MOEA/D: A Multiobjective Evolutionary Algorithm Based on Decomposition" written by Qingfu Zhang and Hui Li.
 * 
 * @author Johannes-Sebastian See
 * 
 */
public class MultiobjectiveEvolutionaryAlgorithm implements IterativeOptimizer {

	protected final int m;

	protected final int n;

	protected final int t;

	protected final int numberOfParents;

	protected final int newIndividuals;

	private final IndividualFactory individualFactory;

	private final IndividualCompleter completer;

	protected final Selector selector;

	protected final Mating mating;

	protected final Decomposition decomposition;

	protected final NeighborhoodCreation neighborhoodCreation;

	private final Population population;
	
	private final Repair repair; 

	protected List<WeightVector> weights;
	
	protected List<int []> neighborhoods;

	protected Archive externalPopulation;

	protected double[] referencePoints;
	
	protected Individual[] x;

	/**
	 * Constructs an {@link MultiObjectiveEvolutionaryAlgorithm} with a {@link Population}, a
	 * {@link Selector}, a {@link mating}, a
	 * {@link decomposition}, a {@link repait}, the number of generations, the
	 * number of objective functions per subproblem, the number of subproblems, the number of wiehgt vectors in the neighborhood, 
	 * and the number of new Individuals per iteration.
	 * 
	 * @param population
	 *            the population
	 * @param individualFactory
	 *            the individual factory
	 * @param completer
	 *            the completer
	 * @param selector
	 *            the selector
	 * @param mating
	 *            the mating method
	 * @param decomposition
	 * 			  the decomposition method
	 * @param neighborhoodCreation
	 * 			  the neighborhood-creation method
	 * @param repair
	 * 			  the repair method
	 * @param m
	 * 			  the number of objective functions	and entries of a weight vector
	 * @param n
	 *            the number of subproblems
	 * @param t
	 *            the number of weight vectors in the neighborhood
	 * @param numberOfParents
	 * 			  the number of parents from which to create new individuals
	 * @param newIndividuals
	 * 			  the number of new Individuals created by the mating method
	 */
	@Inject
	public MultiobjectiveEvolutionaryAlgorithm(
			Population population,
			IndividualFactory individualFactory,
			IndividualCompleter completer,
			Selector selector,
			Mating mating,
			Decomposition decomposition,
			NeighborhoodCreation neighborhoodCreation,
			Repair repair,
			@Constant(value = "m", namespace = MultiobjectiveEvolutionaryAlgorithm.class) int m,
			@Constant(value = "n", namespace = MultiobjectiveEvolutionaryAlgorithm.class) int n,
			@Constant(value = "t", namespace = MultiobjectiveEvolutionaryAlgorithm.class) int t,
			@Constant(value = "numberOfParents", namespace = MultiobjectiveEvolutionaryAlgorithm.class) int numberOfParents,
			@Constant(value = "newIndividuals", namespace = MultiobjectiveEvolutionaryAlgorithm.class) int newIndividuals ) {
		this.selector = selector;
		this.individualFactory = individualFactory;
		this.completer = completer;
		this.mating = mating;
		this.decomposition = decomposition;
		this.neighborhoodCreation = neighborhoodCreation;
		this.repair = repair;
		this.m = m;
		this.n = n;
		this.t = t;
		this.numberOfParents = numberOfParents;
		this.newIndividuals = newIndividuals;
		this.population = population;

		if (m <= 0) {
			throw new IllegalArgumentException("Invalid m: " + m);
		}
		if (n <= 0) {
			throw new IllegalArgumentException("Invalid N: " + n);
		}
		if (t <= 0) {
			throw new IllegalArgumentException("Invalid T: " + t);
		}
		if (newIndividuals <= 0) {
			throw new IllegalArgumentException("Invalid newIndividuals: " + newIndividuals);
		}
		if(numberOfParents < 1){
			throw new IllegalArgumentException("Invalid numberOfParents: " + numberOfParents);	
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#initialize()
	 * 
	 */
	@Override
	public void initialize() {
		weights = decomposition.decompose(n, m);
		
		// Step 1.1
		externalPopulation = new UnboundedArchive();

		// Step 1.2
		neighborhoods = new ArrayList<>(n);
		for( int i = 0; i < n; i++){
			neighborhoods.add(neighborhoodCreation.create(weights.get(i), weights, t));
		}

		// Step 1.3
		while (population.size() < n) {
			population.add(individualFactory.create());
		}
			
		x = new Individual[n];
		x = population.toArray(x);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.core.optimizer.IterativeOptimizer#next()
	 */
	@Override
	public void next() throws TerminationException {
		completer.complete(population);	
		for( int i = 0; i < n; i++) {
			// Step 2.1) Reproduction
			List<Integer> parents = selector.selectParents(neighborhoods.get(i), numberOfParents);
			List<Individual> parentCollection = new ArrayList<>(parents.size());
			for(int j = 0; j < parents.size(); j++){
				parentCollection.add(x[parents.get(j)]);
			}
			
			Collection<Individual> offspring = mating.getOffspring( newIndividuals , parentCollection);
			completer.complete(offspring);
			Iterator<Individual> iter = offspring.iterator();
			Individual best = iter.next();
			
			// Step 2.2) Improvement
			while(iter.hasNext()){
				Individual toCheck = iter.next();
				if(toCheck.getObjectives().weaklyDominates(best.getObjectives()))
					best = toCheck;
			}

			best = repair.repairSolution(best);

			// Step 2.4) Update of Neighboring Solutions
			Objectives objectives = best.getObjectives();
			for(int j = 0; j < t; j++){
				Individual toCheck = x[ neighborhoods.get(i)[j] ];
				if(objectives.weaklyDominates(toCheck.getObjectives() )){
					x[ neighborhoods.get(i)[j] ] = best;
					population.remove(toCheck);
					population.add(best);
				}
			}

			// Step 2.5) Update of EP
			externalPopulation.update(best);
		}
	}
}
