import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramAgent());

            System.out.println("[INFO] System online. Bot is initialized and running.");

        } catch (TelegramApiException e) {
            System.out.println("[ERROR] Critical failure during bot registration:");
            e.printStackTrace();
        }
    }
}