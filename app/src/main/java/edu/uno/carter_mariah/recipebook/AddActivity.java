package edu.uno.carter_mariah.recipebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        final Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Recipe.getCategoriesAsStringArray()
                ));

        Button addItemButton = (Button) findViewById(R.id.add_item);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View newItem = getLayoutInflater().inflate(R.layout.add_item_fragment, null);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.items);
                linearLayout.addView(newItem, linearLayout.getChildCount() - 1);
            }
        });

        Button addStepButton = (Button) findViewById(R.id.add_step);
        addStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View newStep = getLayoutInflater().inflate(R.layout.add_step_fragment, null);
                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.steps);
                linearLayout.addView(newStep, linearLayout.getChildCount() - 1);
            }
        });

        Button add = (Button) findViewById(R.id.add_recipe);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) findViewById(R.id.name_edittext);
                Spinner category = (Spinner) findViewById(R.id.category_spinner);
                EditText serves = (EditText) findViewById(R.id.serves_number);

                ArrayList<String> stepList = new ArrayList<>();
                LinearLayout steps = (LinearLayout) findViewById(R.id.steps);
                for (int i = 0; i < steps.getChildCount(); i++) {
                    View child = steps.getChildAt(i);
                    Log.d("Tag", child.getTag() == null ? "null" : child.getTag().toString());
                    if (child.getClass() == LinearLayout.class) {
                        LinearLayout step = (LinearLayout) child;
                        EditText stepText = (EditText) step.getChildAt(0);
                        Log.d("Step Text", stepText.getText().toString());
                        if (!stepText.getText().toString().equals(""))
                            stepList.add(stepText.getText().toString());
                    }
                }

                ArrayList<Item> itemList = new ArrayList<>();
                LinearLayout items = (LinearLayout) findViewById(R.id.items);
                for (int i = 0; i < items.getChildCount(); i++) {
                    View child = items.getChildAt(i);
                    Log.d("Tag", child.getTag() == null ? "null" : child.getTag().toString());
                    if (child.getClass() == LinearLayout.class) {
                        LinearLayout item = (LinearLayout) child;
                        EditText itemName = (EditText) item.getChildAt(0);
                        EditText itemCount = (EditText) item.getChildAt(1);
                        Spinner itemMeasurement = (Spinner) item.getChildAt(2);
                        if (!itemName.getText().toString().equals("")) {
                            itemList.add(new Item(
                                    itemName.getText().toString(),
                                    itemCount.getText().toString().equals("") ? 0 : Float.parseFloat(itemCount.getText().toString()),
                                    itemMeasurement.getSelectedItem().toString()
                            ));
                        }
                    }
                }

                if (!name.getText().toString().equals("") && !serves.getText().toString().equals("")) {
                    Recipe recipe = new Recipe(
                            name.getText().toString(),
                            Recipe.Category.valueOf(category.getSelectedItem().toString()),
                            Integer.parseInt(serves.getText().toString()),
                            stepList,
                            itemList
                    );
                    Log.d("Recipe", recipe.toString());
                    RecipeSqlWrapper db = new RecipeSqlWrapper(getApplicationContext());
                    db.addRecipe(recipe);
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                } else if (name.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Recipe must have a name", Toast.LENGTH_SHORT).show();
                } else if (serves.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Recipe must have a serving size", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
