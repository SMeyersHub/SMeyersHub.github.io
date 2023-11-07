import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * 
 * @author Steven Meyers
 *
 *Contains the methods used when adding/removing/getting books from
 *the library array. Also contains a toString to print all the books
 *in a fashionable order. Also can scan a library from a CSV document.
 */
public class Library implements LibraryInterface {
	private ArrayList<Book> libraryBooks;
	
	public Library() {
		libraryBooks = new ArrayList<Book>();
	}

	public ArrayList<Book> getBooks() {
		ArrayList<Book> gottenBooks = new ArrayList<Book>(this.libraryBooks);
		return gottenBooks;
	}
	
	public void addBook(Book newBook) {
		libraryBooks.add(newBook);
	}

	@Override
	public void removeBook(int index) {
		if(index < 0 || index >= libraryBooks.size()) {
			//I was told to put this as a print err by my LA, sorry if it's not supposed to be.
			System.err.println("Out of Bounds");
		}else {
			libraryBooks.remove(index);
		}
	}

	@Override
	public Book getBook(int index) {
		if (index < libraryBooks.size() && index >= 0) {
			Book bookIndex = libraryBooks.get(index);
			return bookIndex;
		} 
		return null;
	}
	
	public String toString() {
		String output = "";
		for (int i = 0; i < libraryBooks.size(); i++) {
			output += i + ". \n" + libraryBooks.get(i).toString();
		}
		return output;
	}
	
	/**
	 * Fills a library with book objects from a CSV documented file.
	 */
	public void loadLibraryFromCSV(String csvFilename) {
		try {
			libraryBooks.clear();
			Scanner fileScan = new Scanner(new File(csvFilename));
			while(fileScan.hasNextLine()) {
				String fileLine = fileScan.nextLine();
				Scanner stringScan = new Scanner(fileLine);
				stringScan.useDelimiter(",");
				String title = stringScan.next();
				String author = stringScan.next();
				String genre = stringScan.next();
				String filepath = stringScan.next();
				Book book = new Book(title, author);
				book.setGenre(genre);
				book.setFilename(filepath);
				libraryBooks.add(book);
			}
			fileScan.close();
		} catch (FileNotFoundException e) {
		}
	}
}

