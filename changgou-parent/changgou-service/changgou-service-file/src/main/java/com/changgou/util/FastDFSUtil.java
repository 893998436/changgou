package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
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
        String filename = new ClassPathResource("fdfs_client.conf").getPath();
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
    public static void upload(FastDFSFile fastDFSFile)throws Exception {
        //附加参数
        NameValuePair[] meta_list= new NameValuePair[1];
        meta_list[0]=new NameValuePair("author",fastDFSFile.getAuthor());
        //创建一个Tracker 访问的客户端对象
        TrackerClient trackerClient=new TrackerClient();
        //通过TrackerClient访问TrackerService服务
       TrackerServer trackerServer= trackerClient.getConnection();

        //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息
        StorageClient storageClient = new StorageClient(trackerServer, null);

        //通过StorageClient访问Storage，实现文件上传，并获取文件上传后的信息
     /*
     * 1.上传文件的字节数组
     * 2.文件的扩展名
     * 3.附加参数 比如：拍摄地址
     * */
        storageClient.upload_file(fastDFSFile.getContent(),fastDFSFile.getExt(),meta_list);

    }
}
