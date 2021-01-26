# 巴哈回文抽獎系統 ─ 可輕鬆使用的巴哈回文抽獎系統
![image](https://raw.githubusercontent.com/Peugin/bahaluckydraw/master/MDImage/Header.png?token=AGHGEOHS2K5OTNEICJV4RTLACANN2)

[試用看看](https://bahaluckydraw.herokuapp.com/index)
---
## 目錄
* [使用教學](#使用教學)
* [程式說明](#程式說明)
---
### 使用教學
#### 參數說明
* 網址：想從哪個網址抽人，網址內必須包含 https://forum.gamer.com.tw/C.php?bsn=xxx&snA=xxx ，其餘參數可以保留，參數可調動。
* 樓層：有起始樓層與結束樓層，若樓層過多請務必輸入，否則會搜尋過久導致失敗（建議小於 10000 樓）。
* 時間：有起始日期與時間，結束日期與時間，若要指定日期或時間，則必須將日期或時間填完整，才會作用。
 （例：我填了年份，其他都不填，則沒有作用。填了小時，但沒有填分鐘，則沒有作用。）
* 關鍵字：想搜尋的關鍵字，可透過下方的「使用正規表達式」方式調整成正規表達式搜尋。
* 人數：抽獎的人數。
* 使用正規表達式：可使用正規表達式搜尋關鍵字。
#### 選填說明
* 必填的有「網址」、「關鍵字」與「人數」。
* 「樓層」與「時間」的開始與結束可只填其中一邊，預設會是最小與最大。
#### 抽獎名單說明
![image](https://raw.githubusercontent.com/Peugin/bahaluckydraw/master/MDImage/Tutorial_1.png?token=AGHGEODWBRVDKYA2ZNBIQKTACANGM)
* 帳號：為一個可以點擊的超連結，點擊後會前往對方小屋。
* 暱稱：為一個可以點擊的超連結，點擊後會前往對方小屋。
* 發言：為一個可以點擊的超連結，點擊後會前往該發言網址。為了防止過多留言而導致版面錯亂，只會顯示前面幾個字後剩餘的刪除。圖片不會顯示在發言處。
---
### 程式說明
這個網頁伺服器並沒有使用任何資料庫，所有的抽獎名單都是以爬蟲方式解析而成的。
