package com.hxtao.qmd.hxtpay.utils;

/**
 * @Description:
 * @Author: Cyf on 2016/12/19.
 */

public class HXTUrl {
    public static String HXTHTTP = "http://qmd.hxtao.net/Api/";

    //    public static String HXTHTTP = "http://172.16.0.64/api/";
    //注册接口
    //http://192.168.199.137/api/member/register?
    //http://192.168.199.137/api/member/register?role=1&sign=0c9f3de79f713209d57dcf4a00be3618&account=18510871164&password=123456
    public static String HXTHTTP_REHSITER = HXTHTTP + "member/register?";

    //登录接口
    //http://192.168.199.137/api/member/login?
    //http://172.16.0.64/api/member/login?account=18510871164&password=123456
    public static String HXTHTTP_LOGIN = HXTHTTP + "member/login?";

    //添加用户信息接口
    //http://192.168.199.137/api/member/perfectInformation?username=曹操&trade_password=123456&icon=;
    public static String HXTHTTP_INFO = HXTHTTP + "member/perfectInformation?";

    //获取好友列表信息
    // http://192.168.199.137/api/member/getFriendList?sign=7ff13fffb954630662777173faa7ff8f
    public static String HXTHTTP_FRIENDLIST = HXTHTTP + "member/getFriendList?";

    //获取首页banner轮播图
    //http://172.16.0.64/api/member/getBanner?sign=7ff13fffb954630662777173faa7ff8f
    public static String HXTHTTP_GETBANNER = HXTHTTP + "member/getBanner?";

    //搜索用户
    //http://www.qmd.com/api/member/findMember?sign= &mobile=
    public static String HXTHTTP_QUERYUSER = HXTHTTP + "member/findMember?";

    //确认添加搜索到的好友
    //http://www.qmd.com/api/member/sendAddFriendMessage
    public static String HXTHTTP_ADDFRIEND = HXTHTTP + "member/sendAddFriendMessage?";

    //获取添加我的用户列表
    //http://www.qmd.com/api/member/getAddFriendMessageList
    public static String HXTHTTP_ADDMESSAGE = HXTHTTP + "member/getAddFriendMessageList?";

    //处理好友添加消息处理
    //http://www.qmd.com/api/member/dealAddFriendMessage
    public static String HXTHTTP_DEALMESSAGE = HXTHTTP + "member/dealAddFriendMessage?";

    //获取全部聚会列表
    //  http://192.168.199.137/api/party/getpartylist?sign=7ff13fffb954630662777173faa7ff8f
    public static String HXTHTTP_ALLPARTLIST = HXTHTTP + "party/getpartylist?";


    //发起聚会的请求
    //http://www.qmd.com/api/party/launchParty?    title 聚会标题 detail  详细介绍address  地址

    public static String HXTHTTP_LAUNCHPARTY = HXTHTTP + "party/launchParty?";

    //邀请好友的请求
    //http://www.qmd.com/api/party/sendPartyInvitation id_str    被邀请人的id，逗号隔开 p_id  聚会的id
    public static String HXTHTTP_INVITE = HXTHTTP + "party/sendPartyInvitation?";

    //处理聚会邀请
    //http://www.qmd.com/api/party/dealPartyInvitation
    public static String HXTHTTP_DEALPARTYINVITE = HXTHTTP + "party/dealPartyInvitation?";

    //获取聚会详情
    //http://www.qmd.com/api/party/getPartyInfo
    //http://192.168.199.137/api/party/getPartyInfo?sign=7ff13fffb954630662777173faa7ff8f&p_id=76
    public static String HXTHTTP_PARTYINFO = HXTHTTP + "party/getPartyInfo?";

    //取消聚会
    //http://www.qmd.com/api/party/cancelParty
    public static String HXTHTTP_CANCELPARTY = HXTHTTP + "party/cancelParty?";

    //踢出聚会成员
    //http://www.qmd.com/api/party/cancelMemberPartyQualification
    public static String HXTHTTP_CANCELMEMBERPARTYQUALIFICATION = HXTHTTP + "party/cancelMemberPartyQualification?";

    //获取转让聚会的聚会内的成员信息
    //http://www.qmd.com/api/party/getPartyMemberList?sign=7ff13fffb954630662777173faa7ff8f&p_id=42
    public static String HXTHTTP_PARTYMEMBERLIST = HXTHTTP + "party/getPartyMemberList?";

    //聚会转让到聚会成员的个人
    //http://www.qmd.com/api/party/transferOrganizer
    public static String HXTHTTP_TRANSFERORGANIZER = HXTHTTP + "party/transferOrganizer?";

