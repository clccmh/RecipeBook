package edu.uno.carter_mariah.recipebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String RECIPES_COLUMN_SERVINGS = "servings";


    public static final String TABLE_ITEMS = "items";
    public static final String ITEMS_COLUMN_ID = "_id";
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
                RECIPES_COLUMN_CATEGORY + " TEXT, " +
                RECIPES_COLUMN_SERVINGS + " INTEGER " +
                ");";

        String itemQuery = "CREATE TABLE " + TABLE_ITEMS + "(" +
                ITEMS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ITEMS_COLUMN_RECIPEID + " INTEGER, " +
                ITEMS_COLUMN_NAME + " TEXT, " +
                ITEMS_COLUMN_QUANTITY + " INTEGER, " +
                ITEMS_COLUMN_MEASUREMENT_UNIT + " TEXT, " +
                "FOREIGN KEY(" + ITEMS_COLUMN_RECIPEID + ") REFERENCES " + TABLE_RECIPES +
                ");";

        String stepsQuery = "CREATE TABLE " + TABLE_STEPS + "(" +
                STEPS_COLUMN_RECIPEID + " INTEGER, " +
                STEPS_COLUMN_STEP_NUMBER + " INTEGER, " +
                STEPS_COLUMN_DESCRIPTION + " TEXT, " +
                "FOREIGN KEY(" + STEPS_COLUMN_RECIPEID + ") REFERENCES " + TABLE_RECIPES +
                ");";

        Log.d("Creating recipe table", recipeQuery);
        db.execSQL(recipeQuery);
        Log.d("Creating item table", itemQuery);
        db.execSQL(itemQuery);
        Log.d("Creating steps table", stepsQuery);
        db.execSQL(stepsQuery);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STEPS);
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
        recipeValues.put(RECIPES_COLUMN_SERVINGS, recipe.servings);
        try {
            db.insertOrThrow(TABLE_RECIPES, null, recipeValues);

            Cursor cursor = db.rawQuery("SELECT MAX(_id) FROM recipes;", null);
            int id = 0;
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
            } else {
                throw new SQLiteException("Recipe ID not found");
            }

            for (int i = 0; i < recipe.items.size(); i++) {
                ContentValues itemValues = new ContentValues();
                itemValues.put(ITEMS_COLUMN_RECIPEID, id);
                itemValues.put(ITEMS_COLUMN_NAME, recipe.items.get(i).name);
                itemValues.put(ITEMS_COLUMN_QUANTITY, recipe.items.get(i).quantity);
                itemValues.put(ITEMS_COLUMN_MEASUREMENT_UNIT, recipe.items.get(i).measurement);
                db.insert(TABLE_ITEMS, null, itemValues);
            }

            for (int i = 0; i < recipe.steps.size(); i++) {
                ContentValues stepValues = new ContentValues();
                stepValues.put(STEPS_COLUMN_RECIPEID, id);
                stepValues.put(STEPS_COLUMN_STEP_NUMBER, i);
                stepValues.put(STEPS_COLUMN_DESCRIPTION, recipe.steps.get(i));
                db.insert(TABLE_STEPS, null, stepValues);
            }
        } catch (SQLiteConstraintException e) {
            Log.e("ERROR", e.toString());
        } finally {
            db.close();
        }
    }

    /**
     * Gets all recipes of a category from the database
     *
     * @param category
     */
    public RecipeList getRecipes(Recipe.Category category) {
        RecipeList recipes = new RecipeList(this);
        String query = category == Recipe.Category.ALL
                ? "SELECT * FROM " + TABLE_RECIPES
                : "SELECT * FROM " + TABLE_RECIPES + " WHERE " + RECIPES_COLUMN_CATEGORY + "=" + category.toString();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Log.d("db found", String.valueOf(cursor.getCount()));

        if (cursor.moveToFirst()) {
            do {
                recipes.add(new Recipe(cursor.getInt(0), cursor.getString(1), Recipe.Category.valueOf(cursor.getString(2)), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }


        for (Recipe recipe : recipes) {
            db = this.getWritableDatabase();
            query = "SELECT " + STEPS_COLUMN_DESCRIPTION + " FROM " + TABLE_STEPS +
                    " WHERE " + STEPS_COLUMN_RECIPEID + "=" + recipe.id;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    recipe.steps.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

            db = this.getWritableDatabase();
            query = "SELECT " + ITEMS_COLUMN_NAME + ", " + ITEMS_COLUMN_QUANTITY + ", " + ITEMS_COLUMN_MEASUREMENT_UNIT +
                    " FROM " + TABLE_ITEMS + " WHERE " + ITEMS_COLUMN_RECIPEID + "=" + recipe.id;
            cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    recipe.items.add(new Item(cursor.getString(0), cursor.getInt(1), cursor.getString(2)));
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return recipes;
    }

    public boolean remove(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean success = true;
        success = success && (db.delete(TABLE_RECIPES, RECIPES_COLUMN_ID + "=" + recipe.id, null) > 0);
        success = success && (db.delete(TABLE_ITEMS, ITEMS_COLUMN_RECIPEID + "=" + recipe.id, null) > 0);
        success = success && (db.delete(TABLE_STEPS, STEPS_COLUMN_RECIPEID + "=" + recipe.id, null) > 0);
        db.close();
        return success;
    }
}
