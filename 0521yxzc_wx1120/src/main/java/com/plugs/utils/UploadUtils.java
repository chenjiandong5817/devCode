package com.plugs.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 * 图片传输工具
 *
 * @outhor qfHan
 * @create 2017-09-27 18:53
 */
public class UploadUtils {

    private static final String UTF_8 = "UTF-8";

    private static final String PLAIN_TEXT_TYPE = "text/plain";

    private static final String WEBSITE = "http://192.168.51.176:11001";

    private static final String UPLOAD_IMAGE_API = "/fileServer/uploadFile";

    private static final String PRIVATE_KEY = "dNHKiaRSe+jsMRssG4wvdIfOVnxXpnwj"; //简单的秘钥


    public static String uploadByScott(byte[] imageByte, String uuid, String folderName, String expandedName){

        CloseableHttpResponse response = null;
        try {
            //初始化builder
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setCharset(Charset.forName(UTF_8));
            ContentType contentType = ContentType.create(PLAIN_TEXT_TYPE, UTF_8);

            //加入文件部分
            ByteArrayBody image = new ByteArrayBody(imageByte,ContentType.APPLICATION_JSON,"image.jpg");//传递图片的时候可以通过此处上传image.jpg随便给出即可
            multipartEntityBuilder.addPart("pictureFile", image);

            //加入参数部分
            Map<String, String> map = new HashMap<String, String>();
            map.put("uuid", uuid);
            map.put("folderName", folderName);
            map.put("expandedName", expandedName);
            map.put("platform", "wxtest"); //todo:针对不同项目部署调整命名
            map.put("key", PRIVATE_KEY);
            for(String key : map.keySet()){
                multipartEntityBuilder.addPart(key, new StringBody(map.get(key), contentType));
            }

            HttpPost httpPost = new HttpPost(WEBSITE + UPLOAD_IMAGE_API);
            httpPost.setEntity(multipartEntityBuilder.build());
            //执行请求
            CloseableHttpClient httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity httpEntity = response.getEntity();
                String result = EntityUtils.toString(httpEntity);
                return result;
            }
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } finally{
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
