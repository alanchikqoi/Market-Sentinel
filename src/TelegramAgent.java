import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramAgent extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "AlanMarketSentinel_bot";
    }

    @Override
    public String getBotToken() {
        return "8925210322:AAEKP7FH0pAdLM_8RKNMl6ZvicaiUjYYOak";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));

            // Проверяем, прислал ли пользователь ссылку (начинается с http)
            if (userText.startsWith("http")) {
                System.out.println("Получена ссылка от пользователя, запускаем парсер...");
                message.setText("Принял ссылку! Пробиваю защиту сайта...");

                try {
                    execute(message); // Сразу отвечаем, чтобы пользователь не ждал
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                // 1. Запускаем нашего наемника (парсер)
                KrishaParser parser = new KrishaParser();
                Listing listing = parser.parseDeal(userText);

                if (listing != null) {
                    // 2. Подключаем Анализатор
                    Analyzer analyzer = new Analyzer();
                    String verdict = analyzer.analyzeDeal(listing);

                    // 3. Отправляем готовый вердикт
                    message.setText("РЕЗУЛЬТАТ АНАЛИЗА ОБЪЕКТА:\n\n" + verdict);
                } else {
                    message.setText("Ошибка: Не удалось спарсить объявление. Проверь ссылку.");
                }

            } else {
                message.setText("Салам! Скинь мне ссылку на квартиру с Крыши, а я выдам вердикт BUY/SKIP.");
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}