import java.io.File;

public class Option {
    long totalVolumeSize = 0; // �S�̗̂e�ʂ̃T�C�Yf
    int num = 0; // �P�ʂ̌W��
    File outputFile;

    String fileName; // �t�@�C�������i�[����

    public void parse(String[] args) {
        // TODO Auto-generated constructor stub
        char[] unitNameList = { 'k', 'M', 'G' }; // �����̒P��

        // ����������p�X����������
        outputFile = new File(args[0]);

        // ����������e�ʂ��o�C�g�ɕϊ�����B & �x�[�X�e�ʂ��߂Ă���
        for (int i = 0; i < 3; i++) {
            if (args[1].indexOf(unitNameList[i]) != -1) {
                num = Integer.parseInt(args[1].substring(0, args[1].length() - 1));
                totalVolumeSize = (long) Math.pow(2, 10 * (i + 1)) * num;
            }
        }
        // ��O�������番�����鐔����������B
        if (args[0].equals("nul")) {
            fileName = args[0];
        } else {
            fileName = args[0] + args[1] + ".txt";
        }
        outputFile = new File(fileName);
    }
}
