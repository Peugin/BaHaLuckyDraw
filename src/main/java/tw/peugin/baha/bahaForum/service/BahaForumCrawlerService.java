package tw.peugin.baha.bahaForum.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;
import tw.peugin.baha.luckydraw.exception.BadRequestException;
import tw.peugin.baha.luckydraw.exception.InternalServerException;
import tw.peugin.baha.luckydraw.model.DrawWinnersQueryParameter;
import tw.peugin.baha.utility.HtmlHelper;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class BahaForumCrawlerService {
    private final Pattern bahaCPagePattern = Pattern.compile("https://(forum\\.gamer\\.com\\.tw|m\\.gamer\\.com\\.tw/forum)/C\\.php\\?.+");
    @Autowired
    private BahaPCForumCrawler bahaPCForumCrawler;
    @Autowired
    private BahaMobileForumCrawler bahaMobileFormCrawler;

    public List<BahaCrawlerData> getPosters (DrawWinnersQueryParameter parameter) throws IOException, URISyntaxException {
        if(!bahaCPagePattern.matcher(parameter.getUrl()).find())
            throw new BadRequestException("輸入的網址不正確");
        if(parameter.getEndFloor() < parameter.getStartFloor())
            throw new BadRequestException("輸入的樓層不正確");
        if(parameter.getEndDate().getTime() < parameter.getStartDate().getTime())
            throw new BadRequestException("輸入的時間不正確");

        String url;

        switch (HtmlHelper.testHttpStatus()){
            case PC:
                System.out.println("使用PC版爬蟲");
                url = parameter.getUrl().replace("https://m.gamer.com.tw/forum/","https://forum.gamer.com.tw/");
                return bahaPCForumCrawler.scraping(url,parameter.getStartFloor(),parameter.getEndFloor(),parameter.getStartDate(),parameter.getEndDate());
            case Mobile:
                System.out.println("使用Mobile版爬蟲");
                url = parameter.getUrl().replace("https://forum.gamer.com.tw/","https://m.gamer.com.tw/forum/");
                return bahaMobileFormCrawler.scraping(url,parameter.getStartFloor(),parameter.getEndFloor(),parameter.getStartDate(),parameter.getEndDate());
            default:
                System.out.println("無法爬蟲");
                throw new InternalServerException("無法連線至巴哈姆特，請稍後再試。");
        }
    }
}
