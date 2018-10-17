package com.veyselim.app.tsgundem1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.veyselim.app.tsgundem1.Tools.SystemTools;
import com.veyselim.app.tsgundem1.Tools.WebTools;

public class FeedBackActivity extends AppCompatActivity {

    public EditText EditName, EditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        EditName = (EditText) findViewById(R.id.EditName);
        EditMessage = (EditText) findViewById(R.id.EditMessage);
    }

    public void BtnBackClick(View v) {
        finish();
    }

    public void BtnSendClick(View v) {
        if (EditName.getText().toString().matches("") || EditMessage.getText().toString().matches("")) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (IsNetworkConnection()) {
            String tempText = String.format("Ad : %s\nMesaj : %s", EditName.getText().toString(), EditMessage.getText().toString());
            WebTools.SlackPost(getApplicationContext(), SystemTools.GetSystemInfo(getApplicationContext(), tempText));

            Toast.makeText(this, "Mesajınız gönderildi.", Toast.LENGTH_SHORT).show();

            EditName.setText("");
            EditMessage.setText("");
        } else {
            Toast.makeText(getApplicationContext(), "LÜTFEN İNTERNET BAĞLANTINIZI KONTROL EDİN", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean IsNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}
