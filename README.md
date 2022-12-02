
This repository is the demonstration of BLE(Bluetooth Low Energy) Device.

Supported Feature :-
------------------
- Filtering, scanning, linking, reading, writing, notification subscription and cancellation in a simple way.
- Supports acquiring signal strength and setting the maximum transmission unit.
- Support custom scan rules
- Support multi device connections
- Support reconnection
- Support configuration timeout for connect or operation

BLE Functions Details :-
------------------

# BLE
Bluetooth Low Energy, a subset of the 2.4 GHz Bluetooth wireless technology that specializes in low power and oftentimes infrequent data transmissions for connected devices.

# Central/Client
A device that scans for and connects to BLE peripherals in order to perform some operation. In the context of app development, this is typically an Android device.

# Peripheral/Server
A device that advertises its presence and is connected to by a central in order to accomplish some task. In the context of app development, this is typically a BLE device you’re working with, like a heart rate monitor.

# GATT Service
A collection of characteristics (data fields) that describes a feature of a device, e.g. the Device Information service can contain a characteristic representing the serial number of the device, and another characteristic representing the battery level of the device.

# GATT Characteristic
An entity containing meaningful data that can typically be read from or written to, e.g. the Serial Number String characteristic.

# GATT Descriptor
A defined attribute that describes the characteristic that it’s attached to, e.g. the Client Characteristic Configuration descriptor shows if the central is currently subscribed to a characteristic’s value change.

# Notifications
A means for a BLE peripheral to notify the central when a characteristic’s value changes. The central doesn't need to acknowledge that it’s received the packet.

# Indications
Same as an indication, except each data packet is acknowledged by the central. This guarantees their delivery at the cost of throughput.

# UUID
Universally unique identifier, 128-bit number used to identify services, characteristics and descriptors.