    //反馈付款方式 消费金额接口
    //http://www.qmd.com/api/party/savePartyInfo
    public static String HXTHTTP_SAVEPARTYINFO = HXTHTTP + "party/savePartyInfo?";

    //支付接口
    //http://www.qmd.com/api/party/pay
    public static String HXTHTTP_PAY = HXTHTTP + "party/pay?";

    //生成幸运人
    //http://www.qmd.com/api/party/luckyMan
    public static String HXTHTTP_LUCKYMAN = HXTHTTP + "party/luckyMan?";

    //反馈充值金额
    //http://192.168.199.137/api/Alipay/gndSign?
    public static String HXTHTTP_ALIPAY = HXTHTTP + "Alipay/getOrderAndSign?";

    //转账
    //http://192.168.199.137/api/Transfer/index
    public static String HXTHTTP_TRANSFER = HXTHTTP + "Transfer/index?";

    //个人界面的信息
    //api/member/getmemberinfo
    public static String HXTHTTP_MEMBERINFO = HXTHTTP + "member/getmemberinfo?";

    //个人界面的账单详情
    //api/member/getAccountInfoList
    public static String HXTHTTP_ACCOUNTINFOLIST = HXTHTTP + "member/getAccountInfoList?";
    //HXTHTTP_

    //个人二维码
    //http://172.16.0.64/api/member/getOwnQRcode?sign=7ff13fffb954630662777173faa7ff8f
    //http://www.qmd.com/api/member/getOwnQRcode?sign=7ff13fffb954630662777173faa7ff8f
    public static String HXTHTTP_GETOWNQRCODE = HXTHTTP + "member/getOwnQRcode?sign=";

    //转账二维码
    //getOwnMoneyQRcode
    public static String HXTHTTP_GETOWNMONEYQRCODE = HXTHTTP + "member/getOwnMoneyQRcode?sign=";

    //聚会二维码:
    //"http://qmd.hxtao.net/api/"
    //http://qmd.hxtao.net/api/Party/getPartyQRcode
    public static String HXTHTTP_GETPARTYQRCODE = HXTHTTP + "Party/getPartyQRcode?sign=";

    //发送短信接
    // http://www.qmd.com/api/member/sendMessage   post提交参数mobile （手机号）
    public static String HXTHTTP_SENDMESSAGE = HXTHTTP + "member/sendMessage?";

    //注册获取验证码   sendMessageRegister
    //http://www.qmd.com/api/member/sendMessageRegister
    public static String HXTHTTP_SENDMESSAGEREGISTER = HXTHTTP + "member/sendMessageRegister?";

    //重置密码接
    //http://www.qmd.com/api/member/resetPassword  post提交参数   mobile（手机号）
    //     password（密码）   messageCode（短信验证码）
    public static String HXTHTTP_RESETPASSWORD = HXTHTTP + "member/resetPassword?";

    //删除好友
    //www.qmd.com/api/member/delFriend   提交参数  sign（签名）  fid（要删除的好友id）  post提交
    public static String HXTHTTP_DELFRIEND = HXTHTTP + "member/delFriend?";


    //修改名字  editUserName   参数sign 和  username
    //    http://www.qmd.com/api/member/editUserName
    public static String HXTHTTP_EDITUSERNAME = HXTHTTP + "member/editUserName?";
    //    修改头像   editIcon            参数sign和图像文件
    //http://www.qmd.com/api/member/editIcon
    public static String HXTHTTP_EDITICON = HXTHTTP + "member/editIcon?";
    //    修改支付密码 editPayPwd    参数sign和trade_password
    //http://www.qmd.com/api/member/editPayPwd
    public static String HXTHTTP_EDITPAYPWD = HXTHTTP + "member/editPayPwd?";

    //退出程序
    ///Api/member/loginOut    参数 sign 签名
    public static String HXTHTTP_LOGINOUT = HXTHTTP + "member/loginOut?";


    //http://www.qmd.com/Api/Member/getAddFriendMessageCount
    public static String HXTHTTP_ADDFRIENDMESSAGECOUNT= HXTHTTP + "member/getAddFriendMessageCount?";

    //提现
    //http://www.qmd.com/Api/Member/withdraw
    public static String HXTHTTP_WITHDRAW=HXTHTTP + "member/withdraw?";

    //微信支付
    //http://qmd.hxtao.net/Api/Weixin/getOrderAndSign
    public static String HXTHTTP_WEIXIN=HXTHTTP+"weixin/getOrderAndSign?";

}
