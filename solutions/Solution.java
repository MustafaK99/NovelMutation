package solutions;

import java.util.ArrayList;

public class Solution {
	
	private ArrayList<Activity> listOfActivities;
	
	public Solution(ArrayList<Activity> givenActivityList) {
		
		this.listOfActivities = new ArrayList<Activity>();
		this.listOfActivities = givenActivityList;
		
		
	}

	public ArrayList<Activity> getListOfActivities() {
		return listOfActivities;
	}
	
	public void setListOfActivities(ArrayList<Activity> newSolution) {
		
		this.listOfActivities = newSolution;
	}
	
	

}
