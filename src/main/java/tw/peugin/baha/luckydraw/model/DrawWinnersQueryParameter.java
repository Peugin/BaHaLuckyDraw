package tw.peugin.baha.luckydraw.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DrawWinnersQueryParameter {
    @NotEmpty(message = "必須要填寫網址")
    private String url;
    @Min(value = 1,message = "起始樓層必須大於等於一")
    private Integer startFloor = 1;
    @Min(value = 1,message = "結束樓層必須大於等於一")
    private Integer endFloor = Integer.MAX_VALUE;
    private Date startDate = new Date(Long.MIN_VALUE);
    private Date endDate = new Date(Long.MAX_VALUE);
    @NotNull
    private String keyword = "";
    @NotNull(message = "必須要填寫抽獎人數")
    @Min(value = 1,message = "抽獎人數必須大於等於一")
    private int drawNums;
    private List<String> blackList;
    private boolean useRegex;
    private boolean saveDraw;
    private boolean duplicateSaveDraw;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStartFloor() {
        return startFloor;
    }

    public void setStart_floor(Integer startFloor) {
        this.startFloor = startFloor;
    }

    public Integer getEndFloor() {
        return endFloor;
    }

    public void setEnd_floor(Integer endFloor) {
        this.endFloor = endFloor;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStart_date(String startDate) throws ParseException {
        this.startDate = sdf.parse(startDate);
    }

    /*public void setStart_date(Date startDate) {
        this.startDate = startDate;
    }*/

    public Date getEndDate() {
        return endDate;
    }

    public void setEnd_date(String endDate) throws ParseException {
        this.endDate = sdf.parse(endDate);
    }

    /*public void setEnd_date(Date endDate) {
        this.endDate = endDate;
    }*/

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getDrawNums() {
        return drawNums;
    }

    public void setDraw_nums(int drawNums) {
        this.drawNums = drawNums;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }

    public boolean isUseRegex() {
        return useRegex;
    }

    public void setUse_regex(boolean useRegex) {
        this.useRegex = useRegex;
    }

    public boolean isSaveDraw() {
        return saveDraw;
    }

    public void setSave_draw(boolean saveDraw) {
        this.saveDraw = saveDraw;
    }

    public boolean isDuplicateSaveDraw() {
        return duplicateSaveDraw;
    }

    public void setDuplicate_save_draw(boolean duplicateSaveDraw) {
        this.duplicateSaveDraw = duplicateSaveDraw;
    }
}
