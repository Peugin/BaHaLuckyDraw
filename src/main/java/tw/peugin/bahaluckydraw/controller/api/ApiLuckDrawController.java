package tw.peugin.bahaluckydraw.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tw.peugin.bahaluckydraw.BahaFormCrawler;
import tw.peugin.bahaluckydraw.entity.BahaCrawlerData;
import tw.peugin.bahaluckydraw.exception.ArgumentInvalidException;
import tw.peugin.bahaluckydraw.exception.InternalServerException;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/draw")
public class ApiLuckDrawController {
    private final Pattern bahaC = Pattern.compile("https://forum\\.gamer\\.com\\.tw/C\\.php\\?.+");

    @Autowired
    private BahaFormCrawler bahaFormCrawler;

    Random random = new Random();

    @GetMapping("")
    public List<BahaCrawlerData> draw(@RequestParam(name="url") String url,
                       @RequestParam(name="start_floor",required = false,defaultValue = "0") int startFloor,
                       @RequestParam(name="end_floor",required = false) Integer endFloor,
                       @RequestParam(name="start_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startDate,
                       @RequestParam(name="end_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endDate,
                       @RequestParam(name="keyword") String keyword,
                       @RequestParam(name="draw_nums") int drawNums,
                       @RequestParam(name="use_regex",required = false) boolean useRegex) throws ArgumentInvalidException, InternalServerException {

        if(!bahaC.matcher(url).find())
            throw new ArgumentInvalidException("輸入的網址不正確");
        if(endFloor != null && (startFloor < 0 || endFloor < startFloor))
            throw new ArgumentInvalidException("輸入的樓層不正確");
        if((startDate != null && endDate != null) && endDate.getTime() < startDate.getTime())
            throw new ArgumentInvalidException("輸入的時間不正確");
        if(keyword.isEmpty())
            throw new ArgumentInvalidException("輸入的關鍵字不正確");
        if(drawNums < 0)
            throw new ArgumentInvalidException("輸入的抽獎人數不正確");
        List<BahaCrawlerData> bahaCrawlerDataList = null;

        if(endFloor == null)
            endFloor = Integer.MAX_VALUE;
        if(startDate == null)
            startDate = new Date(Long.MIN_VALUE);
        if(endDate == null)
            endDate = new Date(Long.MAX_VALUE);

        try {
            bahaCrawlerDataList = bahaFormCrawler.scrapying(url,startFloor,endFloor,startDate,endDate);
        } catch (Exception e) {
            throw new InternalServerException(e,"內部網站錯誤");
        }




        if(bahaCrawlerDataList != null){
            if(useRegex){
                bahaCrawlerDataList = bahaCrawlerDataList.parallelStream()
                        .filter(data -> data.getArticle().matches(keyword)).collect(Collectors.toList());
            }else{
                bahaCrawlerDataList = bahaCrawlerDataList.parallelStream()
                        .filter(data -> data.getArticle().contains(keyword)).collect(Collectors.toList());
            }
        }

        List<BahaCrawlerData> winners = null;
        if(bahaCrawlerDataList != null) {
            winners = new LinkedList<>();
            while (winners.size() < drawNums && !bahaCrawlerDataList.isEmpty()) {
                int luckyNumber = random.nextInt(bahaCrawlerDataList.size());
                winners.add(bahaCrawlerDataList.get(luckyNumber));
                bahaCrawlerDataList.remove(luckyNumber);
            }
        }


        return winners;
    }
}
