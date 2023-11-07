/**
 * The basic components for a class.
 * @author Steven Meyers
 * @Date 12/6/2020
 */
public class Class {
    private String courseNum;
    private String term;
    private int section;
    private String desc;
    private int students;
    
    /**
     * Constructor for a class
     * @param classCourse - Classes course
     * @param classTerm - Term the class is in
     * @param classSection - Section the class is in
     * @param classDesc - Description of class
     * @param numStudents - Number of students in the class
     */
    public Class(String classCourse, String classTerm, int classSection, String classDesc, int numStudents) {
        courseNum = classCourse;
        term = classTerm;
        section = classSection;
        desc = classDesc;
        students = numStudents;
    }

    /**
     * Gets the class course
     * @return courseNum
     */
    public String getCourse() {
        return courseNum;
    }

    /**
     * Sets the class course
     * @param newCourse - new course
     */
    public void setCourse(String newCourse) {
        courseNum = newCourse;
    }

    /**
     * Gets the class term
     * @return term
     */
    public String getTerm() {
        return term;
    }

    /**
     * Sets the class term
     * @param newTerm - new term
     */
    public void setTerm(String newTerm) {
        term = newTerm;
    }

    /**
     * Gets the class section
     * @return section
     */
    public int getSection() {
        return section;
    }

    /**
     * Sets the class section
     * @param newSection - new section
     */
    public void setSection(int newSection) {
        section = newSection;
    }

    /**
     * Gets the class description
     * @return desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the class description
     * @param newDesc - new description
     */
    public void setDesc(String newDesc) {
        desc = newDesc;
    }

    /**
     * Gets the class student count
     * @return students
     */
    public int getStudents() {
        return students;
    }

    /**
     * Sets the number of students
     * @param newStudents - new number of students
     */
    public void setStudents(int newStudents) {
        students = newStudents;
    }

    public String toString() {
        return String.format("(%s, %s, %d, %s)", courseNum, term, section, desc);
    }

    public String studentString() {
        return String.format("(%s, %s, %d, %s, %d)", courseNum, term, section, desc, students);
    }

}
