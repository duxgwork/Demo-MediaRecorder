package con.me.kevindue.soundrecorder;


import con.me.kevindue.base.BaseApplication;

public class App extends BaseApplication {
    /**
     * 单一实例
     */
    public static BaseApplication getApplication() {
        if (null == instance) {
            instance = new App();
        }
        return instance;
    }

}
