package con.me.kevindue.recorder;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

public class RecordFileUtil {
    private static final String RECORD_FILE = File.separator + "huochebang_recording_file" + File.separator;
    private static final String PREFIX = "record_"; //文件前缀
    private static final String SUFFIX = ".amr"; //文件后缀

    public static File getRecordingFile() {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            path = Environment.getDataDirectory().getAbsolutePath();
        }
        return new File(path, RECORD_FILE);
    }

    public static String getCurrentRecordingPath() {
        File recordingDir = getRecordingFile();
        if (!recordingDir.exists() || !recordingDir.isDirectory()) {
            recordingDir.mkdirs();
        }
        return new File(recordingDir, PREFIX + System.currentTimeMillis() + SUFFIX).getAbsolutePath();
    }

    public static File[] getAllRecordingFiles() {
        File recordingDir = getRecordingFile();
        if (recordingDir.exists() && recordingDir.isDirectory()) {
            return recordingDir.listFiles();
        }
        return null;
    }

    /**
     * 删除文件
     */
    public static void deleteRecordingFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)){
            File file = new File(filePath);
            if (file.exists() && file.isFile()){
                file.delete();
            }
        }
    }

}
