package bot;

import core.Student;

public class UserSession {
    private String currentCommand;
    private Student tempStudent;
    private long lastActivity = System.currentTimeMillis();

    // Getters and setters
    public String getCurrentCommand() { return currentCommand; }
    public void setCurrentCommand(String command) { 
        this.currentCommand = command;
        updateActivity();
    }
    
    public Student getTempStudent() { return tempStudent; }
    public void setTempStudent(Student student) { 
        this.tempStudent = student;
        updateActivity();
    }
    
    public long getLastActivity() { return lastActivity; }
    public void updateActivity() { this.lastActivity = System.currentTimeMillis(); }
}