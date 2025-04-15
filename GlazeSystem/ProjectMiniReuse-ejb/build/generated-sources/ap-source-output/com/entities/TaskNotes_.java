package com.entities;

import com.entities.Tasks;
import com.entities.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-01-24T08:00:40")
@StaticMetamodel(TaskNotes.class)
public class TaskNotes_ { 

    public static volatile SingularAttribute<TaskNotes, Date> createdAt;
    public static volatile SingularAttribute<TaskNotes, String> noteContent;
    public static volatile SingularAttribute<TaskNotes, Integer> noteId;
    public static volatile SingularAttribute<TaskNotes, Users> userId;
    public static volatile SingularAttribute<TaskNotes, Tasks> taskId;

}