package com.example.linweijie.myrajawaliopengl;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;

import org.rajawali3d.Object3D;
import org.rajawali3d.lights.DirectionalLight;
import org.rajawali3d.lights.PointLight;
import org.rajawali3d.loader.LoaderOBJ;
import org.rajawali3d.materials.Material;
import org.rajawali3d.materials.methods.DiffuseMethod;
import org.rajawali3d.materials.textures.Texture;
import org.rajawali3d.util.debugvisualizer.DebugVisualizer;
import org.rajawali3d.util.debugvisualizer.GridFloor;

/**
 * Created by linweijie on 2/25/16.
 */
public class mRajawaliRenderer extends org.rajawali3d.renderer.RajawaliRenderer {

    public static int FRAME_RATE = 60;
    public Context context;

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

            /** 載入模型 */
//                final LoaderAWD parser = new LoaderAWD(mContext.getResources(), mTextureManager, R.raw.awd_suzanne);
//                parser.parse();
            final LoaderOBJ parser = new LoaderOBJ(mContext.getResources(), mTextureManager, R.raw.watch_obj);
            parser.parse();

            final Object3D monkey = parser.getParsedObject();

            /** 模型材質貼皮 */
            Material material = new Material();
            material.enableLighting(true);
            material.setDiffuseMethod(new DiffuseMethod.Lambert());
            material.addTexture(new Texture("Watch1", R.drawable.watch001));
            material.addTexture(new Texture("Watch2", R.drawable.watch002));
            material.setColor(0x990000);

            monkey.setMaterial(material);
            monkey.setScale(2.5f);
            getCurrentScene().addChild(monkey);

            /** 視角相機設定 */
            ArcballCamera arcball = new ArcballCamera(mContext, ((Activity)mContext).findViewById(R.id.rejawali_layout));
            arcball.setPosition(0.1f, 40.0f, 0.0f);
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
    public void setModelPosition(int x, int y, int z){

    }
    public void setModelRoate(int angle){

    }
    public void setModelScale(int scale){

    }

    /**
     * Camera
     * */
    public void setCameraPosition(int x, int y, int z){

    }
}
