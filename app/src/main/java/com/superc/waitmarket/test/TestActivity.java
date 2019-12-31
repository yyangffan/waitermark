package com.superc.waitmarket.test;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.superc.waitmarket.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity implements PoiSearch.OnPoiSearchListener, AMap.OnPOIClickListener {
    private static final String TAG = "TestActivity";

    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.test_edt)
    EditText mEdt;
    private PoiSearch poiSearch;
    private PoiSearch.Query mQuery;
    private AMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mMap = mMapView.getMap();
        mMap.setOnPOIClickListener(this);
        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(TestActivity.this, marker.getTitle() + "点击了", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mMap.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (TextUtils.isEmpty(s)){
                    mMap.clear();
                }else{
                    mQuery = new PoiSearch.Query(s.toString(), "", "天津");
                    mQuery.setPageSize(100);// 设置每页最多返回多少条poiitem
                    poiSearch = new PoiSearch(TestActivity.this, mQuery);
                    poiSearch.setOnPoiSearchListener(TestActivity.this);
                    poiSearch.searchPOIAsyn();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        Log.e(TAG, "onPoiSearched: " + poiResult.getPois().size());
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        for (int j = 0; j < poiResult.getPois().size(); j++) {
            PoiItem poiItem = poiResult.getPois().get(j);
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
//            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                    .decodeResource(getResources(), R.drawable.icon_head))));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(poiItem.getTitle()).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                    .decodeResource(getResources(), R.drawable.location))));

            boundsBuilder.include(marker.getPosition());//把所有点都include进去（LatLng类型）
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 15));

    }

    @Override
    public void onPOIClick(Poi poi) {
        LatLng coordinate = poi.getCoordinate();
        mMap.addMarker(new MarkerOptions().position(coordinate).title(poi.getName()).icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.location))));
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

}
