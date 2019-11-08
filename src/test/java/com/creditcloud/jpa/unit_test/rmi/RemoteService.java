package com.creditcloud.jpa.unit_test.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteService extends Remote {
    String call(String name) throws RemoteException;;

    DataEntity changeData(DataEntity dataEntity)throws RemoteException;;
}
