import java.util.Scanner;
/**
 * 
 * @author: Steven Meyers
 * @date: 4 April 2019
 * 
 * This is the driver class for the LibraryOfBooks program. It allows
 * the user to access a menu and add/delete/read books to the library
 * which can also be printed.
 */
public class LibraryOfBooks {

	public static void main(String[] args) {
		
		//Sets up and prints out menu header.
		String menuHeader = "*****************************" + "\n" +
							"*      Library Menu         *" + "\n" +
							"*****************************" + "\n" +
							"(P)rint library" +  "\n" +
							"(A)dd a book" +  "\n" +
							"(D)elete a book" +  "\n" +
							"(R)ead a book" +  "\n" +
							"(Q)uit" +  "\n";
		System.out.println(menuHeader);
		String libraryMenuSelect = "";
		String CANCEL = "q";
		Scanner scan = new Scanner(System.in);
		String firstChar = "";
		Library library = new Library();
		
		//While loop menu
		while (CANCEL.equalsIgnoreCase(libraryMenuSelect) != true) {
			System.out.print("Please enter a command: ");
			libraryMenuSelect = scan.nextLine();
			firstChar = libraryMenuSelect.toUpperCase();
			switch(firstChar.substring(0)) {
			
			//Prints the Library
			case "P":
				System.out.print(library);
				break;
				
			//Adds a Book to the Library
			case "A":
				System.out.print("Please enter the book's title: ");
				String title = scan.nextLine();
				System.out.print("Please enter the book's author: ");
				String author = scan.nextLine();
				System.out.print("Please enter the book's genre: ");
				String genre = scan.nextLine();
				System.out.print("Please enter the book's filename: ");
				String filename = scan.nextLine();
				Book book = new Book(title, author);
				book.setGenre(genre);
				book.setFilename(filename);
				library.addBook(book);
				break;
				
			//Deletes a book from the library
			case "D":
				try {
					System.out.print("Enter the index of the book you want removed: ");
					String index = scan.nextLine();
					int indexDel = Integer.parseInt(index);
					System.out.println();
					library.removeBook(indexDel);
				} catch (NullPointerException e) {
					System.out.println("Book is currently not available.");
				}
				break;
				
			//Prints the text from a book in the library
			case "R":
				try {
					System.out.print("Enter the index of the book you want read: ");
					String indexRead = scan.nextLine();
					int indexInt = Integer.parseInt(indexRead);
					Book readBook = library.getBook(indexInt);
					if (readBook.isValid()) {
						System.out.print(readBook.getText());
					} else {
						System.out.println("Book is currently not available.");
					}
				} catch (NullPointerException e) {
					System.out.println("Book is currently not available.");
				}
				break;
				
			//Quits the program
			case "Q":
				break;
			default:
			System.out.println("Invalid input. Please select inputs from the menu.");
			}	
		}
		scan.close();
		System.out.println("Thank you for using this program!");
	}

}
