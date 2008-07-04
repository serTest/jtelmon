/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package olaptest1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author sefit
 */
@Entity
@Table(name = "CARRENTAL")
@NamedQueries({@NamedQuery(name = "Carrental.findByTripid", query = "SELECT c FROM Carrental c WHERE c.tripid = :tripid"), @NamedQuery(name = "Carrental.findByCarrentalid", query = "SELECT c FROM Carrental c WHERE c.carrentalid = :carrentalid"), @NamedQuery(name = "Carrental.findByProvider", query = "SELECT c FROM Carrental c WHERE c.provider = :provider"), @NamedQuery(name = "Carrental.findByCity", query = "SELECT c FROM Carrental c WHERE c.city = :city"), @NamedQuery(name = "Carrental.findByPickupdate", query = "SELECT c FROM Carrental c WHERE c.pickupdate = :pickupdate"), @NamedQuery(name = "Carrental.findByReturndate", query = "SELECT c FROM Carrental c WHERE c.returndate = :returndate"), @NamedQuery(name = "Carrental.findByCartype", query = "SELECT c FROM Carrental c WHERE c.cartype = :cartype"), @NamedQuery(name = "Carrental.findByRate", query = "SELECT c FROM Carrental c WHERE c.rate = :rate"), @NamedQuery(name = "Carrental.findByBookingstatus", query = "SELECT c FROM Carrental c WHERE c.bookingstatus = :bookingstatus"), @NamedQuery(name = "Carrental.findByLastupdated", query = "SELECT c FROM Carrental c WHERE c.lastupdated = :lastupdated")})
public class Carrental implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Column(name = "TRIPID", nullable = false)
    private int tripid;
    @Id
    @Column(name = "CARRENTALID", nullable = false)
    private Integer carrentalid;
    @Column(name = "PROVIDER")
    private String provider;
    @Column(name = "CITY")
    private String city;
    @Column(name = "PICKUPDATE")
    @Temporal(TemporalType.DATE)
    private Date pickupdate;
    @Column(name = "RETURNDATE")
    @Temporal(TemporalType.DATE)
    private Date returndate;
    @Column(name = "CARTYPE")
    private String cartype;
    @Column(name = "RATE")
    private BigDecimal rate;
    @Column(name = "BOOKINGSTATUS")
    private String bookingstatus;
    @Column(name = "LASTUPDATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastupdated;

    public Carrental() {
    }

    public Carrental(Integer carrentalid) {
        this.carrentalid = carrentalid;
    }

    public Carrental(Integer carrentalid, int tripid) {
        this.carrentalid = carrentalid;
        this.tripid = tripid;
    }

    public int getTripid() {
        return tripid;
    }

    public void setTripid(int tripid) {
        int oldTripid = this.tripid;
        this.tripid = tripid;
        changeSupport.firePropertyChange("tripid", oldTripid, tripid);
    }

    public Integer getCarrentalid() {
        return carrentalid;
    }

    public void setCarrentalid(Integer carrentalid) {
        Integer oldCarrentalid = this.carrentalid;
        this.carrentalid = carrentalid;
        changeSupport.firePropertyChange("carrentalid", oldCarrentalid, carrentalid);
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        String oldProvider = this.provider;
        this.provider = provider;
        changeSupport.firePropertyChange("provider", oldProvider, provider);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        String oldCity = this.city;
        this.city = city;
        changeSupport.firePropertyChange("city", oldCity, city);
    }

    public Date getPickupdate() {
        return pickupdate;
    }

    public void setPickupdate(Date pickupdate) {
        Date oldPickupdate = this.pickupdate;
        this.pickupdate = pickupdate;
        changeSupport.firePropertyChange("pickupdate", oldPickupdate, pickupdate);
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        Date oldReturndate = this.returndate;
        this.returndate = returndate;
        changeSupport.firePropertyChange("returndate", oldReturndate, returndate);
    }

    public String getCartype() {
        return cartype;
    }

    public void setCartype(String cartype) {
        String oldCartype = this.cartype;
        this.cartype = cartype;
        changeSupport.firePropertyChange("cartype", oldCartype, cartype);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        BigDecimal oldRate = this.rate;
        this.rate = rate;
        changeSupport.firePropertyChange("rate", oldRate, rate);
    }

    public String getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(String bookingstatus) {
        String oldBookingstatus = this.bookingstatus;
        this.bookingstatus = bookingstatus;
        changeSupport.firePropertyChange("bookingstatus", oldBookingstatus, bookingstatus);
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        Date oldLastupdated = this.lastupdated;
        this.lastupdated = lastupdated;
        changeSupport.firePropertyChange("lastupdated", oldLastupdated, lastupdated);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carrentalid != null ? carrentalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrental)) {
            return false;
        }
        Carrental other = (Carrental) object;
        if ((this.carrentalid == null && other.carrentalid != null) || (this.carrentalid != null && !this.carrentalid.equals(other.carrentalid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "olaptest1.Carrental[carrentalid=" + carrentalid + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

}
