package com.cqjtu.bysj.qiniu;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.http.Response;

public class QiniuUpload{
    String accesskey = "ll5F7Kny9FPUZKlSqU-lGkoory_GXNhv9U7kADDt";
    String secretKey = "BiH924Bk9zIEzFgFQAoUABY40gdyZ9tGeMPM5F8y";

    public String getToken(String bucket) {
        Auth auth = Auth.create(accesskey, secretKey);
        String token = auth.uploadToken(bucket);
        return token;
    }

//    UploadManager uploadManager = new UploadManager();
//        Auth auth = Auth.create(accesskey, secretKey);
//        String token = auth.uploadToken(bucket);
//        Response r = uploadManager.put("hello world".getBytes(), "yourkey", token);
}
