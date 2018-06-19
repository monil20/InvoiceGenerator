package com.example.monilandharia.invoice;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Monil Andharia on 28-Apr-16.
 */
public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/ProductSans-Regular.ttf");
        this.setTypeface(typeface);
    }

}