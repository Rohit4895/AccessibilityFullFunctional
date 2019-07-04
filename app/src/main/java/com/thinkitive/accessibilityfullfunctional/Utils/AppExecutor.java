package com.thinkitive.accessibilityfullfunctional.Utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private final Executor diskIo;
    private final Executor loggerIo;
    private final Executor networkIo;
    private final Executor mainThread;

    private AppExecutor(Executor diskIo, Executor loggerIo, Executor networkIo, Executor mainThread) {
        this.diskIo = diskIo;
        this.loggerIo = loggerIo;
        this.networkIo = networkIo;
        this.mainThread = mainThread;
    }

    private static AppExecutor instance = null;

    public static AppExecutor getInstance(){
        if (instance == null){
            instance = new AppExecutor(Executors.newFixedThreadPool(1),
                    Executors.newFixedThreadPool(2), Executors.newFixedThreadPool(1),
                    new MainThreadExecutor());
        }
        return instance;
    }

    public static class MainThreadExecutor implements Executor{
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    }

    public Executor getDiskIo(){
        return diskIo;
    }

    public Executor getLoggerIo(){
        return loggerIo;
    }

    public Executor getNetworkIo(){
        return networkIo;
    }

    public Executor getMainThread(){
        return mainThread;
    }
}
