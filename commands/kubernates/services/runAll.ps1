Start-Process powershell -ArgumentList "-Command", "cd ./user;./commands/kubernetes/makeAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./file;./commands/kubernetes/makeAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./externalSystem;./commands/kubernetes/makeAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./message;./commands/kubernetes/makeAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./room;./commands/kubernetes/makeAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./collectedData;./commands/kubernetes/makeAll.ps1"
Start-Process powershell -ArgumentList "-Command", "cd ./gateway;./commands/kubernetes/makeAll.ps1"

Start-Process powershell -ArgumentList "-Command", "cd ./frontend;./commands/kubernetes/makeAll.ps1"