Start-Process powershell -ArgumentList "-Command", "cd ./user;./commands/kubernetes/deleteAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./file;./commands/kubernetes/deleteAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./externalSystem;./commands/kubernetes/deleteAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./message;./commands/kubernetes/deleteAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./room;./commands/kubernetes/deleteAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./collectedData;./commands/kubernetes/deleteAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./gateway;./commands/kubernetes/deleteAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./frontend;./commands/kubernetes/deleteAll.ps1"