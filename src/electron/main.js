const electron = require('electron')
// Module to control application life.
const app = electron.app
//Module to control dialog.
const dialog = electron.dialog
// Module to create native browser window.
const BrowserWindow = electron.BrowserWindow
//Module to load native images
const nativeImage = electron.nativeImage

const path = require('path')
const url = require('url')

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let mainWindow

function createWindow () {
    // Create the browser window.
    mainWindow = new BrowserWindow({title: "TraMS", icon:path.join(__dirname, 'trams-frontend/assets/trams-menu-logo.png')})
    mainWindow.maximize()

    //mac os x logo file
    if (process.platform === 'darwin') {
        app.dock.setIcon(path.join(__dirname, 'trams-frontend/assets/trams-menu-logo.png'))
    }

    // and load the index.html of the app.
    mainWindow.loadURL(url.format({
        pathname: path.join(__dirname, 'trams-frontend/index.html'),
        protocol: 'file:',
        slashes: true
    }))

    // Open the DevTools.
    // mainWindow.webContents.openDevTools()

    mainWindow.on('close', e => { // Line 49
        e.preventDefault()
        const dialogIcon = nativeImage.createFromPath(path.join(__dirname, 'trams-frontend/assets/trams-menu-logo.png'));
        dialog.showMessageBox({
            type: 'info',
            buttons: ['No', 'Yes'],
            cancelId: 1,
            defaultId: 0,
            icon: dialogIcon,
            title: 'Please Confirm Exit',
            detail: 'Are you sure you wish to exit TraMS?'
        }).then(({ response, checkboxChecked }) => {
            if (response) {
                mainWindow.destroy()
                app.quit()
            }
        })
    })

    // Emitted when the window is closed.
    mainWindow.on('closed', function () {
        // Dereference the window object, usually you would store windows
        // in an array if your app supports multi windows, this is the time
        // when you should delete the corresponding element.
        mainWindow = null
    })
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {
    // On OS X it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
        app.quit()
    }
})

app.on('activate', function () {
    // On OS X it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (mainWindow === null) {
        createWindow()
    }
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.