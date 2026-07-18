public class Main {
    public static void main(String[] args) {
        Listing testApartment = new Listing("ID-001", 25000000.0, 50.0, "krisha.kz/test");
        Analyzer bot = new Analyzer();
        bot.analyzeDeal(testApartment);
    }
}
