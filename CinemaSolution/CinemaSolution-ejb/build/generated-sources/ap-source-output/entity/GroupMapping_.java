package entity;

import entity.Users;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-14T13:35:31")
@StaticMetamodel(GroupMapping.class)
public class GroupMapping_ { 

    public static volatile SingularAttribute<GroupMapping, String> groupName;
    public static volatile SingularAttribute<GroupMapping, String> email;
    public static volatile SingularAttribute<GroupMapping, Users> users;

}