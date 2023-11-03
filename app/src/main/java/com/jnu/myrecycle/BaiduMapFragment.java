package com.jnu.myrecycle;

import static com.tencent.map.tools.internal.a.j;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.TextureMapView;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.maps.model.TileOverlay;

import org.w3c.dom.Text;

public class BaiduMapFragment extends Fragment {
    private MapView mapView;

    TencentMap Tencent_Map;
    public BaiduMapFragment() {
    }

    public static BaiduMapFragment newInstance(String param1, String param2) {
        BaiduMapFragment fragment = new BaiduMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_baidu_map, container, false);
        mapView = rootview.findViewById(R.id.mapView);//

        TencentMap tencentMap = mapView.getMap();
        LatLng jnuLatLng = new LatLng(22.249931,113.534382); // 珠海暨南大学的经纬度
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(jnuLatLng, 17); // 设置初始显示层级和起始位置
        tencentMap.moveCamera(cameraUpdate);// 设置初始显示层级和起始位置


        int width = 200; // 图标的宽度，单位为像素
        int height = 200; // 图标的高度，单位为像素
        int textSize = 40; // 文本的大小
        // 创建一个 Bitmap 对象，作为自定义图标
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.RED); // 设置文本颜色为红色
        paint.setTextSize(textSize); // 设置文本大小
        // 计算文本绘制的起始位置
        float textWidth = paint.measureText("我的大学"); // 获取文本的宽度
        float textHeight = paint.descent() - paint.ascent(); // 获取文本的高度
        float x = (width - textWidth) / 2; // 计算 x 偏移量，使文本位于图标水平中心位置
        float y = (height - textHeight) / 2 - paint.ascent(); // 计算 y 偏移量，使文本位于图标垂直中心位置
        canvas.drawText("我的大学", x, y, paint); // 在指定位置绘制文本
// 将 Bitmap 对象转换为 BitmapDescriptor
        BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(bitmap);
// 创建图标型 Marker
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(22.249931,113.534382)) // 设置 Marker 的位置
                .icon(icon); // 设置自定义图标
// 添加 Marker 到地图上
        Marker marker = tencentMap.addMarker(markerOptions);


        tencentMap.addMarker(markerOptions);
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TencentMap tencentMap = mapView.getMap();

        // 创建图标型 Marker
        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(22.251031,113.534382)) // 设置 Marker 的位置
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rr)) // 设置 Marker 的图标
                .title("我的大学")
                .alpha(0.5f);
        // 添加 Marker 到地图上
        Marker marker = tencentMap.addMarker(markerOptions);

        // 给标记添加响应事件
        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 在此处添加您的点击标记的逻辑处理
                Toast.makeText(getContext(), "点击了标记", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
}