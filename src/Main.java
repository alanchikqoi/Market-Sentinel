import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем точку входа для Telegram API
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            // Запускаем нашего Telegram-агента
            botsApi.registerBot(new TelegramAgent());

            System.out.println("Система в онлайне. Бот готов к спаррингу!");

        } catch (TelegramApiException e) {
            System.out.println("Критическая ошибка при запуске бота:");
            e.printStackTrace();
        }
    }
}