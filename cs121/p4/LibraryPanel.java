import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * Creates a panel with scroll functionality to store book buttons.
 * Also creates a text panel and button to load a library from CSV.
 * Has a listener which imports the CSV and creates book buttons for
 * it.
 * @author Steven Meyers
 *
 */
public class LibraryPanel extends JPanel{
	private BookButton bookButton;
	private JButton libraryLoad;
	private Library library;
	private JScrollPane libraryScroll;
	private JTextField libraryLoadText;
	private JPanel loadPanel;
	private JPanel scrollPanel;
	private Book book;
	private ActionListener actionListener;
	public LibraryPanel(ActionListener actionListener) {
		this.actionListener = actionListener;
		//library loader
		libraryLoadText = new JTextField(10);
		
		libraryLoad = new JButton("Import library");
		libraryLoad.addActionListener(new LibraryListener());
		
		//Add load button and text field to south panel
		loadPanel = new JPanel();
		loadPanel.add(libraryLoadText);
		loadPanel.add(libraryLoad);
		
		//Scrolling Panel Set Up
		scrollPanel = new JPanel();
		libraryScroll = new JScrollPane(scrollPanel);
		
		//Layout stuff
		scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
		libraryScroll.setBorder(BorderFactory.createTitledBorder("Book List"));
		loadPanel.setBorder(BorderFactory.createTitledBorder("Load Library"));
		setLayout(new BorderLayout());
		add(libraryScroll, BorderLayout.CENTER);
		add(loadPanel, BorderLayout.SOUTH);
	}
	
	private class LibraryListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			scrollPanel.removeAll();
			library = new Library();
			library.loadLibraryFromCSV(libraryLoadText.getText());
			for(int i = 0; library.getBook(i) != null; i++) {
				book = library.getBook(i);
				bookButton = new BookButton(book);
				bookButton.addActionListener(actionListener);
				scrollPanel.add(bookButton);
			}
			revalidate();
		}
	}
} 
