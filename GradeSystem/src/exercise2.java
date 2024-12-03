import java.util.Scanner;

public class exercise2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine() , b = sc.nextLine();
        int count=0;
        String mid=a.replace(b, "").replaceAll("[^a-zA-Z]", "").toLowerCase();

        for( int i=0 ; i<mid.length() ; i++ ){
            if( mid.charAt(i)==mid.charAt( mid.length()-1-i ) ){
                count++;
            }
        }

        if (count==mid.length()) {
            System.out.println("Yes");
        }
        else    System.out.println("No");

    }
}
