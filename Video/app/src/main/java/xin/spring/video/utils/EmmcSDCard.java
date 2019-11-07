package xin.spring.video.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import java.io.File;
import java.io.FileOutputStream;

public class EmmcSDCard {

    /**
     * 保存到手机SD卡存储空间/mnt/sdcard/目录下：
     * 判断SD卡是否存在和是否有写入权限：
     * 是否是sdcard
     * @return
     */
    public static boolean isSDCard(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //保存文件到SD卡：
    public static void saveToSDCard(String filename, String content)throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), filename);
        FileOutputStream outStream = new FileOutputStream(file);
        outStream.write(content.getBytes());
        outStream.close();
    }

    /**
     * 保存到手机自带存储空间/data/data/<应用程序包>/ files/目录下：
     * 保存文件
     * @param filename 文件名称
     * @param content 文件内容
     */
    public void save(Context context, String filename, String content) throws Exception {
        //私有操作模式：创建出来的文件只能被本应用访问，其它应用无法访问该文件，另外采用私有操作模式创建的文件，写入文件中的内容会覆盖原文件的内容
        FileOutputStream outStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        outStream.write(content.getBytes());
        outStream.close();
    }

}
