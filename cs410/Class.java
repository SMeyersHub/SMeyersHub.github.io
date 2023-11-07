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
    
    //Basic constructor for a class.
    public Class(String classCourse, String classTerm, int classSection, String classDesc, int numStudents) {
        courseNum = classCourse;
        term = classTerm;
        section = classSection;
        desc = classDesc;
        students = numStudents;
    }

    public String getCourse() {
        return courseNum;
    }

    public void setCourse(String newCourse) {
        courseNum = newCourse;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String newTerm) {
        term = newTerm;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int newSection) {
        section = newSection;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String newDesc) {
        desc = newDesc;
    }

    public int getStudents() {
        return students;
    }

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
