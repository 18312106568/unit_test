package com.creditcloud.jpa.unit_test.utils;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.springframework.util.StringUtils;

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
            String heads = "accept: */*\n" +
                    "accept-encoding: gzip, deflate, br\n" +
                    "accept-language: zh-CN,zh;q=0.9,en;q=0.8\n" +
                    "cache-control: no-cache\n" +
                    "content-length: 135\n" +
                    "content-type: application/x-www-form-urlencoded; charset=UTF-8\n" +
                    "cookie: BAIDUID=FCB94DB885A48050416D64B2BC80D53D:FG=1; BIDUPSID=FCB94DB885A48050416D64B2BC80D53D; PSTM=1547449951; REALTIME_TRANS_SWITCH=1; FANYI_WORD_SWITCH=1; HISTORY_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; BDUSS=Qzb3Q1SmxIMHB6N2ZCTk1-LU5qa1ZkOFd3eWNBeHJDQ0ZOUktWYURyYW1mN3BjQVFBQUFBJCQAAAAAAAAAAAEAAADmyHE50dXQobymAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAKbyklym8pJcRV; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1557297824,1557994668,1558403021,1558494919; sideAdClose=18044; BDSFRCVID=N3POJeC626vdWRr9nh45UCuIQB_vy33TH6aobt7wzt9apyV8lxbiEG0PjM8g0KubvnQHogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0P3J; H_BDCLCKID_SF=tJkD_I_hJKt3qn7I5KToh4Athxob2bbXHDo-LIvLtDncOR5JhfA-3R-e04C82tkJKHTmLlT1alvvhb3O3M7ShbKP3a7lbtTx-HTDKfQF5l8-sq0x0bOte-bQypoa0q3TLDOMahkM5h7xOKQoQlPK5JkgMx6MqpQJQeQ-5KQN3KJmhpFuD6_Wj5b3Da8s-bbfHDn0WJ32KRROKROvhjRFDp8gyxomtjDqbDnUbt_XaJPMMf5J5frGLl4B2H-eLUkqKCOaLI35Mt-BHtJw367S2-DtQttjQPTufIkja-5tbJrGOb7TyU45bU47yaOR0q4Hb6b9BJcjfU5MSlcNLTjpQT8rDPCE5bj2qRu8VCIK3J; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=1449_21109_29135_29237_28518_29098_29134_28836_28585_26350; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1559796355; yjs_js_security_passport=40671ad420456fc24920bbfacab47fc4a72ac453_1559796426_js; delPer=0; PSINO=7; locale=zh; to_lang_often=%5B%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%5D; from_lang_often=%5B%7B%22value%22%3A%22it%22%2C%22text%22%3A%22%u610F%u5927%u5229%u8BED%22%7D%2C%7B%22value%22%3A%22en%22%2C%22text%22%3A%22%u82F1%u8BED%22%7D%2C%7B%22value%22%3A%22zh%22%2C%22text%22%3A%22%u4E2D%u6587%22%7D%5D\n" +
                    "origin: https://fanyi.baidu.com\n" +
                    "pragma: no-cache\n" +
                    "referer: https://fanyi.baidu.com/\n" +
                    "user-agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36\n" +
                    "x-requested-with: XMLHttpRequest";
            String url = "https://fanyi.baidu.com/v2transapi";
            String sign = TranslateUtils.getSign(queryName);
            String params = String.format("from=zh&to=en&query=%s&transtype=realtime&simple_means_flag=3&sign=%s&token=2e3030089a45be932f9060f4a8c53950", URLEncoder.encode(queryName, "UTF-8"), sign);
            String result = HttpUtil.doPost(url, heads, params, true);
            if(StringUtils.isEmpty(result)){
                System.out.println("request baidufanyi error!");
                return "";
            }
            Gson gson = new Gson();
            Map<String, LinkedTreeMap> resultMap = gson.fromJson(result, Map.class);
            String transDst = ((LinkedTreeMap) ((List) resultMap.get("trans_result").get("data")).get(0)).get("dst").toString();
            return transDst;
        }catch(Exception ex){
            System.out.println(String.format("translate the world:%s error",queryName));
            ex.printStackTrace();
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
