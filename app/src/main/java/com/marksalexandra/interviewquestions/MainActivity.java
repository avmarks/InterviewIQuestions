package com.marksalexandra.interviewquestions;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * This class is responsible for handing the functionality on the main menu.
 * Here we get the arrayList from DB class and create an adapter for displaying it in the
 * List view. Additionally, search functionality for the item is also handled here by
 * calling the lookUp method from DB class.
 * @author avmarks
 */
public class MainActivity extends AppCompatActivity implements AppInfo{

    //declaration for widgets
    EditText mSearchTerm;
    Button mSearchButton;
    ListView mListView;
    TextView mTextView;

    ObjectArrayAdapter mArrayAdapter;
    ArrayList<InterviewQuestionsModel> questionsModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchButton = (Button) findViewById(R.id.searchButton);
        mSearchTerm = (EditText) findViewById(R.id.searchTerm);

        mTextView = (TextView) findViewById(android.R.id.empty);
        mListView = (ListView) findViewById(android.R.id.list);

        //manually attach the 'empty' textview to the listview
        mListView.setEmptyView(mTextView);

        //load the arrayList from myDbHandler class
        questionsModel = new ArrayList<InterviewQuestionsModel>();

        /* create a list adapter bound to a cursor
         * the adapter manages the data model and adapts it to the
         * individual rows in the list view
         */
        mArrayAdapter = new ObjectArrayAdapter(this, R.layout.detail_line, questionsModel);

        mListView.setAdapter(mArrayAdapter);


        mSearchButton.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mSearchTerm.getText().toString() !="") {
                    lookUpTerm(v);

                } else {
                    Snackbar.make(v, "No such term in DB", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


    }


    public void lookUpTerm (View view) {

        //instantiate DB handler
        MyDBHandler myDBHandler = new MyDBHandler(this, null, null, 1);

        questionsModel = myDBHandler.findTerm(mSearchTerm.getText().toString());


        if(questionsModel !=null) {

             mArrayAdapter = new ObjectArrayAdapter(this, R.layout.detail_line, questionsModel);
             mListView.setAdapter(mArrayAdapter);

        } else {
            Snackbar.make(view, "No such term in DB", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        }

    }

}
