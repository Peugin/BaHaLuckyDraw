# 巴哈回文抽獎系統 ─ 可輕鬆使用的巴哈回文抽獎系統
![image](https://github.com/Peugin/bahaluckydraw/blob/master/MDImage/Header.png)
[使用看看](https://bahaluckydraw.herokuapp.com/)
---
## 目錄
* [最新更新](#最新更新)
* [使用教學](#使用教學)
* [預定新增功能](#預定新增功能)
---
##最新更新
###[v3-1] - 2021-05-25
#### 暫時修正
* 有時候抽獎的等待圈圈提示於資料讀取完畢後還是無法消失的錯誤。
---
## 使用教學
### 參數說明
#### 必要參數
* 網址：想從哪個網址抽人，網址內必須包含 https://forum.gamer.com.tw/C.php?bsn=xxx&snA=xxx ，或是 https://m.gamer.com.tw/forum/C.php?bsn=xxx&snA=xxx。
* 關鍵字：想搜尋的關鍵字，可透過下方的「使用正規表達式」方式調整成正規表達式搜尋。
* 人數：抽獎的人數。
#### 可選參數
* 樓層：有起始樓層與結束樓層，若樓層過多請務必輸入，否則會搜尋過久導致失敗（建議小於 10000 樓）。
* 時間：有起始日期與時間，結束日期與時間，若要指定日期或時間，則必須將日期或時間填完整，才會作用。
 （例：我填了年份，但沒有填月份、日期，則沒有作用。填了小時，但沒有填分鐘，則沒有作用。）
* 黑名單：
* 使用正規表達式：可使用正規表達式搜尋關鍵字。
#### 額外說明
* 「樓層」與「時間」的開始與結束可只填其中一邊，預設會是最小與最大。
### 中獎名單說明
![image](https://github.com/Peugin/bahaluckydraw/blob/master/MDImage/Tutorial_1.png)
* 帳號：為一個可以點擊的超連結，點擊後會前往對方小屋。
* 暱稱：為一個可以點擊的超連結，點擊後會前往對方小屋。
* 發言：為一個可以點擊的超連結，點擊後會前往該發言網址。為了防止過多留言而導致版面錯亂，只會顯示前面幾個字後剩餘的刪除。圖片不會顯示在發言處。
---
## 預定新增功能
- [ ] 重複回文排除，或其他機制。
- [ ] 新增會被抽到的抽獎名單人選。
- [ ] 新增更多可選參數。（例如勇者等級，或是回文長度。）
