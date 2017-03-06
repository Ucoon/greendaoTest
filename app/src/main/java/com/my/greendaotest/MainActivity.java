package com.my.greendaotest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.greendaotest.entity.UserBean;
import com.my.greendaotest.entity.UserBeanDao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_add, btn_delete, btn_update, btn_query, btn_all;
    private ListView list;
    private MyAdapter myAdapter;
    private EditText et_name, et_age, et_sex;
    private List<UserBean> userBeanList = new ArrayList<>();
    private UserBeanDao dao;
    private UserBean userBean;

    private String name, age, sex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = MyApplication.getmMyApplication().getmDaoSession().getUserBeanDao();
        initView();
    }

    private void initView(){
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(this);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);
        btn_all = (Button) findViewById(R.id.btn_all);
        btn_all.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_sex = (EditText) findViewById(R.id.et_sex);
        list = (ListView) findViewById(R.id.lv_user);
        myAdapter = new MyAdapter(MainActivity.this, R.layout.item_user, userBeanList);
        list.setAdapter(myAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                insertData();
                break;
            case R.id.btn_delete:

                break;
            case R.id.btn_query:

                break;
            case R.id.btn_update:
                updateData();
                break;
            case R.id.btn_all:
                queryData();
                break;
        }
    }

    //增
    private void insertData(){
        name = et_name.getText().toString().trim();
        age = et_age.getText().toString().trim();
        sex = et_sex.getText().toString().trim();
        if(!"".equals(name) && !"".equals(age) && !"".equals(sex)){
            UserBean userBean = new UserBean(null, name, age, sex);
            dao.insert(userBean);
            userBeanList.add(userBean);
            myAdapter.notifyDataSetChanged();
        }

    }

    //改
    private void updateData(){
        name = et_name.getText().toString().trim();
        age = et_age.getText().toString().trim();
        sex = et_sex.getText().toString().trim();
        UserBean userBean = new UserBean(null, name, age, sex);
        dao.update(userBean);
    }
    //查
    private void queryData(){
        List<UserBean> userBeen = dao.loadAll();
        String username = "";
        for (int i = 0; i<userBeen.size(); i++){
            username += userBeen.get(i).getName() + ",";
        }
        Toast.makeText(this, "查询全部数据==>" + username, Toast.LENGTH_SHORT).show();
    }

    //删
    private void deleteData(Long id){
        dao.deleteByKey(id);
    }

    private class MyAdapter extends ArrayAdapter<UserBean>{
        private int resourceId;
        public MyAdapter(Context context, int resourceId, List<UserBean> list){
            super(context, resourceId, list);
            this.resourceId = resourceId;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            UserBean userBean = getItem(position);
            View view;
            ViewHolder viewHolder;
            if(convertView == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId, null);
                viewHolder = new ViewHolder();
                viewHolder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                viewHolder.tv_age = (TextView) view.findViewById(R.id.tv_age);
                viewHolder.tv_sex = (TextView) view.findViewById(R.id.tv_sex);
                view.setTag(viewHolder);
            }else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.tv_name.setText(userBean.getName());
            viewHolder.tv_sex.setText(userBean.getSex());
            viewHolder.tv_age.setText(userBean.getAge());
            return view;
        }

        class ViewHolder{
            TextView tv_name;
            TextView tv_age;
            TextView tv_sex;
        }
    }
}
