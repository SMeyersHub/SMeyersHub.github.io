import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

/**
 *  Contains panels for the book text as well as info and a navigation panel to move up and down "pages".
 * @author Steven Meyers
 *
 */
public class ReaderPanel extends JPanel {
	private JPanel infoPanel;
	private JScrollPane bookPanel;
	private JTextArea bookContentsPanel;
	private JScrollBar verticalBar;
	private JPanel navPanel;
	private JButton navUp;
	private JButton navDown;
	
	//headerLabels
	private JLabel titleLabel;
	private JLabel authorLabel;
	private JLabel pageLabel;
	public ReaderPanel() {
		//Info title.
		titleLabel = new JLabel("Title: ");
		authorLabel = new JLabel("Author: ");
		pageLabel = new JLabel("Page Number: ");
		
		//infoPanel setup
		setLayout(new BorderLayout());
		infoPanel = new JPanel();
		infoPanel.add(titleLabel, BorderLayout.WEST);
		infoPanel.add(authorLabel, BorderLayout.CENTER);
		infoPanel.add(pageLabel, BorderLayout.EAST);
		infoPanel.setBorder(BorderFactory.createTitledBorder("Book Information"));
		
		//Reader Panel text layout
		bookContentsPanel = new JTextArea("");
		bookPanel = new JScrollPane(bookContentsPanel);
		bookPanel.setBorder(BorderFactory.createTitledBorder("Book Text"));
		
		//navigationButton things
		verticalBar = bookPanel.getVerticalScrollBar();
		verticalBar.setValue(0);
		verticalBar.addAdjustmentListener(new ScrollListener());
		navUp = new JButton("Page Up");
		navDown = new JButton("Page Down");
		navUp.addActionListener(new NavButtonListener());
		navDown.addActionListener(new NavButtonListener());
		navPanel = new JPanel();
		navPanel.add(navUp);
		navPanel.add(navDown);
		navPanel.setBorder(BorderFactory.createTitledBorder("Navigation Panel"));
		
		//Add to ReaderPanel
		setLayout(new BorderLayout());
		add(infoPanel, BorderLayout.NORTH);
		add(bookPanel, BorderLayout.CENTER);
		add(navPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Returns the text area used to print the book contents.
	 * @return JLabel with text area for book contents
	 */
	public JTextArea getTextArea() {
		return bookContentsPanel;
	}
	
	/**
	 * Returns the label with title info.
	 * @return JLabel with title info
	 */
	public JLabel getTitleLabel() {
		return titleLabel;
	}
	
	/**
	 * Returns the label with author info.
	 * @return JLabel with author info
	 */
	public JLabel getAuthorLabel() {
		return authorLabel;
	}
	
	private class NavButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton tempButton = new JButton();
			tempButton = (JButton) e.getSource();
			if(tempButton.getText() == "Page Up") {
				verticalBar.setValue(verticalBar.getValue() - verticalBar.getBlockIncrement(1));	
			} else {
				verticalBar.setValue(verticalBar.getValue() + verticalBar.getBlockIncrement(1));
			}
			revalidate();
		}	
	}
	
	private class ScrollListener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			verticalBar.getBlockIncrement(1);
			pageLabel.setText("Page: " + (verticalBar.getValue()/verticalBar.getBlockIncrement(1)) + "/" + ((verticalBar.getMaximum()/verticalBar.getBlockIncrement(1)-1)));
			if(verticalBar.getValue() == verticalBar.getMaximum()-verticalBar.getBlockIncrement(1)) {
				navDown.setEnabled(false);
			} else {
				navDown.setEnabled(true);
			}
			if (verticalBar.getValue() == verticalBar.getMinimum()) {
				navUp.setEnabled(false);
			} else {
				navUp.setEnabled(true);
			}
			revalidate();
		}
		
	}
}
