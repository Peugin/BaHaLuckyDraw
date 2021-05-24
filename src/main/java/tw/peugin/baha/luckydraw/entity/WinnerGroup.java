package tw.peugin.baha.luckydraw.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "WinnerGroups")
public class WinnerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private long id;
    @Column(name="timestamp",nullable = false)
    private Date timestamp;

    public WinnerGroup() {
    }

    public WinnerGroup(Date timestamp) {
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
