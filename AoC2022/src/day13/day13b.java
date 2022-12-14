package day13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class day13b {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day13.txt"));

        var list = new ArrayList<String>();

        int correctSum = 0;

        PacketList leftPacket = null;
        PacketList rightPacket = null;
        List<String> input = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.isBlank()) {
                input.add(line);
            }
        }

        List<String> resultsInOrder = new ArrayList<>();
        cycle(input,resultsInOrder);

        int firstExtra = resultsInOrder.indexOf("[[2]]") + 1;
        int secondExtra = resultsInOrder.indexOf("[[6]]") + 1;
        System.out.println(firstExtra * secondExtra);

    }

    private static void cycle(List<String> input, List<String> resultsInOrder){

        List<String>  filtered = new ArrayList<>();

        for (String thisLine : input) {
            String leftString = thisLine;
            PacketList left = parsePacket(thisLine);

            for (int i = 0; i < input.size(); i++) {
                PacketList right = parsePacket(input.get(i));
                if (compare(left, right) == -1){
                    left = right;
                    leftString = input.get(i);
                }
            }
            resultsInOrder.add(leftString);
            String finalLeftString = leftString;
            filtered = input.stream().filter(s -> !s.equals(finalLeftString)).collect(Collectors.toList());


            if (filtered.size() !=0){
            cycle(filtered, resultsInOrder);
            }
            return;

        }
    }



    public static PacketList parsePacket(String s) {
        PacketList packet = new PacketList();

        int index = 1;
        int endIndex = index + 1;
        while (index < s.length()) {
            if (s.charAt(index) == '[') {
                int listDepth = 1;

                while (listDepth > 0) {

                    if (s.charAt(endIndex) == '[') {
                        listDepth++;
                    }
                    if (s.charAt(endIndex) == ']') {
                        listDepth--;
                    }

                    endIndex++;
                }

                String subList = s.substring(index, endIndex);
                PacketList newP = parsePacket(subList);
                packet.sublists.add(newP);
                index = endIndex;
            } else {

                endIndex = s.indexOf(",", index + 1);

                if (endIndex == -1) {

                    endIndex = s.indexOf("]", index);
                }

                String ele = s.substring(index, endIndex);
                PacketList num = new PacketList();
                try {
                    num.value = Integer.parseInt(ele);
                    packet.sublists.add(num);
                }catch (NumberFormatException e){
                    //System.out.println("出错：当前处理的对象为[]");
                }
                index = endIndex;
            }
            index++;
        }
        return packet;
    }



    private static int compare(PacketList leftPacket, PacketList rightPacket) {
        int compareIndex = 0;

        while (compareIndex < leftPacket.sublists.size() || compareIndex < rightPacket.sublists.size()) {

            //handle if either list runs out of values
            if (leftPacket.sublists.size() <= compareIndex) return 1;
            if (rightPacket.sublists.size() <= compareIndex) return -1;


            PacketList leftCur = leftPacket.sublists.get(compareIndex);
            PacketList rightCur = rightPacket.sublists.get(compareIndex);


            //Case 1: List on the left, integer on the right
            if (leftCur.sublists.size() > 0 && rightCur.value != -1) {

                PacketList newRightHolder = new PacketList();
                PacketList newRight = new PacketList();
                newRight.value = rightCur.value;
                newRightHolder.sublists.add(newRight);

                int compare = compare(leftCur, newRightHolder);

                if (compare != 0) {
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }


            //Case 2: Integer on the left, list on the right
            if (leftCur.value != -1 && rightCur.sublists.size() > 0) {

                PacketList newLeftHolder = new PacketList();
                PacketList newLeft = new PacketList();
                newLeft.value = leftCur.value;
                newLeftHolder.sublists.add(newLeft);

                int compare = compare(newLeftHolder, rightCur);

                if (compare != 0) {
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }


            int leftVal = leftPacket.sublists.get(compareIndex).value;
            int rightVal = rightPacket.sublists.get(compareIndex).value;

            //Case 3: Both are lists
            if (leftVal == -1 && rightVal == -1 ) {
                int compare = compare(leftPacket.sublists.get(compareIndex), rightPacket.sublists.get(compareIndex));
                if (compare != 0) {
                    return compare;
                } else {
                    compareIndex++;
                    continue;
                }
            }

            //Case 4: Both are integers
            if (leftVal < rightVal) return 1;
            if (leftVal > rightVal) return -1;
            //if equal, move onto next index of loop
            compareIndex++;
        }
        //if nothing returned yet, test was inconclusive
        return 0;
    }
}
