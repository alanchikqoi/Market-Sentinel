import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TelegramAgent extends TelegramLongPollingBot {

    private Long adminChatId = null;

    private HashSet<String> viewedLinks = new HashSet<>();
    private ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);

    @Override
    public String getBotUsername() {
        return "BOT_USERNAME";
    }

    @Override
    public String getBotToken() {
        return "BOT_TOKEN";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (userText.equals("/start")) {
                adminChatId = chatId;
                sendMsg(chatId, "Салем! Я запомнил твой ID. Включаю снайперский автопилот...");

                timer.scheduleAtFixedRate(() -> {
                    if (adminChatId != null) {
                        System.out.println("[INFO] Timer triggered. Scanning for new listings...");


                        String searchUrl = "https://krisha.kz/prodazha/kvartiry/almaty/?das[live.rooms]=2&das[price][to]=35000000";

                        KrishaParser parser = new KrishaParser();


                        List<String> freshLinks = parser.parseSearchPage(searchUrl);

                        for (String dealUrl : freshLinks) {

                            if (!viewedLinks.contains(dealUrl)) {

                                Listing listing = parser.parseDeal(dealUrl);

                                if (listing != null) {
                                    Analyzer analyzer = new Analyzer();
                                    String verdict = analyzer.analyzeDeal(listing);

                                    sendMsg(adminChatId, "🔥 НОВАЯ КВАРТИРА!\n\n" + verdict + "\n\nСсылка: " + dealUrl);


                                    viewedLinks.add(dealUrl);
                                }
                            }
                        }
                    }
                }, 0, 15, TimeUnit.MINUTES);

                return;
            }

            if (userText.startsWith("http")) {
                sendMsg(chatId, "Принял ссылку! Пробиваю защиту сайта...");
            } else {
                sendMsg(chatId, "Жми /start для включения автопилота.");
            }
        }
    }

    private void sendMsg(long ChatId, String text){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(ChatId));
        message.setText(text);
        try {
            execute(message);
        } catch(TelegramApiException e) {
            System.out.println("[ERROR] Failed to send Telegram message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}