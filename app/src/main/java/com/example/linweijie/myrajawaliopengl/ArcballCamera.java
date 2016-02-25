package com.example.linweijie.myrajawaliopengl;

import android.content.Context;
import android.util.Log;
import android.view.View;

import org.rajawali3d.Camera;
import org.rajawali3d.Object3D;
import org.rajawali3d.math.MathUtil;
import org.rajawali3d.math.Matrix4;
import org.rajawali3d.math.Quaternion;
import org.rajawali3d.math.vector.Vector2;
import org.rajawali3d.math.vector.Vector3;

/**
 * Created by linweijie on 2/25/16.
 */
public class ArcballCamera extends Camera {

    private Context mContext;
    //    private ScaleGestureDetector mScaleDetector;
//    private View.OnTouchListener mGestureListener;
//    private GestureDetector mDetector;
    private View mView;
    private boolean mIsRotating;
    private boolean mIsScaling;
    private Vector3 mCameraStartPos;
    private Vector3 mPrevSphereCoord;
    private Vector3 mCurrSphereCoord;
    private Vector2 mPrevScreenCoord;
    private Vector2 mCurrScreenCoord;
    private Quaternion mStartOrientation;
    private Quaternion mCurrentOrientation;
    private Object3D mEmpty;
    private Object3D mTarget;
    private Matrix4 mScratchMatrix;
    private Vector3 mScratchVector;
    private double mStartFOV;

    public ArcballCamera(Context context, View view) {
        this(context, view, null);
    }

    public ArcballCamera(Context context, View view, Object3D target) {
        super();
        mContext = context;
        mTarget = target;
        mView = view;
        initialize();
    }

    private void initialize() {
        mStartFOV = mFieldOfView;
        mLookAtEnabled = true;
        setLookAt(0, 0, 0);
        mEmpty = new Object3D();
        mScratchMatrix = new Matrix4();
        mScratchVector = new Vector3();
        mCameraStartPos = new Vector3();
        mPrevSphereCoord = new Vector3();
        mCurrSphereCoord = new Vector3();
        mPrevScreenCoord = new Vector2();
        mCurrScreenCoord = new Vector2();
        mStartOrientation = new Quaternion();
        mCurrentOrientation = new Quaternion();
    }

    @Override
    public void setProjectionMatrix(int width, int height) {
        super.setProjectionMatrix(width, height);
    }

    private void mapToSphere(final float x, final float y, Vector3 out)
    {
        float lengthSquared = x * x + y * y;
        if (lengthSquared > 1)
        {
            out.setAll(x, y, 0);
            out.normalize();
        }
        else
        {
            out.setAll(x, y, Math.sqrt(1 - lengthSquared));
        }
    }

    private void mapToScreen(final float x, final float y, Vector2 out)
    {
        out.setX((2 * x - mLastWidth) / mLastWidth);
        out.setY(-(2 * y - mLastHeight) / mLastHeight);
        Log.i("mapToScreen", "x : " + String.valueOf(out.getX()));
        Log.i("mapToScreen", "y : " + String.valueOf(out.getY()));
    }

    private void startRotation(final float x, final float y)
    {
        mapToScreen(x, y, mPrevScreenCoord);

        mCurrScreenCoord.setAll(mPrevScreenCoord.getX(), mPrevScreenCoord.getY());

        mIsRotating = true;
    }

    private void updateRotation(final float x, final float y)
    {
        mapToScreen(x, y, mCurrScreenCoord);

        applyRotation();
    }

    private void endRotation()
    {
        mStartOrientation.multiply(mCurrentOrientation);
    }

    private void applyRotation()
    {
        if (mIsRotating)
        {
            mapToSphere((float) mPrevScreenCoord.getX(), (float) mPrevScreenCoord.getY(), mPrevSphereCoord);
            mapToSphere((float) mCurrScreenCoord.getX(), (float) mCurrScreenCoord.getY(), mCurrSphereCoord);

            Vector3 rotationAxis = mPrevSphereCoord.clone();
            rotationAxis.cross(mCurrSphereCoord);
            rotationAxis.normalize();

            double rotationAngle = Math.acos(Math.min(1, mPrevSphereCoord.dot(mCurrSphereCoord)));
            mCurrentOrientation.fromAngleAxis(rotationAxis, MathUtil.radiansToDegrees(rotationAngle));
            mCurrentOrientation.normalize();

            Quaternion q = new Quaternion(mStartOrientation);
            q.multiply(mCurrentOrientation);

            mEmpty.setOrientation(q);
        }
    }

    @Override
    public Matrix4 getViewMatrix() {
        Matrix4 m = super.getViewMatrix();

        if(mTarget != null) {
            mScratchMatrix.identity();
            mScratchMatrix.translate(mTarget.getPosition());
            m.multiply(mScratchMatrix);
        }

        mScratchMatrix.identity();
        mScratchMatrix.rotate(mEmpty.getOrientation());
        m.multiply(mScratchMatrix);

        if(mTarget != null) {
            mScratchVector.setAll(mTarget.getPosition());
            mScratchVector.inverse();

            mScratchMatrix.identity();
            mScratchMatrix.translate(mScratchVector);
            m.multiply(mScratchMatrix);
        }

        return m;
    }

    public void setFieldOfView(double fieldOfView) {
        synchronized (mFrustumLock) {
            mStartFOV = fieldOfView;
            super.setFieldOfView(fieldOfView);
        }
    }
}
