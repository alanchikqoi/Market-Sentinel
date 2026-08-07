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

            System.out.println("Получен запрос от chat_id " + chatId + ": " + userText);

            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Салам! Система Market-Sentinel на связи. Твой запрос: " + userText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}