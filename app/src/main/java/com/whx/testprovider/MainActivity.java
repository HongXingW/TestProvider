package com.whx.testprovider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button1,button2,button3,button4,contactsBtn;
    private ContentResolver contentResolver;
    private Uri uri = Uri.parse("content://org.whx.providers.firstprovider");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        contentResolver = getContentResolver();
        someMethod();
    }

    private void someMethod(){

        button1 = (Button)findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query(v);
            }
        });

        button2 = (Button)findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert(v);
            }
        });

        button3 = (Button)findViewById(R.id.btn3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });

        button4 = (Button)findViewById(R.id.btn4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v);
            }
        });

        contactsBtn = (Button)findViewById(R.id.contacts);
        contactsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ContactProviderTest.class);
                startActivity(intent);
            }
        });
    }

    public void query(View source){
        //调用ContentResolver的query方法
        Cursor c = contentResolver.query(uri,null,"query_where",null,null);
        Toast.makeText(this,"返回的Cursor为："+c,Toast.LENGTH_SHORT).show();

    }
    public void insert(View source){

        ContentValues values = new ContentValues();
        values.put("name","whx");

        Uri newUri = contentResolver.insert(uri, values);
        Toast.makeText(this,"新插入记录的Uri："+newUri,Toast.LENGTH_SHORT).show();

    }
    public void update(View souce){

        ContentValues values = new ContentValues();
        values.put("name","whx");

        int count = contentResolver.update(uri, values, "update_where", null);
        Toast.makeText(this,"更新记录数为："+count,Toast.LENGTH_SHORT).show();

    }
    public void delete(View source){

        int count = contentResolver.delete(uri,"delete_where",null);
        Toast.makeText(this,"删除记录数为："+count,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
