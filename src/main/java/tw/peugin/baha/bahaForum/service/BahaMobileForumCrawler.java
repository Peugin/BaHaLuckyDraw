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

//手機版爬蟲用
@Service
public class BahaMobileForumCrawler extends IForumCrawler {
    private final static String BASE_URL = "https://m.gamer.com.tw/forum/C.php?bsn=%s&snA=%s";

    @Value("${crawler.mobile.floorPerPage}")
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
                        //Remove AD
                        doc.select("#div-gpt-ad-1519981043032-0").remove();
                        //Remove CC
                        doc.select(".c-post__header__info-cc").remove();
                        Elements postElements = doc.select(".cbox");
                        for(Element postElement : postElements) {
                            //本文若被刪除，不繼續處理
                            if(!postElement.select(".float-right").hasText()){
                                continue;
                            }

                            String userID = postElement.select(".cbox_man span").get(0).text().replaceAll("\\(.*\\)","");
                            String userName = postElement.select(".cbox_man span").get(0).text().replaceAll(".* ","").replace("(","").replace(")","");
                            int floor = Integer.parseInt(postElement.select(".cbox_man span").get(1).text().replace("#",""));
                            Date date = sdFormat.parse(postElement.select(".cbox_man span").get(2).text());
                            int bsn = Integer.parseInt(postElement.select(".float-right > .morebtns > .btn_re").attr("href").replaceAll(".*bsn=", "").replaceAll("&snA=.*", ""));
                            int snb = Integer.parseInt(postElement.select(".float-right > .morebtns > .btn_re").attr("href").replaceAll(".*&snA=\\d+&sn=", "").replaceAll("&type=\\d+", ""));
                            String article = postElement.select(".cbox_txt").text();

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
        int lastPage = Integer.parseInt(doc.select(".halac_form option").last().attr("value"));
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
