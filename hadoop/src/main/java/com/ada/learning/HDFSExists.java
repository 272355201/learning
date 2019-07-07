package com.ada.learning;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

import java.util.List;

/**
 * Hello world!
 *
 */
public class HDFSExists
{

    @SneakyThrows
    public static void main( String[] args )
    {
        String fileName = "/user/hadoop/input/core-site.xml";
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://localhost:9000");
        conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
        FileSystem fs = FileSystem.get(conf);
        Path f = new Path(fileName);
        if (fs.exists(f)){
            System.out.println("文件存在");

            if (fs.isFile(f)){
                FSDataInputStream open = fs.open(f);
                List<String> strings = IOUtils.readLines(open);
                strings.forEach(System.out::println);
            }
        } else{
            System.out.println("文件不存在");
        }

    }
}
