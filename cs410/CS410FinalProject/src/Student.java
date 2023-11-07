/**
 * The basic components for a student.
 * @author Steven Meyers
 * @Date 12/9/2020
 */
public class Student {
    private String username;
    private String first;
    private String last;
    private int studentid;

    /**
     * Basic constructor for a student
     * @param newID - studentID
     * @param newLast -student last name
     * @param newFirst - student first name
     * @param newName - student user name
     */
    public Student(int newID, String newLast, String newFirst, String newName) {
        username = newName;
        studentid = newID;
        last = newLast;
        first = newFirst;
    }

    /**
     * Gets the students username
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the students username
     * @param newName - new username
     */
    public void setName(String newName) {
        username = newName;
    }

    /**
     * Gets the students last name
     * @return last - students last name
     */
    public String getLast() {
        return last;
    }

    /**
     * Sets the student's last name
     * @param newLast - students last name
     */
    public void setLast(String newLast) {
        last = newLast;
    }

    /**
     * Gets the student's first name
     * @return first - students first name
     */
    public String getFirst() {
        return first;
    }

    /**
     * Sets the student's first name
     * @param newFirst - students new first name
     */
    public void setFirst(String newFirst) {
        first = newFirst;
    }

    /**
     * Gets the student's ID
     * @return studentid - student's ID
     */
    public int getID() {
        return studentid;
    }

    /**
     * Sets the student's ID
     * @param newID -student's new iD
     */
    public void setID(int newID) {
        studentid = newID;
    }

    public String toString() {
        return String.format("(%d, '%s', '%s', '%s')", studentid, username, last, first);
    }
}
