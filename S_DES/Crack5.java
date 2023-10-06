package S_DES;

public class Crack5 {
	//初始化参数
	static int[] crack_bits = {0,0,0,0,0,0,0,0,0,0};
	static boolean Key_Found1 = false;//判断是否找到正确的密钥
	static boolean Key_Found2 = false;
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
		if(Key_Found1) {
			return;
		}
		else {
			if(index==input.length) {//判断是否到达叶子节点
				String bits = new String();
				for(int num:input) {
					bits+=num;
				}
				String Cipher1 = sdes.S_DES(Plain_Text,bits,0)[0];
				Recursion2(crack_bits,0,Plain_Text,Cipher1,sdes,bits);
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
	
	public static void Recursion2(int [] input,int index,String Plain_Text,String Cipher_Text,SDES sdes,String bits1) {
		if(Key_Found2) {
			return;
		}
		else {
			if(index==input.length) {//判断是否到达叶子节点
				String bits2 = new String();
				for(int num:input) {
					bits2+=num;
				}
				
				String Try = sdes.S_DES(Plain_Text,bits2,0)[0];
				System.out.println(bits1+","+bits2+","+Cipher_Text+","+Try);
				if(Cipher_Text.equals(Try)&&!bits1.equals(bits2)) {//判断密钥是否正确
					Key_Found2=true;
					System.out.println("如下两个明文与对应的两个密钥分别加密后会得到相同的密文：");
					System.out.println("Plain_Text:"+Plain_Text);
					System.out.println("Keys:"+bits1+","+bits2);
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
				Recursion2(temp,index+1,Plain_Text,Cipher_Text,sdes,bits1);
				temp[index]=1;
				Recursion2(temp,index+1,Plain_Text,Cipher_Text,sdes,bits1);
			}
		}
	}

	//寻找给定明文是否有不同密钥加密得到相同密文的可能
	public static void main(String args[]) {
		SDES sdes = new SDES();
		Recursion(crack_bits,0,"11101110","00001000",sdes);
	}
}
