package S_DES;
public class Crack {
	//初始化参数
	static int[] crack_bits = {0,0,0,0,0,0,0,0,0,0};
	static boolean Key_Found = false;//判断是否找到正确的密钥
	//最终输出的密钥
	static String key = new String();
	//破解函数
	public static String[] crack(String Plain_Text,String Cipher_Text) {
		long start_time = System.currentTimeMillis();
		SDES sdes = new SDES();
		Recursion(crack_bits,0,Plain_Text,Cipher_Text,sdes);
		String[] output = new String[2];
		output[0]=key;
		start_time=System.currentTimeMillis()-start_time;
		output[1]=Long.toString(start_time);
		return output;
	}
	//递归遍历所有可能的密钥
	public static void Recursion(int [] input,int index,String Plain_Text,String Cipher_Text,SDES sdes) {
		if(Key_Found) {
			return;
		}
		else {
			if(index==input.length) {//判断是否到达叶子节点
				String bits = new String();
				for(int num:input) {
					bits+=num;
				}
				String Try = sdes.S_DES(Cipher_Text,bits,1)[0];
				if(Plain_Text.equals(Try)) {//判断密钥是否正确
					Key_Found=true;
					key = bits;
				}
				else {
					return;
				}
			}
			else {//递归
				int[] temp = new int[input.length];
				for(int i=0;i<input.length;i++) {
					temp[i]=input[i];
				}
				Recursion(temp,index+1,Plain_Text,Cipher_Text,sdes);
				temp[index]=1;
				Recursion(temp,index+1,Plain_Text,Cipher_Text,sdes);
			}
		}
	}

	//暴力破解得到第一个密钥
	public static void main(String args[]) {
		SDES s = new SDES();
		String Cipher=s.S_DES("01110110", "0010011101",0)[0];
		String[] result = crack("01110110",Cipher);
		System.out.println("The key is:"+result[0]);
		System.out.print("Process running time: "+result[1]+"ms");
	}
}
