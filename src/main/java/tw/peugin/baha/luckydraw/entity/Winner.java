package tw.peugin.baha.luckydraw.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.text.DateFormat;
import java.util.Date;

@Entity
@Table(name = "Winners")
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private long id;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id_fk",referencedColumnName = "id")
    private WinnerGroup winnerGroup;
    @Column(name="floor",nullable = false)
    private int floor;
    @Column(name="user_name",nullable = false)
    private String userName;
    @Column(name="user_id",nullable = false)
    private String userID;
    @Column(name="post_date",nullable = false)
    private Date postDate;
    @Column(name="article",nullable = false,length = 5000)
    @Nationalized
    private String article;
    @Column(name="bsn",nullable = false)
    private int bsn;
    @Column(name="snb",nullable = false)
    private int snb;

    public Winner() {
    }

    public Winner(WinnerGroup winnerGroup, int floor, String userName, String userID, Date postDate, String article, int bsn, int snb) {
        this.winnerGroup = winnerGroup;
        this.floor = floor;
        this.userName = userName;
        this.userID = userID;
        this.postDate = postDate;
        this.article = article;
        this.bsn = bsn;
        this.snb = snb;
    }

    public WinnerGroup getWinnerGroup() {
        return winnerGroup;
    }

    public void setWinnerGroup(WinnerGroup winnerGroup) {
        this.winnerGroup = winnerGroup;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getBsn() {
        return bsn;
    }

    public void setBsn(int bsn) {
        this.bsn = bsn;
    }

    public int getSnb() {
        return snb;
    }

    public void setSnb(int snb) {
        this.snb = snb;
    }

}
