package test.pylogy.com.mygroupen.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class MyCardView extends CardView {
    public MyCardView(Context context) {
        super(context);
    }

    public MyCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(2);
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.STROKE);
        Path path=new Path();
        path.moveTo(getWidth(),0);
        path.lineTo(getWidth(),getHeight());
        canvas.drawPath(path,paint);
    }
}
