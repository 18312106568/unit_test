package com.creditcloud.jpa.unit_test.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServiceClient {
    public static void main(String args[])  {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry("192.168.200.43",1099);
            RemoteService remoteService =(RemoteService) registry.lookup(RemoteServer.SERVER_REGISTER_NAME);

            System.out.println(remoteService.call("晓123"));

            String[] serviceList = registry.list();
            for(String service:serviceList){
                System.out.println(service);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }

    }
}
