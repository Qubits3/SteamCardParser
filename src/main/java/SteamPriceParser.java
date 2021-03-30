import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;

public class SteamPriceParser {

    private ArrayList<String> links;
    private String fullSteamHTML;

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
            String discounts = doc.body().child(0).child(6).child(3).child(2).child(4).child(5).child(1).child(0).child(0).child(0).toString();

            System.out.println(discounts);

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

}
