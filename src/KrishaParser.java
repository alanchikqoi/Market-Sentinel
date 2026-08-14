import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
public class KrishaParser implements MarketParser {

    @Override
    public Listing parseDeal(String url) {
        try {
            System.out.println("Пытаемся пробить защиту сайта: " + url);

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
                System.out.println("Грязная цена с сайта: " + rawPriceText);

                String cleanPriceText = rawPriceText.replaceAll("[^0-9]", "");
                finalPrice = Double.parseDouble(cleanPriceText);
                System.out.println("Готово к математике (Цена): " + finalPrice);
            } else {
                System.out.println("Баг: Не смогли найти тег с ценой.");
            }

            org.jsoup.select.Elements shortInfos = doc.select(".offer__advert-short-info");
            double finalArea = 0;

            for (Element info : shortInfos) {
                String text = info.text();
                if (text.contains("м²")) {
                    System.out.println("Грязная квадратура с сайта: " + text);

                    String cleanAreaText = text.replaceAll("[^0-9.,]", "").replace(",", ".");
                    finalArea = Double.parseDouble(cleanAreaText);
                    System.out.println("Готово к математике (Площадь): " + finalArea);
                    break;
                }
            }

            Listing FinalList = new Listing("1001", finalPrice, finalArea, url);
            return FinalList;

        } catch (IOException e) {
            System.out.println("Сайт отклонил запрос или нет интернета.");
            e.printStackTrace();
            return null;
        }
    }
}