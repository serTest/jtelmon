/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jtelmob;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author sefit
 */
@Entity
@Table(name = "ncities",schema = "public")
@NamedQueries({@NamedQuery(name = "Ncities.findByCityName", query = "SELECT n FROM Ncities n WHERE n.cityName = :cityName"), @NamedQuery(name = "Ncities.findByCityId", query = "SELECT n FROM Ncities n WHERE n.cityId = :cityId")})
public class Ncities implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Column(name = "city_name")
    private String cityName;
    @Id
    @Column(name = "city_id", nullable = false)
    private BigDecimal cityId;

    public Ncities() {
    }

    public Ncities(BigDecimal cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        String oldCityName = this.cityName;
        this.cityName = cityName;
        changeSupport.firePropertyChange("cityName", oldCityName, cityName);
    }

    public BigDecimal getCityId() {
        return cityId;
    }

    public void setCityId(BigDecimal cityId) {
        BigDecimal oldCityId = this.cityId;
        this.cityId = cityId;
        changeSupport.firePropertyChange("cityId", oldCityId, cityId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cityId != null ? cityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ncities)) {
            return false;
        }
        Ncities other = (Ncities) object;
        if ((this.cityId == null && other.cityId != null) || (this.cityId != null && !this.cityId.equals(other.cityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jtelmob.Ncities[cityId=" + cityId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
