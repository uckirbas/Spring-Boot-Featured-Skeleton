package com.example.servicito.domains.remoteconfigs.models.entities;

import com.example.coreweb.domains.base.entities.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "remote_configs")
public class RemoteConfig extends BaseEntity {
    private String appName;
    @Column(unique = true,nullable = false)
    @NotNull
    private String appPackage;
    private String currentAppVersion;
    private String newAppVersion;
    private String appUrl;
    private boolean forceUpdate;
    private boolean turnedOff;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getCurrentAppVersion() {
        return currentAppVersion;
    }

    public void setCurrentAppVersion(String currentAppVersion) {
        this.currentAppVersion = currentAppVersion;
    }

    public String getNewAppVersion() {
        return newAppVersion;
    }

    public void setNewAppVersion(String newAppVersion) {
        this.newAppVersion = newAppVersion;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public boolean isTurnedOff() {
        return turnedOff;
    }

    public void setTurnedOff(boolean turnedOff) {
        this.turnedOff = turnedOff;
    }
}
