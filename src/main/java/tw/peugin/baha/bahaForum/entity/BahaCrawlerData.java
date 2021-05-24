package tw.peugin.baha.bahaForum.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BahaCrawlerData {
    private int floor;
    private String userName;
    private String userID;
    private Date postDate;
    private String article;
    private int bsn;
    private int snb;

    public BahaCrawlerData() {
    }

    public BahaCrawlerData(int floor, String userName, String userID, Date postDate, String article, int bsn, int snb) {
        this.floor = floor;
        this.userName = userName;
        this.userID = userID;
        this.postDate = postDate;
        this.article = article;
        this.bsn = bsn;
        this.snb = snb;
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
}
