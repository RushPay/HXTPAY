package com.hxtao.qmd.hxtpay.utils;

import java.util.regex.Pattern;

/**
 * ���򹤾��� �ṩ��֤���䡢�ֻ��š��绰���롢���֤���롢���ֵȷ���
 */
public final class RegexUtils {

	/**
	 * ��֤Email
	 * 
	 * @param email
	 *            email��ַ����ʽ��zhangsan@sina.com��zhangsan@xxx.com.cn��xxx�����ʼ�������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkEmail(String email) {
		// String regex =
		// "/^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/";
		String regex = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))$";
		return Pattern.matches(regex, email);
	}

	/**
	 * ��֤���֤����
	 * 
	 * @param idCard
	 *            �������֤����15λ��18λ�����һλ���������ֻ���ĸ
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * ��֤�ֻ����루֧�ֹ��ʸ�ʽ��+86135xxxx...���й��ڵأ���+00852137xxxx...���й���ۣ���
	 * 
	 * @param mobile
	 *            �ƶ�����ͨ��������Ӫ�̵ĺ����
	 *            <p>
	 *            �ƶ��ĺŶΣ�134(0-8)��135��136��137��138��139��147��Ԥ������TD��������
	 *            ��150��151��152��157��TDר�ã���158��159��187��δ���ã���188��TDר�ã�
	 *            </p>
	 *            <p>
	 *            ��ͨ�ĺŶΣ�130��131��132��155��156�������ר�ã���185��δ���ã���186��3g��
	 *            </p>
	 *            <p>
	 *            ���ŵĺŶΣ�133��153��180��δ���ã���189
	 *            </p>
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "^(\\+\\d{2,5})?1[34578]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}
	//0.001-10000000��Χ�ڵ����֣�С��λ���3λ
	public static boolean checkBuyNum(String str) {
		String regex = "^(?=([0-9]{1,10}$|[0-9]{1,7}\\.))(0|[1-9][0-9]*)(\\.[0-9]{1,3})?$";
		return Pattern.matches(regex, str);
	}
	
	/**
	 * �жϵ绰��������ֻ��͹̶��绰
	 * @param str
	 * @return
	 */
	public static boolean checkFixedTelephone(String str){
		String regex="^((\\+\\d{2,5})?1[34578]\\d{9})|((0[0-9]{2,3}(\\-)?)?([2-9][0-9]{6,7})(\\-[0-9]{1,4})?)$";
		boolean flag=false;
		if(str.length()>0){
			if(str.contains(",")||str.contains("��")){//���С�����ʱ�п���Ϊ����绰
				String strNew=str.replace("��", ",");//������״̬�µġ������滻ΪӢ��״̬�µĶ���
				String[] telephones=strNew.split(",");
				for(String num:telephones){
					if(Pattern.matches(regex, num)){
						flag=true;
					}else{
						flag=false;
						break;
					}
				}
			}else{//����Ϊһ���绰����
				if(Pattern.matches(regex, str)){
					flag=true;
				}
			}
			
		}
		return flag;
	}
	
	/**
	 * ��֤����ֻ���̶��绰����
	 * @param telephone
	 * @return
	 */
	public static boolean checkTelephoneNumber(String telephone){
		String regex = "^((\\+\\d+)?1[34578]\\d{9}[,��]?|((0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?[,��]?))+[;��]?$";
		return Pattern.matches(regex, telephone);
	}
	/**
	 * ��֤�̶��绰����
	 * 
	 * @param phone
	 *            �绰���룬��ʽ�����ң��������绰���� + ���ţ����д��룩 + �绰���룬�磺+8602085588447
	 *            <p>
	 *            <b>���ң������� ���� ��</b>��ʶ�绰����Ĺ��ң��������ı�׼���ң����������롣�������� 0 �� 9
	 *            ��һλ���λ���֣� ����֮���ǿո�ָ��Ĺ��ң����������롣
	 *            </p>
	 *            <p>
	 *            <b>���ţ����д��룩��</b>����ܰ���һ�������� 0 �� 9 �����֣���������д������Բ���š���
	 *            �Բ�ʹ�õ�������д���Ĺ��ң�����������ʡ�Ը������
	 *            </p>
	 *            <p>
	 *            <b>�绰���룺</b>������� 0 �� 9 ��һ����������
	 *            </p>
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 *         (\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$�����֤���ֵ��ԡ�+����ͷ�Ļ�����������ַ����Ȳ���������
	 * 
	 *         ^(0([0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?\\,?)+[;]$
	 *         ��֤��"����-ֱ������-�ֻ���"��"ֱ������"��"ֱ������-�ֻ���","����-ֱ������"��ʽ�Ĺ̻���ʽ�������źͷֻ��ſɲ���
	 *         �����������дʱ��","�������������ʱ�á�;����β(����һ���绰�����绰��ʹ�á�;����β)
	 *         
	 */
	public static boolean checkPhone(String phone) {
		// String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		String regex = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$";
		boolean flag=false;
		if(phone.length()>0){
			if(phone.contains(",")||phone.contains("��")){//���С�����ʱ�п���Ϊ����绰
				String strNew=phone.replace("��", ",");//������״̬�µġ������滻ΪӢ��״̬�µĶ���
				String[] telephones=strNew.split(",");
				for(String num:telephones){
					if(Pattern.matches(regex, num)){
						flag=true;
					}else{
						flag=false;
						break;
					}
				}
			}else{//����Ϊһ���绰����
				if(Pattern.matches(regex, phone)){
					flag=true;
				}
			}
			
		}
		return flag;
		
		
		
		
		
		
	}

