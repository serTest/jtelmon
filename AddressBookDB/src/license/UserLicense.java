/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package license;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "user_license", catalog = "license", schema = "public")
@NamedQueries({
    @NamedQuery(name = "UserLicense.findAll", query = "SELECT u FROM UserLicense u"),
    @NamedQuery(name = "UserLicense.findByUserId", query = "SELECT u FROM UserLicense u WHERE u.userLicensePK.userId = :userId"),
    @NamedQuery(name = "UserLicense.findByLicenseId", query = "SELECT u FROM UserLicense u WHERE u.userLicensePK.licenseId = :licenseId"),
    @NamedQuery(name = "UserLicense.findByLicenseSerial", query = "SELECT u FROM UserLicense u WHERE u.licenseSerial = :licenseSerial")})
public class UserLicense implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserLicensePK userLicensePK;
    @Column(name = "license_serial", length = 2147483647)
    private String licenseSerial;

    public UserLicense() {
    }

    public UserLicense(UserLicensePK userLicensePK) {
        this.userLicensePK = userLicensePK;
    }

    public UserLicense(long userId, long licenseId) {
        this.userLicensePK = new UserLicensePK(userId, licenseId);
    }

    public UserLicensePK getUserLicensePK() {
        return userLicensePK;
    }

    public void setUserLicensePK(UserLicensePK userLicensePK) {
        this.userLicensePK = userLicensePK;
    }

    public String getLicenseSerial() {
        return licenseSerial;
    }

    public void setLicenseSerial(String licenseSerial) {
        this.licenseSerial = licenseSerial;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userLicensePK != null ? userLicensePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLicense)) {
            return false;
        }
        UserLicense other = (UserLicense) object;
        if ((this.userLicensePK == null && other.userLicensePK != null) || (this.userLicensePK != null && !this.userLicensePK.equals(other.userLicensePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "license.UserLicense[userLicensePK=" + userLicensePK + "]";
    }

}
