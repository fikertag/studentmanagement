package core;

public class RegularStudent extends Student {
    private String major;

    public RegularStudent(String id, String name, int age, double grade, String major) {
        super(id, name, age, grade);
        this.major = major;
    }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    // Add missing setters for inherited fields
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setGrade(double grade) { this.grade = grade; }

@Override
public String getDetails() {
    StringBuilder sb = new StringBuilder();
    sb.append("🆔 ID: ").append(getId()).append("\n")
      .append("👤 Name: ").append(getName()).append("\n")
      .append("🎂 Age: ").append(getAge()).append("\n")
      .append("📊 Grade: ").append(getGrade()).append("\n")
      .append("🎓 Major: ").append(getMajor()).append("\n");

    if (!comments.isEmpty()) {
        sb.append("💬 Comments:\n");
        for (String c : comments) {
            sb.append(" - ").append(c).append("\n");
        }
    }

    return sb.toString();
}


}
