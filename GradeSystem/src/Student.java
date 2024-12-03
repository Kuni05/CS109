public class Student {

    private static int count = 0;

    public static double calculatePersonalScore( int groupScore, int rate, int groupSize ){
        if( groupSize==2 ){
            if( rate <= 5 )         return groupScore * 0.2;
            if( rate <= 10 )        return groupScore * ( 0.2 + (rate - 5) * 0.2 / 5 );
            if( rate <= 30 )        return groupScore * ( 0.4 + (rate - 10) * 0.4 / 20 );
            if( rate <= 50 )        return groupScore * ( 0.8 + (rate - 30) * 0.2 / 20 );
            if( rate <= 90 )        return groupScore * ( 1.0 + (rate - 50) * 0.25 / 40 );
            return groupScore * 1.25;
        }
        else if (groupSize == 3) {
            if( rate <= 5 )           return groupScore * 0.15;
            if( rate <= 18 )          return groupScore * (0.15 + (rate - 5) * 0.65 / 13 );
            if( rate <= 33 )          return groupScore * (0.8 + (rate - 18) * 0.2 / 15 );
            if( rate <= 75 )          return groupScore * (1.0 + (rate - 33) * 0.22 / 42 );
            return groupScore * 1.22;
        }
        return 0;
    }


    private int studentId;
    private int groupSize;
    private char groupNumber;
    private int groupScore;
    private double personalScore;
    private int rate;


    public Student(int groupScore,int rate,char groupNumber){
        this.studentId = ++count ;
        this.groupScore = groupScore;
        this.groupNumber = groupNumber;
        this.groupSize = 0 ;
        this.personalScore = 0 ;
        this.rate = rate ;
    }


    public Student(int groupScore, int rate, int groupSize) {
        this.studentId = ++count;
        this.groupScore = groupScore;
        this.rate = rate;
        this.groupSize = groupSize;
        this.personalScore = 0;
    }



    public int getStudentId(){
        return studentId;
    }

    public int getGroupSize(){
        return groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    public char getGroupNumber(){
        return groupNumber;
    }

    public void setGroupNumber(char groupNumber){
        this.groupNumber = groupNumber;
    }

    public void setGroupScore(int groupScore){
        this.groupScore = groupScore;
    }

    public double getGroupScore(){
        return groupScore;
    }

    public int getRate(){
        return rate;
    }

    public void setPersonalScore(double personalScore) {
        this.personalScore = personalScore;
    }

    public void setRate(int rate){
        this.rate = rate;
    }

    public void updatePersonalScore(){
        this.personalScore = calculatePersonalScore(groupScore, rate, groupSize);
    }

    public String toString(){
        return studentId+" group:"+ groupScore + " personal:" + Math.round(personalScore) + " rate:" + rate ;
    }


}
