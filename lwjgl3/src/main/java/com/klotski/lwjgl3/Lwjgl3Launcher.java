package com.klotski.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.klotski.Main;
import com.klotski.settings.SettingManager;

/** 启动（LWJGL3）前端 */
public class Lwjgl3Launcher
{
    public static SettingManager settingManager;
    public static void main(String[] args)
    {
        if (StartupHelper.startNewJvmIfRequired()) return;
        settingManager = new SettingManager();
        createApplication();
    }

    private static Lwjgl3Application createApplication()
    {
        return new Lwjgl3Application(new Main(settingManager), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration()
    {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Klotski");
        configuration.setBackBufferConfig(8, 8, 8, 8, 16, 0, settingManager.gameSettings.graphics.msaa);
        configuration.useVsync(settingManager.gameSettings.graphics.vsync);
        configuration.setResizable(false);
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// If you remove the above line and set Vsync to false, you can get unlimited FPS, which can be
        //// useful for testing performance, but can also be very stressful to some hardware.
        //// You may also need to configure GPU drivers to fully disable Vsync; this can cause screen tearing.

        configuration.setWindowedMode(1920, 1080);
        //// You can change these files; they are in lwjgl3/src/main/resources/ .
        //// They can also be loaded from the root of assets/ .
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
