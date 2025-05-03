package com.klotski.network;

public interface NetworkMessageObserver
{
    void update(MessageCode code,String message);
}
