/**
 * The basic components for a category.
 * @author Steven Meyers
 * @Date 12/7/2020
 */
public class Category {
    private String name;
    private float weight;
    private int id;

    //Basic constructor for a category
    public Category(String catName, float weight2) {
        name = catName;
        weight = weight2;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public float getWeight() {
        return weight;
    }

    public void setID(int newID) {
        id = newID;
    }

    public float getID() {
        return id;
    }

    public void setWeight(float newWeight) {
        weight = newWeight;
    }


    public String toString() {
        return String.format("(%s, %f)", name, weight);
    }
    
}
