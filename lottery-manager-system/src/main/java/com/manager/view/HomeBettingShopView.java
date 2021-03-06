package com.manager.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.manager.Interface.ICoallBack8;
import com.manager.common.Constants;
import com.manager.common.ImageTools;
import com.manager.lotterypro.R;
import com.manager.lotterypro.SysApplication;
import com.manager.personals.PrivateChatAct;
import com.manager.user.LotteryAndWinningAct;
import com.manager.user.MyEntrustRecordAct;
import com.manager.user.UserTicketAct;
import com.manager.wallet.MyWalletAct;

import java.util.ArrayList;

/**
 * @author donghuiyang
 * @create time 2016/4/28 0028.
 */
public class HomeBettingShopView extends View implements View.OnTouchListener {

    private Context context;

    private Matrix matrix = new Matrix();
    private Paint mPaint;
    private Bitmap mBgBmp, mIconBgBmp1, mIconBgBmp2;
    private ArrayList<Bitmap> iconBmps = new ArrayList<>();

    private int width, height, bgW, bgH;
    private float scale, scaleX, scaleY;
    private int radius = 36, textSize = 32;
    private Point centerPos = new Point();
    private Point[] points = new Point[5];
    private boolean[] isFlip = new boolean[5];

    float[] values = new float[9];
    private Rect[] rect = new Rect[5];
    private boolean isInit = false, isLoad = false;

    /*private static final float[][] iconPos = new float[][]{
            {-0.075f, -0.325f},
            {0.245f, -0.32f},
            {0.062f, 0.048f},
            {-0.052f, 0.132f},
            {-0.21f, 0.19f}
    };
    private static final int IconImgs[] = {
            R.mipmap.cm_btn_service_n,
            R.mipmap.zd_btn_prize_n,
            R.mipmap.cm_btn_purse_n,
            R.mipmap.zd_btn_chat_n,
            R.mipmap.zd_btn_messge_n
    };

    private static final int IconBigImgs[] = {
            R.mipmap.home_fragment_nav_11,
            R.mipmap.home_fragment_nav_7_1,
            R.mipmap.home_fragment_nav_9_1,
            R.mipmap.home_fragment_nav_4,
            R.mipmap.home_fragment_nav_23
    };

    private static final int IconTextImgs[] = {
            R.string.me_item_str_3,
            R.string.me_item_str_1,
            R.string.me_item_str_0,
            R.string.friend_str0,
            R.string.news_item_str_00
    };*/

    private static final float[][] iconPos = new float[][]{
            {-0.33f, -0.158f},
            {-0.065f, -0.30f},
            {0.09f, -0.33f},
            {0.223f, -0.26f},
            {0.34f, -0.16f}
    };
    private static final float[][] iconBigPos = new float[][]{
            {0.06f, 0.13f},
            {0.35f, 0.03f},
            {0.4f, 0.02f},
            {0.223f, -0.26f},
            {0.34f, -0.16f}
    };

    private static final int IconImgs[] = {
            R.mipmap.cm_btn_service_n,
            R.mipmap.zd_btn_prize_n,
            R.mipmap.cm_btn_purse_n,
            R.mipmap.zd_btn_chat_n,
            R.mipmap.zd_btn_messge_n
    };

    private static final int IconBigImgs[] = {
            R.mipmap.home_fragment_nav_11,
            R.mipmap.home_fragment_nav_7_1,
            R.mipmap.home_fragment_nav_9_1,
            R.mipmap.home_fragment_nav_4,
            R.mipmap.home_fragment_nav_23
    };

    private static final int IconTextImgs[] = {
            R.string.me_item_str_9,
            R.string.me_item_str_1,
            R.string.me_item_str_0,
            R.string.friend_str0,
            R.string.news_item_str_41
    };


    private int currIndex = -1;
    private ICoallBack8 iCoallBack;

