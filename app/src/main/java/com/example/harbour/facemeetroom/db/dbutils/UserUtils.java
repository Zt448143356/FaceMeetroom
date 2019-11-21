package com.example.harbour.facemeetroom.db.dbutils;

import android.content.Context;

import com.example.harbour.facemeetroom.db.entity.EmailPassword;
import com.example.harbour.facemeetroom.db.entity.RoomTimes;
import com.example.harbour.facemeetroom.db.entity.User;

import java.util.List;

/**
 * 完成对User表的操作
 */
public class UserUtils {
    private DaoManager manager;

    public UserUtils(Context context){
        manager = DaoManager.getInstance();
        manager.init(context);
    }

    /**
     * 完成对数据库中user表的插入
     * @param user
     * @return
     */
    public boolean inserUser(User user){
        boolean flag = false;
        flag = manager.getDaoSession().insert(user) != -1 ? true: false;
        return flag;
    }

    /**
     * 完成对数据库中roomTime表的插入
     * @param roomTimes
     * @return
     */
    public boolean inserRoomRime(RoomTimes roomTimes){
        boolean flag = false;
        flag = manager.getDaoSession().insert(roomTimes) != -1 ? true: false;
        return flag;
    }


    /**
     * 完成对数据库中emailpassword表的插入
     * @param emailPassword
     * @return
     */
    public boolean inserEmailPassword(EmailPassword emailPassword){
        boolean flag = false;
        flag = manager.getDaoSession().insert(emailPassword) != -1 ? true: false;
        return flag;
    }

    /**
     * 实现全部user删除
     * @param
     * @return
     */
    public boolean deleteUser(){
        boolean flag =false;
        try{
            manager.getDaoSession().deleteAll(User.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现全部RoomTime删除
     * @param
     * @return
     */
    public boolean deleteRoomTime(){
        boolean flag =false;
        try{
            manager.getDaoSession().deleteAll(RoomTimes.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现全部emailpassword删除
     * @param
     * @return
     */
    public boolean deleteEmailPassword(){
        boolean flag =false;
        try{
            manager.getDaoSession().deleteAll(EmailPassword.class);
            flag=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }
    /**
     * 返回多条user记录
     * @return
     */
    public List<User> listAll(){
        return manager.getDaoSession().loadAll(User.class);
    }

    /**
     * 返回多条emailpassword记录
     * @return
     */
    public List<EmailPassword> listAllEmailPassword(){
        return manager.getDaoSession().loadAll(EmailPassword.class);
    }

    /**
     * 返回多条roomTime记录
     * @return
     */
    public List<RoomTimes> listAllRoomTime(){
        return manager.getDaoSession().loadAll(RoomTimes.class);
    }

    public void close(){
        manager.closeConnection();
    }

}
