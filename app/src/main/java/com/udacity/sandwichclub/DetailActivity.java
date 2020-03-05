package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView imageIv;
    private TextView alsoKnownValueTv;
    private TextView originValueTv;
    private TextView ingredientsValueTv;
    private TextView descriptionValueTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageIv = findViewById(R.id.image_iv);
        alsoKnownValueTv = findViewById(R.id.also_known_value_tv);
        originValueTv = findViewById(R.id.origin_value_tv);
        ingredientsValueTv = findViewById(R.id.ingredients_value_tv);
        descriptionValueTv = findViewById(R.id.description_value_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException jse) {
            Log.e("JSON", "Error parsing JSON");
            jse.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        // Load the sandwich image
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIv);
        imageIv.setVisibility(View.VISIBLE);

        // Get also-known-as
        if (!sandwich.getAlsoKnownAs().isEmpty()) {
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                alsoKnownValueTv.append(sandwich.getAlsoKnownAs().get(i));
                // Add a separator(/) if there is more than one in the list
                if ((i < (sandwich.getAlsoKnownAs().size() - 1))) {
                    alsoKnownValueTv.append("/");
                }
            }
        } else {
            alsoKnownValueTv.setText(getString(R.string.not_applicable));
        }

        originValueTv.setText(sandwich.getPlaceOfOrigin());

        // Get also-known-as
        if (!sandwich.getIngredients().isEmpty()) {
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                ingredientsValueTv.append(sandwich.getIngredients().get(i));
                // Add a separator(/) if there is more than one in the list
                if ((i < (sandwich.getIngredients().size() - 1))) {
                    ingredientsValueTv.append("/");
                }
            }
        } else {
            ingredientsValueTv.setText(getString(R.string.not_applicable));
        }

        descriptionValueTv.setText(sandwich.getDescription());

    }

}
