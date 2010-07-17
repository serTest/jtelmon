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
@Table(name = "users", catalog = "license", schema = "public")
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findByUserIp", query = "SELECT u FROM Users u WHERE u.userIp = :userIp"),
    @NamedQuery(name = "Users.findByUserFirstName", query = "SELECT u FROM Users u WHERE u.userFirstName = :userFirstName"),
    @NamedQuery(name = "Users.findByUserLastName", query = "SELECT u FROM Users u WHERE u.userLastName = :userLastName"),
    @NamedQuery(name = "Users.findByAlias", query = "SELECT u FROM Users u WHERE u.alias = :alias")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @Basic(optional = false)
    @Column(name = "user_ip", nullable = false, length = 2147483647)
    private String userIp;
    @Basic(optional = false)
    @Column(name = "user_first_name", nullable = false, length = 2147483647)
    private String userFirstName;
    @Basic(optional = false)
    @Column(name = "user_last_name", nullable = false, length = 2147483647)
    private String userLastName;
    @Column(name = "alias", length = 2147483647)
    private String alias;

    public Users() {
    }

    public Users(Long userId) {
        this.userId = userId;
    }

    public Users(Long userId, String userIp, String userFirstName, String userLastName) {
        this.userId = userId;
        this.userIp = userIp;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "license.Users[userId=" + userId + "]";
    }

}
