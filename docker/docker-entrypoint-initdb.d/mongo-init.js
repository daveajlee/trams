print('Create database for Personalman');
db = db.getSiblingDB('personalman')
db.createUser(
    {
        user: "personalman",
        pwd: "myPMpassword",
        roles: [{role: "readWrite", db: "personalman"}]
    }
);

print('Create database for TraMS Business');
db = db.getSiblingDB('tramsBusiness');
db.createUser(
    {
        user: "tramsBusiness",
        pwd: "myTraMSpassword",
        roles: [{role: "readWrite", db: "tramsBusiness"}]
    }
);

print('Create database for TraMS CRM');
db = db.getSiblingDB('tramsCrm');
db.createUser(
    {
        user: "tramsCrm",
        pwd: "myTraMSpassword",
        roles: [{role: "readWrite", db: "tramsCrm"}]
    }
);

print('Create database for TraMS Operations')
db = db.getSiblingDB('tramsOperations');
db.createUser(
    {
        user: "tramsOperations",
        pwd: "myTraMSpassword",
        roles: [{role: "readWrite", db: "tramsOperations"}]
    }
);