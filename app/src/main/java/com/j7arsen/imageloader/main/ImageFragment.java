package com.j7arsen.imageloader.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.j7arsen.imageloader.R;
import com.j7arsen.imageloader.app.UIConfig;
import com.j7arsen.imageloader.base.BaseActivity;
import com.j7arsen.imageloader.base.BaseFragment;
import com.j7arsen.imageloader.error.ErrorHandler;
import com.j7arsen.imageloader.managers.RequestManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by arsen on 21.03.17.
 */

public class ImageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String SAVE_IMAGE_LIST = "ShotListFragment.SAVE_IMAGE_LIST";

    @BindView(R.id.rl_image_content)
    RelativeLayout mRlShotContent;
    @BindView(R.id.rv_image_list)
    RecyclerView mRvShotList;
    @BindView(R.id.ll_image_list_empty)
    LinearLayout mLLEmptyShotList;

    @BindView(R.id.rl_progress)
    RelativeLayout rlProgress;
    @BindView(R.id.pb_load)
    ProgressBar pbLoad;
    @BindView(R.id.ll_progress_error)
    LinearLayout llError;
    @BindView(R.id.tv_progress_error)
    TextView tvError;

    private Activity mActivity;

    private Subscription mSubscription = Subscriptions.empty();

    private Unbinder mUnbinder;

    private GridLayoutManager mGridLayoutManager;

    private ImageAdapter mImageAdapter;
    private ArrayList<String> mImageList;

    public static ImageFragment newInstance() {
        ImageFragment imageFragment = new ImageFragment();
        return imageFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            mActivity = (Activity) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BaseActivity) {
            mActivity = activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_image_layout, container, false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initAdapters();
        if(savedInstanceState != null){
            mImageList = savedInstanceState.getStringArrayList(SAVE_IMAGE_LIST);
        } else{
            loadShotsRequest(false);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putStringArrayList(SAVE_IMAGE_LIST, mImageList);
        super.onSaveInstanceState(outState);
    }

    private void initAdapters() {
        if (mImageList == null) {
            mImageList = new ArrayList<>();
        }

        mImageAdapter = new ImageAdapter(mImageList);
        if(UIConfig.isTablet(mActivity)){
            mGridLayoutManager = new GridLayoutManager(mActivity, 3, LinearLayoutManager.VERTICAL, false);
        } else {
            mGridLayoutManager = new GridLayoutManager(mActivity, 2, LinearLayoutManager.VERTICAL, false);
        }
        mRvShotList.setLayoutManager(mGridLayoutManager);
        mRvShotList.setAdapter(mImageAdapter);
    }

    private void loadShotsRequest(boolean isRefresh){
        if(!isRefresh){
            startLoading();
        }
        mSubscription = RequestManager.getInstance().getImageList().subscribe(this::fillList, this::onError);
        addSubscription(mSubscription);
    }

    private void fillList(List<String> shotListResponse) {
        unsubscribe(mSubscription);
        completeLoading();
        if (shotListResponse != null) {
            clearList();
            mImageList.addAll(shotListResponse);
            mImageAdapter.notifyDataSetChanged();
            checkIsEmptyList(shotListResponse);
        }
    }

    private void checkIsEmptyList(List<String> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            mRvShotList.setVisibility(View.GONE);
            mLLEmptyShotList.setVisibility(View.VISIBLE);
        } else {
            mRvShotList.setVisibility(View.VISIBLE);
            mLLEmptyShotList.setVisibility(View.GONE);
        }
    }


    private void onError(Throwable throwable){
        unsubscribe(mSubscription);
        errorLoading(throwable);
    }


    private void startLoading() {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.GONE);
            rlProgress.setVisibility(View.VISIBLE);
            pbLoad.setVisibility(View.VISIBLE);
            llError.setVisibility(View.GONE);
        }
    }

    private void completeLoading() {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.VISIBLE);
            rlProgress.setVisibility(View.GONE);
            llError.setVisibility(View.GONE);
            pbLoad.setVisibility(View.GONE);
        }
    }

    private void errorLoading(Throwable throwable) {
        if (isAdded()) {
            mRlShotContent.setVisibility(View.GONE);
            rlProgress.setVisibility(View.VISIBLE);
            llError.setVisibility(View.VISIBLE);
            pbLoad.setVisibility(View.GONE);
            ErrorHandler errorHandler = new ErrorHandler(throwable);
            tvError.setText(errorHandler.getMessage());
        }
    }

    private void clearList() {
        mImageList.clear();
        mImageAdapter.notifyDataSetChanged();
    }

    private void reloadList(boolean isRefresh){
        loadShotsRequest(isRefresh);
    }

    @OnClick(R.id.ll_progress_error)
    void reload() {
        reloadList(false);
    }

    @Override
    public void onRefresh() {
        reloadList(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribeAll();
        mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
