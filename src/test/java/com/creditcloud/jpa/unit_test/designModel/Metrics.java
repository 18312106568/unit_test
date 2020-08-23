package com.creditcloud.jpa.unit_test.designModel;

import com.google.gson.Gson;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Metrics {

    public class MetricsCollector {
        private MetricsStorage metricsStorage;// 基于接口而非实现编程
        // 依赖注入
        public MetricsCollector(MetricsStorage metricsStorage) {
            this.metricsStorage = metricsStorage;
        }
        // 用一个函数代替了最小原型中的两个函数
        public void recordRequest(RequestInfo requestInfo) {
            if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
                return;
            }
            metricsStorage.saveRequestInfo(requestInfo);
        }
    }

    @Data
    public class RequestInfo {
        private String apiName;
        private double responseTime;
        private long timestamp;
        //... 省略 constructor/getter/setter 方法...
    }

    public interface MetricsStorage {
        void saveRequestInfo(RequestInfo requestInfo);
        List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMillis);
        Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMillis);
    }
    public class RedisMetricsStorage implements MetricsStorage {
        //... 省略属性和构造函数等...
        @Override
        public void saveRequestInfo(RequestInfo requestInfo) {
            //...
        }
        @Override
        public List<RequestInfo> getRequestInfos(String apiName, long startTimestamp, long endTimestamp) {
            //...
            return null;
        }
        @Override
        public Map<String, List<RequestInfo>> getRequestInfos(long startTimestamp, long endTimestamp) {
            //...
            return null;
        }
    }

    public static class Aggregator {
        public static  RequestStat aggregate(List<RequestInfo> requestInfos, long durationInMillis) {
            double maxRespTime = Double.MIN_VALUE;
            double minRespTime = Double.MAX_VALUE;
            double avgRespTime = -1;
            double p999RespTime = -1;
            double p99RespTime = -1;
            double sumRespTime = 0;
            long count = 0;
            for (RequestInfo requestInfo : requestInfos) {
                ++count;
                double respTime = requestInfo.getResponseTime();
                if (maxRespTime < respTime) {
                    maxRespTime = respTime;
                }
                if (minRespTime > respTime) {
                    minRespTime = respTime;
                }
                sumRespTime += respTime;
            }
            if (count != 0) {
                avgRespTime = sumRespTime / count;
            }
            long tps = (long)(count / durationInMillis * 1000);
            Collections.sort(requestInfos, new Comparator<RequestInfo>() {
                @Override
                public int compare(RequestInfo o1, RequestInfo o2) {
                    double diff = o1.getResponseTime() - o2.getResponseTime();
                    if (diff < 0.0) {
                        return -1;
                    } else if (diff > 0.0) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
            int idx999 = (int)(count * 0.999);
            int idx99 = (int)(count * 0.99);
            if (count != 0) {
                p999RespTime = requestInfos.get(idx999).getResponseTime();
                p99RespTime = requestInfos.get(idx99).getResponseTime();
            }
            RequestStat requestStat = new RequestStat();
            requestStat.setMaxResponseTime(maxRespTime);
            requestStat.setMinResponseTime(minRespTime);
            requestStat.setAvgResponseTime(avgRespTime);
            requestStat.setP999ResponseTime(p999RespTime);
            requestStat.setP99ResponseTime(p99RespTime);
            requestStat.setCount(count);
            requestStat.setTps(tps);
            return requestStat;
        }
    }

    @Data
    public static class RequestStat {
        private double maxResponseTime;
        private double minResponseTime;
        private double avgResponseTime;
        private double p999ResponseTime;
        private double p99ResponseTime;
        private long count;
        private long tps;
        //... 省略 getter/setter 方法...
    }



    public class ConsoleReporter {
        private MetricsStorage metricsStorage;
        private ScheduledExecutorService executor;
        public ConsoleReporter(MetricsStorage metricsStorage) {
            this.metricsStorage = metricsStorage;
            this.executor = Executors.newSingleThreadScheduledExecutor();
        }

        // 第 4 个代码逻辑：定时触发第 1、2、3 代码逻辑的执行；
        public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
            executor.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    // 第 1 个代码逻辑：根据给定的时间区间，从数据库中拉取数据；
                    long durationInMillis = durationInSeconds * 1000;
                    long endTimeInMillis = System.currentTimeMillis();
                    long startTimeInMillis = endTimeInMillis - durationInMillis;
                    Map<String, List<RequestInfo>> requestInfos =
                            metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                    Map<String, RequestStat> stats = new HashMap<>();
                    for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                        String apiName = entry.getKey();
                        List<RequestInfo> requestInfosPerApi = entry.getValue();
                        // 第 2 个代码逻辑：根据原始数据，计算得到统计数据；
                        RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
                        stats.put(apiName, requestStat);
                    }
                    // 第 3 个代码逻辑：将统计数据显示到终端（命令行或邮件）；
                    System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis + "]");
                    Gson gson = new Gson();
                    System.out.println(gson.toJson(stats));
                }
            }, 0, periodInSeconds, TimeUnit.SECONDS);
        }
    }
    public static class EmailReporter {
        private static final Long DAY_HOURS_IN_SECONDS = 86400L;
        private MetricsStorage metricsStorage;
        private EmailSender emailSender;
        private List<String> toAddresses = new ArrayList<>();
        public EmailReporter(MetricsStorage metricsStorage) {
            this(metricsStorage, new EmailSender(/* 省略参数 */));
        }
        public EmailReporter(MetricsStorage metricsStorage, EmailSender emailSender) {
            this.metricsStorage = metricsStorage;
            this.emailSender = emailSender;
        }
        public void addToAddress(String address) {
            toAddresses.add(address);
        }
        public void startDailyReport() {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date firstTime = calendar.getTime();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    long durationInMillis = DAY_HOURS_IN_SECONDS * 1000;
                    long endTimeInMillis = System.currentTimeMillis();
                    long startTimeInMillis = endTimeInMillis - durationInMillis;
                    Map<String, List<RequestInfo>> requestInfos =
                            metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);
                    Map<String, RequestStat> stats = new HashMap<>();
                    for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
                        String apiName = entry.getKey();
                        List<RequestInfo> requestInfosPerApi = entry.getValue();
                        RequestStat requestStat = Aggregator.aggregate(requestInfosPerApi, durationInMillis);
                        stats.put(apiName, requestStat);
                    }
                    // TODO: 格式化为 html 格式，并且发送邮件
                }
            }, firstTime, DAY_HOURS_IN_SECONDS * 1000);
        }
    }

    public static class EmailSender{}
}
