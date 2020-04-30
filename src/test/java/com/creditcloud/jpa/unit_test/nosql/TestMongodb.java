/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.nosql;

import com.mongodb.MongoClient;
import com.mongodb.client.ListCollectionsIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.Iterator;

/**
 *
 * @author MRB
 */
public class TestMongodb {
    @Test
    public void testConnect(){
         try {

            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            // Now connect to your databases
            MongoDatabase mgdb = mongoClient.getDatabase("local");
            ListCollectionsIterable<Document> tables = mgdb.listCollections();
            Iterator<Document> iterator = tables.iterator();
            while(iterator.hasNext()){
                System.out.println(iterator.next());
            }
            //获取文档
            MongoCollection<Document>  collection = mgdb.getCollection("startup_log");
            System.out.println(collection.find().first());
            System.out.println("Connect to database successfully!");
            System.out.println("MongoDatabase inof is : "+mgdb.getName());
            // If MongoDB in secure mode, authentication is required.
            // boolean auth = db.authenticate(myUserName, myPassword);
            // System.out.println("Authentication: "+auth);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

}
