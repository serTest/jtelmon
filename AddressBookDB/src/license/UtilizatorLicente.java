/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package license;

import java.math.BigInteger;


public class UtilizatorLicente {
    private BigInteger userId;
    private String userIp;
    private String userFirstName;
    private String userLastName;
    private String licenseSerial;
    private String licenseName;
    private String licenseCompany;

    public UtilizatorLicente() {
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getLicenseSerial() {
        return licenseSerial;
    }

    public void setLicenseSerial(String licenseSerial) {
        this.licenseSerial = licenseSerial;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicenseCompany() {
        return licenseCompany;
    }

    public void setLicenseCompany(String licenseCompany) {
        this.licenseCompany = licenseCompany;
    }

}
