package job;


import CosineMatrix.MR3;
import ItemProfileTranspose.MR1;
import ItemUserMultiItemProfile.MR2;
import ZeroPurchaseMatrix.MR4;

public class JobRunner {
    public static void main(String[] args) {
        int status1 = -1;
        int status2 = -1;
        int status3 = -1;
        int status4 = -1;

        status1 = new MR1().run();
        if (status1 == 1) {
            System.out.println("Step 1 运行成功,开始运行Step 2 ......");
            status2 = new MR2().run();
        } else {
            System.out.println("Step 1 运行失败....");
        }
        if (status2 == 1) {
            System.out.println("Step 2 运行成功,开始运行Step 3 ......");
            status3 = new MR3().run();
        } else {
            System.out.println("Step 3 运行失败....");
        }
        if (status3 == 1) {
            System.out.println("Step 3 运行成功,开始运行Step 4 ......");
            status4 = new MR4().run();
        } else {
            System.out.println("Step 3 运行失败....");
        }
        if (status4 == 1) {
            System.out.println("Step 4 运行成功,结束运行 ......");
        } else {
            System.out.println("Step 4 运行失败....");
        }
    }
}
