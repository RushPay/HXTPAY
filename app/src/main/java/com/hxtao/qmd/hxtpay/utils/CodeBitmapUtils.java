package com.hxtao.qmd.hxtpay.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.zxing.encoding.EncodingHandler;

/**
 * @Description:
 * @Author: Cyf on 2017/3/2.
 */

public class CodeBitmapUtils {
    public Bitmap getCodeBitmap(Context context){
        Bitmap bitmap = EncodingHandler.enCodeStringWithLogo("你好,3/2", context, null, 400);
        return bitmap;
    }
}
