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

import static org.opt4j.core.config.annotations.Citation.PublicationMonth.DECEMBER;

import org.opt4j.core.config.annotations.Info;
import org.opt4j.core.config.annotations.Citation;
import org.opt4j.core.config.annotations.Ignore;
import org.opt4j.core.config.annotations.Order;
import org.opt4j.core.optimizer.MaxIterations;
import org.opt4j.core.optimizer.OptimizerModule;
import org.opt4j.core.start.Constant;

import org.opt4j.optimizers.ea.CrossoverRate;
import org.opt4j.optimizers.ea.EvolutionaryAlgorithmModule.CrossoverRateType;
import org.opt4j.optimizers.ea.ConstantCrossoverRate;

/**
 * The {@link MOEADModule} configures the {@link MOEAD}.
 * 
 * @author Johannes-Sebastian See
 * 
 */

@Info("Multi-Objective Evolutionary Algorithm")
@Citation(authors = "Q. Zhang and H. Li", title = "MOEA/D: A Multiobjective Evolutionary Algorithm Based on Decomposition", journal = "IEEE Transactions on Evolutionary Computation", volume = 11, number = 6, pageFirst = 712, pageLast = 731, month = DECEMBER, year = 2007)
public class MOEADModule extends OptimizerModule {

	@Info("The number of generations.")
	@Order(0)
	@MaxIterations
	protected int generations = 250;

	@Constant(value = "numObjectives", namespace = MOEAD.class)
	@Info("The number of objectives per subproblem")
	@Order(1)
	protected int numObjectives = 5;

	@Constant(value = "numProblems", namespace = MOEAD.class)
	@Info("The number of the subproblems considered.")
	@Order(2)
	protected int numProblems = 20;

	@Constant(value = "neighborhoodSize", namespace = MOEAD.class)
	@Info("The number of the weight vectors in the neighborhood.")
	@Order(3)
	protected int neighborhoodSize = 10;

	@Constant(value = "numberOfParents", namespace = MOEAD.class)
	@Info("The number of Parents from which the new individual will be created from.")
	@Order(4)
	protected int numberOfParents = 2;

	@Constant(value = "newIndividuals", namespace = MOEAD.class)
	@Info("The number of new solutions per iteration.")
	@Order(5)
	protected int newIndividuals = 1;

	@Constant(value = "overfill", namespace = MOEAD.class)
	@Info("Controls the number of WeightVectors that are randomly generated per selected WeightVector.")
	@Order(5)
	protected int overfill = 10;

	@Constant(value = "rate", namespace = ConstantCrossoverRate.class)
	@Info("Performs a crossover operation with this given rate.")
	@Order(7)
	protected double crossoverRate = 0.95;

	
	@Constant(value = "measure", namespace = MOEAD.class)
	@Info("The measure for finding neighboring vectors.")
	@Order(8)
	protected SimilarityMeasures measure = SimilarityMeasures.EUCLIDEAN;

	@Ignore
	protected CrossoverRateType crossoverRateType = CrossoverRateType.CONSTANT;

	/**
	 * The used {@link SimilarityMeasure} .
	 * 
	 * @author Kai Amann
	 */
	public enum SimilarityMeasures {
		/**
		 * Use {@link EuclideanDistance} .
		 */
		EUCLIDEAN,
		/**
		 * Use {@link CosineSimilarity}.
		 */
		COSINE;
	}



	/**
	 * Returns the number of generations.
	 * 
	 * @return the number of generations
	 */
	public int getGenerations() {
		return generations;
	}

	/**
	 * Sets the number of generations.
	 * 
	 * @see #getGenerations
	 * @param generations the number of generations
	 */
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	/**
	 * Returns The number of objectives per subproblem {@code numObjectives}.
	 * 
	 * @return The number of objectives per subproblem
	 */
	public int getNumObjectives() {
		return numObjectives;
	}

