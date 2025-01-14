package edu.hitsz.thread;

import javax.sound.sampled.*;
import java.io.*;

/**
 * @author leng
 */
public class MusicThread extends Thread {


    /**
     * 音频文件名
     */
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;



    /**
     * 所有音效有无开启
     */
    public static boolean haveAudio=true;
    /**
     * 是否循环播放 标志
     */
    private boolean isLoop = false;
    /**
     * 结束播放 标志
     */
    private boolean toEnd = false;

    public void setLoop(boolean loop) {
        this.isLoop = loop;
    }

    public void setToEnd(boolean toEnd) {
        this.toEnd = toEnd;
    }

    public MusicThread(String filename) {
        //初始化filename
        this.filename = filename;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return samples;
    }

    public void play(InputStream source) {
        if (toEnd) {
            return;
        }
        int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
        byte[] buffer = new byte[size];
        //源数据行SourceDataLine是可以写入数据的数据行
        SourceDataLine dataLine = null;
        //获取受数据行支持的音频格式DataLine.info
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            dataLine = (SourceDataLine) AudioSystem.getLine(info);
            dataLine.open(audioFormat, size);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        dataLine.start();
        try {
            int numBytesRead = 0;
            while (numBytesRead != -1) {

                ///新加////
                if (toEnd) {
                    return;
                }
                //从音频流读取指定的最大数量的数据字节，并将其放入缓冲区中
                numBytesRead =
                        source.read(buffer, 0, buffer.length);
                if (toEnd) {
                    numBytesRead=-1;
                }
                //通过此源数据行将数据写入混频器
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        dataLine.drain();
        dataLine.close();

    }

    @Override
    public void run() {
        if (haveAudio) {
                while (isLoop) {
                    InputStream stream = new ByteArrayInputStream(samples);
                    play(stream);
                }
                InputStream stream = new ByteArrayInputStream(samples);
                play(stream);
            }
    }
}


