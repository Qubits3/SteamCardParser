import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;

public class Parser {

    final static String URL = "https://steam.tools/cards/#";

    static String fullHTML;

    private static ArrayList<String> cardLinks = new ArrayList<>();
    private static ArrayList<String> steamLinks = new ArrayList<>();

    private static SteamPriceParser steamPriceParser;

    public static void main(String[] args) throws IOException {
        headlessChrome();

    }

    private static void headlessChrome() throws IOException {
        System.setProperty("webdriver.chrome.driver", "D:\\ReferencedLibraries\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("disable-blink-features=AutomationControlled");
        options.addArguments("--window-size=1400,800");

        WebDriver driver = new ChromeDriver(options);
        driver.get(URL);

//        WebElement menu = driver.findElement(By.className("dropdown-menu"));
//        WebElement link = menu.findElement(By.cssSelector("a"));
//        System.out.println(link);

//        List<WebElement> button = driver.findElements(By.xpath("//*[contains(text(), 'TRY')]"));
//
//        JavascriptExecutor executor = (JavascriptExecutor) driver;
//        executor.executeScript("setCurrency(0)", button.get(0));

//        WebElement button = driver.findElement(By.linkText("TRY"));

//        System.out.println(button);

//        webElement.click();

//        WebElement element = driver.findElement(By.xpath("//section[@class='dropdown-menu']"));
//        System.out.println(element);

        fullHTML = driver.getPageSource();

//        System.out.println(fullURL);

        selectTable();

        driver.close();
    }

    private static void selectTable() {
        Document doc = Jsoup.parse(fullHTML);
        Elements table = doc.select("#set_table");

//        System.out.println(table);

        String[] tempLinks = table.select("a[href]")
                .toString()
                .split("</a>");

        for (String link : tempLinks) {
            if (link.contains("[M]")) {  // card links
                cardLinks.add(link.substring(link.indexOf("\"") + 1, link.indexOf(" target=\"_blank\">[M]") - 1));
            }

            if (link.contains("[S]")) {
                steamLinks.add(link.substring(link.indexOf("\"") + 1, link.indexOf(" target=\"_blank\">[S]") - 1));
            }
        }

        steamPriceParser = new SteamPriceParser(steamLinks);

    }

    private static void printArray(ArrayList<String> arrayList) {
        for (String x : arrayList) {
            System.out.println(x);
        }
    }

}
