package com.lfc.zhihuidangjianapp.ui.activity.model;

import com.google.gson.Gson;
import com.lfc.zhihuidangjianapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @date: 2019-08-06
 * @autror: guojian
 * @description:
 */
public class NativePartyBody {

    private List<List<PartBody>> partBodys;


    private String[] titles = {"党建平台", "党建资讯",
//            "示范引领",
            "学习强局", "林区风采", "通讯平台"};

    private int[][] imageArrays = {
            {
                    R.mipmap.img_home_tab1,
                    R.mipmap.img_dangwu_tab1_item2,
                    R.mipmap.img_dangwu_tab1_item3,
                    R.mipmap.img_dangwu_tab1_item4,
                    R.mipmap.img_dangwu_tab1_item5,
                    R.mipmap.img_develop_party
            },
            {
                    R.mipmap.img_dangwu_tab2_item1,
                    R.mipmap.img_dangwu_tab2_item2,
                    R.mipmap.img_dangwu_tab2_item3
            },
//            {
//                    R.mipmap.img_dangwu_tab3_item1,
//                    R.mipmap.img_dangwu_tab3_item2,
//                    R.mipmap.img_dangwu_tab3_item3
//            },
            {
                    R.mipmap.img_dangwu_tab4_item1,
                    R.mipmap.img_dangwu_tab4_item2,
                    R.mipmap.img_dangwu_tab4_item3
            },
            {
                    R.mipmap.img_dangwu_tab5_item1,
                    R.mipmap.img_dangwu_tab5_item2,
                    R.mipmap.img_dangwu_tab5_item3,
                    R.mipmap.img_dangwu_tab5_item4
            },
            {
                    R.mipmap.img_dangwu_tab6_item1,
                    R.mipmap.img_dangwu_tab6_item2
            }
    };

    private String[][] contents = {
            {
                    "党建矩阵", "党费收缴", "党员关系转移", "组织生活", "工作周报", "发展党员"
            },
            {
                    "党建动态", "群团统战", "廉政建设"
            },
//            {
//                    "党委示范", "支部引领", "党员实践"
//            },
            {
                    "林草大讲堂", "工匠培养", "学习心得"
            },
            {
                    "先进基层党", "优秀党务工作者", "优秀共产党员", "党建联系员"
            },
            {
                    "通讯录", "会议连线"
            }
    };


    public List<List<PartBody>> getPartBodys() {
        if (partBodys == null) {
            partBodys = new ArrayList<>();
            for (int i = 0; i < titles.length; i++) {
                List<PartBody> partBodyList = new ArrayList<>();
                for (int j = 0; j < contents[i].length; j++) {
                    PartBody partBody = new PartBody();
                    partBody.text = contents[i][j];
                    partBody.imageRes = imageArrays[i][j];
                    partBody.title = titles[i];
                    partBodyList.add(partBody);
                }
                partBodys.add(partBodyList);
            }
        }
        String part = new Gson().toJson(partBodys).toString();
        return partBodys;
    }

    public class PartBody {
        public String title;
        public int imageRes;
        public String text;

    }


}
