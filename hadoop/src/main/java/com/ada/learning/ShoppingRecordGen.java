package com.ada.learning;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ShoppingRecordGen {
    @SneakyThrows
    public static void main(String[] args) {
        String file = "/tmp/record";
        FileWriter writer = new FileWriter(file, true);
        List<String> lines = new LinkedList<>();
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        for (int i = 0; i < 300000; i++) {
            lines.add(String.format("%s,%s,%s,%s,%s,%s",
                    random.nextInt(10000), // user_id
                    random.nextInt(100000), // good_id
                    random.nextInt(4) + 1, //行为id，1234：浏览/收藏/加购物车/购买
                    random.nextInt(1000) < 500? "" : random.nextInt(1000), //用户位置
                    random.nextInt(100), //goods type
                    sdf.format(random.nextLong()) //create_time
            ));
            if (lines.size() >= 5000){
                IOUtils.writeLines(lines, "\n", writer);
                lines.clear();
            }
        }

        IOUtils.writeLines(lines, "\n", writer);
        writer.close();
    }
}
