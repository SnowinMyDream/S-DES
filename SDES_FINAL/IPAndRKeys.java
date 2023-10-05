package SDES_FINAL;

public class IPAndRKeys {
    public int IP[] = {2,6,3,1,4,8,5,7};
    public int IP_inverse[] = {4,1,3,5,7,2,8,6};
    public int P_10[] = {3,5,2,7,4,10,1,9,8,6};
    public int P_8[] = {6,3,7,4,8,5,10,9};
    public int Leftshift1[] = {2,3,4,5,1};
    public int Leftshift2[] = {3,4,5,1,2};

    //IP置换,8bit明文进行初始置换
    public String IP(String plaintext){
        String str = new String();

        for(int i=0;i<8;i++){
            str+=plaintext.charAt(IP[i]-1);
        }

        return str;
    }

    //IP逆置换
    public String IP_inverse(String lasttext){
        String str = new String();

        for(int i=0;i<8;i++){
            str+=lasttext.charAt(IP_inverse[i]-1);
        }

        return str;
    }
    //Leftshift置换1
    public String LeftShift1(String halftext){
        String  str = new String();

        for(int i=0;i<5;i++){
            str+=halftext.charAt(Leftshift1[i]-1);
        }

        return str;
    }
    //Leftshift置换2
    public String LeftShift2(String halftext){
        String  str = new String();

        for(int i=0;i<5;i++){
            str+=halftext.charAt(Leftshift2[i]-1);
        }

        return str;
    }
    //P10置换
    public String P_10(String lasttext){
        String  str = new String();

        for(int i=0;i<10;i++){
            str+=lasttext.charAt(P_10[i]-1);
        }

        return  str;
    }
    //P8置换
    public String P_8(String lasttext){
        String  str = new String();

        for(int i=0;i<8;i++){
            str+=lasttext.charAt(P_8[i]-1);
        }

        return  str;
    }
    //密钥生成，生成子密钥k1、k2
    public String[] Key(String key_original){
        String str = P_10(key_original);
        String left1 = LeftShift1(str.substring(0,5));
        String right1 = LeftShift1(str.substring(5,10));

        String key1 = P_8(left1+right1);

        String left2 = LeftShift2(left1);
        String right2 = LeftShift2(right1);

        String key2 = P_8(left2+right2);

        String key[] = {key1,key2};

        return key;
    }
}

