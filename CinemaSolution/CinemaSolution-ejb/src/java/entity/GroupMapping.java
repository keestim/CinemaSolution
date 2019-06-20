/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author KeesTim
 */
@Entity
@Table(name = "GROUP_MAPPING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GroupMapping.findAll", query = "SELECT g FROM GroupMapping g")
    , @NamedQuery(name = "GroupMapping.findByEmail", query = "SELECT g FROM GroupMapping g WHERE g.email = :email")
    , @NamedQuery(name = "GroupMapping.findByGroupName", query = "SELECT g FROM GroupMapping g WHERE g.groupName = :groupName")})
public class GroupMapping implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 90)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "GROUP_NAME")
    private String groupName;
    @JoinColumn(name = "EMAIL", referencedColumnName = "EMAIL", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Users users;

    public GroupMapping() {
    }

    public GroupMapping(String email) {
        this.email = email;
    }

    public GroupMapping(String email, String groupName) {
        this.email = email;
        this.groupName = groupName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (email != null ? email.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroupMapping)) {
            return false;
        }
        GroupMapping other = (GroupMapping) object;
        if ((this.email == null && other.email != null) || (this.email != null && !this.email.equals(other.email))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.GroupMapping[ email=" + email + " ]";
    }
    
}
