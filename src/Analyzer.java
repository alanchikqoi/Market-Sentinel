public class Analyzer {
    public String analyzeDeal(Listing target) {
        double pricePerSquare = target.getCost() / target.getArea();

        String str = "Цена: " + (long) target.getCost() + " ₸\n" +
                "Площадь: " + target.getArea() + " м²\n" +
                "Цена за квадрат: " + (long) pricePerSquare + " ₸/м²";

        if (pricePerSquare < 480000.0) {
            str += "\n\nВЕРДИКТ: Ниже рынка! 🔥 БРАТЬ.";
        } else {
            str += "\n\nВЕРДИКТ: Оверпрайс. ❌ ПРОПУСК.";
        }

        return str;
    }
}