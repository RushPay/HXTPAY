package com.hxtao.qmd.hxtpay.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hxtao.qmd.hxtpay.R;
import com.hxtao.qmd.hxtpay.app.BaseApplication;
import com.hxtao.qmd.hxtpay.been.PartyInfoBeen;
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
 * @Author: Cyf on 2016/12/30.
 */

public class PartyDetilsAdapter extends BaseAdapter {
    private Context context;
    private List<PartyInfoBeen> partDataList;

    public PartyDetilsAdapter(Context context, List<PartyInfoBeen> partDataList) {
        this.context = context;
        this.partDataList = partDataList;
    }

    @Override
    public int getCount() {
        return partDataList==null?0:partDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return partDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MeetDetilsViewHolder meetDetilsViewHolder ;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.itme_second_meet_layout,parent,false);
            meetDetilsViewHolder=new MeetDetilsViewHolder();
            meetDetilsViewHolder.meetIcon= (CircleImageView) convertView.findViewById(R.id.itme_second_meet_icon_img);
            meetDetilsViewHolder.meetTheme= (TextView) convertView.findViewById(R.id.itme_second_meet_theme_tv);
            meetDetilsViewHolder.meetAddress= (TextView) convertView.findViewById(R.id.itme_second_meet_address_tv);
            meetDetilsViewHolder.acceptButton= (Button) convertView.findViewById(R.id.itme_second_meet_accept_btn);
            meetDetilsViewHolder.denyButton= (Button) convertView.findViewById(R.id.itme_second_meet_deny_btn);
            meetDetilsViewHolder.meetState= (TextView) convertView.findViewById(R.id.itme_second_meet_state_tv);
            meetDetilsViewHolder.numberLayout= (LinearLayout) convertView.findViewById(R.id.itme_second_meet_member_ll);
            convertView.setTag(meetDetilsViewHolder);
        }else {
            meetDetilsViewHolder= (MeetDetilsViewHolder) convertView.getTag();
        }
        PartyInfoBeen partyInfoBeen = partDataList.get(position);
        String title = partyInfoBeen.getTitle();
        String address = partyInfoBeen.getAddress();
        String p_status = partyInfoBeen.getP_status();
        final String id = partyInfoBeen.getId();
        String icon = partyInfoBeen.icon;
        String invite_status = partyInfoBeen.getInvite_status();
        List<List<String>> numberList = partyInfoBeen.getNumberList();
        int meetStat=Integer.valueOf(p_status);
        if (icon!=null){
            ImageLoadUtil.disPlayImage(icon,meetDetilsViewHolder.meetIcon);
        }
        if (!TextUtils.isEmpty(title)){
            meetDetilsViewHolder.meetTheme.setText(title);
        }
        if (!TextUtils.isEmpty(address)){
            meetDetilsViewHolder.meetAddress.setText(address);
        }
        switch (meetStat){//最新判断聚会状态 1聚会中 2生产订单 3结束 4取消    判断聚会状态 1聚会中 2结束 3取消
            case 1:

                if (TextUtils.equals("0",invite_status)){
                    meetDetilsViewHolder.acceptButton.setVisibility(View.VISIBLE);
                    meetDetilsViewHolder.denyButton.setVisibility(View.VISIBLE);

                    //接受聚会的监听
                    meetDetilsViewHolder.acceptButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dealRequest("1",id,meetDetilsViewHolder);
                            ToastUtil.createToastConfig().ToastShow(context,"接受",15000);

                        }
                    });
                    //拒绝聚会的监听
                    meetDetilsViewHolder.denyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dealRequest("2",id,meetDetilsViewHolder);
                            ToastUtil.createToastConfig().ToastShow(context,"取消",15000);

                        }
                    });

                }else {
                    meetDetilsViewHolder.meetState.setVisibility(View.VISIBLE);
                    meetDetilsViewHolder.meetState.setText("聚会中...");
                }

                break;
            case 2:
                meetDetilsViewHolder.meetState.setVisibility(View.VISIBLE);
                meetDetilsViewHolder.meetState.setText("生产订单");
                break;
            case 3:
                meetDetilsViewHolder.meetState.setVisibility(View.VISIBLE);
                meetDetilsViewHolder.meetState.setText("结束");
                break;
            case 4:
                meetDetilsViewHolder.meetState.setVisibility(View.VISIBLE);
                meetDetilsViewHolder.meetState.setText("取消");
                break;
        }

        //显示成员头像
//        if (numberList!=null&&numberList.size()>0){
//            for (int i = 0; i <numberList.size() ; i++) {
////                Log.e("numberList.size()","numbersunm"+numberList.size());
//                List<String> list = numberList.get(i);
//                meetDetilsViewHolder.numberLayout.setVisibility(View.VISIBLE);
////            for (int i = 0; i <list.size() ; i++) {
//                String s = list.get(2);
////                Log.e("list",s);
//                ImageView numberIcon=new ImageView(context);
//                Picasso.with(context).load(icon).into(numberIcon);
//                meetDetilsViewHolder.numberLayout.addView(numberIcon,50,50);
////            }
//            }
//        }

        return convertView;
    }
    static class MeetDetilsViewHolder{
        public CircleImageView meetIcon;
        public TextView meetTheme;
        public TextView meetAddress;
        public TextView meetState;
        public Button acceptButton;
        public Button denyButton;

        public LinearLayout numberLayout;
    }


    public void dealRequest(String dealStr, String p_id, final MeetDetilsViewHolder meetDetilsViewHolder){
        RequestParams requestParams=new RequestParams(HXTUrl.HXTHTTP_DEALPARTYINVITE);
        requestParams.addBodyParameter("sign", BaseApplication.createApplication().getSign());
        requestParams.addBodyParameter("p_id",p_id);
        requestParams.addBodyParameter("op",dealStr);
//        Log.e("dealRequest","p_id+"+p_id+",op :"+ dealStr);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Log.e("dealRequest",result);
                meetDetilsViewHolder.acceptButton.setEnabled(false);
                meetDetilsViewHolder.denyButton.setEnabled(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Log.e("dealRequest_onError",ex.toString());
                ToastUtil.createToastConfig().ToastShow(context,"网络异常请检查网络",15000);
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
