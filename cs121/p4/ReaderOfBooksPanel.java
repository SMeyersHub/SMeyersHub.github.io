import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
/**
 * 
 * Creates a panel which stores both the Reader panel and Library
 * panel. Also puts the book button listener classes on the book 
 * buttons.
 * @author Steven Meyers
 *  
 */
public class ReaderOfBooksPanel extends JPanel {
	private Book tempBook = new Book("", "");
	private BookButtonListener bookButtonListener = new BookButtonListener();
	private LibraryPanel libraryPanel= new LibraryPanel(bookButtonListener);
	private ReaderPanel readerPanel = new ReaderPanel();
	public ReaderOfBooksPanel() {
		
		//Border Layout
		setLayout(new BorderLayout());
		libraryPanel.setBorder(BorderFactory.createTitledBorder("Library"));
		readerPanel.setBorder(BorderFactory.createTitledBorder("Reader"));
		
		//Add components to ReaderOfBooksPanel.
		this.add(libraryPanel, BorderLayout.WEST);
		this.add(readerPanel, BorderLayout.CENTER);
	}
	
	//Applied to book buttons when they are created.
	private class BookButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			BookButton bB = (BookButton) e.getSource();
			tempBook = bB.getBook();
			readerPanel.getTextArea().setText(tempBook.getText());
			readerPanel.getTitleLabel().setText("Title: " + tempBook.getTitle());
			readerPanel.getAuthorLabel().setText("Title: " + tempBook.getAuthor());
			revalidate();
		}
	}
}
