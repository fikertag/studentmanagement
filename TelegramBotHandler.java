import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private final StudentManager manager = new StudentManager();

    @Override
    public String getBotUsername() {
        return "StudentManagerBot"; // Your bot's name
    }

    @Override
    public String getBotToken() {
        return "YOUR_BOT_TOKEN"; // From BotFather
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendText(chatId, "Welcome to Student Manager Bot! Use:\n" +
                                     "/add - Add student\n" +
                                     "/view - View all students");
                    break;
                case "/add":
                    sendText(chatId, "Enter student details:\n" +
                                     "Format: /add name,age,grade,type,major_or_country");
                    break;
                case "/view":
                    sendText(chatId, manager.viewAllStudentsAsString());
                    break;
                default:
                    if (messageText.startsWith("/add ")) {
                        String[] parts = messageText.substring(5).split(",");
                        // Parse and add student (simplified)
                        manager.addStudent(new RegularStudent(...));
                        sendText(chatId, "Student added!");
                    }
            }
        }
    }

    private void sendText(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}