	/**
	 * Sets the number of objectives per subproblem {@code numObjectives}.
	 * 
	 * @param numObjectives The number of objectives per subproblem
	 */
	public void setNumObjectives(int numObjectives) {
		this.numObjectives = numObjectives;
	}

	/**
	 * Returns the number of subproblems.
	 * 
	 * @return the number of subproblems
	 */
	public int getNumProblems() {
		return numProblems;
	}

	/**
	 * Sets the number of subproblems.
	 * 
	 * @param numProblems the number of subproblems
	 */
	public void setNumProblems(int numProblems) {
		this.numProblems = numProblems;
	}

	/**
	 * Returns The number of the weight vectors in the neighborhood {@code T}.
	 * 
	 * @return The number of the weight vectors in the neighborhood
	 */
	public int getNeighborhoodSize() {
		return neighborhoodSize;
	}

	/**
	 * Sets the number of weight vectors {@code T}.
	 * 
	 * @param neighborhoodSize The number of the weight vectors
	 */
	public void setNeighborhoodSize(int neighborhoodSize) {
		this.neighborhoodSize = neighborhoodSize;
	}

	/**
	 * Returns the number of parents from which to create new individuals
	 * {@code numberOfParents}.
	 * 
	 * @return the number of parents
	 */
	public int getNumberOfParents() {
		return numberOfParents;
	}

	/**
	 * Sets the number of parents from which to create new individuals
	 * {@code numberOfParents}.
	 * 
	 * @param numberOfParents The number of new Individuals per iteration
	 */
	public void setNumberOfParents(int numberOfParents) {
		this.numberOfParents = numberOfParents;
	}

	/**
	 * Returns The number of new Individuals per iteration {@code newIndividuals}.
	 * 
	 * @return The number of new Individuals per iteration
	 */
	public int getnewIndividuals() {
		return newIndividuals;
	}

	/**
	 * Sets the number of new individuals per iteration {@code newIndividuals}.
	 * 
	 * @param newIndividuals The number of new Individuals per iteration
	 */
	public void setnewIndividuals(int newIndividuals) {
		this.newIndividuals = newIndividuals;
	}

	/**
	 * Returns The overfill {@code overfill}.
	 * 
	 * @return The overfill
	 */
	public int getoverfill() {
		return overfill;
	}

	/**
	 * Sets the overfill {@code newIndividuals}.
	 * 
	 * @param overfill The overfill
	 */
	public void setoverfill(int overfill) {
		this.overfill = overfill;
	}

	/**
	 * Returns the type of crossover rate that is used.
	 * 
	 * @return the crossoverRateType
	 */
	public CrossoverRateType getCrossoverRateType() {
		return crossoverRateType;
	}

	/**
	 * Sets the type of crossover rate to use.
	 * 
	 * @param crossoverRateType the crossoverRateType to set
	 */
	public void setCrossoverRateType(CrossoverRateType crossoverRateType) {
		this.crossoverRateType = crossoverRateType;
	}

	/**
	 * Returns the used crossover rate.
	 * 
	 * @return the crossoverRate
	 */
	public double getCrossoverRate() {
		return crossoverRate;
	}

	/**
	 * Sets the crossover rate.
	 * 
	 * @param crossoverRate the crossoverRate to set
	 */
	public void setCrossoverRate(double crossoverRate) {
		this.crossoverRate = crossoverRate;
	}

	/**
	 * Returns the similarity measure.
	 * 
	 * @return the similarity measure
	 */
	public SimilarityMeasures getMeasure() {
		return measure;
	}

	/**
	 * Set the similarity measure.
	 * 
	 * @param m the similarity measure
	 */
	public void setMeasure(SimilarityMeasures m) {
		this.measure = m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opt4j.start.Opt4JModule#config()
	 */
	@Override
	public void config() {
		bindIterativeOptimizer(MOEAD.class);
		bind(CrossoverRate.class).to(ConstantCrossoverRate.class).in(SINGLETON);
	}
}
