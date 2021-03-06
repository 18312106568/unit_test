/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.creditcloud.jpa.unit_test.parse;

import com.creditcloud.jpa.unit_test.utils.ConverUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author MRB
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestJson {
    
    @Autowired
    Configuration cfg;
    
    char[] jsonArrays;

    Properties properties = new Properties();
    
    final String BEAN_FTL = "bean-json.ftl";
    
    final String JAVA_FIX = ".java";
    
    final String ENCODE = "UTF-8";
    
    final String TEMP_DIR = "/tmp";

    @Before
    public void init(){

        try{
            File file = ResourceUtils.getFile("classpath:gateio-comment.properties");
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            properties.load(bufReader);
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
    
    //@Test
    public static void main(String args[]) throws IOException{
        System.out.println(System.getProperty("file.encoding"));
        System.out.println("中文");
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
              .url("https://www.huya.com/member/task.php?m=User&do=listTotal&callback=huyaNavUserCard")
              .get()
              .addHeader("Accept-Encoding", "gzip")
               .addHeader("cookie", "__yamid_tt1=0.5201708322216603; __yamid_new=C7F4227E01C0000138B4296BB5001B15; hd_newui=0.04613345228277432; SoundValue=1.00; udb_guiddata=50be64759c324c3e9b949a307e1840a8; alphaValue=0.80; guid=716c41f63e1c9e5b5d705e185f189e3e; videoLine=1; isInLiveRoom=; Hm_lvt_51700b6c722f5bb4cf39906a596ea41f=1537088556,1538798535,1538967117; PHPSESSID=92cd2lqsjn3l5fdln4no0s63c5; __yasmid=0.5201708322216603; udb_passdata=3; web_qrlogin_confirm_id=9162a928-2459-4614-b3b3-0421728278b0; avatar=http://thirdqq.qlogo.cn/qqapp/101406359/0FFC013FFD95202344530B9D4235B60C/100; nickname=MRB; partner_uid=0FFC013FFD95202344530B9D4235B60C; udb_biztoken=AQBnd-HaV-zvJXtAx79cHbIxpDfHp9g8DaHNYYcQIRpcrbHB3F3agU6aIj2ZwEGIP46b-XWTvXjaXeClDeUZtTmB2enfgBHkorYJ5vBxa-7dMWremstHAg3zBbgJMDA_u4sGS3pMEAc4cewYY06MSGFUfDhHNQPY8NrFd3bLRTpJTuIp0fU5qZarO6fDekwGbAJxTdzs5MWA0dehg8-Uz8Nv1_FCki2G4pXqOXpBXUagzC38K-wIV2RTVAfwciDsUU-OPsSr6xj5_HrAWtP79RFtFD0KG1y8sszrDI8iambB4ng6QdWYEz2-PzUN8kGj_K3rg-_KNs9YUNCnadXHLYIO; udb_openid=0FFC013FFD95202344530B9D4235B60C; udb_origin=100; udb_other=%7B%22lt%22%3A%221538967177826%22%2C%22isRem%22%3A%221%22%7D; udb_passport=newqq_a1j_vzisk; udb_status=0; udb_uid=1850294048; udb_version=1.0; username=newqq_a1j_vzisk; yyuid=1850294048; lType=qq; Hm_lpvt_51700b6c722f5bb4cf39906a596ea41f=1538967178; h_unt=1538967179; __yaoldyyuid=1850294048; _yasids=__rootsid%3DC82BA4A6C3100001A0871400CE58E670")
              .build();
            Response response = client.newCall(request).execute();
            byte[] data = response.body().bytes();
            System.out.println(new String(data));
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(bis);
            byte[] buf = new byte[1024];
            int num = -1;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                    bos.write(buf, 0, num);
            }
            gzip.close();
            bis.close();
            byte[] ret = bos.toByteArray();
            bos.flush();
            bos.close();
            System.out.println(new String(ret));
    }
    
    @Test
    public void jsonAnalysis()throws Exception{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
              .url("http://www.huya.com/cache.php?m=LiveList&do=getLiveListByPage&tagAll=0&page=4")
              .get()
              .build();

            try {
                    Response response = client.newCall(request).execute();
                    jsonArrays= response.body().string().toCharArray();
                    transMapToBean(parseJson(jsonArrays,0,jsonArrays.length),"com.test","TestJson");
                    
                    //System.out.println(response.body().string());
            } catch (IOException e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
            }
    }
    
    @Test
    public void testParseJson(){
            String json = "{\"orderNumber\":\"30032151\",\"type\":\"buy\",\"rate\":21367.521367521,\"amount\":\"0.0936\",\"total\":\"2000\",\"initialRate\":21367.521367521,\"initialAmount\":\"0.0936\",\"filledRate\":0,\"filledAmount\":0,\"currencyPair\":\"eth_btc\",\"timestamp\":\"1407828913\",\"status\":\"open\"}";//"{\"key1\":\"val,:ue1\",\"key2\":1,\"key3\":\"200\"}";
            System.out.println(json);	
            jsonArrays = json.toCharArray();
            transMapToBean(parseJson(jsonArrays,0,jsonArrays.length),"com.test","TestJson");
    }
    
    public Map<String,JsonObject> parseJson(char[] jsonChars,int start,int len){
		Map<String,JsonObject> parseMap = new HashMap();
		int lBrackNum =0;
		int lmBrackNum = 0;
		boolean needQuo = false;
		boolean keyMode = true; //true为找key阶段 false为找value阶段
		boolean lBrackQuoMode = true;
		int lastIndex =start+1; //记录上次操作位置
		int newIndex =0;
		String tempKey=null;
		String tempValue=null;
		JsonType jsonType = JsonType.StringC; //默认字符串类型
		for(int i=start;i<start+len;i++){
			//进入数组对象逻辑
			if(lmBrackNum>0){
				switch(jsonChars[i]){
					case '[':
						if(lBrackQuoMode){
							lmBrackNum++;
						}
						break;
					case ']':
						if(lBrackQuoMode){
							lmBrackNum--;
						}
						break;
					case '"':
						if(jsonChars[i-1]!='\\'){
							lBrackQuoMode=!lBrackQuoMode;
						}
						break;
				}
				continue;
			}
			if(lBrackNum>1){
				switch(jsonChars[i]){
					case '{':
						if(lBrackQuoMode){
							lBrackNum++;
						}
						break;
					case '}':
						if(lBrackQuoMode){
							lBrackNum--;
						}
						break;
					case '"':
						if(jsonChars[i-1]!='\\'){
							lBrackQuoMode=!lBrackQuoMode;
						}
						break;
				}
				continue;
			}
			if(!needQuo){
				switch(jsonChars[i]){
					case '{':
						jsonType = JsonType.JsonC;
						lBrackNum++;
						break;
					case '}':
						lBrackNum--;
						//如果有括号数为0
						if(lBrackNum==0){
							newIndex=i;
							keyMode=true;
						}
						break;
					case '[':  //如果不是在引号中，左中括号数++
						jsonType = JsonType.JsonCArr;
						lmBrackNum++;
						break;
					case ']':
						lmBrackNum--;
						break;
					case '"':
						if(jsonChars[i-1]!='\\'){
							needQuo=!needQuo;
						}
						if(!keyMode){
							jsonType = JsonType.StringC; //默认字符串类型
						}
						break;
					case ':': 
						keyMode=false;
						newIndex=i;
						break;
					case ',':
							keyMode=true;
							newIndex=i;
					default: 
						int v = jsonChars[i]-'0';
						if(v<=9&&v>=0){
							jsonType = JsonType.NumC;
						}
						break;
					
				}
			}else{
				switch(jsonChars[i]){
					case '"':
						if(jsonChars[i-1]!='\\'){
							needQuo=!needQuo;
						}
					break;
				}
			}
			
			if(newIndex>lastIndex){
				int count = newIndex-lastIndex;
				int dcount = 1;
				//去前空格
				while(jsonChars[lastIndex]==' '||jsonChars[lastIndex]=='\t'
						||jsonChars[lastIndex]=='\r'||jsonChars[lastIndex]=='\n'
							||jsonChars[lastIndex]=='\f'||jsonChars[lastIndex]=='\b'){
					lastIndex++;
					count--;
				}
				//去后空格
				while(jsonChars[lastIndex]==' '||jsonChars[lastIndex]=='\t'
						||jsonChars[lastIndex]=='\r'||jsonChars[lastIndex]=='\n'
							||jsonChars[lastIndex]=='\f'||jsonChars[lastIndex]=='\b'){
					count--;
				}
				//去双引号
				if(jsonChars[lastIndex]=='"'){
					lastIndex++;
					count = count-2;
				}
				if(!keyMode){
					tempKey = String.valueOf(jsonChars,lastIndex,count);
					lastIndex=newIndex+1;
				}else{
					//tempValue =  String.valueOf(jsonChars,lastIndex,count).trim();
					parseMap.put(tempKey,new JsonObject(lastIndex,count,jsonType));
					lastIndex=newIndex+1;
				}
			}
		}
		
		return parseMap;
		
	}
	
	static class JsonObject{
		int start;
		int count;
		JsonType type;//NumC , StringC ,JsonC,JsonCArr
		public JsonObject(int start,int count,JsonType type){
			this.start = start;
			this.count = count;
			this.type = type;
		}
		@Override
		public String toString(){
			return type.name+","+start+","+count;
		}
	}
	
	static enum JsonType{
		NumC("num"),
                IntC("Integer"),
                LongC("Long"),
                DoubleC("Double"),
                BoolC("Boolean"),
		StringC("String"),
		JsonC("Json"),
		JsonCArr("List<Type>");
		String name;
		private JsonType(String name){
			this.name = name;
		}
		public String toString(){
			return name;
		}
	}


    public void transMapToBean(Map<String,JsonObject> parseMap,String packageName,String className){
            BeanTemplate beanTemplate = new BeanTemplate();

           
            List<String> importList = new ArrayList();
            List<Field> fieldList = new ArrayList();
            beanTemplate.setClassName(className);
            beanTemplate.setPackageName(packageName);
            beanTemplate.setFieldList(fieldList);
            beanTemplate.setImportList(importList);
            for(Map.Entry<String,JsonObject> entry:parseMap.entrySet()){
                JsonObject jObj = entry.getValue();
                switch(jObj.type){
                    case NumC:
                        fieldList.add(numField(entry.getKey(),entry.getValue()));
                        break;
                    case StringC:
                        fieldList.add(strField(entry.getKey(),entry.getValue()));
                        break;
                    case JsonC:
                        Field field = new Field();
                        String subClassName = new StringBuilder(className).append(entry.getKey()).toString();
                        field.setAlias(entry.getKey());
                        field.setName(ConverUtil.hungToChangeCame(entry.getKey()));
                        field.setComment(properties.getProperty(entry.getKey()));
                        field.setType(subClassName);
                        importList.add(new StringBuilder(packageName)
                                        .append('.').append(subClassName).append(';').toString());
                        fieldList.add(field);
                        transMapToBean(parseJson(jsonArrays, jObj.start, jObj.count),packageName,subClassName);
                        break;
                    case JsonCArr:
                        Field field2 = new Field();
                        String subClassName2 = new StringBuilder(className).append(entry.getKey()).toString();
                        field2.setAlias(entry.getKey());
                        field2.setName(ConverUtil.hungToChangeCame(entry.getKey()));
                        field2.setComment(properties.getProperty(entry.getKey()));
                        field2.setType(JsonType.JsonCArr.name.replaceAll("Type", subClassName2));
                        importList.add(new StringBuilder(packageName)
                                        .append('.').append(subClassName2).append(';').toString());
                        importList.add("java.util.List;");
                        fieldList.add(field2);
                        jObj = jsonArrOne(jsonArrays,jObj);
                        if(jObj.count>1){
                            transMapToBean(parseJson(jsonArrays, jObj.start, jObj.count),packageName,subClassName2);
                        }
                        break;
                }
            }
            produceJava(className,beanTemplate);
        }
        /**
         * 生成类文件 *.java
         * @param className
         * @param beanTemplate
         */
        public void produceJava(String className,BeanTemplate beanTemplate){
              try {
                Template temp = cfg.getTemplate(BEAN_FTL);
                String fileName = className.concat(JAVA_FIX);
                
                Map<String, Object> root = new HashMap<>();
                root.put("bean", beanTemplate);
                //生成Java文件
//                OutputStream fos = new FileOutputStream(new File(TEMP_DIR,fileName));
//                Writer out = new OutputStreamWriter(fos,ENCODE);
                Writer out = new OutputStreamWriter(System.out);
                temp.process(root, out);

//                fos.flush();
//                fos.close();
            } catch (IOException|TemplateException ex ) {
                ex.printStackTrace();
            }
            
        }
        
        private Field strField(String name,JsonObject jsonObject){
             Field fieldType = new Field();
             fieldType.setAlias(name);
             fieldType.setName(ConverUtil.hungToChangeCame(name));
             fieldType.setComment(properties.getProperty(name));
             int lastIndex = jsonObject.start;
//             while(jsonArrays[lastIndex]==' '||jsonArrays[lastIndex]=='\t'
//                            ||jsonArrays[lastIndex]=='\r'||jsonArrays[lastIndex]=='\n'
//                                    ||jsonArrays[lastIndex]=='\f'||jsonArrays[lastIndex]=='\b'){
//                    lastIndex++;
//            }
             if(jsonArrays[lastIndex]=='t'||jsonArrays[lastIndex]=='T'
                     ||jsonArrays[lastIndex]=='f'||jsonArrays[lastIndex]=='F'){
                  fieldType.setType(JsonType.BoolC.name);
             }else{
                 fieldType.setType(JsonType.StringC.name);
             }
             return fieldType;
        }
        
        
        private Field numField(String name,JsonObject jsonObject){
            Field fieldType = new Field();
            fieldType.setAlias(name);
            fieldType.setName(ConverUtil.hungToChangeCame(name));
            fieldType.setComment(properties.getProperty(name));
            for(int i=0;i<jsonObject.count;i++){
                if(jsonArrays[jsonObject.start+i]=='.'){
                    fieldType.setType(JsonType.DoubleC.name);
                    return fieldType;
                }
            }
            if(jsonObject.count>=8){
                fieldType.setType(JsonType.LongC.name);
            }else{
                fieldType.setType(JsonType.IntC.name);
            }
            return fieldType;
        }
        
        private JsonObject jsonArrOne(char[] jsonArrays,JsonObject jsonObject){
            JsonObject jResult = new JsonObject(0, 0, JsonType.JsonC);
            int lBrackNum =0;
            boolean lBrackQuoMode = true;
            jResult.start = jsonObject.start+1;
            int i=jResult.start;
            int len = jsonObject.start+jsonObject.count;
            for(;i<=len;i++){
                if(lBrackNum>0){
                        switch(jsonArrays[i]){
                                case '{':
                                        if(lBrackQuoMode){
                                                lBrackNum++;
                                        }
                                        break;
                                case '}':
                                        if(lBrackQuoMode){
                                                lBrackNum--;
                                        }
                                        break;
                                case '"':
                                        if(jsonArrays[i-1]!='\\'){
                                                lBrackQuoMode=!lBrackQuoMode;
                                        }
                                        break;
                        }
                        continue;
                }else{
                    switch(jsonArrays[i]){
                                case '{':
                                        if(lBrackQuoMode){
                                                lBrackNum++;
                                        }
                                        break;
                    }
                    if(lBrackNum==0){
                        jResult.count= i-jResult.start;
                        break;
                    }
                }
            }
            
            return jResult;
        }
}
