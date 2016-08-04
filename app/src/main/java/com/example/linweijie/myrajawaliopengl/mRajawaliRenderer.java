package com.example.linweijie.myrajawaliopengl;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.animation.mesh.SkeletalAnimationObject3D;
import org.rajawali3d.animation.mesh.SkeletalAnimationSequence;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.ParsingException;
import org.rajawali3d.loader.md5.LoaderMD5Anim;
import org.rajawali3d.loader.md5.LoaderMD5Mesh;
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
    private SkeletalAnimationObject3D mObject;
    private SkeletalAnimationSequence mSequenceIdle;
    private ArcballCamera arcball;

    public static double INIT_MODEL_X = 0.0f;
    public static double INIT_MODEL_Y = 0.0f;
    public static double INIT_MODEL_Z = 0.0f;
    public static double INIT_MODEL_SCALE = 2.5f;

    public static double INIT_CAMERA_X = 50.0f;
    public static double INIT_CAMERA_Y = 50.0f;
    public static double INIT_CAMERA_Z = 50.0f;

    private VideoTexture mVideoTexture;
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
            /** 載入模型 */
//                final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.awd_suzanne);
//                parser.parse();
//            final LoaderOBJ parser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.watch_obj);
//            parser.parse();
//            watch = parser.getParsedObject();

            /** 模型材質貼皮 */
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
//            // Create media player
//            mMediaPlayer = MediaPlayer.create(getContext(), R.raw.music_viedo);
//
//            // Create video texture and apply to material
//            mVideoTexture = new VideoTexture("VideoTexture", mMediaPlayer);
//
//            Material mVideo = new Material();
//            mVideo.setColorInfluence(0);
//            mVideo.enableLighting(true);
//            mVideo.setDiffuseMethod(new DiffuseMethod.Lambert());
//            mVideo.addTexture(mVideoTexture);
//
//            // Create object
//            watch = new Plane(10f,10f,1,1);
//            watch.setMaterial(mVideo);
//            watch.setDoubleSided(true);
//            watch.setPosition(INIT_MODEL_X, INIT_MODEL_Y + 2, INIT_MODEL_Z);
//
//            watch.setRotZ(-90f); //  Rotate as video texture displays up-side-down
//            watch.setRotY(180f); //  Rotate as video texture displays up-side-down
//            getCurrentScene().addChild(watch);
//            mMediaPlayer.setLooping(true);

            /** Cylinder */
            Material material = new Material();
            material.setColorInfluence(1);
            material.enableLighting(true);
            material.setDiffuseMethod(new DiffuseMethod.Lambert());
            material.setColor(0x990000);
            material.addTexture(new Texture("Watch1", R.drawable.watch001));

            watch = new Cylinder(10, 2, 1, 10);
//            watch = new Cube(1);
            watch.setMaterial(material);
            watch.setPosition(INIT_MODEL_X, INIT_MODEL_Y + 2, INIT_MODEL_Z);
            watch.setScale(INIT_MODEL_SCALE);
            watch.setDoubleSided(true);
            getCurrentScene().addChild(watch);

            /** FBX */
//            LoaderFBX parser = new LoaderFBX(this, R.raw.bacteria2);
//            parser.parse();
//            watch = parser.getParsedObject();
//            getCurrentScene().addChild(watch);

            /** MD5 */
//            LoaderMD5Mesh meshParser = new LoaderMD5Mesh(this, R.raw.boblampclean_mesh);
//            meshParser.parse();
//            mObject = (SkeletalAnimationObject3D) meshParser.getParsedAnimationObject();
//
//            LoaderMD5Anim animParser = new LoaderMD5Anim("boblampclean", this, R.raw.boblampclean_anim);
//            animParser.parse();
//            mSequenceIdle = (SkeletalAnimationSequence) animParser.getParsedAnimationSequence();
//
//            mObject.setAnimationSequence(mSequenceIdle);
//            mObject.setFps(24);
//            mObject.play();
//
//            getCurrentScene().addChild(mObject);

            /** AWD */
//            final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.boblampclean_anim_awd);
//            parser.parse();
//            mObject = (SkeletalAnimationObject3D) parser.getParsedObject();
//            mObject.rotate(Vector3.Y, -90.0);
//
//            LoaderMD5Anim animParser = new LoaderMD5Anim("idle", this, R.raw.boblampclean_anim);
//            animParser.parse();
//
//            mSequenceIdle = (SkeletalAnimationSequence) animParser.getParsedAnimationSequence();
//
//            mObject.setAnimationSequence(0);
//            mObject.setFps(24);
//            mObject.setScale(.04f);
//            mObject.play();
//
//            getCurrentScene().addChild(mObject);

            /** 視角相機設定 */
            arcball = new ArcballCamera(mContext, ((Activity)mContext).findViewById(R.id.rejawali_layout));
            arcball.setPosition(INIT_CAMERA_X, INIT_CAMERA_Y, INIT_CAMERA_Z);
            getCurrentScene().replaceAndSwitchCamera(getCurrentCamera(), arcball);
//        } catch (ParsingException e){
//            Log.d("test", e.getMessage());
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
        if (watch!=null)
            watch.setPosition(x, y, z);

        if (mObject!=null)
            mObject.setPosition(x, y, z);
    }
    public void setModelScale(double scale){
        if (watch!=null)
            watch.setScale(scale);

        if (mObject!=null)
            mObject.setScale(scale);
    }
    public void setModelRoateX(double rotx, double roty, double rotz){

        if (watch!=null)
            watch.setRotation(rotx, roty, rotz);

        if (mObject!=null)
            mObject.setRotation(rotx, roty, rotz);
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
//        mVideoTexture.update();
    }

    public void OnOff(){
        if (mMediaPlayer != null){
            if (mMediaPlayer.isPlaying())
                mMediaPlayer.pause();
            else
                mMediaPlayer.start();
        }
    }
}
