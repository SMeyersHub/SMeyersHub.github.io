import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Lesson 18-19: Activity - Task Master GUI
 * 
 * This class represents the main TaskMaster JPanel. 
 * 
 * It contains a ToDoListPanel and the control sub-panel.
 * 
 * @author CS121 Instructors
 * @version Spring 2019
 * @author Steven Meyers
 */
@SuppressWarnings("serial")
public class TaskMasterPanel extends JPanel {
	private JButton getWorkButton;
	private ToDoListPanel listPanel;
	/**
	 * Creates a new TaskMasterPanel.
	 */
	public TaskMasterPanel()
	{
		this.setPreferredSize(new Dimension(500, 400));
		this.setLayout(new BorderLayout());
		listPanel = new ToDoListPanel("To Do List");
		//Task buttons
		listPanel.addTask(new Task("Task 1"));
		listPanel.addTask(new Task("Task 2"));
		listPanel.addTask(new Task("Task 3"));
		
		//getWorkButtonListener
		getWorkButton = new JButton("Get Work");
		add(BorderLayout.CENTER, listPanel);
		
		JPanel controlPanel = new JPanel();
		controlPanel.add(getWorkButton);
		
		getWorkButton.addActionListener(new GetWorkButtonListener());
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	private class GetWorkButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			listPanel.showWorkDialog();
		}
		
	}
}
