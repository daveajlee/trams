// main.js
const { app, BrowserWindow, dialog, nativeImage } = require('electron');
const path = require('path');
const url = require('url')

// Error Handling
process.on('uncaughtException', (error) => {
  console.error("Unexpected error: ", error);
});
function createWindow() {
  const win = new BrowserWindow({
    title: "TraMS",
    webPreferences: {
      contextIsolation: true,
      enableRemoteModule: false,
      devTools: true
    }
  });
  win.maximize();

  //mac os x logo file
  if (process.platform === 'darwin') {
    app.dock.setIcon(path.join(__dirname, 'dist/tramsDesktop/browser/assets/trams-menu-logo.png'))
  }

  win.loadURL(url.format({
    pathname: path.join(__dirname, 'dist/tramsDesktop/browser/index.html'),
    protocol: 'file:',
    slashes: true
  }))
  win.webContents.openDevTools();

  win.on('close', e => { // Line 49
    e.preventDefault()
    const dialogIcon = nativeImage.createFromPath(path.join(__dirname, 'dist/tramsDesktop/browser/assets/trams-menu-logo.png'));
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
        win.destroy()
        app.quit()
      }
    })
  })

}
// App Lifecycle
app.whenReady().then(createWindow);
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') app.quit();
});
app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) createWindow();
});
