/**
 * Creates a task class with methods for use in TaskMaster.
 * @author Steven Meyers
 * 
 *
 */
public class Task implements Comparable<Task>{
	/**
	 * @instantiate variables
	 */
	private String description = "No description set.";
	private int priority = 0;
	private boolean taskComplete = false;
	public enum Category {PERSONAL, WORK, SCHOOL, NONE};
	private Category category = Category.NONE;
	/**
	 * Create a Task object with a description and priority number
	 * @param description Description of what the task is
	 * @param priority Number that shows priority of the task
	 * @param category Category of the task from the enum
	 */
	public Task(String description, int priority) {
		this.description = description;
		this.priority = priority;
		this.taskComplete = false;
		this.category = Category.NONE;
	}
	
	/**
	 * Sets up a task object with only a description 
	 * @param description Description of what the task is
	 * @param category Gets the category out of the enum list
	 */
	public Task(String description) {
		this.description = description;
		this.category = Category.NONE;
	}
	
	/**
	 * @param priority the Priority to be set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	/**
	 * 
	 * @return the Priority number of that task
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * @param description The description to set 
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the Description of that task
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @return true or false if task is complete or not
	 */
	public boolean isComplete() {
		return this.taskComplete; 
	}
	
	/**
	 * @param complete Sets the task as complete
	 */
	public void setComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}
	
	/**
	 * 
	 * @param category Sets the category of the task
	 */
	public void setCategory(Category category) {
		this.category = category;
	}
	
	/**
	 * 
	 * @return The enum category of the task.
	 */
	public Category getCategory() {
		return category;
	}
	/**
	 * 
	 * @param task Task with the description being compared
	 * @return true or false if the tasks are equal or not* Analyzes 3 user input songs. Using these songs,it finds the
 * average play time, song closest to 4 minutes, and creates
 * an ordered list starting from the shortest song
	 */
	public boolean equals(Task task) {
		String taskDescription = this.getDescription();
		String passedDescription = task.getDescription();
		Category taskCategory = this.getCategory();
		Category passedCategory = task.getCategory();
		if (passedDescription.equalsIgnoreCase(taskDescription) && passedCategory.equals(taskCategory)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * @return whether the Task is complete, its description and priority number
	 */
	public String toString() {
		String output = "";
		if (this.taskComplete == true) {
			output += "[X] " + this.description + ", " + this.category +
					", " + this.priority + "\n";
		} else {
			output += "[ ] " + this.description + ", " + this.category +
					", " + this.priority + "\n";
		}
		return output;	
	}
	
	/**
	 * @returns a numberId that signifies which task has higher priority based off 
	 * 			of completion
	 */
	public int compareTo(Task task) {
		int numberId = 0;
		if (this.isComplete() == task.isComplete()) {
			if (this.getPriority() == task.getPriority()) {
				numberId = 0;
			} else if (this.getPriority() < task.getPriority()) {
				numberId = -1;
			}else if (this.getPriority() > task.getPriority()) {
				numberId = 1;
			}
		} else if (this.isComplete() == true && task.isComplete() == false) {
			numberId = -1;
		} else if (this.isComplete() == false && task.isComplete() == true) {
			numberId = 1;
		}
		return numberId;
	}
}
