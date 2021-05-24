package tw.peugin.baha.luckydraw.repositroy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.peugin.baha.luckydraw.entity.Winner;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface WinnerRepository extends CrudRepository<Winner,Long> {
    @Query(value = "select * from Winners where group_id_fk = ?1",nativeQuery = true)
    List<Winner> getWinnersByID(long groupID);
    @Transactional
    @Modifying
    @Query(value = "delete from Winners where group_id_fk = ?1",nativeQuery = true)
    void wipeWinnersByGID(long groupID);
}
