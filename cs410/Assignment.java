/**
 * The basic components for an assignment.
 * @author Steven Meyers
 * @Date 12/9/2020
 */
public class Assignment {
    private String name;
    private String category;
    private String description;
    private int points;
    private float weight = 0;

    //Basic constructor for an assignment
    public Assignment(String newName, String newCategory, String newDescription, int newPoints) {
        name = newName;
        category = newCategory;
        description = newDescription;
        points = newPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String newCategory) {
        category = newCategory;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String newDesc) {
        description = newDesc;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int newPoints) {
        points = newPoints;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float newWeight) {
        weight = newWeight;
    }

    public String toString() {
        return String.format("(%s, %s, %s, %d)", name, category, description, points);
    }

}
