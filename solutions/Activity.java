package solutions;

import java.util.ArrayList;

public class Activity {
	
	private int StockLengthUsed;
	private ArrayList<Integer> peicesUsed;
	
	public Activity(int givenStockLength, ArrayList<Integer> givenPeicesUsed) {
		
		this.peicesUsed = givenPeicesUsed;
		this.StockLengthUsed = givenStockLength;
		
		
		
		
	}

	public int getStockLengthUsed() {
		return StockLengthUsed;
	}

	public ArrayList<Integer> getPeicesUsed() {
		return peicesUsed;
	}
	
	public void setStockLengthUsed(int givenStockLength) {
		this.StockLengthUsed = givenStockLength;
		
	}
	
	public int activitiesTotal() {
		int total = 0;
		for(int i = 0; i < this.peicesUsed.size();i++) {
			total = total + this.peicesUsed.get(i);
		}
		return total;
		
	}
	
	public void printTest() {
		System.out.println("The stock length for this activity " + this.StockLengthUsed);
		for(int i = 0; i < this.peicesUsed.size(); i++) {
			System.out.println("The peices used " + this.peicesUsed.get(i));
			
		}
		
	}
	

}
