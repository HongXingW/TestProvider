package com.whx.testprovider;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by whx on 2015/12/12.
 */
public class ContactProviderTest extends Activity{

    private Button searchBtn,addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_test);

        searchBtn = (Button)findViewById(R.id.search);
        searchBtn.setOnClickListener(new SearchListen());

        addBtn = (Button)findViewById(R.id.add);
        addBtn.setOnClickListener(new AddListen());
    }
    private class SearchListen implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            //定义两个List来封装信息
            final ArrayList<String> names = new ArrayList<>();
            final ArrayList<ArrayList<String>> details = new ArrayList<>();
            //使用ContenResolver查找联系人数据
            Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    null,null,null,null);
            //遍历查询结果，获取系统所有联系人
            while(cursor.moveToNext()){
                //联系人id
                String contactId = cursor.getString(cursor.
                        getColumnIndex(ContactsContract.Contacts._ID));
                //联系人名字
                String name = cursor.getString(cursor
                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                names.add(name);
                //查找联系人电话号码
                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds
                .Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                +" = "+contactId,null,null);

                ArrayList<String> detail = new ArrayList<>();
                //遍历查询结果，获得联系人多个联系电话
                while (phones.moveToNext()){
                    String phoneNum = phones.getString(phones
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    detail.add("电话号码："+phoneNum);
                }
                phones.close();
                //查找联系人邮箱
                Cursor email = getContentResolver().query(ContactsContract.CommonDataKinds.Email
                .CONTENT_URI,null, ContactsContract.CommonDataKinds.Email.CONTACT_ID
                + " = "+contactId,null,null);
                while (email.moveToNext()){
                    //
                    String emailAddr = email.getString(email.getColumnIndex(ContactsContract.
                            CommonDataKinds.Email.DATA));
                    detail.add("邮箱："+emailAddr);
                }
                email.close();
                details.add(detail);
            }
            cursor.close();

            Bundle bundle = new Bundle();
            bundle.putSerializable("name",names);
            bundle.putSerializable("detail",details);

            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setClass(ContactProviderTest.this, ResultActivity.class);

            startActivity(intent);
        }
    }
    private class AddListen implements View.OnClickListener{
        @Override
        public void onClick(View v) {

        }
    }
}
