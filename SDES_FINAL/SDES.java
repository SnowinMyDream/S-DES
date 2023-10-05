package SDES_FINAL;

public class SDES {
	//state=0表示加密，state=1表示解密
	public static String[] S_DES(String Plain_Text,String Key,int state) {
		Round_Func RF = new Round_Func();
		IPAndRKeys IPK = new IPAndRKeys();
		//初始IP置换
		Plain_Text = IPK.IP(Plain_Text);
		//轮密钥生成
		String[] Keys = IPK.Key(Key);
		int[] PT = RF.StringToArray(Plain_Text);
		int[] key1 = RF.StringToArray(Keys[0]);
		int[] key2 = RF.StringToArray(Keys[1]);
		//密文或明文生成
		int[] Cipher_Array = new int[8];
		if(state==0) {
			Cipher_Array = RF.Round_Function(RF.Swap(RF.Round_Function(PT,key1)),key2);
		}
		else {
			Cipher_Array = RF.Round_Function(RF.Swap(RF.Round_Function(PT,key2)),key1);
		}
		String Cipher_Text = new String();
		for(int i=0;i<Cipher_Array.length;i++) {
			Cipher_Text += Cipher_Array[i];
		}
		//最终IP置换
		Cipher_Text = IPK.IP_inverse(Cipher_Text);
		//输出
		String[] output = new String[]{Cipher_Text,Key};
		return output;
	}

}
