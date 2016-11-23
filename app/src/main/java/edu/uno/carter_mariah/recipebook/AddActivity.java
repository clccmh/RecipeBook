package edu.uno.carter_mariah.recipebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Spinner categorySpinner = (Spinner) findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                Recipe.getCategoriesAsStringArray()
                ));
    }
}
