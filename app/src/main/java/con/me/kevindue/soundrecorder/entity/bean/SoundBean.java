package con.me.kevindue.soundrecorder.entity.bean;

public class SoundBean {
    public String path;//文件路径
    public String name;//文件名称
    public long lastModified;//文件的最后一次修改时间
    public int iconRes;//"开始/停止"按钮图标

    public SoundBean(String path, String name, long lastModified){
        this.path = path;
        this.name = name;
        this.lastModified = lastModified;
    }
}
