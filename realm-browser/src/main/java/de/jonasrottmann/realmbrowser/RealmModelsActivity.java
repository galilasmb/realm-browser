package de.jonasrottmann.realmbrowser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

import static de.jonasrottmann.realmbrowser.RealmBrowser.*;

public class RealmModelsActivity extends AppCompatActivity {

    private static final String EXTRAS_REALM_FILE_NAME = "EXTRAS_REALM_FILE_NAME";



    public static void start(@NonNull Activity activity, @NonNull String realmFileName) {
        Intent intent = new Intent(activity, RealmModelsActivity.class);
        intent.putExtra(EXTRAS_REALM_FILE_NAME, realmFileName);
        activity.startActivity(intent);
    }

    public static void start(@NonNull Context context, @NonNull String realmFileName) {
        Intent intent = new Intent(context, RealmModelsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(EXTRAS_REALM_FILE_NAME, realmFileName);
        context.startActivity(intent);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_realm_list_view);

        List<String> modelList = new ArrayList<>();
        for (Class<? extends RealmObject> file : getInstance().getRealmModelList()) {
            modelList.add(file.getSimpleName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, modelList);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(position);
            }
        });
    }



    private void onItemClicked(int position) {
        String realmFileName = getIntent().getStringExtra(EXTRAS_REALM_FILE_NAME);
        RealmBrowserActivity.start(this, position, realmFileName);
    }
}
