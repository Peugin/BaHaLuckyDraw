package tw.peugin.baha.bahaForum.service;

import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

public abstract class IForumCrawler {
    protected final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    protected final static String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected final static String BOARD_ID_PARAM = "bsn";
    protected final static String ARTICLE_ID_PARAM = "sna";

    abstract List<BahaCrawlerData> scraping(String url, int startFloor, int endFloor, Date startDate, Date endDate) throws IOException, URISyntaxException;
}
