package CosineMatrix;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MR3 {
    //输入文件路径
    //private static String inPath = "/user/wzy/ContentRecommend/step1_input/";
    private static String inPath = "ContentRecommend/step1_input/";
    //输出文件路径
    //private static String outPath = "/user/wzy/ItemCF/step3_output";
    private static String outPath = "ContentRecommend/step3_output";
    //设置缓存路径
    //这样让路径直接找输出矩阵所在的文件，它的别名才起作用
    //private static String cache = "/user/wzy/ItemCF/step2_output/part-r-00000";
    private static String cache = "ContentRecommend/step2_output/part-r-00000";
    //hdfs文件地址
    private static String hdfs = "hdfs://localhost:9000";

    public static int run() {
        try {
            //创建job配置类
            Configuration conf = new Configuration();
            //设置hdfs地址
            conf.set("fs.defaultFS", hdfs);
            //创建一个job实例
            Job job = Job.getInstance(conf, "step3");

            System.out.println(cache + "#ItemUserScore2");
            //添加分布式缓存文件
            job.addCacheFile(new URI(cache + "#ItemUserScore2"));

            //设置job的主类
            job.setJarByClass(MR3.class);
            //设置job的Mapper和Reduce
            job.setMapperClass(Mapper3.class);
            job.setReducerClass(Reducer3.class);
            //设置Mapper的输出key和value类型
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            //设置Reducer的输出key和value类型
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileSystem fs = FileSystem.get(conf);
            Path inputPath = new Path(inPath);
            //设置输入输出路径
            if (fs.exists(inputPath)) {
                FileInputFormat.addInputPath(job, inputPath);
            }
            Path outputPath = new Path(outPath);
            //如果输出路径存在则删除路径
            fs.delete(outputPath, true);
            //将输出路径添加到job中
            FileOutputFormat.setOutputPath(job, outputPath);

            return job.waitForCompletion(true) ? 1 : -1;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args) {
        int result = 0;
        result = new MR3().run();
        if (result == 1) {
            System.out.println("step3运行成功!");
        } else {
            System.out.println("step3运行失败!");
        }
    }
}