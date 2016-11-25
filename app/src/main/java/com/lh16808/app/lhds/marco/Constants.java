package com.lh16808.app.lhds.marco;


import com.lh16808.app.lhds.R;

public interface Constants {

    public static String MyCollect_title = "我的收藏";
    public static String MyBBs_title = "我的貼子";
    public static String MyRep_title = "我的回覆";
    String DownloadAPPName = "spkj.apk";
    // 配置文件
    String SHARED_PREFERENCE = "SharedPreferences";
    int[] music_sum = {R.raw.one_sum, R.raw.two_sum, R.raw.three_sum, R.raw.four_sum, R.raw.five_sum, R.raw.six_sum,
            R.raw.tm_sum};
    int[] music = {R.raw.one, R.raw.a1, R.raw.a2, R.raw.a3, R.raw.a4, R.raw.a5, R.raw.a6, R.raw.a7, R.raw.a8, R.raw.a9,
            R.raw.a10, R.raw.a11, R.raw.a12, R.raw.a13, R.raw.a14, R.raw.a15, R.raw.a16, R.raw.a17, R.raw.a18,
            R.raw.a19, R.raw.a20, R.raw.a21, R.raw.a22, R.raw.a23, R.raw.a24, R.raw.a25, R.raw.a26, R.raw.a27,
            R.raw.a28, R.raw.a29, R.raw.a30, R.raw.a31, R.raw.a32, R.raw.a33, R.raw.a34, R.raw.a35, R.raw.a36,
            R.raw.a37, R.raw.a38, R.raw.a39, R.raw.a40, R.raw.a41, R.raw.a42, R.raw.a43, R.raw.a44, R.raw.a45,
            R.raw.a46, R.raw.a47, R.raw.a48, R.raw.a49};
    int[] redbo = {1, 2, 7, 8, 12, 13, 18, 19, 23, 24, 29, 30, 34, 35, 40, 45, 46};
    int[] bulebo = {3, 4, 9, 10, 14, 15, 20, 25, 26, 31, 36, 37, 41, 42, 47, 48};
    int[] greenbo = {5, 6, 11, 16, 17, 21, 22, 27, 28, 32, 33, 38, 39, 43, 44, 49};
    int[] rbg = {R.drawable.ball_red_tool_lover, R.drawable.ball_blue_tool_lover, R.drawable.ball_green_tool_lover};
    int[] musicsx = {R.raw.shu, R.raw.niu, R.raw.hu, R.raw.tu, R.raw._long, R.raw.she, R.raw.ma, R.raw.yang, R.raw.hou,
            R.raw.ji, R.raw.gou, R.raw.zhu};
    String[] sxstr = new String[]{"鼠", "牛", "虎", "兔", "龍", "蛇", "馬", "羊", "猴", "雞", "狗", "豬"};
    String[] Sxstr2 = new String[]{"龙", "马", "鸡", "猪"};
    int[] Sxstr2L = new int[]{4, 6, 9, 11};
    int[] sa = {R.drawable.kj_sa1, R.drawable.kj_sa2, R.drawable.kj_sa3, R.drawable.kj_sa4, R.drawable.kj_sa5, R.drawable.kj_sa6,
            R.drawable.kj_sa7, R.drawable.kj_sa8, R.drawable.kj_sa9, R.drawable.kj_sa10, R.drawable.kj_sa11, R.drawable.kj_sa12,
            R.drawable.kj_sa13, R.drawable.kj_sa14, R.drawable.kj_sa15, R.drawable.kj_sa16, R.drawable.kj_sa17, R.drawable.kj_sa18,
            R.drawable.kj_sa19, R.drawable.kj_sa20, R.drawable.kj_sa21, R.drawable.kj_sa22, R.drawable.kj_sa23, R.drawable.kj_sa24,
            R.drawable.kj_sa25};
    int[] ca = {R.drawable.kj_ca1, R.drawable.kj_ca2, R.drawable.kj_ca3, R.drawable.kj_ca4, R.drawable.kj_ca5, R.drawable.kj_ca6,
            R.drawable.kj_ca7, R.drawable.kj_ca8, R.drawable.kj_ca9, R.drawable.kj_ca10, R.drawable.kj_ca11, R.drawable.kj_ca12,
            R.drawable.kj_ca13, R.drawable.kj_ca14, R.drawable.kj_ca15, R.drawable.kj_ca16, R.drawable.kj_ca17, R.drawable.kj_ca18,
            R.drawable.kj_ca19, R.drawable.kj_ca20, R.drawable.kj_ca21, R.drawable.kj_ca22, R.drawable.kj_ca23, R.drawable.kj_ca24,
            R.drawable.kj_ca25, R.drawable.kj_ca26, R.drawable.kj_ca27};
    int[] ed = {R.drawable.kj_ed1, R.drawable.kj_ed2, R.drawable.kj_ed3, R.drawable.kj_ed4, R.drawable.kj_ed5, R.drawable.kj_ed6,
            R.drawable.kj_ed7, R.drawable.kj_ed8, R.drawable.kj_ed9, R.drawable.kj_ed10, R.drawable.kj_ed11, R.drawable.kj_ed12,
            R.drawable.kj_ed13, R.drawable.kj_ed14, R.drawable.kj_ed15, R.drawable.kj_ed16, R.drawable.kj_ed17, R.drawable.kj_ed18,
            R.drawable.kj_ed19, R.drawable.kj_ed20, R.drawable.kj_ed21, R.drawable.kj_ed22, R.drawable.kj_ed23, R.drawable.kj_ed24,
            R.drawable.kj_ed25};
    String sendB = "XXXXyear";
    String IsPlay = "IsPlay";
    String URL_KEY = "enews";

    int[] SXCard = {R.drawable.ico_card_chicken, R.drawable.ico_card_cow, R.drawable.ico_card_dog,
            R.drawable.ico_card_dragon, R.drawable.ico_card_horse, R.drawable.ico_card_monkey,
            R.drawable.ico_card_mouse, R.drawable.ico_card_pig, R.drawable.ico_card_rabbit,
            R.drawable.ico_card_sheep, R.drawable.ico_card_snake, R.drawable.ico_card_tiger};
    //能轉盤
    public static final String CAN_LUCK_PAN = "can_luakpan";
    //轉盤的結果
    public static final String LUCK_PAN_RESULT = "luck_pan_result";
    // 开奖时间
    public static final String LOTTERY_TIME = "lottery_time";

    //能翻牌
    public static final String CAN_TURN_CARDS = "can_turn_cards";
    //翻牌的結果
    public static final String TURN_CARDS_RESULT = "turn_cards_result";

    //能搖一搖
    public static final String CAN_SHAKE = "can_shake";
    //搖一搖的結果
    public static final String SHAKE_RESULT = "shake_result";
}
