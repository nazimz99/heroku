package edu.tacoma.uw.nazimz.courses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import model.Course;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseAddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseAddFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AddListener mAddListener;

    public interface AddListener {
        public void addCourse(Course course);
    }

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CourseAddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @param //param2 Parameter 2.
     * @return A new instance of fragment CourseAddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CourseAddFragment newInstance(String param1, String param2) {
        CourseAddFragment fragment = new CourseAddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAddListener = (AddListener) getActivity();
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_course_add, container, false);
        getActivity().setTitle("Add a New Course");

        final EditText courseIdEditText = view.findViewById(R.id.add_course_id);
        final EditText courseShortDescEditText = view.findViewById(R.id.add_course_short_desc);
        final EditText courseLongDescEditText = view.findViewById(R.id.add_course_long_desc);
        final EditText coursePrereqsEditText = view.findViewById(R.id.add_course_prereqs);
        Button addButton = view.findViewById(R.id.btn_add_course);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = courseIdEditText.getText().toString();
                String courseShortDesc = courseShortDescEditText.getText().toString();
                String courseLongDesc = courseLongDescEditText.getText().toString();
                String coursePrereqs = coursePrereqsEditText.getText().toString();
                Course course = new Course(courseId, courseShortDesc, courseLongDesc, coursePrereqs);
                if (mAddListener != null) {
                    mAddListener.addCourse(course);
                }
            }
        });
        return view;
    }
}
