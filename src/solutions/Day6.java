package solutions;

public class Day6 {
    // DAY 6 : Find index of first group of 4 unique characters
    public static void p1(){
        String[] lines = Common.FileToLines("inputs/d6.input");
        if(lines == null){
            System.out.println("d6p1:  STRING ARRAY IS NULL");
        }
        String input = lines[0];
        int answer = FindIndexOfUniqueGroup(input, 4);
        System.out.println(String.format("d6p2:  First instance = %d", answer));
    }

    //DAY 6 : Find index of first group of 14 unique characters
    public static void p2(){
        String[] lines = Common.FileToLines("inputs/d6.input");
        if(lines == null){
            System.out.println("d6p2:  STRING ARRAY IS NULL");
        }
        String input = lines[0];
        int answer = FindIndexOfUniqueGroup(input, 14);
        System.out.println(String.format("d6p2:  First instance = %d", answer));
    }
    
    // returns the index of the character after the first group of unique characters 
    // in string 'string' of length 'groupLength'
    // returns -1 if no group is found
    private static int FindIndexOfUniqueGroup(String string, int groupLength){
        int groupStartIndex = 0;

        for(int i = 0; i < string.length(); i++) {
            for(int j = groupStartIndex; j < i; j++){
                if(string.charAt(i) == string.charAt(j)){
                    groupStartIndex = j+1;
                    break;
                }
            }
            if(i - groupStartIndex == groupLength-1){
                return i+1;
            }
        }

        return -1;
    }
}
