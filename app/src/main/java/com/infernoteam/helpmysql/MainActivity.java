package com.infernoteam.helpmysql;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
@SuppressLint("StaticFieldLeak")
public class MainActivity extends AppCompatActivity {
    ListView _listView;
    LayoutManager adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _listView=(ListView)findViewById(R.id.listView);
    }

    public void _Find(View view) {
        MyTask task=new MyTask();
        task.execute("http://10.0.2.2/get_all_items.php"); // or 10.0.3.2
    }

    public void _Insert(View view) {
        final AlertDialog dialog=new AlertDialog.Builder(this)
                .setView(R.layout.insert_item).show();

        final EditText _insert_name=(EditText)
                dialog.findViewById(R.id._insert_name);
        final EditText _insert_company_name=(EditText)
                dialog.findViewById(R.id._insert_company_name);
        final EditText _insert_salary=(EditText)
                dialog.findViewById(R.id._insert_salary);
        Button insert_done=(Button)dialog.findViewById(R.id._insert_done);

        assert insert_done != null;
        insert_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert _insert_name != null;
                String name=_insert_name.getText().toString();
                assert _insert_company_name != null;
                String company_name=_insert_company_name.getText().toString();
                assert _insert_salary != null;
                String _salary=_insert_salary.getText().toString();
                double salary=Double.parseDouble(_salary);
                Item item = new Item(name,company_name,salary);
                _InsertItem(item);
                dialog.dismiss();
            }
        });
        Button _insert_cancel=(Button)dialog.findViewById(R.id._insert_cancel);
        assert _insert_cancel!=null;
        _insert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void _InsertItem(final Item item) {
        class InsertItem  extends AsyncTask<Void,Void,String>{
            private final String _url="http://10.0.2.2/add_item.php"; // or 10.0.3.2
            @Override
            protected String doInBackground(Void... voids) {
                StringBuilder str=new StringBuilder();
                try{
                    URL url=new URL(_url);
                    HttpURLConnection connection=(HttpURLConnection)
                            url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    String testing= URLEncoder.encode("name", "utf-8")
                            + "='" + URLEncoder.encode(item.getName(), "utf-8")+"'"
                            +"&"+URLEncoder.encode("company_name", "utf-8")
                            + "='" + URLEncoder.encode(item.getCompany_name(), "utf-8")+"'"
                            +"&"+URLEncoder.encode("salary", "utf-8")
                            + "=" + URLEncoder.encode(String.valueOf(item.getSalary()), "utf-8");
                    System.out.println(testing);
                    OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
                    wr.write( testing );
                    wr.flush();
                    InputStream stream=new BufferedInputStream(connection.getInputStream());
                    BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
                    String line;
                    while((line=reader.readLine())!=null)
                        str.append(line);
                    reader.close();
                    connection.disconnect();
                }catch (IOException ignored){}
                return str.toString();
            }
            private ProgressDialog _dialog;

             private InsertItem() {
                 _dialog=new ProgressDialog(MainActivity.this);
             }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                _dialog.setTitle("Inserting");
                _dialog.setMessage("Wait...");
                _dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                _dialog.dismiss();
                System.out.println(s);
                try{
                    JSONObject jsonObject=new JSONObject(s);
                    JSONArray array=jsonObject.getJSONArray("result");
                    JSONObject object=array.getJSONObject(0);
                    String value=object.getString("value");
                    Toast.makeText(MainActivity.this, value, Toast.LENGTH_LONG).show();
                }catch (JSONException ignored){}
            }
        }
        new InsertItem().execute();
    }

    private class MyTask extends AsyncTask<String,Void,String>{
         MyTask() {
            dialog=new ProgressDialog(MainActivity.this);
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder str=new StringBuilder();
            try{
                URL url=new URL(strings[0]);
                HttpURLConnection connection=(HttpURLConnection)
                        url.openConnection();
                BufferedInputStream stream=new BufferedInputStream(connection.getInputStream());
                BufferedReader reader=new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line=reader.readLine())!=null){
                    str.append(line);
                }
                reader.close();
                stream.close();
                connection.disconnect();
            }catch (IOException ignored){}
            return str.toString();
        }
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog.setTitle("Progressing");
            dialog.setMessage("Wait...");
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            System.out.println("result : "+s);
            try{
                ArrayList<Item>items=new ArrayList<>();
                JSONObject jsonObject=new JSONObject(s);
                JSONArray arr=jsonObject.getJSONArray("result");
                for(int i=0;i<arr.length();i++){
                    JSONObject object=arr.getJSONObject(i);
                    String name=object.getString("name");
                    String company_name=object.getString("company_name");
                    double salary=object.getDouble("salary");
                    Item item=new Item(name,company_name,salary);
                    items.add(item);
                }
                adapter=new LayoutManager(items,MainActivity.this);
                _listView.setAdapter(adapter);
            }catch (JSONException ignored){}
        }
    }
}
