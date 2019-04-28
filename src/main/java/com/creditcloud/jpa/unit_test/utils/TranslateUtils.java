package com.creditcloud.jpa.unit_test.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class TranslateUtils {

    private static ScriptEngine scriptEngine;

    public static ScriptEngine getEngine() throws ScriptException {
        if(scriptEngine == null){
            scriptEngine =  new ScriptEngineManager().getEngineByExtension("js");
            scriptEngine.eval(BAIDUFANYI_SIGN_SCRIPT);
        }
        return scriptEngine;
    }

    public static String getSign(String params) throws  ScriptException {
        return (String)getEngine().eval(String.format("sign('%s')",params));
    }

    public static String translate(String queryName){
        try {
            String heads = "Accept: */*\n" +
                    "Accept-Encoding: gzip, deflate, br\n" +
                    "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                    "Connection: keep-alive\n" +
                    "Content-Length: 135\n" +
                    "Content-Type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                    "Cookie: BAIDUID=FCB94DB885A48050416D64B2BC80D53D:FG=1; BIDUPSID=FCB94DB885A48050416D64B2BC80D53D; PSTM=1547449951; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; BDUSS=Qzb3Q1SmxIMHB6N2ZCTk1-LU5qa1ZkOFd3eWNBeHJDQ0ZOUktWYURyYW1mN3BjQVFBQUFBJCQAAAAAAAAAAAEAAADmyHE50dXQobymAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKbyklym8pJcRV; BDSFRCVID=0KKOJeC624GIAD69R9w-UCuILZ_LPOJTH6aIedtMVlBhEMemOxpDEG0PDx8g0KubzID5ogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJIeoIIatI_3fP36q4QV-JIehxQfbK62aJ0jahvvWJ5TMC_mQUrNQ55-0GrfelQmbm5rhf71Lp7kShPC-frqbb0I3xvGaUQu5Cj9XPJg3l02Vhb9e-t2ynLV24rRt4RMW20e0h7mWIbUsxA45J7cM4IseboJLfT-0bc4KKJxthF0HPonHj_-e5oW3f; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1554791761,1555381331,1555482509,1555576313; to_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1449_21109_28775_28724_28836_28585_26350_28603_28892; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; delPer=0; PSINO=7; locale=zh; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1555990862; from_lang_often=%5B%7B%22value%22%3A%22est%22%2C%22text%22%3A%22%u7231%u6C99%u5C3C%u4E9A%u8BED%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D\n" +
                    "Host: fanyi.baidu.com\n" +
                    "Origin: https://fanyi.baidu.com\n" +
                    "Referer: https://fanyi.baidu.com/\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.103 Safari/537.36\n" +
                    "X-Requested-With: XMLHttpRequest";
            String url = "https://fanyi.baidu.com/v2transapi";
            String sign = TranslateUtils.getSign(queryName);
            String params = String.format("from=zh&to=en&query=%s&transtype=realtime&simple_means_flag=3&sign=%s&token=2e3030089a45be932f9060f4a8c53950", URLEncoder.encode(queryName, "UTF-8"), sign);
            String result = HttpUtil.doPost(url, heads, params, true);
            Gson gson = new Gson();
            Map<String, LinkedTreeMap> resultMap = gson.fromJson(result, Map.class);
            String transDst = ((LinkedTreeMap) ((List) resultMap.get("trans_result").get("data")).get(0)).get("dst").toString();
            return transDst;
        }catch(Exception ex){
            System.out.println(String.format("translate the world:%s error",queryName));
        }
        return "";
    }

    private static String BAIDUFANYI_SIGN_SCRIPT = "var i =null;\n" +
            "function sign(r) {\n" +
            "    var o = r.match(/[\\uD800-\\uDBFF][\\uDC00-\\uDFFF]/g);\n" +
            "    if (null === o) {\n" +
            "        var t = r.length;\n" +
            "        t > 30 && (r = \"\" + r.substr(0, 10) + r.substr(Math.floor(t / 2) - 5, 10) + r.substr(-10, 10))\n" +
            "    } else {\n" +
            "        for (var e = r.split(/[\\uD800-\\uDBFF][\\uDC00-\\uDFFF]/), C = 0, h = e.length, f = []; h > C; C++)\n" +
            "            \"\" !== e[C] && f.push.apply(f, a(e[C].split(\"\"))),\n" +
            "            C !== h - 1 && f.push(o[C]);\n" +
            "        var g = f.length;\n" +
            "        g > 30 && (r = f.slice(0, 10).join(\"\") + f.slice(Math.floor(g / 2) - 5, Math.floor(g / 2) + 5).join(\"\") + f.slice(-10).join(\"\"))\n" +
            "    }\n" +
            "    var u = void 0\n" +
            "        , l = \"\" + String.fromCharCode(103) + String.fromCharCode(116) + String.fromCharCode(107);\n" +
            "    u = null !== i ? i : (i = \"320305.131321201\" || \"\") || \"\";\n" +
            "    for (var d = u.split(\".\"), m = Number(d[0]) || 0, s = Number(d[1]) || 0, S = [], c = 0, v = 0; v < r.length; v++) {\n" +
            "        var A = r.charCodeAt(v);\n" +
            "        128 > A ? S[c++] = A : (2048 > A ? S[c++] = A >> 6 | 192 : (55296 === (64512 & A) && v + 1 < r.length && 56320 === (64512 & r.charCodeAt(v + 1)) ? (A = 65536 + ((1023 & A) << 10) + (1023 & r.charCodeAt(++v)),\n" +
            "            S[c++] = A >> 18 | 240,\n" +
            "            S[c++] = A >> 12 & 63 | 128) : S[c++] = A >> 12 | 224,\n" +
            "            S[c++] = A >> 6 & 63 | 128),\n" +
            "            S[c++] = 63 & A | 128)\n" +
            "    }\n" +
            "    for (var p = m, F = \"\" + String.fromCharCode(43) + String.fromCharCode(45) + String.fromCharCode(97) + (\"\" + String.fromCharCode(94) + String.fromCharCode(43) + String.fromCharCode(54)), D = \"\" + String.fromCharCode(43) + String.fromCharCode(45) + String.fromCharCode(51) + (\"\" + String.fromCharCode(94) + String.fromCharCode(43) + String.fromCharCode(98)) + (\"\" + String.fromCharCode(43) + String.fromCharCode(45) + String.fromCharCode(102)), b = 0; b < S.length; b++)\n" +
            "        p += S[b],\n" +
            "            p = n(p, F);\n" +
            "    return p = n(p, D),\n" +
            "        p ^= s,\n" +
            "    0 > p && (p = (2147483647 & p) + 2147483648),p %= 1e6,\n" +
            "    p.toString() + \".\" + (p ^ m)\n" +
            "}\n" +
            "function n(r, o) {\n" +
            "    for (var t = 0; t < o.length - 2; t += 3) {\n" +
            "        var a = o.charAt(t + 2);\n" +
            "        a = a >= \"a\" ? a.charCodeAt(0) - 87 : Number(a),\n" +
            "            a = \"+\" === o.charAt(t + 1) ? r >>> a : r << a,\n" +
            "            r = \"+\" === o.charAt(t) ? r + a & 4294967295 : r ^ a\n" +
            "    }\n" +
            "    return r\n" +
            "}";
}
