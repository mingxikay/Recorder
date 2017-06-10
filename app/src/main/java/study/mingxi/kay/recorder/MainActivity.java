package study.mingxi.kay.recorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements OnClickListener{
    Button record,stop;
    File soundFile;
    MediaRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取按钮
        record = (Button)findViewById(R.id.record);
        stop =(Button)findViewById(R.id.stop);
        record.setOnClickListener(this);
        stop.setOnClickListener(this);
    }
    @Override
    public void onDestroy() {
        if (soundFile!=null && soundFile.exists())
        {
            //停止录音
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
        super.onDestroy();

    }
    @Override
    public void onClick(View source) {
        switch (source.getId())
        {
            //录音
            case  R.id.record:
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    Toast.makeText(MainActivity.this,"SD卡不存在，请插入SD卡",Toast.LENGTH_SHORT).show();
                    return;
                }
                try
                {
                    //创建音频文件
                    soundFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile()+"/sound.mp3");
                    //实例化对象
                    mRecorder = new MediaRecorder();
                    //设置声音来源（麦克风）
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //设置音频输出格式
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    //设置编码模式
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    mRecorder.setOutputFile(soundFile.getAbsolutePath());
                    mRecorder.prepare();
                    //录音开始
                    mRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            //停止录音
            case R.id.stop:
                if (soundFile!=null && soundFile.exists())
                {
                    //停止录音
                    mRecorder.stop();
                    mRecorder.release();
                    mRecorder=null;
                }
                break;
        }

    }
}
