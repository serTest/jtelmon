/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package license;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author User
 */
@Entity
@Table(name = "license_agreements", catalog = "license", schema = "public")
@NamedQueries({
    @NamedQuery(name = "LicenseAgreements.findAll", query = "SELECT l FROM LicenseAgreements l"),
    @NamedQuery(name = "LicenseAgreements.findByLicenseId", query = "SELECT l FROM LicenseAgreements l WHERE l.licenseId = :licenseId"),
    @NamedQuery(name = "LicenseAgreements.findByLicenseName", query = "SELECT l FROM LicenseAgreements l WHERE l.licenseName = :licenseName"),
    @NamedQuery(name = "LicenseAgreements.findByLicenseCompany", query = "SELECT l FROM LicenseAgreements l WHERE l.licenseCompany = :licenseCompany")})
public class LicenseAgreements implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "license_id", nullable = false)
    private Long licenseId;
    @Column(name = "license_name", length = 2147483647)
    private String licenseName;
    @Column(name = "license_company", length = 2147483647)
    private String licenseCompany;

    public LicenseAgreements() {
    }

    public LicenseAgreements(Long licenseId) {
        this.licenseId = licenseId;
    }

    public Long getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Long licenseId) {
        this.licenseId = licenseId;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (licenseId != null ? licenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LicenseAgreements)) {
            return false;
        }
        LicenseAgreements other = (LicenseAgreements) object;
        if ((this.licenseId == null && other.licenseId != null) || (this.licenseId != null && !this.licenseId.equals(other.licenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "license.LicenseAgreements[licenseId=" + licenseId + "]";
    }

}
