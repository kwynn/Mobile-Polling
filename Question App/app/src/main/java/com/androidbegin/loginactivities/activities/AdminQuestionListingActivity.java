package com.androidbegin.loginactivities.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class AdminQuestionListingActivity extends Activity {

    private String groupID;
    private boolean isAdmin;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_question_listing_activity);

        Bundle b = this.getIntent().getExtras();
        groupID = b.getString("groupID");
        isAdmin = b.getBoolean("isAdmin");
        groupName = b.getString("groupName");

        // Locate TextView in welcome.xmlroups.xml
        TextView txt = (TextView) findViewById(R.id.txt);

        // Set the currentUser String into TextView
        txt.setText("You are viewing the group, " + groupName);

        //Past Question Button
        Button pastQuestion = (Button) findViewById(R.id.pastQuestion);

        //Past Question Listener
        pastQuestion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminPastQuestionActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();
            }
        });

        //Current Question Button
        Button currentQuestion = (Button) findViewById(R.id.currentQuestion);

        //Current Question Listener
        currentQuestion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminCurrentQuestionActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        });

        /*
        //Future Question Button
        Button futureQuestion = (Button) findViewById(R.id.futureQuestion);

        //Future Question Listener
        futureQuestion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);

                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminFutureQuestionActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
*/

        //Create Question Button
        Button createQuestion = (Button) findViewById(R.id.createQuestion);

        //Create Question Listener
        createQuestion.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                Bundle b = new Bundle();
                b.putString("groupID", groupID);
                b.putBoolean("isAdmin", isAdmin);
                b.putString("groupName", groupName);
                Intent intent = new Intent(AdminQuestionListingActivity.this,
                        AdminCreateQuestionActivity.class);
                intent.putExtras(b);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {

        Bundle b = new Bundle();
        b.putString("groupID", groupID);
        b.putBoolean("isAdmin", isAdmin);
        b.putString("groupName", groupName);
        Intent intent = new Intent(AdminQuestionListingActivity.this,
                GroupViewActivity.class);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}

