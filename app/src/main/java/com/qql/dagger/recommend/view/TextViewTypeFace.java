package com.qql.dagger.recommend.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.qql.dagger.recommend.R;

/**
 * Created by qiao on 2017/3/30.
 */

public class TextViewTypeFace extends AppCompatTextView {
    private int typeFace;
    public TextViewTypeFace(Context context) {
        super(context);
    }

    public TextViewTypeFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public TextViewTypeFace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray tArray = context.obtainStyledAttributes(attrs,
                R.styleable.textType);
        typeFace = tArray.getInteger(R.styleable.textType_type_face,0);
        switchFace(context);
    }

    private void switchFace(Context context) {
        switch (typeFace) {
            case 1:
//                Typeface typeFace = Typeface.createFromAsset(context.getAssets(),"fonts/Bernardo-Moda.ttf");
//                this.setTypeface(typeFace);
                break;
            case 0:
            default:
        }
    }
}
