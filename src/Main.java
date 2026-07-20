public class Main {
    public static void main(String[] args) {

        MarketParser mercenary = new KrishaParser();
        Listing finalResult = mercenary.parseDeal("krisha.kz/kvartira");

        Analyzer bot = new Analyzer();
        bot.analyzeDeal(finalResult);
    }
}
