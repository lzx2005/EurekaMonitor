package com.lzx2005.eurekamonitor.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzx2005.eurekamonitor.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerListActivity extends AppCompatActivity {

    SharedPreferences serverInfo;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.server_list);
        serverInfo = getSharedPreferences("serverInfo", 0);
        String serverInfoJson = serverInfo.getString("serverInfoJson", "[]");

        JSONArray serverInfos = JSONArray.parseArray(serverInfoJson);

        List<Map<String,Object>> maps = new ArrayList<>();

        //遍历展示服务器信息
        for (int i = 0; i < serverInfos.size(); i++) {
            JSONObject jsonObject = serverInfos.getJSONObject(i);
            String serverName = jsonObject.getString("serverName");
            String serverHost = jsonObject.getString("serverHost");
            HashMap<String, Object> map = new HashMap<>();
            map.put("serverName",serverName);
            map.put("serverHost",serverHost);
            maps.add(map);
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("serverName","s1");
        map.put("serverHost","localhost");
        maps.add(map);

        listView.setAdapter(new SimpleAdapter(
                getApplicationContext(),
                maps,
                R.layout.server_item,
                new String[]{"serverName","serverHost"},
                new int[]{R.id.server_name,R.id.server_host}
        ));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
