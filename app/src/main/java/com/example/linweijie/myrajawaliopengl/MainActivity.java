package com.example.linweijie.myrajawaliopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_panel;

    mRajawaliRenderer renderer;

    boolean isShow = true;
    int x, y, z, roate, scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate((double) mRajawaliRenderer.FRAME_RATE);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        RelativeLayout rejawali_layout = (RelativeLayout)findViewById(R.id.rejawali_layout);
        rejawali_layout.addView(surface);

        renderer = new mRajawaliRenderer(this);
        surface.setSurfaceRenderer(renderer);



        ll_panel = (LinearLayout)findViewById(R.id.ll_panel);
        Button bt_show = (Button)findViewById(R.id.bt_show);
        Button bt_reset = (Button)findViewById(R.id.bt_reset);
        Button bt_switch = (Button)findViewById(R.id.bt_switch);

        bt_show.setOnClickListener(onClickListener);
        bt_reset.setOnClickListener(onClickListener);
        bt_switch.setOnClickListener(onClickListener);

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_show:
                    if (isShow) {
                        ll_panel.setVisibility(View.GONE);
                        isShow = false;
                    }else{
                        ll_panel.setVisibility(View.VISIBLE);
                        isShow = true;
                    }
                    break;
                case R.id.bt_reset:
                    //TODO 重置位置 和 panel
                    break;
                case R.id.bt_switch:
                    //TODO 切換選擇：相機/物件
                    break;
            }
        }
    };

}
