package com.edevelopers.fastecom.activities_M;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> cricket = new ArrayList<String>();
        cricket.add("Sub Menu");
        cricket.add("Sub Menu");
        cricket.add("Sub Menu");
        cricket.add("Sub Menu");
        cricket.add("Sub Menu");

        List<String> football = new ArrayList<String>();
        football.add("Sub Menu");
        football.add("Sub Menu");
        football.add("Sub Menu");
        football.add("Sub Menu");
        football.add("Sub Menu");

        List<String> basketball = new ArrayList<String>();
        basketball.add("Sub Menu");
        basketball.add("Sub Menu");
        basketball.add("Sub Menu");
        basketball.add("Sub Menu");
        basketball.add("Sub Menu");

        List<String> baseball = new ArrayList<String>();
        baseball.add("Sub Menu");
        baseball.add("Sub Menu");
        baseball.add("Sub Menu");
        baseball.add("Sub Menu");
        baseball.add("Sub Menu");

        expandableListDetail.put("Menu List1", cricket);
        expandableListDetail.put("Menu List2", football);
        expandableListDetail.put("Menu List3", basketball);
        expandableListDetail.put("Menu List4", baseball);
        return expandableListDetail;
    }
}
