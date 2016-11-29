package edu.uno.carter_mariah.recipebook;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

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

    @TargetApi(Build.VERSION_CODES.M)
    public void setItems() {
        Log.d("Set items", recipe.items.toString());
        LinearLayout ingredients = (LinearLayout) view.findViewById(R.id.ingredients);
        ingredients.removeAllViews();
        for (final Item item : recipe.items) {
            final TextView tv = new TextView(view.getContext());
            tv.setText(item.toString());
            tv.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Medium);
            tv.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.performLongClick();
                }
            });

            tv.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                View v;
                @Override
                public void onCreateContextMenu(ContextMenu menu, final View v, final ContextMenu.ContextMenuInfo menuInfo) {
                    this.v = v;
                    MenuItem.OnMenuItemClickListener itemClickListener = new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (!item.convert(menuItem.getTitle().toString())) {
                                Toast.makeText(v.getContext(), "Those measurements don't work together. I cannot convert the item.", Toast.LENGTH_SHORT).show();
                            } else {
                                tv.setText(item.toString());
                            }
                            return false;
                        }
                    };

                    menu.setHeaderTitle("Convert");
                    for (String val : item.possibleConversions) {
                        menu.add(val).setOnMenuItemClickListener(itemClickListener);
                    }
                }
            });

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
