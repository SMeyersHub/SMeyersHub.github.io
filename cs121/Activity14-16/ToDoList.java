import java.util.ArrayList;
import java.util.Collections;


public class ToDoList implements ToDoListInterface {
private String name;
private ArrayList<Task> tasks; 
	/**
	 * 
	 * @param name Name of the list
	 * @param tasks An ArrayList of tasks.
	 */
	public ToDoList(String name, ArrayList<Task> tasks) {
		this.tasks = new ArrayList<Task>();
		this.name = name;
	}
	
	/**
	 * @return name of the list
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @add A task to the list
	 */
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	/**
	 * @add Task with description passed in.
	 */
	public void addTask(String description) {
		Task task = new Task(description);
		tasks.add(task);
	}
	
	public void addTask(String description, Task.Category category) {
		Task task = new Task(description);
		task.setCategory(category);
		tasks.add(task);
	}

	/**
	 * @return The not completed task with the highest priority
	 */
	public Task getWork() {
		if (tasks.isEmpty()) {
			return null;
		}
		else {
			Task maxTask = Collections.max(tasks);
			return maxTask;
		}
	}

	/**
	 * Gets the current list.
	 */	
	public ArrayList<Task> getTaskList() {
		return tasks;
	}
	
	/**
	 *@return The contents of the ArrayList
	 */
	public String toString() {
		String output = "-------------\nMy ToDo List\n-------------\n";
		for (int i = 0; i < tasks.size(); i++) {
			output += tasks.get(i);
		}
		return output;
	}

}
