package day13;

import com.sun.source.tree.IfTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class day13a {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("AoC2022/inputs/input_day13.txt"));

        int correctSum = 0;
        String left = "";
        String right = "";

        PacketList leftPacket = null;
        PacketList rightPacket = null;

        String line;
        int index = 0; //记录第n-1对
        int i = 1; //记录单双数
        while ((line = br.readLine()) != null) {

            if (!line.isBlank()) {
                if (i % 2 != 0) { //单数
                    left = line;
                    leftPacket = parsePacket(left);
                    int n = leftPacket.sublists.size();
                } else { //双数
                    right = line;
                    rightPacket = parsePacket(right);
                    int m = rightPacket.sublists.size();
                }
                i++;
                if (leftPacket != null & rightPacket != null) {
                    if (compare(leftPacket, rightPacket) == 1) {//正确的顺序
                        System.out.println("正确的pair：" + (index + 1));
                        correctSum += index + 1;
                    }
                }

            } else {
                index++;
                leftPacket = null;
                rightPacket = null;
                continue;
            }
        }
        System.out.println("所有正确组合的索引之和等于：" + correctSum);
    }


    /**
     * 接受一个字符串，不断拆分为最小单位的PacketList用于储存
     * @return PacketList-- 其sublists包含这个字符串的全部拆分内容
     */
    public static PacketList parsePacket(String s) {
        PacketList packet = new PacketList();
        //只要是列表，就把列表里的东西recursion
        //只要是数字，就包装为PacketList加入packet

        int index = 1; //从第二个位置开始，第一个位置默认为[

        while (index < s.length()) {
            if (s.charAt(index) == '[') {
                int listDepth = 1;
                int endIndex = index + 1;
                //说明第x个元素为list,则需要找到list结束的位置，也就是第一个']'的位置

                while (listDepth > 0) {

                    if (s.charAt(endIndex) == ']') {
                        listDepth--;
                    }
                    if (s.charAt(endIndex) == '[') {
                        listDepth++;
                    }

                    //如果此list还未结束，则继续进一位index，去找到它结束的位置
                    endIndex++;
                }

                String subList = s.substring(index, endIndex);//一个list
                PacketList newP = parsePacket(subList);
                packet.sublists.add(newP);
                index = endIndex;
            } else {
                int endIndex = s.indexOf(",", index + 1);

                if (endIndex == -1) { //是list中最后一个元素
                    endIndex = s.indexOf("]", index);
                }

                //不是list中最后一个元素
                String ele = s.substring(index, endIndex);
                PacketList number = new PacketList();
                try {
                    number.value = Integer.parseInt(ele);
                    packet.sublists.add(number);
                }catch (NumberFormatException e){
                    //System.out.println("出错：当前处理的对象为[]");
                }
                index = endIndex;
            }
            index++;
        }
        return packet;
    }


    /**
     * 比较左右顺序是否正确
     * @return 1是正确顺序, -1是错误顺序，0是还未分出错对
     */
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

                if (compare != 0) { //已经可以比较出是否正确
                    return compare;
                } else { //还看不出是否正确，这时候要进一步比较
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

                if (compare != 0) { //已经可以比较出是否正确
                    return compare;
                } else { //还看不出是否正确，这时候要进一步比较
                    compareIndex++;
                    continue;
                }
            }

            int leftVal = leftCur.value;
            int rightVal = rightCur.value;


            //Case 3: Both are lists
            if (leftVal == -1 && rightVal == -1 ) {
                int compare = compare(leftCur, rightCur);
                if (compare != 0) { //已经可以比较出是否正确
                    return compare;
                } else { //还看不出是否正确，这时候要进一步比较
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

