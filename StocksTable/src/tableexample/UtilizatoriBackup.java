/*
 * UtilizatoriBackup.java
 *
 * Created on April 1, 2007, 4:59 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package tableexample;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity class UtilizatoriBackup
 * 
 * @author alex
 */
@Entity
@Table(name = "utilizatori_backup")
@NamedQueries( {
        @NamedQuery(name = "UtilizatoriBackup.findByNumarTelefon", query = "SELECT u FROM UtilizatoriBackup u WHERE u.numarTelefon = :numarTelefon"),
        @NamedQuery(name = "UtilizatoriBackup.findByNumePrenume", query = "SELECT u FROM UtilizatoriBackup u WHERE u.numePrenume = :numePrenume"),
        @NamedQuery(name = "UtilizatoriBackup.findByFunctie", query = "SELECT u FROM UtilizatoriBackup u WHERE u.functie = :functie"),
        @NamedQuery(name = "UtilizatoriBackup.findByZona", query = "SELECT u FROM UtilizatoriBackup u WHERE u.zona = :zona"),
        @NamedQuery(name = "UtilizatoriBackup.findByLocalitate", query = "SELECT u FROM UtilizatoriBackup u WHERE u.localitate = :localitate"),
        @NamedQuery(name = "UtilizatoriBackup.findByDeductibil", query = "SELECT u FROM UtilizatoriBackup u WHERE u.deductibil = :deductibil"),
        @NamedQuery(name = "UtilizatoriBackup.findByStampila", query = "SELECT u FROM UtilizatoriBackup u WHERE u.stampila = :stampila")
    })
public class UtilizatoriBackup implements Serializable {

    @Id
    @Column(name = "numar_telefon", nullable = false)
    private BigInteger numarTelefon;

    @Column(name = "nume_prenume")
    private String numePrenume;

    @Column(name = "functie")
    private String functie;

    @Column(name = "zona", nullable = false)
    private String zona;

    @Column(name = "localitate")
    private String localitate;

    @Column(name = "deductibil")
    private Double deductibil;

    @Column(name = "stampila")
    @Temporal(TemporalType.TIMESTAMP)
    private Date stampila;
    
    /** Creates a new instance of UtilizatoriBackup */
    public UtilizatoriBackup() {
    }

    /**
     * Creates a new instance of UtilizatoriBackup with the specified values.
     * @param numarTelefon the numarTelefon of the UtilizatoriBackup
     */
    public UtilizatoriBackup(BigInteger numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    /**
     * Creates a new instance of UtilizatoriBackup with the specified values.
     * @param numarTelefon the numarTelefon of the UtilizatoriBackup
     * @param zona the zona of the UtilizatoriBackup
     */
    public UtilizatoriBackup(BigInteger numarTelefon, String zona) {
        this.numarTelefon = numarTelefon;
        this.zona = zona;
    }

    /**
     * Gets the numarTelefon of this UtilizatoriBackup.
     * @return the numarTelefon
     */
    public BigInteger getNumarTelefon() {
        return this.numarTelefon;
    }

    /**
     * Sets the numarTelefon of this UtilizatoriBackup to the specified value.
     * @param numarTelefon the new numarTelefon
     */
    public void setNumarTelefon(BigInteger numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    /**
     * Gets the numePrenume of this UtilizatoriBackup.
     * @return the numePrenume
     */
    public String getNumePrenume() {
        return this.numePrenume;
    }

    /**
     * Sets the numePrenume of this UtilizatoriBackup to the specified value.
     * @param numePrenume the new numePrenume
     */
    public void setNumePrenume(String numePrenume) {
        this.numePrenume = numePrenume;
    }

    /**
     * Gets the functie of this UtilizatoriBackup.
     * @return the functie
     */
    public String getFunctie() {
        return this.functie;
    }

    /**
     * Sets the functie of this UtilizatoriBackup to the specified value.
     * @param functie the new functie
     */
    public void setFunctie(String functie) {
        this.functie = functie;
    }

    /**
     * Gets the zona of this UtilizatoriBackup.
     * @return the zona
     */
    public String getZona() {
        return this.zona;
    }

    /**
     * Sets the zona of this UtilizatoriBackup to the specified value.
     * @param zona the new zona
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * Gets the localitate of this UtilizatoriBackup.
     * @return the localitate
     */
    public String getLocalitate() {
        return this.localitate;
    }

    /**
     * Sets the localitate of this UtilizatoriBackup to the specified value.
     * @param localitate the new localitate
     */
    public void setLocalitate(String localitate) {
        this.localitate = localitate;
    }

    /**
     * Gets the deductibil of this UtilizatoriBackup.
     * @return the deductibil
     */
    public Double getDeductibil() {
        return this.deductibil;
    }

    /**
     * Sets the deductibil of this UtilizatoriBackup to the specified value.
     * @param deductibil the new deductibil
     */
    public void setDeductibil(Double deductibil) {
        this.deductibil = deductibil;
    }

    /**
     * Gets the stampila of this UtilizatoriBackup.
     * @return the stampila
     */
    public Date getStampila() {
        return this.stampila;
    }

    /**
     * Sets the stampila of this UtilizatoriBackup to the specified value.
     * @param stampila the new stampila
     */
    public void setStampila(Date stampila) {
        this.stampila = stampila;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.numarTelefon != null ? this.numarTelefon.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this UtilizatoriBackup.  The result is 
     * <code>true</code> if and only if the argument is not null and is a UtilizatoriBackup object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UtilizatoriBackup)) {
            return false;
        }
        UtilizatoriBackup other = (UtilizatoriBackup)object;
        if (this.numarTelefon != other.numarTelefon && (this.numarTelefon == null || !this.numarTelefon.equals(other.numarTelefon))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "tableexample.UtilizatoriBackup[numarTelefon=" + numarTelefon + "]";
    }
    
}
