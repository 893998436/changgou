package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class FastDFSUtil {
    /*
    * 实现FastDFS文件管理
    * 文件上传
    * 文件删除
    * 文件下载
    * 文件信息获取
    * Storage信息获取
    * Tracker信息获取
    * */
    //1加载Tracker信息
    static {
        //查找classpath下的文件路径
        String filename=new ClassPathResource("fdfs_client.conf").getPath();
        try {
            //加载Tracker链接信息
            ClientGlobal.init(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
    /*
    * 文件上传
    * @parame  fastDFSFile 上传的文件信息封装
    *
    * */
  public  void upload(FastDFSFile fastDFSFile){
   //创建一个Tracker 访问的客户端对象

      //通过TrackerClient访问TrackerService服务



      //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息




      //通过StorageClient访问Storage，实现文件上传


  }
}
