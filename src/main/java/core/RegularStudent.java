package core;

public class RegularStudent extends Student {
    private String major;

    public RegularStudent(String id, String name, int age, double grade, String major) {
        super(id, name, age, grade);
        this.major = major;
    }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    @Override
    public String getDetails() {
        return String.format("[Regular] %s (ID: %s) - Age: %d, Grade: %.1f, Major: %s",
                name, id, age, grade, major);
    }
}