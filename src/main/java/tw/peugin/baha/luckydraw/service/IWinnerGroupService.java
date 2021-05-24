package tw.peugin.baha.luckydraw.service;

import tw.peugin.baha.luckydraw.entity.WinnerGroup;

public interface IWinnerGroupService {
    void save(WinnerGroup winnerGroup);
    WinnerGroup getGroupByMinTimestamp();
    void wipeWinnerGroupByGID(long groupID);
}
