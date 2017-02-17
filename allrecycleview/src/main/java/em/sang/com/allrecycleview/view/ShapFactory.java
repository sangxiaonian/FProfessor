package em.sang.com.allrecycleview.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/12/8 17:38
 */
public class ShapFactory {

    private Path mPath;
    private Paint mPaint;
    private static ShapFactory factory;

    public static ShapFactory getInstance(Path mPath, Paint mPaint) {
        factory = new ShapFactory(mPath, mPaint);
        return factory;
    }

    private ShapFactory(Path mPath, Paint mPaint) {
        this.mPaint = mPaint;
        this.mPath = mPath;
    }

    /**
     * 绘制一个箭头
     *
     * @param mWidth  控件高度
     * @param mHeight 控件宽
     * @param den     箭头杆空格数量
     * @return 一个向下的箭头
     */
    public Bitmap creatArrows(int mWidth, int mHeight, int den) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL);
        int amount = 0;
        for (int i = 1; i <= den; i++) {
            amount += i;
        }
        float start = mHeight * 2 / 3;
        float l = (float) ((mHeight - start) / (Math.sin(60 * Math.PI / 180) * 2));
        mPath.moveTo(mWidth / 2, mHeight);
        mPath.lineTo(mWidth / 2 - l, start);
        mPath.lineTo(mWidth / 2 + l, start);
        mPath.lineTo(mWidth / 2, mHeight);
        int count = 0;
        int last = 0;
        float left = mWidth / 2 - l / 2;
        float right = mWidth / 2 + l / 2;
        for (int i = den; i > 0; i--) {
            last = count;
            count += i;
            if (i % 2 == 0) {
                float top = start - start * last / (amount);
                float boom = start - start * count / (amount);
                mPath.addRect(left, top, right, boom, Path.Direction.CCW);
            }
        }
        canvas.drawPath(mPath, mPaint);
        return bitmap;
    }

    /**
     * 绘制一个方块
     *
     * @param mWidth  宽
     * @param mHeight 高
     * @return 一个四方形的Bitmap
     */
    public Bitmap creatShap(int mWidth, int mHeight) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        mPath.reset();
        mPaint.setStyle(Paint.Style.FILL);
        mPath.addRect(0, 0, mWidth , mHeight, Path.Direction.CCW);
        canvas.drawPath(mPath, mPaint);
        return bitmap;
    }

    public Bitmap creatCorrect(int mWidth, int mHeight) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        mPath.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath.moveTo(0,mHeight*2/3);
        mPath.lineTo(mWidth/3,mHeight);
        mPath.lineTo(mWidth,0);
        canvas.drawPath(mPath, mPaint);
        return bitmap;
    }

    public Bitmap creatError(int mWidth, int mHeight) {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        mPath.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPath.moveTo(mWidth/3,mHeight/3);
        mPath.lineTo(mWidth*2/3,mHeight*2/3);
        mPath.moveTo(mWidth*2/3,mHeight/3);
        mPath.lineTo(mWidth/3,mHeight*2/3);
        canvas.drawPath(mPath, mPaint);
        return bitmap;
    }
}
