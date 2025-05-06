package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.JsonParseException;


public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private static final String FILE_PATH = "students.json";

    public void addStudent(Student student) {
        students.add(student);
                saveStudentsToFile();  
    }

     // Save students to file
    public void saveStudentsToFile() {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(students, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // Load students from file
  public void loadStudentsFromFile() {
    try (FileReader reader = new FileReader(FILE_PATH)) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Student.class, new StudentDeserializer()); // Register custom deserializer
        Gson gson = gsonBuilder.create();

        Student[] studentArray = gson.fromJson(reader, Student[].class);
        students = new ArrayList<>(List.of(studentArray));
    } catch (IOException e) {
        System.out.println("No previous data found. Starting fresh.");
    } catch (JsonParseException e) {
        System.out.println("Error parsing student data: " + e.getMessage());
    }
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

    // ✅ NEW: Delete a student by ID
    public boolean deleteStudent(String id) {
        return students.removeIf(s -> s.getId().equals(id));
    }

    // ✅ NEW: Get average grade of all students
    public double getAverageGrade() {
        if (students.isEmpty()) return 0.0;
        return students.stream()
                .mapToDouble(Student::getGrade)
                .average()
                .orElse(0.0);
    }

    // ✅ NEW: Return full student list
    public List<Student> getAllStudents() {
        return students;
    }
}
