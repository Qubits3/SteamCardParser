import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

public class SteamPriceParser {

    private ArrayList<String> links;
    private String fullSteamHTML;

    private final String COMMENT = "<!--[if lte IE 7]>\n" +
            "<style type=\"text/css\">\n" +
            ".game_area_purchase_game_dropdown_right_panel .btn_addtocart { float: none; }\n" +
            "</style>\n" +
            "<![endif]-->";
    private final String BTN_ADD_TO_CART = "<div class=\"btn_addtocart\">";
    private final String DISCOUNT_PCT = "<div class=\"discount_pct\">";
    private final String DISCOUNT_ORIGINAL_PRICE = "<div class=\"discount_original_price\">";
    private final String DISCOUNT_FINAL_PRICE = "<div class=\"discount_final_price\">";

    public SteamPriceParser(ArrayList<String> links){
        this.links = links;

        headlessChrome();
    }

    private void headlessChrome(){
        System.setProperty("webdriver.chrome.driver", "D:\\ReferencedLibraries\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1400,800");

        WebDriver driver = new ChromeDriver(options);

//        for (String link: links){
            driver.get("https://store.steampowered.com/app/754120/Ninja_Stealth_3/");
//            driver.get(link);

            fullSteamHTML = driver.getPageSource();

            Document doc = Jsoup.parse(fullSteamHTML);
            String purchaseArea = doc.body().select("#game_area_purchase").toString();

            // ToDo: Buraları jsoup parser a atıp öyle ayır
            String cleanPurchaseArea = purchaseArea.substring(purchaseArea.indexOf(COMMENT), purchaseArea.indexOf(BTN_ADD_TO_CART));
            String gameName = cleanPurchaseArea.substring(cleanPurchaseArea.indexOf("<h1>") + 8, cleanPurchaseArea.indexOf("</h1>"));
            String discount = cleanPurchaseArea.substring(cleanPurchaseArea.indexOf(DISCOUNT_PCT) + 34, cleanPurchaseArea.indexOf("<div class=\"discount_prices\">") - 20);
            String discountFinalPrice = cleanPurchaseArea.substring(cleanPurchaseArea.indexOf(DISCOUNT_FINAL_PRICE) + 43, cleanPurchaseArea.length() - 46);

            System.out.println(cleanPurchaseArea);
            System.out.println(gameName);
            System.out.println(discount);
            System.out.println(discountFinalPrice);

//            if (discounts.equals("")){
//                System.out.println(link + " indirim falan yok kardeş");
//            }else{
//                String discount = discounts.substring(0, discounts.indexOf(" "));   // Gets the first discount
//                System.out.println(link + " " + discount);
//            }

//        }

        driver.close();
        driver.quit();


    }

    private static void printArray(List<String> arrayList) {
        for (String x : arrayList) {
            System.out.println(x.toString());
        }
    }
}
