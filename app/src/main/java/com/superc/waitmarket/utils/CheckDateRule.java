package com.superc.waitmarket.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CheckDateRule {

    /**
     *
     * @param mMapList 大小至少为2不然没意义
     * @return false=不符合条件  true=符合条件
     */
    public static boolean toCompare(List<Map<String, String>> mMapList) {
        Map<String, String> map_one = mMapList.get(0);
        Map<String, String> map_two = mMapList.get(1);
        int one_compare = compare_date(map_one.get("startTime"), map_one.get("endTime"));
        int two_compare = compare_date(map_two.get("startTime"), map_two.get("endTime"));
        if(one_compare==-1||one_compare==0||two_compare==-1||two_compare==0){
            return false;
        }
        boolean a = compareUtil(map_one, map_two);
        if (mMapList.size() > 2) {
            Map<String, String> map_three = mMapList.get(2);
            int three_compare = compare_date(map_three.get("startTime"), map_three.get("endTime"));
            if(three_compare==-1||three_compare==0){
                return false;
            }
            boolean b = compareUtil(map_two, map_three);
            boolean c = compareUtil(map_three, map_one);
            return a && b && c;
        }
        return a;
    }

    private static boolean compareUtil(Map<String, String> map_one, Map<String, String> map_two) {
        boolean is_sure = false;
        if (compare_date(map_one.get("endTime"), map_two.get("startTime")) == 1 || compare_date(map_one.get("endTime"), map_two.get("startTime")) == 0) {
            is_sure = true;
        } else {
            if (compare_date(map_one.get("startTime"), map_two.get("startTime")) == 1 || compare_date(map_one.get("startTime"), map_two.get("startTime")) == 0) {
                return false;
            } else {
                if (compare_date(map_one.get("startTime"), map_two.get("endTime")) == -1) {
                    is_sure = true;
                } else {
                    return false;
                }
            }
        }
        return is_sure;
    }

    /**
     * @param DATE1
     * @param DATE2
     * @return -1:DATE1>DATE2
     * 1:DATE1< DATE2
     * 0:DATE1==DATE2
     */
    public static int compare_date(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 > dt2");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1 < dt2");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     *
     * @param mMapList 大小至少为2不然没意义
     * @return false=不符合条件  true=符合条件
     */
    public static boolean toCompareDate(List<Map<String, String>> mMapList) {
        Map<String, String> map_one = mMapList.get(0);
        Map<String, String> map_two = mMapList.get(1);
        int one_compare = compare_dateDate(map_one.get("startDate"), map_one.get("endDate"));
        int two_compare = compare_dateDate(map_two.get("startDate"), map_two.get("endDate"));
        if(one_compare==-1||one_compare==0||two_compare==-1||two_compare==0){
            return false;
        }
        boolean a = compareUtilDate(map_one, map_two);
        if (mMapList.size() > 2) {
            Map<String, String> map_three = mMapList.get(2);
            int three_compare = compare_dateDate(map_three.get("startDate"), map_three.get("endDate"));
            if(three_compare==-1||three_compare==0){
                return false;
            }
            boolean b = compareUtilDate(map_two, map_three);
            boolean c = compareUtilDate(map_three, map_one);
            return a && b && c;
        }
        return a;
    }

    private static boolean compareUtilDate(Map<String, String> map_one, Map<String, String> map_two) {
        boolean is_sure = false;
        if (compare_dateDate(map_one.get("endDate"), map_two.get("startDate")) == 1 || compare_dateDate(map_one.get("endDate"), map_two.get("startDate")) == 0) {
            is_sure = true;
        } else {
            if (compare_dateDate(map_one.get("startDate"), map_two.get("startDate")) == 1 || compare_dateDate(map_one.get("startDate"), map_two.get("startDate")) == 0) {
                return false;
            } else {
                if (compare_dateDate(map_one.get("startDate"), map_two.get("endDate")) == -1) {
                    is_sure = true;
                } else {
                    return false;
                }
            }
        }
        return is_sure;
    }

    /**
     * @param DATE1
     * @param DATE2
     * @return -1:DATE1>DATE2
     * 1:DATE1< DATE2
     * 0:DATE1==DATE2
     */
    public static int compare_dateDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("MM-dd");
        try {
            if(DATE1.contains("/")){
                DATE1=DATE1.replaceAll("/","-");
            }
            if(DATE2.contains("/")){
                DATE2=DATE2.replaceAll("/","-");
            }
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 > dt2");
                return -1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1 < dt2");
                return 1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
