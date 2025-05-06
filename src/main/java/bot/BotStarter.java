package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class BotStarter {
    public static void main(String[] args) {
        // Start a simple HTTP server (optional, for health checks)
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/", exchange -> {
                String response = "Bot is running!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            });
            server.start();
            System.out.println("HTTP server started at http://localhost:8080");

            // Start Telegram bot
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBotHandler());
            System.out.println("Bot started successfully!");
        } catch (IOException | TelegramApiException e) {
            System.err.println("Error starting bot or HTTP server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
