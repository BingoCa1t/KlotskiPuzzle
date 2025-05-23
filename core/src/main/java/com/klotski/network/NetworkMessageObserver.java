package com.klotski.network;

/**
 * 接收网络通信需实现此接口
 *
 * @author BingoCAT
 */
public interface NetworkMessageObserver
{
    void update(MessageCode code,String message);
}
