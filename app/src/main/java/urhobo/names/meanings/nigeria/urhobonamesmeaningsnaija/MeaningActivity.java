package urhobo.names.meanings.nigeria.urhobonamesmeaningsnaija;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;


public class MeaningActivity extends AppCompatActivity {
    private TextView wordMeaning;
    private AdView mAdView;
    private TextToSpeech convertToSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });
        MobileAds.initialize(this, "ca-app-pub-2439901986027384~5204985877");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        String dictionaryId = bundle.getString("DICTIONARY_ID");

        String Name = dictionaryId;
        getSupportActionBar().setTitle(Name);
     //   TextView word = (TextView)findViewById(R.id.word);

        wordMeaning = (TextView)findViewById(R.id.dictionary);

        FancyButton textToSpeech = (FancyButton)findViewById(R.id.button);

        DbBackend dbBackend = new DbBackend(MeaningActivity.this);

        QuizObject allQuizQuestions = dbBackend.getQuizByName(Name);

      //  word.setText(allQuizQuestions.getWord());

        wordMeaning.setText(allQuizQuestions.getDefinition());

        textToSpeech.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                final String convertTextToSpeech = wordMeaning.getText().toString();

                convertToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {

                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)

                    @Override

                    public void onInit(int status) {

                        if(status != TextToSpeech.ERROR){

                            convertToSpeech.setLanguage(Locale.US);

                            convertToSpeech.speak(convertTextToSpeech, TextToSpeech.QUEUE_FLUSH, null, null);

                        }

                    }

                });

            }

        });

    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {

// Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_dictionary, menu);

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
            Intent intent = new Intent(MeaningActivity.this, About.class);
            startActivity(intent);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override

    protected void onPause() {

        if(convertToSpeech != null){

            convertToSpeech.stop();

            convertToSpeech.shutdown();

        }

        super.onPause();

    }

}