package com.marksalexandra.interviewquestions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ObjectArrayAdapter extends ArrayAdapter<InterviewQuestionsModel> {

    // declare ArrayList of items
    private ArrayList<InterviewQuestionsModel> objects;

    public ObjectArrayAdapter(@NonNull Context context, int resource, ArrayList<InterviewQuestionsModel> objects) {

        super(context, resource, objects);
        this.objects = (ArrayList) objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // assign the view we are converting to a local variable
        View view = convertView;

        // Check to see if view is null.  If so, we have to inflate the view
        // "inflate" means to render or show the view
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.detail_line, null);
        }



        /*
            Recall that the variable position is sent in as an argument to this method

            The variable simply refers to the position of the current object on the list.
            The ArrayAdapter iterate through the list we sent it

            iqObject refers to the current InterviewQuestionsModel object
         */
        InterviewQuestionsModel iqObject = objects.get(position);

        if (iqObject != null) {
            //obtain a reference to widgets in the defined layout "wire up the widgets from detail_line"
            // note:  view.  which ties it to detail_line


            TextView mTerm = (TextView) view.findViewById(R.id.term);
            TextView mTermDefinition = (TextView) view.findViewById(R.id.termDefinition);

            if (mTerm != null) {
                mTerm.setText(iqObject.getTerm());
            }
            if (mTermDefinition != null) {
                mTermDefinition.setText(iqObject.getTermMeaning());
            }
        }

        // the view (my custom detail_line with loaded data) returned to our Activity
        //return super.getView(position, convertView, parent);

        return view;
    }
}