    public HomeBettingShopView(Context context) {
        this(context, null);
    }
    public HomeBettingShopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);// 消除锯齿
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        mPaint.setColor(getResources().getColor(R.color.text_color_1));
        //mPaint.setTypeface(Typeface.SANS_SERIF);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/fangzhengdaheijianti.ttf");
        mPaint.setTypeface(font);

        this.setOnTouchListener(this);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        width = getWidth();// 获取对应宽度
        height = getHeight();// 获取对应高度

        centerPos.x = width / 2;
        centerPos.y = height / 2;

        if (!isLoad) {

            LoadBmp();
            isLoad = true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBgBmp == null) return;

        canvas.drawBitmap(mBgBmp, centerPos.x - mBgBmp.getWidth() / 2, centerPos.y - mBgBmp.getHeight() / 2, mPaint);

        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                //偶数
                //mIconPaint.setColor(getResources().getColor(R.color.bg_color_2));
                canvas.drawBitmap(mIconBgBmp1, centerPos.x + bgW * iconPos[i][0] - mIconBgBmp1.getWidth() / 2, centerPos.y + bgH * iconPos[i][1] -
                        mIconBgBmp1.getHeight() / 2, mPaint);
            } else {
                //mIconPaint.setColor(getResources().getColor(R.color.bg_color_3));
                canvas.drawBitmap(mIconBgBmp2, centerPos.x + bgW * iconPos[i][0] - mIconBgBmp2.getWidth() / 2, centerPos.y + bgH * iconPos[i][1] -
                        mIconBgBmp2.getHeight() / 2, mPaint);
            }

            //canvas.drawCircle(centerPos.x + bgW * iconPos[i][0], centerPos.y + bgH * iconPos[i][1], radius, mIconPaint);
            canvas.drawBitmap(iconBmps.get(i), centerPos.x + bgW * iconPos[i][0] - iconBmps.get(i).getWidth() / 2, centerPos.y + bgH * iconPos[i][1] -
                    iconBmps.get(i).getHeight() / 2, mPaint);

            if (i == 2 || i == 3){
                //聊天 通知
                //canvas.drawCircle(centerPos.x + bgW * iconPos[i][0] + iconBmps.get(i).getWidth()/2 - radius/2, centerPos.y + bgH * iconPos[i][1] - iconBmps.get(i).getHeight()/2 +  radius/2, radius, mDrawPaint);
            }

            if (!isInit){
                rect[i] = new Rect((int)(centerPos.x + bgW * iconPos[i][0] - mIconBgBmp1.getWidth() / 2),
                        (int)(centerPos.y + bgH * iconPos[i][1] - mIconBgBmp1.getHeight() / 2) + mIconBgBmp1.getHeight(),
                        ((int)(centerPos.x + bgW * iconPos[i][0] - mIconBgBmp1.getWidth() / 2))+ mIconBgBmp1.getWidth(),
                        (int)(centerPos.y + bgH * iconPos[i][1] - mIconBgBmp1.getHeight() / 2));

                if (i < 3){
                    points[i] = new Point((int)(rect[i].left),(int)(rect[i].bottom));
                    isFlip[i] = false;
                }else{
                    points[i] = new Point((int)(rect[i].right),(int)(rect[i].bottom));
                    isFlip[i] = true;
                }

                if (i >= 4){
                    isInit = true;
                }
            }
        }

        canvas.drawText(getResources().getString(R.string.app_name_info), centerPos.x + bgW * 0.13f, centerPos.y + bgH * 0.08f, mPaint);
        canvas.drawText(SysApplication.getUserIdentity(), centerPos.x + bgW * 0.13f, centerPos.y + bgH * 0.08f + textSize * 1.2f, mPaint);
    }

    /**
     * 加载图片
     */
    private void LoadBmp() {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.zd_bg_00);
        mBgBmp = ImageTools.zoomBitmapUniform(bmp, width, height, matrix);//Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        bgW = mBgBmp.getWidth();
        bgH = mBgBmp.getHeight();

        matrix.getValues(values);
        textSize *= values[8];
        mPaint.setTextSize(textSize);

        for (int i = 0; i < 5; i++) {
            Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), IconImgs[i]);
            Bitmap bmp2 = Bitmap.createBitmap(bmp1, 0, 0, bmp1.getWidth(), bmp1.getHeight(), matrix, true);
            iconBmps.add(bmp2);
        }

        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_bg0);
        mIconBgBmp1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_bg1);
        mIconBgBmp2 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
    }

    public void setICoallBack(ICoallBack8 iCoallBack){
        this.iCoallBack = iCoallBack;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("touch", "doing");
        int action=event.getAction();
        int x=(int) event.getX();
        int y=(int) event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            //显示大图标
            for (int i=0;i<5;i++) {
                if (x >= rect[i].left && x <= rect[i].right && y >= rect[i].bottom && y <= rect[i].top) {
                    currIndex = i;
                    iCoallBack.onItemTouchDown(IconBigImgs[i], IconTextImgs[i], Constants.TextStyles[i], points[i].x, points[i].y, isFlip[i]);
                    break;
                }
            }

        }else if(action == MotionEvent.ACTION_MOVE){
            int index = -1;
            for (int i=0;i<5;i++) {
                if (x >= rect[i].left && x <= rect[i].right && y >= rect[i].bottom && y <= rect[i].top ) {
                    index = i;
                    break;
                }
            }

            if (index == -1){
                iCoallBack.onItemTouchUp();
            }else{
                currIndex = index;
                iCoallBack.onItemTouchDown(IconBigImgs[currIndex], IconTextImgs[currIndex], Constants.TextStyles[currIndex], points[currIndex].x, points[currIndex].y, isFlip[currIndex]);
            }

        }else if (action == MotionEvent.ACTION_UP) {
            currIndex = -1;
            iCoallBack.onItemTouchUp();

            Class next = null;
            for (int i=0;i<5;i++) {
                if (x >= rect[i].left && x <= rect[i].right && y >= rect[i].bottom && y <= rect[i].top){
                    Log.e("touch", "doing==========");

                    switch (i){
                        case 0:{
                            //委托服务 历史记录
                            next = MyEntrustRecordAct.class;
                        }
                        break;

                        case 1:{
                            //兑奖
                            next = UserTicketAct.class;
                        }
                        break;

                        case 2:{
                            //钱包
                            next = MyWalletAct.class;
                        }
                        break;
                        case 3:{
                            //私聊
                            next = PrivateChatAct.class;
                        }
                        break;
                        case 4:{
                            //开奖信息
                            next = LotteryAndWinningAct.class;
                        }
                        break;
                        default:
                            break;
                    }

                    if (next != null){
                        SysApplication.enterNext1(context, next);

                        break;
                    }
                }
            }
        }
        return true;
    }
}
