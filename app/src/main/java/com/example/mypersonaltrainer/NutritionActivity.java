package com.example.mypersonaltrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NutritionActivity extends AppCompatActivity implements MacroCounter.OnFragmentInteractionListener {
    public String x;
    EditText Search;
    Button button;
    String food = "";
    FragmentManager fm = getSupportFragmentManager();

    private RequestBuilder r;
    ListView list;
    ArrayList<FoodItem> meals;
    ArrayAdapter<FoodItem> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        Search = (EditText)findViewById(R.id.search_bar);
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.macro, new MacroCounter(), "");
        ft.commit();
        r = new RequestBuilder("25225b852837488abe926fc56eb5163c","2096792bd3014b1280b7d91a87efa88b");
        searchImplementation();
        list = (ListView) findViewById(R.id.list);
        //button = (Button) findViewById(R.id.btn);
        x ="";
        meals = new ArrayList<FoodItem>();

        adapter = new ArrayAdapter<FoodItem>(this,android.R.layout.simple_list_item_1,meals);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NutritionActivity.this,FoodInfo.class);
                FoodItem a = (FoodItem) list.getItemAtPosition(position);
                String i = a.id;

                intent.putExtra("id", i);
                intent.putExtra("name",a.name);
                startActivity(intent);
               // finish();


            }
        });




        Search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                //Value = Search.getText().toString();


                //searchFood(Search.getText().toString(), 0);

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void searchImplementation() {
        Search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    searchFood(Search.getText().toString(), 0);

                    return true;
                }
                return false;
            }
        });
    }

    private void paresResponse(final String response) {

        try{
            final JSONObject jsonObject = new JSONObject(response);
            final JSONObject jsonObjectResult = jsonObject.optJSONObject("foods");
            //final JSONObject jsonObjectFoods = jsonObject.optJSONObject("foods");
            Log.d("TAG", jsonObjectResult.toString());
            final JSONArray jsonArrayFood = jsonObjectResult.optJSONArray("food");
            if(jsonArrayFood!=null && jsonArrayFood.length()>0)
                for (int i = 0; i < jsonArrayFood.length(); i++) {
                    final JSONObject f = jsonArrayFood.optJSONObject(i);
                    if (f != null && f.length() > 0) {


                        String food = f.getString("food_name");
                        String des= f.getString("food_description");
                        String id = f.getString("food_id");
                        Log.d("fff", food);
                        meals.add(new FoodItem(food,des,id));




                    }
                }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    private void searchFood(final String item, final int page_num) {

        meals.clear();

        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                // mProgressMore.setVisibility(View.VISIBLE);
                //  mProgressSearch.setVisibility(View.VISIBLE);
            }
            String x = "";
            String res = "";
            @Override
            protected String doInBackground(String... arg0) {
                try {
                    x = r.buildFoodsSearchUrl(item,0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    URL url = new URL(x);
                    URLConnection api = url.openConnection();
                    String line;
                    StringBuilder builder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(api.getInputStream()));
                    while ((line = reader.readLine()) != null) builder.append(line);
                    res = builder.toString();
                    //JSONObject food = new JSONObject(builder.toString());   // { first
                    //foods = food.getJSONObject("foods");                    // { second
                } catch (Exception exception) {
                    Log.e("FatSecret Error", exception.toString());
                    exception.printStackTrace();
                }



                //String food = mFatSecretSearch.searchFood(item, page_num);
                paresResponse(res);
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Error")){

                }


                Search.clearFocus();
                list.setAdapter(adapter);

            }
        }.execute();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public DailyMacro getMacros()  {
        SharedPreferences sp = getSharedPreferences("your_prefs", MODE_PRIVATE);




        float cal = sp.getFloat("Cal",0);
        float carbs = sp.getFloat("Carbs", 0);
        float protein = sp.getFloat("Protein", 0);
        float fat = sp.getFloat("Fats", 0);

        DailyMacro m = new DailyMacro(cal,protein,fat,carbs);
        return  m;

    }
}