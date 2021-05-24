package tw.peugin.baha.luckydraw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.peugin.baha.luckydraw.entity.WinnerGroup;
import tw.peugin.baha.luckydraw.repositroy.WinnerGroupRepository;

@Service
public class WinnerGroupService implements IWinnerGroupService{
    @Autowired
    private WinnerGroupRepository winnerGroupRepository;

    @Override
    public void save(WinnerGroup winnerGroup) {
        winnerGroupRepository.save(winnerGroup);
    }

    @Override
    public WinnerGroup getGroupByMinTimestamp() {
        return winnerGroupRepository.getGroupByMinTimestamp();
    }

    @Override
    public void wipeWinnerGroupByGID(long groupID) {
        winnerGroupRepository.wipeWinnerGroupByGID(groupID);
    }
}
