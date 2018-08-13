package com.zmm.nettyserverpc_zbd.client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/8/11
 * Time:上午9:18
 */

public class UDPSocketClient3 {

    public static void main(String[] args) {
        while (true){
            try {
                Thread.sleep(100);
                byte[] bytes = receiveUDPData();

                System.out.println("获取服务端数据："+ Arrays.toString(bytes));


                if(bytes[0] == 100 & bytes[1] == 101){
                    receiveNormalData(bytes);
                }else if(bytes[0] == 105 & bytes[1] == 106){
                    receivePauseData(bytes);
                }else if(bytes[0] == 102 & bytes[1] == 103){
                    receiveUnPauseData(bytes);
                }else if(bytes[0] == 110 & bytes[1] == 111){
                    receiveEndData(bytes);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private static void receiveNormalData(byte[] bytes) {
        String type = "被动模式";
        int flag = bytes[2];

        if(flag == 0){
            type = "主动模式";
        }else {
            type = "被动模式";
        }

        byte[] calBytes = new byte[8];
        byte[] milBytes = new byte[8];
        byte[] leftBytes = new byte[8];
        byte[] timeBytes = new byte[8];
        byte[] idStr = new byte[12];

        calBytes[0] = bytes[8];
        calBytes[1] = bytes[9];
        calBytes[2] = bytes[10];
        calBytes[3] = bytes[11];

        milBytes[0] = bytes[12];
        milBytes[1] = bytes[13];
        milBytes[2] = bytes[14];
        milBytes[3] = bytes[15];

        leftBytes[0] = bytes[16];
        leftBytes[1] = bytes[17];
        leftBytes[2] = bytes[18];
        leftBytes[3] = bytes[19];

        timeBytes[0] = bytes[20];
        timeBytes[1] = bytes[21];
        timeBytes[2] = bytes[22];
        timeBytes[3] = bytes[23];

        idStr[0] = bytes[24];
        idStr[1] = bytes[25];
        idStr[2] = bytes[26];
        idStr[3] = bytes[27];
        idStr[4] = bytes[28];
        idStr[5] = bytes[29];
        idStr[6] = bytes[30];
        idStr[7] = bytes[31];
        idStr[8] = bytes[32];
        idStr[9] = bytes[33];
        idStr[10] = bytes[34];
        idStr[11] = bytes[35];

        String id = byteArrayToStr(idStr);


        int speed = bytes[3];
        int resistance = bytes[4];
        int spasm = bytes[5];
        int mSpasmLevel = bytes[6];
        int offset = bytes[7];

        int cal = byteToInt(calBytes);
        int mil = byteToInt(milBytes);
        int left = byteToInt(leftBytes);
        int time = byteToInt(timeBytes);
        System.out.println(type+"cal = "+cal+",mil = "+mil+",left = "+left+",time = "+time+",id = "+id);
        System.out.println(type+":: "+id+",速度："+speed+"，阻力： "+resistance+"，痉挛： "+spasm+"，痉挛等级： "+mSpasmLevel+"，偏移： "+offset);

    }

    private static void receivePauseData(byte[] bytes) {
        System.out.println("---暂停---");
    }

    private static void receiveUnPauseData(byte[] bytes) {
        System.out.println("---取消暂停---");
    }

    private static void receiveEndData(byte[] bytes) {
        System.out.println("---结束---");
    }

    private static byte[] receiveUDPData() {
        DatagramSocket ds;
        byte[] data = new byte[36];
        try {
            ds = new DatagramSocket(12346);
            // 创建数据包
            byte[] bys = new byte[36];
            DatagramPacket dp = new DatagramPacket(bys, bys.length);
            ds.receive(dp);
            data = dp.getData();
            ds.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }

    /**
     * 字节数组到int的转换.
     */
    public static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[0] & 0xff;// 最低位
        int s1 = b[1] & 0xff;
        int s2 = b[2] & 0xff;
        int s3 = b[3] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;
    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }

    private static void start()
    {
        System.out.println("----");
    }

    private int a;
    private int b;
    private int c;

    private void eat()
    {
        int t;
        if(a<b)
        {
            t = b;
            b = a;
            a = t;
        }
    }

}
