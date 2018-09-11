/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.nosql;
import com.google.common.collect.ImmutableList;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;  
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.cluster.node.DiscoveryNode;
/**
 *
 * @author MRB
 */
public class TestElasticSearch {
    
    @Test
    public void testConnection(){
        
            Settings settings = Settings.builder()
                    //嗅探功能
                    .put("client.transport.sniff", true)
                    //集群名称
                    .put("cluster.name", "my-es")
                    .build();
            try {
               TransportClient client = new PreBuiltTransportClient(settings)
                        //连接es端口
                        .addTransportAddress(new InetSocketTransportAddress(
                                InetAddress.getByName("192.168.1.102"), 9300));
                System.out.println(".................连接成功！");
                System.out.println(client);
                List<DiscoveryNode> connectedNodes = client.connectedNodes();
                for(DiscoveryNode node : connectedNodes)
                {
                    System.out.println(node.getHostAddress());
                }
               GetResponse getResponse = client.prepareGet("movies", "movie", "1").get();
		System.out.println(getResponse.getSourceAsString());

               
            } catch (UnknownHostException e) {
                System.out.println("1232123123");
            }
        
    }
}
