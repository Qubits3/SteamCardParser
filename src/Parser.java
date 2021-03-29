import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Parser {
    public static void main(String[] args) throws IOException {
//        getMain();
        selectTable();
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
        Document doc = Jsoup.connect("https://steam.tools/cards/#").maxBodySize(0).timeout(0).followRedirects(true).get();

        Elements table = doc.select("table[class=table table-striped table-condensed table-hover dataTable no-footer]");

        Elements rows = table.get(0).select("tr");

        for (Element row : rows) {
            System.out.println(row.select("td").get(0).text());
        }

//        System.out.println(row);
    }
}
