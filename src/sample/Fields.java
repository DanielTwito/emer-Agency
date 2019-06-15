package sample;

public enum Fields {
                    //complaintRequest Table
                     requestId,
                     content,
                     owner,
                     complaintUser,
                     isApproved,

                    //events Table
                    eventId,
                    time,
                    status,
                    headLine,
                    categoryName,
                    representiveName_events,

                    //eventUpdates Table
                    date,
                    version,
                    description,

                    //userss Table
                    userName,
                    name,
                    password,
                    rank,
                    score,
                    userStatus,
                    email,
                    isAdmin,
                    warrings,

                    //organizationMembers Tables
                    organization,

                    //representive
                    representiveName,
                    representivePassword,

                    //permissions
                    permission,

    }
