package com.klotski.assets;

/**
 * 图像资源枚举
 */
public enum ImageAssets
{
    ChessBoardFrame("gameMainScene\\chessBoard.png"),
    ChessBoardBackground("gameMainScene\\chessBoardBackground.png"),
    LevelSelectBackground("levelSelect\\levelSelectBackground.png"),
    SelectLevelText("levelSelect\\selectLevelText.png"),
    GameMainRestartButton("gameMainButton\\restart.png"),
    GameMainUndoButton("gameMainButton\\undo.png"),
    GameMainHintButton("gameMainButton\\hint.png"),
    GameMainBackButton("gameMainButton\\backButton.png"),
    GameMainUpButton("gameMainButton\\upButton.png"),
    GameMainDownButton("gameMainButton\\downButton.png"),
    GameMainLeftButton("gameMainButton\\leftButton.png"),
    GameMainRightButton("gameMainButton\\rightButton.png"),
    GameMainSettingButton("gameMainButton\\settingButton.png"),
    GameMainBackground("gameMainScene\\mainBackground.jpeg"),
    GameMainRecordBackground("gameMainScene\\recordBackground.png"),
    GameMainStepBackground("gameMainScene\\stepBackground.png"),
    GameMainDirectionBackground("gameMainScene\\directionBackground.png"),
    GameMainStarProgress("gameMainScene\\starProgress.png"),
    GameMainStepRectangle("gameMainScene\\stepRectangle.png"),
    ObstacleChessBoardFrame("gameMainScene\\obstacleChessBoard.png"),
    ObstacleStepBackground("gameMainScene\\obstacleStepBackground.png"),
    RegisterBackButton("registerScene/left_arrow.png"),
    RegisterScene("registerScene/RegisterScene.png"),
    LoginButton("registerScene/loginButton.png"),
    StartSceneBackground("startScene\\startBackground.png"),
    StartButton("startScene\\startButton.png"),
    WatchButton("startScene\\watchButton.png"),
    White("gameMainScene\\white.png"),
    OneStar("levelSelect\\1star.png"),
    TwoStar("levelSelect\\2star.png"),
    ThreeStar("levelSelect\\3star.png"),
    OneStarInGame("gameMainScene\\oneStar.png"),
    // 曹操
    cc("chess\\cc.png"),
    ccS("chess\\ccS.png"),
    // 关羽
    gyH("chess\\gyH.png"),
    gyW("chess\\gyW.png"),
    gyHS("chess\\gyHS.png"),
    gyWS("chess\\gyWS.png"),
    // 赵云
    zyH("chess\\zyH.png"),
    zyW("chess\\zyW.png"),
    zyHS("chess\\zyHS.png"),
    zyWS("chess\\zyWS.png"),
    // 黄忠
    hzH("chess\\hzH.png"),
    hzW("chess\\hzW.png"),
    hzHS("chess\\hzHS.png"),
    hzWS("chess\\hzWS.png"),
    // 马超
    mcH("chess\\mcH.png"),
    mcW("chess\\mcW.png"),
    mcHS("chess\\mcHS.png"),
    mcWS("chess\\mcWS.png"),
    // 卒
    zu("chess\\zu.png"),
    zuS("chess\\zuS.png"),
    // 张飞
    zfH("chess\\zfH.png"),
    zfW("chess\\zfW.png"),
    zfHS("chess\\zfHS.png"),
    zfWS("chess\\zfWS.png"),
    pbButtonBackground("playbackScene\\pbButtonBackground.png"),
    pbBackButton("playbackScene\\pbBackButton.png"),
    pbBackKButton("playbackScene\\pbBackKButton.png"),
    pbNextButton("playbackScene\\pbNextButton.png"),
    pbNextTButton("playbackScene\\pbNextTButton.png"),
    pbPlayButton("playbackScene\\pbPlayButton.png"),
    pbPauseButton("playbackScene\\pbPauseButton.png"),
    BoomButton("gameMainScene\\boom.png"),
    ;
    private final String alias;

    ImageAssets(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
}
