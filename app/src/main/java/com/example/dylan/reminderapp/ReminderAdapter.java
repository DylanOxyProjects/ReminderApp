package com.example.dylan.reminderapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class ReminderAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<ReminderObject> reminderList;
    private LayoutInflater inflator;

    //constructor
    public ReminderAdapter(Context context, ArrayList<ReminderObject> reminderList){
        this.context = context;
        this.reminderList = reminderList;
        this.inflator = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return reminderList.size();
    }

    @Override
    public Object getItem(int position) {
        return reminderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // View class represents the basic building block for user interface components.
        // A View occupies a rectangular area on the screen and is responsible for drawing and event handling.
        // View is the base class for widgets, which are used to create interactive UI components (buttons, text fields, etc.).

        // The ViewGroup subclass is the base class for layouts,
        // which are invisible containers that hold other Views (or other ViewGroups) and define their layout properties.

        // create a ViewHolder variable called holder that will hold other views or viewgroups
        ViewHolder holder;

        // check if the view already exists
        // if yes, you don't need to inflate and findViewbyID again

        if (view == null){
            //inflate
            view = inflator.inflate(R.layout.list_item_reminder, viewGroup, false);
            // add views to the holder
            holder = new ViewHolder();
            holder.reminderListTitle = view.findViewById(R.id.reminderListTitle);
            holder.reminderListDate = view.findViewById(R.id.dateView);
            holder.reminderListTime = view.findViewById(R.id.timeView);
            //holder.reminderlistDateTime = view.findViewById(R.id.reminderListDateTime);
            holder.reminderListUrgency = view.findViewById(R.id.reminderListUrgency);
            holder.reminderListTopic = view.findViewById(R.id.reminderListTopic);
            holder.reminderListPicture = view.findViewById(R.id.reminderListPicture);

            // add the holder to the view
            // for future use
            view.setTag(holder);
        }
        else{
            // get the view holder from converview
            holder = (ViewHolder)view.getTag();
        }
        // get relavate subview of the row view
        ImageView reminderListPicture = holder.reminderListPicture;
        TextView reminderListTitle = holder.reminderListTitle;
        TextView reminderListDate = holder.reminderListDate;
        TextView reminderListTime = holder.reminderListTime;
        //TextView reminderlistDateTime = holder.reminderlistDateTime;
        TextView reminderListUrgency = holder.reminderListUrgency;
        TextView reminderListTopic = holder.reminderListTopic;

        // get corresonpinding movie for each row
        ReminderObject currentReminder = (ReminderObject)getItem(i);


        // update the row view's textviews and imageview to display the information
        reminderListTitle.setText("Title: " + currentReminder.reminderTitle);
        reminderListDate.setText("Date: " + currentReminder.date);
        reminderListTime.setText("Time: " + currentReminder.time);
        //reminderlistDateTime.setText(currentReminder.reminderDateTime);
        reminderListUrgency.setText("Urgency: " + currentReminder.reminderUrgency);
        reminderListTopic.setText("Topic: " + currentReminder.reminderTopic);
        Picasso.get().load("https://cdn1.iconfinder.com/data/icons/office-22/48/alarm-clock-512.png").into(reminderListPicture);


        return view;
    }


    private static class ViewHolder{
        ImageView reminderListPicture;
        TextView reminderListTitle;
        TextView reminderListDate;
        TextView reminderListTime;
        //TextView reminderlistDateTime;
        TextView reminderListUrgency;
        TextView reminderListTopic;

    }
}
