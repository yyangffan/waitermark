package com.superc.waitmarket.test;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.superc.waitmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.item_magapb_check)
    ImageView mItemMagapbCheck;
    @BindView(R.id.constraintLayout11)
    ConstraintLayout mConstraintLayout;
    @BindView(R.id.item_magapb_edt)
    TextView mtv;
    @BindView(R.id.item_magapb_posi)
    TextView item_magapb_posi;
    private boolean is_go = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        final ObjectAnimator animator1_con = ObjectAnimator.ofFloat(mConstraintLayout, "translationX", 0f, 80f);
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(mtv, "translationX", 0f, 80f);

        final ObjectAnimator animator2_con = ObjectAnimator.ofFloat(mConstraintLayout, "translationX", 80f, 0f);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(mtv, "translationX", 80f, 0f);


        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_go) {
                    animator1_con.start();
                    animator1.start();
//                    mtv.setVisibility(View.GONE);
//                    animator_pos.start();
//                    mItemMagapbCheck.setVisibility(View.VISIBLE);
                } else {
                    animator2_con.start();
                    animator2.start();
//                    mtv.setVisibility(View.VISIBLE);
//                    animator_back.start();
//                    mConstraintLayout.startAnimation(anima_back);
//                    mItemMagapbCheck.setVisibility(View.GONE);
                }
                is_go = !is_go;
            }
        });
    }

}
