import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ToDoListPanel extends JPanel {
	private ToDoList list;
	private ArrayList<Task> tasks;
	public ToDoListPanel(String listName) {
		list = new ToDoList(listName, tasks);
		JLabel label = new JLabel(list.getName());
		add(label);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void addTask(Task t) {
		list.addTask(t);
		TaskButton button = new TaskButton(t);
		add(button);
		revalidate();
	}
	public void showWorkDialog() {
		Task nextTask = list.getWork();
		if (nextTask.isComplete()) {
			String results = "No work to do. Enjoy your day!";
			JOptionPane.showMessageDialog(null, results);
		} else {
			String results = "You need to " + nextTask.getDescription() + " next.";
			JOptionPane.showMessageDialog(null, results);
		}
		revalidate();
	}
}
