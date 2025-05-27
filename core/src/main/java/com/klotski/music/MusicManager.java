package com.klotski.music;

import com.badlogic.gdx.audio.Music;
import com.klotski.Main;
import com.klotski.assets.AssetsPathManager;
import com.klotski.assets.MusicAssets;
import com.klotski.utils.logger.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 音乐播放管理器
 * @author Life_Checkpoint
 * @author BingoCAT
 */
public class MusicManager {
    private AssetsPathManager apManager;
    private MusicAudio currentMusicName;
    private boolean isPlaying = false;
    private float volume = 1.0f;
    private Map<MusicAudio, Music> musicMap;
    private Music currentMusic;

    public enum MusicAudio {
        GameMusic,MainBGM;
    }

    public MusicManager(Main gameMain) {
        musicMap = new HashMap<>();
        this.apManager = gameMain.getAssetsPathManager();
        // 主音量 × 音乐音量
        setVolume(gameMain.getSettingManager().gameSettings.sound.masterVolume * gameMain.getSettingManager().gameSettings.sound.musicVolume);

    }

    public void loadMusic(MusicAudio audioAlias, MusicAssets audioAssets) {
        if (musicMap.containsKey(audioAlias)) {
            Logger.warning("MusicManager", "Music already loaded: " + audioAlias.toString());
            return;
        }
        Music music = apManager.get(audioAssets);
        musicMap.put(audioAlias, music);
    }

    // 播放
    public void play(MusicAudio audioName, boolean loop) {
        // 停止当前音乐
        if (currentMusic != null) {
            currentMusic.stop();
        }

        currentMusic = musicMap.get(audioName);
        if (currentMusic != null) {
            currentMusic.setLooping(loop);
            currentMusic.setVolume(volume);
            currentMusic.play();
            currentMusicName = audioName;
            isPlaying = true;

            // 设置播放结束回调
            currentMusic.setOnCompletionListener(Music::play);

            Logger.info("MusicManager", "Music playing: " + audioName);

        } else {
            Logger.error("MusicManager", "Music not found: " + audioName);
        }
    }



    // 停止
    public void stop() {
        if (currentMusic != null) {
            currentMusic.stop();
            isPlaying = false;
        }
    }

    // 暂停
    public void pause() {
        if (currentMusic != null && isPlaying) {
            currentMusic.pause();
            isPlaying = false;
        }
    }

    // 从暂停恢复播放
    public void resume() {
        if (currentMusic != null && !isPlaying) {
            currentMusic.play();
            isPlaying = true;
        }
    }

    /**
     * 设置音量
     * @param volume 音量，介于 0~1
     */
    public void setVolume(float volume) {
        if (volume < 0 || volume > 1) Logger.error("MusicManager", String.format("Volumn is not in range. Expect (0, 1), get %.2f", volume));
        this.volume = Math.max(0, Math.min(volume, 1));
        if (currentMusic != null) {
            currentMusic.setVolume(this.volume);
        }
    }

    public void dispose() {
        stop();
        for (Music music : musicMap.values()) {
            music.dispose();
        }
        musicMap.clear();
    }

    public float getVolume() {
        return volume;
    }

}
