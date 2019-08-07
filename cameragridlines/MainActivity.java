package com.example.cameragridlines;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AndroidCamera";
    private TextureView textureView;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textureView = findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        ProgressBar bar = findViewById(R.id.bar);
        bar.setProgress(50);

        RelativeLayout rl = findViewById(R.id.rl);

        FrameLayout f1_1 = new FrameLayout(this); FrameLayout f1_2 = new FrameLayout(this);
        FrameLayout f1_3 = new FrameLayout(this); FrameLayout f1_4 = new FrameLayout(this);
        FrameLayout f1_5 = new FrameLayout(this); FrameLayout f1_6 = new FrameLayout(this);
        FrameLayout f1_7 = new FrameLayout(this); FrameLayout f1_8 = new FrameLayout(this);
        FrameLayout f1_9 = new FrameLayout(this);
        FrameLayout f2_1 = new FrameLayout(this); FrameLayout f2_2 = new FrameLayout(this);
        FrameLayout f2_3 = new FrameLayout(this); FrameLayout f2_4 = new FrameLayout(this);
        FrameLayout f2_5 = new FrameLayout(this); FrameLayout f2_6 = new FrameLayout(this);
        FrameLayout f2_7 = new FrameLayout(this); FrameLayout f2_8 = new FrameLayout(this);
        FrameLayout f2_9 = new FrameLayout(this);
        FrameLayout f3_1 = new FrameLayout(this); FrameLayout f3_2 = new FrameLayout(this);
        FrameLayout f3_3 = new FrameLayout(this); FrameLayout f3_4 = new FrameLayout(this);
        FrameLayout f3_5 = new FrameLayout(this); FrameLayout f3_6 = new FrameLayout(this);
        FrameLayout f3_7 = new FrameLayout(this); FrameLayout f3_8 = new FrameLayout(this);
        FrameLayout f3_9 = new FrameLayout(this);
        FrameLayout f4_1 = new FrameLayout(this); FrameLayout f4_2 = new FrameLayout(this);
        FrameLayout f4_3 = new FrameLayout(this); FrameLayout f4_4 = new FrameLayout(this);
        FrameLayout f4_5 = new FrameLayout(this); FrameLayout f4_6 = new FrameLayout(this);
        FrameLayout f4_7 = new FrameLayout(this); FrameLayout f4_8 = new FrameLayout(this);
        FrameLayout f4_9 = new FrameLayout(this);
        FrameLayout f5_1 = new FrameLayout(this); FrameLayout f5_2 = new FrameLayout(this);
        FrameLayout f5_3 = new FrameLayout(this); FrameLayout f5_4 = new FrameLayout(this);
        FrameLayout f5_5 = new FrameLayout(this); FrameLayout f5_6 = new FrameLayout(this);
        FrameLayout f5_7 = new FrameLayout(this); FrameLayout f5_8 = new FrameLayout(this);
        FrameLayout f5_9 = new FrameLayout(this);
        FrameLayout f6_1 = new FrameLayout(this); FrameLayout f6_2 = new FrameLayout(this);
        FrameLayout f6_3 = new FrameLayout(this); FrameLayout f6_4 = new FrameLayout(this);
        FrameLayout f6_5 = new FrameLayout(this); FrameLayout f6_6 = new FrameLayout(this);
        FrameLayout f6_7 = new FrameLayout(this); FrameLayout f6_8 = new FrameLayout(this);
        FrameLayout f6_9 = new FrameLayout(this);
        FrameLayout f7_1 = new FrameLayout(this); FrameLayout f7_2 = new FrameLayout(this);
        FrameLayout f7_3 = new FrameLayout(this); FrameLayout f7_4 = new FrameLayout(this);
        FrameLayout f7_5 = new FrameLayout(this); FrameLayout f7_6 = new FrameLayout(this);
        FrameLayout f7_7 = new FrameLayout(this); FrameLayout f7_8 = new FrameLayout(this);
        FrameLayout f7_9 = new FrameLayout(this);
        FrameLayout f8_1 = new FrameLayout(this); FrameLayout f8_2 = new FrameLayout(this);
        FrameLayout f8_3 = new FrameLayout(this); FrameLayout f8_4 = new FrameLayout(this);
        FrameLayout f8_5 = new FrameLayout(this); FrameLayout f8_6 = new FrameLayout(this);
        FrameLayout f8_7 = new FrameLayout(this); FrameLayout f8_8 = new FrameLayout(this);
        FrameLayout f8_9 = new FrameLayout(this);
        FrameLayout f9_1 = new FrameLayout(this); FrameLayout f9_2 = new FrameLayout(this);
        FrameLayout f9_3 = new FrameLayout(this); FrameLayout f9_4 = new FrameLayout(this);
        FrameLayout f9_5 = new FrameLayout(this); FrameLayout f9_6 = new FrameLayout(this);
        FrameLayout f9_7 = new FrameLayout(this); FrameLayout f9_8 = new FrameLayout(this);
        FrameLayout f9_9 = new FrameLayout(this);
        FrameLayout f10_1 = new FrameLayout(this); FrameLayout f10_2 = new FrameLayout(this);
        FrameLayout f10_3 = new FrameLayout(this); FrameLayout f10_4 = new FrameLayout(this);
        FrameLayout f10_5 = new FrameLayout(this); FrameLayout f10_6 = new FrameLayout(this);
        FrameLayout f10_7 = new FrameLayout(this); FrameLayout f10_8 = new FrameLayout(this);
        FrameLayout f10_9 = new FrameLayout(this);
        FrameLayout f11_1 = new FrameLayout(this); FrameLayout f11_2 = new FrameLayout(this);
        FrameLayout f11_3 = new FrameLayout(this); FrameLayout f11_4 = new FrameLayout(this);
        FrameLayout f11_5 = new FrameLayout(this); FrameLayout f11_6 = new FrameLayout(this);
        FrameLayout f11_7 = new FrameLayout(this); FrameLayout f11_8 = new FrameLayout(this);
        FrameLayout f11_9 = new FrameLayout(this);
        FrameLayout f12_1 = new FrameLayout(this); FrameLayout f12_2 = new FrameLayout(this);
        FrameLayout f12_3 = new FrameLayout(this); FrameLayout f12_4 = new FrameLayout(this);
        FrameLayout f12_5 = new FrameLayout(this); FrameLayout f12_6 = new FrameLayout(this);
        FrameLayout f12_7 = new FrameLayout(this); FrameLayout f12_8 = new FrameLayout(this);
        FrameLayout f12_9 = new FrameLayout(this);
        FrameLayout f13_1 = new FrameLayout(this); FrameLayout f13_2 = new FrameLayout(this);
        FrameLayout f13_3 = new FrameLayout(this); FrameLayout f13_4 = new FrameLayout(this);
        FrameLayout f13_5 = new FrameLayout(this); FrameLayout f13_6 = new FrameLayout(this);
        FrameLayout f13_7 = new FrameLayout(this); FrameLayout f13_8 = new FrameLayout(this);
        FrameLayout f13_9 = new FrameLayout(this);
        FrameLayout f14_1 = new FrameLayout(this); FrameLayout f14_2 = new FrameLayout(this);
        FrameLayout f14_3 = new FrameLayout(this); FrameLayout f14_4 = new FrameLayout(this);
        FrameLayout f14_5 = new FrameLayout(this); FrameLayout f14_6 = new FrameLayout(this);
        FrameLayout f14_7 = new FrameLayout(this); FrameLayout f14_8 = new FrameLayout(this);
        FrameLayout f14_9 = new FrameLayout(this);
        FrameLayout f15_1 = new FrameLayout(this); FrameLayout f15_2 = new FrameLayout(this);
        FrameLayout f15_3 = new FrameLayout(this); FrameLayout f15_4 = new FrameLayout(this);
        FrameLayout f15_5 = new FrameLayout(this); FrameLayout f15_6 = new FrameLayout(this);
        FrameLayout f15_7 = new FrameLayout(this); FrameLayout f15_8 = new FrameLayout(this);
        FrameLayout f15_9 = new FrameLayout(this);
        FrameLayout f16_1 = new FrameLayout(this); FrameLayout f16_2 = new FrameLayout(this);
        FrameLayout f16_3 = new FrameLayout(this); /*FrameLayout f16_4 = new FrameLayout(this);
        FrameLayout f16_5 = new FrameLayout(this); FrameLayout f16_6 = new FrameLayout(this);
        FrameLayout f16_7 = new FrameLayout(this); FrameLayout f16_8 = new FrameLayout(this);
        FrameLayout f16_9 = new FrameLayout(this);*/

        FrameLayout[] frames = {f1_1, f1_2, f1_3, f1_4, f1_5, f1_6, f1_7, f1_8, f1_9, f2_1, f2_2, f2_3, f2_4, f2_5, f2_6, f2_7, f2_8, f2_9,
                f3_1, f3_2, f3_3, f3_4, f3_5, f3_6, f3_7, f3_8, f3_9, f4_1, f4_2, f4_3, f4_4, f4_5, f4_6, f4_7, f4_8, f4_9,
                f5_1, f5_2, f5_3, f5_4, f5_5, f5_6, f5_7, f5_8, f5_9, f6_1, f6_2, f6_3, f6_4, f6_5, f6_6, f6_7, f6_8, f6_9,
                f7_1, f7_2, f7_3, f7_4, f7_5, f7_6, f7_7, f7_8, f7_9, f8_1, f8_2, f8_3, f8_4, f8_5, f8_6, f8_7, f8_8, f8_9,
                f9_1, f9_2, f9_3, f9_4, f9_5, f9_6, f9_7, f9_8, f9_9, f10_1, f10_2, f10_3, f10_4, f10_5, f10_6, f10_7, f10_8, f10_9,
                f11_1, f11_2, f11_3, f11_4, f11_5, f11_6, f11_7, f11_8, f11_9, f12_1, f12_2, f12_3, f12_4, f12_5, f12_6, f12_7, f12_8, f12_9,
                f13_1, f13_2, f13_3, f13_4, f13_5, f13_6, f13_7, f13_8, f13_9, f14_1, f14_2, f14_3, f14_4, f14_5, f14_6, f14_7, f14_8, f14_9,
                f15_1, f15_2, f15_3, f15_4, f15_5, f15_6, f15_7, f15_8, f15_9, f16_1, f16_2, f16_3};


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;

        int ctr = 0;

        for (FrameLayout frame : frames)
        {
            frame.setBackgroundResource(R.drawable.rectangle_green);
            rl.addView(frame, getLayoutParamsFor(ctr, w/9, h/16));
            frame.getLayoutParams().width = w/9;
            frame.getLayoutParams().height = h/16;

            ctr++;
        }

        f5_5.setBackgroundResource(R.drawable.rectangle_red);
        f4_4.setBackgroundResource(R.drawable.rectangle_yellow);
        f4_5.setBackgroundResource(R.drawable.rectangle_yellow);
        f4_6.setBackgroundResource(R.drawable.rectangle_yellow);
        f5_4.setBackgroundResource(R.drawable.rectangle_yellow);
        f5_6.setBackgroundResource(R.drawable.rectangle_yellow);
        f6_4.setBackgroundResource(R.drawable.rectangle_yellow);
        f6_5.setBackgroundResource(R.drawable.rectangle_yellow);
        f6_6.setBackgroundResource(R.drawable.rectangle_yellow);


    }

    private RelativeLayout.LayoutParams getLayoutParamsFor(final int i, int w, int h) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(w * (i % 9), h * (i / 9), 0, 0);
        return params;
    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            //open your camera here
            openCamera();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Transform you image captured size according to the surface width and height
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };
    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            //This is called when the camera is open
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreview();
        }

        @Override
        public void onDisconnected(CameraDevice camera) {
            cameraDevice.close();
        }

        @Override
        public void onError(CameraDevice camera, int error) {
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    protected void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    protected void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    protected void createCameraPreview() {
        try {
            SurfaceTexture texture = textureView.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            captureRequestBuilder.addTarget(surface);
            cameraDevice.createCaptureSession(Collections.singletonList(surface), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;
                    updatePreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(MainActivity.this, "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void openCamera() {
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {
            assert manager != null;
            String cameraId = manager.getCameraIdList()[0];
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = map.getOutputSizes(SurfaceTexture.class)[0];
            // Add permission for camera and let user grant the permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
                return;
            }
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "openCamera X");
    }

    protected void updatePreview() {
        if (null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        captureRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(captureRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void closeCamera() {
        if (null != cameraDevice) {
            cameraDevice.close();
            cameraDevice = null;
        }
        if (null != imageReader) {
            imageReader.close();
            imageReader = null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(MainActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        startBackgroundThread();
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }

    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");
        closeCamera();
        stopBackgroundThread();
        super.onPause();
    }
}
