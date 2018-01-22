package demo.example.com.videodemo.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

public class AlwaysMarqueeTextView extends AppCompatTextView {
    public static final int SPEED = 0;

    public AlwaysMarqueeTextView(Context paramContext) {
        super(paramContext);
        setMarqueeSpeed(this, 0F, false);
    }

    public AlwaysMarqueeTextView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        setMarqueeSpeed(this, 0F, false);
    }

    public AlwaysMarqueeTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        setMarqueeSpeed(this, 0F, false);
    }

    public boolean isFocused() {
        return true;
    }

    protected void setMarqueeSpeed(TextView paramTextView, float paramFloat, boolean paramBoolean) {
        Field localField;
        try {
            localField = paramTextView.getClass().getDeclaredField("mMarquee");
            localField.setAccessible(true);
            paramTextView = (TextView) localField.get(paramTextView);
            if (paramTextView != null) {
                localField = paramTextView.getClass().getDeclaredField("mScrollUnit");
                localField.setAccessible(true);
                float f = paramFloat;
                if (paramBoolean)
                    f = localField.getFloat(paramTextView) * paramFloat;
                localField.setFloat(paramTextView, f);
            }
            return;
        } catch (Exception e) {
        }
    }
}
