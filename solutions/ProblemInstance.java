package solutions;

import java.util.Random;
import java.util.ArrayList;

public class ProblemInstance {
	private static final int NO_OF_STOCK_LENGHTH = 5;
	private static final int N0_OF_STOCK_REQUIRED = 36;
    private int[] stockLengths =  {120, 115, 110, 105, 100};
    private double[] stockCosts = {12, 11.5, 11, 10.5, 10};
    private int[] pieces =       {21, 22, 24, 25, 27, 29, 30, 31, 32, 33, 34, 35, 38, 39, 42, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 59, 60, 61, 63, 65, 66, 67};
    private int[] quantities =   {13, 15, 7, 5, 9, 9, 3, 15, 18, 17, 4, 17, 20, 9, 4, 19, 4, 12, 15, 3, 20, 14, 15, 6, 4, 7, 5, 19, 19, 6, 3, 7, 20, 5, 10, 17};
    private Random random;
    private int totalRequired;
 
    private ArrayList<Integer> ArrayListRecipes;
    private ArrayList<Integer> stockLengthUsed;
    private ArrayList<Activity> Solution;
   //15,8,5,12,28,16,9,30  
    
	
	public ProblemInstance() {	
		
		this.ArrayListRecipes = new ArrayList<Integer>();
		this.stockLengthUsed = new ArrayList<Integer>();
		this.random = new Random();
		totalRequired = generateTotal();
		this.Solution = new  ArrayList<Activity>();
		
		
	}
	public int generateTotal() {
		int total = 0;
		
		for(int i = 0; i < quantities.length; i ++) {
			 total = total + quantities[i];
			
		}
	
		return total;
	}

	
	
	public Solution generateSolution() {
		
		int leftOverAmount = 0;  //variable that holds amount of leftOver stock
		int amountUsed = 0;
		for(int i = 0; i < pieces.length; i++ ) {
			
			    int currentStock = stockLengths[random.nextInt(stockLengths.length)]; //pick a random stock length
			    if(amountUsed < totalRequired) {
			    	this.stockLengthUsed.add(currentStock);//add it to the stock lengths used
			    }
				int stockLengthLeft = currentStock; //store as local variable stockLengthLeft
				int currentRequired = quantities[i]; // get the current amount required for the given quantity
				while(currentRequired > 0) {
					if(leftOverAmount >= pieces[i]) {   //check if there is left over stock of sufficient size use
						leftOverAmount = leftOverAmount - pieces[i]; //this first
						amountUsed++;
						this.ArrayListRecipes.add(pieces[i]);     //add this to a list of all pieces subtracted so far
						currentRequired = currentRequired - 1;	 //reduce the amount required		
					}
					else {
						leftOverAmount = 0;                       //set left over to zero because it is not enough
						if(stockLengthLeft >= pieces[i]) {                        //check if stock length is sufficient
							stockLengthLeft = stockLengthLeft - pieces[i];        //for required piece subtract if it is
							amountUsed++;
							this.ArrayListRecipes.add(pieces[i]);             //add this to a list of all pieces subtracted so far
							currentRequired = currentRequired - 1;  //reduce the amount required
						}
						else {
							
							if(amountUsed < this.totalRequired) {
							   stockLengthLeft = stockLengths[random.nextInt(3)]; //generate a random amount of stock
							   this.stockLengthUsed.add(stockLengthLeft);        //if the current one is not big enough
							}                                                         //add it to the stock already used
						}                                                    
						
					}
				}
				leftOverAmount = stockLengthLeft;  // update any left over stock
						
		}
	
	
		
		
		int peiceIndex = 0;
		for(int i = 0; i < this.stockLengthUsed.size(); i++) {
			
			int currentStockLength = this.stockLengthUsed.get(i);
			int amountLeft = currentStockLength;
			ArrayList<Integer> stockPeicesUsed = new ArrayList<Integer>();
			//System.out.println("Stock length used " + this.stockLengthUsed.get(i));
			while(amountLeft >= this.ArrayListRecipes.get(peiceIndex)) {
				
				amountLeft = amountLeft - this.ArrayListRecipes.get(peiceIndex);
				stockPeicesUsed.add(this.ArrayListRecipes.get(peiceIndex));
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
		
		/*for(int i = 0; i < this.Solution.size(); i++) {
			 Solution.get(i).printTest();
			
			
		}*/
		Solution solution = new Solution(Solution);
	   return solution;
	
	
	}
	

		
			
   
		
	public int[] getStockLengths() {
		return stockLengths;
	}


	public void setStockLengths(int[] stockLengths) {
		this.stockLengths = stockLengths;
	}


	public double[] getStockCosts() {
		return stockCosts;
	}


	public void setStockCosts(double[] stockCosts) {
		this.stockCosts = stockCosts;
	}


	public int[] getPieceLengths() {
		return pieces;
	}


	public void setPieceLengths(int[] pieceLengths) {
		this.pieces = pieceLengths;
	}


	public int[] getQuantities() {
		return quantities;
	}


	public void setQuantities(int[] quantities) {
		this.quantities = quantities;
	}


	public Random getRandom() {
		return random;
	}


	public void setRandom(Random random) {
		this.random = random;
	}
	
	public int getTotalRequired() {
		return this.totalRequired;
	}


	public static int getNoOfStockLenghth() {
		return NO_OF_STOCK_LENGHTH;
	}


	public static int getN0OfStockRequired() {
		return N0_OF_STOCK_REQUIRED;
	}
		
		
	
	
	
	

}
