package com.klotski.user;

import com.badlogic.gdx.Net;
import com.klotski.network.MessageCode;
import com.klotski.network.NetManager;
import com.klotski.network.NetworkMessageObserver;

import java.io.PrintWriter;
import java.net.Socket;

public class UserManager implements NetworkMessageObserver
{
    private NetManager netManager;

    public UserManager(NetManager netManager)
    {
        this.netManager = netManager;
    }

    public String getCode(String email)
    {
        netManager.sendMessage(email);
        return null;
    }

    public int register(String username, String passwordMD5, String email, String code)
    {
        return -1;
    }

    @Override
    public void update(MessageCode code, String message)
    {

    }
}
