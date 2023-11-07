import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	private JTextField descriptionField;
	private JButton addTaskButton;
	private JScrollPane toDoListScrollPane;
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
		//add(BorderLayout.CENTER, listPanel);
		
		//Text field
		descriptionField = new JTextField(15);
		
		//Add Task Button
		addTaskButton = new JButton("Add Task");
		
		//Scroll Pane
		toDoListScrollPane = new JScrollPane(listPanel);
		toDoListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		toDoListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(toDoListScrollPane, BorderLayout.CENTER);
		
		//Panel things are displayed in
		JPanel controlPanel = new JPanel();
		controlPanel.add(descriptionField);
		controlPanel.add(addTaskButton);
		controlPanel.add(getWorkButton);
		addTaskButton.addActionListener(new AddTaskButtonListener());
		getWorkButton.addActionListener(new GetWorkButtonListener());
		add(controlPanel, BorderLayout.SOUTH);
	}
	
	private class GetWorkButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			listPanel.showWorkDialog();
		}
	}
	
	private class AddTaskButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String input = descriptionField.getText();
			Task task = new Task(input);
			listPanel.addTask(task);
			revalidate();
		}
		
	}
}
