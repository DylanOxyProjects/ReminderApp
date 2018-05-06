package com.example.dylan.reminderapp;

//

import java.io.Serializable;

public class ReminderObject implements Serializable{

    public String reminderTitleSentence;
    public String reminderDateTimeSentence;
    public String reminderUrgencySentence;
    public String reminderTopicSentence;
    public String reminderTitle;
    public String reminderDateTime;
    public String reminderUrgency;
    public String reminderTopic;
    public String time;
    public String date;



    ReminderObject(){
        this.reminderTitleSentence = "Your Reminder Title Will Appear Here! :D";
        this.reminderDateTimeSentence = "Your Reminder Date/Time Will Appear Here! :D";
        this.reminderUrgencySentence = "Your Reminder Urgency Will Appear Here! :D";
        this.reminderTopicSentence = "Your Reminder Topic Will Appear Here! :D";
        this.reminderTitle = "Your Reminder Title Will Appear Here! :D";
        this.reminderDateTime = "Your Reminder Date/Time Will Appear Here! :D";
        this.reminderUrgency = "Your Reminder Urgency Will Appear Here! :D";
        this.reminderTopic = "Your Reminder Topic Will Appear Here! :D";
    }






}
