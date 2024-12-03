public class GradeSystem {
    public static void generateStudentGroupSize(Student[] students){
        int[] countGroupSize =new int[26];
        for( Student s: students){
            countGroupSize[s.getGroupNumber()-'A']++;
        }
        for(Student s: students){
            s.setGroupSize(countGroupSize[s.getGroupNumber()-'A']);;
        }
    }


    public static void  standardizedScores(Student[] students){
        for(int i=0; i<students.length; i++)
            for(int j=i+1; j<students.length; j++)
            {
                if( (students[i].getGroupNumber()==students[j].getGroupNumber()) && (students[i].getGroupScore()!=students[j].getGroupScore()) ) {
                    students[i].setGroupScore(60);
                    students[j].setGroupScore(60);
                }
            }
    }



    public static void  generatePersonalScore(Student[] students){
        for(Student s: students )
            s.updatePersonalScore();
    }
}
