package cn.e3mall.fast;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

public class FastDfsTest {

    @Test
    public void testUpload() throws Exception{
        //1、加载配置文件，配置文件中的内容就是tracker服务的地址。
        //    配置文件内容：tracker_server=192.168.25.133:22122
        ClientGlobal.init("E:/projects/myProject/shop/e3-manager-web/src/main/resources/conf/client.conf");
        //2、创建一个TrackerClient对象。直接new一个。
        TrackerClient trackerClient = new TrackerClient();
        //3、使用TrackerClient对象创建连接，获得一个TrackerServer对象。
        TrackerServer trackerServer = trackerClient.getConnection();
        //4、创建一个StorageServer的引用，值为null
        StorageServer storageServer = null;
        //5、创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        //6、使用StorageClient对象上传图片。
        String[] strings = storageClient.upload_file("C:/Users/Administrator/Desktop/71005092900858.jpg", "jpg", null);
        //7、返回数组。包含组名和图片的路径。
        for (String string:strings) {
            System.out.println(string);
        }
    }



    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("E:/projects/myProject/shop/e3-manager-web/src/main/resources/conf/client.conf");
        String file = fastDFSClient.uploadFile("C:/Users/Administrator/Desktop/71005092900858.jpg");
        System.out.println(file);
    }
}
