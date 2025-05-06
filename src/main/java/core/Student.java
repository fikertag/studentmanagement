package core;
import java.util.List;
import java.util.ArrayList;

public abstract class Student {
    protected String id;
    protected String name;
    protected int age;
    protected double grade;
protected List<String> comments = new ArrayList<>();

    public Student(String id, String name, int age, double grade) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getGrade() { return grade; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGrade(double grade) { this.grade = grade; }
    public void addComment(String comment) {
    comments.add(comment);
}

public List<String> getComments() {
    return comments;
}

    public abstract String getDetails();
}