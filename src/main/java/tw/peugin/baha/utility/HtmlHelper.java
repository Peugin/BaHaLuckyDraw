package tw.peugin.baha.utility;

import org.apache.http.HttpStatus;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;

public class HtmlHelper {
    private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:84.0) Gecko/20100101 Firefox/84.0";
    private final static String PCForumPage = "https://forum.gamer.com.tw/C.php";
    private final static String MobileForumPage = "https://m.gamer.com.tw/forum/C.php";

    public static BahaAccessStatus testHttpStatus() throws IOException {
        Connection.Response response = Jsoup.connect(PCForumPage).userAgent(USER_AGENT).execute();

        if(response.statusCode() != 403) {
            return BahaAccessStatus.PC;
        }

        response = Jsoup.connect(MobileForumPage).userAgent(USER_AGENT).execute();

        if(response.statusCode() != 403) {
            return BahaAccessStatus.Mobile;
        }

        return BahaAccessStatus.None;
    }
}