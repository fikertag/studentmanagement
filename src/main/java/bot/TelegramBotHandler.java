package bot;

import core.*;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramBotHandler extends TelegramLongPollingBot {
    private StudentManager manager = new StudentManager();

    @Override
    public String getBotUsername() {
        return "StudentManagerBot";
    }

    @Override
    public String getBotToken() {
        return "7914863803:AAHmUMXZxjCG3TxtdeugZKUgJdM6sISMBNQ"; 
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    sendResponse(chatId, "Welcome! Commands:\n" +
                            "/addregular - Add regular student\n" +
                            "/addexchange - Add exchange student\n" +
                            "/list - View all students");
                    break;
                    
                case "/list":
                    sendResponse(chatId, manager.listAllStudents());
                    break;
                    
                // Add more commands as needed
            }
        }
    }

    private void sendResponse(long chatId, String text) {
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