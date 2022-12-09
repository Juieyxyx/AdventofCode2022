package day7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class day7a {

    public static String currentDir = "";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("inputs/input_day7.txt"));
        String line;
        Map<String, Long> dic = new HashMap<>();

        while ((line = br.readLine()) != null) {
            String arr[] = line.trim().split(" ");

            if (line.startsWith("$ cd")) {
                //change directory
                switch (arr[2]) {
                    case "/":
                        //switches the current directory to the outermost directory, /.
                        currentDir = "/.";
                        dic.put(currentDir, 0L);
                        break;
                    case "..":
                        //makes the parent directory the current directory.
                        String[] chars = currentDir.split("");
                        for (int i = chars.length - 1; i >= 0; i--) {
                            if (chars[i].equals("_")) {
                                currentDir = currentDir.substring(0, i);
                                break;
                            }
                        }
                        break;
                    default:
                        //makes the child directory "X" the current directory.
                        currentDir = currentDir + "_/" + arr[2];
                        if (!dic.containsKey(currentDir)) {
                            dic.put(currentDir, 0L);
                        }
                        break;
                }
            } else if (line.startsWith("$ ls")) {
                //do nothing
            } else if (line.startsWith("dir")) {
                //dir xyz: meaning the current directory contains a directory named xyz.
                String childDir = currentDir + "_/" + arr[1];
                if (!dic.containsKey(childDir)) {
                    dic.put(childDir, 0L);
                }
            } else {
                //123 abc ->the current directory contains a file named abc with size 123.
                long size = dic.get(currentDir) + Long.parseLong(arr[0]);
                dic.put(currentDir, size);
            }
        }

        //add child dir sizes to parent dir sizes
        addChildToParent(dic);


        //find all the directories with a total size of at most 100000 and calculate the sum of their sizes
        long sum = 0;
        Iterator<Long> iterator = dic.values().iterator();
        while (iterator.hasNext()) {
            long size = iterator.next();
            if (size <= 100000) {
                sum += size;
            }
        }
        System.out.println(sum);
        System.out.println(dic.get("/."));
    }

    private static void addChildToParent(Map<String, Long> dic) {
        ArrayList<String> dirNames = new ArrayList<>();
        String parent = "";

        for (Map.Entry<String, Long> entry : dic.entrySet()) {
            dirNames.add(entry.getKey());
        }

        List<String >sorted = dirNames.stream().sorted(Comparator.comparingInt(String::length)).collect(Collectors.toList());

        for (int i = sorted.size() - 1; i >= 0; i--) {
            String name = sorted.get(i);

            if (name.contains("_/")) {
                if (name.equals("/.")){
                    break ;
                }
                String[] chars = name.split("");
                for (int j = chars.length - 1; j >= 0; j--) {
                    if (chars[j].equals("_")) {
                        parent = name.substring(0, j);
                        break;
                    }
                }
                long oldParentSize = dic.get(parent);
                long newParentSize = dic.get(parent) + dic.get(name);
                dic.put(parent, newParentSize);
            }
        }
    }
}

