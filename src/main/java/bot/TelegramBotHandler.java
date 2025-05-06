package bot;

import core.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private final StudentManager manager = new StudentManager();
    private String currentCommand = "";
    private Student tempStudent;

 public TelegramBotHandler() {
        manager.loadStudentsFromFile();  // Load students from file on bot startup
    }

    @Override
    public String getBotUsername() {
        return "smsjava_bot";
    }

   @Override
public String getBotToken() {
    String botToken = System.getenv("BOT_TOKEN");
    if (botToken == null) {
        throw new IllegalStateException("Bot token is missing. Please set the BOT_TOKEN environment variable.");
    }
    return botToken;
}


    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) return;

        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        try {
            if (messageText.startsWith("/")) {
                handleCommand(chatId, messageText);
            } else {
                handleInput(chatId, messageText);
            }
        } catch (Exception e) {
            sendMessage(chatId, "❌ Error: " + e.getMessage());
        }
    }

    private void handleCommand(long chatId, String command) {
        currentCommand = command;
        switch (command) {
            case "/start":
                showMainMenu(chatId);
                break;
            case "/add":
                askStudentType(chatId);
                break;
            case "/view":
                sendMessage(chatId, formatStudentList());
                break;
            case "/edit":
                sendMessage(chatId, "Enter student ID to edit:");
                break;
            case "/comment":
                sendMessage(chatId, "Enter student ID you want to comment on:");
                break;
            case "/find":
                sendMessage(chatId, "Enter student ID:");
                break;
            case "/delete":
                sendMessage(chatId, "Enter student ID to delete:");
                break;
            case "/stats":
                sendMessage(chatId, "Average grade: " + manager.getAverageGrade());
                break;
            default:
                sendMessage(chatId, "Unknown command");
        }
    }

    private void handleInput(long chatId, String input) {
        switch (currentCommand) {
            case "/add":
                handleAddStudent(chatId, input);
                break;
            case "/edit":
                handleEditStudent(chatId, input);
                break;
            case "/editing":
                handleEditDetails(chatId, input);
                break;
                case "/comment":
                tempStudent = manager.findStudent(input);
                if (tempStudent != null) {
                    currentCommand = "/addcomment";
                    sendMessage(chatId, "Enter your comment for " + tempStudent.getName());
                } else {
                    sendMessage(chatId, "❌ Student not found.");
                }
                break;
           case "/addcomment":
                tempStudent.addComment(input);
                sendMessage(chatId, "✅ Comment added.");
                tempStudent = null;
                break;

            case "/find":
                Student found = manager.findStudent(input);
                sendMessage(chatId, found != null ? found.getDetails() : "Student not found");
                break;
            case "/delete":
                boolean deleted = manager.deleteStudent(input);
                sendMessage(chatId, deleted ? "Student deleted" : "Student not found");
                break;
            default:
                sendMessage(chatId, "Please select a command first");
        }
    }

    private void handleAddStudent(long chatId, String input) {
        if (tempStudent == null) {
            // First input: student type
            if (input.equalsIgnoreCase("regular")) {
                tempStudent = new RegularStudent("", "", 0, 0.0, "");
                sendMessage(chatId, "Enter student details in this format:\nID,Name,Age,Grade,Major");
            } else {
                tempStudent = new ExchangeStudent("", "", 0, 0.0, "", 0);
                sendMessage(chatId, "Enter student details in this format:\nID,Name,Age,Grade,Country,Duration(months)");
            }
        } else {
            // Process student data
            String[] parts = input.split(",");
            try {
                if (tempStudent instanceof RegularStudent) {
                    RegularStudent s = (RegularStudent) tempStudent;
                    s.setId(parts[0].trim());
                    s.setName(parts[1].trim());
                    s.setAge(Integer.parseInt(parts[2].trim()));
                    s.setGrade(Double.parseDouble(parts[3].trim()));
                    s.setMajor(parts[4].trim());
                } else {
                    ExchangeStudent s = (ExchangeStudent) tempStudent;
                    s.setId(parts[0].trim());
                    s.setName(parts[1].trim());
                    s.setAge(Integer.parseInt(parts[2].trim()));
                    s.setGrade(Double.parseDouble(parts[3].trim()));
                    s.setHomeCountry(parts[4].trim());
                    s.setDurationMonths(Integer.parseInt(parts[5].trim()));
                }
                
                manager.addStudent(tempStudent);
                sendMessage(chatId, "✅ Student added successfully!");
                tempStudent = null;
            } catch (Exception e) {
                sendMessage(chatId, "❌ Invalid format. Please try again.");
            }
        }
    }

           private void handleEditStudent(long chatId, String input) {
           Student student = manager.findStudent(input);
           if (student == null) {
               sendMessage(chatId, "❌ Student not found");
               return;
           }
           tempStudent = student;
           currentCommand = "/editing";
           sendMessage(chatId, "Send updated details in the same format as before:\n" +
               (student instanceof RegularStudent ?
                   "ID,Name,Age,Grade,Major" :
                   "ID,Name,Age,Grade,Country,Duration(months)"));
       }
       private void handleEditDetails(long chatId, String input) {
    try {
        String[] parts = input.split(",");
        if (tempStudent instanceof RegularStudent s) {
            s.setId(parts[0].trim());
            s.setName(parts[1].trim());
            s.setAge(Integer.parseInt(parts[2].trim()));
            s.setGrade(Double.parseDouble(parts[3].trim()));
            s.setMajor(parts[4].trim());
        } else if (tempStudent instanceof ExchangeStudent s) {
            s.setId(parts[0].trim());
            s.setName(parts[1].trim());
            s.setAge(Integer.parseInt(parts[2].trim()));
            s.setGrade(Double.parseDouble(parts[3].trim()));
            s.setHomeCountry(parts[4].trim());
            s.setDurationMonths(Integer.parseInt(parts[5].trim()));
        }
        sendMessage(chatId, "✅ Student updated!");
        tempStudent = null;
    } catch (Exception e) {
        sendMessage(chatId, "❌ Invalid format.");
    }
}


    private String formatStudentList() {
        StringBuilder sb = new StringBuilder();
        if (manager.getAllStudents().isEmpty()) {
            return "No students registered";
        }
        manager.getAllStudents().forEach(s -> sb.append(s.getDetails()).append("\n\n"));
        return sb.toString();
    }

    private void showMainMenu(long chatId) {
    String menu = """
        🎓 *Student Management Bot*
        Choose an option:

        /add - Add new student
        /view - List all students
        /find - Find student by ID
        /edit - Edit student info
        /comment - Add comment to student
        /delete - Delete student
        /stats - View statistics
        """;
    sendMessage(chatId, menu);
}


    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setParseMode("Markdown");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void askStudentType(long chatId) {
    sendMessage(chatId, "Is the student 'regular' or 'exchange'?");
}

}

