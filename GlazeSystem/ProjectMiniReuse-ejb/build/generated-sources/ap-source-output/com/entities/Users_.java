package com.entities;

import com.entities.Shifts;
import com.entities.TaskNotes;
import com.entities.Tasks;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-01-24T08:00:40")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile CollectionAttribute<Users, Shifts> shiftsCollection1;
    public static volatile SingularAttribute<Users, String> role;
    public static volatile CollectionAttribute<Users, TaskNotes> taskNotesCollection;
    public static volatile SingularAttribute<Users, String> fullName;
    public static volatile SingularAttribute<Users, Boolean> isActive;
    public static volatile SingularAttribute<Users, Integer> userId;
    public static volatile SingularAttribute<Users, Date> createdAt;
    public static volatile SingularAttribute<Users, String> password;
    public static volatile CollectionAttribute<Users, Shifts> shiftsCollection;
    public static volatile CollectionAttribute<Users, Tasks> tasksCollection;
    public static volatile CollectionAttribute<Users, Tasks> tasksCollection1;
    public static volatile SingularAttribute<Users, String> email;
    public static volatile SingularAttribute<Users, String> username;

}