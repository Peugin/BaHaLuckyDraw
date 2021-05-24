package tw.peugin.baha.luckydraw.entity;

import tw.peugin.baha.bahaForum.entity.BahaCrawlerData;

import java.util.ArrayList;
import java.util.List;

public class RawWinnersResponse {
    private List<BahaCrawlerData> rawWinners;

    public List<BahaCrawlerData> getRawWinners() {
        return rawWinners;
    }
    public void setRawWinners(List<BahaCrawlerData> rawWinners) {
        this.rawWinners = rawWinners;
    }
}
