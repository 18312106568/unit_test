package com.creditcloud.jpa.unit_test.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RemoteServer {
    public static final String SERVER_REGISTER_NAME = "RemoteService";

     public static void main(String[] args) throws RemoteException {
           int port = 8021;
           RemoteService remoteService = new RemoteServiceImpl();
           //暴露服务端口
          UnicastRemoteObject.exportObject(remoteService,port);
          //创建注册中心
           Registry registry = LocateRegistry.createRegistry(1099);
           //注册服务
           registry.rebind(SERVER_REGISTER_NAME, remoteService);
    }
}
