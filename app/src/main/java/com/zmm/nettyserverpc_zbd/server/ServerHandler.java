package com.zmm.nettyserverpc_zbd.server;

import com.alibaba.fastjson.JSON;
import com.zmm.nettyserverpc_zbd.model.ActiveModel;
import com.zmm.nettyserverpc_zbd.model.PassiveModel;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.util.CharsetUtil;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerReadListener mServerReadListener;

    public interface ServerReadListener{
        void onServerReadListener(String msg);
    }

    public void setServerReadListener(ServerReadListener serverReadListener) {
        mServerReadListener = serverReadListener;
    }
    private int mPassiveMil;
    private int mActiveMil;
    private byte mSpasmLevel = 1;
    private boolean isPause = false;
    private long mPreTime;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {

        if (msg instanceof HttpContent) {

            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String data = buf.toString(CharsetUtil.UTF_8).substring(12);

//            System.out.println("客户端消息 = "+data);
//            parseJson(data);

            if(mServerReadListener != null){
                mServerReadListener.onServerReadListener(data);
            }
            buf.release();

        }
    }

//    private void parseJson(String data) {
//
//        if (data.contains("maxSpeed") || data.contains("maxResistance")) {
//            gameOver();
//        } else if (data.contains("passiveMileage")) {
//            passiveModel(data);
//            if(!isPause){
//                sendUDPDataUnPause();
//            }
//        } else if (data.contains("activeMileage")) {
//            activeModel(data);
//            if(!isPause){
//                sendUDPDataUnPause();
//            }
//        } else if (data.contains("beginTime")) {
//            isPause = true;
//            gamePause();
//        }
//    }
//
//    private void passiveModel(String msg) {
//        if (msg.contains("spasmTimes") && msg.contains("spasmLevel") && msg.contains("passiveMileage") &&
//                msg.contains("calories") && msg.contains("curDirection") && msg.contains("curResistance") && msg.contains("curSpeed")) {
//            PassiveModel passiveModel = JSON.parseObject(msg, PassiveModel.class);
//            byte flag = 1;
//            byte speed = (byte) Integer.parseInt(passiveModel.getCurSpeed());
//            byte resistance = (byte) Integer.parseInt(passiveModel.getCurResistance());
//            byte spasm = (byte) Integer.parseInt(passiveModel.getSpasmTimes());
//            byte level = (byte) Integer.parseInt(passiveModel.getSpasmLevel());
//            int cal = (int) (Double.parseDouble(passiveModel.getCalories()) * 1000);
//            int mil = (int) (Double.parseDouble(passiveModel.getPassiveMileage()) * 1000);
//            byte[] id = strToByteArray(passiveModel.getLoginId());
//            if(speed < 0){
//                speed = (byte) (speed+128);
//            }
//            mPassiveMil = mil;
//            mSpasmLevel = level;
//            byte offset = 0;
//            int v = 5000;
//
//            isPause = false;
//            sendUDPData(flag, speed, resistance, spasm, offset, cal, mil, v,id);
//        }
//    }
//
//    private void activeModel(String msg) {
//        if (msg.contains("spasmTimes") && msg.contains("offset") && msg.contains("activeMileage") &&
//                msg.contains("calories") && msg.contains("curDirection") && msg.contains("curResistance") && msg.contains("curSpeed")) {
//            ActiveModel activeModel = JSON.parseObject(msg, ActiveModel.class);
//            byte flag = 0;
//            byte speed = (byte) Integer.parseInt(activeModel.getCurSpeed());
//            byte resistance = (byte) (Integer.parseInt(activeModel.getCurResistance())+1);
//            byte spasm = (byte) Integer.parseInt(activeModel.getSpasmTimes());
//            int cal = (int) (Double.parseDouble(activeModel.getCalories()) * 1000);
//            int mil = (int) (Double.parseDouble(activeModel.getActiveMileage()) * 1000);
//            byte[] id = strToByteArray(activeModel.getLoginId());
//            if(speed < 0){
//                speed = (byte) (speed+128);
//            }
//            mActiveMil = mil;
//            byte offset = Byte.parseByte(activeModel.getOffset());
//            int v = (15 - offset) * 10000 / 30;
//
//            isPause = false;
//            sendUDPData(flag, speed, resistance, spasm, offset, cal, mil, v,id);
//        }
//    }
//
//    private void gamePause() {
//        sendUDPDataPause();
//    }
//
//    private void gameOver() {
//        sendUDPDataEnd();
//    }
//
//    /**
//     * 发送主动或被动数据
//     * @param flag
//     * @param speed
//     * @param resistance
//     * @param spasm
//     * @param offset
//     * @param cal
//     * @param mil
//     * @param left
//     * @param id
//     */
//    private void sendUDPData(byte flag, byte speed, byte resistance, byte spasm, byte offset,
//                             int cal, int mil, int left,byte[] id) {
//
//        DatagramSocket ds;
//        DatagramPacket dp = null;
//        byte[] bys;
//        try {
//            ds = new DatagramSocket();
//            byte[] calBytes = intToByte(cal);
//            byte[] milBytes = intToByte(mPassiveMil + mActiveMil);
//            byte[] leftBytes = intToByte(left);
//            byte[] time = intToByte(0);
//            bys = new byte[]{100, 101, flag, speed, resistance, spasm, mSpasmLevel, offset,
//                    calBytes[0], calBytes[1], calBytes[2], calBytes[3],
//                    milBytes[0], milBytes[1], milBytes[2], milBytes[3],
//                    leftBytes[0], leftBytes[1], leftBytes[2], leftBytes[3],
//                    time[0], time[1], time[2], time[3],id[0],id[1],id[2],id[3],
//                    id[4],id[5],id[6],id[7],id[8],id[9],id[10],id[11]};
////            System.out.println("Server发送数据：" + Arrays.toString(bys));
//            dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("127.0.0.1"), 12346);
//            ds.send(dp);
//            ds.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 暂停
//     */
//    private void sendUDPDataPause() {
//        DatagramSocket ds;
//        DatagramPacket dp = null;
//        byte[] bys;
//        try {
//
//            ds = new DatagramSocket();
//            bys = new byte[]{105, 106, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0};
////            System.out.println("Server发送数据：" + Arrays.toString(bys));
//            dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("127.0.0.1"), 12346);
//            ds.send(dp);
//            ds.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 取消暂停
//     */
//    private void sendUDPDataUnPause() {
//        DatagramSocket ds;
//        DatagramPacket dp = null;
//        byte[] bys;
//        try {
//
//            ds = new DatagramSocket();
//            bys = new byte[]{102, 103, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0};
////            System.out.println("Server发送UnPause数据：" + Arrays.toString(bys));
//            dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("127.0.0.1"), 12346);
//            ds.send(dp);
//            ds.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 结束
//     */
//    private void sendUDPDataEnd() {
//        DatagramSocket ds;
//        DatagramPacket dp = null;
//        byte[] bys;
//        try {
//            ds = new DatagramSocket();
//            bys = new byte[]{110, 111, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0, 0,
//                    0, 0, 0, 0};
//            dp = new DatagramPacket(bys, bys.length, InetAddress.getByName("127.0.0.1"), 12346);
//            ds.send(dp);
//            ds.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * int到字节数组的转换.
//     */
//    public byte[] intToByte(int number) {
//        int temp = number;
//        byte[] b = new byte[4];
//        for (int i = 0; i < b.length; i++) {
//            b[i] = new Integer(temp & 0xff).byteValue();// 将最低位保存在最低位
//            temp = temp >> 8;// 向右移8位
//        }
//        return b;
//    }
//
//
//    public static byte[] strToByteArray(String str) {
//        if (str == null) {
//            return null;
//        }
//        byte[] byteArray = str.getBytes();
//        return byteArray;
//    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(cause.getMessage());
        ctx.close();
    }

}