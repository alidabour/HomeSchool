package com.example.ali.homeschool.childClass;

import android.view.View;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;

/**
 * Created by manar_000 on 6/25/2017.
 */

public class CubeOutTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0f ? view.getWidth() : 0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);
    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }}
