package edu.uno.carter_mariah.recipebook;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by carter on 11/1/16.
 */
public class RecipeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_fragment, container, false);
        ListView ingredients = (ListView) view.findViewById(R.id.ingredients);
        ingredients.setAdapter(new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, getArguments().getStringArray("ingredients")));
        return view;
    }

}
