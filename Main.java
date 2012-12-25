import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class Main {
    private static Option opt = new Option();
    private static MersenneTwisterFast MTF = new MersenneTwisterFast();
    
    public  Main(String[] args) throws Exception {
        opt.parse(args);
        
        int writeUnitSize = 1024; 
        Random rnd = new Random();
        int count = 1; // ある程度書き込んだら表示する係数
        ByteBuffer buff = ByteBuffer.allocateDirect(writeUnitSize);
        long volumeNow = 0; // 書き込んでる最中のその都度の容量
        long start = 0; // 時間計測(start)
        long stop = 0; // 時間計測(stop)
        byte[] data = new byte[writeUnitSize];
        long totalSize = opt.totalVolumeSize;
        FileChannel channel = new FileOutputStream(opt.outputFile).getChannel();

        start = java.lang.System.currentTimeMillis();
        while (totalSize > volumeNow) {
            volumeNow += writeUnitSize; 
            makeData(data, rnd);
            write(buff, channel, data);
            if (volumeNow ==  count * totalSize/opt.num ) { 
                display(volumeNow, count, start, stop,totalSize);
                count++;
            }
        } 
        channel.close();
    }

    void display(long volumeNow, int count, long start, long stop,long totalSize) {
        stop = java.lang.System.currentTimeMillis();
        double time = ((stop - start) / 1000.0)/count ;
        long diff = volumeNow - (count-1) * totalSize / opt.num;
        long unit = (long) Math.pow(2, 30);
        System.out.println(time + " [s]             " + 8 * diff / (time * unit) + "[Gbps]");
        start = java.lang.System.currentTimeMillis();
    }

    void makeData(byte[] data, Random rnd) {
        MTF.nextBytes(data);
    }

    void write(ByteBuffer buff, FileChannel channel, byte[] data) throws Exception {
        buff.put(data);
        buff.flip();
        channel.write(buff);
        buff.compact(); // バッファへのデータ格納に備える。
    }
    
    public static void main(String[] args) throws Exception{
        new Main(args);
    }
}