package tw.peugin.baha.luckydraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;
import tw.peugin.baha.bahaForum.service.BahaForumCrawlerService;
import tw.peugin.baha.luckydraw.entity.Winner;
import tw.peugin.baha.luckydraw.entity.WinnerGroup;
import tw.peugin.baha.luckydraw.exception.BadRequestException;
import tw.peugin.baha.luckydraw.model.DrawWinnersQueryParameter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrawService {

    @Value("${draw.maxDrawNums}")
    private int maxDrawNums;
    @Value("${db.data.expireTime}")
    private Long expireTime;

    private Random random = new Random();

    @Autowired
    private BahaForumCrawlerService bahaForumCrawlerService;
    @Autowired
    private WinnerGroupService winnerGroupService;
    @Autowired
    private WinnerService winnerService;

    public List<Winner> drawWinners(DrawWinnersQueryParameter parameter) throws IOException, URISyntaxException,BadRequestException {
        if(parameter.isSaveDraw() && parameter.getDrawNums() > maxDrawNums)
            throw new BadRequestException(String.format("儲存中獎名單時，中獎人數不得超過%d人。",maxDrawNums));

        List<BahaCrawlerData> drawList = bahaForumCrawlerService.getPosters(parameter);
        List<BahaCrawlerData> rawWinners = new LinkedList<>();
        List<Winner> winners = new LinkedList<>();

        if (parameter.isUseRegex()) {
            drawList = drawList.parallelStream()
                    .filter(data -> data.getArticle().matches(parameter.getKeyword())).collect(Collectors.toList());
        } else {
            drawList = drawList.parallelStream()
                    .filter(data -> data.getArticle().contains(parameter.getKeyword())).collect(Collectors.toList());
        }

        if(parameter.isDuplicateSaveDraw()){
            Set<String> idAlreadySeen = new HashSet<>();
            drawList.removeIf(drawer -> !idAlreadySeen.add(drawer.getUserID()));
        }

        if(parameter.getBlackList() != null)
            drawList = drawList.parallelStream()
                    .filter(data -> !parameter.getBlackList().contains(data.getUserID())).collect(Collectors.toList());;

        while (rawWinners.size() < parameter.getDrawNums() && !drawList.isEmpty()) {
            int luckyNumber = random.nextInt(drawList.size());
            rawWinners.add(drawList.get(luckyNumber));
            drawList.remove(luckyNumber);
        }

        if(parameter.isSaveDraw())
            winners = saveToDB(rawWinners);
        else {
            List<Winner> finalWinners = winners;
            rawWinners.forEach(rawWinner -> finalWinners.add(new Winner(new WinnerGroup(),rawWinner.getFloor(),rawWinner.getUserName(),rawWinner.getUserID(),rawWinner.getPostDate(),rawWinner.getArticle(),rawWinner.getBsn(),rawWinner.getSnb())));
        }

        return winners;
    }

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
