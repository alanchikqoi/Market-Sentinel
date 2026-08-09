import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramAgent extends TelegramLongPollingBot {

    private Long adminChatId = null;

    private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

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

            // === НОВАЯ ЛОГИКА АВТОПИЛОТА ===
            if (userText.equals("/start")) {
                adminChatId = chatId; // Запоминаем хозяина
                sendMsg(chatId, "Салам! Я запомнил твой ID: " + adminChatId + ". Включаю автопилот...");

                // Запускаем фоновую задачу каждые 10 секунд
                timer.scheduleAtFixedRate(() -> {
                    if (adminChatId != null) {
                        System.out.println("Таймер сработал! Имитация поиска...");
                        sendMsg(adminChatId, "Автопилот в деле: мониторю рынок...");
                    }
                }, 0, 10, TimeUnit.SECONDS);

                return; // Прерываем метод, чтобы он не пошел дальше
            }

            // === ТВОЯ СТАРАЯ ЛОГИКА ПАРСЕРА ===
            if (userText.startsWith("http")) {
                System.out.println("Получена ссылка от пользователя, запускаем парсер...");
                sendMsg(chatId, "Принял ссылку! Пробиваю защиту сайта...");

                // ... (здесь твой рабочий код с KrishaParser и Analyzer) ...

                // А в конце вместо старого отправления пишешь просто:
                // sendMsg(chatId, "РЕЗУЛЬТАТ АНАЛИЗА ОБЪЕКТА:\n\n" + verdict);
            } else {
                sendMsg(chatId, "Скинь мне ссылку на квартиру с Крыши, а я выдам вердикт BUY/SKIP. Или нажми /start для автопилота.");
            }
        }
    }
    private void sendMsg(long ChatId, String text){
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(ChatId));
            message.setText(text);
            try {
                execute(message);
            }catch(TelegramApiException e) {
                e.printStackTrace();
        }
    }
}