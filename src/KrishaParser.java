import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KrishaParser implements MarketParser {

    @Override
    public Listing parseDeal(String url) {
        try {
            System.out.println("[INFO] Fetching target URL: " + url);

            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                    .referrer("https://www.google.com")
                    .header("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .timeout(10000)
                    .get();

            Element priceElement = doc.selectFirst(".offer__price");
            double finalPrice = 0;

            if (priceElement != null) {
                String rawPriceText = priceElement.text();
                System.out.println("[DEBUG] Raw price extracted: " + rawPriceText);

                String cleanPriceText = rawPriceText.replaceAll("[^0-9]", "");
                finalPrice = Double.parseDouble(cleanPriceText);
                System.out.println("[DEBUG] Parsed price value: " + finalPrice);
            } else {
                System.out.println("[ERROR] Failed to locate price element.");
            }

            Elements shortInfos = doc.select(".offer__advert-short-info");
            double finalArea = 0;

            for (Element info : shortInfos) {
                String text = info.text();
                if (text.contains("м²")) {
                    System.out.println("[DEBUG] Raw area extracted: " + text);

                    String cleanAreaText = text.split(",")[0].replaceAll("[^0-9.]", "");
                    finalArea = Double.parseDouble(cleanAreaText);
                    System.out.println("[DEBUG] Parsed area value: " + finalArea);
                    break;
                }
            }

            Listing FinalList = new Listing("1001", finalPrice, finalArea, url);
            return FinalList;

        } catch (IOException e) {
            System.out.println("[ERROR] Connection refused or network timeout.");
            e.printStackTrace();
            return null;
        }
    }

    public List<String> parseSearchPage(String searchUrl) {
        List<String> links = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .get();

            Elements cards = doc.select(".a-card__title");
            System.out.println("[INFO] Cards found on search page: " + cards.size());

            for (Element card : cards) {
                String href = card.attr("href");
                if (!href.startsWith("http")) {
                    href = "https://krisha.kz" + href;
                }
                links.add(href);

                if (links.size() >= 5) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Search page parsing failed: " + e.getMessage());
        }
        return links;
    }
}