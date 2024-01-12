# How to Install Fastlane

* Install Fastlane via Homebrew:
`brew install fastlane`

# Set up Fastlane 
* In the root project directory: `fastlane init`
* Then change to the ios directory: `cd ios`
* Run the command again: `fastlane init`
* Enter when prompted Apple Developer ID
* Enter when prompted Password
* Enter when prompted 2FA Code
* Press enter 3 times to confirm.

# Environment File for FastLane

Create a file called .env.default in the fastlane directory of the ios directory with the following content:

FASTLANE_USER=APPLE_DEVELOPER_USERNAME
FASTLANE_APPLE_APPLICATION_SPECIFIC_PASSWORD=PASSWORD_ASSIGNED_FROM_APP_SPECIFIC_PASSWORD_IN_CONSOLE

# Upload a beta release to TestFlight

* In the ios directory run the command: `fastlane beta`

# Future Work

Make it compatible with GitHub Actions