package com.example.harbour.facemeetroom.widget.expandableListView;

import android.widget.ExpandableListView;

public class EListViewUtils {
    /**
     * 每展开一个分组后，关闭其他分组的功能
     */
    public boolean expandOnlyOne(int postion, ExpandableListView expandableListView){
        boolean result=true;
        int groupCount = expandableListView.getExpandableListAdapter().getGroupCount();
        for (int i=0;i<groupCount;i++){
            if (i!=postion && expandableListView.isGroupExpanded(i)){
                //当点击一个组对象后，关闭其他
                result &= expandableListView.collapseGroup(i);
            }
        }
        return  result;
    }
}
