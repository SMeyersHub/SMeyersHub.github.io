import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This program simulates a single cache or double cache system.
 * @author Steven Meyers
 * @date 7/13/2020
 */
public class Test {
	
	public static void main(String[] args) {
		//Define variables
		int cacheCount = 0;
		int cacheOneSize = 0;
		int cacheTwoSize = 0;
		
		double totalHit = 0;
		double oneHit = 0;
		double twoHit = 0;
		double totalRef = 0;
		double oneRef = 0;
		double twoRef = 0;
		double oneHitRatio = 0;
		double twoHitRatio = 0;
		double totalHitRatio = 0;
		Cache<String> cacheOne;
		Cache<String> cacheTwo;
		
		//Try and see if the first variable is an int
		//If not an int, print error and return cause no cache is made then
		try {
			cacheCount = Integer.parseInt(args[0]);
		} catch(NumberFormatException e) {
			System.out.println("Cache count is not a number");
			System.out.println(e.getMessage());
		}
		
		//If else: Cache either is 1 cache or 2 caches.
		if(cacheCount == 1) {
			//Check to see if cacheSize is an int, if not return error
			try {
				cacheOneSize = Integer.parseInt(args[1]);
				
				//Check to see if size is not negative, then create cache one
				if(cacheOneSize >= 0) {
					cacheOne = new Cache<String>(cacheOneSize);
					//Print out cache creation and size;
					System.out.println("First level cache with " + cacheOneSize + " entries has been created");
					
					try {
						//Scan in the file name and make sure its real
						Scanner fileScan = new Scanner(new File(args[2]));
						String scannedString;
						
						//set delimiter as a space to let every word be scanned
						while(fileScan.hasNext()) {
							++totalRef;
							scannedString = fileScan.next();
							//HIT
							if(cacheOne.contains(scannedString)) {
								++oneRef;
								++oneHit;
								++totalHit;
								cacheOne.remove(scannedString);
								cacheOne.addToRear(scannedString);
							//MISS
							} else {
								++oneRef;
								//check to see if the cache is full, and if it is, remove the last and add new string to front
								if(cacheOne.size() == cacheOneSize) {
									cacheOne.removeFirst();
									cacheOne.addToRear(scannedString);
								} else {
									cacheOne.addToRear(scannedString);
								}
							}
						}
						fileScan.close();
					} catch (FileNotFoundException e) {
						System.out.println("File can't be accessed.");
						System.out.println("Error: " + e.getMessage());
					}
					
				}
				
			} catch(NumberFormatException e) {
				System.out.println("Cache size is not a number");
				System.out.println(e.getMessage());
			}	
			
		} else if(cacheCount == 2) {
			//Check cache one and cache two sizes for errors
			try {
				cacheOneSize = Integer.parseInt(args[1]);
				cacheTwoSize = Integer.parseInt(args[2]);
				
				
				if(cacheOneSize >= 0) {
					cacheOne = new Cache<String>(cacheOneSize);
					//Print out cache creation and size;
					System.out.println("First level cache with " + cacheOneSize + " entries has been created");
				} else {
					throw new NumberFormatException("Cache one size is too small.");
				}
				
				if(cacheTwoSize >= 0 && cacheTwoSize > cacheOneSize) {
					cacheTwo = new Cache<String>(cacheTwoSize);
					//Print out cache creation and size;
					System.out.println("Second level cache with " + cacheTwoSize + " entries has been created");
				} else {
					throw new NumberFormatException("Cache two size is too small.");
				}
				
				try {
					Scanner fileScan = new Scanner(new File(args[3]));
					String scannedString;
					
					while(fileScan.hasNext()) {
						++totalRef;
						scannedString = fileScan.next();
						if(cacheOne.contains(scannedString)) {
							oneHit++;
							oneRef++;
							totalHit++;
							
							cacheOne.remove(scannedString);
							cacheOne.addToRear(scannedString);
							cacheTwo.remove(scannedString);
							cacheTwo.addToRear(scannedString);
						}
						else if(cacheTwo.contains(scannedString)) {
							twoHit++;
							twoRef++;
							oneRef++;
							totalHit++;
							
							cacheTwo.remove(scannedString);
							cacheTwo.addToRear(scannedString);
							
							//check to see if cache one is full, if so, clear oldest entry (first)
							if(cacheOne.size() == cacheOneSize) {
								cacheOne.removeFirst();
								cacheOne.addToRear(scannedString);
							} else {
								cacheOne.addToRear(scannedString);
							}
						}
						else {
							twoRef++;
							oneRef++;
							
							//Check to see if cache one and two are full, if so, clear oldest entry
							if(cacheOne.size() == cacheOneSize) {
								cacheOne.removeFirst();
								cacheOne.addToRear(scannedString);
							} else {
								cacheOne.addToRear(scannedString);
							}
							if(cacheTwo.size() == cacheTwoSize) {
								cacheTwo.removeFirst();
								cacheTwo.addToRear(scannedString);
							} else {
								cacheTwo.addToRear(scannedString);
							}
						}
					}
					fileScan.close();
				} catch (FileNotFoundException e) {
					System.out.println("File can't be accessed.");
					System.out.println("Error: " + e.getMessage());
				}
				
			} catch(NumberFormatException e) {
				System.out.println("Cache size for one of the caches is not a number or is not positive");
				System.out.println(e.getMessage());
			}
			
		}
		
		//Check to make sure that the refs arent 0, as this will cause errors when dividing
		if(oneRef != 0) {
			oneHitRatio = oneHit/oneRef;
			totalHitRatio = totalHit/totalRef;
		}
		
		if(twoRef != 0) {
			twoHitRatio = twoHit/twoRef;
		}
		
		//Print output
		System.out.println("----------------------------------------------");
		System.out.println("The number of global references: " + (int)totalRef);
		System.out.println("The number of global cache hits: " + (int)totalHit);
		System.out.println("The global hit ratio:  " + totalHitRatio);
		System.out.println(" ");
		System.out.println("The number of 1st-level references: " + (int)oneRef);
		System.out.println("The number of 1st-level cache hits: " + (int)oneHit);
		System.out.println("The 1st-level cache hit ratio:  " + oneHitRatio);
		System.out.println(" ");
		System.out.println("The number of 2nd-level references: " + (int)twoRef);
		System.out.println("The number of 2nd-level cache hits: " + (int)twoHit);
		System.out.println("The 2nd-level cache hit ratio:  " + twoHitRatio);
	}
}
