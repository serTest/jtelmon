/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jtelmob;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
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
@Table(name = "nfunctions",schema = "public")
@NamedQueries({@NamedQuery(name = "Nfunctions.findByFunctionName", query = "SELECT n FROM Nfunctions n WHERE n.functionName = :functionName"), @NamedQuery(name = "Nfunctions.findByFunctionId", query = "SELECT n FROM Nfunctions n WHERE n.functionId = :functionId")})
public class Nfunctions implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Column(name = "function_name")
    private String functionName;
    @Id
    @Column(name = "function_id", nullable = false)
    private Long functionId;

    public Nfunctions() {
    }

    public Nfunctions(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        String oldFunctionName = this.functionName;
        this.functionName = functionName;
        changeSupport.firePropertyChange("functionName", oldFunctionName, functionName);
    }

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        Long oldFunctionId = this.functionId;
        this.functionId = functionId;
        changeSupport.firePropertyChange("functionId", oldFunctionId, functionId);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (functionId != null ? functionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nfunctions)) {
            return false;
        }
        Nfunctions other = (Nfunctions) object;
        if ((this.functionId == null && other.functionId != null) || (this.functionId != null && !this.functionId.equals(other.functionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jtelmob.Nfunctions[functionId=" + functionId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

}
