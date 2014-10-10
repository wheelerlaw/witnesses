import java.math.BigInteger;
import edu.rit.pj2.LongVbl;
import edu.rit.pj2.Task;
import edu.rit.pj2.Loop;

/**
 * The main task class. This gets executed by the pj2 main package and creates a task out of it. 
 * This class computes the number of witnesses of a given number. 
 * @author Wheeler Law <wpl3499>
 *
 */
public class Fermat extends Task{

	LongVbl numWitnesses;
	int p;
	
	/**
	 * The main function is equivalent to the static void main of a regular  
	 * class except in this case it is not static and is used by pj2 to start the task. 
	 * 
	 * @param String[] arg0 The list of command line arguments. In this case, it is 
	 * 	just one integer p that indicates the number to check witnesses for. 
	 */
	public void main(String[] args) throws Exception{
		
		if(args.length != 1) usage();
		
		p = Integer.parseInt(args[0]);
		numWitnesses = new LongVbl.Sum();
		
		parallelFor(2, p-1).exec(new Loop(){
			
			LongVbl thrNumWitnesses;
			
			/**
			 * Set the reduction variable to the executing thread. 
			 */
			public void start(){
				thrNumWitnesses = threadLocal(numWitnesses);
			}
			
			/**
			 * The main calculation component. Takes a to the power of p 
			 * and then takes the modulo p of that result. If this not 
			 * equal to a it is not a witness, and will be counted. 
			 * 
			 * @param int a The current number that is greater than or equal
			 * 		to 2 and less than or equal to p - 1 that will be checked
			 * 		against p to see if it is a witness. 
			 */
			public void run(int a) throws Exception {
				
				BigInteger aBig = new BigInteger(String.valueOf(a));
				BigInteger pBig = new BigInteger(String.valueOf(p));
				
				if(aBig.modPow(pBig, pBig).compareTo(aBig) != 0){
					thrNumWitnesses.item++;
				}
			}
		});
		
		System.out.println(numWitnesses.item);
	}
	
	/**
	 * Abstraction of the usage function. Prints out the usage of the Fermat 
	 * class and throws an exception. 
	 */
	public static void usage(){
		System.out.println("Usage: java pj2 Fermet <p>");
		throw new IllegalArgumentException();
	}
	
}
