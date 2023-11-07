import java.util.ArrayList;


/**
 * Creates 2 task objects and changes the priorities and the descriptions of them, then prints results.
 * @author Steven Meyers
 * @semester Spring 2019
 * @reviewer Jason Hayhurst
 */
public class TaskMaster {

	public static void main(String[] args) {
		// Creates task1
		Task task1 = new Task("Finish Activity 14");
		System.out.println("Task 1: " + task1.getDescription());
		System.out.print(task1);
		task1.setCategory(Task.Category.SCHOOL);
		task1.setComplete(true);
		System.out.println(task1.getDescription() + " is complete: " +task1.isComplete());
		System.out.print(task1);
		
		// Creates task 2
		Task task2 = new Task("Give Tigger a Bath", 10);
		task2.setCategory(Task.Category.WORK);
		System.out.println("\n" + "Task 2: " + task2.getDescription());
		System.out.print(task2);
		task2.setPriority(20);
		System.out.println(task2.getDescription() + " priority: " + task2.getPriority());
		System.out.print(task2);
		
		// Creates task 3
		Task task3 = new Task("Finish Activity 14");
		task3.setCategory(Task.Category.PERSONAL);
		
		//Check if task1 description is equal to task3 description
		if (task3.equals(task1)) {
			System.out.println(task1.getDescription() + " is equal to " + task3.getDescription());
		} else {
			System.out.println(task1.getDescription() + " is not equal to " + task3.getDescription());
		}

		//Compares task1 and task2 and declares the priority of them.
		if (task1.compareTo(task2) == 1) {
			System.out.println(task1.getDescription() + " priority is equal to " + task2.getDescription());
		} else if (task1.compareTo(task2) == 0) {
			System.out.println(task1.getDescription() + " priority is less than " + task2.getDescription());
		} else if (task1.compareTo(task2) == -1) {
			System.out.println(task1.getDescription() + " priority is greater than " + task2.getDescription());
		}
		
		//Activity 16
		ArrayList<Task> task = new ArrayList<Task>();
		ToDoList taskList = new ToDoList("Task List", task);
		taskList.addTask(task1);
		taskList.addTask(task2);
		taskList.addTask(task3);
		taskList.addTask("Added Task");
		System.out.print(taskList);
		System.out.println("Most important task (highest priority): ");
		System.out.print(taskList.getWork());
		
		//Tests for the equalTo working with Categories, not just priority.
		System.out.println("Tasks with different categories do not equal each other: ");
		if (task3.equals(task1)) {
			System.out.println(task1.getDescription() + " is equal to " + task3.getDescription());
		} else {
			System.out.println(task1.getDescription() + " is not equal to " + task3.getDescription());
		}
		
	}
}
