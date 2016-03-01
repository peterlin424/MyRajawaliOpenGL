package com.example.linweijie.myrajawaliopengl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.rajawali3d.surface.IRajawaliSurface;
import org.rajawali3d.surface.RajawaliSurfaceView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout ll_panel, ll_roate;
    private TextView tv_switch_type;
    private LinearLayout ll_scale;
    private SeekBar sb_x, sb_y, sb_z, sb_scale, sb_rx, sb_ry, sb_rz;
    private mRajawaliRenderer renderer;

    private boolean isShow = true;
    private int seekbar_full = 100;
    private int m_x=seekbar_full/2, m_y=seekbar_full/2, m_z=seekbar_full/2, m_scale=seekbar_full/2;
    private int c_x=seekbar_full/2, c_y=seekbar_full/2, c_z=seekbar_full/2;
    private int rx=seekbar_full/2, ry = seekbar_full/2, rz = seekbar_full/2;
    private String SWITCH_MODE = Pub.CHOOSE_MODEL;

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
        tv_switch_type = (TextView)findViewById(R.id.tv_switch_type);

        ll_roate = (LinearLayout)findViewById(R.id.ll_roate);
        ll_panel = (LinearLayout)findViewById(R.id.ll_panel);
        ll_scale = (LinearLayout)findViewById(R.id.ll_scale);
        sb_rx = (SeekBar)findViewById(R.id.sb_rx);
        sb_ry = (SeekBar)findViewById(R.id.sb_ry);
        sb_rz = (SeekBar)findViewById(R.id.sb_rz);
        sb_x = (SeekBar)findViewById(R.id.sb_x);
        sb_y = (SeekBar)findViewById(R.id.sb_y);
        sb_z = (SeekBar)findViewById(R.id.sb_z);
        sb_scale = (SeekBar)findViewById(R.id.sb_scale);
        Button bt_show = (Button)findViewById(R.id.bt_show);
        Button bt_reset = (Button)findViewById(R.id.bt_reset);
        Button bt_switch = (Button)findViewById(R.id.bt_switch);

        SWITCH_MODE = Pub.CHOOSE_MODEL;

        tv_switch_type.setText(SWITCH_MODE);

        bt_show.setOnClickListener(onClickListener);
        bt_reset.setOnClickListener(onClickListener);
        bt_switch.setOnClickListener(onClickListener);

        sb_rx.setOnSeekBarChangeListener(seekBarChangeListener2);
        sb_ry.setOnSeekBarChangeListener(seekBarChangeListener2);
        sb_rz.setOnSeekBarChangeListener(seekBarChangeListener2);
        sb_x.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_y.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_z.setOnSeekBarChangeListener(seekBarChangeListener);
        sb_scale.setOnSeekBarChangeListener(seekBarChangeListener);

        sb_rx.setProgress(seekbar_full / 2);
        sb_ry.setProgress(seekbar_full / 2);
        sb_rz.setProgress(seekbar_full / 2);
        sb_x.setProgress(seekbar_full / 2);
        sb_y.setProgress(seekbar_full / 2);
        sb_z.setProgress(seekbar_full / 2);
        sb_scale.setProgress(seekbar_full / 2);

        ll_roate.setVisibility(View.VISIBLE);
        ll_scale.setVisibility(View.VISIBLE);

        m_x=seekbar_full/2; m_y=seekbar_full/2; m_z=seekbar_full/2; m_scale=seekbar_full/2;
        c_x=seekbar_full/2; c_y=seekbar_full/2; c_z=seekbar_full/2;
        rx=seekbar_full/2; ry=seekbar_full/2; rz=seekbar_full/2;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_show:
                    if (isShow) {
                        ll_panel.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.panel_hide));
                        ll_panel.setVisibility(View.GONE);
                        ll_roate.setVisibility(View.GONE);
                        isShow = false;
                    }else{
                        ll_panel.setVisibility(View.VISIBLE);
                        ll_roate.setVisibility(View.VISIBLE);
                        isShow = true;
                    }
                    break;
                case R.id.bt_reset:
                    init();
                    renderer.setCameraPosition((double) c_x, (double) c_y, (double) c_z);
                    break;
                case R.id.bt_switch:
                    int tmp_x = 0, tmp_y = 0, tmp_z = 0;
                    switch (SWITCH_MODE){
                        case Pub.CHOOSE_CAMERA:
                            SWITCH_MODE = Pub.CHOOSE_MODEL;
                            ll_scale.setVisibility(View.VISIBLE);
                            ll_roate.setVisibility(View.VISIBLE);
                            tmp_x = m_x;
                            tmp_y = m_y;
                            tmp_z = m_z;
                            break;
                        case Pub.CHOOSE_MODEL:
                            SWITCH_MODE = Pub.CHOOSE_CAMERA;
                            ll_scale.setVisibility(View.GONE);
                            ll_roate.setVisibility(View.GONE);
                            tmp_x = c_x;
                            tmp_y = c_y;
                            tmp_z = c_z;
                            break;
                    }
                    tv_switch_type.setText(SWITCH_MODE);
                    sb_x.setProgress(tmp_x);
                    sb_y.setProgress(tmp_y);
                    sb_z.setProgress(tmp_z);
                    break;
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            switch (SWITCH_MODE){
                case Pub.CHOOSE_MODEL:
                    switch (seekBar.getId()){
                        case R.id.sb_x:
                            m_x = progress;
                            PLog.e(Pub.TAG, "x : " + String.valueOf(progress));
                            break;

                        case R.id.sb_y:
                            m_y = progress;
                            PLog.e(Pub.TAG, "y : " + String.valueOf(progress));
                            break;

                        case R.id.sb_z:
                            m_z = progress;
                            PLog.e(Pub.TAG, "z : " + String.valueOf(progress));
                            break;

                        case R.id.sb_scale:
                            m_scale = progress;
                            renderer.setModelScale((double)(m_scale*5.0f)/100);
                            PLog.e(Pub.TAG, "scale : " + String.valueOf(progress));
                            break;
                    }
                    renderer.setModelPosition((double)(m_x-50)/10, (double)(m_y-50)/10, (double)(m_z-50)/10);
                    break;


                case Pub.CHOOSE_CAMERA:
                    switch (seekBar.getId()){
                        case R.id.sb_x:
                            c_x = progress;
                            PLog.e(Pub.TAG, "x : " + String.valueOf(progress));
                            break;

                        case R.id.sb_y:
                            c_y = progress;
                            PLog.e(Pub.TAG, "y : " + String.valueOf(progress));
                            break;

                        case R.id.sb_z:
                            c_z = progress;
                            PLog.e(Pub.TAG, "z : " + String.valueOf(progress));
                            break;
                    }
                    renderer.setCameraPosition((double) c_x, (double) c_y, (double) c_z);
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

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener2 = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            switch (seekBar.getId()){
                case R.id.sb_rx:
                    rx = progress;
                    PLog.e(Pub.TAG, "rx : " + String.valueOf(progress));
                    break;
                case R.id.sb_ry:
                    ry = progress;
                    PLog.e(Pub.TAG, "ry : " + String.valueOf(progress));
                    break;
                case R.id.sb_rz:
                    rz = progress;
                    PLog.e(Pub.TAG, "rz : " + String.valueOf(progress));
                    break;
            }
            renderer.setModelRoateX((double)(rx-50)*180/50, (double)(ry-50)*180/50, (double)(rz-50)*180/50);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
