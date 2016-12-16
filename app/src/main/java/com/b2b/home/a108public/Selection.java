package com.b2b.home.a108public;

import android.animation.Animator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import io.codetail.animation.ViewAnimationUtils;

/**
 * Created by Abishek on 14-12-2016.
 */

public class Selection {

    void move(int id1,int id2,int id3,Activity activity){

        RelativeLayout rl1,rl2,rl3;
        rl1= (RelativeLayout) activity.findViewById(id1);
        rl2= (RelativeLayout) activity.findViewById(id2);
        rl3= (RelativeLayout) activity.findViewById(id3);
        if(!(rl2.getVisibility()==View.GONE)){
            unreveal(rl2);
        }
        if(!(rl3.getVisibility()==View.GONE)){
            unreveal(rl3);
        }
        reveal(rl1);
    }
    void reveal(final RelativeLayout widgetlayout){
        int cx, cy;
        widgetlayout.setVisibility(View.VISIBLE);
        cx = widgetlayout.getWidth() / 2;
        cy = widgetlayout.getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)

        Animator anim =
                ViewAnimationUtils.createCircularReveal(widgetlayout, cx, cy, 0, finalRadius);
        anim.start();
    }
    void unreveal(final RelativeLayout widgetlayout){
        int cx, cy;
        cx = widgetlayout.getWidth() / 2;
        cy = widgetlayout.getHeight() / 2;
        float finalRadius = (float) Math.hypot(cx, cy);
        Animator anim =
                ViewAnimationUtils.createCircularReveal(widgetlayout, cx, cy, finalRadius, 0);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                widgetlayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
