package com.raslab.cloudnotepad;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.raslab.cloudnotepad.pojo.PageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    private RecyclerView recyclerView;
    private Context context;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView=view.findViewById(R.id.recyclerViewitem);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String[] mTitleNames = {"Notes", "To-Do List"};
        setActionBarTitle(mTitleNames[0]);

        ViewPager viewPager = view.findViewById(R.id.viewPager2);
        TabLayout tabLayout = view.findViewById(R.id.tabLayout2);
        TabItem tabItem1= view.findViewById(R.id.noteTab);
        TabItem tabItem2= view.findViewById(R.id.todolistTab);
        tabLayout.setupWithViewPager(viewPager);
        PageAdapter pageAdapter = new PageAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).setText(getResources().getText(R.string.notes));
        tabLayout.getTabAt(1).setText(getResources().getText(R.string.to_do_list));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                setActionBarTitle(mTitleNames[pos]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //((MainActivity) context).finish();
    }

    private void setActionBarTitle(String mTitleName) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mTitleName);
    }

}
