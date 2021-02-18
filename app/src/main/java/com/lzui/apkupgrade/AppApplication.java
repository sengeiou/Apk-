package com.lzui.apkupgrade;

import android.app.Application;
import android.content.Context;

import com.lunzn.tool.autofit.GetScreenSize;
import com.lunzn.tool.crash.CrashHandler;
import com.lunzn.tool.log.LogUtil;
import com.lunzn.tool.util.DeviceInfoMgr;
import com.lunzn.tool.util.DeviceInfoMgrDs;
import com.lzui.apkcheck.BuildConfig;
import com.lzui.apkupgrade.util.FileSavePathUtil;
import com.realsil.ota.DfuController;
import com.realsil.sdk.core.RtkConfigure;
import com.realsil.sdk.core.RtkCore;
import com.realsil.sdk.dfu.RtkDfu;
import com.smart.biz.DeviceInfoBiz;

import java.util.HashMap;
import java.util.Map;

public class AppApplication extends Application {

    public static Context mContext;

    /**
     * 缓存状态信息
     */
    private static Map<String, Object> _dataObj = new HashMap<String, Object>();

    /**
     * 获取设备信息业务类
     */
    private DeviceInfoBiz mInfoBiz = null;
    private String mSoftwareVersion;

    /**
     * 升级线程是否打开
     *
     * @return
     */
    public static boolean isUpgradeThreadStart() {
        return (boolean) _dataObj.get("isUpgradeThreadStart");
    }

    /**
     * 设置升级线程是否打开
     *
     * @param flag
     */
    public static void setUpgradeThreadStart(boolean flag) {
        _dataObj.put("isUpgradeThreadStart", flag);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInfoBiz = new DeviceInfoBiz(this);
        //DS项目
        if (BuildConfig.APPLICATION_ID.equalsIgnoreCase("hs.apk.update")) {
            try {
                DeviceInfoMgrDs.getInstance().connect(this, new DeviceInfoMgrDs.OnDeviceInfoListener() {
                    @Override
                    public void onResult(DeviceInfoMgrDs.DeviceInfo device) {
                        if (device != null) {
                            mSoftwareVersion = device.getSoftwareVersion();

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                DeviceInfoMgr.getInstance().connect(this, new DeviceInfoMgr.OnDeviceInfoListener() {
                    @Override
                    public void onResult(DeviceInfoMgr.DeviceInfo device) {
                        if (device != null) {
                            mSoftwareVersion = device.getSoftwareVersion();

                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        GetScreenSize.initData(this);

        LogUtil.init(this);
        LogUtil.setPrintLevel(LogUtil.LEVEL_V);
        LogUtil.setSaveLogPath(this, "apkcheck.log", LogUtil.LEVEL_V);

        CrashHandler.getInstance().init(this);

        FileSavePathUtil.initCachePath(this);
        LogUtil.d("start apk upgrade app");
        mContext = this;
        DfuController.getInstance().init(this);

        //第三代遥控器升级要初始化的
        RtkConfigure configure = new RtkConfigure.Builder()
                .debugEnabled(true)
                .printLog(true)
                .logTag("OTA")
                .globalLogLevel(1)
                .build();
        RtkCore.initialize(this, configure);
        // Mandatory, initialize rtk-dfu library
        // this: context
        // isDebug: true, switch on debug log; false, switch off debug log
        RtkDfu.initialize(this, true);

    }

    /**
     * 获取设备信息业务类
     *
     * @return 设备信息业务类对象
     */
    public DeviceInfoBiz getDeviceInfoBiz() {
        return mInfoBiz;
    }

    public String getSoftwareVersion() {
        return mSoftwareVersion;
    }
}
