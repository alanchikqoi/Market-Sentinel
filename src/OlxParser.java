public class OlxParser implements MarketParser{

    @Override
    public Listing parseDeal(String url) {
        double cost = 18000000.0;
        double area = 40.0;
        return new Listing("OLX-999", cost, area, url);
    }
}
