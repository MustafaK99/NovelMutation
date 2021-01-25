package solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EvolutionaryNovel {
	public static final int POPULATION_SIZE = 100;
	public static final int SELECTION_SIZE = 50;
	public static final int GENERATIONS = 100;
	public static final int MUTATION_PROBABILITY =  70;
	public static final int RECOMBINATION_PROBABILITY = 100;
	
	
	
	private ArrayList<Solution> Population;
	private double[] stockCosts;
	private ArrayList<Solution> Parents;
	private int[] peicesAvailable;
	private int totalRequired;
	private int[] quantities;
	private double bestCost;
	private int[] stockLengthsAvailable;
	private int mutationCounter = 0;
	private int novelMutationCounter = 0;
	
	public EvolutionaryNovel (ProblemInstance givenPi) {
		
		
		this.Population = new ArrayList<Solution>();
		this.stockCosts = givenPi.getStockCosts();
		this.Parents = new ArrayList<Solution>();
		this.peicesAvailable = givenPi.getPieceLengths();
		this.totalRequired = givenPi.getTotalRequired();
		this.quantities = givenPi.getQuantities();
		this.stockLengthsAvailable = givenPi.getStockLengths();
	}
	
	public void solve() {
		
		generatePopulation();
		double bestCost = bestCostForPopulation();
		System.out.println(bestCost);
		
		for(int i = 0; i < EvolutionaryNovel.GENERATIONS; i++) {
			int printVal = i + 1;
			//System.out.println("Generation " + printVal);
			generateNextGen();
			this.bestCost = bestCostForPopulation();
			System.out.println(this.bestCost);
			//System.out.println("mutation occured this much times in this generation " + this.mutationCounter + " times");
			//System.out.println("this number of novel mutations occured " + this.novelMutationCounter);
			this.mutationCounter = 0;
			this.novelMutationCounter = 0;
		}
		
	}
	
	public void generateNextGen() {
		
		parentSelection();
		ArrayList<Solution> Children = produceChildren();
		this.Population.addAll(0, Children);
		ProblemInstance pi = new ProblemInstance();
		ArrayList<Solution> diversityMaintainer = new ArrayList<Solution>();
		for(int i = 0; i < 10 ; i++) {
			diversityMaintainer.add(pi.generateSolution());
			
		}
		
		int index = this.Population.size() - 10;
		this.Population.addAll(index, diversityMaintainer);
		
		
	}
	
	public void generatePopulation() {
		
		for(int i = 0; i < EvolutionaryNovel.POPULATION_SIZE; i++) {
			ProblemInstance pi = new ProblemInstance();
			pi.generateTotal();
			Solution currentSolution = pi.generateSolution();
			Population.add(currentSolution);
		}
	
		
	}
	
	public double bestCostForPopulation() {
		double currentBestCost = calculateCost(Population.get(0)); 
		for(int i = 1; i < Population.size(); i++) {
			Solution solution = Population.get(i);
			  double currentCost = calculateCost(solution); 
			  if(currentCost < currentBestCost) {	  
				  currentBestCost = currentCost;
			  }
			
		}
		

		return currentBestCost;
	}

	
	public double calculateCost(Solution givenSolution) {
		 ArrayList<Activity> listOfActivities = givenSolution.getListOfActivities();
		 double cost = 0;
		 for(int i = 0; i < listOfActivities.size(); i++) {
			Activity currentActivity=  listOfActivities.get(i);		
			int stockLengthUsed = currentActivity.getStockLengthUsed();
			if(stockLengthUsed == 120) {
				cost = cost + this.stockCosts[0];
			}
			else if(stockLengthUsed == 115) {
				cost = cost + this.stockCosts[1];
			}
			else if(stockLengthUsed == 110) {
				cost = cost + this.stockCosts[2];
			}
			else if(stockLengthUsed == 105) {
				cost = cost + this.stockCosts[3];
			}
			else if(stockLengthUsed == 100) {
				cost = cost + this.stockCosts[4];
			}
			
		 }
		
		return cost;
		
		
		
	}
	
	

	public void parentSelection() {
		Random random = new Random();
		for(int i = 0; i < SELECTION_SIZE; i++) {
			Solution bestParent;
			double bestCost = 0.0;
			ArrayList<Solution> potentialParent = new ArrayList<Solution>();
			for(int j = 0; j < 10 ; j++) {
				potentialParent.add(this.Population.get(random.nextInt(100)));	
			}
			bestParent = potentialParent.get(0);
			bestCost  = calculateCost(potentialParent.get(0));
			for(int k = 1; k < potentialParent.size(); k ++) {
				if(calculateCost(potentialParent.get(k)) < bestCost){
					bestCost = calculateCost(potentialParent.get(k));
					bestParent = potentialParent.get(k);
				}
				
			}
			this.Parents.add(bestParent);
			
		}

	}
	
	public ArrayList<Solution> produceChildren() {
		ArrayList<Solution> currentParents = this.Parents;
		ArrayList<Solution> children = new ArrayList<Solution>();
		for(int i = 0; i < POPULATION_SIZE; i++) {
			Collections.shuffle(currentParents);
			List<Solution> selectedParents = currentParents.subList(0, 2);
			Random rn = new Random();
			Solution currentChild = new Solution(selectedParents.get(rn.nextInt(selectedParents.size())).getListOfActivities());
			if(rn.nextInt(100) <= EvolutionaryNovel.RECOMBINATION_PROBABILITY) {
			     currentChild = orderOneCrossOver(selectedParents.get(0),selectedParents.get(1));
			}
			if(rn.nextInt(100) <= EvolutionaryNovel.MUTATION_PROBABILITY) {
				currentChild = mutate(currentChild);
			     this.mutationCounter++;
			}
			children.add(currentChild);
		}
 		
		return children;
	}
	
	
	public Solution orderOneCrossOver(Solution parent1, Solution parent2) {
           ArrayList<Activity> parentOneActivities = parent1.getListOfActivities();
           ArrayList<Activity> parentTwoActivities = parent2.getListOfActivities();
           
           int parentOneEnd =  parentOneActivities.size()/2;
           int parentTwoStart = parentTwoActivities.size()/2;
           
           parentOneEnd = (int) Math.floor(parentOneEnd);
           parentTwoStart = (int) Math.ceil(parentTwoStart);
           
           ArrayList<Integer> childStockLengths = new ArrayList<Integer>();
           
           for(int i = 0; i < parentOneEnd; i ++) {
        	   childStockLengths.add((parentOneActivities.get(i).getStockLengthUsed()));	   
           }
           
           for(int i = parentTwoStart; i < parentTwoActivities.size(); i++) {
        	   childStockLengths.add(parentTwoActivities.get(i).getStockLengthUsed());
        	   
           }
           
            ArrayList<Activity> solutionGenerated = generateActivitiesForChild(childStockLengths);
            Solution solution  = new Solution(solutionGenerated);
		
		
		return solution;
	}
	
	public ArrayList<Activity> generateActivitiesForChild(ArrayList<Integer> stockLengthGiven) {
		ArrayList<Integer> stockLengthUsed = new ArrayList<Integer>();
		ArrayList<Integer> ArrayListRecipes = new ArrayList<Integer>();
		ArrayList<Activity> Solution = new ArrayList<Activity>();
		int leftOverAmount = 0;  //variable that holds amount of leftOver stock
		int amountUsed = 0;
		for(int i = 0; i < this.peicesAvailable.length; i++ ) {
			
			    int currentStock = stockLengthGiven.get(i); //pick a random stock length
			    if(amountUsed < totalRequired) {
			    	stockLengthUsed.add(currentStock);//add it to the stock lengths used
			    }
				int stockLengthLeft = currentStock; //store as local variable stockLengthLeft
				int currentRequired = quantities[i]; // get the current amount required for the given quantity
				while(currentRequired > 0) {
					if(leftOverAmount >= this.peicesAvailable[i]) {   //check if there is left over stock of sufficient size use
						leftOverAmount = leftOverAmount - this.peicesAvailable[i]; //this first
						amountUsed++;
						ArrayListRecipes.add(this.peicesAvailable[i]);     //add this to a list of all pieces subtracted so far
						currentRequired = currentRequired - 1;	 //reduce the amount required		
					}
					else {
						leftOverAmount = 0;                       //set left over to zero because it is not enough
						if(stockLengthLeft >= this.peicesAvailable[i]) {                        //check if stock length is sufficient
							stockLengthLeft = stockLengthLeft - this.peicesAvailable[i];        //for required piece subtract if it is
							amountUsed++;
							ArrayListRecipes.add(this.peicesAvailable[i]);             //add this to a list of all pieces subtracted so far
							currentRequired = currentRequired - 1;  //reduce the amount required
						}
						else {
							
							if(amountUsed < this.totalRequired) {
							   stockLengthLeft = stockLengthGiven.get(i); //generate a random amount of stock
							   stockLengthUsed.add(stockLengthLeft);        //if the current one is not big enough
							}                                                         //add it to the stock already used
						}                                                    
						
					}
				}
				leftOverAmount = stockLengthLeft;  // update any left over stock
						
		}
		
		
		int peiceIndex = 0;
		for(int i = 0; i < stockLengthUsed.size(); i++) {
			
			int currentStockLength = stockLengthUsed.get(i);
			int amountLeft = currentStockLength;
			ArrayList<Integer> stockPeicesUsed = new ArrayList<Integer>();
			//System.out.println("Stock length used " + this.stockLengthUsed.get(i));
			while(amountLeft >= ArrayListRecipes.get(peiceIndex)) {
				
				amountLeft = amountLeft - ArrayListRecipes.get(peiceIndex);
				stockPeicesUsed.add(ArrayListRecipes.get(peiceIndex));
				//System.out.println(this.ArrayListRecipes.get(peiceIndex));
				peiceIndex++;
				if(peiceIndex == this.totalRequired) {
					break;
				}
			}
			Activity currentAcitivity = new Activity(currentStockLength,stockPeicesUsed) ;
			Solution.add(currentAcitivity);
			if(peiceIndex == this.totalRequired) {
				break;
			}
			
		}
		
		return Solution;
	
	}
	
	public Solution mutate(Solution currentChild) {
		
		ArrayList<Activity> activities = currentChild.getListOfActivities();
	    for(int i = 0; i < activities.size()/2;i++) {
	    	 Activity currentActivity = activities.get(i);
	    	 int currentStockLength = currentActivity.getStockLengthUsed();
	    	 double currentCost = stockLengthCostGen(currentStockLength);
	    	 int totalCostForRecipe = currentActivity.activitiesTotal();
	    	 for(int j = 0; j < this.stockLengthsAvailable.length; j++) {
	   
	    		 if(currentStockLength != stockLengthsAvailable[j]) {
	    			 int potentialStockLength = stockLengthsAvailable[j];
	    			 double potentialCost = stockLengthCostGen(potentialStockLength);
	    			 if(potentialStockLength - totalCostForRecipe >= 0 && potentialCost < currentCost) {
	    				
	    				 novelMutationCounter++;
	    				 currentActivity.setStockLengthUsed(potentialStockLength);
	    				 activities.add(i,currentActivity );
	    				 break;
	    				 
	    			 }
	    			 /*else if(potentialStockLength - totalCostForRecipe >= 0){
	    				 currentActivity.setStockLengthUsed(potentialStockLength);
	    				 activities.add(i,currentActivity );
	    				 break;
	    				 
	    			 }*/
	    			 
	    		 }
	    		 
	    		 
	    	 }
	    	
	    	
	    }
	    
	    currentChild.setListOfActivities(activities);
		
		return currentChild;
	}
	
	public double stockLengthCostGen(int stockLength) {
		double cost = 0;
		if(stockLength == 120) {
			cost =  this.stockCosts[0];
		}
		else if(stockLength == 115) {
			cost = this.stockCosts[1];
		}
		else if(stockLength == 110) {
			cost = stockCosts[2];
		}
		else if(stockLength == 105) {
			cost = this.stockCosts[3];
		}
		else if(stockLength == 100) {
			cost =  this.stockCosts[4];
		}
		
	return cost;	
	
	}
}
	
	
