package com.creditcloud.jpa.unit_test.designModel;

public class LiskovSubstitutionPrinciple {
//    public class Transporter {
//        private HttpClient httpClient;
//
//        public Transporter(HttpClient httpClient) {
//            this.httpClient = httpClient;
//        }
//        public Response sendRequest(Request request) {
//            // ...use httpClient to send request
//        }
//    }
//
//    public class SecurityTransporter extends Transporter {
//        private String appId;
//        private String appToken;
//        public SecurityTransporter(HttpClient httpClient, String appId, String appToken) {
//            super(httpClient);
//            this.appId = appId;
//            this.appToken = appToken;
//        }
//        @Override
//        public Response sendRequest(Request request) {
//            if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(appToken)) {
//                request.addPayload("app-id", appId);
//                request.addPayload("app-token", appToken);
//            }
//            return super.sendRequest(request);
//        }
//    }
//
//
//    static void main(String args[]){
//        Reuqest request = new Request();
//        //... 省略设置 request 中数据值的代码...
//        Response response = transporter.sendRequest(request);
//        //... 省略其他逻辑...
//    }
}
