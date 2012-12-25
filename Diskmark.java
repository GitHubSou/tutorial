import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Diskmark {
    private static MersenneTwisterFast MTF = new MersenneTwisterFast();
    private static final long GIGABYTE = (long)Math.pow(2, 30);
    
    public  static void main(String[] args) throws Exception {
        Option opt = new Option();
        opt.parse(args);
        Diskmark.benchmark(opt);
    }
    
    public static void benchmark(Option opt) throws Exception {
        int writeUnitSize = 1024; 
        ByteBuffer buff = ByteBuffer.allocateDirect(writeUnitSize);
        long volumeNow = 0; // 書き込んでる最中のその都度の容量
        byte[] data = new byte[writeUnitSize];
        long totalSize = opt.totalVolumeSize;
        FileOutputStream fos = new FileOutputStream(opt.outputFile);
        FileChannel channel = fos.getChannel();

        long start = java.lang.System.currentTimeMillis();
        while (totalSize > volumeNow) {
            volumeNow += writeUnitSize; 
            MTF.nextBytes(data);        //メルセンヌツイストの高速版
            Diskmark.write(buff, channel, data);
        }
        double elapsedTime = (java.lang.System.currentTimeMillis() - start) / 1000.0;      
        
        System.out.println(String.format("%.4f[s], %.4f[Gbps]", elapsedTime, 8 * volumeNow / (elapsedTime * GIGABYTE)));
        
        channel.close();
        fos.close();
    }

    public static void write(ByteBuffer buff, FileChannel channel, byte[] data) throws Exception {
        buff.put(data);
        buff.flip();
        channel.write(buff);
        buff.compact(); // バッファへのデータ格納に備える。
    }
}