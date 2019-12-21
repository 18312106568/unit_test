package com.creditcloud.jpa.unit_test.bigData;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.io.OutputFormat;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvOutputFormat;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.fs.Path;
import org.apache.flink.util.Collector;

import java.util.List;

public class TestFlink {

    public static void main(String[] args) throws Exception {

        // set up the batch execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //读取目录下的文件
        DataSource<String> data = env.readTextFile("E:\\tmp\\gm-appserver-fee.log.2019-12-14.1");
        //把文件中的内容按照空格进行拆分为 word,1    1 是为了能够在下面进行计算.
        DataSet<Tuple2<String, Integer>> dataset = data.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
                Gson gson = new Gson();
                try{
                    String objStr = s.substring(s.indexOf("[{"));
                    List<Object> wordMapArr =
                            gson.fromJson(objStr,new TypeToken<List<Object>>(){}.getType());
                    for(Object wordMap : wordMapArr){
                        LinkedTreeMap<String,Object> treeMap = (LinkedTreeMap) wordMap;
                        for(String key : treeMap.keySet()){
                            collector.collect(new Tuple2<>(key+"-"+treeMap.get(key).toString(),1));
                        }
                    }

                }catch(Exception ex){}

            }
        })
                // 按照元组中的第1位进行分组
                .groupBy(0)
                // 分组的元组的计算方式为  value +value  也就是刚才的 同样的词 把 1+1
                .reduce(new ReduceFunction<Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> t2, Tuple2<String, Integer> t1) throws Exception {
                        return new Tuple2<>(t1.f0,t1.f1+ t2.f1);
                    }
                }).getInput();
                //输出结果
                //.print();
        OutputFormat outputFormat = new CsvOutputFormat(new Path("E:\\tmp\\flink.txt"));//(new Path("E:\\tmp\\flink.txt"));
        dataset.output(outputFormat);
//        dataset.addSink(new FlinkKafkaProducer011<>("localhost:9092", "wiki-result", new SimpleStringSchema()));
//        dataset.print();
        System.out.println(dataset.count());
    }
}
