db.createUser(
    {
        user: "tramsCrm",
        pwd: "myTraMSpassword",
        roles: [
            {
                role: "readWrite",
                db: "tramsCrm"
            }
        ]
    }
);