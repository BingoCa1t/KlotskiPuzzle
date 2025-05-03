package com.klotski.utils.logger;

public class TestLogger
{
    public static void Test()
    {
        Logger.trace("Module 1","Trace Message 1");
        Logger.trace("Module 2");
        Logger.info("Module 3"," Info Message 3");
        Logger.info("Module 4");
        Logger.error("Module 5"," Error Message 5");
        Logger.error("Module 6");
        Logger.debug("Module 7"," Debug Message 7");
        Logger.debug("Module 8");
        Logger.warning("Module 9"," Warning Message 9");
        Logger.warning("Module 10");
        Logger.error("Module 5"," Error Message 5");
        Logger.error("Module 6");
        Logger.debug("Module 7"," Debug Message 7");
        Logger.debug("Module 8");
        System.out.println("Succeed.");
    }

}
