import java.io.File;

public class Option {
    long totalVolumeSize = 0; // 全体の容量のサイズf
    int num = 0; // 単位の係数
    File outputFile;

    String fileName; // ファイル名を格納する

    public void parse(String[] args) {
        // TODO Auto-generated constructor stub
        char[] unitNameList = { 'k', 'M', 'G' }; // 引数の単位

        // 第一引数からパスを所得する
        outputFile = new File(args[0]);

        // 第二引数から容量をバイトに変換する。 & ベース容量を定めておく
        for (int i = 0; i < 3; i++) {
            if (args[1].indexOf(unitNameList[i]) != -1) {
                num = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
                totalVolumeSize = (long) Math.pow(2, 10 * (i + 1)) * num;
            }
        }
        // 第三引数から分割する数を所得する。
        if (args[0].equals("nul")) {
            fileName = args[0];
        } else {
            fileName = args[0] + args[1] + ".txt";
        }
        outputFile = new File(fileName);
    }
}
