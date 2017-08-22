package com.tobacco.xinyiyun.knowledge.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;

import com.tbruyelle.rxpermissions.RxPermissions;
import com.tobacco.xinyiyun.knowledge.BuildConfig;
import com.tobacco.xinyiyun.knowledge.R;
import com.tobacco.xinyiyun.knowledge.util.ToastUtils;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import rx.functions.Action1;

/**
 * Created by YangQiang on 2017/1/23.
 */

public class PhotoBottomSheetDialog {
    public static final int REQUEST_CODE_CHOOSE_ALBUM = 10001;
    public static final int REQUEST_CODE_CAMEIA = 10002;
    public static String IMAGESIGN_DIR = Environment.getExternalStorageDirectory() + File.separator + "sign" + File.separator;
    public static String IMAGE_DIR = Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APPLICATION_ID + File.separator + "ImageCache" + File.separator;
    public static String COMPRESSOR_DIR = Environment.getExternalStorageDirectory() + File.separator + BuildConfig.APPLICATION_ID + File.separator + "CompressorCache" + File.separator;
    private String mPhotoPath;
    OnDataCallback mCallback;
    Activity mContext;
    Compressor mCompressor;
    int mQuality = 90;
    BottomSheetDialog mBottomSheetDialog;

    public PhotoBottomSheetDialog(Activity context) {
        this.mContext = context;
        mBottomSheetDialog = new BottomSheetDialog(context);
        View mView = LayoutInflater.from(context).inflate(R.layout.view_photo_bttom_sheet, null);
        mBottomSheetDialog.setContentView(mView);
        mView.findViewById(R.id.tx_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameia();
                mBottomSheetDialog.dismiss();
            }
        });

        mView.findViewById(R.id.tx_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                mBottomSheetDialog.dismiss();
            }
        });
        mCompressor = new Compressor(mContext).setQuality(mQuality).setDestinationDirectoryPath(COMPRESSOR_DIR);
    }

    public void show() {
        mBottomSheetDialog.show();
    }

    /**
     * 打开相机
     */
    private PhotoBottomSheetDialog openCameia() {
        new RxPermissions(mContext).request(Manifest.permission.CAMERA).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    Intent mCametaIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    mPhotoPath = IMAGE_DIR + System.currentTimeMillis() + ".jpg";
                    Uri mPhotoUri = Uri.fromFile(new File(mPhotoPath));

                    File mDirFile = new File(mPhotoPath).getParentFile();
                    if (!mDirFile.exists()) {
                        mDirFile.mkdirs();
                    }
                    mCametaIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    mCametaIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoUri);
                    mCametaIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                    mContext.startActivityForResult(mCametaIntent, REQUEST_CODE_CAMEIA);
                } else {
                    ToastUtils.show(mContext, "请打开相机权限");
                }
            }
        });

        return this;
    }

    /**
     * 打开相册
     */
    private PhotoBottomSheetDialog openAlbum() {
        Intent mPhotoIntent = new Intent(Intent.ACTION_PICK);
        mPhotoIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//相片类型
        mContext.startActivityForResult(mPhotoIntent, REQUEST_CODE_CHOOSE_ALBUM);
        return this;
    }

    /**
     * 打开文件夹
     */
    private PhotoBottomSheetDialog openFile(int i) {
        File file = new File(i == 1 ? IMAGE_DIR : IMAGESIGN_DIR);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(Uri.fromFile(file), "image/*");
        ((Activity) mContext).startActivityForResult(intent, REQUEST_CODE_CHOOSE_ALBUM);
        return this;
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
        //从相机获取
        if (requestCode == REQUEST_CODE_CAMEIA) {
            File mFile = new File(mPhotoPath);
            if (mFile.exists()) {
                if (mCallback != null) {
                    mCallback.onCallBack(compressor(mFile.getAbsolutePath()));
                }
            }
        }
        //从相册获取
        else if (requestCode == REQUEST_CODE_CHOOSE_ALBUM) {
            if (data != null && data.getData() != null) {

                Uri mUri = data.getData();
                if (data.getScheme().contains("file")) {
                    if (mCallback != null) {
                        mCallback.onCallBack(compressor(mUri.getPath()));
                    }
                } else {

                    Cursor mCursor = new CursorLoader(mContext, data.getData(), new String[]{MediaStore.Images.Media.DATA}, null, null, null).loadInBackground();
                    int mIndex = mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    mCursor.moveToFirst();
                    if (mCallback != null) {
                        mCallback.onCallBack(compressor(mCursor.getString(mIndex)));
                    }
                    mCursor.close();

                }

            }
        }
    }

    private String compressor(String path) {
        try {
            return mCompressor.compressToFile(new File(path)).getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

    public PhotoBottomSheetDialog dataCallback(OnDataCallback callback) {
        mCallback = callback;
        return this;
    }

    public interface OnDataCallback {
        void onCallBack(String filePath);
    }
}
