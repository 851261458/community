package com.mju.band3.community.Provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;

import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;

import com.mju.band3.community.Exception.CustomizeErrorCode;
import com.mju.band3.community.Exception.CustomizeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.util.UUID;

@Component
public class UcloudProvider {

    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;



  public String upload(InputStream fileStream, String mimeType,String fileName){
      String generatedFileName;
      String[] filePaths = fileName.split("\\.");
      if (filePaths.length > 1) {
          generatedFileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
      } else {
          throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
      }

      try {
          ObjectAuthorization OBJECT_AUTHORIZER = new UfileObjectLocalAuthorization(publicKey, privateKey);
          ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
          PutObjectResultBean response = UfileClient.object(OBJECT_AUTHORIZER, config)
                  .putObject(fileStream, mimeType)
                  .nameAs(generatedFileName)
                  .toBucket("doudou")
                  /**
                   * 是否上传校验MD5, Default = true
                   */
                  //  .withVerifyMd5(false)
                  /**
                   * 指定progress callback的间隔, Default = 每秒回调
                   */
                  //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                  /**
                   * 配置进度监听
                   */
                  .setOnProgressListener((bytesWritten, contentLength) -> {

                  })
                  .execute();
          if (response != null && response.getRetCode() == 0) {
              String url = UfileClient.object(OBJECT_AUTHORIZER, config)
                      .getDownloadUrlFromPrivateBucket(generatedFileName, "doudou", 24*60*60*365*10)
                      .createUrl();
              return url;
          }else {
//              log.error("upload error,{}", response);
              throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
          }

      } catch (UfileClientException e) {
          throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
      } catch (UfileServerException e) {
          throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
      }

  }


}
