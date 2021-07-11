package tw.peugin.baha.utility;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;

public class HtmlHelper {
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    private final static String PCForumPage = "https://forum.gamer.com.tw/C.php";
    private final static String MobileForumPage = "https://m.gamer.com.tw/forum/C.php";

    public static BahaAccessStatus testHttpStatus() throws IOException {
        try {
            Connection.Response response = Jsoup.connect(PCForumPage).userAgent(USER_AGENT).timeout(30000).execute();
            return BahaAccessStatus.PC;
        }catch (HttpStatusException e) {
            if (e.getStatusCode() == 403) {
                try {
                    Connection.Response response = Jsoup.connect(MobileForumPage).userAgent(USER_AGENT).timeout(30000).execute();
                    return BahaAccessStatus.Mobile;
                } catch (HttpStatusException f) {
                    if (f.getStatusCode() == 403) {
                        return BahaAccessStatus.None;
                    }
                }
            }
        }
        return BahaAccessStatus.None;
    }
}