import java.util.Scanner;

public class exercise1 {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String IPv4 = sc.nextLine();
        IPv4= IPv4.replaceAll("[a-zA-Z]","256");
        String[] ipv4 = IPv4.split("\\.");
        if(ipv4.length != 4){
            System.out.println("No");
            return;
        }
        for(String e: ipv4){
            if( e.isEmpty() || !(Integer.valueOf(e)>=0 && Integer.valueOf(e)<=255) || (e.length()>1 && e.startsWith("0")) )
            {
                System.out.println("No");
                return;
            }
        }
        System.out.println("Yes");
    }
}
