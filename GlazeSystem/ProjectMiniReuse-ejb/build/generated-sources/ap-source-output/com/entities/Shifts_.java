package com.entities;

import com.entities.Users;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2025-01-24T08:00:40")
@StaticMetamodel(Shifts.class)
public class Shifts_ { 

    public static volatile SingularAttribute<Shifts, Integer> shiftId;
    public static volatile SingularAttribute<Shifts, Date> createdAt;
    public static volatile SingularAttribute<Shifts, String> shiftType;
    public static volatile SingularAttribute<Shifts, Date> shiftDate;
    public static volatile SingularAttribute<Shifts, Users> creatorId;
    public static volatile SingularAttribute<Shifts, Date> startTime;
    public static volatile SingularAttribute<Shifts, Date> endTime;
    public static volatile SingularAttribute<Shifts, Users> userId;
    public static volatile SingularAttribute<Shifts, Date> updatedAt;

}