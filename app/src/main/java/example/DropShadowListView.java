package example;

import bisnis.com.official.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

public class DropShadowListView extends ListView {
		 
	    private static final int LOC_LEFT = 0;
	    private static final int LOC_TOP = 1;
	    private static final int LOC_RIGHT = 2;
	    private static final int LOC_BOTTOM = 3;
	 
	    private int mDropShadowRightId;
	    private int mDropShadowLeftId;
	    private int mDropShadowTopId;
	    private int mDropShadowBottomId;
	 
	    private boolean mDropShadowRight;
	    private boolean mDropShadowLeft;
	    private boolean mDropShadowTop;
	    private boolean mDropShadowBottom;
	 
	    public DropShadowListView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	 
	        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DropShadowListView);
	 
	        mDropShadowRightId = a.getResourceId(R.styleable.DropShadowListView_dropShadowRightSrc, -1);
	        mDropShadowLeftId = a.getResourceId(R.styleable.DropShadowListView_dropShadowLeftSrc, -1);
	        mDropShadowTopId = a.getResourceId(R.styleable.DropShadowListView_dropShadowTopSrc, -1);
	        mDropShadowBottomId = a.getResourceId(R.styleable.DropShadowListView_dropShadowBottomSrc, -1);
	 
	        mDropShadowRight = a.getBoolean(R.styleable.DropShadowListView_dropShadowRight, false);
	        mDropShadowLeft = a.getBoolean(R.styleable.DropShadowListView_dropShadowLeft, false);
	        mDropShadowTop = a.getBoolean(R.styleable.DropShadowListView_dropShadowTop, false);
	        mDropShadowBottom = a.getBoolean(R.styleable.DropShadowListView_dropShadowBottom, false);
	    }
	 
	    /*
	     * (non-Javadoc)
	     * 
	     * @see android.widget.ListView#dispatchDraw(android.graphics.Canvas)
	     */
	    @Override
	    protected void dispatchDraw(Canvas canvas) {
	        super.dispatchDraw(canvas);
	 
	        Bitmap bm;
	        if (mDropShadowRight) {
	            bm = getBitmap(mDropShadowRightId);
	            canvas.drawBitmap(bm, null, getDropShadowArea(LOC_RIGHT, canvas, bm), null);
	        }
	 
	        if (mDropShadowLeft) {
	            bm = getBitmap(mDropShadowLeftId);
	            canvas.drawBitmap(bm, null, getDropShadowArea(LOC_LEFT, canvas, bm), null);
	        }
	 
	        if (mDropShadowTop) {
	            bm = getBitmap(mDropShadowTopId);
	            canvas.drawBitmap(bm, null, getDropShadowArea(LOC_TOP, canvas, bm), null);
	        }
	 
	        if (mDropShadowBottom) {
	            bm = getBitmap(mDropShadowBottomId);
	            canvas.drawBitmap(bm, null, getDropShadowArea(LOC_BOTTOM, canvas, bm), null);
	        }
	    }
	 
	    /**
	     * Get the correct bitmap from context resources
	     * 
	     * @param id
	     *            Resource id
	     * @return Bitmap to draw on the view
	     */
	    private Bitmap getBitmap(int id) {
	        return BitmapFactory.decodeResource(getContext().getResources(), id);
	    }
	 
	    /**
	     * Get the Rect to draw the Bitmap in
	     * 
	     * @param loc
	     *            Left, Top, Right or Bottom (0,1,2,3)
	     * @param canvas
	     *            The canvas to we're drawing on
	     * @param bm
	     *            The bitmap we're drawing
	     * @return Rect for the area we are drawing the Bitmap onto the canvas
	     */
	    private Rect getDropShadowArea(int loc, Canvas canvas, Bitmap bm) {
	        switch (loc) {
	            case LOC_LEFT :
	                return new Rect(0, 0, bm.getWidth(), canvas.getHeight());
	            case LOC_TOP :
	                return new Rect(0, 0, canvas.getWidth(), bm.getHeight());
	            case LOC_RIGHT :
	                return new Rect(canvas.getWidth() - bm.getWidth(), 0, canvas.getWidth(), canvas.getHeight());
	            case LOC_BOTTOM :
	                return new Rect(0, canvas.getHeight() - bm.getHeight(), canvas.getWidth(), canvas.getHeight());
	            default :
	                return null;
	        }
	    }

}
