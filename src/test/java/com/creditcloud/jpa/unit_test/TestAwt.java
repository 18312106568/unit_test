package com.creditcloud.jpa.unit_test;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestAwt {

    static final String SCREEM_IMG_FORMAT = "png";

    /**
     * 截屏
     * @throws AWTException
     */
    @Test
    public void shotJpg() throws AWTException {
        Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
        Robot robot = new Robot();
        int width = (int)screensize.getWidth();
        int height = (int)screensize.getHeight();
        BufferedImage screenshot=robot.createScreenCapture(
                new Rectangle(0,0,width,height));
        String currentTemp =String.valueOf(System.currentTimeMillis()) ;
        File file = new File("D://"+currentTemp+".png");
        try {
            ImageIO.write(screenshot,"png",file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试粘贴板
     */
    @Test
    public void testClipboard(){
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = new StringSelection("你好吗,123？");
        clip.setContents(transferable,null);
    }
}
