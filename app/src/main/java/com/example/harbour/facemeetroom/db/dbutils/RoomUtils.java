package com.example.harbour.facemeetroom.db.dbutils;

import android.content.Context;
import android.util.Log;

import com.example.harbour.facemeetroom.db.entity.RecommendRoom;
import com.example.harbour.facemeetroom.db.entity.Room;
import com.example.harbour.facemeetroom.db.entity.RoomReserveUp;

import java.util.ArrayList;
import java.util.List;

/**
 * 完成对某一张表的具体操作，ORM 操作的是对象，Room
 */
public class RoomUtils {
    private DaoManager manager;

    public RoomUtils(Context context) {
        manager = DaoManager.getInstance();
        manager.init(context);
    }

    /**
     * 完成对数据库中room 表的插入操作
     * @param room
     * @return
     */
    public boolean insertRoom(Room room) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(room) != -1 ? true: false;
        return flag;
    }

    /**
     * 完成对数据库中roomReserveUp 表的插入操作
     * @param roomReserveUp
     * @return
     */
    public boolean insertRoomReserve(RoomReserveUp roomReserveUp) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(roomReserveUp) != -1 ? true: false;
        return flag;
    }

    /**
     * 插入多条记录，需要开辟新的线程
     * @param dataList
     * @return
     */
    public boolean insertMultRoomReserve(final List<RoomReserveUp> dataList){
        boolean flag = false;

        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for(RoomReserveUp room:dataList) {
                        manager.getDaoSession().insertOrReplace(room);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 插入多条记录，需要开辟新的线程
     * @param dataList
     * @return
     */
    public boolean insertMultRoom(final List<Room> dataList){
        boolean flag = false;

        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for(Room room:dataList) {
                        manager.getDaoSession().insertOrReplace(room);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 插入多条记录，需要开辟新的线程
     * @param recommendRooms
     * @return
     */
    public boolean insertMulRecommendRoom(final List<RecommendRoom> recommendRooms){
        boolean flag = false;

        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for(RecommendRoom room:recommendRooms) {
                        manager.getDaoSession().insertOrReplace(room);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现删除
     * @return
     */
    public boolean deleteRoom(){
        boolean flag =false;
        try{
            //删除所有的记录
            manager.getDaoSession().deleteAll(Room.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现删除
     * @return
     */
    public boolean deleteRecomendRoom(){
        boolean flag =false;
        try{
            //删除所有的记录
            manager.getDaoSession().deleteAll(RecommendRoom.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现删除
     * @return
     */
    public boolean deleteMyRoom(){
        boolean flag =false;
        try{
            //删除所有的记录
            manager.getDaoSession().deleteAll(RoomReserveUp.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 返回多条记录
     * @return
     */
    public ArrayList<Room> listAll(){
        List<Room> dataList= manager.getDaoSession().loadAll(Room.class);
        ArrayList<Room> dataArrayList = new ArrayList<>();
        for(Room room:dataList){
            dataArrayList.add(room);
        }
        return dataArrayList;
    }

    /**
     * 返回多条记录
     * @return
     */
    public ArrayList<RoomReserveUp> listAllmyRoom(){
        List<RoomReserveUp> dataList= manager.getDaoSession().loadAll(RoomReserveUp.class);
        ArrayList<RoomReserveUp> dataArrayList = new ArrayList<>();
        for(RoomReserveUp room:dataList){
            dataArrayList.add(room);
        }
        return dataArrayList;
    }

    /**
     * 返回多条记录
     * @return
     */
    public ArrayList<RecommendRoom> listAllRecommendRoom(){
        List<RecommendRoom> dataList= manager.getDaoSession().loadAll(RecommendRoom.class);
        ArrayList<RecommendRoom> dataArrayList = new ArrayList<>();
        for(RecommendRoom room:dataList){
            dataArrayList.add(room);
        }
        return dataArrayList;
    }

   public void close(){
        manager.closeConnection();
   }
}


















