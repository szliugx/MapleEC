package com.ascend.wangfeng.wifimap;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by fengye on 2018/4/23.
 * email 1040441325@qq.com
 */

public class CircleImageView extends android.support.v7.widget.AppCompatImageView {
    // 为避免出现小数,比例采用倒数
    private static final int RADIUS_ICON = 3;// 小图标半径比例的倒数
    private static final int RADIUS_POINT = 5;//点半径比例的倒数
    private static final int BLANK = 20; //空白距离比例
    private int mRadius;
    private int mRadiusIcon;
    private int mRadiusPoint;
    private int mBlank;
    private Drawable mImage;// 图片
    private Drawable mIcon;//图标
    private boolean mOnline;//是否在线
    private int mWidth;
    private int mHeight;
    private Paint mImgPaint;// 绘制bitmap
    private Paint mBlankPaint;//绘制空白
    private Paint mOnlinePaint;//绘制点;


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView, defStyleAttr, 0);
        mOnline = ta.getBoolean(R.styleable.CircleImageView_online, false);
        if (ta.hasValue(R.styleable.CircleImageView_icon)) {
            mIcon = ta.getDrawable(R.styleable.CircleImageView_icon);
        }
        // 不再使用typedArray,释放资源,以便系统复用;
        ta.recycle();

    }

    // 设置图片
    public void setImage(Drawable drawable) {
        mImage = drawable;
        invalidate();
    }

    // 设置图标
    public void setIcon(Drawable drawable) {
        mIcon = drawable;
        invalidate();
    }

    // 设置状态
    public void setState(boolean on) {
        mOnline = on;
        if (mOnlinePaint != null)
            mOnlinePaint.setColor(mOnline ? Color.GREEN : Color.GRAY);
        invalidate();
    }

    private void init() {
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;
        mRadiusIcon = mRadius / RADIUS_ICON;
        mRadiusPoint = mRadius / RADIUS_POINT;
        mBlank = mRadius / BLANK;
        initPaint();
    }

    private void initPaint() {
        mImgPaint = new Paint();
        mImgPaint.setAntiAlias(true);

        mBlankPaint = new Paint();
        mBlankPaint.setAntiAlias(true);
        mBlankPaint.setColor(Color.WHITE);
        mBlankPaint.setStyle(Paint.Style.FILL);

        mOnlinePaint = new Paint();
        mOnlinePaint.setAntiAlias(true);
        mOnlinePaint.setColor(mOnline ? Color.GREEN : Color.GRAY);
        mOnlinePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawIcon(canvas);
        drawPoint(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }

    private void drawCircle(Canvas canvas) {
        Bitmap bitmap = null;
        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = drawableToBitmap(drawable);
            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // 缩放
            float scale = (mRadius - mBlank) * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());
            Matrix matrix = new Matrix();
            matrix.setTranslate(mBlank, mBlank);
            matrix.preScale(scale, scale);
            shader.setLocalMatrix(matrix);
            mImgPaint.setShader(shader);
            // 绘制主圆
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius, mOnlinePaint);
            canvas.drawCircle(mWidth / 2, mHeight / 2, mRadius - mBlank, mImgPaint);

        }
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) return null;
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = null;
        try {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void drawIcon(Canvas canvas) {
        Bitmap bitmap = null;

        if (mIcon != null) {
            bitmap = drawableToBitmap(mIcon);

            BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            // 缩放 该方式绘制的bitmap仍从左上角开始绘制
            float scale = (mRadiusIcon - mBlank) * 2.0f / Math.min(bitmap.getHeight(), bitmap.getWidth());
            Matrix matrix = new Matrix();

            // 位移
            matrix.setTranslate(mWidth - 2 * mRadiusIcon + mBlank, mBlank);
            matrix.preScale(scale, scale);
            shader.setLocalMatrix(matrix);

            mImgPaint.setShader(shader);
            // 绘制ICON
            canvas.drawCircle(mWidth - mRadiusIcon, mRadiusIcon, mRadiusIcon, mBlankPaint);
            canvas.drawCircle(mWidth - mRadiusIcon, mRadiusIcon, mRadiusIcon - mBlank, mImgPaint);

        }
    }

    private void drawPoint(Canvas canvas) {
        canvas.drawCircle(mWidth / 2 + mRadius * 7 / 10, mHeight / 2 + mRadius * 7 / 10, mRadiusPoint, mBlankPaint);
        canvas.drawCircle(mWidth / 2 + mRadius * 7 / 10, mHeight / 2 + mRadius * 7 / 10, mRadiusPoint - mBlank, mOnlinePaint);
    }
}
