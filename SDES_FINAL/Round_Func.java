package SDES_FINAL;

import java.util.Arrays;

public class Round_Func {
	public int[] StringToArray(String s) {
		int[] Input = new int[s.length()];
		for(int i=0;i<s.length();i++) {
			Input[i]=Integer.parseInt(s.substring(i,i+1));
		}
		return Input;
	}
	//EPBox拓展
	public int[] E_P_Box(int[] n) {
		int[] Extended_bits = new int[8];
		Extended_bits[0]=n[3];Extended_bits[6]=n[3];
		Extended_bits[1]=n[0];Extended_bits[7]=n[0];
		Extended_bits[2]=n[1];Extended_bits[4]=n[1];
		Extended_bits[3]=n[2];Extended_bits[5]=n[2];
		return Extended_bits;
	}
	//Sbox压缩
	public int[] Sboxs(int[] n) {
		//分割数组
		int[] temp1 = new int[4];
		int[] temp2 = new int[4];
		for(int i=0;i<4;i++) {
			temp1[i]=n[i];
			temp2[i]=n[i+4];
		}
		//查表
		String table1[][] = new String[][]{{"01","00","11","10"},
										{"11","10","01","00"},
										{"00","10","01","11"},
										{"11","01","00","10"}};
		String table2[][] = new String[][]{{"00","01","10","11"},
										{"10","11","01","00"},
										{"11","00","01","10"},
										{"10","01","00","11"}};
		int row1 = temp1[0]*2+temp1[3];
		int column1 = temp1[1]*2+temp1[2];
		int row2 = temp2[0]*2+temp2[3];
		int column2 = temp2[1]*2+temp2[2];
		//合并
		String result = new String();
		result = table1[row1][column1]+table2[row2][column2];
		int[] compressed_bits = new int[4];
		for(int i=0;i<4;i++) {
			compressed_bits[i]=Integer.parseInt(result.substring(i,i+1));
		}
		return compressed_bits;
	}
	//SP置换
	public int[] SPBox(int[] n) {
		int[] temp = new int[4];
		temp[0]=n[1];temp[1]=n[3];temp[2]=n[2];temp[3]=n[0];
		return temp;
	}
	//异或运算
	public int[] XOR(int[] num1,int[] num2) {
		int[] result = new int[num1.length];
		for(int i=0;i<num1.length;i++) {
			if(num1[i]!=num2[i]) {
				result[i]=1;
			}
			else {
				result[i]=0;
			}
		}
		return result;
	}
	//交换运算
	public int[] Swap(int[] input){
		int[] output = new int[8];
		for(int i=0;i<4;i++){
			output[i]=input[i+4];
			output[i+4]=input[i];
		}
		return output;
	}
	//轮函数
	public int[] Round_Function(int[] round_data,int[] round_key) {
		//分割数组
		int[] left = new int[4];
		int[] right = new int[4];
		for(int i=0;i<4;i++) {
			left[i]=round_data[i];
			right[i]=round_data[i+4];
		}
		//右半数组进行F运算
		int [] changed_right = SPBox(Sboxs(XOR(E_P_Box(right),round_key)));
		//运算后的右半数组与左半异或
		left = XOR(left,changed_right);
		//合并结果
		int[] round_output = new int[8];
		for(int i=0;i<4;i++){//错误4：循环次数错误，原：for(int i=0;i<3;i++)
			round_output[i]=left[i];
			round_output[i+4]=right[i];
		}
		return round_output;
	}

}
