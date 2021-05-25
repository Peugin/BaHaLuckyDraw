package tw.peugin.baha.luckydraw.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tw.peugin.baha.bahaForum.controller.api.ApiBahaForumController;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;
import tw.peugin.baha.luckydraw.editorsupport.RawWinnersResponseEditorSupport;
import tw.peugin.baha.luckydraw.entity.RawWinnersResponse;
import tw.peugin.baha.luckydraw.entity.Winner;
import tw.peugin.baha.luckydraw.entity.WinnerGroup;
import tw.peugin.baha.luckydraw.exception.ArgumentInvalidException;
import tw.peugin.baha.luckydraw.exception.InternalServerException;
import tw.peugin.baha.luckydraw.service.IWinnerGroupService;
import tw.peugin.baha.luckydraw.service.IWinnerService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/draw")
public class ApiLuckDrawController {
    @Autowired
    private ApiBahaForumController apiBahaForumController;
    @Autowired
    private IWinnerService winnerService;
    @Autowired
    private IWinnerGroupService winnerGroupService;
    @Value("${db.data.expireTime}")
    private Long expireTime;


    Random random = new Random();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(RawWinnersResponse.class, new RawWinnersResponseEditorSupport());
    }

    @GetMapping("/drawWinners")
    public List<Winner> drawWinners(@RequestParam(name="url") String url,
                                      @RequestParam(name="start_floor",required = false) Integer startFloor,
                                      @RequestParam(name="end_floor",required = false) Integer endFloor,
                                      @RequestParam(name="start_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date startDate,
                                      @RequestParam(name="end_date",required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm") Date endDate,
                                      @RequestParam(name="keyword",required = false) String keyword,
                                      @RequestParam(name="draw_nums") int drawNums,
                                      @RequestParam(name="black_list",required = false) List<String> blackList,
                                      @RequestParam(name="use_regex",required = false) boolean useRegex,
                                      @RequestParam(name="save_draw",required = false) boolean saveDraw) throws ArgumentInvalidException, IOException, URISyntaxException {

        if(saveDraw && drawNums > 99)
            throw new ArgumentInvalidException("儲存中獎名單時，中獎人數不得超過99人。");

        List<BahaCrawlerData> drawList = apiBahaForumController.getPosters(url, startFloor, endFloor, startDate, endDate);
        List<BahaCrawlerData> rawWinners = new LinkedList<>();
        List<Winner> winners = new LinkedList<>();

        if(keyword != null) {
            if (useRegex) {
                drawList = drawList.parallelStream()
                        .filter(data -> data.getArticle().matches(keyword)).collect(Collectors.toList());
            } else {
                drawList = drawList.parallelStream()
                        .filter(data -> data.getArticle().contains(keyword)).collect(Collectors.toList());
            }
        }

        if(blackList != null)
            drawList = drawList.parallelStream()
                    .filter(data -> !blackList.contains(data.getUserID())).collect(Collectors.toList());;

        while (rawWinners.size() < drawNums && !drawList.isEmpty()) {
            int luckyNumber = random.nextInt(drawList.size()-1);
            rawWinners.add(drawList.get(luckyNumber));
            drawList.remove(luckyNumber);
        }

        if(saveDraw)
            winners = saveToDB(rawWinners);
        else {
            List<Winner> finalWinners = winners;
            rawWinners.forEach(rawWinner -> finalWinners.add(new Winner(new WinnerGroup(),rawWinner.getFloor(),rawWinner.getUserName(),rawWinner.getUserID(),rawWinner.getPostDate(),rawWinner.getArticle(),rawWinner.getBsn(),rawWinner.getSnb())));
        }

        return winners;
    }

    @GetMapping("/getWinners")
    public List<Winner> getWinners(@RequestParam(name="group_id",required = true)  long groupID){
        return winnerService.getWinnersByID(groupID);
    }

    /*@GetMapping(path = "/saveToDB")
    public void saveToDB(@RequestParam("winners") RawWinnersResponse winnersResponse){
        List<Winner> winners = new LinkedList<>();
        WinnerGroup group = new WinnerGroup(new Date());
        winnerGroupService.save(group);
        winnersResponse.getRawWinners().forEach(rawWinner -> winners.add(new Winner(group,rawWinner.getFloor(),rawWinner.getUserName(),rawWinner.getUserID(),rawWinner.getPostDate(),rawWinner.getArticle(),rawWinner.getBsn(),rawWinner.getSnb())));
        winnerService.saveAll(winners);
    }*/

    private List<Winner> saveToDB(List<BahaCrawlerData> rawWinners){
        wipeExpiredRecord();
        WinnerGroup group = new WinnerGroup(new Date());
        winnerGroupService.save(group);
        List<Winner> winners = new LinkedList<>();
        rawWinners.forEach(rawWinner -> winners.add(new Winner(group,rawWinner.getFloor(),rawWinner.getUserName(),rawWinner.getUserID(),rawWinner.getPostDate(),rawWinner.getArticle(),rawWinner.getBsn(),rawWinner.getSnb())));
        winnerService.saveAll(winners);

        return winners;
    }

    private void wipeExpiredRecord(){
        WinnerGroup winnerGroup = winnerGroupService.getGroupByMinTimestamp();

        if(winnerGroup != null && winnerGroup.getTimestamp().getTime() + expireTime < System.currentTimeMillis()){
            long gid = winnerGroup.getId();
            winnerService.wipeWinnersByGID(gid);
            winnerGroupService.wipeWinnerGroupByGID(gid);
        }
    }
}
