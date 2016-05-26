package com.example.linweijie.myrajawaliopengl;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;

import org.rajawali3d.materials.textures.VideoTexture;
import org.rajawali3d.primitives.Cube;
import org.rajawali3d.primitives.Plane;
import org.rajawali3d.util.debugvisualizer.DebugVisualizer;
import org.rajawali3d.util.debugvisualizer.GridFloor;

/**
 * Created by linweijie on 2/25/16.
 */
public class mRajawaliRenderer extends org.rajawali3d.renderer.RajawaliRenderer {

    public static int FRAME_RATE = 60;
    public Context context;

    private Object3D watch;
    private ArcballCamera arcball;

    public static double INIT_MODEL_X = 0.0f;
    public static double INIT_MODEL_Y = 0.0f;
    public static double INIT_MODEL_Z = 0.0f;
    public static double INIT_MODEL_SCALE = 2.5f;

    public static double INIT_CAMERA_X = 50.0f;
    public static double INIT_CAMERA_Y = 50.0f;
    public static double INIT_CAMERA_Z = 50.0f;

    private VideoTexture  mVideoTexture;
    private MediaPlayer mMediaPlayer;

    public mRajawaliRenderer(Context context) {
        super(context);
        this.context = context;
        setFrameRate(FRAME_RATE);

    }

    @Override
    protected void initScene() {
        try {
            /** 定向光 */
            DirectionalLight directionalLight;
            directionalLight = new DirectionalLight(0.1f, -10.0f, 0.0f); // 座標
            directionalLight.setColor(1.0f, 1.0f, 1.0f);             // 燈光色
            directionalLight.setPower(2);                            // 燈光強度
            getCurrentScene().addLight(directionalLight);

            PointLight mLight = new PointLight();//物件本身亮度
            mLight.setPosition(0.1f, 40.0f, 0.0f);//設定光亮動畫位置
            mLight.setPower(25); //3D物件亮度調整
            getCurrentScene().addLight(mLight);

            /** debug 隔線 */
            DebugVisualizer debugViz = new DebugVisualizer(this);
            debugViz.addChild(new GridFloor());
            getCurrentScene().addChild(debugViz);


            /**
             * 模型 mtl obj Object3D
             * */
//            /** 載入模型 */
////                final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.awd_suzanne);
////                parser.parse();
//            final LoaderOBJ parser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.watch_obj);
//            parser.parse();
//
//            watch = parser.getParsedObject();
//
//            /** 模型材質貼皮 */
//            Material material = new Material();
//            material.enableLighting(true);
//            material.setDiffuseMethod(new DiffuseMethod.Lambert());
//            material.addTexture(new Texture("Watch1", R.drawable.watch001));
//            material.addTexture(new Texture("Watch2", R.drawable.watch002));
//            material.setColor(0x990000);
//
//            watch.setMaterial(material);
//            watch.setPosition(INIT_MODEL_X, INIT_MODEL_Y, INIT_MODEL_Z);
////            watch.setRotation(INIT_MODEL_X, INIT_MODEL_Y, INIT_MODEL_Z, INIT_MODEL_ROATE);
//            watch.setScale(INIT_MODEL_SCALE);
//            getCurrentScene().addChild(watch);

            /**
             * 影片 plane
             * */
            // Create media player
            mMediaPlayer = MediaPlayer.create(getContext(), R.raw.music_viedo);

            // Create video texture and apply to material
            mVideoTexture = new VideoTexture("VideoTexture", mMediaPlayer);
            Material mVideo = new Material();
            mVideo.setColorInfluence(0);
            mVideo.addTexture(mVideoTexture);

            // Create object
            watch = new Plane(3f,3f,5,5);
            watch.setMaterial(mVideo);
            watch.setPosition(INIT_MODEL_X, INIT_MODEL_Y + 2, INIT_MODEL_Z);
            watch.setScale(INIT_MODEL_SCALE);

            watch.setRotZ(-90f); //  Rotate as video texture displays up-side-down
            watch.setRotY(180f); //  Rotate as video texture displays up-side-down
            getCurrentScene().addChild(watch);
            mMediaPlayer.setLooping(true);

            /** 視角相機設定 */
            arcball = new ArcballCamera(mContext, ((Activity)mContext).findViewById(R.id.rejawali_layout));
            arcball.setPosition(INIT_CAMERA_X, INIT_CAMERA_Y, INIT_CAMERA_Z);
            getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

    }

    @Override
    public void onTouchEvent(MotionEvent event) {

    }

    /**
     * Model
     * */
    public void setModelPosition(double x, double y, double z){
        watch.setPosition(x, y, z);
    }
    public void setModelScale(double scale){
        watch.setScale(scale);
    }
    public void setModelRoateX(double rotx, double roty, double rotz){
        try {
            watch.setRotation(rotx, roty, rotz);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Camera
     * */
    public void setCameraPosition(double x, double y, double z){
        arcball.setPosition(x, y, z);
    }

    /**
     * Other
     * */
    @Override
    protected void onRender(long ellapsedRealtime, double deltaTime) {
        super.onRender(ellapsedRealtime, deltaTime);
        mVideoTexture.update();
    }

    public void OnOff(){
        if (mMediaPlayer.isPlaying())
            mMediaPlayer.pause();
        else
            mMediaPlayer.start();
    }
}
