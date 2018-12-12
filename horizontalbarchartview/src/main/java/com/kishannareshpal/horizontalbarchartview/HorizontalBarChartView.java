package com.kishannareshpal.horizontalbarchartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.kishannareshpal.horizontalbarchartview.models.Data;
import com.kishannareshpal.horizontalbarchartview.models.MyData;

import java.util.ArrayList;
import java.util.List;



/**
 * Common class for storage data view implementations
 * Info: To illustrate how much storage the device has used or has left, relative to the device's storage size. For my Exames App.
 * I thought of open sourcing this, bc why not?!
 *
 * @author Kishan Jadav - kishannareshpal (kishan_jadav@hotmail.com)
 */

public class HorizontalBarChartView extends View {

    // List containing the Storage data.
    private List<Data> dataList;

    private float corner_radius = 14F; // radius
    private int background_color; // background color
    private RectF main_rect; // the base rectangle.
    private Rect bounds;
    private Paint main_paint;
    private GradientDrawable newDrawable;

    private float[] tb_left_radius;
    private float[] flat_radius;
    private float[] tb_right_radius;

    private float currentPercentage;

    private Context ctx;

    private int fullWidth; // the max canvas width.
    private int fullHeight; // the max canvas height.

    /**
     * Constructor.
     * Default view constructor.
     * @param ctx ctx
     * @param attrs attributes.
     */
    public HorizontalBarChartView(Context ctx, @Nullable AttributeSet attrs) {
        super(ctx, attrs);
        this.ctx = ctx;
        init(attrs);
    }


    // Default Initializer.
    public void init(@Nullable AttributeSet set){
        if (set != null){
            // if custom attributes were set.
            TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.HorizontalBarChartView);
            corner_radius    = ta.getFloat(R.styleable.HorizontalBarChartView_hbcv_cornerRadius, corner_radius);
            background_color = ta.getColor(R.styleable.HorizontalBarChartView_hbcv_color, ContextCompat.getColor(ctx, R.color.default_bar_color));
            ta.recycle();
        }

        // Create the main_rect.
        main_rect = new RectF();
        main_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bounds = new Rect();

        // Init the list that will contain newly added data.
        dataList = new ArrayList<>();

        tb_left_radius  = new float[]{corner_radius, corner_radius, 0, 0, 0, 0, corner_radius, corner_radius}; // Is only round on top-bottom left.
        flat_radius     = new float[]{0, 0, 0, 0, 0, 0, 0, 0}; // A flat square. Without rounded corners.
        tb_right_radius = new float[]{0, 0, corner_radius, corner_radius, corner_radius, corner_radius, 0, 0}; // Is only round on top-bottom right.

        newDrawable = new GradientDrawable(); // the individual data rectangle.
        newDrawable.setShape(GradientDrawable.RECTANGLE);

        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        fullWidth = w;
        fullHeight = h;
        invalidate();
    }

    /**
     * To add the data on top of the main bar.
     *
     * @param newDataId
     * @param newPercentage
     * @param newColorRes
     */
    public void addData(int newDataId, float newPercentage, int newColorRes){

        if (currentPercentage >= 100F || newPercentage > 100F){
            Log.e("HorizontalBarCV_ERR", "Current total percentage " + currentPercentage + " exceeded 100.\nDid not add new data.");
            return;
        }

        // Add it to the list of new data.
        Data data = new Data(newDataId, newColorRes, (int) newPercentage);
        dataList.add(data);
    }


    /**
     * Must be called after the addData() method to redraw and show the data.
     */
    public void show(){
        invalidate();
    }


    /**
     * Get a data's percentage that has been already set, using it's id.
     * Note: this may return 0 if called before the .show() method.
     *
     * @param dataId
     * @return
     */
    public float getDataPercentage(int dataId){
        float percentage = -404;

        for (Data data : dataList) {
            if (data.getId() == dataId) {
                percentage = data.getPercentage();
            }
        }

        return percentage;
    }


    /**
     * Get a list of all of the data set.
     * Note: this may return 0 if called before the .show() method.
     *
     * @return List of MyData.class, which each, contains an ID and a PERCENTAGE.
     */
    public List<MyData> getAllData(){
        List<MyData> mydataList = new ArrayList<>();

        for (Data data : dataList) {
            MyData myData = new MyData(data.getId(), data.getPercentage());
            mydataList.add(myData);
        }

        return mydataList;
    }


    /**
     * Get the sum of percentage of all data were set until now.
     * Note: this may return 0 if called before the .show() method.
     *
     * @return total used percentage
     */
    public float getFilledPercentage(){
        return currentPercentage;
    }




    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // First save the canvas width and height to be used later (when adding new data).
        fullWidth = getWidth();
        fullHeight = getHeight();

        // Create the main_rect
        main_rect.top = 0;
        main_rect.left = 0;
        main_rect.right = fullWidth;
        main_rect.bottom = fullHeight;
        main_paint.setColor(background_color);
        canvas.drawRoundRect(main_rect, corner_radius, corner_radius, main_paint);

        // Itterate through the list of data that were added.
        boolean starting = true;
        float currentWidth = 0;
        for (Data data : dataList){
            // Create and Show the data on top of the main_rect.
            float newWidth = fullWidth * data.getPercentage() / 100F;
            bounds.top = 0;
            bounds.left = 0;
            bounds.right = (int) newWidth;
            bounds.bottom = fullHeight;

            currentWidth += newWidth;

            if (starting){
                newDrawable.setCornerRadii(tb_left_radius);
                starting = false;

            } else if ((int) currentWidth < fullWidth){
                newDrawable.setCornerRadii(flat_radius);

            } else {
                newDrawable.setCornerRadii(tb_right_radius);
            }

            if ((int) currentWidth <= fullWidth){
                newDrawable.setColor(ContextCompat.getColor(ctx, data.getColor()));
                newDrawable.setBounds(bounds);
                newDrawable.draw(canvas);
                canvas.translate(newWidth - corner_radius, 0);

            } else {
                Log.e("HorizontalBarCV_ERR", "This actions, exceeds a total of 100%.\nDid not add new data.");
                return;
            }
        }


        currentPercentage = toPercentage(currentWidth);
    }


    private float toPercentage(float rectWidth){
        return rectWidth * 100 / fullWidth;
    }
}
