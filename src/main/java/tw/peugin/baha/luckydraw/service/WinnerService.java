package tw.peugin.baha.luckydraw.service;

import org.postgresql.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.peugin.baha.luckydraw.entity.Winner;
import tw.peugin.baha.luckydraw.repositroy.WinnerRepository;

import java.nio.ByteBuffer;
import java.util.List;

@Service
public class WinnerService implements IWinnerService {

    @Autowired
    private WinnerRepository winnerRepository;

    @Override
    public <S extends Winner> void saveAll(Iterable<S> entities) {
        winnerRepository.saveAll(entities);
    }

    @Override
    public List<Winner> getWinnersByID(long groupID) {
        return winnerRepository.getWinnersByID(groupID);
    }

    @Override
    public void wipeWinnersByGID(long groupID) {
        winnerRepository.wipeWinnersByGID(groupID);
    }
}
