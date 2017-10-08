package cn.e3mall.controller;

import cn.e3mall.common.utils.FastDFSClient;
import cn.e3mall.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 图片上传工具Controller
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;    //图片服务器地址


    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE+";  charset=utf-8")
    @ResponseBody
    public String fileUpload(MultipartFile uploadFile){
        Map result = new HashMap();
        try {
            //1.取文件的扩展名
            String originalFilename = uploadFile.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            //2.创建一个FastDFS的客户端
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //3.执行处理上传
            String path = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //4.拼接反悔的url和 ip地址，瓶装成完整的url
            String url = IMAGE_SERVER_URL + path;
            //5.返回map

            result.put("error", 0);
            result.put("url", url);
            return JsonUtils.objectToJson(result);
        } catch (Exception e) {
            e.printStackTrace();
            //5.返回map
            result.put("error", 0);
            result.put("message", "图片上传失败");
            return  JsonUtils.objectToJson(result);
        }
    }


}
