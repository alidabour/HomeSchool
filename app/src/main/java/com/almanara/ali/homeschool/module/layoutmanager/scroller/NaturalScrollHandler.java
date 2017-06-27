package com.almanara.ali.homeschool.module.layoutmanager.scroller;

import android.view.View;

import com.almanara.ali.homeschool.module.layoutmanager.circle_helper.quadrant_helper.QuadrantHelper;
import com.almanara.ali.homeschool.module.layoutmanager.layouter.Layouter;

/**
 * Created by danylo.volokh on 12/9/2015.
 *
 * This scroll handler scrolls every view by the offset that user scrolled with his finger.
 */
public class NaturalScrollHandler extends ScrollHandler {

    private final ScrollHandlerCallback mCallback;

    public NaturalScrollHandler(ScrollHandlerCallback callback, QuadrantHelper quadrantHelper, Layouter layouter) {
        super(callback, quadrantHelper, layouter);
        mCallback = callback;
    }

    @Override
    protected void scrollViews(View firstView, int delta) {
        for (int indexOfView = 0; indexOfView < mCallback.getChildCount(); indexOfView++) {
            View view = mCallback.getChildAt(indexOfView);
            scrollSingleViewVerticallyBy(view, delta);
        }
    }
