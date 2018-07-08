# Device set up
## Virtual device
Install kvm:
  sudo apt install qemu-kvm
Add user to the group:
  sudo adduser <username> kvm

## Physical device:
Install adb:
  sudo apt install adb

Probe devices:
  adb devices

When no devices detected - on the device:
* Settings -> About phone -> Tap on Build number several times
* Settings -> Developer -> USB debugging -> Enable

On CLI again:
  adb devices
On the phone accept connection from the host

# Build
On the right expand the Gradle menu.
* android -> Tasks -> build -> assembleDebug

# Test

# Deploy
On the right expand the Gradle menu.
* android -> Tasks -> install -> installDebug