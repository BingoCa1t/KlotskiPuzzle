package com.klotski.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.klotski.Main;
import com.klotski.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统一资源管理器 AssetsPathManager
 *
 * @author Life_Checkpoint
 * @author BingoCAT
 */
public class AssetsPathManager
{
    private static boolean usingMipMap = false;

    AssetManager assetManager = new AssetManager();
    /**
     * 资源类型-路径映射表
     */
    private Map<Class<?>, List<String>> assetsMap = new HashMap<>();

    /**
     * 资源管理器构造
     *
     * @param gameMain 游戏全局句柄
     */
    public AssetsPathManager(Main gameMain)
    {
        usingMipMap = gameMain.getSettingManager().gameSettings.graphics.mipmap;
    }

    /**
     * 通过以上定义的资源枚举<b>将所有 Image Music 和 Spine 资源加入映射表</b>
     */
    public void preloadAllAssets()
    {

        // 加载 ImageAssets
        for (ImageAssets imageAsset : ImageAssets.values())
        {
            addAsset(Texture.class, imageAsset.getAlias());
        }

        // 加载 MusicAssets
        for (MusicAssets musicAsset : MusicAssets.values())
        {
            addAsset(Music.class, musicAsset.getAlias());
        }


        int preloadAssetsNum = ImageAssets.values().length + MusicAssets.values().length;

        Logger.info("AssetsPathManager", String.format("Preload %d Assets", preloadAssetsNum));
    }

    /**
     * 向映射表加入资源
     *
     * @param resourceClass 资源类型
     * @param resourcePath  资源路径
     */
    public <T> void addAsset(Class<T> resourceClass, String resourcePath)
    {
        assetsMap.computeIfAbsent(resourceClass, k -> new ArrayList<>()).add(resourcePath);
    }

    /**
     * 更新加载器
     */
    public boolean update()
    {
        return assetManager.update();
    }

    /**
     * 获取加载进度
     */
    public float getProgress()
    {
        return assetManager.getProgress();
    }

    /**
     * 将加载字典注入加载器
     * <br><br>
     * 某些类型只能使用同步加载而非当前异步加载
     */
    public void startAssetsLoading()
    {

        // mipmap配置
        TextureParameter textureMipmapParam = new TextureParameter();
        textureMipmapParam.minFilter = TextureFilter.Linear;
        textureMipmapParam.genMipMaps = true;

        for (Map.Entry<Class<?>, List<String>> entry : assetsMap.entrySet())
        {
            Class<?> resourceClass = entry.getKey();
            List<String> resourcePaths = entry.getValue();

            for (String resourcePath : resourcePaths)
            {
                // 根据类型加载资源

                if (resourceClass == Texture.class)
                {
                    if (usingMipMap) assetManager.load(resourcePath, Texture.class, textureMipmapParam);
                    else assetManager.load(resourcePath, Texture.class);
                } else if (resourceClass == Music.class)
                {
                    assetManager.load(resourcePath, Music.class);
                } else if (resourceClass == Skin.class)
                {
                    assetManager.load(resourcePath, Skin.class);
                } else if (resourceClass == Sound.class)
                {
                    assetManager.load(resourcePath, Sound.class);
                } else if (resourceClass == TextureAtlas.class)
                {
                    assetManager.load(resourcePath, TextureAtlas.class);
                } else
                {
                    Logger.error("AssetsPathManager", String.format("Unknow assets type: %s", resourceClass));
                }
            }
        }

        assetsMap.clear();
    }

    /**
     * 获得 Image 资源对象
     *
     * @param resourceEnum 图像资源枚举
     * @return 指定资源
     */
    public Texture get(ImageAssets resourceEnum)
    {
        return get(resourceEnum.getAlias(), Texture.class);
    }

    /**
     * 获得 Music 资源对象
     *
     * @param resourceEnum 音乐资源枚举
     * @return 指定资源
     */
    public Music get(MusicAssets resourceEnum)
    {
        return get(resourceEnum.getAlias(), Music.class);
    }

    /**
     * 获得资源对象
     *
     * @param resourcePath  资源路径
     * @param resourceClass 资源类型
     * @return 指定资源
     */
    public <T> T get(String resourcePath, Class<T> resourceClass)
    {
        // 未实际加载的资源进行同步加载
        if (!assetManager.isLoaded(resourcePath))
        {
            Logger.warning("AssetsPathManager", resourcePath + " hasn't loaded by AssetManager. Load synchronously");
            assetManager.load(resourcePath, resourceClass);
            assetManager.finishLoading();
        }

        return assetManager.get(resourcePath, resourceClass);
    }

    public static Music audioLoad(String fileName)
    {
        return Gdx.audio.newMusic(Gdx.files.internal(fileName));
    }

    public static Texture textureLoad(String fileName)
    {
        return new Texture(fileName);
    }

    public static ShaderProgram shaderLoad(String vertexFileName, String fragmentFileName)
    {
        return new ShaderProgram(
            Gdx.files.internal(vertexFileName),
            Gdx.files.internal(fragmentFileName)
        );
    }

    public FileHandle fileObj(String filePath)
    {
        return Gdx.files.internal(filePath);
    }

    public void dispose()
    {
        assetManager.dispose();
    }

}
