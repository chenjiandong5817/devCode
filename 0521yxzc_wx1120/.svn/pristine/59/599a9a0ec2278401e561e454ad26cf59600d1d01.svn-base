package com.plugs.utils;

import com.exception.BizException;
import com.exception.ValidateException;
import com.plugs.constants.ReturnCodeConstants;
import com.util.PropertiesUtil;
import com.util.StringUtil;

/**
 * Created by Zhouhy on 2016/9/19.
 */
public class FileUtils {


    /***
     * @param btImg
     * @param uuid
     * @param folderName
     * @return
     * @throws ValidateException
     */
    public static String uploadImages(byte[] btImg, String uuid, String folderName) throws BizException {
        if (btImg != null && btImg.length > 0) {
            long fileSize = btImg.length;
            String imageSize = PropertiesUtil.getConfigInfo("imageSize");
            //限制文件大小
            Long fileLimit = 1048576l;
            if (StringUtil.isNotEmpty(imageSize))
                fileLimit = Long.parseLong(imageSize);
            if (fileLimit < fileSize)
                throw new BizException("单张图片不能超过" + (fileLimit / 1024) + "KB", ReturnCodeConstants.ERR_10008_FILE_LIMIT);

            return UploadUtils.uploadByScott(btImg, uuid, folderName, "png");
        }
        return null;
    }
}
