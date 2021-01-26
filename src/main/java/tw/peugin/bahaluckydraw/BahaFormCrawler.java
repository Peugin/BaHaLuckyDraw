package tw.peugin.bahaluckydraw;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import tw.peugin.bahaluckydraw.entity.BahaCrawlerData;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class BahaFormCrawler {
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    private final static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final int floorPerPage = 20;

    public List<BahaCrawlerData> scrapying(String url,int startFloor,int endFloor,Date startDate,Date endDate) throws IOException, URISyntaxException {
        List<BahaCrawlerData> bahaCrawlerDataList = new LinkedList<>();
        String baseUrl = reBaseUrl(url);
        int lastPage = getLastPage(url);
        int startPage = startFloor/floorPerPage + 1;
        IntStream.range(startPage,lastPage+1).parallel().forEach(
                page ->{
                    SimpleDateFormat sdFormat = new SimpleDateFormat(DATA_FORMAT);
                    String currentUrl = baseUrl + "&page=" + page;
                    Document doc = null;
                    try {
                        doc = Jsoup.connect(currentUrl).userAgent(USER_AGENT).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert doc != null;
                    Elements postElements = doc.select("#BH-master > section[id^=post_]");
                    for(Element postElement : postElements) {
                        int floor = Integer.parseInt(postElement.select(".floor").attr("data-floor"));
                        String userName = postElement.select(".username").text();
                        String userID = postElement.select(".userid").text();
                        int bsn = Integer.parseInt(postElement.select(".reply-input textarea").attr("data-bsn"));
                        int snb = Integer.parseInt(postElement.select(".reply-input textarea").attr("data-snb"));

                        Date date = null;
                        try {
                            String html_date = postElement.select(".edittime").attr("data-mtime");
                            date = sdFormat.parse(html_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String article = postElement.select(".c-article__content").text();

                        if(floor < startFloor)
                            continue;
                        if(floor > endFloor)
                            break;
                        assert date != null;
                        if(date.getTime() < startDate.getTime())
                            continue;
                        if(date.getTime() > endDate.getTime())
                            break;
                        bahaCrawlerDataList.add(new BahaCrawlerData(floor, userName, userID, date, article,bsn,snb));
                    }
                }
        );

        return bahaCrawlerDataList;
    }

    private int getLastPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).get();
        int lastPage = Integer.parseInt(doc.select(".c-section__main .BH-pagebtnA a").last().text());
        return lastPage;
    }

    String reBaseUrl(String url) throws URISyntaxException {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url.toLowerCase()), StandardCharsets.UTF_8);
        String bsn = null,snA = null;
        for (NameValuePair param : params) {
            switch(param.getName()){
                case "bsn":
                    bsn = param.getValue();
                    break;
                case "sna":
                    snA = param.getValue();
                    break;
            }
        }

        return String.format("https://forum.gamer.com.tw/C.php?bsn=%s&snA=%s",bsn,snA);
    }
}
