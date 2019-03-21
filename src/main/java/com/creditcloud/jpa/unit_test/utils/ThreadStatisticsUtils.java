package com.creditcloud.jpa.unit_test.utils;

import lombok.Data;


public class ThreadStatisticsUtils {

    public static Statistics statistics;

    private static ThreadLocal<Statistics> threadLocal = new ThreadLocal<>();

    public static Statistics getStatistics(){
        statistics = threadLocal.get();
        if(statistics==null){
            statistics = new Statistics(0,0);
            threadLocal.set(statistics);
        }
        return statistics;
    }

    @Data
    public static class Statistics{
        int count;
        long cost;

        public Statistics(int count,long cost){
            this.count = count;
            this.cost = cost;
        }

        public void addCost(long increasedCost){
            this.cost+=increasedCost;
        }

        public void addCount(long increasedCount){
            this.count+=increasedCount;
        }
    }
}
