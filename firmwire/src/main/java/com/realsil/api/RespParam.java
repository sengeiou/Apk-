package com.realsil.api;

import java.util.List;

/**
 * Desc: TODO
 * <p>
 * Author: meijie
 * PackageName: com.realsil.api
 * ProjectName: APKCheck
 * Date: 2018/12/7 10:35
 */
public class RespParam {

    private String channel;
    private String packageName;
    private List<UpgradeTypesBean> upgradeTypes;

    public String getChannel() { return channel;}

    public void setChannel(String channel) { this.channel = channel;}

    public String getPackageName() { return packageName;}

    public void setPackageName(String packageName) { this.packageName = packageName;}

    public List<UpgradeTypesBean> getUpgradeTypes() { return upgradeTypes;}

    public void setUpgradeTypes(List<UpgradeTypesBean> upgradeTypes) { this.upgradeTypes = upgradeTypes;}

    public static class UpgradeTypesBean {

        private String name;
        private String version;
        private int generation;

        public int getGeneration() {
            return generation;
        }

        public void setGeneration(int generation) {
            this.generation = generation;
        }

        public UpgradeTypesBean(String name, String version, int generation) {
            this.name = name;
            this.version = version;
            this.generation = generation;
        }

        public String getName() { return name;}

        public void setName(String name) { this.name = name;}

        public String getVersion() { return version;}

        public void setVersion(String version) { this.version = version;}

        @Override
        public String toString() {
            return "UpgradeTypesBean{" +
                    "name='" + name + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "RespParam{" +
                "channel='" + channel + '\'' +
                ", packageName='" + packageName + '\'' +
                ", upgradeTypes=" + upgradeTypes +
                '}';
    }
}
