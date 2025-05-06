package core;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public String listAllStudents() {
        if (students.isEmpty()) return "No students registered.";
        
        StringBuilder sb = new StringBuilder();
        for (Student s : students) {
            sb.append(s.getDetails()).append("\n\n");
        }
        return sb.toString();
    }

    public Student findStudent(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}