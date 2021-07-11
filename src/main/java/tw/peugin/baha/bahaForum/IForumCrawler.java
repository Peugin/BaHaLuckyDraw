package tw.peugin.baha.bahaForum;

import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

public interface IForumCrawler {
    List<BahaCrawlerData> scraping(String url, int startFloor, int endFloor, Date startDate, Date endDate) throws IOException, URISyntaxException;
}
