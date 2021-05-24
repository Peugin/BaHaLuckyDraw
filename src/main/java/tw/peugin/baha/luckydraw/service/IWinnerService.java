package tw.peugin.baha.luckydraw.service;

import org.springframework.data.jpa.repository.Query;
import tw.peugin.baha.luckydraw.entity.Winner;

import java.util.List;

public interface IWinnerService {
    <S extends Winner> void saveAll(Iterable<S> entities);
    List<Winner> getWinnersByID(long groupID);
    void wipeWinnersByGID(long groupID);
}
