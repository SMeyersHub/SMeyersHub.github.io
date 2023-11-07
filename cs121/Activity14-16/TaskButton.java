import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class TaskButton extends JButton{
	private Task task;
	
	public TaskButton(Task passedTask) {
		task = passedTask;
		this.setText(passedTask.toString());
		addActionListener(new TaskButtonListener());
	}
	private class TaskButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (task.isComplete()) {
				task.setComplete(false);
				setForeground(Color.BLACK);
			} else {
				task.setComplete(true);
				setForeground(Color.GRAY);
			}
			setText(task.toString());
		}
		
	}
}
