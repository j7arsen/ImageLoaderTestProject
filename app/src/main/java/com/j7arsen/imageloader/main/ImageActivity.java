package com.j7arsen.imageloader.main;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.j7arsen.imageloader.R;
import com.j7arsen.imageloader.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by arsen on 21.03.17.
 */

public class ImageActivity extends BaseActivity {

    @BindView(R.id.navigation_bar)
    Toolbar mToolbar;

    private Unbinder mUnbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_layout);
        mUnbinder = ButterKnife.bind(this);
        initToolbar();

        if(savedInstanceState == null) {
            openFragment();
        }
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(this.getResources().getString(R.string.label_image_list_activity_title));
    }


    protected void openFragment() {
        getFragmentManager().beginTransaction().replace(R.id.container, ImageFragment.newInstance()).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


}
