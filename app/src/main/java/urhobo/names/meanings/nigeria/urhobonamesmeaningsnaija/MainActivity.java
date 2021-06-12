package urhobo.names.meanings.nigeria.urhobonamesmeaningsnaija;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    private EditText filterText;
    private AdView mAdView;
    ListView itemList;
    SearchView searchView;
    ImageView iki;
    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        filterText = (EditText)findViewById(R.id.editText);
        iki = (ImageView) findViewById(R.id.iki);
        MobileAds.initialize(this, "ca-app-pub-2439901986027384~5204985877");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        itemList = (ListView)findViewById(R.id.listView);

        DbBackend dbBackend = new DbBackend(MainActivity.this);

        String[] terms = dbBackend.dictionaryWords();

        listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, terms);

        itemList.setAdapter(listAdapter);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

// make Toast when click
                String selectedFromList = (itemList.getItemAtPosition(position)).toString();
              //  Toast.makeText(getApplicationContext(), "Position " + selectedFromList, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, MeaningActivity.class);

                intent.putExtra("DICTIONARY_ID",  selectedFromList);

                startActivity(intent);

            }

        });

//        filterText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                MainActivity.this.listAdapter.getFilter().filter(s);
//
//            }
//
//            @Override
//
//            public void afterTextChanged(Editable s) {
//
//            }
//
//        });

        searchView=(SearchView) findViewById(R.id.searchView);
        searchView.setQueryHint("Search For Name");
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getBaseContext(), query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.listAdapter.getFilter().filter(newText);

                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // searchView expanded
                    iki.setVisibility(View.GONE);
                } else {
                    // searchView not expanded
                    iki.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

// Handle action bar item clicks here. The action bar will

// automatically handle clicks on the Home/Up button, so long

// as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

//noinspection SimplifiableIfStatement

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:Pensoftcorp@gmail.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "New Yoruba Name Suggestion");
            intent.putExtra(Intent.EXTRA_TEXT, "Kindly Tell us the name and its meaning ...");
            startActivity(Intent.createChooser(intent, "Send Email"));
            return true;

        } else if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
            return true;

        }




        return super.onOptionsItemSelected(item);

    }

}