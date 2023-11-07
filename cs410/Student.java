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

    //Basic constructor for a category
    public Student(int newID, String newLast, String newFirst, String newName) {
        username = newName;
        studentid = newID;
        last = newLast;
        first = newFirst;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String newName) {
        username = newName;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String newLast) {
        last = newLast;
    }

    public String getFirst() {
        return first;
    }

    public void setDesc(String newFirst) {
        first = newFirst;
    }

    public int getID() {
        return studentid;
    }

    public void setID(int newID) {
        studentid = newID;
    }

    public String toString() {
        return String.format("(%d, '%s', '%s', '%s')", studentid, username, last, first);
    }
}