	/**
	 * ��֤�������������͸�������
	 * 
	 * @param digit
	 *            һλ���λ0-9֮�������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * ��֤�����͸�����������������������������
	 * 
	 * @param decimals
	 *            һλ���λ0-9֮��ĸ��������磺1.23��233.30
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * ��֤�հ��ַ�
	 * 
	 * @param blankSpace
	 *            �հ��ַ����������ո�\t��\n��\r��\f��\x0B
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * ��֤QQ����
	 */
	public static boolean checkQQ(String qq) {
		String regex = "^[1-9][0-9]{3,9}$";
		return Pattern.matches(regex, qq);
	}

	/**
	 * ��֤����
	 * 
	 * @param chinese
	 *            �����ַ�
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * ��֤���ڣ������գ�
	 * 
	 * @param birthday
	 *            ���ڣ���ʽ��1992-09-03����1992.09.03
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * ��֤URL��ַ
	 * 
	 * @param url
	 *            ��ʽ��http://blog.csdn.net:80/xyang81/article/details/7705960? ��
	 *            http://www.csdn.net:80
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * ƥ���й���������
	 * 
	 * @param postcode
	 *            ��������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * ƥ��IP��ַ(��ƥ�䣬��ʽ���磺192.168.1.1��127.0.0.1��û��ƥ��IP�εĴ�С)
	 * 
	 * @param ipAddress
	 *            IPv4��׼��ַ
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * ��֤���� ��֤��ʽΪ��Сд��ĸ+����,��ĸ�����ֱ���ͬʱ���ڳ�����СΪ6λ������6λ�� ��ĸ���������Ⱥ�˳��Ҫ��
	 * 
	 * @param password
	 * @return
	 * ��ĸ�����֣����룩+�����ַ������ޣ�
	 * ^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*\S)[A-Za-z0-9\S]{6,}$
	 */
	public static boolean checkPassword(String password) {
//		String regex = "^(?=.*?[a-zA-Z])(?=.*?[0-6])[A-Za-z0-9]{6,}$";
		String regex = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*\\S)[A-Za-z0-9\\S]{6,}$";
		return Pattern.matches(regex, password);
	}
	/*
	 * ��֤���������
	 */
	
	public static boolean isAllNumber(String str){
		String reg = "^\\d+$";
		return Pattern.matches(reg, str);
	}
	/*
	 * ��֤���������
	 */
	
	public static boolean isPhone(String str){
		String reg = "^\\d+$";
		if(Pattern.matches(reg, str)){
			if(str.trim().length()==11){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	//��������С����^[0-9]+\.{0,1}[0-9]{0,2}$
	public static boolean isZhengShuAndXiaoShu(String str){
		String reg = "^[0-9]+\\.{0,1}[0-9]{0,2}$";
		if(Pattern.matches(reg, str)){
			if(str.trim().length()==11){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	//ֻ���������֣�"^[0-9]*$"
	public static boolean isNumber(String str){
		String reg = "^[0-9]*$";
		if(Pattern.matches(reg, str)){
			if(str.trim().length()==11){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}