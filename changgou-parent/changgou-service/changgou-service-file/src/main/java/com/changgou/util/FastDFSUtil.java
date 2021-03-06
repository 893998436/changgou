package com.changgou.util;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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
     *
     */

    public static String[] upload(FastDFSFile fastDFSFile) throws Exception {
        //附加参数
        NameValuePair[] meta_list = new NameValuePair[1];
        meta_list[0] = new NameValuePair("author", fastDFSFile.getAuthor());
        //创建一个TrackClient,访问TrackerService
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = getTrackerServer();
        //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息
        StorageClient storageClient = getStorageClient(trackerServer);
        /*
         * 1.上传文件的字节数组
         * 2.文件的扩展名
         * 3.附加参数 比如：拍摄地址
         * uploads
         *   uploads[0]文件上传所储的Storage的组名字group1
         *   uploads[1]文件储存到Storage上的文件名字M00/02/**
         *
         * */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
        return uploads;
    }

    /*
     *获取文件信息
     * param groupName ：文件的组名
     * remoteFileNAme 文件的存储路径名字
     *
     */

    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        //创建一个TrackClient,访问TrackerService
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = getTrackerServer();
        //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }

    //文件下载
    /* @param groupName:组名
     * @param remoteFileName：文件存储完整名
     *
     */

    public InputStream downloadFile(String groupName, String remoteFileName) throws Exception {
        //创建一个TrackClient,访问TrackerService
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = getTrackerServer();
        //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /*
     * 文件删除
     * @param groupName:组名
     * @param remoteFileName：文件存储完整名
     * */

    public static void delectFile(String groupName, String remoteFileName) throws Exception {
        //创建一个TrackClient,访问TrackerService
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = getTrackerServer();
        //tracker获取Storage信息创建StorageClient对象存储Storge的链接信息
        StorageClient storageClient = getStorageClient(trackerServer);
        //文件删除
        storageClient.delete_file(groupName, remoteFileName);
    }

    /*
     * 获取Storage信息
     * */

    public static StorageServer getStorage() throws Exception {
        //创建一个TrackClient,访问TrackerService
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /*
     * 获取Storage的IP和端口信息
     *
     * */

    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception {
        //创建一个TrackClient,访问TrackerService
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //获取Storage IP和端口信息
        return trackerClient.getFetchStorages(trackerServer, groupName, remoteFileName);
    }

    /*
     * 获取Tracker信息
     * */

    public static String getTrackerInfo() throws Exception {
        TrackerServer trackerServer = getTrackerServer();
        //Tracker的ip，http端口
        int tracker_http_port = ClientGlobal.getG_tracker_http_port();
        //Tracker IP
        String ip = trackerServer.getInetSocketAddress().getHostString();
        String url = "http://" + ip +":"+ tracker_http_port;
        return url;
    }

    /*
     * 获取 Tracker
     * */

    public static TrackerServer getTrackerServer() throws Exception {
        //创建一个TrackClient,访问TrackerService
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerService链接对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /*
     *获取StorageClient
     * */

    public static StorageClient getStorageClient(TrackerServer trackerServer) {
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }

   /* public static void main(String[] args) throws Exception {
        delectFile("group1", "/M00/00/00/wKgBA1969XaAIiZ3AASaoxZsnks807.jpg");
    }*/
}
