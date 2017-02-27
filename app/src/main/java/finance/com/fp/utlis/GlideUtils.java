package finance.com.fp.utlis;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import finance.com.fp.R;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2017/2/27 15:23
 */
public class GlideUtils {

    public static void loadImage(Context context, ImageView img, String loadUrl) {
        Glide.with(context).load(loadUrl).placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(img);
    }

    public static void loadImage(Context context, ImageView img, int img_id) {
        Glide.with(context).load(img_id).placeholder(R.mipmap.loading)
                .error(R.mipmap.load_fail)
                .fitCenter()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade().into(img);
    }
}
