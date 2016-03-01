package com.example.linweijie.myrajawaliopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll_panel;
    TextView tv_switch_type;
    LinearLayout ll_roate, ll_scale;

    mRajawaliRenderer renderer;

    boolean isShow = true;
    int seekbar_full = 100;
    int x, y, z, roate, scale;
    String SWITCH_MODE = Pub.CHOOSE_MODEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        final RajawaliSurfaceView surface = new RajawaliSurfaceView(this);
        surface.setFrameRate((double) mRajawaliRenderer.FRAME_RATE);
        surface.setRenderMode(IRajawaliSurface.RENDERMODE_WHEN_DIRTY);

        RelativeLayout rejawali_layout = (RelativeLayout)findViewById(R.id.rejawali_layout);
        rejawali_layout.addView(surface);

        renderer = new mRajawaliRenderer(this);
        surface.setSurfaceRenderer(renderer);

        //
        init();
    }
    private void init(){
        SWITCH_MODE = Pub.CHOOSE_MODEL;
        tv_switch_type = (TextView)findViewById(R.id.tv_switch_type);
        tv_switch_type.setText(SWITCH_MODE);

        ll_panel = (LinearLayout)findViewById(R.id.ll_panel);
        Button bt_show = (Button)findViewById(R.id.bt_show);
        Button bt_reset = (Button)findViewById(R.id.bt_reset);
        Button bt_switch = (Button)findViewById(R.id.bt_switch);

        bt_show.setOnClickListener(onClickListener);
        bt_reset.setOnClickListener(onClickListener);
        bt_switch.setOnClickListener(onClickListener);

        ll_roate = (LinearLayout)findViewById(R.id.ll_roate);
        ll_scale = (LinearLayout)findViewById(R.id.ll_scale);
        SeekBar sb_x = (SeekBar)findViewById(R.id.sb_x);
        SeekBar sb_y = (SeekBar)findViewById(R.id.sb_y);
        SeekBar sb_z = (SeekBar)findViewById(R.id.sb_z);
        SeekBar sb_roate = (SeekBar)findViewById(R.id.sb_roate);
        SeekBar sb_scale = (SeekBar)findViewById(R.id.sb_scale);

        sb_x.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_y.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_z.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_roate.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_scale.setOnSeekBarChangeListener(seekBarChangeListener);

        sb_x.setProgress(seekbar_full / 2);
        sb_y.setProgress(seekbar_full / 2);
        sb_z.setProgress(seekbar_full/2);
        sb_roate.setProgress(seekbar_full/2);
        sb_scale.setProgress(seekbar_full/2);
        ll_roate.setVisibility(View.VISIBLE);
        ll_scale.setVisibility(View.VISIBLE);
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
                    init();
                    break;
                case R.id.bt_switch:
                    //TODO 切換選擇：相機/物件
                    switch (SWITCH_MODE){
                        case Pub.CHOOSE_CAMERA:
                            SWITCH_MODE = Pub.CHOOSE_MODEL;
                            ll_roate.setVisibility(View.VISIBLE);
                            ll_scale.setVisibility(View.VISIBLE);
                            break;
                        case Pub.CHOOSE_MODEL:
                            SWITCH_MODE = Pub.CHOOSE_CAMERA;
                            ll_roate.setVisibility(View.GONE);
                            ll_scale.setVisibility(View.GONE);
                            break;
                    }
                    tv_switch_type.setText(SWITCH_MODE);
                    break;
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        //TODO 移動手錶或相機位置
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.sb_x:
                    x = progress;
                    PLog.e(Pub.TAG, "x : " + String.valueOf(progress));
                    break;
                case R.id.sb_y:
                    y = progress;
                    PLog.e(Pub.TAG, "y : " + String.valueOf(progress));
                    break;
                case R.id.sb_z:
                    z = progress;
                    PLog.e(Pub.TAG, "z : " + String.valueOf(progress));
                    break;
                case R.id.sb_roate:
                    roate = progress;
                    PLog.e(Pub.TAG, "roate : " + String.valueOf(progress));
                    break;
                case R.id.sb_scale:
                    scale = progress;
                    PLog.e(Pub.TAG, "scale : " + String.valueOf(progress));
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
