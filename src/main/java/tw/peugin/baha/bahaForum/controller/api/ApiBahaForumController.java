package tw.peugin.baha.bahaForum.controller.api;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tw.peugin.baha.bahaForum.BahaMobileForumCrawler;
import tw.peugin.baha.bahaForum.BahaPCForumCrawler;
import tw.peugin.baha.bahaForum.IForumCrawler;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;
import tw.peugin.baha.luckydraw.exception.ArgumentInvalidException;
import tw.peugin.baha.utility.HtmlHelper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ApiBahaForumController {
    private final Pattern bahaCPagePattern = Pattern.compile("https://(forum\\.gamer\\.com\\.tw|m\\.gamer\\.com\\.tw/forum)/C\\.php\\?.+");
    @Autowired
    private BahaPCForumCrawler bahaPCForumCrawler;
    @Autowired
    private BahaMobileForumCrawler bahaMobileFormCrawler;

    @RequestMapping(value = "/getPosters")
    public List<BahaCrawlerData> getPosters (@RequestParam(name="url") String url,
                                             @RequestParam(name="start_floor",required = false) Integer startFloor,
                                             @RequestParam(name="end_floor",required = false) Integer endFloor,
                                             @RequestParam(name="start_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startDate,
                                             @RequestParam(name="end_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endDate) throws ArgumentInvalidException, IOException, URISyntaxException {
        if(!bahaCPagePattern.matcher(url).find())
            throw new ArgumentInvalidException("輸入的網址不正確");
        if(endFloor != null && endFloor < 0 || (startFloor != null &&  endFloor != null && (endFloor > 0 && endFloor < startFloor)))
            throw new ArgumentInvalidException("輸入的樓層不正確");
        if(startDate != null && endDate != null && endDate.getTime() < startDate.getTime())
            throw new ArgumentInvalidException("輸入的時間不正確");

        if(startFloor == null) startFloor = 0;
        if(endFloor == null) endFloor = Integer.MAX_VALUE;
        if(startDate == null) startDate = new Date(Long.MIN_VALUE);
        if(endDate == null) endDate = new Date(Long.MAX_VALUE);


        switch (HtmlHelper.testHttpStatus()){
            case PC:
                System.out.println("使用PC版爬蟲");
                url = url.replace("https://m.gamer.com.tw/forum/","https://forum.gamer.com.tw/");
                return bahaPCForumCrawler.scraping(url,startFloor,endFloor,startDate,endDate);
            case Mobile:
                System.out.println("使用Mobile版爬蟲");
                url = url.replace("https://forum.gamer.com.tw/","https://m.gamer.com.tw/forum/");
                return bahaMobileFormCrawler.scraping(url,startFloor,endFloor,startDate,endDate);
            default:
                System.out.println("無法爬蟲");
                throw new InternalException("無法連線至巴哈姆特，請稍後再試。");
        }
    }
}
