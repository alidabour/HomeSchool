package com.almanara.homeschool.adapter;

/**
 * Created by hossam on 21/06/17.
 */

//
//public class StudentTopicsAdapter extends RecyclerView.Adapter<StudentTopicsAdapter.TopicViewHolder>{
//    static String lessonId;
//    static String courseId;
//    Activity activity;
//    List<TopicModel> topicModelList;
//    public StudentTopicsAdapter.OnClickHandler onClickHandler;
//
//
//    public interface OnClickHandler {
//        void onClick(TopicModel test);
//    }
//    public StudentTopicsAdapter(List<TopicModel> topicModelList, StudentTopicsAdapter.OnClickHandler onClickHandler,
//                                Activity activity , String courseId , String lessonId) {
//        this.topicModelList = topicModelList;
//        this.onClickHandler = onClickHandler;
//        this.activity = activity;
//        this.courseId = courseId;
//        this.lessonId = lessonId;
//    }
//
//    @Override
//    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.student_lesson_item_view,parent,false);
//        return new TopicViewHolder(itemView);
//    }
//
//    @Override
//    public void onBindViewHolder(final TopicViewHolder holder, int position) {
//        final TopicModel topicModelInFocus = topicModelList.get(position);
//        holder.topicName.setText(topicModelInFocus.getName());
//        holder.topicName.setTextColor(ContextCompat.getColor(activity, R.color.colorLesson));
//        holder.startbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                startIntentFromAdapter(activity,topicModelInFocus);
//
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return topicModelList.size();
//    }
//
//    public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private TextView topicName;
//        protected Button startbtn;
//
//        public TopicViewHolder(View itemView) {
//            super(itemView);
//            topicName = (TextView) itemView.findViewById(R.id.lessonName);
//            startbtn = (Button) itemView.findViewById(R.id.startbtn);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            int p = getAdapterPosition();
//            onClickHandler.onClick(topicModelList.get(p));
//        }
//    }
//
//
//    public static void startIntentFromAdapter(Activity activity,TopicModel topicModel){
//        Intent intent = new Intent(activity,
//                ClassActivity.class);
//        intent.putExtra("topicid", topicModel.getId());
//        intent.putExtra("topicname", topicModel.getName());
//        intent.putExtra("layout", topicModel.getLayout());
//        intent.putExtra("lessonid", lessonId);
//        intent.putExtra("courseId", courseId);
//        activity.startActivity(intent);
//    }
//}
//
