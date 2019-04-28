package com.creditcloud.jpa.unit_test.js;

import com.creditcloud.jpa.unit_test.utils.TranslateUtils;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

public class TestJS {

    @Test
    public void testBaiduSiginJS() throws ScriptException, IOException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByExtension("js");
        engine.put("console", System.out);
        engine.eval("console.println('hello world')");
        String projectPath = System.getProperty("user.dir");
        String srcName = "src\\test\\java";
        String packagePath=getClass().getPackage().getName().replaceAll("\\.","\\\\");
        StringBuilder sb = new StringBuilder();
        sb.append(projectPath).append(File.separator)
                .append(srcName).append(File.separator).append(packagePath).append(File.separator).append("BaiduSign.js");
        InputStream cin = new FileInputStream(new File(sb.toString()));
        InputStreamReader reader = new InputStreamReader(cin);
        engine.eval(reader);
        String result = (String)engine.eval("sign('中文')");
        System.out.println(result);
    }

    @Test
    public void testTranslateUtils() throws  ScriptException {
        System.out.println(TranslateUtils.getSign("中文"));
    }
}
