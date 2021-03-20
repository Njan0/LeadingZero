/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package njan.leadingzero;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Jan
 */
public class LeadingZeroGenerator {
    static class SplittedString {
        ArrayList<String> strings;
        ArrayList<Integer> splitters;

        public SplittedString() {
            this.strings = new ArrayList<>();
            this.splitters = new ArrayList<>();
        }
        
        public String concatenate() {
            String result = "";
            for (int i = 0; i < splitters.size(); ++i) {
                result += strings.get(i) + splitters.get(i);
            }
            result += strings.get(strings.size() - 1);
            
            return result;
        }
        
        /**
         * Get the length of the first digit
         * @return 
         */
        public int getFirstDigitLength() {
            if (splitters.isEmpty())
                return 0;
            
            return splitters.get(0).toString().length();
        }
        
        /**
         * Concatenate the first split and pad the 
         * splitting digit with 0 to given length.
         * @param length 
         */
        public void setFirstDigitLength(int length) {
            if (splitters.isEmpty())
                return;
            
            String con = strings.get(0);
            // pad with zeroes
            for (int i = length - getFirstDigitLength(); i > 0; --i) {
                con += "0";
            }
            con += splitters.get(0) + strings.get(1);
            
            strings.set(0, con);
            strings.remove(1);
            splitters.remove(0);
        }
        
        public boolean hasSplits() {
            return splitters.size() > 0;
        }
    }
    
    public static String[] Generate(String[] list) {
        var splits = Split(list);
        String[] result = new String[list.length];
        java.util.Arrays.fill(result, "");
        
        while(true) {
            // Put each split into a bucket using the first string as key
            Map<String, ArrayList<Integer>> map = new HashMap<>();
            for (int i = 0; i < splits.length; ++i) {
                if (!splits[i].hasSplits())
                    result[i] = splits[i].concatenate();

                if (!result[i].equals(""))
                    continue;

                // use first string as key
                String key = splits[i].strings.get(0);
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(i);
            }
            
            // Nothing to do
            if (map.isEmpty())
                break;

            // loop through each bucket
            for (var entry : map.entrySet()) {
                var group = entry.getValue();
                if (group.size() == 1) {
                    // only one element in the bucket
                    var single = group.get(0);
                    result[single] = splits[single].concatenate();
                } else {
                    // get longest digit in group
                    int maxLen = 0;
                    for (var id : group) {
                        maxLen = Integer.max(maxLen, splits[id].getFirstDigitLength());
                    }

                    // pad first digits to maxLen
                    for (var id : group) {
                        splits[id].setFirstDigitLength(maxLen);
                    }
                }
            }
        }
        
        return result;
    }
    
    
    /**
     * Separate each string in list by numbers.
     * @param list
     * @return 
     */
    static SplittedString[] Split(String[] list) {
        SplittedString[] result = new SplittedString[list.length];
        for (int i = 0; i < list.length; ++i) {
            result[i] = new SplittedString();
        }
        
        for (int i = 0; i < list.length; ++i) {
            String current = "";
            for (int ci = 0; ci < list[i].length(); ++ci) {
                char c = list[i].charAt(ci);
                // Append to current until a digit is encountered
                if (Character.isDigit(c)) {
                    // Store current string
                    result[i].strings.add(current);
                    
                    // Read number
                    current = Character.toString(c);
                    for (++ci; ci < list[i].length(); ++ci) {
                        c = list[i].charAt(ci);
                        // Append to current until a non digit is encountered
                        if (Character.isDigit(c)) {
                            current += c;
                        } else {
                            // Store number
                            result[i].splitters.add(Integer.parseInt(current));
                            
                            // Read non-digits again
                            current = Character.toString(c);
                            break;
                        }
                    }
                    // End of string reached while searching number
                    if (ci == list[i].length()) {
                        result[i].splitters.add(Integer.parseInt(current));
                        current = "";
                    }
                } else {
                    current += c;
                }
            }
            
            result[i].strings.add(current);
        }
        
        return result;
    }
    
    
}
