import javax.swing.JButton;
/**
 * Creates a book button class and sets text of button to the book
 * title.
 * @author Steven Meyers
 *
 */
public class BookButton extends JButton {
	private Book newBook;
	public BookButton(Book book) {
		newBook = book;
		if (newBook.getTitle().length() > 20) {
			this.setText(newBook.getTitle().substring(0,20) + "...");
		} else {
			this.setText(newBook.getTitle());
		}
		this.setToolTipText(newBook.toString());
	}
	
	/**
	 * 
	 * @return A book object that is the book stored in the button.
	 */
	public Book getBook() {
		return newBook;
	}
}
