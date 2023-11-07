import javax.swing.JFrame;

public class ReaderOfBooks {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Reader of Books");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ReaderOfBooksPanel readerOfBooksPanel = new ReaderOfBooksPanel();
		frame.getContentPane().add(readerOfBooksPanel);
		frame.setSize(1000, 1000);
		frame.setVisible(true);
	}
}
