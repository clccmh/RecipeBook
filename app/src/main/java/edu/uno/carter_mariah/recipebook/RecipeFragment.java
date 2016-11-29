package edu.uno.carter_mariah.recipebook;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
        setHasOptionsMenu(true);

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
            final TextView tv = (TextView) View.inflate(view.getContext(), R.layout.recipe_fragment_text, null);
            tv.setText(item.toString());

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

    @TargetApi(Build.VERSION_CODES.M)
    public void setSteps() {
        Log.d("Set steps", recipe.steps.toString());
        LinearLayout steps = (LinearLayout) view.findViewById(R.id.steps);
        for (String step : recipe.steps) {
            TextView tv = (TextView) View.inflate(view.getContext(), R.layout.recipe_fragment_text, null);
            tv.setText(step);
            steps.addView(tv);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       inflater.inflate(R.menu.recipe, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            RecipeSqlWrapper db = new RecipeSqlWrapper(view.getContext());
            db.remove(recipe);

            // This is dirty. There is probably a better way to do this.
            // TODO: Find a more correct way to do this.
            super.getActivity().finish();
            Intent intent = new Intent(view.getContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.action_edit) {
            Intent intent = new Intent(view.getContext(), EditActivity.class);
            intent.putExtra("recipe", recipe);
            startActivity(intent);
        }
        return true;
    }


}
