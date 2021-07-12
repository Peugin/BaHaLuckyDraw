package tw.peugin.baha.bahaForum.service;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;

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

//電腦版爬蟲用
@Service
public class BahaPCForumCrawler extends IForumCrawler {
    private final static String BASE_URL = "https://forum.gamer.com.tw/C.php?bsn=%s&snA=%s";

    @Value("${crawler.pc.floorPerPage}")
    private int floorPerPage;

    public List<BahaCrawlerData> scraping(String url, int startFloor, int endFloor, Date startDate, Date endDate) throws IOException, URISyntaxException {
        List<BahaCrawlerData> bahaCrawlerDataList = new LinkedList<>();
        String baseUrl = reBaseUrl(url);
        int lastPage = getLastPage(url);
        int startPage = startFloor/floorPerPage+1;

        if(lastPage > endFloor/floorPerPage+1){
            lastPage = endFloor/floorPerPage+1;
        }

        IntStream.range(startPage,lastPage+1).parallel().forEach(
                page ->{
                    try {
                        SimpleDateFormat sdFormat = new SimpleDateFormat(DATA_FORMAT);
                        String currentUrl = baseUrl + "&page=" + page;
                        Document doc = Jsoup.connect(currentUrl).userAgent(USER_AGENT).get();
                        Elements postElements = doc.select("#BH-master > section[id^=post_]");
                        for(Element postElement : postElements) {
                            int floor = Integer.parseInt(postElement.select(".floor").attr("data-floor"));
                            String userName = postElement.select(".username").text();
                            String userID = postElement.select(".userid").text();
                            int bsn = Integer.parseInt(postElement.select(".reply-input textarea").attr("data-bsn"));
                            int snb = Integer.parseInt(postElement.select(".reply-input textarea").attr("data-snb"));
                            Date date = sdFormat.parse(postElement.select(".edittime").attr("data-mtime"));
                            String article = postElement.select(".c-article__content").text();

                            if(floor < startFloor)
                                continue;
                            if(floor > endFloor)
                                break;
                            if(date.getTime() < startDate.getTime())
                                continue;
                            if(date.getTime() > endDate.getTime())
                                break;
                            bahaCrawlerDataList.add(new BahaCrawlerData(floor, userName, userID, date, article,bsn,snb));
                        }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
        );

        return bahaCrawlerDataList;
    }

    private int getLastPage(String url) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).ignoreContentType(true).get();
        int lastPage = Integer.parseInt(doc.select(".c-section__main .BH-pagebtnA a").last().text());
        return lastPage;
    }

    String reBaseUrl(String url) throws URISyntaxException {
        List<NameValuePair> params = URLEncodedUtils.parse(new URI(url.toLowerCase()), StandardCharsets.UTF_8);
        String bsn = null,snA = null;
        for (NameValuePair param : params) {
            switch(param.getName()){
                case BOARD_ID_PARAM:
                    bsn = param.getValue();
                    break;
                case ARTICLE_ID_PARAM:
                    snA = param.getValue();
                    break;
            }
        }

        return String.format(BASE_URL,bsn,snA);
    }
}
