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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
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

        // *****************************************************************************************

        FrameLayout f1_1 = findViewById(R.id.f1_1); FrameLayout f1_2 = findViewById(R.id.f1_2);
        FrameLayout f1_3 = findViewById(R.id.f1_3); FrameLayout f1_4 = findViewById(R.id.f1_4);
        FrameLayout f1_5 = findViewById(R.id.f1_5); FrameLayout f1_6 = findViewById(R.id.f1_6);
        FrameLayout f1_7 = findViewById(R.id.f1_7); FrameLayout f1_8 = findViewById(R.id.f1_8);
        FrameLayout f1_9 = findViewById(R.id.f1_9);
        FrameLayout f2_1 = findViewById(R.id.f2_1); FrameLayout f2_2 = findViewById(R.id.f2_2);
        FrameLayout f2_3 = findViewById(R.id.f2_3); FrameLayout f2_4 = findViewById(R.id.f2_4);
        FrameLayout f2_5 = findViewById(R.id.f2_5); FrameLayout f2_6 = findViewById(R.id.f2_6);
        FrameLayout f2_7 = findViewById(R.id.f2_7); FrameLayout f2_8 = findViewById(R.id.f2_8);
        FrameLayout f2_9 = findViewById(R.id.f2_9);
        FrameLayout f3_1 = findViewById(R.id.f3_1); FrameLayout f3_2 = findViewById(R.id.f3_2);
        FrameLayout f3_3 = findViewById(R.id.f3_3); FrameLayout f3_4 = findViewById(R.id.f3_4);
        FrameLayout f3_5 = findViewById(R.id.f3_5); FrameLayout f3_6 = findViewById(R.id.f3_6);
        FrameLayout f3_7 = findViewById(R.id.f3_7); FrameLayout f3_8 = findViewById(R.id.f3_8);
        FrameLayout f3_9 = findViewById(R.id.f3_9);
        FrameLayout f4_1 = findViewById(R.id.f4_1); FrameLayout f4_2 = findViewById(R.id.f4_2);
        FrameLayout f4_3 = findViewById(R.id.f4_3); FrameLayout f4_4 = findViewById(R.id.f4_4);
        FrameLayout f4_5 = findViewById(R.id.f4_5); FrameLayout f4_6 = findViewById(R.id.f4_6);
        FrameLayout f4_7 = findViewById(R.id.f4_7); FrameLayout f4_8 = findViewById(R.id.f4_8);
        FrameLayout f4_9 = findViewById(R.id.f4_9);
        FrameLayout f5_1 = findViewById(R.id.f5_1); FrameLayout f5_2 = findViewById(R.id.f5_2);
        FrameLayout f5_3 = findViewById(R.id.f5_3); FrameLayout f5_4 = findViewById(R.id.f5_4);
        FrameLayout f5_5 = findViewById(R.id.f5_5); FrameLayout f5_6 = findViewById(R.id.f5_6);
        FrameLayout f5_7 = findViewById(R.id.f5_7); FrameLayout f5_8 = findViewById(R.id.f5_8);
        FrameLayout f5_9 = findViewById(R.id.f5_9);
        FrameLayout f6_1 = findViewById(R.id.f6_1); FrameLayout f6_2 = findViewById(R.id.f6_2);
        FrameLayout f6_3 = findViewById(R.id.f6_3); FrameLayout f6_4 = findViewById(R.id.f6_4);
        FrameLayout f6_5 = findViewById(R.id.f6_5); FrameLayout f6_6 = findViewById(R.id.f6_6);
        FrameLayout f6_7 = findViewById(R.id.f6_7); FrameLayout f6_8 = findViewById(R.id.f6_8);
        FrameLayout f6_9 = findViewById(R.id.f6_9);
        FrameLayout f7_1 = findViewById(R.id.f7_1); FrameLayout f7_2 = findViewById(R.id.f7_2);
        FrameLayout f7_3 = findViewById(R.id.f7_3); FrameLayout f7_4 = findViewById(R.id.f7_4);
        FrameLayout f7_5 = findViewById(R.id.f7_5); FrameLayout f7_6 = findViewById(R.id.f7_6);
        FrameLayout f7_7 = findViewById(R.id.f7_7); FrameLayout f7_8 = findViewById(R.id.f7_8);
        FrameLayout f7_9 = findViewById(R.id.f7_9);
        FrameLayout f8_1 = findViewById(R.id.f8_1); FrameLayout f8_2 = findViewById(R.id.f8_2);
        FrameLayout f8_3 = findViewById(R.id.f8_3); FrameLayout f8_4 = findViewById(R.id.f8_4);
        FrameLayout f8_5 = findViewById(R.id.f8_5); FrameLayout f8_6 = findViewById(R.id.f8_6);
        FrameLayout f8_7 = findViewById(R.id.f8_7); FrameLayout f8_8 = findViewById(R.id.f8_8);
        FrameLayout f8_9 = findViewById(R.id.f8_9);
        FrameLayout f9_1 = findViewById(R.id.f9_1); FrameLayout f9_2 = findViewById(R.id.f9_2);
        FrameLayout f9_3 = findViewById(R.id.f9_3); FrameLayout f9_4 = findViewById(R.id.f9_4);
        FrameLayout f9_5 = findViewById(R.id.f9_5); FrameLayout f9_6 = findViewById(R.id.f9_6);
        FrameLayout f9_7 = findViewById(R.id.f9_7); FrameLayout f9_8 = findViewById(R.id.f9_8);
        FrameLayout f9_9 = findViewById(R.id.f9_9);
        FrameLayout f10_1 = findViewById(R.id.f10_1); FrameLayout f10_2 = findViewById(R.id.f10_2);
        FrameLayout f10_3 = findViewById(R.id.f10_3); FrameLayout f10_4 = findViewById(R.id.f10_4);
        FrameLayout f10_5 = findViewById(R.id.f10_5); FrameLayout f10_6 = findViewById(R.id.f10_6);
        FrameLayout f10_7 = findViewById(R.id.f10_7); FrameLayout f10_8 = findViewById(R.id.f10_8);
        FrameLayout f10_9 = findViewById(R.id.f10_9);
        FrameLayout f11_1 = findViewById(R.id.f11_1); FrameLayout f11_2 = findViewById(R.id.f11_2);
        FrameLayout f11_3 = findViewById(R.id.f11_3); FrameLayout f11_4 = findViewById(R.id.f11_4);
        FrameLayout f11_5 = findViewById(R.id.f11_5); FrameLayout f11_6 = findViewById(R.id.f11_6);
        FrameLayout f11_7 = findViewById(R.id.f11_7); FrameLayout f11_8 = findViewById(R.id.f11_8);
        FrameLayout f11_9 = findViewById(R.id.f11_9);
        FrameLayout f12_1 = findViewById(R.id.f12_1); FrameLayout f12_2 = findViewById(R.id.f12_2);
        FrameLayout f12_3 = findViewById(R.id.f12_3); FrameLayout f12_4 = findViewById(R.id.f12_4);
        FrameLayout f12_5 = findViewById(R.id.f12_5); FrameLayout f12_6 = findViewById(R.id.f12_6);
        FrameLayout f12_7 = findViewById(R.id.f12_7); FrameLayout f12_8 = findViewById(R.id.f12_8);
        FrameLayout f12_9 = findViewById(R.id.f12_9);
        FrameLayout f13_1 = findViewById(R.id.f13_1); FrameLayout f13_2 = findViewById(R.id.f13_2);
        FrameLayout f13_3 = findViewById(R.id.f13_3); FrameLayout f13_4 = findViewById(R.id.f13_4);
        FrameLayout f13_5 = findViewById(R.id.f13_5); FrameLayout f13_6 = findViewById(R.id.f13_6);
        FrameLayout f13_7 = findViewById(R.id.f13_7); FrameLayout f13_8 = findViewById(R.id.f13_8);
        FrameLayout f13_9 = findViewById(R.id.f13_9);
        FrameLayout f14_1 = findViewById(R.id.f14_1); FrameLayout f14_2 = findViewById(R.id.f14_2);
        FrameLayout f14_3 = findViewById(R.id.f14_3); FrameLayout f14_4 = findViewById(R.id.f14_4);
        FrameLayout f14_5 = findViewById(R.id.f14_5); FrameLayout f14_6 = findViewById(R.id.f14_6);
        FrameLayout f14_7 = findViewById(R.id.f14_7); FrameLayout f14_8 = findViewById(R.id.f14_8);
        FrameLayout f14_9 = findViewById(R.id.f14_9);
        FrameLayout f15_1 = findViewById(R.id.f15_1); FrameLayout f15_2 = findViewById(R.id.f15_2);
        FrameLayout f15_3 = findViewById(R.id.f15_3); FrameLayout f15_4 = findViewById(R.id.f15_4);
        FrameLayout f15_5 = findViewById(R.id.f15_5); FrameLayout f15_6 = findViewById(R.id.f15_6);
        FrameLayout f15_7 = findViewById(R.id.f15_7); FrameLayout f15_8 = findViewById(R.id.f15_8);
        FrameLayout f15_9 = findViewById(R.id.f15_9);
        FrameLayout f16_1 = findViewById(R.id.f16_1); FrameLayout f16_2 = findViewById(R.id.f16_2);
        FrameLayout f16_3 = findViewById(R.id.f16_3); FrameLayout f16_4 = findViewById(R.id.f16_4);
        FrameLayout f16_5 = findViewById(R.id.f16_5); FrameLayout f16_6 = findViewById(R.id.f16_6);
        FrameLayout f16_7 = findViewById(R.id.f16_7); FrameLayout f16_8 = findViewById(R.id.f16_8);
        FrameLayout f16_9 = findViewById(R.id.f16_9);

        FrameLayout[] frames = {f1_1, f1_2, f1_3, f1_4, f1_5, f1_6, f1_7, f1_8, f1_9, f2_1, f2_2, f2_3, f2_4, f2_5, f2_6, f2_7, f2_8, f2_9,
                f3_1, f3_2, f3_3, f3_4, f3_5, f3_6, f3_7, f3_8, f3_9, f4_1, f4_2, f4_3, f4_4, f4_5, f4_6, f4_7, f4_8, f4_9,
                f5_1, f5_2, f5_3, f5_4, f5_5, f5_6, f5_7, f5_8, f5_9, f6_1, f6_2, f6_3, f6_4, f6_5, f6_6, f6_7, f6_8, f6_9,
                f7_1, f7_2, f7_3, f7_4, f7_5, f7_6, f7_7, f7_8, f7_9, f8_1, f8_2, f8_3, f8_4, f8_5, f8_6, f8_7, f8_8, f8_9,
                f9_1, f9_2, f9_3, f9_4, f9_5, f9_6, f9_7, f9_8, f9_9, f10_1, f10_2, f10_3, f10_4, f10_5, f10_6, f10_7, f10_8, f10_9,
                f11_1, f11_2, f11_3, f11_4, f11_5, f11_6, f11_7, f11_8, f11_9, f12_1, f12_2, f12_3, f12_4, f12_5, f12_6, f12_7, f12_8, f12_9,
                f13_1, f13_2, f13_3, f13_4, f13_5, f13_6, f13_7, f13_8, f13_9, f14_1, f14_2, f14_3, f14_4, f14_5, f14_6, f14_7, f14_8, f14_9,
                f15_1, f15_2, f15_3, f15_4, f15_5, f15_6, f15_7, f15_8, f15_9, f16_1, f16_2, f16_3, f16_4, f16_5, f16_6, f16_7, f16_8, f16_9 };

        // *****************************************************************************************

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int h = displayMetrics.heightPixels;
        int w = displayMetrics.widthPixels;

        //int ctr = 0;

        for (FrameLayout frame : frames)
        {
            frame.getLayoutParams().width = w/9;
            frame.getLayoutParams().height = h/14;
            frame.setBackgroundResource(R.drawable.rectangle_green);
            /*
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.topMargin = (h/16)*(ctr/9);
            params.leftMargin = (w/9)*(ctr%9);
            frame.setLayoutParams(params);

            ctr++; */
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
