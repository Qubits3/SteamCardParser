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
            driver.get("https://store.steampowered.com/app/339190/Dead_Synchronicity_Tomorrow_Comes_Today/");

            fullSteamHTML = driver.getPageSource();

            Document doc = Jsoup.parse(fullSteamHTML);
            String discount = doc.select(".discount_pct").text();
            System.out.println(discount);
//        }

        driver.close();
    }

}
