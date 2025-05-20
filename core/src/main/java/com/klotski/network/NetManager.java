package com.klotski.network;

import com.klotski.utils.logger.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 客户端线程类，实现 Runnable 接口
public class NetManager implements Runnable
{
    private final String serverAddress;
    private final int port;
    private PrintWriter out;
    private final Queue<String> messageQueue = new LinkedList<>();
    private boolean isRunning = true;
    private ArrayList<NetworkMessageObserver> observers = new ArrayList<>();
    public NetManager(String serverAddress, int port)
    {
        this.serverAddress = serverAddress;
        this.port = port;
    }
    public void addObserver(NetworkMessageObserver observer)
    {
        observers.add(observer);
    }
    @Override
    public void run()
    {
        try (
            // 创建 Socket 对象，连接到服务器
            Socket socket = new Socket(serverAddress, port);
            // 获取输出流，用于向服务器发送消息
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            // 获取输入流，用于接收服务器的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        )
        {
            this.out = out;
            System.out.println("已连接到服务器：" + serverAddress + ":" + port);

            // 启动一个线程处理接收服务器消息
            Thread receiveThread = new Thread(() ->
            {
                try
                {
                    String response;
                    while ((response = in.readLine()) != null)
                    {
                        System.out.println("服务器响应：" + response);
                        try
                        {
                            MessageCode mc = MessageCode.fromCode(response.substring(0, 5));
                            for (NetworkMessageObserver observer : observers)
                            {
                                observer.update(mc, response.substring(5));
                            }
                        }
                        catch (Exception e)
                        {
                            Logger.warning("NetManager",e.getMessage());
                        }

                    }
                } catch (IOException e)
                {
                    if (isRunning)
                    {
                        Logger.error("NetworkManager",e.getMessage());
                    }
                }
            });
            receiveThread.start();

            // 循环检查消息队列并发送消息
            while (isRunning)
            {
                if (!messageQueue.isEmpty())
                {
                    String message = messageQueue.poll();
                    out.println(message);
                }
                Thread.sleep(100);
            }
        } catch (IOException | InterruptedException e)
        {
            if (isRunning)
            {
                Logger.error("NetworkManager",e.getMessage());
            }
        }
    }

    // 向消息队列中添加消息
    public synchronized void sendMessage(String message)
    {
        messageQueue.add(message);
    }

    public synchronized void sendMessage(MessageCode code, String message)
    {
        messageQueue.add(code.getCode()+message);
    }
    public synchronized void sendMessage(MessageCode code,String... messages)
    {
        StringBuilder message = new StringBuilder();
        message.append(code.getCode());
        for(String m : messages)
        {
            message.append(m);
            message.append("|");
        }
        message.deleteCharAt(message.length()-1);
        messageQueue.add(message.toString());
    }
    // 停止线程
    public synchronized void stopThread()
    {
        isRunning = false;
    }
}
