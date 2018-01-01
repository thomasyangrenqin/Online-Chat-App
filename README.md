
### Overview
---
Online ChatApp is a multi-player online chat application, developed by Java RMI and multi-layer MVC(Model-view-controller) architecture.

It utilized the visitor design pattern to support various unknown data packages, like text, image and file.

### Usage
---
You can use the launch file to launch the chat app.

In the client, to connect to another remote server by entering its IP address into the “Remote Host” field and clicking “Connect”.  After successful connection, you can obtain the chat room list of the remote host, the remote host also can get your chat room list, since we implemented auto connect back.

You can create your own chat room by typing a chat room name and then click "Create Room" button. You can join the
chat room of the remote host by selecting one of the chatrooms in the combo box list and then click "join room" button.

After joining a chat room, the app will automatically create a new chat room, in that chat room, you can send text message,
images and files to all the users in that chat room. The user list on the right side of the chat room is updating automatically.
