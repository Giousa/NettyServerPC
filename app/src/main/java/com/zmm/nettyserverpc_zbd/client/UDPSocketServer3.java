package com.zmm.nettyserverpc_zbd.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/8/11
 * Time:上午9:18
 */

public class UDPSocketServer3 {

    public static void main(String[] args) {
        DatagramSocket ds;
        DatagramPacket dp = null;
        byte[] bys;
        try {

            ds = new DatagramSocket();
            bys = new byte[]{105, 106, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0,
                    0, 0, 0, 0};
//            System.out.println("Server发送数据：" + Arrays.toString(bys));
            dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("127.0.0.1"), 12346);
            ds.send(dp);
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
