/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JD028;


public class User {

    private Long userId;
    private String userIp;
    private String userFirstName;
    private String userLastName;
    private String alias;

    public User(Long userId, String userIp, String userFirstName, String userLastName) {
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

}
