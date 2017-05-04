package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.AddFriendResult;
import com.hxtao.qmd.hxtpay.utils.HXTUrl;
import com.hxtao.qmd.hxtpay.utils.ImageLoadUtil;
import com.hxtao.qmd.hxtpay.utils.ToastUtil;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Description:
 * @Author: Cyf on 2017/1/4.
 */

public class PartyDetilsNumAdapter extends BaseAdapter {
    private Context context;
    private List<List<String>> numList;
    private String isOrganizer;
    private String numberId;
    private String organizerId;
    private String p_id;
    private boolean isClick = false;
    private String strState;

    public PartyDetilsNumAdapter(Context context, List<List<String>> numList,String p_id) {
        this.context = context;
        this.numList = numList;
        this.p_id=p_id;
    }

    //获取是否是组织者的方法 用于显示踢人控件
    public void getIsOrganizer(String isOrganizer, String organizerId) {
        this.isOrganizer = isOrganizer;
        this.organizerId = organizerId;
    }

    @Override
    public int getCount() {
        return numList == null ? 0 : numList.size();
    }

    @Override
    public Object getItem(int position) {
        return numList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PartyDetilsViewHolder partyDetilsViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.itme_party_detials_num_layout, parent, false);
            partyDetilsViewHolder = new PartyDetilsViewHolder();

            partyDetilsViewHolder.iconImg = (CircleImageView) convertView.findViewById(R.id.icon_part_details_num_img);
            partyDetilsViewHolder.nameTv = (TextView) convertView.findViewById(R.id.name_part_details_num_tv);
            partyDetilsViewHolder.organizerTv = (TextView) convertView.findViewById(R.id.organizer_part_details_num_tv);
            partyDetilsViewHolder.moneyTv = (TextView) convertView.findViewById(R.id.monery_part_details_num_tv);
            partyDetilsViewHolder.outBtn = (Button) convertView.findViewById(R.id.out_part_details_num_btn);
            partyDetilsViewHolder.billStateBtn = (Button) convertView.findViewById(R.id.bill_state_part_details_num_btn);

            convertView.setTag(partyDetilsViewHolder);
        } else {
            partyDetilsViewHolder = (PartyDetilsViewHolder) convertView.getTag();
        }
        List<String> stringList = numList.get(position);

        switch (stringList.size()){
            case 3:
                for (int i = 0; i < stringList.size(); i++) {
                    switch (i) {
                        case 0:
                            numberId = stringList.get(i);
                            partyDetilsViewHolder.outBtn.setTag(numberId);
                            break;
                        case 1:
                            partyDetilsViewHolder.nameTv.setText(stringList.get(i));
                            break;
                        case 2:
                            ImageLoadUtil.disPlayImage(stringList.get(i),partyDetilsViewHolder.iconImg);
//                            Picasso.with(context).load(stringList.get(i)).into(partyDetilsViewHolder.iconImg);
                            break;
                    }
                }

                if (TextUtils.equals("1", isOrganizer)) {//判定是否为组织者
                    partyDetilsViewHolder.outBtn.setVisibility(View.VISIBLE);//是组织者显示踢人btn
                    if (TextUtils.equals(organizerId, numberId)) {//判定是否为组织者 在聚会成员列表中显示 组织者
                        partyDetilsViewHolder.organizerTv.setVisibility(View.VISIBLE);//显示组织者tv
                        partyDetilsViewHolder.outBtn.setVisibility(View.INVISIBLE);//隐藏 踢出btn
                    }else {
                        partyDetilsViewHolder.organizerTv.setVisibility(View.GONE);//隐藏组织者tv
                    }
                    partyDetilsViewHolder.outBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String numId= (String) partyDetilsViewHolder.outBtn.getTag();
//                    Log.e("numberCancel","numId:"+ numId);
                            //向服务端提出踢人请求
                            numberCancel(numId,partyDetilsViewHolder);
//                    partyDetilsViewHolder.outBtn.setText("已踢出");

                        }
                    });
                }
                break;
            case 6:
                for (int i = 0; i < stringList.size(); i++) {
                    switch (i) {
                        case 0:
                            numberId = stringList.get(i);
                            partyDetilsViewHolder.outBtn.setTag(numberId);
                            break;
                        case 1:
                            partyDetilsViewHolder.nameTv.setText(stringList.get(i));
                            break;
                        case 2:
                            ImageLoadUtil.disPlayImage(stringList.get(i),partyDetilsViewHolder.iconImg);
//                            Picasso.with(context).load(stringList.get(i)).into(partyDetilsViewHolder.iconImg);
                            break;
                        case 4 :
                            partyDetilsViewHolder.moneyTv.setText(stringList.get(i));
                            break;
                        case 5:
                            strState = stringList.get(i);
                            break;
                    }
                }


                if (TextUtils.equals("1",isOrganizer)){
                    partyDetilsViewHolder.organizerTv.setVisibility(View.GONE);
                    partyDetilsViewHolder.outBtn.setVisibility(View.INVISIBLE);
                    partyDetilsViewHolder.billStateBtn.setVisibility(View.VISIBLE);
                    partyDetilsViewHolder.moneyTv.setVisibility(View.VISIBLE);
                    if (TextUtils.equals("1",strState)){
                        partyDetilsViewHolder.billStateBtn.setText("未付款");
                    }else {
                        partyDetilsViewHolder.billStateBtn.setText("已付款");
                    }
                }else {
                    partyDetilsViewHolder.billStateBtn.setVisibility(View.VISIBLE);
                    partyDetilsViewHolder.moneyTv.setVisibility(View.VISIBLE);
                    if (TextUtils.equals("1",strState)){
                        partyDetilsViewHolder.billStateBtn.setText("未付款");
                    }else {
                        partyDetilsViewHolder.billStateBtn.setText("已付款");
                    }
                }




                break;
        }




        return convertView;
    }

    static class PartyDetilsViewHolder {
        public CircleImageView iconImg;
        public TextView nameTv, organizerTv,moneyTv;
        public Button outBtn,billStateBtn;
    }

    private void numberCancel(String numId, final PartyDetilsViewHolder partyDetilsViewHolder) {
//        Log.e("numberCancel","id:"+ numId);
        RequestParams requestParams = new RequestParams(HXTUrl.HXTHTTP_CANCELMEMBERPARTYQUALIFICATION);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id",p_id);
        requestParams.addBodyParameter("b_mid", numId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("numberCancel", result);
                if(result!=null){
                    Gson gson=new Gson();
                    AddFriendResult addFriendResult = gson.fromJson(result, AddFriendResult.class);
                    switch (addFriendResult.getStatus()){
                        case 1:
                            ToastUtil.createToastConfig().ToastShow(context, addFriendResult.getInfo(), 15000);
                            partyDetilsViewHolder.outBtn.setText("已踢出");
                            partyDetilsViewHolder.outBtn.setEnabled(false);
                            break;
                        case 0:
                            ToastUtil.createToastConfig().ToastShow(context, addFriendResult.getInfo(), 15000);
                            break;
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ToastUtil.createToastConfig().ToastShow(context, "网络异常请检查网络", 15000);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
