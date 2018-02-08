package com.lzx2005.eurekamonitor;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzx2005.eurekamonitor.activity.ServerListActivity;
import com.lzx2005.eurekamonitor.task.GetAppsTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    //SharedPreferences serverInfo;



    ListView listView;
    List<Map<String,Object>> appDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = null;
        //getSharedPreferences("serverInfo", 0);
        //Set<String> serverNames = serverInfo.getStringSet("server_names",new HashSet<String>());
        //把这个名词列表展示到下拉框中
//        if(names.size()==0){
//            // 服务器列表为空，请用户输入列表
//        }else{
//            // 选择默认列表
//            String defaultServer = loginInfo.getString("defaultServer", null);
//            if(defaultServer==null){
//                //选择第一个
//                url = loginInfo.getString(names.iterator().next(),null);
//            }else{
//                //选择默认服务器
//                url = loginInfo.getString(defaultServer, null);
//            }
//        }

        listView = findViewById(R.id.list);
        url = "http://10.200.141.52:30000/eureka/apps";
        new Thread(new GetAppsTask(url, new GetAppsHandler())).start();
    }



    class GetAppsHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String val = data.getString("value");
            Log.i("lzx",val);
            JSONObject jsonObject = JSONObject.parseObject(val);
            JSONObject applications = jsonObject.getJSONObject("applications");
            JSONArray application = applications.getJSONArray("application");
            for (int i = 0; i < application.size(); i++) {
                JSONObject app = application.getJSONObject(i);
                HashMap<String, Object> map = new HashMap<>();
                map.put("name",app.getString("name"));
                map.put("instance",app.getJSONObject("instance").getString("instanceId"));
                appDataList.add(map);
            }

            listView.setAdapter(new SimpleAdapter(
                    getApplicationContext(),
                    appDataList,
                    R.layout.app_item,
                    new String[]{"name","instance"},
                    new int[]{R.id.app_name,R.id.instance}
            ));



        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, ServerListActivity.class);
        startActivityForResult(intent,1);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.server_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getApplicationContext(),"Activity返回了"+requestCode,Toast.LENGTH_SHORT).show();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
