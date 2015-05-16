package com.kauemendes.asynctask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;


public class MainActivity extends Activity {

    Button btnDevagarProcess;
    Button btnRapidoProcess;
    EditText etMsg;
    Long startingMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMsg = (EditText) findViewById(R.id.editText1);

        btnDevagarProcess = (Button) findViewById(R.id.btnDevagar);

        this.btnDevagarProcess.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new VerySlowTask().execute();
            }
        });

        btnRapidoProcess = (Button) findViewById(R.id.btnRapido);

        this.btnRapidoProcess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                etMsg.setText( (new Date()).toLocaleString() );
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class VerySlowTask extends AsyncTask<String, Long, Void> {

        private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute(){
            startingMillis = System.currentTimeMillis();

            etMsg.setText("Start Time: " + startingMillis);

            this.dialog.setMessage("Wait \nFazer Devagar esta sendo iniciado...");
            this.dialog.show();

        }

        @Override
        protected Void doInBackground( final String... args) {

            try{
                for(Long i = 0L; i < 3L; i++){
                    Thread.sleep(2000);
                    publishProgress( (Long) i );
                }

            } catch(InterruptedException e){
                Log.v("Slow-job feito", e.getMessage());
            }
            return null;
        }

        protected void onProgressUpdate(Long... value){
            super.onProgressUpdate( value );
            etMsg.append("\ntrabalhando..." + value[0]);

        }

        protected void onPostExecute( final void unsed ){
            if( this.dialog.isShowing() ){
                this.dialog.dismiss();
            }
            etMsg.append("\nEnd Time:" + (System.currentTimeMillis() - startingMillis)/1000);
            etMsg.append("\nFeito!");
        }
    }

}

