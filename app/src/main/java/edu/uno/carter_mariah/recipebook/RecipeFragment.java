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
import android.widget.TextView;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
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
        return view;
    }

}
