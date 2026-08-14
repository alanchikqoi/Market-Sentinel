import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

            if (userText.equals("/start")) {
                adminChatId = chatId;
                sendMsg(chatId, "Салам! Я запомнил твой ID: " + adminChatId + ". Включаю автопилот...");

                timer.scheduleAtFixedRate(() -> {
                    if (adminChatId != null) {
                        System.out.println("Таймер сработал! Запускаем парсер в фоне...");


                        String targetUrl = "https://krisha.kz/a/show/1012348514";

                        KrishaParser parser = new KrishaParser();
                        Listing listing = parser.parseDeal(targetUrl);

                        if (listing != null) {
                            Analyzer analyzer = new Analyzer();
                            String verdict = analyzer.analyzeDeal(listing);

                            sendMsg(adminChatId, "АВТОПИЛОТ ОТЧЕТ:\n\n" + verdict);
                        } else {
                            System.out.println("Баг автопилота: Не удалось спарсить объявление.");
                        }
                    }
                }, 0, 30, TimeUnit.MINUTES);

                return;
            }

            if (userText.startsWith("http")) {
                System.out.println("Получена ссылка от пользователя, запускаем парсер...");
                sendMsg(chatId, "Принял ссылку! Пробиваю защиту сайта...");



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