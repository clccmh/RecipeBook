package edu.uno.carter_mariah.recipebook;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        Recipe recipe = (Recipe) getArguments().getSerializable("recipe");
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(recipe.name);

        Log.d("Set items", recipe.items.toString());
        LinearLayout ingredients = (LinearLayout) view.findViewById(R.id.ingredients);
        for (Item item : recipe.items) {
            TextView tv = new TextView(view.getContext());
            tv.setText(item.toString());
            ingredients.addView(tv);
        }

        Log.d("Set steps", recipe.steps.toString());
        LinearLayout steps = (LinearLayout) view.findViewById(R.id.steps);
        for (String step : recipe.steps) {
            TextView tv = new TextView(view.getContext());
            tv.setText(step);
            steps.addView(tv);
        }

        NumberPicker servingsNumber = (NumberPicker) view.findViewById(R.id.serving_numberpicker);
        servingsNumber.setMaxValue(1000);
        servingsNumber.setMinValue(0);
        servingsNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Toast.makeText(view.getContext(), "Number changed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
