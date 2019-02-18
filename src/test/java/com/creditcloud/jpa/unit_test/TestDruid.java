package com.creditcloud.jpa.unit_test;

import org.junit.Test;
import com.alibaba.druid.filter.config.ConfigTools;

public class TestDruid {

    /**
     * 9      * 对文字进行解密
     * 10      * @throws Exception
     * 11
     */
    @Test
    public void testDecrypt() throws Exception {
        //解密
        String word = "MMUcTIwe+HMRBUYAVqdozWhxSB+rjY/HIBo08LsxlPJ/ocVXrvcKPwaMgWEKkApeDylU8RGPOAqsjsNy7Xg+fQ==";
        String decryptword = ConfigTools.decrypt(word);
        System.out.println(decryptword);
    }

    @Test
    public void testEncrypt() throws Exception {
        //加密
        String password = "root";
        String encryptword = ConfigTools.encrypt(password);
        System.out.println(encryptword);

    }
}
