import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;
/**
 * CS 121 Project 1: PlayList Analyzer
 *
 * Analyzes 3 user input songs. Using these songs,it finds the
 * average play time, song closest to 4 minutes, and creates
 * an ordered list starting from the shortest song
 *
 * @author Steven Meyers
 */
public class PlayList {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		// Input the first song
		System.out.print("Enter a song title: ");
		String songT = scan.nextLine();
		System.out.print("Enter the song's artist: ");
		String songA = scan.nextLine();
		System.out.print("Enter the song play time (mm:ss): ");
		String songP = scan.nextLine();
		System.out.print("Enter a file path for the song: ");
		String songF = scan.nextLine();
		
		//Conversions of time for Song One
		int index = songP.indexOf(":");
		String songPMinString = songP.substring(0, index);
		String songPSecString = songP.substring(index+1);
		int songPSec = Integer.parseInt(songPSecString);
		int songPMin = Integer.parseInt(songPMinString);
		int songTime = songPSec + (songPMin*60);
		Song songOne = new Song(songT, songA, songTime, songF); //Set up the song one object
		System.out.println(songOne);
		
		//Repeat the process for a second song
		// Input the second song
		System.out.print("Enter a song title: ");
		songT = scan.nextLine();
		System.out.print("Enter the song's artist: ");
		songA = scan.nextLine();
		System.out.print("Enter the song play time (mm:ss): ");
		songP = scan.nextLine();
		System.out.print("Enter a file path for the song: ");
		songF = scan.nextLine();
		
		//Conversions of time for Song Two
		index = songP.indexOf(":");
		songPMinString = songP.substring(0, index);
		songPSecString = songP.substring(index+1);
		songPSec = Integer.parseInt(songPSecString);
		songPMin = Integer.parseInt(songPMinString);
		songTime = songPSec + (songPMin*60);	
		Song songTwo = new Song(songT, songA, songTime, songF); //Set up the song two object
		System.out.println(songTwo);
		
		//Repeat the process for a third song
		// Input the third song
		System.out.print("Enter a song title: ");
		songT = scan.nextLine();
		System.out.print("Enter the song's artist: ");
		songA = scan.nextLine();
		System.out.print("Enter the song play time (mm:ss): ");
		songP = scan.nextLine();
		System.out.print("Enter a file path for the song: ");
		songF = scan.nextLine();
		scan.close();
		
		//Conversions of time for Song Three
		index = songP.indexOf(":");
		songPMinString = songP.substring(0, index);
		songPSecString = songP.substring(index+1);
		songPSec = Integer.parseInt(songPSecString);
		songPMin = Integer.parseInt(songPMinString);
		songTime = songPSec + (songPMin*60);			
		Song songThree = new Song(songT, songA, songTime, songF); //Set up the song three object
		System.out.println(songThree);
		
		//Calculate the average play time of songs
		System.out.println("==============================================================================");
		double avgPlayTime = ((double)songOne.getPlayTime() + (double)songTwo.getPlayTime() + 
				(double)songThree.getPlayTime())/3;
		NumberFormat decimal = new DecimalFormat("0.00");
		System.out.println("Average play time: " + decimal.format(avgPlayTime));
		
		//Calculate which song is closest to 4 mins. If they are all equal, it will print 3 lines. 
		int songOnePlayTime = (songOne.getPlayTime()-240);
		int songTwoPlayTime = (songTwo.getPlayTime()-240);
		int songThreePlayTime = (songThree.getPlayTime()-240);
		songOnePlayTime = Math.abs(songOnePlayTime);
		songTwoPlayTime = Math.abs(songTwoPlayTime);
		songThreePlayTime = Math.abs(songThreePlayTime);
		
		//Evaluate the first songs distance
		if (songOnePlayTime <= songTwoPlayTime && songOnePlayTime <= songThreePlayTime) {
			System.out.println("Song with play time closest to 240 secs is: " + songOne.getTitle());
		}
		
		//Evaluate the second songs distance
		else if (songTwoPlayTime <= songOnePlayTime && songTwoPlayTime <= songThreePlayTime) {
			System.out.println("Song with play time closest to 240 secs is: " + songTwo.getTitle());
		}
		
		//Evaluate the third songs distance.
		else if (songThreePlayTime <= songTwoPlayTime && songThreePlayTime <= songOnePlayTime) {
			System.out.println("Song with play time closest to 240 secs is: " + songThree.getTitle());
		}
		
		//Print the header for the song list
		System.out.println("==============================================================================");
		System.out.println("Title                Artist               File Name                  Play Time");
		System.out.println("==============================================================================");
		
		//Print the song's in descending order
		int timeOne = songOne.getPlayTime();
		int timeTwo = songTwo.getPlayTime();
		int timeThree = songThree.getPlayTime();
		
		//if song one has largest time 
		if (timeOne >= timeTwo && timeOne >= timeThree) {
			if (timeTwo <= timeThree) {
				System.out.println(songTwo);
				System.out.println(songThree);
			} else {
				System.out.println(songThree);
				System.out.println(songTwo);
			}
			System.out.println(songOne);
		} else
			
		//If song two has largest time
		if (timeTwo >= timeOne && timeTwo >= timeThree) {
			if (timeOne <= timeThree) {
				System.out.println(songOne);
				System.out.println(songThree);
			} else {
				System.out.println(songThree);
				System.out.println(songOne);
			}
			System.out.println(songTwo);
		} else
			
		//if song three has largest time
		if (timeThree >= timeTwo && timeThree >= timeOne) {
			if (timeTwo <= timeOne) {
				System.out.println(songTwo);
				System.out.println(songOne);
			} else {
				System.out.println(songOne);
				System.out.println(songTwo);
			}
			System.out.println(songThree);
		}
		System.out.println("==============================================================================");
	}
}


