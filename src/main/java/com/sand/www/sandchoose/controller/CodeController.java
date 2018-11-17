package com.sand.www.sandchoose.controller;

import com.sand.www.sandchoose.enums.CodeTypeEnum;
import com.sand.www.sandchoose.properties.CodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("code")
public class CodeController {

    @Autowired
    private CodeProperties codeProperties;
    private static Integer DEFAULT_COUNT = 5;//默认选5注
    private static Integer DEFAULT_NUM = 3;//默认选3个号码
    private static String DEFAULT_RANGE = "0-9";//默认的范围为0-9

    @RequestMapping("generate")
    public ModelAndView generate() {
        /*选号次数，选5注为5*/
        int count = StringUtils.hasText(codeProperties.getCount()) && Integer.parseInt(codeProperties.getCount()) > 0 ? Integer.parseInt(codeProperties.getCount()) : DEFAULT_COUNT;
        /*选号个数,3d为3个，排列5为5个*/
        int num = StringUtils.hasText(codeProperties.getNum()) && Integer.parseInt(codeProperties.getNum()) > 0 ? Integer.parseInt(codeProperties.getNum()) : DEFAULT_NUM;
        /*选号范围，0-9表示3d*/
        String range = StringUtils.hasText(codeProperties.getRange()) ? codeProperties.getRange() : DEFAULT_RANGE;
        /*历史参考数据*/
        String data = codeProperties.getHistory().getData();
        /*历史参考数据的期数*/
        int hisCount = StringUtils.hasText(codeProperties.getHistory().getCount()) ? Integer.parseInt(codeProperties.getHistory().getCount()) : 0;

        List<String> sbList = new ArrayList<>();
        //选号
        chooseCode(count, num, range, data, hisCount, sbList);
        ModelAndView modelAndView = new ModelAndView("gen");
        modelAndView.addObject("sbList",sbList);

        return modelAndView;
    }

    /**
     * 获得号码的变化范围
     * @param num
     * @return
     */
    private String getRangeByType(int num) {
        if (num <= 0) {
            return "";
        }
        if (num == CodeTypeEnum.KUAISAN.getCode()) {
            return CodeTypeEnum.KUAISAN.getValue();
        } else if (num == CodeTypeEnum.SAND.getCode()) {
            return CodeTypeEnum.SAND.getValue();
        } else if (num == CodeTypeEnum.PAILIESAN.getCode()) {
            return CodeTypeEnum.PAILIESAN.getValue();
        } else if (num == CodeTypeEnum.PAILIEWU.getCode()) {
            return CodeTypeEnum.PAILIEWU.getValue();
        }
        return "";
    }

    @RequestMapping("gen")
    public ModelAndView gen(String count, int number, String data) {
        System.out.println("count:=="+ count);
        System.out.println("number:=="+ number);
        System.out.println("data:=="+ data);
        /*选号次数，选5注为5*/
        int cou = StringUtils.hasText(count) ? Integer.parseInt(count) : DEFAULT_COUNT;
        /*选号个数,3d为3个，排列5为5个*/
        int num = getNum(number);
        num = num > 0 ? num : DEFAULT_NUM;
        /*选号范围，0-9表示3d*/
        String range = getRangeByType(number);
        range = StringUtils.hasText(range) ? range : DEFAULT_RANGE;

        List<String> sbList = new ArrayList<>();
        chooseCode(cou, num, range, data, 0, sbList);
        ModelAndView modelAndView = new ModelAndView("gen");
        modelAndView.addObject("sbList",sbList);

        return modelAndView;
    }

    private int getNum(int number) {
        if (number <= 0) {
            return 0;
        }
        if (number == CodeTypeEnum.KUAISAN.getCode() || number == CodeTypeEnum.SAND.getCode() || number == CodeTypeEnum.PAILIESAN.getCode()) {
            return 3;
        } else if (number == CodeTypeEnum.PAILIEWU.getCode()) {
            return 5;
        }
        return 0;
    }

    /**
     * 选号的逻辑
     * @param count
     * @param num
     * @param range
     * @param data
     * @param hisCount
     * @param sbList
     */
    private void chooseCode(int count, int num, String range, String data, int hisCount, List<String> sbList) {
        for (int i = 0; i < count; i++) {
            System.out.println("开始选取" + (i + 1) + "注号码");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < num; j++) {
                List<Integer> list = new ArrayList<>();
                String[] split = range.split("-");
                int start = Integer.parseInt(split[0]);
                int end = Integer.parseInt(split[1]);
                int totalCount = end;
                for (int k = start; k <= end; k++) {
                    list.add(k);
                }
                if (StringUtils.hasText(data)) {
                    String[] datas = data.split(",");
                    int kcount = hisCount > 0 ? hisCount : datas.length;
                    totalCount += datas.length;
                    for (int k = 0; k < kcount; k++) {
                        try{
                            list.add(Integer.parseInt(datas[k].substring(j, j + 1)));
                        } catch (Exception e) {
                            System.out.println("历史号码选择的位数不正确。");
                            continue;
                        }
                    }
                }
                Collections.shuffle(list);
                Random random = new Random();
                //防止数据越界
                totalCount = list.size() < totalCount ? list.size() : totalCount;
                Integer number = list.get(random.nextInt(totalCount));
                sb.append(number);
                list = null;
            }
            System.out.println("第" + (i + 1) + "注产生的号码为：" + sb.toString());
            sbList.add(sb.toString());
        }
    }
}
