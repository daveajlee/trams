module.exports = {
    "packagerConfig": {
        "appBundleId": "de.davelee.trams.desktop",
        "osxSign": {}
    },
    "makers": [
        {
            "name": "@electron-forge/maker-zip"
        },
        {
            "name": "@electron-forge/maker-pkg",
            "config": {
                "type": "distribution",
                "platforms": "mas",
                "entitlements": process.env.ENTITLEMENTS_FILE,
                "identity": process.env.IDENTITY_ACCOUNT,
                "provisioningProfile": process.env.PROVISIONING_PROFILE,
                "minimumSystemVersion": "12.0",
                "version": "0.5.1"
            }
        }
    ]
}