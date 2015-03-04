package fragment.submissions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class StefanCross {
    
    private static ListIterator<String> iterator;

    // Template main method preserved as provided
    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new FileReader(args[0]))) {
            String fragmentProblem;
            while ((fragmentProblem = in.readLine()) != null) {
                System.out.println(reassemble(fragmentProblem)); }
        } catch (Exception e) { 
            e.printStackTrace();
        } 
    }


    /**
     * Function takes a fragmented problem, splits up fragments by semicolon and then proceeds
     * to find the head by capital letter, then iterates through the list looking for overlaps
     * or existing substring and removes them from the list, finally returning an attempted reconstruction 
     *  
     * @param fragmentProblem Jumbled original doc to be reassembled
     * @return String reconstituted document
     */
    public static String reassemble(String fragmentProblem){
        
        String[] fragprob = fragmentProblem.split(";");
        List<String> list = new ArrayList<>(Arrays.asList(fragprob));

        Collections.sort(list);
        String output = find_head(list);
        iterator = list.listIterator();
        
        while(iterator.hasNext()){
            String el = iterator.next();
            
            // Dispose of our existing elements
            if(output.contains(el)){
                list.remove(el);
                iterator = list.listIterator(); // reset iterator
            }
        
            String overlap = overlap(output, el);
            if(overlap.length() > 1){
                String word1 = el.replace(overlap, "");
                output += word1;
                list.remove(el);
                iterator = list.listIterator(); // reset iterator
            }
            
            if(!iterator.hasNext()){
                iterator = list.listIterator(); // reset iterator
            }
        }
        return output;
        
    }

    /**
     * Function to find the start of a sentence by presence of capital letter.
     * Returns first element with a capital as a string or an empty string if not.
     * List data structure is not mutated out of courtesy.
     * @param list list to search for starting caps element
     * @return String of the beginning sentence
     */
    public static String find_head(List list){
        Iterator<String> it = list.iterator();
        String output = "";
        while(it.hasNext()){
            String el = it.next();
            if(el.matches("^[A-Z].*")){
                output = el;
                return output;
            }
        }
        // should potentially return null if nothing found
        return output;
    }

    /**
     * Identify where two strings overlap, see tests for more specifics about what constitutes 
     * an overlap, eg either string has to end with the identified overlap.
     * Functions by taking each char of the first string and looking for matching subsequent 
     * in string 2. If there are no matches it will continue to the next char of the first string
     * again looking for matches. We have to compare all chars against all char sequences, so very
     * much brute force and far from optimal on this version.
     *  
     * @param string1 string for comparison
     * @param string2 string for comparison
     * @return String of overlap between inputs
     */
    public static String overlap(String string1, String string2){

        Map<Integer, String> map = new HashMap<>();
        String match = "";
        // Used for reassemble function when at the start of a doc
        if(string1.length() == 0){
            return string1;
        }
        // Loop through initial string1
        for(int i = 0; i < string1.length(); i++){
            // Look for a match for the first part of second string
            if(string1.charAt(i) == string2.charAt(0)){ 
                // Then loop through the second string matches
                for(int k = 0; k < string2.length(); k++){
                    // avoid searching beyond the length of the string1
                    if(i + k < string1.length()){
                        // accumulate matches in the strings
                        if(string1.charAt(i + k) == string2.charAt(k)){
                            match = (map.containsKey(i)) ? map.get(i).toString() + string2.charAt(k) :  "" + string2.charAt(k);
                            map.put(i, match);
                            //System.out.println("Match at: string1 char" + string1.charAt(i + k) + " with " + string2.charAt(k));
                        }
                    }
                }
            }
        }
        
        if(match.length() == 0){
            // recursive call through the string after removing head
            return overlap(string1, string2.substring(1));
        }
        // Get the longest match value in our hash map
        Iterator it = map.keySet().iterator();
        String longestmatch = "";
        while (it.hasNext()){
            String el = map.get(it.next());
            // comparator is dubious, better analysis required to ensure correct match
            if(el.length() >= longestmatch.length()){
                longestmatch = el;
            }
        }
        // final criteria check, overlap must extend to the end of either string
        if(string1.endsWith(longestmatch) || string2.endsWith(longestmatch)){
            return longestmatch;
        } else{
            // potentially throw an error, but bad practice to have exceptions control flow
            return ""; 
        }

    }

    // First attempt as reassembling string, only works for the first input text problem.
    public static String basic_reassemble(String fragmentProblem){
        String[] list = fragmentProblem.split(";");
        String output = "";

        for(int i = 0; i < list.length; i++){
            String overlap = overlap(output, list[i]);
            String word1 = list[i].replace(overlap, "");
            output += word1;

        }
        return output;
    }
}

