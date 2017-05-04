package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.base.BaseActivity;
import com.hxtao.qmd.hxtpay.fragment.FriendFragment;
import com.hxtao.qmd.hxtpay.fragment.HomeFragment;
import com.hxtao.qmd.hxtpay.fragment.PartyFragment;
import com.hxtao.qmd.hxtpay.fragment.PersonFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fillfragment_ll_main) LinearLayout fillfragmentLlMain;
    @BindView(R.id.radiogroup_main) RadioGroup radiogroupMain;
    private List<Fragment> fragmentList;
    public int current=0;
    private HomeFragment homeFragment;
    private PartyFragment partyFragment;
    private FriendFragment friendFragment;
    private PersonFragment personFragment;

    @Override
    public int layoutContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        radiogroupMain.check(R.id.rb_home_mian);
        //初始化
        initFragment();
    }

    @Override
    public void initListener() {

        radiogroupMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home_mian:
                        changeFragment(0);
                        break;
                    case R.id.rb_paty_mian:
                        changeFragment(1);
                        break;
                    case R.id.rb_friend_mian:
                        changeFragment(2);
                        break;
                    case R.id.rb_person_mian:
                        changeFragment(3);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        partyFragment = new PartyFragment();
        friendFragment = new FriendFragment();
        personFragment = new PersonFragment();

        fragmentList.add(homeFragment);
        fragmentList.add(partyFragment);
        fragmentList.add(friendFragment);
        fragmentList.add(personFragment);

        getSupportFragmentManager().beginTransaction().add(R.id.fillfragment_ll_main, homeFragment).commit();
    }

    private void changeFragment(int postion){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment targetfragment = fragmentList.get(postion);
        Fragment currentfragment = fragmentList.get(current);

        if (targetfragment.isAdded()){
            fragmentTransaction.show(targetfragment).hide(currentfragment).commit();
        }else {
            fragmentTransaction.add(R.id.fillfragment_ll_main,targetfragment).hide(currentfragment).commit();
        }

        current=postion;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        homeFragment.onActivityResult(requestCode,resultCode,data);
    }
}
