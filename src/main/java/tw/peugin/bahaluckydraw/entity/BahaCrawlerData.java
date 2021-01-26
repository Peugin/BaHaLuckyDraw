package tw.peugin.bahaluckydraw.entity;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Date;

@ComponentScan
public class BahaCrawlerData {
    private int floor;
    private String userName;
    private String userID;
    private Date postDate;
    private String article;
    private int bsn;
    private int snb;

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
