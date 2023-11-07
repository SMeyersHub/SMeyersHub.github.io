import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * @author Steven Meyers
 *
 *This class has all the methods for creating Books and getting/setting
 *all of the book details. It also contains the methods to check
 *if a book is valid and to get the text in the book.
 */
public class Book implements BookInterface{
	private String title;
	private String author;
	private String genre;
	private String filename;
	
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		genre = null;
		filename = null;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getFilename() {
		return filename;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Checks to see if the book exists.
	 * @return boolean of whether book exists or not.
	 */
	public boolean isValid() {
			if (this.author != null && this.genre != null && this.title != null && this.filename != null) {
				if (new File(this.filename).exists()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
	}

	/**
	 * Scans the book file and returns the text contained in the 
	 * file.
	 * @return Text from book in a string.
	 */
	public String getText() {
		try {
			Scanner fileScan = new Scanner(new File(this.filename));
			String contents = "";
			fileScan.useDelimiter(".");
			while (fileScan.hasNext()) {
				String line =fileScan.nextLine() ;
				contents += line+ "\n";
			}
			fileScan.close();
			return contents;
		} catch (FileNotFoundException e) {
			String fileNotFound = "File was not able to be opened";
			return fileNotFound;
		}
		
	}
	
	/**
	 * Returns a string with the book info.
	 */
	public String toString() {
		String output = "|Title: " + this.title + "\n|" +
						"Author: " + this.author + "\n|" +
						"Genre: " + this.genre + "\n";
		return output;
		
	}
}
