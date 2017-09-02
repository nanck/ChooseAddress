package com.nanck.chooseaddressexample;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * @author nanck on 2016/6/2.
 */
public class EditTextWithDel extends AppCompatEditText {
    private static final String TAG = "EditTextWithDel";

    private Drawable mDrawable;
    private Context mContext;
    private boolean isPreview = false;  // Drawable 是否已绘制在屏幕上


    public EditTextWithDel(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextWithDel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EditTextWithDel);
//        try {
//            mDrawableTint = typedArray.getColor(R.styleable.EditTextWithDel_drawable_tint_color, Color.BLACK);
//            mDrawableWidth = typedArray.getColor(R.styleable.EditTextWithDel_drawable_width, 100);
//        } finally {
//            typedArray.recycle();
//        }
        init();
    }

    public boolean isDrawablePreview() {
        return isPreview;
    }

    private void init() {
        if (Build.VERSION.SDK_INT >= 21) {
            mDrawable = mContext.getResources().getDrawable(R.drawable.ic_close_black_24dp, mContext.getTheme());
        } else {
            mDrawable = mContext.getResources().getDrawable(R.drawable.ic_close_black_24dp);
        }
        setDrawable();

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (!focused) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            isPreview = false;
        } else {
            setDrawable();
        }
    }

    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], null, null, null);
            isPreview = false;
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
            isPreview = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isDrawablePreview() && mDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - mDrawable.getIntrinsicWidth() - 40;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }
}
