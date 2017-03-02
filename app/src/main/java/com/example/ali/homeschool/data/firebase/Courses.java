package com.example.ali.homeschool.data.firebase;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by hossam on 01/03/17.
 */

public class Courses implements Parcelable {

    public String name;
    public String rate;
    public String subject;
    public String teacher;
    public String teacher_id;
    public String privacy;
    public String description;
    public String course_id;
   
    private Courses(){

    }
    public Courses(String name, String rate, String subject, String teacher, String teacher_id, String privacy, String description, String course_id) {
        this.name = name;
        this.rate = rate;
        this.subject = subject;
        this.teacher = teacher;
        this.teacher_id = teacher_id;
        this.privacy = privacy;
        this.description = description;
        this.course_id = course_id;
    }

    protected Courses(Parcel in) {
        this.name = (String) in.readValue((String.class.getClassLoader()));
        this.rate = (String) in.readValue((String.class.getClassLoader()));
        this.subject = (String) in.readValue((String.class.getClassLoader()));
        this.teacher =(String) in.readValue((String.class.getClassLoader()));
        this.teacher_id = (String) in.readValue((String.class.getClassLoader()));
        this.privacy = (String) in.readValue((String.class.getClassLoader()));
        this.description = (String) in.readValue((String.class.getClassLoader()));
        this.course_id = (String) in.readValue((String.class.getClassLoader()));
    }

    public static final Creator<Courses> CREATOR = new Creator<Courses>() {
        @Override
        public Courses createFromParcel(Parcel in) {
            Courses courses = new Courses(in);
            Log.v("Test","Courses : "+courses);
            return courses;
        }

        @Override
        public Courses[] newArray(int size) {
            return new Courses[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getPrivacy() {
        return privacy;
    }

    public String getRate() {
        return rate;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public String getName() {
        return name;
    }


    public String getTeacher() {
        return teacher;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i) {
        p.writeValue(name);
        p.writeValue(rate);
        p.writeValue(subject);
        p.writeValue(teacher);
        p.writeValue(teacher_id);
        p.writeValue(privacy);
        p.writeValue(description);
        p.writeValue(course_id);
    }
}


/*
       databaseReference = FirebaseDatabase.getInstance().getReference().child("courses");
    Courses
    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                // Get Post object and use the values to update the UI
                // [START_EXCLUDE]
                for (DataSnapshot x:dataSnapshot.getChildren()){
                    Log.v("Test","Child : " +x.toString() );
                    Course c = x.getValue(Course.class);
                    users.add(c);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        databaseReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

 */

/*
        databaseReference = FirebaseDatabase.getInstance().getReference().child("topics");
    Topics
    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                // Get Post object and use the values to update the UI
                // [START_EXCLUDE]
                for (DataSnapshot x:dataSnapshot.getChildren()){
                    Log.v("Test","Child : " +x.toString() );
                    Course c = x.getValue(Course.class);
                    users.add(c);
                }
                // [END_EXCLUDE]
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // [START_EXCLUDE]
                Toast.makeText(MainActivity.this, "Failed to load post.",
                        Toast.LENGTH_SHORT).show();
                // [END_EXCLUDE]
            }
        };
        databaseReference.addValueEventListener(postListener);
        // [END post_value_event_listener]

        // Keep copy of post listener so we can remove it when app stops
        mPostListener = postListener;
    }

 */

/*
databaseReference = FirebaseDatabase.getInstance().getReference().child("lessons");

    @Override
    public void onStart() {
        super.onStart();
        // Add value event listener to the post
        // [START post_value_event_listener]
        DatabaseReference myRef = databaseReference;
        myRef.orderByChild("course_id").equalTo("0").addValueEventListener(
                new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        users = new ArrayList<>();
                        // Get Post object and use the values to update the UI
                        // [START_EXCLUDE]
                        for (DataSnapshot x : dataSnapshot.getChildren()) {
                            Log.v("Test", "Child : " + x.toString());
                            Course c = x.getValue(Course.class);
                            users.add(c);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError DatabaseError) {

                    }
                });




//
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                users = new ArrayList<>();
//                // Get Post object and use the values to update the UI
//                // [START_EXCLUDE]
//                for (DataSnapshot x:dataSnapshot.getChildren()){
//                    Log.v("Test","Child : " +x.toString() );
//                    Course c = x.getValue(Course.class);
//                    users.add(c);
//                }
//                // [END_EXCLUDE]
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Getting Post failed, log a message
//                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
//                // [START_EXCLUDE]
//                Toast.makeText(MainActivity.this, "Failed to load post.",
//                        Toast.LENGTH_SHORT).show();
//                // [END_EXCLUDE]
//            }
//        };
//        databaseReference.addValueEventListener(postListener);
//        // [END post_value_event_listener]
//
//        // Keep copy of post listener so we can remove it when app stops
//        mPostListener = postListener;
    }
 */