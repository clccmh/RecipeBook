package edu.uno.carter_mariah.recipebook;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeFragment extends Fragment {
    Recipe recipe;
    View view;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recipe_fragment, container, false);
        recipe = (Recipe) getArguments().getSerializable("recipe");

        setItems();
        setSteps();

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(recipe.name);

        NumberPicker servingsNumber = (NumberPicker) view.findViewById(R.id.serving_numberpicker);
        servingsNumber.setMaxValue(1000);
        servingsNumber.setMinValue(0);
        servingsNumber.setValue(recipe.servings);
        servingsNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                recipe.updateServingSize(newVal);
                setItems();
            }
        });

        return view;
    }

    public void setItems() {
        Log.d("Set items", recipe.items.toString());
        LinearLayout ingredients = (LinearLayout) view.findViewById(R.id.ingredients);
        ingredients.removeAllViews();
        for (Item item : recipe.items) {
            TextView tv = new TextView(view.getContext());
            tv.setText(item.toString());
            ingredients.addView(tv);
        }
    }

    public void setSteps() {
        Log.d("Set steps", recipe.steps.toString());
        LinearLayout steps = (LinearLayout) view.findViewById(R.id.steps);
        for (String step : recipe.steps) {
            TextView tv = new TextView(view.getContext());
            tv.setText(step);
            steps.addView(tv);
        }
    }

}
