import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;

public class Parser {

    final static String URL = "https://steam.tools/cards/#";

    static String fullURL;

    public static void main(String[] args) throws IOException {
        headlessChrome();   // Get the full HTML

//        getMain();
        selectTable();


    }

    private static void headlessChrome() {
        System.setProperty("webdriver.chrome.driver", "D:\\ReferencedLibraries\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1400,800");

        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);

        fullURL = driver.getPageSource();
    }

    private static void getMain() throws IOException {
        Document doc = Jsoup.connect("https://steam.tools/cards/#").get();

        System.out.println(doc);

        Elements elements = doc.select("tbody");

        for (Element e : elements) {
            System.out.println(e);
        }
    }

    private static void selectTable() throws IOException {
//        Document doc = Jsoup.connect("https://steam.tools/cards/#").maxBodySize(0).timeout(0).followRedirects(true).get();

        Document doc = Jsoup.parse(fullURL);
        Elements table = doc.select("#set_table");

        System.out.println(table);

        Elements rows = table.get(0).select("tr");

        for (Element row : rows) {
            System.out.println(row.select("td").get(0).text());
        }
    }

}
