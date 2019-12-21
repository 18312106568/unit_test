package com.creditcloud.jpa.unit_test.bigData;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.util.Collector;

import java.io.File;
import java.util.List;

/**
 * Simple example that shows how the Batch SQL API is used in Java.
 *
 * <p>This example shows how to:
 *  - Convert DataSets to Tables
 *  - Register a Table under a name
 *  - Run a SQL query on the registered Table
 */
public class WordCountSQL {

    // *************************************************************************
    //     PROGRAM
    // *************************************************************************

    public static void main(String[] args) throws Exception {

        // set up execution environment
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);

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
        }).groupBy(0)
                // 分组的元组的计算方式为  value +value  也就是刚才的 同样的词 把 1+1
                .reduce(new ReduceFunction<Tuple2<String, Integer>>() {
                    @Override
                    public Tuple2<String, Integer> reduce(Tuple2<String, Integer> t2, Tuple2<String, Integer> t1) throws Exception {
                        return new Tuple2<>(t1.f0,t1.f1+ t2.f1);
                    }
                }).getInput();
        DataSet<WC> wcDataSet = dataset.map(new WC());

        // register the DataSet as a view "WordCount"
        tEnv.registerDataSet("WordCount", wcDataSet, "word, frequency");

        //tEnv.createTemporaryView("WordCount", input, "word, frequency");

        // run a SQL query on the Table and retrieve the result as a new Table
        Table table = tEnv.sqlQuery(
                "SELECT word, SUM(frequency) as frequency FROM WordCount GROUP BY word order by frequency desc").filter("frequency > 1");


        DataSet<WC> result = tEnv.toDataSet(table,WC.class);

        // emit result
        final ParameterTool params = ParameterTool.fromArgs(args);
        if (params.has("output")) {
            String outputName = params.get("output");
            File dir = new File(outputName);
            if(dir.exists()){
                if(dir.isDirectory()) {
                    File[] tempFiles = dir.listFiles();
                    for (File tempFile : tempFiles) {
                        tempFile.delete();
                    }
                }
                dir.delete();
            }
            result.writeAsText(outputName);

        } else {
            System.out.println("Printing result to stdout. Use --output to specify output path.");
            result.print();
        }
//        result.writeAsText("E:\\tmp\\flink\\out5");
////        OutputFormat outputFormat = new TextOutputFormat(new Path("E:\\tmp\\flink\\out5"));
////        result.output(outputFormat);
//        result.print();
        env.execute("WordCountSQL");
    }

    // *************************************************************************
    //     USER DATA TYPES
    // *************************************************************************

    /**
     * Simple POJO containing a word and its respective count.
     */
    @Data
    public static class WC  extends RichMapFunction<Tuple2<String, Integer>, WC> {
        public String word;
        public long frequency;

        // public constructor to make it a Flink POJO
        public WC() {}

        @Override
        public WC map(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
            return new WC(stringIntegerTuple2.f0,stringIntegerTuple2.f1);
        }


        public WC(String word, long frequency) {
            this.word = word;
            this.frequency = frequency;
        }
    }
}
