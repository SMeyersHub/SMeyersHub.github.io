import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The driver file for the Java Shell
 * @author Steven Meyers
 * @Date 12/14/2020
 */
public class JavaShell {

    static Class currentClass = new Class("TestClass", "Sp20", 1, "Spring Test Class", 0);
    static int currentClassID = 0;

    /**
     * Class Methods Here
     * new-class [course number] [term] [section] [description]
     * list-classes: lists the number of classes
     * select-class [course number] | [course number] [term] | [course number] [term] [section]
     * show-class: shows the active class
     */

    /**
     * Creates a class and return the new class object.
     * @String classCourse - course that the class is
     * @string classTerm - term that the class is in
     * @int classSection - Section that the class is in
     * @String classDesc - A basic description of class
     * */
    public static Class createClass(String classCourse, String classTerm, int classSection, String classDesc) throws SQLException {
        Connection connection = null;
        Class newClass = new Class(classCourse, classTerm, classSection, classDesc, 0);
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sqlOutput = String.format("INSERT INTO class(course_num, term, section_num, class_desc) VALUES ('%s', '%s', %d, '%s');",
				newClass.getCourse(),
				newClass.getTerm(),
                newClass.getSection(),
                newClass.getDesc());
        
		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
        return newClass;
    }

    /**
     * Attempts to create a class and catches SQL errors thrown.
     * @String classCourse - course that the class is
     * @string classTerm - term that the class is in
     * @int classSection - Section that the class is in
     * @String classDesc - A basic description of class
     * */
	public static void attemptToCreateClass(String classCourse, String classTerm, int classSection, String classDesc) {
		try {
			Class newClass = createClass(classCourse, classTerm, classSection, classDesc);
			System.out.println(newClass.toString());
		} catch (SQLException sqlException) {
			System.out.println("Class creation failed.");
            System.out.println(sqlException.getMessage());
		}
		
	}

    /**
     * Lists all classes.
     **/
    public static List<Class> listClasses() throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		String sqlOutput = "SELECT course_num, term, section_num, class_desc, count(student_id) FROM class LEFT JOIN enrolled_in ON class.class_id = enrolled_in.class_id GROUP BY class.class_id;";
		ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
		List<Class> classes = new ArrayList<Class>();
		
