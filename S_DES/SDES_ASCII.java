package S_DES;

public class SDES_ASCII {
	public static String transform(String Input_Text, String Key,int state) {
		SDES sdes = new SDES();
		Round_Func RF = new Round_Func();
		if(state==0) {
			String Cipher_Text = new String();
			for(int i=0;i<Input_Text.length();i++) {
				char ch = Input_Text.charAt(i);
				int ch_num = (int)ch;
				String Sub_Plain_Bits = Add_Zeros(Integer.toBinaryString(ch_num));
				String Sub_Chipher_Bits = sdes.S_DES(Sub_Plain_Bits,Key,state)[0];
				int[] Sub_Cipher_Array = RF.StringToArray(Sub_Chipher_Bits);
				Cipher_Text+=(char)Cauculate(Sub_Cipher_Array);
			}
			return Cipher_Text;
		}
		else {
			String Plain_Text = new String();
			for(int i=0;i<Input_Text.length();i++) {
				int ch_num = (int)Input_Text.charAt(i);
				String Sub_Cipher_Bits = Add_Zeros(Integer.toBinaryString(ch_num));
				String Sub_Plain_Bits = sdes.S_DES(Sub_Cipher_Bits,Key,state)[0];;
				ch_num = Cauculate(RF.StringToArray(Sub_Plain_Bits));
				Plain_Text += (char)ch_num;
			}
			return Plain_Text; 
		}
		
	}
	
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
	
	public static int Cauculate(int[] input) {
		int output = 0;
		for(int i=0;i<input.length;i++) {
			output+=input[i]*Math.pow(2, input.length-1-i);
		}
		return output;
	}

}
