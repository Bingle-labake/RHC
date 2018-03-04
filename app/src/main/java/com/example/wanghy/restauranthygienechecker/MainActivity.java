package com.example.wanghy.restauranthygienechecker;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.ResultReceiver;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wanghy.restauranthygienechecker.common.FetchAddressIntentService;
import com.example.wanghy.restauranthygienechecker.entity.BitmapCache;
import com.example.wanghy.restauranthygienechecker.common.Consts;
import com.facebook.drawee.view.SimpleDraweeView;

import android.location.Geocoder;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        , LandingFragment.OnFragmentInteractionListener
        , ConnectionCallbacks
        , OnConnectionFailedListener {
    private final String TAG = "MainActivity";


    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private AddressResultReceiver mResultReceiver;
    protected boolean mAddressRequested;
    private EditText mLatitudeText;
    private EditText mLongitudeText;


//    头像部分
//    protected static final int CHOOSE_PICTURE = 0;
//    protected static final int TAKE_PICTURE = 1;
//    private static final int CROP_SMALL_PICTURE = 2;
//    protected static Uri tempUri;
//    private ImageView iv_personal_icon;


//    头像部分

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        Log.i("MapsActivity", "buildGoogleApiClient-->finished.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        头像
//        ImageView btn_change = (ImageView) findViewById(R.id.imageView);
//        iv_personal_icon = (ImageView) findViewById(R.id.imageView);
//
//    }
//
//    /**
//     * 显示修改头像的对话框
//     */
//    /**
//     * 显示修改头像的对话框
//     */
//    public void showChoosePicDialog(View v) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("设置头像");
//        String[] items = { "选择本地照片", "拍照" };
//        builder.setNegativeButton("取消", null);
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case CHOOSE_PICTURE: // 选择本地照片
//                        Intent openAlbumIntent = new Intent(
//                                Intent.ACTION_GET_CONTENT);
//                        openAlbumIntent.setType("image/*");
//                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
//                        break;
//                    case TAKE_PICTURE: // 拍照
//                        takePicture();
//                        break;
//                }
//            }
//        });
//        builder.create().show();
//    }
//
//    private void takePicture() {
//        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//        if (Build.VERSION.SDK_INT >= 23) {
//            // 需要申请动态权限
//            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//            if (check != PackageManager.PERMISSION_GRANTED) {
//                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//            }
//        }
//        Intent openCameraIntent = new Intent(
//                MediaStore.ACTION_IMAGE_CAPTURE);
//        File file = new File(Environment
//                .getExternalStorageDirectory(), "image.jpg");
//        //判断是否是AndroidN以及更高的版本
//        if (Build.VERSION.SDK_INT >= 24) {
//            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            tempUri = FileProvider.getUriForFile(MainActivity.this, "com.lt.uploadpicdemo.fileProvider", file);
//        } else {
//            tempUri = Uri.fromFile(new File(Environment
//                    .getExternalStorageDirectory(), "image.jpg"));
//        }
//        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
//        startActivityForResult(openCameraIntent, TAKE_PICTURE);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
//            switch (requestCode) {
//                case TAKE_PICTURE:
//                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
//                    break;
//                case CHOOSE_PICTURE:
//                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
//                    break;
//                case CROP_SMALL_PICTURE:
//                    if (data != null) {
//                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
//                    }
//                    break;
//            }
//        }
//    }
//
//    /**
//     * 裁剪图片方法实现
//     *
//     * @param uri
//     */
//    protected void startPhotoZoom(Uri uri) {
//        if (uri == null) {
//            Log.i("tag", "The uri is not exist.");
//        }
//        tempUri = uri;
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 150);
//        intent.putExtra("outputY", 150);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_SMALL_PICTURE);
//    }
//
//    /**
//     * 保存裁剪之后的图片数据
//     *
//     * @param
//     *
//     * @param
//     */
//    protected void setImageToView(Intent data) {
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            Log.d(TAG,"setImageToView:"+photo);
//            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
//            iv_personal_icon.setImageBitmap(photo);
//            uploadPic(photo);
//        }
//    }
//
//    private void uploadPic(Bitmap bitmap) {
//        // 上传至服务器
//        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
//        // 注意这里得到的图片已经是圆形图片了
//        // bitmap是没有做个圆形处理的，但已经被裁剪了
//
//        String imagePath = ImageUtils.savePhoto(bitmap, Environment
//                .getExternalStorageDirectory().getAbsolutePath(), String
//                .valueOf(System.currentTimeMillis()));
//        Log.e("imagePath", imagePath+"");
//        if(imagePath != null){
//            // 拿着imagePath上传了
//            // ...
//        }

//        头像


//        mLatitudeText = (EditText) findViewById(R.id.latitudeText);
//        mLongitudeText = (EditText)findViewById(R.idƒ.longitudeText);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment fragment = new LandingFragment();

        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        //接收FetchAddressIntentService返回的结果
        mAddressRequested = false;
        mResultReceiver = new AddressResultReceiver(new Handler());
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
            Log.i("MapsActivity", "buildGoogleApiClient-->" + mAddressRequested);
            mGoogleApiClient.connect();
            Log.i("MapsActivity", "mGoogleApiClient-->connect" + mAddressRequested);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        if (id == R.id.nav_simple_search) {
            fragment = new SimpleSearchFragment();
            // Handle the camera action
        } else if (id == R.id.nav_location_search) {
            fragment = new LocationSearchFragment();

        } else if (id == R.id.nav_filter_search) {
            fragment = new FilterSearchFragment();

        } else if (id == R.id.nav_favorite) {
            fragment = new FavoriteFragment();
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.main_frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    class AddressResultReceiver extends ResultReceiver {
        private String mAddressOutput;

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            mAddressOutput = resultData.getString(FetchAddressIntentService.RESULT_DATA_KEY);
            if (resultCode == FetchAddressIntentService.SUCCESS_RESULT) {
                Log.i("MapsActivity", "mAddressOutput-->" + mAddressOutput);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Position")
                        .setMessage(mAddressOutput)
                        .create()
                        .show();
            }
            mAddressRequested = false;
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("MapsActivity", "buildGoogleApiClient-->checkSelfPermission.");
            Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Log.i("MapsActivity", "buildGoogleApiClient-->mLastLocation.");
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));

            Log.i("MapsActivity", "buildGoogleApiClient-->getLatitude."+String.valueOf(mLastLocation.getLatitude()));
            Log.i("MapsActivity", "buildGoogleApiClient-->getLongitude."+String.valueOf(mLastLocation.getLongitude()));
            if (mLastLocation != null) {
                // Determine whether a Geocoder is available.
                if (!Geocoder.isPresent()) {
                    Toast.makeText(this, R.string.no_geocoder_available, Toast.LENGTH_LONG).show();
                    return;
                }
                // It is possible that the user presses the button to get the address before the
                // GoogleApiClient object successfully connects. In such a case, mAddressRequested
                // is set to true, but no attempt is made to fetch the address (see
                // fetchAddressButtonHandler()) . Instead, we start the intent service here if the
                // user has requested an address, since we now have a connection to GoogleApiClient.
                if (mAddressRequested) {
                    startIntentService(); //开启服务解析经纬度
                    mAddressRequested = true;
                }
            }
        }else {
            Log.i("MapsActivity", "buildGoogleApiClient-->mLastLocation:null");
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 连接失败
     * @param result
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i("MapsActivity", "buildGoogleApiClient-->onConnectionFailed.");
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode()+", msg:"+result.getErrorMessage());
    }

    /**
     * 连接被暂停了，重新连接
     * @param cause
     */
    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    protected void startIntentService() {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(this, FetchAddressIntentService.class);

        // Pass the result receiver as an extra to the service.
        intent.putExtra(Consts.RECEIVER, mResultReceiver);

        // Pass the location data as an extra to the service.
        intent.putExtra(Consts.LOCATION_DATA_EXTRA, mLastLocation);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        startService(intent);
    }





    private void updateWithNewLocation(Location location) {
        Log.i("GPS", "updateLocation");
        String latitude = String.valueOf(location.getLatitude());
        String longitude = String.valueOf(location.getLongitude());
        Log.i("GPS", "updateLocation" + "latitude=" + latitude + "longitude=" + longitude);

        String url = String.format(
                "http://maps.google.cn/maps/api/geocode/json?latlng=%s,%s&sensor=false&language=en_us",
                latitude, longitude); //请求的链接是重点，尝试了很多次，默认语言中文，这里由于需要设置成美国英语
        //sendLocationAdressRequest(url);
    }

    private String showResponse(String response) {
        JSONObject jsonObj = null;
        String result = "";
        try {
            // 把服务器相应的字符串转换为JSONObject
            jsonObj = new JSONObject(response);
            // 解析出响应结果中的地址数据
            JSONArray jsonArray = jsonObj.getJSONArray("results");
            Log.i("GPS", "lenth = " + jsonArray.length());
            result = jsonArray.getJSONObject(jsonArray.length() - 1).getString("formatted_address");
            /*result =  jsonObj.getJSONArray(0).getString("formatted_address");*/
            // 此处jsonArray.length()-1得到的位置信息是最后一列，得到的是Google地图划分区域的最外层，
            // 如国家或者特殊城市-香港等，若需要得到具体位置使用0；
            Log.i("GPS", "address json result = " + result);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("GPS", "address json result error");
        }
        return result;
    }
}
