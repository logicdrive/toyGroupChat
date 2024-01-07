Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./user;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./file;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./externalSystem;./commands/run.ps1"

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./message;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./room;./commands/run.ps1"

Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./collectedData;./commands/run.ps1"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd ./gateway;./commands/run.ps1"