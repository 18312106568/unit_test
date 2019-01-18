package com.creditcloud.jpa.unit_test;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import org.junit.Test;


import jpcap.*;

import java.io.IOException;

public class TestJpcap {

    final static String BAIDU_IP = "14.215.177.39";

    final static String ONLINE_DOWNLOAD = "183.2.192.198";

    @Test
    public void testDevicesLen() throws IOException {
        System.out.println(System.getProperty("java.library.path"));
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        for(NetworkInterface device : devices){
            System.out.println(device.name+device.description);
        }
        if(devices.length<2){
            System.out.println("找不到网卡");
        }
        JpcapCaptor captor = JpcapCaptor.openDevice(devices[0], 65535,
                false, 20);
        captor.loopPacket(-1,new Receiver());
    }

    static class Receiver implements  PacketReceiver {
        /*实例receivePacket方法*/
        public void receivePacket(Packet packet) {
            /*进行简单的处理 只获取tcp*/
            if(TCPPacket.class.equals(packet.getClass())){
                TCPPacket tcpPacket = (TCPPacket)packet;
                if(ONLINE_DOWNLOAD.equals(tcpPacket.dst_ip.getHostAddress().trim())
                        ||ONLINE_DOWNLOAD.equals(tcpPacket.src_ip.getHostAddress().trim())){
                    System.out.println(new String(tcpPacket.data));
                }
            }

        }
    }
}