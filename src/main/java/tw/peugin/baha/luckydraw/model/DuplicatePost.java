package tw.peugin.baha.luckydraw.model;

public enum DuplicatePost {
    //不特別處理
    None,
    //保留一個名額
    SaveOne,
    //刪除名額
    Delete;

    public static DuplicatePost fromInteger(Integer x) {
        switch(x) {
            case 0:
                return None;
            case 1:
                return SaveOne;
            case 2:
                return Delete;
            default:
                throw new IllegalArgumentException();
        }
    }
}
