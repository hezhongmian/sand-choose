package com.sand.www.sandchoose;

import com.sand.www.sandchoose.properties.CodeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class SandChooseApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SandChooseApplication.class, args);
    }

    @Autowired
    private CodeProperties codeProperties;

    private static Integer DEFAULT_COUNT = 5;//默认选5注
    private static Integer DEFAULT_NUM = 3;//默认选3个号码
    private static String DEFAULT_RANGE = "0-9";//默认的范围为0-9

    @Override
    public void run(String... args) throws Exception {
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

//        for (int i = 0; i < count; i++) {
//            System.out.println("开始选取" + (i + 1) + "注号码");
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j < num; j++) {
//                List<Integer> list = new ArrayList<>();
//                String[] split = range.split("-");
//                int start = Integer.parseInt(split[0]);
//                int end = Integer.parseInt(split[1]);
//                int totalCount = end;
//                for (int k = start; k <= end; k++) {
//                    list.add(k);
//                }
//                if (StringUtils.hasText(data)) {
//                    String[] datas = data.split(",");
//                    int kcount = hisCount > 0 ? hisCount : datas.length;
//                    for (int k = 0; k < kcount; k++) {
//                        list.add(Integer.parseInt(datas[k].substring(j, j + 1)));
//                    }
//                    totalCount += datas.length;
//                }
//                Collections.shuffle(list);
//                Random random = new Random();
//                Integer number = list.get(random.nextInt(totalCount));
//                System.out.println("第" + (j + 1) + "位的号码是：" + number);
//                sb.append(number);
//                list = null;
//            }
//            System.out.println("第" + (i + 1) + "注产生的号码为：" + sb.toString());
//        }
    }
}
