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

            // 1. Создаем тестовую хату с правильным конструктором (ID, Цена, Площадь, Ссылка)
            Listing testListing = new Listing("1001", 15000000, 45.0, "https://krisha.kz/test");

            // 2. Подключаем твой мозг (Анализатор)
            Analyzer analyzer = new Analyzer();
            String verdict = analyzer.analyzeDeal(testListing);

            // 3. Формируем ответный удар с результатами анализа
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Запрос принят: " + userText + "\n\nРЕЗУЛЬТАТ АНАЛИЗА:\n" + verdict);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}