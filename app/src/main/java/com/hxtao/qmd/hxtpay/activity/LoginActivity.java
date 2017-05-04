package com.hxtao.qmd.hxtpay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.LoginBeen;
import com.hxtao.qmd.hxtpay.utils.ConstantUtils;
import com.hxtao.qmd.hxtpay.utils.FileService;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.VersionUtils;
import com.hxtao.qmd.hxtpay.widgets.ChooseText;
import com.hxtao.qmd.hxtpay.widgets.DropEditText;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.login_btn) Button loginBtn;
    @BindView(R.id.register_btn) Button registerBtn;
    @BindView(R.id.forgetpwd_btn) Button forgetpwdBtn;
    @BindView(R.id.name_ed_login_act) DropEditText nameEdLoginAct;
    @BindView(R.id.pwd_ed_login_act) EditText pwdEdLoginAct;
    @BindView(R.id.show_img_login_act) ImageView showImgLoginAct;
    private String userName;
    private String userPwd;
    private Handler handler;
    private LoginBeen loginBeen;


    private FileService fileService =new FileService(this);

    private String TXTNAME="hxtUser.txt";

    private ArrayList<String> nameList;

    private boolean showPwd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //设置输入类型只能为数字
        nameEdLoginAct.setInputType();
        nameEdLoginAct.setHint("手机号/抢买单帐号");


        final Map<String, String> userMap = fileService.readFile(TXTNAME);
        if (userMap!=null&&userMap.size()>0){
            nameList = new ArrayList<>();
            for (Map.Entry<String, String> entry : userMap.entrySet()) {
//                Log.e("userMap", "key"+entry.getKey()+" ---value:"+entry.getValue());
                nameList.add(entry.getKey());
            }
            nameEdLoginAct.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList));
            nameEdLoginAct.setText(nameList.get(0));
            pwdEdLoginAct.setText(userMap.get(nameList.get(0)));
        }

        nameEdLoginAct.getPosition(new ChooseText() {
            @Override
            public void getChoosePostion(int postin) {
//                Log.e("getPosition",""+postin);
                pwdEdLoginAct.setText(userMap.get(nameList.get(postin)));
//                Log.e("getPosition",userMap.get(nameList.get(postin)));
            }
        });

        showImgLoginAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!showPwd) {
                    pwdEdLoginAct.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPwd = !showPwd;
                } else {
                    pwdEdLoginAct.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPwd = !showPwd;
                }
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 100:
                        if (msg.obj != null) {
                            loginBeen = (LoginBeen) msg.obj;
                            switch (loginBeen.getStatus()) {
                                case 0:
                                    Toast.makeText(LoginActivity.this, loginBeen.getInfo(), Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    LoginBeen.DataBean data = loginBeen.getData();
                                    BaseApplication.createApplication().setId(data.getId());
                                    BaseApplication.createApplication().setSign(data.getSign());
                                    BaseApplication.createApplication().setAccounts(data.getAccount());
                                    BaseApplication.createApplication().setUsername((String) data.getUsername());
                                    BaseApplication.createApplication().setIcon((String) data.getIcon());
                                    BaseApplication.createApplication().setMark(data.getMark());
                                    BaseApplication.createApplication().setMoney(data.getMoney());
                                    BaseApplication.createApplication().setLast_login_time(data.getLast_login_time());
                                    BaseApplication.createApplication().setMonetary_month(data.getMonetary_month());
                                    BaseApplication.createApplication().setParty_times_month(data.getParty_times_month());
                                    Intent intent2 = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent2);
                                    fileService.saveToRom(userName,userPwd,TXTNAME);
                                    break;
                                case 2:
                                    LoginBeen.DataBean data2 = loginBeen.getData();
                                    BaseApplication.createApplication().setSign(data2.getSign());
                                    Intent intent = new Intent(LoginActivity.this, AddInforActivity.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                        break;
                }
            }
        };
        //控件监听
        setWidgetOnClick();
    }

    //获取登录信息
    private Map<String, String> getLoginInformation() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        String date = formatter.format(new Date());// 获取当前时间
        String ver = VersionUtils.getVersionName(this);
        int versionCode = VersionUtils.getVersionCode(this);
        String hxtClient = ConstantUtils.CLIENT;

        Map<String, String> map = new HashMap();
        map.put("ver", ver);
        map.put("hxtClient", hxtClient);
        map.put("time", date);
        return map;
    }


    /**
     * @param
     * @return
     * @description:控件的点击监听
     */
    private void setWidgetOnClick() {
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        forgetpwdBtn.setOnClickListener(this);
    }

    /**
     * @param
     * @return
     * @description:获取用户输入信息
     */
    private void getViewData() {
        userName =nameEdLoginAct.getText();
        userPwd = pwdEdLoginAct.getText().toString().trim();
    }

    @Override
    public void onClick(View v) {
        //获取用户输入信息
        getViewData();

        switch (v.getId()) {
            case R.id.login_btn://登录
                dataProcessor(userName, userPwd);
                break;
            case R.id.register_btn://注册
                //跳转注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forgetpwd_btn://忘记密码
                Intent forgetintent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(forgetintent);
                break;
        }
    }

    /**
     * @param
     * @return
     * @description:用户信息处理以及完成登录
     */
    public void dataProcessor(final String name, String pwd) {
        if (TextUtils.isEmpty(name)) {//判断用户名是否为空
            nameEdLoginAct.setError("用户名不能为空");
        }
        if (TextUtils.isEmpty(pwd)) {//判断密码是否为空
            pwdEdLoginAct.setError("密码不能为空");
        }
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {//用户名和密码不为空时
            RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_LOGIN);
            requestParams.addBodyParameter("account", name);
            requestParams.addBodyParameter("password", pwd);
            x.http().post(requestParams, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
//                    Log.e("login",result);
                    if (result != null) {
                        Gson gson = new Gson();
                        LoginBeen login = gson.fromJson(result, LoginBeen.class);

                        if (login != null) {
                            Message message = handler.obtainMessage();
                            message.what = 100;
                            message.obj = login;
                            message.sendToTarget();
                        }
                    }
//                    Log.e("onSuccess:","onSuccess");
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
//                    Log.e("onError:", "网络连接异常" + ex.toString());
                    Toast.makeText(LoginActivity.this, "网络连接异常,请检查网络", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(CancelledException cex) {
//                    Log.e("onCancelled:", "onCancelled");
                }

                @Override
                public void onFinished() {
//                    Log.e("onFinished:","onFinished");
                }
            });
        }
    }


}
