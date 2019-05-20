package com.example.mypersonaltrainer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class FoodInfo extends AppCompatActivity implements MacroCounter.OnFragmentInteractionListener{
    TextView name;
    TextView Serving;
    EditText Amount;

    private RequestBuilder r;
    String id;
    String p = "0";
    String cal = "0";
    String car ="0";
    String f ="0";
    String ser = "";
    FragmentManager fm = getSupportFragmentManager();
    Button Eat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        r = new RequestBuilder("25225b852837488abe926fc56eb5163c","2096792bd3014b1280b7d91a87efa88b");
        setContentView(R.layout.activity_food_info);

        p ="";
        cal="";
        car="";
        f="";
        name = findViewById(R.id.name);
        Serving = findViewById(R.id.serving);
        Eat = findViewById(R.id.Eat);
        Amount = findViewById(R.id.amount);

        id = getIntent().getStringExtra("id");
        name.setText(getIntent().getStringExtra("name"));
        if(!id.equals("")){
            getFood(id,0);
        }

        Eat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*Intent intent = new Intent(MainActivity.this, NutritionActivity.class);
                startActivity(intent);*/
                SharedPreferences sp = getApplicationContext().getSharedPreferences("your_prefs", 0); // 0 - for private mode
                SharedPreferences.Editor editor = sp.edit();
                float ocal = sp.getFloat("Cal",-1);
                float ocarbs = sp.getFloat("Carbs", -1);
                float oprotein = sp.getFloat("Protein", -1);
                float ofat = sp.getFloat("Fats", -1);
                float multiplier =Float.parseFloat(Amount.getEditableText().toString());
                float c = Float.parseFloat(cal);
                float carb = Float.parseFloat(car);
                float protein = Float.parseFloat(p);
                float fat = Float.parseFloat(f);

                editor.putFloat("Cal", (multiplier*ocal)+c); // Storing boolean - true/false
                editor.putFloat("Carbs", (multiplier*ocarbs)+carb); // Sofattoring string
                editor.putFloat("Protein", (multiplier*oprotein)+protein); // Storing integer
                editor.putFloat("Fats", (multiplier*ofat)+fat); // Storing float
              // Storing long

                editor.commit(); // commit changes

                Intent intent = new Intent(FoodInfo.this, NutritionActivity.class);
                startActivity(intent);
                finish();


            }
        });





    }


    private void paresResponse(final String response) {

        try{
            final JSONObject jsonObject = new JSONObject(response);

            Log.d("TAG", jsonObject .toString());
            final JSONObject food= jsonObject.optJSONObject("food");
            final JSONObject servings= food.optJSONObject("servings");

            Log.d("TAG", servings.toString());

            //final JSONObject serving= servings.optJSONObject("serving");
            final JSONArray serving= servings.optJSONArray("serving");
            if(serving!=null){
                cal = serving.getJSONObject(0).getString("calories");
                p = serving.getJSONObject(0).getString("protein");
                car = serving.getJSONObject(0).getString("carbohydrate");
                f = serving.getJSONObject(0).getString("fat");
                ser = serving.getJSONObject(0).getString("serving_description");
            }
            else{
                final JSONObject s= servings.optJSONObject("serving");

                cal = s.getString("calories");
                p = s.getString("protein");
                car = s.getString("carbohydrate");
                f = s.getString("fat");

            }



        }catch (JSONException e){
            e.printStackTrace();
        }
    }



    private void getFood(final String item, final int page_num) {



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
                    x =  r.buildFoodGetUrl(Long.parseLong(item));

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
                /*calories.setText("Calories "+cal);
                carbs.setText(car+"g");
                proteins.setText(p+"g");
                fats.setText(f+"g");*/
                Serving.setText(ser);
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.macro, new MacroCounter(), "");

                ft.commit();


            }
        }.execute();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public DailyMacro getMacros() {

        float c = Float.parseFloat(cal);
        float carb = Float.parseFloat(car);
        float protein = Float.parseFloat(p);
        float fat = Float.parseFloat(f);

        DailyMacro m = new DailyMacro(c,protein,fat,carb);
        return  m;
    }
}
