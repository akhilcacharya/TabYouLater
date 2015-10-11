package xyz.iou.app.iou;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import xyz.iou.app.iou.Models.Person;
import xyz.iou.app.iou.Models.Responses.LoginResponse;
import xyz.iou.app.iou.Services.IOUService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String id = sharedPrefs.getString("id", "");

        if(id.equals("")){
            startActivity(new Intent(this, BaseActivity.class));
            return;
        }

        //Build service
        final IOUService service = buildService();

        //Handle log
        final TextView message = (TextView) findViewById(R.id.activity_login_message);
        final EditText username = (EditText) findViewById(R.id.activity_login_username);
        final EditText password = (EditText) findViewById(R.id.activity_login_password);

        Button submit = (Button) findViewById(R.id.activity_login_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                service.login(username.getText().toString(), password.getText().toString(), new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Response<LoginResponse> response, Retrofit retrofit) {
                        Person person = response.body().person;
                        String msg = response.body().message;

                        if(msg != null && !msg.equals("")){
                            message.setText(msg);
                            return;
                        }

                        //Save customer ID to shared preferences
                        //Use in the future;
                        sharedPrefs.edit().putString("id", person.getCustomerId()).apply();
                        sharedPrefs.edit().putString("name", person.getName()).apply();
                        startActivity(new Intent(LoginActivity.this, BaseActivity.class));

                        return;
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        message.setText(t.getMessage());
                    }
                });

            }
        });
    }

    private IOUService buildService(){
        String endpoint = getString(R.string.endpoint);
        Retrofit adapter = new Retrofit.Builder().baseUrl(endpoint).build();
        IOUService service = adapter.create(IOUService.class);
        return service;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
