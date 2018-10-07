/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.nosql;
import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import org.apache.http.HttpHost;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;  
import org.apache.logging.log4j.Logger;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;

import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
/**
 *
 * @author MRB
 */
public class TestElasticSearch {
    
    @Test
    public void testConnection(){
            System.out.println(System.getProperty("file.encoding"));
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
                System.out.println(".................connect success!");
                //System.out.println(client);
                //获取所有节点
                List<DiscoveryNode> connectedNodes = client.connectedNodes();
                for(DiscoveryNode node : connectedNodes)
                {
                    System.out.println(node.getHostAddress());
                }
                //查询数据
               GetResponse getResponse = client.prepareGet("movies", "movie", "2").get();
               
		System.out.println(getResponse.getSourceAsString());

               client.close();
            } catch (UnknownHostException e) {
                System.out.println("1232123123");
            }
        
    }
    
//    @Test
//    public void testConnection2() throws IOException{
//        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
//                    new HttpHost("192.168.1.102", 9300, "http")));
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.query(QueryBuilders.matchAllQuery());            
//        searchSourceBuilder.aggregation(AggregationBuilders.terms("movies").field("movie").size(10));
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("movies");
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = client.search(searchRequest);
//    }
}
