package com.klotski.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledTask
{
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Runnable task;
    private boolean isPaused = false;
    private long initialDelay;
    private long period;
    private TimeUnit timeUnit;
    private java.util.concurrent.ScheduledFuture<?> future;

    public ScheduledTask(long initialDelay, long period, TimeUnit timeUnit)
    {
        this.initialDelay = initialDelay;
        this.period = period;
        this.timeUnit = timeUnit;
        this.task = () -> System.out.println("执行定时任务: " + System.currentTimeMillis());
    }

    // 设置自定义任务
    public void setTask(Runnable task)
    {
        this.task = task;
    }

    // 启动定时任务
    public void start()
    {
        if (future == null || future.isCancelled()) {
            future = scheduler.scheduleAtFixedRate(() -> {
                if (!isPaused) {
                    task.run();
                }
            }, initialDelay, period, timeUnit);
        }
    }

    // 暂停任务
    public void pause()
    {
        isPaused = true;
    }

    // 恢复任务
    public void resume()
    {
        isPaused = false;
    }

    // 终止任务
    public void stop()
    {
        if (future != null) {
            future.cancel(false);
            future = null;
        }
    }

    // 关闭调度器
    public void shutdown()
    {
        stop();
        scheduler.shutdown();
    }
}
