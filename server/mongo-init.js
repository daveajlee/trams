db.createUser(
    {
        user: "trams",
        pwd: "myTraMSpassword",
        roles: [
            {
                role: "readWrite",
                db: "trams"
            }
        ]
    }
);