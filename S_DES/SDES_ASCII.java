package S_DES;
public class SDES_ASCII {
	//ASCII串的加解密函数
	public static String transform(String Input_Text, String Key,int state) {
		SDES sdes = new SDES();
		Round_Func RF = new Round_Func();
		if(state==0) {//加密
			String Cipher_Text = new String();
			for(int i=0;i<Input_Text.length();i++) {
				//取出ASCII串中的第i个字符，转化为8bit二进制字符串
				char ch = Input_Text.charAt(i);
				int ch_num = (int)ch;
				String Sub_Plain_Bits = Add_Zeros(Integer.toBinaryString(ch_num));
				//对二进制子明文进行加密，得到子密文
				String Sub_Chipher_Bits = sdes.S_DES(Sub_Plain_Bits,Key,state)[0];
				//将二进制子密文转换为整数，并转换为字符合并到密文串中
				int[] Sub_Cipher_Array = RF.StringToArray(Sub_Chipher_Bits);
				Cipher_Text+=(char)Cauculate(Sub_Cipher_Array);
			}
			return Cipher_Text;
		}
		else {//解密
			String Plain_Text = new String();
			for(int i=0;i<Input_Text.length();i++) {
				//取出ASCII串中的第i个字符，转化为8bit二进制字符串
				int ch_num = (int)Input_Text.charAt(i);
				String Sub_Cipher_Bits = Add_Zeros(Integer.toBinaryString(ch_num));
				//对二进制子密文进行解密，得到子明文
				String Sub_Plain_Bits = sdes.S_DES(Sub_Cipher_Bits,Key,state)[0];;
				//将二进制子明文转换为整数，并转换为字符合并到明文串中
				ch_num = Cauculate(RF.StringToArray(Sub_Plain_Bits));
				Plain_Text += (char)ch_num;
			}
			return Plain_Text;
		}

	}
	//给二进制串补零至八位
	public static String Add_Zeros(String input) {
		while(true) {
			if(input.length()<8) {
				input='0'+input;
			}
			else {
				return input;
			}
		}
	}
	//计算二进制串对应的十进制整数值
	public static int Cauculate(int[] input) {
		int output = 0;
		for(int i=0;i<input.length;i++) {
			output+=input[i]*Math.pow(2, input.length-1-i);
		}
		return output;
	}

}
