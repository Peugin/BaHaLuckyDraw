package tw.peugin.baha.luckydraw.repositroy;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tw.peugin.baha.luckydraw.entity.WinnerGroup;

import javax.transaction.Transactional;

@Repository
public interface WinnerGroupRepository extends CrudRepository<WinnerGroup,Long> {
    @Query(value = "select * from winner_groups where timestamp = (select min(timestamp) from winner_groups) limit 1;",nativeQuery = true)
    WinnerGroup getGroupByMinTimestamp();
    @Transactional
    @Modifying
    @Query(value = "delete from winner_groups where id = ?1",nativeQuery = true)
    void wipeWinnerGroupByGID(long groupID);
}
