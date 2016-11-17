package edu.uno.carter_mariah.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by carter on 11/17/16.
 */
public class RecipeSqlWrapper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "recipe_book.db";

    public static final String TABLE_RECIPES = "recipes";
    public static final String RECIPES_COLUMN_ID = "_id";
    public static final String RECIPES_COLUMN_NAME = "name";
    public static final String RECIPES_COLUMN_CATEGORY = "category";

    public static final String TABLE_ITEMS = "items";
    public static final String ITEMS_COLUMN_RECIPEID = "recipe_id";
    public static final String ITEMS_COLUMN_NAME = "name";
    public static final String ITEMS_COLUMN_QUANTITY = "quantity";
    public static final String ITEMS_COLUMN_MEASUREMENT_UNIT = "measurement_unit";

    public static final String TABLE_STEPS = "steps";
    public static final String STEPS_COLUMN_RECIPEID = "recipe_id";
    public static final String STEPS_COLUMN_STEP_NUMBER = "step_number";
    public static final String STEPS_COLUMN_DESCRIPTION = "description";

    public RecipeSqlWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String recipeQuery = "CREATE TABLE " + TABLE_RECIPES + "(" +
                RECIPES_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPES_COLUMN_NAME + " TEXT UNIQUE, " +
                RECIPES_COLUMN_CATEGORY + " TEXT " +
                ");";
        db.execSQL(recipeQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        onCreate(db);
    }

    /**
     * Adds a product to the database
     *
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(RECIPES_COLUMN_NAME, recipe.name);
        recipeValues.put(RECIPES_COLUMN_CATEGORY, recipe.category.toString());
        db.insert(TABLE_RECIPES, null, recipeValues);

        int id = 0; //TODO: GET RECIPE ID

        for (int i = 0; i < recipe.items.length; i++) {
            ContentValues itemValues = new ContentValues();
            itemValues.put(ITEMS_COLUMN_RECIPEID, id);
            itemValues.put(ITEMS_COLUMN_NAME, recipe.items[i]);
            itemValues.put(ITEMS_COLUMN_QUANTITY, recipe.quantities[i]);
            itemValues.put(ITEMS_COLUMN_MEASUREMENT_UNIT, recipe.measurementUnit[i]);
            db.insert(TABLE_ITEMS, null, itemValues);
        }
        db.close();

    }

    /**
     * Gets all recipes of a category from the database
     *
     * @param category
     */
    public void getRecipes(Recipe.Category category) {

    }

}
