package finance.com.fp.utlis;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;

import finance.com.fp.R;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/27 15:23
 */
public class GlideUtils {

    public static void loadImage(Context context, ImageView img, String loadUrl) {
        File cacheDir = context.getExternalCacheDir();//指定的是数据的缓存地址
        int diskCacheSize = 1024 * 1024 * 30;//最多可以缓存多少字节的数据
        //设置磁盘缓存大小
//        builder.setDiskCache(new DiskLruCacheFactory(cacheDir.getPath(), "glide", diskCacheSize));
        Glide.with(context)
                .load(loadUrl).placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .fitCenter()
                .thumbnail(0.2f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(img);
    }

    public static void loadImage(Context context, ImageView img, int img_id) {
        Glide.with(context).load(img_id).placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(img);
    }
}
