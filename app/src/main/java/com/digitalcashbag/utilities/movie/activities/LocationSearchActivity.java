package com.digitalcashbag.utilities.movie.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

import com.digitalcashbag.R;
import com.digitalcashbag.utilities.movie.adapter.LocationSearchAdapter;

public class LocationSearchActivity extends AppCompatActivity {
    Bundle param = new Bundle();
    private ListView listView;

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.locationsearchable_layout);

        listView = findViewById(R.id.listView);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            finish();
            hideKeyboard(LocationSearchActivity.this);
//                Toast.makeText( LocationSearchActivity.this, "clicked search result item is" + ((TextView) view).getText(), Toast.LENGTH_SHORT ).show();
        });
        // search
        handleSearch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleSearch();
    }

    private void handleSearch() {
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String searchQuery = intent.getStringExtra(SearchManager.QUERY);

            LocationSearchAdapter adapter = new LocationSearchAdapter(this, android.R.layout.simple_dropdown_item_1line,
                    StoresData.filterData(searchQuery));
            listView.setAdapter(adapter);

        } else if (Intent.ACTION_VIEW.equals(intent.getAction())) {
            String selectedSuggestionRowId = intent.getDataString();
//            Toast.makeText( this, "selected search suggestion " + selectedSuggestionRowId, Toast.LENGTH_SHORT ).show();
        }
    }
}