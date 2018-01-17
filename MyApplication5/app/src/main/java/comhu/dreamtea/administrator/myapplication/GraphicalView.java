package comhu.dreamtea.administrator.myapplication;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 1/15 0015.
 */

public class GraphicalView extends View {
     private XYChart x;

    public GraphicalView(Context context) {
        super(context);
    }

    public GraphicalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GraphicalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GraphicalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context,attrs);
    }

    public void invalidate() {

    }
}
