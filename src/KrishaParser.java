public class KrishaParser implements MarketParser {
    @Override
    public Listing parseDeal(String url){
        double cost = 25000000.0;
        double area = 50.0;

        Listing lootBag = new Listing("ID-1", cost, area, url);

        return lootBag;
    }

}
