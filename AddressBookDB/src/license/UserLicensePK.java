/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package license;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author User
 */
@Embeddable
public class UserLicensePK implements Serializable {
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private long userId;
    @Basic(optional = false)
    @Column(name = "license_id", nullable = false)
    private long licenseId;

    public UserLicensePK() {
    }

    public UserLicensePK(long userId, long licenseId) {
        this.userId = userId;
        this.licenseId = licenseId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(long licenseId) {
        this.licenseId = licenseId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) userId;
        hash += (int) licenseId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserLicensePK)) {
            return false;
        }
        UserLicensePK other = (UserLicensePK) object;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.licenseId != other.licenseId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "license.UserLicensePK[userId=" + userId + ", licenseId=" + licenseId + "]";
    }

}