        //CHANGE THIS PART FOR WHAT RESULTS YOU GET BACK
		while (resultSet.next()) {
			String resultCourse = resultSet.getString(1);
			String resultTerm = resultSet.getString(2);
			int resultSection = resultSet.getInt(3);
			String resultDesc = resultSet.getString(4);
            int resultNumStudents = resultSet.getInt(5);
			
            Class newClass = new Class(resultCourse, resultTerm, resultSection, resultDesc, resultNumStudents);
			classes.add(newClass);
		}
			resultSet.close();
			connection.close();
			return classes;
	}

    /**
     * Attempts to list the classes and catches the SQL error if something goes wrong.
     **/
    public static void attemptToListClasses() {
        try {
			List<Class> classes = listClasses();
			for (Class curClass : classes) {
				System.out.println(curClass.studentString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get classes");
            System.out.println(sqlException.getMessage());
		}
    }

    /**
     * Selects a class based off the course/term/section
     * @String classCourse - course that the class is
     * @string classTerm - term that the class is in
     * @int classSection - Section that the class is in
     * */
    public static Class selectClass(String classCourse, String classTerm, int classSection) throws SQLException {
		Connection connection = null;
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
        String sqlOutput = "";

        Class selectedClass = currentClass;

        if(classSection != 0) {
            sqlOutput += String.format("SELECT * FROM class WHERE course_num = '%s' AND term = '%s' AND section_num = %d", 
            classCourse, 
            classTerm, 
            classSection);
        } else if (classSection == 0 && classTerm.equals("none")) {
            sqlOutput += String.format("SELECT * FROM class WHERE course_num = '%s' ORDER BY class_id DESC", 
            classCourse);
        } else if (classSection == 0) {
            sqlOutput += String.format("SELECT * FROM class WHERE course_num = '%s' AND term = '%s' ORDER BY class_id ASC", 
            classCourse, 
            classTerm);
        }
		ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
        int counter = 0;
        while(resultSet.next()) {
            counter++;
        }
        if(counter == 1) {
            resultSet.beforeFirst();
            resultSet.next();
            currentClassID = resultSet.getInt(1);
            String resultCourse = resultSet.getString(2);
            String resultTerm = resultSet.getString(3);
            int resultSection = resultSet.getInt(4);
            String resultDesc = resultSet.getString(5);
            selectedClass = new Class(resultCourse, resultTerm, resultSection, resultDesc, 0);
        } else {
            System.out.println(String.format("More than one entry with those parameters. Number of results: %d", counter));
        }

		resultSet.close();
		connection.close();
		return selectedClass;
	}

    /**
     * Attempts to select a class and catches an error if something goes wrong.
     * @String classCourse - course that the class is
     * @string classTerm - term that the class is in
     * @int classSection - Section that the class is in
     * @String classDesc - A basic description of class
     **/
    public static void attemptToSelectClass(String classCourse, String classTerm, int classSection) {
        try {
			Class newClass = selectClass(classCourse, classTerm,classSection);
            currentClass = newClass;
		} catch (SQLException sqlException) {
			System.out.println("Failed to get class");
            System.out.println(sqlException.getMessage());
		}
    }

    /*
     * Category Methods Here
     * show-categories: list the categories with their weights
     * add-category [name] [weight]
     */

    /**
     * Lists all the categories.
     **/
    public static List<Category> listCategories() throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		String sqlOutput = String.format("SELECT * FROM category INNER JOIN has_category ON has_category.category_id = category.category_id INNER JOIN class ON has_category.class_id = class.class_id WHERE class.class_id = %d", currentClassID);
		ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
		List<Category> categories = new ArrayList<Category>();
		
		while (resultSet.next()) {
			String name = resultSet.getString(1);
			float weight = resultSet.getFloat(2);
			
            Category newCategory = new Category(name, weight);
			categories.add(newCategory);
		}
			resultSet.close();
			connection.close();
			return categories;
	}

    /**
     * Attempts to list the categories and catches SQL error if something goes wrong.
     **/
    public static void attemptToShowCategories() {
        try {
			List<Category> categories = listCategories();
			for (Category curCategory : categories) {
				System.out.println(curCategory.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get categories");
            System.out.println(sqlException.getMessage());
		}
    }

   /**
     * Creates a category and return the new category object.
     * @String name - name of the category
     * @float weight - weight % of total grade that the category is
     **/
    public static Category addCategory(String name, float weight) throws SQLException {
        Connection connection = null;
        Category newCategory = new Category(name, weight);
        int categoryID = 0;
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sqlOutput = String.format("INSERT INTO category(category_name, weight) VALUES ('%s', %f);",
				newCategory.getName(),
				newCategory.getWeight());
        
		sqlStatement.executeUpdate(sqlOutput);

        String sqlCheck = String.format("SELECT category_id FROM category WHERE category_name = '%s' ORDER BY category_id DESC LIMIT 1;", newCategory.getName());
        ResultSet resultSet = sqlStatement.executeQuery(sqlCheck);

        while (resultSet.next()) {
            categoryID = resultSet.getInt(1);
		}
			resultSet.close();

        String sqlOutput2 = String.format("INSERT INTO has_category(category_id, class_id) VALUES ('%s', %d);",
				categoryID,
				currentClassID);

        sqlStatement.executeUpdate(sqlOutput2);
		connection.close();
        return newCategory;
    }

    /**
     * Attempts to create a category and return the new category object.
     * @String name - name of the category
     * @float f - weight % of total grade that the category is
     **/
	public static void attemptToAddCategory(String catName, float f) {
		try {
			Category newCategory = addCategory(catName, f);
			System.out.println(newCategory.toString());
		} catch (SQLException sqlException) {
			System.out.println("Category addition failed.");
            System.out.println(sqlException.getMessage());
		}
		
	}

    /**
     * Assignment commands here
     * show-assignment: list assignments with weight
     * add-assignment [name] [category] [description] [points]
     */

     /**
     * Lists the assignments in the currently selected class.
     **/
    public static List<Assignment> listAssignments() throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		String sqlOutput = String.format("SELECT assignment_name, category.weight FROM assignment INNER JOIN has_category ON assignment.category_id = has_category.category_id INNER JOIN category ON has_category.category_id = category.category_id INNER JOIN class ON has_category.class_id = class.class_id WHERE class.class_id = %d ORDER BY category.category_name", currentClassID);
		ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
			
		List<Assignment> assignments = new ArrayList<Assignment>();
		
        //CHANGE THIS PART FOR WHAT RESULTS YOU GET BACK
		while (resultSet.next()) {
			String name = resultSet.getString(1);
			float weight = resultSet.getFloat(2);
			
            Assignment newAssignment = new Assignment(name, "", "", 0);
            newAssignment.setWeight(weight);
			assignments.add(newAssignment);
		}
			resultSet.close();
			connection.close();
			return assignments;
	}

    /**
     * Attempts to show the assignments in the class and catches the sql error if something goes wrong.
     **/
    public static void attemptToShowAssignments() {
        try {
			List<Assignment> assignments = listAssignments();
			for (Assignment curAssignment : assignments) {
				System.out.println(String.format("(%s, %f)", curAssignment.getName(), curAssignment.getWeight()));
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get assignments");
            System.out.println(sqlException.getMessage());
		}
    }

    /**
     * Creates an assignment.
     * @String name - name of the assignment
     * @String category - category of the assignment
     * @String description - description of the assignment
     * @int points - number of points the assignment is out of
     **/
    public static Assignment addAssignment(String name, String category, String description, int points) throws SQLException {
        Connection connection = null;
        Assignment newAssignment = new Assignment(name, category, description, points);
        int categoryID = 0;
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sqlCheck = String.format("SELECT category.category_id FROM has_category INNER JOIN category ON has_category.category_id = category.category_id WHERE class_id = %d AND category_name = '%s'", currentClassID, newAssignment.getCategory());
        ResultSet resultSet = sqlStatement.executeQuery(sqlCheck);

        while (resultSet.next()) {
            categoryID = resultSet.getInt(1);
		}
		resultSet.close();

        String sqlOutput = String.format("INSERT INTO assignment(assignment_name, category_id, assignment_desc, point_value) VALUES ('%s', '%s', '%s', %d);",
            newAssignment.getName(),
            categoryID,
            newAssignment.getDesc(),
            newAssignment.getPoints());


		sqlStatement.executeUpdate(sqlOutput);
		connection.close();
        return newAssignment;
    }

    /**
     * Attempts to create an assignment.
     * @String name - name of the assignment
     * @String category - category of the assignment
     * @String description - description of the assignment
     * @int points - number of points the assignment is out of
     **/
	public static void attemptToAddAssignment(String name, String category, String description, int points) {
		try {
			Assignment newAssignment = addAssignment(name, category, description, points);
			System.out.println(newAssignment.toString());
		} catch (SQLException sqlException) {
			System.out.println("Assignment addition failed.");
            System.out.println(sqlException.getMessage());
		}
		
	}

    /* Student Management commands here
    * add-student [username] [studentid] [Last] [First]
    * add-student [username]
    * show-students
    * show-students [string]
    * grade [assignmentname] [username] [grade]
    */

    /**
     * Creates a student.
     * @String username - username of the student
     * @int studentid - id of the student
     * @String lastName - last name of the student
     * @String firstName - first name of the student
     **/
    public static Student addStudent(String username, int studentid, String lastName, String firstName) throws SQLException {
        Connection connection = null;
        Student newStudent = new Student(studentid, lastName, firstName, username);
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        if(studentid == 0) {
            String sqlCheck = String.format("SELECT * FROM student WHERE username = '%s'", username);
            ResultSet resultSet = sqlStatement.executeQuery(sqlCheck);
            resultSet.next();
            studentid = resultSet.getInt(1);

            resultSet.close();
            
            String sqlOutput2 = String.format("INSERT INTO enrolled_in(class_id, student_id) VALUES (%d, %d);",
                currentClassID,
                studentid);

            sqlStatement.executeUpdate(sqlOutput2);

        } else {

            String sqlCheck = String.format("SELECT * FROM student WHERE username = '%s' AND student_id = %d", username, studentid);
            ResultSet resultSet = sqlStatement.executeQuery(sqlCheck);
            //Create and enroll student
            
            if(!resultSet.next()) {
                String sqlOutput = String.format("INSERT INTO student(student_id, student_last, student_first, username) VALUES (%d, '%s', '%s', '%s'); ",
                    newStudent.getID(),
                    newStudent.getLast(),
                    newStudent.getFirst(),
                    newStudent.getUsername());
                
                sqlStatement.executeUpdate(sqlOutput);

                String sqlOutput2 = String.format("INSERT INTO enrolled_in(class_id, student_id) VALUES (%d, %d);",
                    currentClassID,
                    newStudent.getID());

                sqlStatement.executeUpdate(sqlOutput2);
            //If student already exists, just change their name
            } else if(!resultSet.getString(2).equals(lastName) || !resultSet.getString(3).equals(firstName)) {
                System.out.println("Updating name.");

                String sqlOutput = String.format("UPDATE student SET student_last = '%s', student_first = '%s' WHERE student_id = %d AND username = '%s'",
                    newStudent.getLast(),
                    newStudent.getFirst(),
                    resultSet.getInt(1),
                    resultSet.getString(4));
                
                sqlStatement.executeUpdate(sqlOutput);
            }
            resultSet.close();
        }

		connection.close();
        return newStudent;
    }

    /**
     * Attempts to create an assignment using addAssignment and prints errors if they occur.
     * @String username - username of the student
     * @int studentid - id of the student
     * @String lastName - last name of the student
     * @String firstName - first name of the student
     **/
	public static void attemptToAddStudent(int studentid, String lastName, String firstName, String username) {
		try {
			Student newStudent = addStudent(username, studentid, lastName, firstName);
            if(studentid != 0) {
                System.out.println(newStudent.toString());
            }
		} catch (SQLException sqlException) {
			System.out.println("Student addition failed.");
            System.out.println(sqlException.getMessage());
		}
		
	}

    /**
     * Gets all the students for the currently selected class.
     * @String string - search string in a username/last/first name.
     **/
    public static List<Student> listStudents(String string) throws SQLException {

        if(string.isEmpty()) {
            Connection connection = null;
            connection = MySqlDatabase.getDatabaseConnection();
            Statement sqlStatement = connection.createStatement();
            String sqlOutput = String.format("SELECT student.username, student.student_id, student.student_last, student.student_first FROM enrolled_in INNER JOIN student ON enrolled_in.student_id = student.student_id WHERE enrolled_in.class_id = %d;", currentClassID);
            ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
                
            List<Student> students = new ArrayList<Student>();
            
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                int studentID = resultSet.getInt(2);
                String studentLast = resultSet.getString(3);
                String studentFirst = resultSet.getString(4);
                Student newStudent = new Student(studentID, studentLast, studentFirst, username);
                students.add(newStudent);
            }
                resultSet.close();
                connection.close();
                return students;
        } else {
            Connection connection = null;
            connection = MySqlDatabase.getDatabaseConnection();
            Statement sqlStatement = connection.createStatement();
            String sqlOutput = String.format("SELECT student.username, student.student_id, student.student_last, student.student_first FROM enrolled_in INNER JOIN student ON enrolled_in.student_id = student.student_id WHERE enrolled_in.class_id = %d AND student.student_first LIKE '%%%s%%' OR student.student_last LIKE '%%%s%%' OR student.username LIKE '%%%s%%' GROUP BY student_id;", currentClassID, string, string, string);
            ResultSet resultSet = sqlStatement.executeQuery(sqlOutput);
                
            List<Student> students = new ArrayList<Student>();
            
            while (resultSet.next()) {
                String username = resultSet.getString(1);
                int studentID = resultSet.getInt(2);
                String studentLast = resultSet.getString(3);
                String studentFirst = resultSet.getString(4);
                Student newStudent = new Student(studentID, studentLast, studentFirst, username);
                students.add(newStudent);
            }
                resultSet.close();
                connection.close();
                return students;
        }
	}

    /**
     * Attempts to list the classes and catches the SQL error if it doesn't work.
     * @String string - search string in a username/last/first name.
     **/
    public static void attemptToShowStudents(String string) {
        try {
			List<Student> students = listStudents(string);
			for (Student curStudent : students) {
				System.out.println(curStudent.toString());
			}
		} catch (SQLException sqlException) {
			System.out.println("Failed to get categories");
            System.out.println(sqlException.getMessage());
		}
    }

    /**
     * Grades an assignment.
     * @String assignmentName - name of the assignment
     * @String username - username of the student
     * @int grade - grade being assigned to that assignment and student
     **/
    public static void gradeAssignment(String assignmentName, String username, int grade) throws SQLException {
        Connection connection = null;
        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        int gradeStudentID = 0;
        int gradeAssignmentID = 0;
        int allowedPoints = 0;

        String sqlCheck = String.format("SELECT * FROM student INNER JOIN enrolled_in ON enrolled_in.student_id = student.student_id INNER JOIN has_category ON enrolled_in.class_id = has_category.class_id INNER JOIN assignment ON assignment.category_id = has_category.category_id WHERE enrolled_in.class_id = %d AND student.username = '%s' AND assignment.assignment_name = '%s'",currentClassID, username,assignmentName);
        ResultSet resultSet = sqlStatement.executeQuery(sqlCheck);

        while (resultSet.next()) {
            gradeStudentID = resultSet.getInt(1);
            gradeAssignmentID = resultSet.getInt(11);
            allowedPoints = resultSet.getInt(15);
		}
		resultSet.close();
        if(grade <= allowedPoints) {
            String sqlCheck2 = String.format("SELECT * FROM grade_on WHERE student_id = %d AND assignment_id = %d", gradeStudentID, gradeAssignmentID);
            ResultSet resultSet2 = sqlStatement.executeQuery(sqlCheck2);

            if(!resultSet2.next()) {
                String sqlOutput = String.format("INSERT INTO grade_on(student_id, assignment_id, grade_score) VALUES (%d, %d, %d);",
                gradeStudentID,
                gradeAssignmentID,
                grade);

                sqlStatement.executeUpdate(sqlOutput);

            } else {
                String sqlOutput = String.format("UPDATE grade_on SET grade_score = %d WHERE student_id = %d AND assignment_id = %d",
                    grade,
                    gradeStudentID,
                    gradeAssignmentID);
                sqlStatement.executeUpdate(sqlOutput);
            }
        } else {
            System.out.println(String.format("Grade can not be higher than the allowed points! Allowed points for this assignment: %d", allowedPoints));
        }
		connection.close();
    }

    /**
     * Attempts to grade an assignment.
     * @String assignmentName - name of the assignment
     * @String username - username of the student
     * @int grade - grade being assigned to that assignment and student
     **/
	public static void attemptToGrade(String assignmentName, String username, int grade) {
		try {
			gradeAssignment(assignmentName, username, grade);
		} catch (SQLException sqlException) {
			System.out.println("Grading failed.");
            System.out.println(sqlException.getMessage());
		}
		
	}

    /**
     * Main method that runs the Java Shell Script.
     */
    public static void main(String args[]) {
        boolean loop = true;
        System.out.print("Command List: \nClass Management Commands: \n new-class [course number] [term] [section] [description] \n list-classes: lists the number of classes \n select-class [course number] | [course number] [term] | [course number] [term] [section] \n show-class: shows the active class \n");
        System.out.print("Category and Assignment Management: \n show-categories: list the categories with their weights \n add-category [name] [weight] \n show-assignment: list assignments with weight \n add-assignment [name] [category] [description] [points] \n");
        System.out.print("Student Management: \n add-student [username] [studentid] [Last] [First] \n add-student [username] \n show-students \n show-students [string] \n grade [assignmentname] [username] [grade] \n");
        System.out.print("Grade Reporting: \n student-grades [username] \n gradebook \n");
        Scanner myObj = new Scanner(System.in);
        while(loop) {
            System.out.print("! ");

            String command = myObj.nextLine();
            //System.out.println("Command recieved: " + command);
            String[] commandArr = command.split(" ");

        switch(commandArr[0]) {
            /*
             * Class Management Commands
             * new-class [course number] [term] [section] [description]
             * list-classes: lists the number of classes
             * select-class [course number] | [course number] [term] | [course number] [term] [section]
             * show-class: shows the active class
             */
            case "new-class":
                if(commandArr.length == 5) {
                    attemptToCreateClass(commandArr[1], commandArr[2], Integer.parseInt(commandArr[3]), commandArr[4]);
                } else {
                    System.out.println("Wrong use of command. Try: new-class [course number] [term] [section] [description]");
                }
                break;

            case "list-classes":
                attemptToListClasses();
                break;

            case "select-class":
                if(commandArr.length == 2) {
                    attemptToSelectClass(commandArr[1], "none", 0);
                } else if (commandArr.length == 3) {
                    attemptToSelectClass(commandArr[1], commandArr[2], 0);
                } else if (commandArr.length == 4) {
                    attemptToSelectClass(commandArr[1], commandArr[2], Integer.parseInt(commandArr[3]));
                } else {
                    System.out.println("Wrong use of command. Try: select-class [course number] | [course number] [term] | [course number] [term] [section]");
                }
                break;

            case "show-class":
                System.out.println(currentClass.toString());
                break;

            /*
             * Category and Assignment Management
             * show-categories: list the categories with their weights
             * add-category [name] [weight]
             * show-assignment: list assignments with weight
             * add-assignment [name] [category] [description] [points]
             */
            case "show-categories":
                attemptToShowCategories();
                break;

            case "add-category":
                if(commandArr.length == 3) {
                    attemptToAddCategory(commandArr[1], Float.parseFloat(commandArr[2]));
                } else {
                    System.out.println("Wrong use of command. Try: add-category [name] [weight]");
                }
                break;
            
            case "show-assignment":
                attemptToShowAssignments();
                break;
            
            case "add-assignment":
                if(commandArr.length == 5) {
                    attemptToAddAssignment(commandArr[1], commandArr[2], commandArr[3], Integer.parseInt(commandArr[4]));
                } else {
                    System.out.println("Wrong use of command. Try: add-assignment [name] [category] [description] [points]");
                }
                break;
            
            /* Student Management
             * add-student [username] [studentid] [Last] [First]
             * add-student [username]
             * show-students
             * show-students [string]
             * grade [assignmentname] [username] [grade]
             */
            case "add-student":
                if(commandArr.length == 5) {
                    attemptToAddStudent(Integer.parseInt(commandArr[2]), commandArr[3], commandArr[4], commandArr[1]);
                } else if(commandArr.length == 2) {
                    attemptToAddStudent(0,"", "", commandArr[1]);
                } else {
                    System.out.println("Wrong use of command. Try: add-student [username] | [username] [studentid] [Last] [First]");
                }
                break;
            
            case "show-students":
                if(commandArr.length == 1) {
                    attemptToShowStudents("");
                } else if(commandArr.length == 2) {
                    attemptToShowStudents(commandArr[1]);
                }
                break;

            case "grade":
                attemptToGrade(commandArr[1],commandArr[2],Integer.parseInt(commandArr[3]));
                break;
            /*
             * Grade Reporting
             * student-grades [username]
             * gradebook
             */
            case "student-grades":
                
                break;
            
            case "gradebook":
                
                break;
            
            case "exit":
                loop = false;
                break;
            
            default:
                System.out.println("Input not accepted. Please check your input and retry.");

        }
    }
        myObj.close();
    }
    
}
