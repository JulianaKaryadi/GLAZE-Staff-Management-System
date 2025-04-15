package com.entities;

import com.entities.TaskNotes;
import com.entities.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-01-24T08:00:40")
@StaticMetamodel(Tasks.class)
public class Tasks_ { 

    public static volatile SingularAttribute<Tasks, Users> assignedToId;
    public static volatile SingularAttribute<Tasks, Date> createdAt;
    public static volatile SingularAttribute<Tasks, Date> completedAt;
    public static volatile CollectionAttribute<Tasks, TaskNotes> taskNotesCollection;
    public static volatile SingularAttribute<Tasks, Date> dueDate;
    public static volatile SingularAttribute<Tasks, Users> creatorId;
    public static volatile SingularAttribute<Tasks, String> description;
    public static volatile SingularAttribute<Tasks, String> title;
    public static volatile SingularAttribute<Tasks, String> priority;
    public static volatile SingularAttribute<Tasks, Integer> taskId;
    public static volatile SingularAttribute<Tasks, String> status;
    public static volatile SingularAttribute<Tasks, Date> updatedAt;

}