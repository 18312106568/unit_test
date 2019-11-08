package com.creditcloud.jpa.unit_test.rmi;

import java.rmi.RemoteException;

public class RemoteServiceImpl implements RemoteService {
    @Override
    public String call(String name)throws RemoteException {
        return String.format("hi,i am %s",name);
    }

    @Override
    public DataEntity changeData(DataEntity dataEntity) throws RemoteException {
        dataEntity.setName("321");
        System.out.println(dataEntity);
        return dataEntity;
    }
}
