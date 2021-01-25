package solutions;

public class Main {
	
	public static void main(String[] args) {
		
		ProblemInstance pi = new ProblemInstance();
		//pi.generateTotal();
		//pi.generateSolution();
		
	Evolutionary evolutionary = new Evolutionary(pi);
	    evolutionary.solve();
	   // evolutionary.generatePopulation();
		//evolutionary.parentSelection();
		//evolutionary.produceChildren();
		
		//EvolutionaryNovel evoNov = new EvolutionaryNovel(pi);
		
	   // evoNov.solve();
		
	}
	

}
