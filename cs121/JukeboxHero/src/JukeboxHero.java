import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class JukeboxHero {
	public static void main(String[] args) {
		//Sets up and prints out menu header.
		String menuHeader = "*****************************" + "\n" +
							"*      Program Menu         *" + "\n" +
							"*****************************" + "\n" +
							"(L)oad catalog" +  "\n" +
							"(S)earch catalog" +  "\n" +
							"(A)nalyse catalog" +  "\n" +
							"(P)rint catalog" +  "\n" +
							"(M)enu reprint" + "\n" +
							"(Q)uit" +  "\n";
		System.out.println(menuHeader);
		
		//Sets up the while loop to host the command enter
		String menuSelect = "";
		Scanner scan = new Scanner(System.in);
		String cancel = "q";
		String firstChar = "m";
		ArrayList<Song> songList = new ArrayList<Song>();
		ArrayList<Song> searchResults = new ArrayList<Song>();
		//TODO: Ask if I am supposed to have multiple arrays for album and artist.
		ArrayList<String> artistList = new ArrayList<String>();
		ArrayList<String> albumList = new ArrayList<String>();
		int counter = 0;
		int searchCounter = 0;
		int totalPlaytime = 0;
		
		//Sets up the initial menu and scanner for the user to navigate from
		while (cancel.equalsIgnoreCase(menuSelect) != true) {
			System.out.print("Please enter a command (press 'm' for menu): ");
			menuSelect = scan.nextLine();
			firstChar = menuSelect.toUpperCase();
			
			//Sets up the switch that reads the users input and directs them to the requested program.
			switch (firstChar.charAt(0)) {
			
			//Loads up a song list
			case 'L': 
				counter = 0;
				songList.clear();
				System.out.print("Enter a file name: ");
				String fileName = scan.nextLine();
				try {
					Scanner fileScan = new Scanner(new File(fileName));
					while(fileScan.hasNextLine()) {
						String fileLine = fileScan.nextLine();
						Scanner stringScan = new Scanner(fileLine);
						stringScan.useDelimiter(",");
						String artist = stringScan.next();
						String album = stringScan.next();
						String title = stringScan.next();
						int duration = stringScan.nextInt();
						Song song = new Song(title, artist, album, duration);
						songList.add(song);	
						counter++;
					}
					System.out.println("Success! Loaded " + counter + " songs!");
				} catch (FileNotFoundException e) {
					System.out.println("No file found with that name. Check and retry.");
				}
				
				break;
				
			//Searches the array for a song title.
			case 'S':
				searchResults.clear();
				searchCounter = 0;
				System.out.print("Enter a song title: ");
				String userSearch = scan.nextLine();
				String lowSearch = userSearch.toLowerCase();
				//TODO: ask about this for loop
				for (int i = 0; i < songList.size(); i++) {
					Song searchSong = songList.get(i);
					String searchTitle = searchSong.getTitle();
					String lowTitle = searchTitle.toLowerCase();
					if (lowTitle.contains(lowSearch)) {
						searchResults.add(searchSong);
						searchCounter++;
					}
				}
				//TODO: Ask if its ok to have a new line input to seperate output from input spam.
				System.out.println("\n" + "Found " + searchCounter + " matches." + "\n" +
									"---------------------------------");
				for(int i = 0; i < searchResults.size(); i++) {
					Song o = searchResults.get(i);
					System.out.println(o);
				}
				System.out.println();
				break;
				
			//Analyzes the current song list.
			case 'A':
				totalPlaytime = 0;
				artistList.clear();
				albumList.clear();
				for (int i = 0; i < songList.size(); i++) {
					Song currentSong = songList.get(i); 
					if (artistList.contains(currentSong.getArtist())) {
						
					} else {
						artistList.add(currentSong.getArtist());
					}
					if (albumList.contains(currentSong.getAlbum())) {
						
					} else {
						albumList.add(currentSong.getAlbum());
					}
					totalPlaytime = currentSong.getPlayTime() + totalPlaytime;
				}
				//Prints output for analysis
				System.out.print("Catalog analysis: " + "\n" +
								"\t" + "Number of Artists: " + artistList.size() + "\n" +
								"\t" + "Number of Albums: " + albumList.size() + "\n" +
								"\t" + "Number of Songs: " + songList.size() + "\n" +
								"\t" + "Total Playtime of Catalog: " + totalPlaytime + "\n");
				break;
			
			//Prints the song list
			case 'P':
				System.out.print( "\n" + "Current song list contains: " + counter + " songs." + "\n" +
			    "---------------------------------" + "\n");
				for (Song i : songList) {
					System.out.println(i + "");
				}
				break;
			case 'M':
				System.out.println( "\n" + menuHeader);
				break;
				
			//Prevents the invalid input line from being printed when terminating.
			case 'Q':
				break;
				
			//Prints an invalid input message for any letter not in a case.
			default:
				System.out.println("Invalid input. Please select inputs from the menu.");
			}
			
		}
		scan.close();
		System.out.print("Quit successfully. Thank you!");
	}

}
